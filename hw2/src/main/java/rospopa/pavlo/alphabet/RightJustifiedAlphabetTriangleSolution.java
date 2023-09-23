package rospopa.pavlo.alphabet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RightJustifiedAlphabetTriangleSolution {

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        // get input from console
        var n = Integer.parseInt(reader.readLine());
        // validate input
        if (n <= 0 || n > 26) {
            System.err.println("Wrong input!");
            return;
        }

        var triangle = calculateTriangle(n);
        printTriangle(triangle);
    }

    private static void printTriangle(RightJustifiedTriangle triangle) {
        var alphabetCharsInRow = 1;
        var startingChar = 65; // 'A' in ASCII table
        // let's track number of chars we've used to be able to compose the last triangle row
        var usedCharCount = 0;

        // iterate from 1st row to second last row and print each row into standard output
        for (var i = 1; i < triangle.rowCount; i++) {
            // calculate second section of a row - sequence of alphabet characters
            var alphabetChars = joinChars((char) startingChar, alphabetCharsInRow);
            // update used char count
            usedCharCount += alphabetCharsInRow;

            // equivalent of `row = f"{alphabet_chars:>{row_length}}"` in python
            var row = String.format("%" + triangle.rowLength + "s", alphabetChars);
            // print ready-made row
            System.out.println(row);

            // update starting character of a next row
            startingChar += alphabetCharsInRow;
            // in each subsequent row we have more alphabet chars available
            alphabetCharsInRow++;
        }

        // handle last row separately
        // `triangle.providedCharCount` is the number of chars for which we should print the right justified triangle (provided as input `n`)
        var numberOfCharsInLastRow = triangle.providedCharCount() - usedCharCount;
        var lastRow = joinChars((char) startingChar, numberOfCharsInLastRow);
        // print the last ready-made row
        System.out.println(lastRow);
    }

    private static String joinChars(char startingChar, int numberOfChars) {
        var alphabetChars = "";
        // join all but last character with a whitespace
        for (var i = 1; i < numberOfChars; i++) {
            alphabetChars += startingChar + " ";

            // increment `startingChar` to the next alphabet character
            startingChar++;
        }
        // add last character without trailing whitespace
        alphabetChars += startingChar;

        return alphabetChars;
    }

    private static RightJustifiedTriangle calculateTriangle(int n) {
        // chars in each row of the triangle
        var charsInRow = 0;
        // rows we counted in the triangle
        var rowCount = 0;
        // chars needed to form a full triangle with `rowCount` rows
        var charCount = 0;
        while (charCount < n) {
            // in each subsequent row we have more alphabet chars available
            charsInRow++;
            rowCount++;
            charCount += charsInRow;
        }

        var charsInLastRow = charsInRow;
        var rowLength = 2 * charsInLastRow - 1;

        return new RightJustifiedTriangle(rowCount, rowLength, n);
    }

    record RightJustifiedTriangle(int rowCount, int rowLength, int providedCharCount) {
    }
}
