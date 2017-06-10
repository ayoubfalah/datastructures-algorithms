import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Change {
    
    // The coins are sorted in descending order.
    private static final int[] COINS = {10, 5, 1};

    /**
     *
     * @param amount a natural number
     * @preconstraint 1 <= amount < 10^3
     * @return the coins needed to change amount into coins with denominations
     * 1, 5 and 10.
     */
    private static List<Integer> change(final int amount) {

        ArrayList<Integer> change = new ArrayList();
        int changeSum = 0;

        while (changeSum < amount) {
            
            int coinIndex = findLargestCoinIndex(changeSum, amount);

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

    /**
     *
     * @param changeSum a natural number
     * @param amount a natural number
     * @preconstraint 0 <= changeSum <= amount
     * @preconstraint 1 <= amount < 10^3
     * @return The index of largest coin in COINS
     * @postconstraint changeSum + COINS[index] <= amount
     */
    private static int findLargestCoinIndex(int changeSum, final int amount) {
        int initialCoinIndex = -1;
        int coinIndex = initialCoinIndex;
        for (int i = 0; i < COINS.length; i++) {
            if (changeSum + COINS[i] <= amount) {
                coinIndex = i;
                break;
            }
        }
        return coinIndex;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> change = change(n);
        System.out.println(change.size());

    }
}
