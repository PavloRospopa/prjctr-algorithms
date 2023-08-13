package rospopa.pavlo.ex4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MeetingPlaceSolution {
    public static final double UPPER_BOUND = Math.pow(10, 9);

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var n = Integer.parseInt(reader.readLine());
        var xArr = toIntArr(reader.readLine().split(" "));
        var vArr = toIntArr(reader.readLine().split(" "));

        var friends = new Friend[n];
        for (var i = 0; i < n; i++) {
            friends[i] = new Friend(xArr[i], vArr[i]);
        }

        System.out.println(bsTimeToMeet(friends));
    }

    static int[] toIntArr(String[] arr) {
        var intArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            intArr[i] = Integer.parseInt(arr[i]);
        }
        return intArr;
    }

    static double bsTimeToMeet(Friend[] friends) {
        var good = UPPER_BOUND;
        var bad = 0.0;

        for (var i = 0; i < 50; i++) {
            var m = (good + bad) / 2;
            if (canAllMeetInPlaceInTime(m, friends)) {
                good = m;
            } else {
                bad = m;
            }
        }

        return good;
    }

    static boolean canAllMeetInPlaceInTime(double t, Friend[] friends) {
        double l = 1, r = UPPER_BOUND;
        for (int i = 0; i < friends.length; i++) {
            var f = friends[i];
            l = Math.max(l, f.x - f.v * t);
            r = Math.min(r, f.x + f.v * t);
        }
        return l <= r;
    }

    record Friend(int x, int v) {
    }
}
