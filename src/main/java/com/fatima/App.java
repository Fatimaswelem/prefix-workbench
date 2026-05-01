package com.fatima;

import java.util.List;

public class App {
    private static final int UI_WIDTH = 50;
    private static final String INDENT = "    "; 

    public static void main(String[] args) {
        System.out.println("=== PREFIX EXPRESSION WORKBENCH DEMO ===");

        // --- PART 1: AUTOMATED BATCH TESTS ---
        runDemo("Variadic Arguments Test", "(+ 10 20 30 40)");
        runDemo("Nested Expression Test", "(* (+ 1 2) (- 10 5))");
        runDemo("Variable Binding Test", "(let x 50) (* x 2)");
        runDemo("Boolean Logic Test", "(and true (> 10 5))");
        runDemo("Malformed Input Test", "(+ 5 (* 2 3)"); 
        runDemo("Invalid Character Test", "(let price $100)"); 

        // --- NEW COMPLICATED & LONG TESTS ---
        runDemo("Very Long Variadic Addition", "(+ 10 20 30 40 50 60 70 80 90 100)");
        runDemo("Deeply Nested Expression", "(+ 1 (+ 2 (+ 3 (+ 4 (+ 5 (+ 6 (+ 7 (+ 8 9)))))))))");
        runDemo("Complex Mixed Logic", "(and true (> (* 2 5) (+ 3 4)))");
        runDemo("Empty List Test", "()");

        // --- EXPERT-LEVEL EDGE CASES ---
        runDemo("Keyword Reservation Test", "(let and 10)");
        runDemo("Keyword Reservation Test 2", "(let let 10)");
        runDemo("Overflow Error Test", "(+ 999999999999 1)");

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
            if (userInput.equalsIgnoreCase("exit") || userInput.equalsIgnoreCase("quit")) break;
            if (userInput.isEmpty()) continue;

            runDemo("REPL Input", userInput);
        }
        console.close();
    }

    private static void runDemo(String testLabel, String source) {
        System.out.println("\n" + "=".repeat(UI_WIDTH));
        System.out.println(String.format("LABEL:  %s", testLabel));
        System.out.println(String.format("SOURCE: %s", source));
        System.out.println("=".repeat(UI_WIDTH));

        try {
            System.out.println("\n" + INDENT + "[1] SCANNER (Token Stream):");
            System.out.println(INDENT + String.format("%-18s | %-15s | %s", "TYPE", "LEXEME", "LITERAL"));
            System.out.println(INDENT + "-".repeat(UI_WIDTH - 4));
            
            Scanner scanner = new Scanner(source);
            List<Token> tokens = scanner.scanTokens();
            
            for (Token token : tokens) {
                if (token.type != TokenType.EOF) {
                    System.out.println(INDENT + String.format("%-18s | %-15s | %s", 
                        token.type, token.lexeme, token.literal));
                }
            }

            System.out.println("\n" + INDENT + "[2] PARSER (Visual AST Structure):");
            System.out.println(INDENT + "-".repeat(UI_WIDTH - 4));
            Parser parser = new Parser(tokens);
            List<Node> astRootNodes = parser.parse();

            if (astRootNodes.isEmpty()) {
                System.out.println(INDENT + "(No expressions parsed)");
            } else {
                for (Node node : astRootNodes) {
                    node.prettyPrint(INDENT, true); 
                }
            }

        } catch (Exception e) {
            System.err.println("\n" + "!".repeat(UI_WIDTH));
            System.err.println(">>> FRONT-END ERROR: " + e.getMessage());
            System.err.println("!".repeat(UI_WIDTH));
        }
    }
}