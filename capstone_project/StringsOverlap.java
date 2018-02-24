package capstone_project;

/**
 *
 * @author ayoubfalah
 */
public class StringsOverlap 
{
    public static void main(String[] args) 
    {
        String a = "TTACGT";
        String b = "CGTACCGT";
        int longestOverlapLength = longestOverlapLength(a, b);
        int threshold = 3;
        System.out.println(effectiveOverlapLength(longestOverlapLength, threshold));
    }
    
    /**
     * 
     * @param a a string
     * @param b a string
     * @return the length of the longest overlap between a and b
     */
    private static int longestOverlapLength(String a, String b)
    {
        int result = 0;
        int n = a.length();
        for (int i = 0; i < n; i++)
        {
            if (b.startsWith(a.substring(i))) 
            {
                result = n - i;
                break;
            }            
        }
        return result;
    }
    
    /**
     * 
     * @param longestOverlapLength a naural number that represents the length of
     *                           the longest overlap of two strings.
     * @param threshold a natural number that will be used as a threshold value
     * @return longestOverlapLength if (longestOverlapLength >= threshold)
     *          and zero otherwise.
     */
    private static int effectiveOverlapLength(int longestOverlapLength, int threshold)
    {
        return longestOverlapLength >= threshold? longestOverlapLength : 0;
    }
}