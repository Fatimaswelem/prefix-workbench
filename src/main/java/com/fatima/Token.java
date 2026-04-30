package com.fatima;

public class Token {
    final TokenType type;
    final String lexeme;  // The raw text (e.g., "let")
    final Object literal; // The actual value (e.g., 10 or true)
    final int line;       // For error reporting

    public Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    @Override
    public String toString() {
        return String.format("%-12s | %-10s | %s", type, lexeme, literal);
    }
}