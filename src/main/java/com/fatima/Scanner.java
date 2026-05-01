package com.fatima;

import java.util.*;

public class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("let",   TokenType.LET);
        keywords.put("and",   TokenType.AND);
        keywords.put("or",    TokenType.OR);
        keywords.put("not",   TokenType.NOT);
        keywords.put("true",  TokenType.TRUE);
        keywords.put("false", TokenType.FALSE);
    }

    public Scanner(String source) {
        this.source = source;
    }

    public List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }
        tokens.add(new Token(TokenType.EOF, "", null, line));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(TokenType.LPAREN); break;
            case ')': addToken(TokenType.RPAREN); break;
            case '+': addToken(TokenType.PLUS); break;
            case '-': addToken(TokenType.MINUS); break;
            case '*': addToken(TokenType.STAR); break;
            case '/': addToken(TokenType.SLASH); break;
            case '>': addToken(TokenType.GREATER); break;
            case '<': addToken(TokenType.LESS); break;
            case '=': addToken(TokenType.EQUAL); break;
            case ' ':
            case '\r':
            case '\t': break; // Ignore whitespace
            case '\n': line++; break;
            default:
                if (Character.isDigit(c)) {
                    number();
                } else if (Character.isLetter(c)) {
                    identifier();
                } else {
                    // Updated to throw exception for high-visibility catching[cite: 46]
                    throw new RuntimeException("Lexical Error at Line " + line + ": Unexpected character: " + c);
                }
                break;
        }
    }

    private void identifier() {
        while (Character.isLetterOrDigit(peek())) advance();
        String text = source.substring(start, current);
        TokenType type = keywords.getOrDefault(text, TokenType.IDENTIFIER);
        
        // Handle boolean literals as actual boolean objects
        Object value = null;
        if (type == TokenType.TRUE) value = true;
        if (type == TokenType.FALSE) value = false;
        
        addToken(type, value);
    }

    private void number() {
        while (Character.isDigit(peek())) advance();
        String text = source.substring(start, current);
        try {
            int value = Integer.parseInt(text);
            addToken(TokenType.NUMBER, value);
        } catch (NumberFormatException e) {
            // Updated for specific overflow error message
            throw new RuntimeException("Lexical Error: Number '" + text + "' is too large for 32-bit Integer.");
        }
    }

    private boolean isAtEnd() { return current >= source.length(); }
    private char advance() { return source.charAt(current++); }
    private char peek() { return isAtEnd() ? '\0' : source.charAt(current); }
    private void addToken(TokenType type) { addToken(type, null); }
    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}