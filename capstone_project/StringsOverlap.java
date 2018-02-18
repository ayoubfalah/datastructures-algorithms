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
        int maxOverlapLength = computeLengthOfMaxOverlap(a, b);
        int threshold = 3;
        System.out.println(effectiveOverlapLength(maxOverlapLength, threshold));
    }
    
    /**
     * 
     * @param a a string
     * @param b a string
     * @return the length of the maximum overlap between a and b
     */
    private static int computeLengthOfMaxOverlap(String a, String b)
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
     * @param lengthOfMaxOverlap a naural number that represents the length of
     *                           the maximum overlap of two strings.
     * @param threshold a natural number that will be used as a threshold value
     * @return lengthOfMaxOverlap if (lengthOfMaxOverlap >= threshold)
     *          and zero otherwise.
     */
    private static int effectiveOverlapLength(int lengthOfMaxOverlap, int threshold)
    {
        return lengthOfMaxOverlap >= threshold? lengthOfMaxOverlap : 0;
    }

}
