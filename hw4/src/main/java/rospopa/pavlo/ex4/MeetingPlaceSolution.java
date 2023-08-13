package rospopa.pavlo.ex4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

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
        var ranges = new RangeBorder[friends.length * 2];
        for (int i = 0, j = 0; i < friends.length; i++, j += 2) {
            var friend = friends[i];
            var southRangeBorder = new RangeBorder(Math.max(friend.x - friend.v * t, 1), -1);
            var northRangeBorder = new RangeBorder(Math.min(friend.x + friend.v * t, UPPER_BOUND), 1);
            ranges[j] = southRangeBorder;
            ranges[j + 1] = northRangeBorder;
        }
        Arrays.sort(ranges, Comparator.comparing(RangeBorder::x).thenComparing(RangeBorder::type));

        var friendsWhoMet = 0;
        for (var i = 0; i < ranges.length; i++) {
            var rangeBorder = ranges[i];
            if (rangeBorder.type == -1) {
                friendsWhoMet++;
                if (friendsWhoMet == friends.length) {
                    return true;
                }
            } else if (rangeBorder.type == 1) {
                return false;
            }
        }

        return false;
    }

    record Friend(int x, int v) {
    }

    // type = -1 - south border
    // type = 1 - north border
    record RangeBorder(double x, int type) {
    }
}
