package com.fatima;

public enum TokenType {
    // Single-character tokens
    LPAREN, RPAREN,
    
    // Operators (Arithmetic & Comparison)
    PLUS, MINUS, STAR, SLASH, 
    GREATER, LESS, EQUAL,
    
    // Keywords (Optional Extensions)
    LET, AND, OR, NOT, TRUE, FALSE,
    
    // Literals and Identifiers
    IDENTIFIER, NUMBER,
    
    // Special
    EOF
}