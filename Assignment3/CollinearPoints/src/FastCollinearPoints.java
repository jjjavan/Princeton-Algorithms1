import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Merge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;


public class FastCollinearPoints
{
    private LineSegment[] ray;
    private int n;

    public FastCollinearPoints(Point[] points)
    {
        if (points == null)
            throw new NullPointerException("Argument is null.");
        for (Point p: points)
            if (p == null)
                throw new NullPointerException("Point is null.");

        Point[] ptsCopy = new Point[points.length];
        //Shallow copy is fine since Point only contains immutable, primitive types.
        ptsCopy = points.clone();
        ArrayList<LineSegment> segs = new ArrayList<>();
        Arrays.sort(ptsCopy,ptsCopy[0].slopeOrder());
        for (int i = 0; i < ptsCopy.length-1; ++i)
            if (ptsCopy[i].slopeTo(ptsCopy[i+1]) == Double.NEGATIVE_INFINITY)
                throw new IllegalArgumentException("Degenerate points found.");
        //How it works: Every iteration sorts then finds all slopes between p0 and [p1...pn]
        //if points are collinear a line is instantiated.
        //then initial point is removed and origin moves to next point unitl complete.
        double epsilon = .0000001;//used to compare difference in slope in inner for loop below
        for (int i = 0; i < ptsCopy.length - 1; i++)
        {

            Point origin = ptsCopy[i];

            Point[] ptsArr = new Point[ptsCopy.length - (i+1)];

            for (int minIndex = 0, j = i+1; j < ptsCopy.length; ++minIndex, ++j)
                ptsArr[minIndex] = ptsCopy[j];
            //Optimized mergesort uses insertion sort for array lengths less than CUTOFF
            MergeX.sort(ptsArr,origin.SLOPE_ORDER);

            for (int k = 0; k < ptsArr.length - 1;++k)
            {
               double slope1 = origin.slopeTo(ptsArr[k]);
               Point end = null;
               int count = 1;
               boolean stop = false;
                for (int l = k+1; l < ptsArr.length && !stop; l++)
                {
                    double slope2 = origin.slopeTo(ptsArr[l]);
                    if (slope1 == slope2 || Math.abs(slope2 - slope1) < epsilon)
                    {
                        ++count;
                        if (count >= 3)
                        {
                            end = ptsArr[l];
                            k = l;
                        }

                    }
                    else
                        stop = true;

                }//for
                if (end != null)
                    segs.add(new LineSegment(origin,end));
            }//for

        }//for
        n = segs.size();
        ray = segs.toArray(new LineSegment[n]);
    }


    public int numberOfSegments() { return n; }

    public LineSegment[] segments()
    {
        LineSegment[] tmp = new LineSegment[n];

        for (int i = 0; i < n ; i++)
            tmp[i] = ray[i];

        return tmp;
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        // read the n points from a file
        File file = new File(args[0]);
        Scanner in = new Scanner(file);
        int n = in.nextInt();
        Point[] points = new Point[n];
        int i = 0;
        while(in.hasNext())
        {
            int x = in.nextInt();
            int y = in.nextInt();
            points[i] = new Point(x, y);
            ++i;
        }


        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();// print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

        in.close();
    }
}

/*
slopes = new Double[p.length - 1];

        for (int i = 0; i < p.length; i++) {
        for (int j = 0, k = i + 1; k < p.length; ++j, ++k) {
        slopes[j] = p[i].slopeTo(p[k]);
        }//for
        order = Merge.indexSort(slopes);
        int k = 0, l = k + 1, start = k, end;
        boolean found = false;
        while (l < order.length && !found) {
        if (p.) {
        start = order[k];//if S = set of slopes then k is inclusive
        found = true;

        }
        ++k;
        ++l;
        }//while

        if (found) {
        found = false;
        k = start;
        l = start + 1;
        end = l;
        int count = 1;
        while (l < order.length && !found) {
        if (slopes[order[k]] != slopes[order[l]]) {
        end = order[l];//exclusive - S = [start,end)
        found = true;
        }
        ++l;
        ++count;
        }//while
        if (count >= 4)
        ray[n++] = new LineSegment(p[i], p[order[end]]);
        }
        }//for*/
/*
n = 0;
        ray = new LineSegment[p.length];
        Point base, start = null, end = null;
        int count = 3;
        boolean found = false;
        int next = 3;
        for (int i = 0; i  < p.length - 3 && !found; ++i) {
        for (int j = i + 1; j < p.length && !found ; ++j) {
        base = p[i];
        Arrays.sort(p,0,p.length,base.slopeOrder());
        while(next < p.length && base.slopeTo(p[j]) == base.slopeTo(p[next++]))
        {
        ++count;
        found = true;
        }
        next = 3;//reset for next iteration
        }//for

        if (count >= 4) {
        start = p[i]; end = p[count - 1];//subtract 1 from count because of 0 indexing
        ray[n++] = new LineSegment(start, end);
        }

        }//
*/
