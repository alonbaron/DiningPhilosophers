
public class Main {
    public static void main(String[] args) {
        Fork fork1 = new Fork(1);
        Fork fork2 = new Fork(2);
        Fork fork3 = new Fork(3);
        Fork fork4 = new Fork(4);
        Fork fork5 = new Fork(5);

        Philosopher philosopher1 = new Philosopher("P1", fork1, fork2);
        Philosopher philosopher2 = new Philosopher("P2", fork2, fork3);
        Philosopher philosopher3 = new Philosopher("P3", fork3, fork4);
        Philosopher philosopher4 = new Philosopher("P4", fork4, fork5);
        Philosopher philosopher5 = new Philosopher("P5", fork5, fork1);
        new Thread(() -> {
            while (true) {
                System.out.println(philosopher1);
                System.out.println(philosopher2);
                System.out.println(philosopher3);
                System.out.println(philosopher4);
                System.out.println(philosopher5);
                Utils.sleep(10000);
            }
        }).start();
    }
}