package analyzer;

import binary_search_tree.BSTNodeConst;
import binary_search_tree.BSTNodeId;
import finite_automata.FiniteAutomata;
import model.Operator;
import model.OperatorType;
import model.Token;
import model.TokenType;

import java.io.FileNotFoundException;
import java.util.*;

import static binary_search_tree.BSTNodeConst.search;

public class LexicalAnalyzer {
    private final ArrayList<String> keyWords = new ArrayList<>(Arrays.asList(
            "public class Main { public static void main(String[] args) {Scanner in = new Scanner(System.in);",
            "int", "double", "enum",
            "in.next()", "in.nextInt()", "in.nextDouble()",
            "System.out.println",
            "if", "while"));
    private ArrayList<String> delimiters = new ArrayList<>(Arrays.asList("{", "}", "(", ")", ",", "[", "]", ";", "."));
    private String floatDigits = "[0-9]+"; //"[-+]?[0-9]*.?[0-9]*";

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
            new Operator("||", OperatorType.LOGICAL)
    ));

    private String identifiers = "([a-z]|[A-Z])([0-9]|[a-z]+|[A-Z]|_){0,250}";
    private String intDigits = "[0-9]+";

    private List<Token> detectedTokens = new ArrayList<>();
    private ArrayList<String> code = null;
    Map<String, Pair> tsiDS = new LinkedHashMap<>();
    Map<String, Pair> tsConstants = new LinkedHashMap<>();
    private List<PairString> tsTokens = new ArrayList<>(Arrays.asList(
            new PairString("ID", 0),
            new PairString("CONST", 1),
            new PairString("public class Main { public static void main(String[] args) {Scanner in = new Scanner(System.in);", 2),
            new PairString("int", 3),
            new PairString("double", 4),
            new PairString("enum", 5),
            new PairString("in.next()", 6),
            new PairString("in.nextInt()", 7),
            new PairString("in.nextDouble()", 8),
            new PairString("System.out.println", 9),
            new PairString("if", 10),
            new PairString("while", 11),
            new PairString("=", 12),
            new PairString("+", 13),
            new PairString("-", 14),
            new PairString("*", 15),
            new PairString("/", 16),
            new PairString("%", 17),
            new PairString("==", 18),
            new PairString("!=", 19),
            new PairString("<", 20),
            new PairString(">", 21),
            new PairString("<=", 22),
            new PairString(">=", 23),
            new PairString("&&", 24),
            new PairString("||", 25),
            new PairString("{", 26),
            new PairString("}", 27),
            new PairString("(", 28),
            new PairString(")", 29),
            new PairString("[", 30),
            new PairString("]", 31),
            new PairString(",", 32),
            new PairString(".", 33),
            new PairString(";", 34)
    ));
    private List<Pair> FIP = new ArrayList<>();

    FiniteAutomata faConstants, faIds;

    public LexicalAnalyzer(ArrayList<String> allLines) throws FileNotFoundException {
        this.code = allLines;
        this.faConstants = new FiniteAutomata("C:\\Facultate\\Cursuri facultate sem 5\\LFTC\\Laboratoare\\Teme\\Lab2\\lftc_lab2_af_part2\\src\\finite_automata\\faConstants.txt");
        this.faIds = new FiniteAutomata("C:\\Facultate\\Cursuri facultate sem 5\\LFTC\\Laboratoare\\Teme\\Lab2\\lftc_lab2_af_part2\\src\\finite_automata\\faIds");
        faConstants.readFromFile();
        faIds.readFromFile();
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
        } else if (faConstants.isSequenceAccepted(split)) {
            return TokenType.CONSTANT;
        } else if (faIds.isSequenceAccepted(split)) {
            return TokenType.IDENTIFIER;
        }
        return TokenType.UNKNOWN;
    }



    public Map<String, Pair> createTSConstants(List<Token> tokens) {
        List<Token> tokenListInOrder = new ArrayList<>();
        int nrIds = 0;
        BSTNodeId root = null;
        //creare arbore binar de cautare
        for (Token token : tokens) {
            if (token.getType().equals(TokenType.CONSTANT) && (!BSTNodeId.search(token.getName(), root))) {
                String value = token.getName();
                if (nrIds == 0) root = new BSTNodeId(value);
                else root = BSTNodeId.insert(value, root);
                nrIds++;
                tokenListInOrder.add(token);
            }
        }

        //Map<String, Pair> tsiDS = new LinkedHashMap<>();
        //parcurgere tokenuri in ordinea inserarii pentru a crea TS
        for (Token token : tokenListInOrder) {
            if (root.levelOrder(root, token.getName()) != null) {
                String left, right;
                BSTNodeId node = root.levelOrder(root, token.getName());
                if (node.getLeftChild() == null) left = String.valueOf(-1);
                else left = node.getLeftChild().getValue();

                if (node.getRightChild() == null) right = String.valueOf(-1);
                else right = node.getRightChild().getValue();

                int left_pos = getPositionFromTokenList(tokenListInOrder, String.valueOf(left));
                int right_pos = getPositionFromTokenList(tokenListInOrder, String.valueOf(right));
                tsConstants.put(token.getName(), new Pair(left_pos, right_pos));
            }
        }
        return tsConstants;
    }
    public Map<String, Pair> createTSIds(List<Token> tokens) {
        List<Token> tokenListInOrder = new ArrayList<>();
        int nrIds = 0;
        BSTNodeId root = null;
        //creare arbore binar de cautare
        for (Token token : tokens) {
            if (token.getType().equals(TokenType.IDENTIFIER) && (!BSTNodeId.search(token.getName(), root))) {
                String value = token.getName();
                if (nrIds == 0) root = new BSTNodeId(value);
                else root = BSTNodeId.insert(value, root);
                nrIds++;
                tokenListInOrder.add(token);
            }
        }

        //Map<String, Pair> tsiDS = new LinkedHashMap<>();
        //parcurgere tokenuri in ordinea inserarii pentru a crea TS
        for (Token token : tokenListInOrder) {
            if (root.levelOrder(root, token.getName()) != null) {
                String left, right;
                BSTNodeId node = root.levelOrder(root, token.getName());
                if (node.getLeftChild() == null) left = String.valueOf(-1);
                else left = node.getLeftChild().getValue();

                if (node.getRightChild() == null) right = String.valueOf(-1);
                else right = node.getRightChild().getValue();

                int left_pos = getPositionFromTokenList(tokenListInOrder, String.valueOf(left));
                int right_pos = getPositionFromTokenList(tokenListInOrder, String.valueOf(right));
                tsiDS.put(token.getName(), new Pair(left_pos, right_pos));
            }
        }
        return tsiDS;
    }

    public List<Pair> createFIP(List<Token> tokens) {
        for (Token token : tokens) {
            double left = -1, right = -1;
            if (token.getType() == TokenType.CONSTANT) {
                left = 1;
                if (token.getName().contains("b")){
                    //right = getPositionFromTsConstants(token.getName());
                }
                 else right = getPositionFromTsConstants(token.getName());
                // else right = getPositionFromTsConstants(Double.parseDouble(token.getName()));
            } else if (token.getType() == TokenType.IDENTIFIER) {
                left = 0;
                right = getPositionFromTsIdentifier(token.getName());
            } else {
                left = getPositionFromTsTokens(token.getName());

            }
            FIP.add(new Pair(left, right));
        }
        return FIP;
    }

    public int getPositionFromTokenList(List<Token> tokenList, String elem) {
        for (int i = 0; i < tokenList.size(); i++) {
            if (tokenList.get(i).getName().equals(elem))
                return i + 1;
        }
        return -1;
    }

    public int getPositionFromTsTokens(String elem) {
        for (int i = 0; i < tsTokens.size(); i++) {
            if (tsTokens.get(i).getLeft().equals(elem))
                return tsTokens.get(i).getRight();
        }
        return -1;
    }

    public int getPositionFromTsConstants(String elem) {
        int poz = 1;
        for (Map.Entry<String, Pair> entry : tsConstants.entrySet()) {
                if (entry.getKey().equals(elem)) {
                    return poz;
            }
            poz++;
        }
        return -1;
    }

    public int getPositionFromTsIdentifier(String elem) {
        int poz = 1;
        for (Map.Entry<String, Pair> entry : tsiDS.entrySet()) {
            if (entry.getKey().equals(elem)) {
                return poz;
            }
            poz++;
        }
        return -1;
    }
}