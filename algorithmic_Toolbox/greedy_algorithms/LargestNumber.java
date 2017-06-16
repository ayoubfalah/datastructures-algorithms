import java.util.*;

public class LargestNumber 
{    
    /**
     * 
     * @param digits Array of natural numbers represented as strings
     * @preconstraint 1 <= |digits| <= 100
     * @preconstraint For any digit d in digit: 1 <= d <= 10^3 
     * @return The largest number that can be composed out of 
     *         digits[0], digits[1], ...,digits[n-1], 
     */
    private static String largestNumber(String[] digits)
    {
        Arrays.sort(digits, (digit1, digit2) -> 
        { return digit2.concat(digit1).compareTo(digit1.concat(digit2));});
        String delimiter = "";
        String result = String.join(delimiter, digits);
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String[] a = new String[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.next();
        }
        System.out.println(largestNumber(a));
    }
}

