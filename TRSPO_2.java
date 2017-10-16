import java.util.Scanner;

class ThreadOne implements Runnable {
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("������� ��� �����: ");
        System.out.println("����� ����� = "+(sc.nextInt() + sc.nextInt()));
        sc.close();
    }
}

class ThreadTwo implements Runnable {
    public void run() {
        int time = 1;
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ie) {
                System.out.println("The thread has been interrupted");
            }
            if(time % 5 != 0) {
                System.out.println("� �������...");
            } else {
                System.out.println("����� �������!");
            }
            time++;
        }
    }
}

public class TRSPO_2 {
    public static void main(String[] args) {
        ThreadOne one = new ThreadOne();
        ThreadTwo two = new ThreadTwo();
        Thread first = new Thread(one);
        Thread second = new Thread(two);
        first.start();
        second.start();
    }
}