package rospopa.pavlo.ex4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Solution {

    /**
     * Solution for <a href="https://codeforces.com/problemset/problem/580/B?locale=en">B. Kefa and Company problem</a>
     */
    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));

        var input = reader.readLine().split(" ");
        var n = Integer.parseInt(input[0]);
        var d = Integer.parseInt(input[1]);

        var friends = new Friend[n];
        for (int i = 0; i < n; i++) {
            input = reader.readLine().split(" ");
            friends[i] = new Friend(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
        }
        Arrays.sort(friends, Comparator.comparingInt(Friend::money));

        var l = 0;
        var poorestFriend = friends[l];
        var cumulativeFriendshipFactor = 0L;
        var maxCumulativeFriendshipFactor = 0L;
        for (int r = 0; r < n; r++) {
            var friend = friends[r];
            cumulativeFriendshipFactor += friend.friendshipFactor;

            while (friend.money - poorestFriend.money >= d) {
                cumulativeFriendshipFactor -= poorestFriend.friendshipFactor;
                l++;
                poorestFriend = friends[l];
            }

            if (cumulativeFriendshipFactor > maxCumulativeFriendshipFactor) {
                maxCumulativeFriendshipFactor = cumulativeFriendshipFactor;
            }
        }

        System.out.println(maxCumulativeFriendshipFactor);
    }

    record Friend(int money, int friendshipFactor) {
    }
}
