import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Change {

    /**
     *
     * @param amount a natural number
     * @preconstraint 1 <= amount < 10^3
     * @return the coins needed to change amount into coins with denominations
     * 1, 5 and 10.
     */
    private static List<Integer> change(final int amount) {

        // The coins sorted in descending order.
        final int[] COINS = {10, 5, 1};

        ArrayList<Integer> change = new ArrayList();
        int changeSum = 0;

        while (changeSum < amount) {
            int initialCoinIndex = -1;
            int coinIndex = initialCoinIndex;
            for (int i = 0; i < COINS.length; i++) {
                if (changeSum + COINS[i] <= amount) {
                    coinIndex = i;
                    break;
                }
            }

            boolean amountIsUnchangeable = (coinIndex == -1);
            if (amountIsUnchangeable) {
                break;
            } else {
                change.add(COINS[coinIndex]);
                changeSum += COINS[coinIndex];
            }
        }
        return change;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> change = change(n);
        System.out.println(change.size());

    }
}
