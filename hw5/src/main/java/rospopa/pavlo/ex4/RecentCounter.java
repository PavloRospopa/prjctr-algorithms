package rospopa.pavlo.ex4;

import rospopa.pavlo.ex3.MyCircularDeque;

public class RecentCounter {
    private final int timeWindowInMillis;
    private final MyCircularDeque deque;

    public static void main(String[] args) {
        RecentCounter recentCounter = new RecentCounter(3000);
        System.out.println(recentCounter.ping(1));     // requests = [1], range is [-2999,1], return 1
        System.out.println(recentCounter.ping(100));   // requests = [1, 100], range is [-2900,100], return 2
        System.out.println(recentCounter.ping(3001));  // requests = [1, 100, 3001], range is [1,3001], return 3
        System.out.println(recentCounter.ping(3002));  // requests = [1, 100, 3001, 3002], range is [2,3002], return 3
    }

    public RecentCounter(int timeWindowInMillis) {
        this.timeWindowInMillis = timeWindowInMillis;
        deque = new MyCircularDeque(timeWindowInMillis + 2);
    }

    public int ping(int t) {
        deque.insertLast(t);
        while (deque.getFront() < t - timeWindowInMillis) {
            deque.deleteFront();
        }
        return deque.getLength();
    }
}
