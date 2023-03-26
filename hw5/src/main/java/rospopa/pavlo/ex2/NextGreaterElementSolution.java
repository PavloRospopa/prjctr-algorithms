package rospopa.pavlo.ex2;

import rospopa.pavlo.FixedArrayStack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class NextGreaterElementSolution {

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var nums = toIntArr(reader.readLine().split(" "));
        var solution = new NextGreaterElementSolution();
        System.out.println(Arrays.toString(solution.nextGreaterElements(nums)));
    }

    static int[] toIntArr(String[] arr) {
        var intArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            intArr[i] = Integer.parseInt(arr[i]);
        }
        return intArr;
    }

    public int[] nextGreaterElements(int[] nums) {
        var n = nums.length;
        var result = new int[n];
        var waiting = new FixedArrayStack<>(new Num[n]);

        for (int j = 0; j % n < n && j / n < 2; j++) {
            var i = j % n;
            while (waiting.top() != null) {
                var w = waiting.top();
                if (j >= n && i >= w.i) {
                    // no greater element exists for waiting num
                    waiting.pop();
                    result[w.i] = -1;
                } else if (nums[i] > w.v) {
                    // greater element has been found for waiting num
                    waiting.pop();
                    result[w.i] = nums[i];
                } else {
                    // element is not greater, skip checking waiting stack
                    break;
                }
            }
            // don't add same element to waiting stack twice
            if (j < n) {
                waiting.push(new Num(i, nums[i]));
            }
        }

        return result;
    }

    record Num(int i, int v) {
    }
}
