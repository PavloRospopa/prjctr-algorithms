package rospopa.pavlo.ex4;

import java.util.ArrayDeque;
import java.util.Queue;

public class RecentCounter {
    private final int timeWindowInMillis;
    private final Queue<Integer> queue;

    public static void main(String[] args) {
        RecentCounter recentCounter = new RecentCounter(3000);
        System.out.println(recentCounter.ping(1));     // requests = [1], range is [-2999,1], return 1
        System.out.println(recentCounter.ping(100));   // requests = [1, 100], range is [-2900,100], return 2
        System.out.println(recentCounter.ping(3001));  // requests = [1, 100, 3001], range is [1,3001], return 3
        System.out.println(recentCounter.ping(3002));  // requests = [1, 100, 3001, 3002], range is [2,3002], return 3
    }

    public RecentCounter(int timeWindowInMillis) {
        this.timeWindowInMillis = timeWindowInMillis;
        queue = new ArrayDeque<>(timeWindowInMillis + 2);
    }

    public int ping(int t) {
        queue.add(t);
        while (queue.element() < t - timeWindowInMillis) {
            queue.remove();
        }
        return queue.size();
    }
}
