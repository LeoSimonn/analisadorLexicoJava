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
    public static final int SEMI_COLON = 27;

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
            case ';':
                addChar();
                nextToken = SEMI_COLON; // Você precisará definir isso como uma constante.
                break;
            case '(':
                addChar();
                nextToken = LEFT_PAREN;
                break;
            case ')':
                addChar();
                nextToken = RIGHT_PAREN;
                break;
            case '+':
                addChar();
                nextToken = ADD_OP;
                break;
            case '-':
                addChar();
                nextToken = SUB_OP;
                break;
            case '*':
                addChar();
                nextToken = MULT_OP;
                break;
            case '/':
                addChar();
                nextToken = DIV_OP;
                break;
            // Inclua isso no método lookup
            case '=':
                addChar();
                nextToken = ASSIGN_OP;
                break;
            default:
                addChar();
                nextToken = UNKNOWN; // Deve ser UNKNOWN em vez de EOF.
                break;
        }
        return nextToken;
    }

    public void addChar() {
        if (lexLen <= 98) {
            lexeme[lexLen++] = nextChar;
            lexeme[lexLen] = 0; // Define o próximo caractere do lexeme como o terminador nulo
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
            case LETTER:
                addChar();
                getChar();
                while (charClass == LETTER || charClass == DIGIT) {
                    addChar();
                    getChar();
                }
                nextToken = IDENT;
                break;
            case DIGIT:
                addChar();
                getChar();
                while (charClass == DIGIT) {
                    addChar();
                    getChar();
                }
                nextToken = INT_LIT;
                break;
            case UNKNOWN:
                lookup(nextChar);
                getChar();
                break;
            case EOF:
                nextToken = EOF;
                lexeme[0] = 'E';
                lexeme[1] = 'O';
                lexeme[2] = 'F';
                lexeme[3] = 0;
                break;
            default:
                break;
        }

        String s = new String(lexeme, 0, lexLen);
        System.out.println("Next token is: " + nextToken + ", Next lexeme is " + s);

        // Reseta o array lexeme para o próximo lex
        for (int i = 0; i < lexeme.length; i++) {
            lexeme[i] = 0;
        }
        lexLen = 0; // Reseta o contador de comprimento do lexema

        return nextToken;
    }

    public int getNextToken() {
        return nextToken;
    }

}
