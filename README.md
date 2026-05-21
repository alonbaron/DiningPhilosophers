# Dining Philosophers — Concurrency Visualizer

A live, Swing-based visualization of Dijkstra's classic Dining Philosophers problem, written in Java. Five philosopher threads compete for five shared forks around a round table; the GUI shows each philosopher's state (thinking, waiting, eating) in real time and lets you kill threads at random to watch the system degrade gracefully.

The point isn't the simulation — the point is the **concurrency model** that keeps it deadlock-free.

---

## 🏗️ Architecture & Design

### Deadlock prevention via resource ordering

The naive solution to Dining Philosophers — "pick up your left fork, then your right" — deadlocks the moment all five philosophers grab their left fork at the same time. Nobody can ever pick up a second fork. Classic circular wait.

This implementation breaks the circular-wait condition with **Dijkstra's hierarchical lock-ordering strategy**. In the `Philosopher` constructor, the two assigned forks are sorted by number:

```java
if (fork1.getNumber() < fork2.getNumber()) {
    this.firstFork  = fork1;
    this.secondFork = fork2;
} else {
    this.firstFork  = fork2;
    this.secondFork = fork1;
}
```

Every philosopher then acquires `firstFork` (lower number) before `secondFork` (higher). Because the acquisition order is globally consistent, **no circular wait is possible** — a deadlock cycle can't form. One of the four Coffman conditions is structurally eliminated.

### Graceful shutdown

Each philosopher's run loop is gated by a `volatile boolean isRunning` flag. The "Stop Philosopher" button in the UI flips the flag on a randomly chosen thread; the philosopher releases any held fork on its next loop iteration and exits cleanly. `volatile` is deliberate here — without it, the worker thread could cache the flag in a register and never see the update.

### Swing thread discipline

Swing has one rule: touch UI components only on the Event Dispatch Thread. A separate polling thread reads philosopher state once per second and dispatches the label updates through `SwingUtilities.invokeLater(...)`, keeping the simulation threads and the EDT properly isolated.

### Component breakdown

| File | Responsibility |
|---|---|
| `Main.java` | Bootstraps the `JFrame` and main panel |
| `MainPanel.java` | Lays out the table, wires up forks, philosophers, labels, and the stop button |
| `Philosopher.java` | One philosopher = one worker thread; owns the think → wait → eat state machine |
| `Fork.java` | Shared resource; tracks which philosopher (if any) currently holds it; renders itself |
| `ForkPositioning.java` | Maps `(fork, philosopher)` pairs to pixel coordinates when a fork is held |
| `Utils.java` | `Thread.sleep` wrapper with checked-exception handling |

---

## 🧠 The Journey — From Busy-Wait to ReentrantLock

The original implementation used a **check-then-act busy-wait** for fork acquisition:

```java
while (firstFork.getHeldBy() != null) { Utils.sleep(100); }
firstFork.setHeldBy(this);
```

This prevented deadlock thanks to the ordering rule, but it had a real race condition: two philosophers could observe `heldBy == null` in the same window and both claim the same fork. Mutual exclusion was the weak link.

The current version gives each `Fork` a `java.util.concurrent.locks.ReentrantLock` and exposes `tryAcquire()` / `release()`. Acquisition becomes atomic: a philosopher polls `tryAcquire()` in a loop, and only on success does it call `setHeldBy(this)`. The `heldBy` field stays — it's still needed for the visualization (`Fork.draw` reads it to know whose hand to render the fork in) — but it's now set strictly *after* the lock has been won.

One subtle bug survived the initial refactor: a narrow window between a successful `tryAcquire()` and the `isRunning` shutdown check meant `stop()` could flip the flag after the lock was won but before the thread checked it, causing the thread to break out while still holding the lock — leaking it forever. A follow-up commit captures acquisition success in a local boolean and releases explicitly on the shutdown branch — so the mutual-exclusion guarantee holds during shutdown as well as during normal operation.

Combined with the ordering rule, the system now has both guarantees:

- **No deadlock** — resource ordering breaks the circular-wait condition (Coffman #4)
- **No double-holding** — the `ReentrantLock` enforces mutual exclusion (Coffman #1) properly

Possible next steps if I revisit again:

- Model each fork as a `Semaphore(1)` for variety, or
- Add an arbitrator (waiter) thread to cap concurrent eaters at N-1 and avoid contention altogether

---

## 🛠️ How to Run

Requires JDK 17+ (uses switch expressions).

```bash
git clone https://github.com/alonbaron/DiningPhilosophers.git
cd DiningPhilosophers
javac *.java
java Main
```

Or open `DiningPhilosophers.iml` in IntelliJ and run `Main`.

You should see five philosophers around a round table, each cycling through `thinking → waiting for fork → eating`. Labels turn red while eating. The **Stop Philosopher** button kills one philosopher at random — the others keep running.

---

## 📚 Concepts Touched

- Concurrent programming with `java.lang.Thread`
- The four Coffman conditions for deadlock and how to break each one
- Resource hierarchy / lock ordering as a deadlock prevention strategy
- The `volatile` keyword and the Java Memory Model
- Swing threading model and `SwingUtilities.invokeLater`
- Separating simulation state from rendering

---

## 📝 License

MIT License. See [LICENSE](LICENSE).
