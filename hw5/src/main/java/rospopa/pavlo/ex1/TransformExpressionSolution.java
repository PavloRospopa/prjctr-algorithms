package rospopa.pavlo.ex1;

import rospopa.pavlo.FixedArrayStack;
import rospopa.pavlo.Stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TransformExpressionSolution {

    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var t = Integer.parseInt(reader.readLine());
        var transformer = new ExpressionTransformer(400);

        for (int i = 0; i < t; i++) {
            var expression = reader.readLine();
            System.out.println(transformer.transform(expression));
        }
    }

    static class ExpressionTransformer {
        private final Stack<String> operands;
        private final Stack<String> operators;

        public ExpressionTransformer(int maxExpressionLength) {
            this.operands = new FixedArrayStack<>(new String[maxExpressionLength]);
            this.operators = new FixedArrayStack<>(new String[maxExpressionLength]);
        }

        public String transform(String expression) {
            for (String s : expression.split("")) {
                if (isOperand(s)) {
                    operands.push(s);
                } else if (s.equals("(")) {
                    continue;
                } else if (s.equals(")")) {
                    var operand2 = operands.pop();
                    var operand1 = operands.pop();
                    var operator = operators.pop();
                    var rpnSubExpression = operand1 + operand2 + operator;
                    operands.push(rpnSubExpression);
                } else {
                    // should be some operator
                    operators.push(s);
                }
            }

            return operands.pop();
        }

        private boolean isOperand(String s) {
            return Character.isLetter(s.charAt(0));
        }
    }
}
