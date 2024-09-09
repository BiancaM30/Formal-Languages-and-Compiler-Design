package analyzer;

import model.Operator;
import model.OperatorType;
import model.Token;
import model.TokenType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LexicalAnalyzer {
    private final ArrayList<String> keyWords = new ArrayList<>(Arrays.asList(
            "public class Main {public static void main(String[] args) {Scanner in = new Scanner(System.in);",
            "int", "double", "enum",
            "in.next()", "in.nextInt()", "in.nextDouble()",
            "System.out.println",
            "if", "while"));
    private ArrayList<String> delimiters = new ArrayList<>(Arrays.asList("{", "}", "(", ")", ",", "[", "]", ";","."));
    private List<Operator> operatorList = new ArrayList<>(Arrays.asList(
            new Operator("=", OperatorType.ASSIGNMENT),
            new Operator("+", OperatorType.ARITHMETIC),
            new Operator("-", OperatorType.ARITHMETIC),
            new Operator("*", OperatorType.ARITHMETIC),
            new Operator("/", OperatorType.ARITHMETIC),
            new Operator("%", OperatorType.ARITHMETIC),
            new Operator("==", OperatorType.COMPARISON),
            new Operator("!=", OperatorType.COMPARISON),
            new Operator("<", OperatorType.COMPARISON),
            new Operator(">", OperatorType.COMPARISON),
            new Operator("<=", OperatorType.COMPARISON),
            new Operator(">=", OperatorType.COMPARISON),
            new Operator("&&", OperatorType.LOGICAL),
            new Operator("&&", OperatorType.LOGICAL)
    ));

    private String identifiers = "([a-z]|[A-Z])([0-9]|[a-z]+|[A-Z]|_)*";
    private String intDigits = "[0-9]+";
    private String floatDigits = "[-+]?[0-9]*.?[0-9]*";

    private List<Token> detectedTokens = new ArrayList<>();
    private ArrayList<String> code = null;

    public LexicalAnalyzer(ArrayList<String> allLines) {
        this.code = allLines;
    }

    public List<Token> parse() {
        // special case for first line witch is hardcoded
        detectedTokens.add(new Token(code.get(0), TokenType.KEYWORD));

        for (int i = 1; i < this.code.size(); i++) {
            String line = this.code.get(i);
            String[] splited = line.split("\\s+");

            for (int j = 0; j < splited.length; j++) {
                String token = splited[j];
                TokenType result = checkToken(token);
                if (!result.equals(TokenType.UNKNOWN)) {
                    detectedTokens.add(new Token(token, result));
                } else {
                    String subToken = token.substring(0, token.length() - 1);
                    result = checkToken(subToken);
                    if (!result.equals(TokenType.UNKNOWN)) {
                        detectedTokens.add(new Token(subToken, result));

                        String lastToken = token.substring(token.length() - 1);
                        result = checkToken(lastToken);
                        if (!result.equals(TokenType.UNKNOWN)) {
                            detectedTokens.add(new Token(lastToken, result));
                        } else {
                            detectedTokens.add(new Token(subToken, result));
                            System.err.println("Error: Does not belong to language!");
                        }
                    } else {
                        detectedTokens.add(new Token(subToken, result));
                        System.err.println("Line: " + i + ", Status: " + result + " -> " + subToken);
                    }
                }
            }
        }
        return this.detectedTokens;
    }

    boolean verifyIfOperatorExists(String operator) {
        for (Operator value : operatorList) {
            if (operator.equals(value.getName()))
                return true;
        }
        return false;
    }

    private TokenType checkToken(String split) {
        if (this.keyWords.contains(split)) {
            return TokenType.KEYWORD;
        } else if (this.delimiters.contains(split)) {
            return TokenType.DELIMITER;
        } else if (verifyIfOperatorExists(split)) {
            return TokenType.OPERATOR;
        } else if (split.matches(this.intDigits) || split.matches(this.floatDigits)) {
            return TokenType.CONSTANT;
        }
        else if (split.matches(this.identifiers)) {
            return TokenType.IDENTIFIER;
        }
        return TokenType.UNKNOWN;
    }
}