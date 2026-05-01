package com.fatima;

import java.util.*;

public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Node> parse() {
        List<Node> nodes = new ArrayList<>();
        while (!isAtEnd()) {
            nodes.add(expression());
        }
        return nodes;
    }

    private Node expression() {
        if (match(TokenType.LPAREN)) return list();
        return atom();
    }

    private Node atom() {
        if (match(TokenType.NUMBER, TokenType.TRUE, TokenType.FALSE)) {
            return new LiteralNode(previous().literal);
        }
        if (match(TokenType.IDENTIFIER)) {
            return new IdNode(previous().lexeme);
        }
        throw error(peek(), "Expected expression.");
    }

    private Node list() {
        if (match(TokenType.LET)) {
                Token idToken = advance(); // Get next token for validation
                if (idToken.type != TokenType.IDENTIFIER) {
                    // Updated for specific Keyword Reservation error message
                    throw error(idToken, "Keyword Reservation Error: Cannot use '" + idToken.lexeme + "' as a variable name.");
                }
        
        Node value = expression();
        consume(TokenType.RPAREN, "Expect ')' after let binding.");
        return new LetNode(idToken.lexeme, value);
    }

        // Handle standard operators (+, -, *, /, and, or, not, >, <, =)
        Token op = advance(); // Take the operator
        List<Node> args = new ArrayList<>();
        while (!check(TokenType.RPAREN) && !isAtEnd()) {
            args.add(expression());
        }

        consume(TokenType.RPAREN, "Expect ')' after arguments.");
        return new ApplicationNode(op.lexeme, args);
    }

    // Helper methods
    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
        throw error(peek(), message);
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() { return peek().type == TokenType.EOF; }
    private Token peek() { return tokens.get(current); }
    private Token previous() { return tokens.get(current - 1); }

    private RuntimeException error(Token token, String message) {
        // Return a RuntimeException with the full formatted string for App to print
        return new RuntimeException("[Line " + token.line + "] " + message);
    }
}
