package com.example.lab3lpfc;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.example.lab3lpfc.TokenType.*;

public class Lexer {
    public static void main(String[] args) {
        String testSource = "func findletter(string x, char c){\n" +
                "string nr = \"\";\n" +
                "int i = 0;\n" +
                "while (i < x.length){\n" +
                "\tif x(i) == c;\n" +
                "\tnr = nr + i;\n" +
                "\tbreak;\n" +
                "}\n" +
                "\telse {\n" +
                "\ti = i + 1;\n" +
                "}\n" +
                "rtrn nr;\n" +
                "}\n" +
                "\n" +
                "mainPR{\n" +
                "string alphabet;\n" +
                "alphabet = \"abc\";\n" +
                "string result;\n" +
                "findletter (alphabet,c);\n" +
                "print (result)\n" +
                "}";

        ArrayList<Token> listofTokens = new Lexer().tokenize(testSource);
        for (Token token : listofTokens) {
            System.out.println("\t" + token.getValue() + " - " + token.getType());
        }
    }

    public TokenType nextTokenType(String input, int marker) {
        char nextChar = input.charAt(marker);

        if (Character.isAlphabetic(nextChar)) {
            return TokenType.IDENTIFIER;
        } else if (Character.isDigit(nextChar) || nextChar == '.') {
            return TokenType.NUMBER;
        } else if (Character.isWhitespace(nextChar)) {
            return TokenType.WHITESPACE;
        } else if (nextChar == '"') {
            return TokenType.STRING;
        } else {
            TokenType x;
            switch (nextChar) {
                case '(':
                    x = TokenType.LEFT_PAREN;
                    break;
                case ')':
                    x = TokenType.RIGHT_PAREN;
                    break;
                case '{':
                    x = TokenType.LEFT_BRACE;
                    break;
                case '}':
                    x = TokenType.RIGHT_BRACE;
                    break;
                case ',':
                    x = TokenType.COMMA;
                    break;
                case '.':
                    x = TokenType.DOT;
                    break;
                case '-':
                    x = TokenType.MINUS;
                    break;
                case '+':
                    x = TokenType.PLUS;
                    break;
                case ';':
                    x = TokenType.SEMICOLON;
                    break;
                case '*':
                    x = TokenType.STAR;
                    break;
                case '<':
                    x = TokenType.LESS;
                    break;
                case '>':
                    x = TokenType.GREATER;
                    break;
                case '=':
                    x = TokenType.EQUAL;
                    break;
                default:
                    throw new RuntimeException("Unrecognized character");
            }
            return x;
        }
    }

    //method to look at beginning and find first token
    public ArrayList<Token> tokenize(String input) {
        ArrayList<Token> result = new ArrayList<Token>();
        final Map<String, TokenType> keywords;
        final Map<String, TokenType> datatypes;
        keywords = new HashMap<>();
        keywords.put("and", AND);
        keywords.put("else", ELSE);
        keywords.put("for", FOR);
        keywords.put("if", IF);
        keywords.put("or", OR);
        keywords.put("print", PRINT);
        keywords.put("return", RETURN);
        keywords.put("true", TRUE);
        keywords.put("while", WHILE);

        int marker = 0;
        while (marker < input.length()) {
            TokenType nextTokenType = nextTokenType(input, marker);
            if (nextTokenType == IDENTIFIER) {
                String tokenValue = "";
                while (nextTokenType(input, marker) == nextTokenType && marker < input.length()) {
                    tokenValue += input.charAt(marker);
                    if (marker != input.length() - 1) {
                        marker++;
                    } else break;
                }
                TokenType type = keywords.get(tokenValue);
                if (type == null) {
                    nextTokenType = IDENTIFIER;
                } else {
                    nextTokenType = keywords.get(tokenValue);
                }
                Token newToken = new Token(tokenValue, nextTokenType);
                result.add(newToken);
            } else if (nextTokenType == nextTokenType.NUMBER) {
                String tokenValue = "";
                while (nextTokenType(input, marker) == nextTokenType && marker < input.length()) {
                    tokenValue += input.charAt(marker);
                    if (marker != input.length() - 1) {
                        marker++;
                    } else break;
                }
                Token newToken = new Token(tokenValue, nextTokenType);
                result.add(newToken);
                marker += tokenValue.length();
            } else if (nextTokenType == WHITESPACE && marker < input.length()) {
                marker++;
            } else if (nextTokenType == STRING) {
                String tokenValue = "";
                marker++;
                while (marker < input.length() && input.charAt(marker) != '"') {
                    tokenValue += input.charAt(marker);
                    marker++;
                }
                if (marker == input.length()) {
                    throw new RuntimeException("Missing ending quote");
                }
                marker++;
                result.add(new Token(tokenValue, nextTokenType));

            } else {

                String tokenValue = "";
                tokenValue += input.charAt(marker);
                Token newToken = new Token(tokenValue, nextTokenType);
                result.add(newToken);
                marker++;
            }
        }
        return result;
    }
}

