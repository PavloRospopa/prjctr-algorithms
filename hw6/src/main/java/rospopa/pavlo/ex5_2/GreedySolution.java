package rospopa.pavlo.ex5_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class GreedySolution {
    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var n = Integer.parseInt(reader.readLine());
        var myUnits = createMaxHeapOfUnits(reader.readLine().split(" "));
        var opponentUnits = createMaxHeapOfUnits(reader.readLine().split(" "));

        var myVictoriousUnits = new ArrayList<Unit>();

        while (!opponentUnits.isEmpty()) {
            var opponentUnit = opponentUnits.remove();

            if (myUnits.element().strength > opponentUnit.strength) {
                myVictoriousUnits.add(myUnits.remove());
            }
        }

        System.out.println(myVictoriousUnits.stream().mapToInt(Unit::getStrength).sum());
    }

    static PriorityQueue<Unit> createMaxHeapOfUnits(String[] units) {
        var heap = new PriorityQueue<>(Comparator.comparingInt(Unit::getStrength).reversed());
        for (int i = 0; i < units.length; i++) {
            heap.add(new Unit(i, Integer.parseInt(units[i])));
        }
        return heap;
    }

    static class Unit {
        final int id;
        final int strength;

        public Unit(int id, int strength) {
            this.id = id;
            this.strength = strength;
        }

        public int getId() {
            return id;
        }

        public int getStrength() {
            return strength;
        }
    }
}
