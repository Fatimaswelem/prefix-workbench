package com.fatima;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AST Node hierarchy for Variant 5: Prefix Expression Workbench.
 * Includes Pretty Print logic for Milestone 3/5 demo readiness.
 */
public abstract class Node {
    public abstract String toString();
    
    // This defines the method that App.java is looking for
    public abstract void prettyPrint(String indent, boolean isLast);
}

class LiteralNode extends Node {
    final Object value;
    LiteralNode(Object value) { this.value = value; }

    @Override public String toString() { return String.valueOf(value); }

    @Override
    public void prettyPrint(String indent, boolean isLast) {
        System.out.println(indent + (isLast ? "└── " : "├── ") + value + " [Literal]");
    }
}

class IdNode extends Node {
    final String name;
    IdNode(String name) { this.name = name; }

    @Override public String toString() { return name; }

    @Override
    public void prettyPrint(String indent, boolean isLast) {
        System.out.println(indent + (isLast ? "└── " : "├── ") + name + " [Identifier]");
    }
}

class ApplicationNode extends Node {
    final String operator;
    final List<Node> arguments;

    ApplicationNode(String operator, List<Node> arguments) {
        this.operator = operator;
        this.arguments = arguments;
    }

    @Override 
    public String toString() { 
        String args = arguments.stream().map(Node::toString).collect(Collectors.joining(" "));
        return "(" + operator + " " + args + ")"; 
    }

    @Override
    public void prettyPrint(String indent, boolean isLast) {
        System.out.println(indent + (isLast ? "└── " : "├── ") + operator + " [Operation]");
        String newIndent = indent + (isLast ? "    " : "│   ");
        for (int i = 0; i < arguments.size(); i++) {
            arguments.get(i).prettyPrint(newIndent, i == arguments.size() - 1);
        }
    }
}

class LetNode extends Node {
    final String id;
    final Node value;

    LetNode(String id, Node value) {
        this.id = id;
        this.value = value;
    }

    @Override 
    public String toString() { return "(let " + id + " " + value.toString() + ")"; }

    @Override
    public void prettyPrint(String indent, boolean isLast) {
        System.out.println(indent + (isLast ? "└── " : "├── ") + "let (" + id + ") [Binding]");
        value.prettyPrint(indent + (isLast ? "    " : "│   "), true);
    }
}
