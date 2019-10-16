package runner;

import logic.Expression;

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) throws InterruptedException {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input:");
            String result = new Expression(scanner.nextLine()).calculate();
            System.out.println("Output:\n" + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Thread.sleep(5000);
            System.exit(0);
        }
    }
}
