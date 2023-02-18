package rospopa.pavlo;

import java.util.Scanner;

public class Main {
    /**
     * This program:
     * 1. Reads number of points n (2 < n < 101).
     * 2. Reads n points defined as x y (0 <= x <= 1000, 0 <= y <= 1000).
     * 3. Finds all tuples consisting of 3 points that can form a triangle.
     * 4. Calculates perimeter of triangles and finds the largest triangle.
     * 5. Prints result into stdout.
     * Algorithm analysis: program has time complexity O(n^3); n max = 100 thus the program executes magnitude of 10^6 operations < 10^9.
     */
    public static void main(String[] args) {
        var in = new Scanner(System.in);
        var n = in.nextInt();
        var points = new Point[n];

        for (int i = 0; i < n; i++) {
            var x = in.nextInt();
            var y = in.nextInt();
            var point = new Point(x, y);
            points[i] = point;
        }

        Triangle maxTriangle = null;
        double maxTrianglePerimeter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    var pi = points[i];
                    var pj = points[j];
                    var pk = points[k];
                    if (Triangle.canFormValidTriangle(pi, pj, pk)) {
                        var triangle = new Triangle(pi, pj, pk);
                        var perimeter = triangle.perimeter();
                        if (perimeter > maxTrianglePerimeter) {
                            maxTriangle = triangle;
                            maxTrianglePerimeter = perimeter;
                        }
                    }
                }
            }
        }

        var roundedTo16SigFigs = String.format("%.16g", maxTrianglePerimeter);
        var first15SigFigs = roundedTo16SigFigs.substring(0, roundedTo16SigFigs.length() - 1);

        System.out.printf("%s has max perimeter equal %s", maxTriangle, first15SigFigs);
    }

    record Point(int x, int y) {

        double distanceTo(Point other) {
            return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
        }
    }

    record Triangle(Point p1, Point p2, Point p3) {

        static boolean canFormValidTriangle(Point p1, Point p2, Point p3) {
            return (p1.x != p2.x || p2.x != p3.x) && (p1.y != p2.y || p2.y != p3.y);
        }

        double perimeter() {
            var a = p1.distanceTo(p2);
            var b = p2.distanceTo(p3);
            var c = p3.distanceTo(p1);
            return a + b + c;
        }
    }
}
