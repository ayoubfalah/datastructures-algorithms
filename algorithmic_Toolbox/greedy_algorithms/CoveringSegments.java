import java.util.*;

public class CoveringSegments {

    /**
     *
     * @param segments a set of segments in the plane
     * @preconstraint 1 <= |segments| <= 100
     * @preconstraint for any segment [a, b]: 0 <= a <= b <= 10^9
     * @
     * return the minimum set of points P such that for any segment [a, b] there
     * is a point p in P such that a <= p <= b
     */
    private static Integer[] optimalPoints(Segment[] segments) {
        Arrays.sort(segments);

        ArrayList<Integer> points = new ArrayList();
        int currentSegmentIndex = 0;
        int SEGMENTS_LENGTH = segments.length;

        while (currentSegmentIndex < SEGMENTS_LENGTH) {
            Segment currentSegment = segments[currentSegmentIndex];
            int currentSegmentRightEndPoint = currentSegment.end;
            points.add(currentSegmentRightEndPoint);

            int successorSegmentIndex = currentSegmentIndex + 1;

            while ((successorSegmentIndex < SEGMENTS_LENGTH)
                    && ((segments[successorSegmentIndex].start
                    <= currentSegmentRightEndPoint)
                    && (currentSegmentRightEndPoint
                    <= segments[successorSegmentIndex].end))) {
                successorSegmentIndex++;
            }
            currentSegmentIndex = successorSegmentIndex;
        }
        Integer[] pointsArray = points.toArray(new Integer[points.size()]);
        return pointsArray;
    }

    private static class Segment implements Comparable<Segment> {

        int start, end;

        Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Segment that) {
            return Integer.compare(this.end, that.end);
        }

        @Override
        public String toString() {
            return "[" + this.start + ", " + this.end + "]";
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        for (int i = 0; i < n; i++) {
            int start, end;
            start = scanner.nextInt();
            end = scanner.nextInt();
            segments[i] = new Segment(start, end);
        }

        Integer[] points = optimalPoints(segments);
        System.out.println(points.length);
        for (int point : points) {
            System.out.print(point + " ");
        }
    }
}
