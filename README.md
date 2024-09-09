# Formal Languages and Compiler Design

This repository contains the solutions to the laboratory assignments for the **Formal Languages and Compiler Design** course.

## Lab 1: Lexical Analyzer
- Implementation of a lexical analyzer for a custom mini programming language (MLP).
- MLP includes data types, assignment, input/output, conditional, and loop statements.
- The analyzer:
  - Reads a source file.
  - Generates the internal form (FIP) and symbol table (TS).
  - Detects and reports lexical errors.
  

## Lab 2: Finite Automata
- Developed a program that reads and simulates both deterministic and non-deterministic finite automata (FA/NFA).
- The program supports:
  - Displaying states, alphabet, transitions, and final states.
  - Verifying if a sequence is accepted by a deterministic finite automaton (DFA).
  - Finding the longest prefix accepted by the DFA.
  

## Lab 3: Regular Expressions and Finite Automata
- Extended the lexical analyzer to use finite automata for token identification.
- Implemented finite automata for identifiers, integer constants, and real numbers.
- No regular expressions used; everything is manually implemented using automata.


## Lab 4: Syntax Analyzer
- Designed a syntax analyzer for the custom MLP.
- The analyzer:
  - Reads a source file and checks it against the syntax rules.
  - Builds a parse tree for correct sequences and reports syntax errors for incorrect ones.
  

## Lab 5: Semantic Analyzer
- Implemented a semantic analyzer that checks for type mismatches, undefined variables, and other semantic errors in the MLP.
- The analyzer ensures that the program follows semantic rules.


## Lab 6: Intermediate Code Generation
- Developed a module for generating intermediate code from a given source program in MLP.
- The generated code can be used for further optimization or execution by a virtual machine.


