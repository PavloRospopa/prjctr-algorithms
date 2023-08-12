package rospopa.pavlo.ex3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

class Twitter {
    private final Map<Integer, Set<Integer>> followersOfUser = new HashMap<>();
    private final Map<Integer, LinkedList<Tweet>> latestTweetsOfUser = new HashMap<>();
    private final Map<Integer, PriorityQueue<Tweet>> feedOfUser = new HashMap<>();
    private int tweetCounter;

    public Twitter() {
    }

    public void postTweet(int userId, int tweetId) {
        var tweet = new Tweet(userId, tweetId, ++tweetCounter);
        var latestTweets = getLatestTweetsOfUser(userId);
        addTweet(latestTweets, tweet);

        getFollowersOfUser(userId).forEach(follower -> getFeedOfUser(follower).add(tweet));
    }

    public List<Integer> getNewsFeed(int userId) {
        var userFeed = getFeedOfUser(userId);
        var newsFeed = new ArrayList<Tweet>(10);

        while (newsFeed.size() != 10 && !userFeed.isEmpty()) {
            var tweet = userFeed.remove();
            newsFeed.add(tweet);
        }

        userFeed.addAll(newsFeed);

        return newsFeed.stream().map(Tweet::tweetId).toList();
    }

    public void follow(int followerId, int followeeId) {
        var followers = getFollowersOfUser(followeeId);
        if (followers.contains(followerId) || followerId == followeeId) {
            return;
        }

        followers.add(followerId);

        var latestTweets = getLatestTweetsOfUser(followeeId);
        getFeedOfUser(followerId).addAll(latestTweets);
    }

    public void unfollow(int followerId, int followeeId) {
        var followers = getFollowersOfUser(followeeId);
        if (!followers.contains(followerId) || followerId == followeeId) {
            return;
        }

        followers.remove(followerId);

        var followerFeed = getFeedOfUser(followerId);
        var newsFeed = new ArrayList<Tweet>(10);

        while (newsFeed.size() != 10 && !followerFeed.isEmpty()) {
            var tweet = followerFeed.remove();
            if (tweet.userId != followeeId) {
                newsFeed.add(tweet);
            }
        }

        followerFeed.addAll(newsFeed);
    }

    private LinkedList<Tweet> getLatestTweetsOfUser(int userId) {
        return latestTweetsOfUser.computeIfAbsent(userId, $ -> new LinkedList<>());
    }

    private void addTweet(LinkedList<Tweet> list, Tweet tweet) {
        if (list.size() < 10) {
            list.addLast(tweet);
        } else {
            list.removeFirst();
            list.addLast(tweet);
        }
    }

    private PriorityQueue<Tweet> getFeedOfUser(int userId) {
        return feedOfUser.computeIfAbsent(userId, $ -> new PriorityQueue<>(Comparator.comparing(Tweet::tweetNum).reversed()));
    }

    private Set<Integer> getFollowersOfUser(int userId) {
        return followersOfUser.computeIfAbsent(userId, self -> {
            var followers = new HashSet<Integer>();
            followers.add(self);
            return followers;
        });
    }

    record Tweet(int userId, int tweetId, int tweetNum) {
    }
}
