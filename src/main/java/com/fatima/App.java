package com.fatima;

import java.util.List;

/**
 * Prefix Expression Workbench - Final Integration Demo
 * Standardized Formatting for Professional Table Display.
 */
public class App {
    // Shared constant to ensure all dividers match exactly
    private static final int UI_WIDTH = 50;

    public static void main(String[] args) {
        System.out.println("=== PREFIX EXPRESSION WORKBENCH DEMO ===");

        // --- PART 1: AUTOMATED BATCH TESTS ---
        runDemo("Variadic Arguments Test", "(+ 10 20 30 40)");
        runDemo("Nested Expression Test", "(* (+ 1 2) (- 10 5))");
        runDemo("Variable Binding Test", "(let x 50) (* x 2)");
        runDemo("Boolean Logic Test", "(and true (> 10 5))");
        runDemo("Malformed Input Test", "(+ 5 (* 2 3)"); 
        runDemo("Invalid Character Test", "(let price $100)"); 

        // --- PART 2: INTERACTIVE REPL MODE ---
        startREPL();
    }

    private static void startREPL() {
        java.util.Scanner console = new java.util.Scanner(System.in);
        
        System.out.println("\n" + "=".repeat(UI_WIDTH));
        System.out.println("REPL MODE ACTIVE: Type any expression to test.");
        System.out.println("Type 'exit' to quit.");
        System.out.println("=".repeat(UI_WIDTH));

        while (true) {
            System.out.print("\nprefix > ");
            if (!console.hasNextLine()) break;
            
            String userInput = console.nextLine().trim();

            if (userInput.equalsIgnoreCase("exit") || userInput.equalsIgnoreCase("quit")) {
                System.out.println("Closing Workbench. Happy Compiling!");
                break;
            }

            if (userInput.isEmpty()) continue;

            runDemo("REPL Input", userInput);
        }
        console.close();
    }

    private static void runDemo(String testLabel, String source) {
        // Label Divider
        System.out.println("\n" + "=".repeat(UI_WIDTH));
        System.out.println(String.format("%-" + UI_WIDTH + "s", "LABEL:  " + testLabel));
        System.out.println(String.format("%-" + UI_WIDTH + "s", "SOURCE: " + source));
        System.out.println("=".repeat(UI_WIDTH));

        try {
            // 1. Scanner Output
            System.out.println("\n[1] SCANNER (Token Stream):");
            // Standardizing columns to fit the UI_WIDTH
            System.out.printf("%-18s | %-15s | %s%n", "TYPE", "LEXEME", "LITERAL");
            System.out.println("-".repeat(UI_WIDTH));
            
            Scanner scanner = new Scanner(source);
            List<Token> tokens = scanner.scanTokens();
            
            for (Token token : tokens) {
                if (token.type != TokenType.EOF) {
                    // Update the Token's toString format if needed, 
                    // or format here for precision:
                    System.out.printf("%-18s | %-15s | %s%n", 
                        token.type, token.lexeme, token.literal);
                }
            }

            // 2. Parser Output
            System.out.println("\n[2] PARSER (Visual AST Structure):");
            System.out.println("-".repeat(UI_WIDTH));
            Parser parser = new Parser(tokens);
            List<Node> astRootNodes = parser.parse();

            if (astRootNodes.isEmpty()) {
                System.out.println("(No expressions parsed)");
            } else {
                for (Node node : astRootNodes) {
                    node.prettyPrint("", true);
                }
            }

        } catch (RuntimeException e) {
            System.err.println("\n" + "!".repeat(UI_WIDTH));
            System.err.println(">>> FRONT-END ERROR: " + e.getMessage());
            System.err.println("!".repeat(UI_WIDTH));
        }
    }
}