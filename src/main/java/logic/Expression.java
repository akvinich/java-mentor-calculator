package logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {

    public static final String REGEX_PLUS = "\\+";
    public static final String REGEX_MINUS = "\\-";
    public static final String REGEX_DIVIDE = "\\/";
    public static final String REGEX_MULT = "\\*";
    public static final String[] SPLITTERS = new String[] {"+", "-", "/", "*"};
    public static final String[] REGEX_SPLITTERS = new String[] {REGEX_PLUS, REGEX_MINUS, REGEX_DIVIDE, REGEX_MULT};
    public static final String REGEX_PUNCT = "\\p{Punct}";
    public static final String REGEX_BLANK = "(\\p{Blank})";
    public static final String REGEX_SPL_EXP = "((\\+)|(\\-)|(\\/)|(\\*))";
    public static final String REGEX_INT = "((\\d{1,2}))";
    public static final String REGEX_ROMAN = "((I{2,3})|(I[VX]?)|(VI{0,3})|(X{1}))";
    public static final String REGEX_INT_EXPRESSION = "(" + REGEX_INT + REGEX_BLANK + REGEX_SPL_EXP + REGEX_BLANK
            + REGEX_INT + ")";
    public static final String REGEX_ROMAN_EXPRESSION = "(" + REGEX_ROMAN + REGEX_BLANK + REGEX_SPL_EXP
            + REGEX_BLANK + REGEX_ROMAN + ")";

    private String exp;
    private ExpressionType expressionType;
    private String operation = "";

    public Expression(String exp) {
        if (isArabicExpression(exp)) {
            expressionType = ExpressionType.Arabic;
        } else if (isRomanExpression(exp)) {
            expressionType = ExpressionType.Roman;
        } else {
            throw new RuntimeException("Invalid expression!");
        }
        String op = extractOperation(exp);
        for (int i = 0; i < SPLITTERS.length; i++) {
            if (op.equals(SPLITTERS[i])) {
                this.operation = REGEX_SPLITTERS[i];
                break;
            }
            if (i == SPLITTERS.length - 1) {
                throw new RuntimeException("Invalid expression!");
            }
        }
        this.exp = exp;
    }

    private boolean isCorrect() {
        String[] partsOfExpression = exp.split(operation);
        if (partsOfExpression.length > 2 || partsOfExpression.length < 2) {
            return false;
        } else if (partsOfExpression[0].length() + partsOfExpression[1].length() != exp.length() - 1) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isArabicExpression(String expression) {
        return prepareMatcher(REGEX_INT_EXPRESSION, expression).find();
    }

    private boolean isRomanExpression(String expression) {
        return prepareMatcher(REGEX_ROMAN_EXPRESSION, expression).find();
    }

    private Matcher prepareMatcher(String regex, String expression) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(expression);
    }

    private String extractOperation(String expression) {
        Matcher matcher = prepareMatcher(REGEX_PUNCT, expression);
        matcher.find();
        return matcher.group();
    }

    public String calculate() {
        if (isCorrect()) {
            String[] operators = exp.split(operation);
            switch (expressionType) {
                case Arabic:
                    int first = Integer.valueOf(operators[0].trim());
                    int second = Integer.valueOf(operators[1].trim());
                    if (first > 10 || second > 10 || first < 1 || second < 1) {
                        throw new RuntimeException("Invalid expression!");
                    }
                    return String.valueOf(calculate(Integer.valueOf(operators[0].trim()),
                            Integer.valueOf(operators[1].trim())));
                case Roman:
                    return calculate(new RomanNumeral(operators[0].trim()),
                            new RomanNumeral(operators[1].trim()));
                default:
                    throw new RuntimeException("Invalid expression!");
            }
        } else {
            throw new RuntimeException("Invalid expression!");
        }
    }

    private String calculate(RomanNumeral first, RomanNumeral second) {
        return new RomanNumeral(sendForCalculation(first.getNum(), second.getNum())).toString();
    }

    private int calculate(int first, int second) {
        return sendForCalculation(first, second);
    }

    private int sendForCalculation(int first, int second) {
        switch (operation) {
            case REGEX_PLUS:
                return add(first, second);
            case REGEX_MINUS:
                return sub(first, second);
            case REGEX_DIVIDE:
                return div(first, second);
            case REGEX_MULT:
                return mult(first, second);
            default:
                throw new RuntimeException("Invalid expression!");
        }
    }

    private int add(int first, int second) {
        return first + second;
    }

    private int sub(int first, int second) {
        return first - second;
    }

    private int div(int first, int second) {
        return first / second;
    }

    private int mult(int first, int second) {
        return first * second;
    }
}
