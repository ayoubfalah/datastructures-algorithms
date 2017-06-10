import java.util.*;

public class GCD {
  private static int gcd_naive(int a, int b) {
    int current_gcd = 1;
    for(int d = 2; d <= a && d <= b; ++d) {
      if (a % d == 0 && b % d == 0) {
        if (d > current_gcd) {
          current_gcd = d;
        }
      }
    }

    return current_gcd;
  }
  
  /**
   * 
   * @param a natural number
   * @param b natural number
   * @preconstraint 1 <= a, b <= 2e10
   * @return find the greatest common divisor of a and b
   */
  private static int gcd(int a, int b)
  {
      if (b == 0) return a;
      return gcd(b, a % b);
  }

  public static void main(String args[]) {
    Scanner scanner = new Scanner(System.in);
    int a = scanner.nextInt();
    int b = scanner.nextInt();

    System.out.println(gcd(a, b));
  }
}
