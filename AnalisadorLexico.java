import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;

public class AnalisadorLexico {

    // Códigos de tokens
    public static final int INT_LIT = 10;
    public static final int IDENTIFIER = 11;
    public static final int ASSIGN_OP = 20;
    public static final int ADD_OP = 21;
    public static final int SUBTRACTION_OP = 22;
    public static final int MULTIPLICATION_OP = 23;
    public static final int DIVISION_OP = 24;
    public static final int LEFT_PARENTHESIS = 25;
    public static final int RIGHT_PARENTHESIS = 26;
    public static final int FOR_PAL = 20;
    public static final int IF_PAL = 21;
    public static final int ELSE_PAL = 22;
    public static final int WHILE_PAL = 23;
    public static final int DO_PAL = 24;
    public static final int INT_PAL = 25;
    public static final int FLOAT_PAL = 26;
    public static final int SWITCH_PAL = 27;

    // Variáveis globais
    private CharacterClass characterClass;
    private final ArrayList<Character> lexeme = new ArrayList<>();
    private char currentChar;
    private int nextToken;
    final private BufferedReader input;

    public AnalisadorLexico(BufferedReader reader) {
        input = reader;
    }

    // Método para checar se um char é palavra reservada é um símbolo
    public void lookup() {
        switch (currentChar) {
            case '(':
                lexeme.add(currentChar);
                nextToken = LEFT_PARENTHESIS;
                break;
            case ')':
                lexeme.add(currentChar);
                nextToken = RIGHT_PARENTHESIS;
                break;
            case '+':
                lexeme.add(currentChar);
                nextToken = ADD_OP;
                break;
            case '-':
                lexeme.add(currentChar);
                nextToken = SUBTRACTION_OP;
                break;
            case '*':
                lexeme.add(currentChar);
                nextToken = MULTIPLICATION_OP;
                break;
            case '/':
                lexeme.add(currentChar);
                nextToken = DIVISION_OP;
                break;
            default:
                nextToken = -1;
                break;
        }
    }

    public void nextChar() throws IOException {
        int readChar = input.read();
        currentChar = (char) readChar;
        if (readChar != -1) {
            if (Character.isLetter(currentChar)) {
                characterClass = CharacterClass.LETTER;
            } else if (Character.isDigit(currentChar)) {
                characterClass = CharacterClass.DIGIT;
            } else {
                characterClass = CharacterClass.UNKNOWN;
            }
        } else {
            characterClass = CharacterClass.EOF;
        }
    }

    public void getNonBlank() throws IOException {
        while (Character.isWhitespace(currentChar)) {
            nextChar();
        }
    }

    public void getNextLexeme() throws IOException {
        // Limpa o ArrayList lexeme
        lexeme.clear();

        getNonBlank();
        switch (characterClass) {

            // Se começa com letra, automaticamente é um Identificador
            case LETTER:
                lexeme.add(currentChar);
                nextChar();

                while (characterClass == CharacterClass.LETTER || characterClass == CharacterClass.DIGIT) {
                    lexeme.add(currentChar);
                    nextChar();
                }

                switch (lexemeString()) {
                    case "for":
                        nextToken = FOR_PAL;
                        break;

                    case "if":
                        nextToken = IF_PAL;
                        break;

                    case "else":
                        nextToken = ELSE_PAL;
                        break;

                    case "while":
                        nextToken = WHILE_PAL;
                        break;

                    case "do":
                        nextToken = DO_PAL;
                        break;

                    case "switch":
                        nextToken = SWITCH_PAL;
                        break;

                    default:
                        nextToken = IDENTIFIER;
                }

                break;

            // Se começa com dígito, é um Inteiro
            case DIGIT:
                lexeme.add(currentChar);
                nextChar();
                while (characterClass == CharacterClass.DIGIT) {
                    lexeme.add(currentChar);
                    nextChar();
                }
                nextToken = INT_LIT;
                break;

            // Se é um símbolo ou palavra reservada (unknown), vai no lookup() pra checar qual dos símbolos é
            case UNKNOWN:
                lookup();
                nextChar();
                break;

            case EOF:
                nextToken = -1;
                lexeme.add('E');
                lexeme.add('O');
                lexeme.add('F');
                break;
        }


        // Cria uma string a partir do ArrayList lexeme e remove os caracteres nulos
        System.out.println("Next token is: " + nextToken + ", Next lexeme is " + lexemeStringified());
    }

    // Getters para uso no método main, se necessário
    public int getNextToken() {
        return nextToken;
    }


    public String lexemeStringified() {
        StringBuilder lexemeCastedToString = new StringBuilder();

        for (Character ch : lexeme) {
            lexemeCastedToString.append(ch);
        }

        return lexemeCastedToString.toString();
    }
}
