package com.example.lab3lpfc;

public class Token {
    private String tokenValue;
    private TokenType tokenType;

    public Token(String tokenValue, TokenType tokenType){
        this.tokenValue = tokenValue;
        this.tokenType = tokenType;
    }

    public TokenType getType(){
        return tokenType;
    }
    public String getValue(){
        return tokenValue;
    }
}
