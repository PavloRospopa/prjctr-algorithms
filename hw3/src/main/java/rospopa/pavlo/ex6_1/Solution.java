package rospopa.pavlo.ex6_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Solution {

    /**
     * Solution for <a href="https://www.hackerrank.com/contests/projector-algo-base-7-hw-2-123/challenges/raskin-bobbins">Raskin Bobbins problem</a>
     */
    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));

        var tripsCount = Integer.parseInt(reader.readLine());
        var trips = new Trip[tripsCount];
        for (int i = 0; i < tripsCount; i++) {
            var money = Integer.parseInt(reader.readLine());
            var flavorsCount = Integer.parseInt(reader.readLine());
            var flavorsCosts = toIntArr(reader.readLine().split(" "));
            trips[i] = new Trip(money, flavorsCosts);
        }

        for (Trip trip : trips) {
            var flavors = trip.pickBestFlavors();
            flavors.sort(Comparator.comparingInt(Flavor::getId));
            System.out.printf("%1$d %2$d", flavors.get(0).getId(), flavors.get(1).getId());
            System.out.println();
        }
    }

    static int[] toIntArr(String[] arr) {
        var intArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            intArr[i] = Integer.parseInt(arr[i]);
        }
        return intArr;
    }

    static class Trip {
        int money;
        List<Flavor> flavors;

        Trip(int money, int[] flavorsCosts) {
            this.money = money;

            flavors = new ArrayList<>(flavorsCosts.length);
            for (int i = 0; i < flavorsCosts.length; i++) {
                flavors.add(new Flavor(i + 1, flavorsCosts[i]));
            }
            flavors.sort(Comparator.comparingInt(Flavor::getCost));
        }

        List<Flavor> pickBestFlavors() {
            Flavor bf1 = null;
            Flavor bf2 = null;

            for (int i = 0; i < flavors.size(); i++) {
                var f1 = flavors.get(i);
                var wantedF2Cost = money - f1.cost;
                var f2Opt = binarySearchFirstMatching(flavors.subList(i, flavors.size()), (Flavor f) -> f.cost >= wantedF2Cost);
                if (f2Opt.filter(f -> f.cost == wantedF2Cost).isPresent()) {
                    bf1 = f1;
                    bf2 = f2Opt.get();
                    break;
                }
            }

            var result = new ArrayList<Flavor>(2);
            result.add(bf1);
            result.add(bf2);
            return result;
        }

        private Optional<Flavor> binarySearchFirstMatching(List<Flavor> flavors, Predicate<Flavor> predicate) {
            var bad = 0;
            var good = flavors.size();
            while (good - bad > 1) {
                var middle = (bad + good) / 2;
                if (predicate.test(flavors.get(middle))) {
                    good = middle;
                } else {
                    bad = middle;
                }
            }

            if (good < flavors.size() && predicate.test(flavors.get(good))) {
                return Optional.of(flavors.get(good));
            } else {
                return Optional.empty();
            }
        }
    }

    static class Flavor {
        int id;
        int cost;

        Flavor(int id, int cost) {
            this.id = id;
            this.cost = cost;
        }

        int getId() {
            return id;
        }

        int getCost() {
            return cost;
        }
    }
}
