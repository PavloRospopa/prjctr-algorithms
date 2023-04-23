package rospopa.pavlo.ex5_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

public class Solution {
    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var n = Integer.parseInt(reader.readLine());
        var myUnits = toIntArr(reader.readLine().split(" "));
        var opponentsUnits = toIntArr(reader.readLine().split(" "));

        var frontline = new Frontline(n, myUnits, opponentsUnits);
        gen(0, emptyList(), frontline);

        System.out.println(frontline.bestOutcomeOfBattle);
    }

    static void gen(int opponentsUnitToDeploy, List<UnitPair> combatLineup, Frontline frontline) {
        if (opponentsUnitToDeploy == frontline.battleSize) {
            System.out.println(combatLineup.toString());
            var outcome = frontline.calculateOutcomeOfBattle(combatLineup);
            if (outcome > frontline.bestOutcomeOfBattle) {
                frontline.bestOutcomeOfBattle = outcome;
            }
            return;
        }

        for (var myUnit = 0; myUnit < frontline.myUnits.length; myUnit++) {
            if (frontline.canDeployUnits(myUnit, opponentsUnitToDeploy)) {
                frontline.deployUnits(myUnit, opponentsUnitToDeploy);
                var updatedCombatLineup = new ArrayList<>(combatLineup);
                updatedCombatLineup.add(new UnitPair(myUnit, opponentsUnitToDeploy));
                gen(opponentsUnitToDeploy + 1, updatedCombatLineup, frontline);
                frontline.concealUnits(myUnit, opponentsUnitToDeploy);
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

    static class Frontline {
        private final int battleSize;
        private final int[] myUnits;
        private final int[] opponentsUnits;
        private final boolean[] myUnitsDeployed;
        private final boolean[] opponentsUnitsDeployed;
        private int bestOutcomeOfBattle;

        public Frontline(int n, int[] myUnits, int[] opponentsUnits) {
            battleSize = n;
            this.myUnits = myUnits;
            this.opponentsUnits = opponentsUnits;
            myUnitsDeployed = new boolean[n];
            opponentsUnitsDeployed = new boolean[n];
        }

        boolean canDeployUnits(int mine, int opponents) {
            return !myUnitsDeployed[mine] && !opponentsUnitsDeployed[opponents];
        }

        void deployUnits(int mine, int opponents) {
            myUnitsDeployed[mine] = true;
            opponentsUnitsDeployed[opponents] = true;
        }

        void concealUnits(int mine, int opponents) {
            myUnitsDeployed[mine] = false;
            opponentsUnitsDeployed[opponents] = false;
        }

        int calculateOutcomeOfBattle(List<UnitPair> combatLineup) {
            var strength = 0;
            for (UnitPair pair : combatLineup) {
                var myUnit = myUnits[pair.mine];
                if (myUnit > opponentsUnits[pair.opponents]) {
                    strength += myUnit;
                }
            }
            return strength;
        }
    }

    static class UnitPair {
        final int mine;
        final int opponents;

        UnitPair(int mine, int opponents) {
            this.mine = mine;
            this.opponents = opponents;
        }

        @Override
        public String toString() {
            return mine + " vs " + opponents;
        }
    }
}
