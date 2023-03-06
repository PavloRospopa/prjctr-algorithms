package rospopa.pavlo.ex6_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InterestRateCalculator {
    final double price;
    final double monthlyPayment;
    final int loanTerm;

    InterestRateCalculator(double price, double monthlyPayment, int loanTerm) {
        this.price = price;
        this.monthlyPayment = monthlyPayment;
        this.loanTerm = loanTerm;
    }

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var input = reader.readLine().split(" ");
        var price = Double.parseDouble(input[0]);
        var monthlyPayment = Double.parseDouble(input[1]);
        var loanTerm = Integer.parseInt(input[2]);

        var calculator = new InterestRateCalculator(price, monthlyPayment, loanTerm);
        System.out.printf("%.15f", calculator.binarySearchYearlyInterestRate());
    }

    double binarySearchYearlyInterestRate() {
        // yearly interest rate can be between [0.0; 100.0]
        // it will be always lower than bad yearly interest rate
        var bad = 100.1;
        // and always higher than good yearly interest rate
        var good = -0.1;

        for (int i = 0; i < 100; i++) {
            var guess = (good + bad) / 2;
            if (paymentsCoverLoanWithInterestRate(guess)) {
                good = guess;
            } else {
                bad = guess;
            }
        }

        return good;
    }

    private boolean paymentsCoverLoanWithInterestRate(double yearlyInterestRate) {
        var monthlyInterestRate = yearlyInterestRate / 100.0 / 12.0;
        double balance = price;
        for (int i = 0; i < loanTerm; i++) {
            balance += balance * monthlyInterestRate;
            balance -= monthlyPayment;
        }
        return balance <= 0.0;
    }
}
