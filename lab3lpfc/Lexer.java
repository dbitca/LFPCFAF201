package com.example.lab3lpfc;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Lexer {
    public static void main(String[] args) {
        String testSource = "public static int minFunction(int n1, int n2) {\n" +
                "   int min;\n" +
                "   if (n1 > n2)\n" +
                "      min = n2;\n" +
                "   else\n" +
                "      min = n1;\n" +
                "\n" +
                "   return min; \n" +
                "}";
        ArrayList<Token> listofTokens = new Lexer().tokenize(testSource);
        for (Token token : listofTokens){
            System.out.println("\t" + token.getValue() + " - " + token.getType());
            //System.out.println(" ");
           // System.out.print(token.getType());
        }
    }
    public TokenType nextTokenType(String input, int marker){
        char nextChar = input.charAt(marker);
        if (Character.isAlphabetic(nextChar)) {
            return TokenType.IDENTIFIER;
        }
        else if (Character.isDigit(nextChar) || nextChar == '.'){
            return TokenType.NUMBER;
        }
        else if (Character.isWhitespace(nextChar)){
            return TokenType.WHITESPACE;
        }
        else {
            return TokenType.OPERATOR;
        }
    }
    //method to look at beginning and find first token
    public ArrayList<Token> tokenize(String input)
    {
        ArrayList<Token> result = new ArrayList<Token>();
        int marker = 0;
        while (marker<input.length()) {
            TokenType nextTokenType = nextTokenType(input, marker);
            if (nextTokenType == TokenType.IDENTIFIER || nextTokenType == nextTokenType.NUMBER) {
                String tokenValue = "";
                while (nextTokenType(input, marker) == nextTokenType && marker < input.length()) {
                    tokenValue += input.charAt(marker);
                    marker++;
                }
                Token newToken = new Token(tokenValue, nextTokenType);
                result.add(newToken);
            } else if (nextTokenType == TokenType.WHITESPACE && marker < input.length()) {
                marker++;
            } else {
                final String [] VALID_OPERATORS = {"++", "==", "!=", ">=", "+", "-", "+", "/", ">", "<", ";", "=", "(", ")", ",", "{", "}"};
                for (String operator : VALID_OPERATORS){
                    if(input.substring(marker).startsWith(operator)){
                        result.add(new Token(operator, TokenType.OPERATOR));
                        marker += operator.length();
                        break;
                    }
                }

            }
        }
        return result;
    }
}
