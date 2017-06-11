
import java.util.Arrays;
import java.util.Scanner;

public class FractionalKnapsack {

    static class Item implements Comparable<Item> {

        public double value;
        public double weight;
        public double valuePerWeight;

        public Item(double value, double weight) {
            this.value = value;
            this.weight = weight;
            this.valuePerWeight = this.value / this.weight;
        }

        @Override
        public int compareTo(Item that) {
            return Double.compare(this.valuePerWeight, that.valuePerWeight);
        }
    }

    /**
     *
     * @param capacity The capacity of a knapsack.
     * @param values The values of the items that will be taken in the knapsack
     * @param weights The weights of the items that will be taken in the knapsack
     * @return  The maximal value of fractions of items that fit into the knapsack
     */
    private static double getOptimalValue(final int capacity, final int[] values,
            final int[] weights) {
        int numberOfItems = weights.length;
        Item[] items = new Item[numberOfItems];
        for (int i = 0; i < numberOfItems; i++) {
            items[i] = new Item(values[i], weights[i]);
        }
        Arrays.sort(items);

        // Initialy the whole capacity is available.
        int availableCapacity = capacity;
        double optimalValue = 0;
        for (int i = numberOfItems - 1; i >= 0; i--) {
            
            if (availableCapacity == 0) { return optimalValue; }

            double a = Math.min(items[i].weight, availableCapacity);
            optimalValue += (a * items[i].valuePerWeight);

            items[i].weight -= a;
            availableCapacity -= a;
        }

        return optimalValue;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int capacity = scanner.nextInt();
        int[] values = new int[n];
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = scanner.nextInt();
            weights[i] = scanner.nextInt();
        }
        double optimalValue = getOptimalValue(capacity, values, weights);
        System.out.printf("%.4f\n", optimalValue);
    }
}
