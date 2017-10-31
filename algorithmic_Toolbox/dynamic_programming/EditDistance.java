import java.util.*;

class EditDistance {
  
  /**
  * @param s a string
  * @param t a string
  * @precondition 1 <= |s| <= 100 and 1 <= |t| <= 100
  * return the Edit Distance between s and t. That is, the minimum number of
  *        inserations, deletion, and mismatches in an alignment of s and t. 
  */
  public static int editDistance(String s, String t) 
  {
      int n = s.length() + 1;
      int m = t.length() + 1;
      int[][] D = new int[n][m];
      
      for (int j = 0; j < m; j++) D[0][j] = j;          
      for (int i = 0; i < n; i++) D[i][0] = i;          

      for (int j = 1; j < m; j++) {
          for (int i = 1; i < n; i++) {
              int insertion = D[i][j - 1]  + 1;
              int deletion = D[i - 1][j]  + 1;
              int match = D[i - 1][j - 1];
              int mismatch = D[i - 1][j - 1]  + 1;

              if (s.charAt(i - 1) == t.charAt(j - 1))
              {
                  List<Integer> list = Arrays.asList(
                          new Integer[]{insertion, deletion, match});
                  D[i][j] = Collections.min(list);                  
              }else{
                  List<Integer> list = Arrays.asList(
                          new Integer[]{insertion, deletion, mismatch});
                  D[i][j] = Collections.min(list);
              }             
          }          
      }
    return D[n-1][m-1];
  }
  public static void main(String args[]) {
    Scanner scan = new Scanner(System.in);

    String s = scan.next();
    String t = scan.next();

    System.out.println(editDistance(s, t));
  }

}
