import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

//Helper class that contains all methods needed to perform test tasks
public final class TestTaskHelper {
    private Scanner scanner;

    public TestTaskHelper() {
        this.scanner = new Scanner(System.in);
    }

    //Method that finds number of correct bracket expressions
    public void findBracketNumber() {
        System.out.print("N = ");
        int n = Integer.parseInt(scanner.nextLine());

        if (n < 0 ) {
            throw new IllegalArgumentException("N should not be negative");
        }

        //To find number of correct bracket expressions we use the Catalan number formula
        int bracketNumber = findFactorial(2 * n).intValue() /
                (findFactorial(n + 1).intValue() * findFactorial(n).intValue());

        System.out.println("Number of correct bracket expressions: " + bracketNumber);
    }

    //Method that finds the minimum transportation cost between two cities
    public void findMinimumCostPath() {
        int testCases = Integer.parseInt(scanner.nextLine());

        while (testCases > 0) {
            int cityNumber = Integer.parseInt(scanner.nextLine());
            Map<String, Integer> cityToIndex = new HashMap<>();
            Map<Integer, List<Edge>> graph = new HashMap<>();

            // Process each city
            for (int i = 1; i <= cityNumber; i++) {
                String cityName = scanner.nextLine(); // Read the city name

                //Check if the city name is correct according to the condition
                if (!cityName.matches("[a-zA-Z]{1,10}")) {
                    throw new IllegalArgumentException("name of the city should contain only letters [a - z] " +
                            "and its length should not be greater than 10");
                }

                // Map the city name to an index
                cityToIndex.put(cityName, i);
                int neighbors = Integer.parseInt(scanner.nextLine());
                graph.putIfAbsent(i, new ArrayList<>());

                // Read each neighbor and its transportation cost
                for (int j = 0; j < neighbors; j++) {
                    String[] edgeData = scanner.nextLine().split(" ");
                    int neighbor = Integer.parseInt(edgeData[0]);
                    int cost = Integer.parseInt(edgeData[1]);
                    graph.get(i).add(new Edge(neighbor, cost));
                }
            }

            // Number of paths to find
            int queries = Integer.parseInt(scanner.nextLine());
            //Loop to find the shortest path according to city indexes
            for (int i = 0; i < queries; i++) {
                String[] query = scanner.nextLine().split(" ");
                String source = query[0];
                String destination = query[1];

                int start = cityToIndex.get(source);
                int end = cityToIndex.get(destination);

                int minCost = calculateMinimumCost(graph, start, end, cityNumber);
                System.out.println(minCost);
            }

            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            testCases--;
        }
    }

    //Method that finds sum of digits in number
    public void findDigitSum() {
        BigInteger factValue = findFactorial(100);
        String factStringValue = factValue.toString();
        int digitSum = 0;

        //Loop to convert each next char to int and add it to digit sum variable
        for (int i = 0; i < factStringValue.length(); i++) {
            int digit = factStringValue.charAt(i) - '0';
            digitSum += digit;
        }

        System.out.println(digitSum);
    }

    //Method that finds factorial of some number
    private BigInteger findFactorial(int number) {
        BigInteger result = BigInteger.ONE;

        for (int i = 1; i <= number; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }

        return result;
    }

    //Method that finds the shortest path using Dijkstra's algorithm
    private int calculateMinimumCost(Map<Integer, List<Edge>> graph, int start, int end, int n) {
        // Array to store the shortest distances to each node
        int[] distances = new int[n + 1];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        // Priority queue to process nodes by their current minimum distance
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        queue.offer(new int[]{0, start}); // Add the start node with distance 0

        //Loop to calculate the shortest distance to the destination
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int currentCost = current[0];
            int currentNode = current[1];

            if (currentCost > distances[currentNode]) {
                continue;
            }

            //Check if the cost of the path is correct according to the condition
            if (currentCost > 200000) {
                throw new IllegalArgumentException("The cost of the path should not be greater than 200000");
            }

            // Update distances for all neighbors
            for (Edge edge : graph.getOrDefault(currentNode, new ArrayList<>())) {
                int newCost = currentCost + edge.getCost();
                if (newCost < distances[edge.getTo()]) {
                    distances[edge.getTo()] = newCost;
                    queue.offer(new int[]{newCost, edge.getTo()});
                }
            }
        }

        return distances[end];
    }
}
