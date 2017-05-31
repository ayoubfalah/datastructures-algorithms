import java.util.*;

public class FibonacciHuge {
    
    private static long getFibonacciHuge(long n, long m)
    {
        List<Long> period = computePisanoPeriod(m);
        int periodLength = period.size();            
        n %= periodLength;            
        return period.get((int) n);
    }

    private static List<Long> computePisanoPeriod(long m) {
        List<Long> period = new ArrayList(Arrays.asList(0l, 1l));
        int i = 2;
        boolean periodFound = false;
        while (!periodFound)
        {
            Long item = (period.get(i-1) + period.get(i-2)) % m;
            period.add(item);
            boolean periodStarts = period.get(i-1) == 0 && period.get(i) == 1; 
            if (periodStarts) 
            {
                period.remove(i);
                period.remove(i - 1);
                periodFound = true;                
            }
            i++;
        }
        return period;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long m = scanner.nextLong();
        System.out.println(getFibonacciHuge(n, m));
    }
}

