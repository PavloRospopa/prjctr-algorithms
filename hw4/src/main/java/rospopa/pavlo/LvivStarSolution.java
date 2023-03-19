package rospopa.pavlo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LvivStarSolution {

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var stationsCount = Integer.parseInt(reader.readLine());
        var clientsPerStation = toIntArr(reader.readLine().split(" "));
        var operationsCount = Integer.parseInt(reader.readLine());

        var clientCounter = new ClientCounter(clientsPerStation);

        for (int i = 0; i < operationsCount; i++) {
            var input = reader.readLine().split(" ");
            var operator = input[0];
            switch (operator) {
                case "ENTER":
                    clientCounter.increment(Integer.parseInt(input[1]));
                    break;
                case "LEAVE":
                    clientCounter.decrement(Integer.parseInt(input[1]));
                    break;
                case "COUNT":
                    System.out.println(clientCounter.count(Integer.parseInt(input[1]), Integer.parseInt(input[2])));
                    break;
            }
        }
    }

    static int[] toIntArr(String[] arr) {
        var intArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            intArr[i] = Integer.parseInt(arr[i]);
        }
        return intArr;
    }

    static class ClientCounter {
        int[] clientsPerStation;
        int clusterSize;
        int[] precalculatedCounts;

        ClientCounter(int[] clientsPerStation) {
            this.clientsPerStation = clientsPerStation;

            var clusterSize = (int) Math.round(Math.sqrt(clientsPerStation.length));
            this.clusterSize = clusterSize;

            var numberOfClusters = clientsPerStation.length % clusterSize > 0 ? clusterSize + 1 : clusterSize;
            var precalculatedCounts = new int[numberOfClusters];
            for (int i = 0; i < clientsPerStation.length; i++) {
                precalculatedCounts[clusterOfStation(i)] += clientsPerStation[i];
            }

            this.precalculatedCounts = precalculatedCounts;
        }

        void increment(int stationId) {
            var i = stationId - 1;
            clientsPerStation[i]++;
            precalculatedCounts[clusterOfStation(i)]++;
        }

        void decrement(int stationId) {
            var i = stationId - 1;
            clientsPerStation[i]--;
            precalculatedCounts[clusterOfStation(i)]--;
        }

        long count(int fromStationId, int toStationId) {
            var l = fromStationId - 1;
            var r = toStationId - 1;
            if (l == r) {
                return clientsPerStation[l];
            } else if (clusterOfStation(l) == clusterOfStation(r)) {
                return countClientsOfStations(l, r);
            }

            var result = 0L;

            var leftmostCluster = clusterOfStation(l);
            if (l % clusterSize > 0) {
                result += countClientsOfStations(l, lastStationInCluster(leftmostCluster));
                leftmostCluster++;
            }

            var rightmostCluster = clusterOfStation(r);
            if ((r + 1) % clusterSize > 0) {
                result += countClientsOfStations(firstStationInCluster(rightmostCluster), r);
                rightmostCluster--;
            }

            for (int i = leftmostCluster; i <= rightmostCluster; i++) {
                result += precalculatedCounts[i];
            }

            return result;
        }

        private int clusterOfStation(int s) {
            return s / clusterSize;
        }

        private int firstStationInCluster(int c) {
            return c * clusterSize;
        }

        private int lastStationInCluster(int c) {
            return firstStationInCluster(c + 1) - 1;
        }

        private long countClientsOfStations(int l, int r) {
            var result = 0;
            for (int i = l; i <= r; i++) {
                result += clientsPerStation[i];
            }
            return result;
        }
    }
}
