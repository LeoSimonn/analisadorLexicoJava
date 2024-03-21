import java.io.IOException;
import java.io.BufferedReader;

public class AnalisadorLexico {

    // Classes de caracteres
    public static final int LETTER = 0;
    public static final int DIGIT = 1;
    public static final int UNKNOWN = 99;

    // Códigos de tokens
    public static final int INT_LIT = 10;
    public static final int IDENT = 11;
    public static final int ASSIGN_OP = 20;
    public static final int ADD_OP = 21;
    public static final int SUB_OP = 22;
    public static final int MULT_OP = 23;
    public static final int DIV_OP = 24;
    public static final int LEFT_PAREN = 25;
    public static final int RIGHT_PAREN = 26;
    public static final int EOF = -1;

    // Variáveis globais
    private int charClass;
    private char[] lexeme = new char[100];
    private char nextChar;
    private int lexLen;
    private int token;
    private int nextToken;
    private BufferedReader in_fp;

    public AnalisadorLexico(BufferedReader reader) {
        in_fp = reader;
    }

    public int lookup(char ch) {
        switch (ch) {
            case '(':
                addChar();
                nextToken = LEFT_PAREN;
                break;
            // ... rest of lookup cases ...
            default:
                addChar();
                nextToken = EOF;
                break;
        }
        return nextToken;
    }

    public void addChar() {
        if (lexLen <= 98) {
            lexeme[lexLen++] = nextChar;
        } else {
            System.out.println("Error - lexeme is too long");
        }
    }

    public void getChar() throws IOException {
        int readChar = in_fp.read();
        nextChar = (char) readChar;
        if (readChar != -1) {
            if (Character.isLetter(nextChar)) {
                charClass = LETTER;
            } else if (Character.isDigit(nextChar)) {
                charClass = DIGIT;
            } else {
                charClass = UNKNOWN;
            }
        } else {
            charClass = EOF;
        }
    }

    public void getNonBlank() throws IOException {
        while (Character.isWhitespace(nextChar)) {
            getChar();
        }
    }

    public int lex() throws IOException {
        lexLen = 0;
        getNonBlank();
        switch (charClass) {
            // ... rest of lex method ...
        }
        System.out.println("Next token is: " + nextToken + ", Next lexeme is " + String.valueOf(lexeme).trim());
        return nextToken;
    }

    // Getters para uso no método main, se necessário
    public int getNextToken() {
        return nextToken;
    }

    // ... possíveis outros getters e setters ...
}
