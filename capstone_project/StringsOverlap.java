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
        System.out.println(computeLengthOfMaxOverlap(a, b));
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

}
