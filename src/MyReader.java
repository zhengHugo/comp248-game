import java.util.Scanner;

// MyReader class is a Scanner asking for corrrect input
class MyReader {
    private Scanner scanner;

    MyReader() {
        scanner = new Scanner(System.in);
    }

    int nextInt(Checker checker) {
        int a = scanner.nextInt();
        while (!checker.check(a)) {
            System.out.println("Sorry but " + a + " is not a legal choice. ");
            a = scanner.nextInt();
        }
        return a;
    }

    String next() {
        return scanner.next();
    }

    interface Checker {
        boolean check(Object o);
    }


}
