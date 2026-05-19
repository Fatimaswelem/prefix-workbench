# Prefix Expression Workbench

A Java program that reads, understands, and visually maps out math and logic expressions written in prefix format (where the operator comes first, like `+ 1 2`). It is built from scratch without using any outside tools to generate the code.


## Features

* **Custom Reader:** Scans the raw text and breaks it down into meaningful pieces (like numbers, symbols, and keywords).

* **Structure Builder:** Checks the pieces against strict grammar rules to figure out how they fit together, even if the expressions are long or deeply nested.

* **Visual Tree Map:** Draws a clear, step-by-step diagram on the screen to show how the program understands the expression.

* **Interactive Testing:** Includes a live prompt where you can type your own expressions and see the results instantly.

* **Clear Error Catching:** If you type something incorrectly, the program will point out exactly what is missing or wrong without crashing.


## Grammar Rules

The program understands expressions based on these specific rules:

``` BNF:

_Program    -> StmtSeq

StmtSeq    -> Expression StmtSeq | ε

Expression -> Atom | List

Atom       -> NUMBER | IDENTIFIER | BOOLEAN

List       -> '(' Form ')'

Form       -> OPERATOR ArgList | 'let' IDENTIFIER Expression

ArgList    -> Expression ArgList | ε

OPERATOR   -> '+' | '-' | '*' | '/' | 'and' | 'or' | 'not'|'>' | '<' | '='_

```

## How to Run the Program


* **Method 1:** Using a Code Editor (Easiest)

Open the project folder in your preferred Java editor (like IntelliJ IDEA or VS Code), open the com/fatima/App.java file, and click Run.


* **Method 2:** Using the Command Line

Open your terminal or command prompt, go to the main project folder, and enter these commands:

  Compile the code:
    Mac/Linux: javac com/fatima/*.java
    Windows: javac com\fatima\*.java

  Run the application:
    java com.fatima.App

Note: When the program starts, it will quickly run a few automatic tests to prove it works. After that, it will give you a prefix > prompt so you can test your own expressions. Type exit to close the program.


## Project Files Overview

* App.java: The main file that starts the program, runs the tests, and handles the interactive prompt.

* Scanner.java: The reader that looks at the text and identifies words, numbers, and symbols.

* Parser.java: The builder that enforces the grammar rules and organizes the symbols into a tree structure.

* Node.java: The file that defines how the different parts of the visual tree are drawn on the screen.

* Token.java & TokenType.java: Simple files that define the vocabulary the program is allowed to use.


## Documentation

For more specific details on how the code works, including a full list of test cases and examples of error messages, please read the provided documentation file included in this repository.
