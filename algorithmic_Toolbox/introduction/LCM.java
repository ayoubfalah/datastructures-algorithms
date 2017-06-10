import java.util.*;

public class LCM {
  /**
   * @param a a positive integer
   * @param b a positive integer
   * @preconstraint 1 <= a <= 2*10^9
   *                1 <= b <= 2*10^9
   * @return computes the least common multiple of a and b
   */
  private static long lcm(int a, int b)
  {
      return ((long)a * b)/ gcd(a, b);
  }
  
  /**
   * @param a a positive integer
   * @param b a positive integer
   * @preconstraint 1 <= a <= 2*10^9
   *                1 <= b <= 2*10^9
   * @return computes the greatest common divisor of a and b
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

    System.out.println(lcm(a, b));
  }
}
