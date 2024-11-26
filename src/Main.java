public class Main {
    public static void main(String[] args) {
        TestTaskHelper testTaskHelper = new TestTaskHelper();

        System.out.println("Task 1");
        testTaskHelper.findBracketNumber();

        System.out.println("\nTask 2");
        testTaskHelper.findMinimumCostPath();

        System.out.println("\nTask 3");
        testTaskHelper.findDigitSum();
    }
}