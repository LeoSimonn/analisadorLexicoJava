import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;

public class AnalisadorLexico {

    // Códigos de tokens
    private static final int INT_LIT = 10;
    private static final int IDENTIFIER = 11;
    private static final int ASSIGN_OP = 12;
    private static final int ADD_OP = 13;
    private static final int SUBTRACTION_OP = 14;
    private static final int MULTIPLICATION_OP = 15;
    private static final int DIVISION_OP = 16;
    private static final int LEFT_PARENTHESIS = 17;
    private static final int RIGHT_PARENTHESIS = 18;
    private static final int FOR_PAL = 20;
    private static final int IF_PAL = 21;
    private static final int ELSE_PAL = 22;
    private static final int WHILE_PAL = 23;
    private static final int DO_PAL = 24;
    private static final int INT_PAL = 25;
    private static final int FLOAT_PAL = 26;
    private static final int SWITCH_PAL = 27;

    // Variáveis auxiliares globais
    private CharacterClass characterClass;
    private final ArrayList<Character> lexeme = new ArrayList<>();
    private char currentChar;
    private int nextToken;
    final private BufferedReader input;

    public AnalisadorLexico(BufferedReader reader) {
        input = reader;
    }

    // lookup: método para checar se o char único é um símbolo
    private void lookup() {
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
            case '=':
                lexeme.add(currentChar);
                nextToken = ASSIGN_OP;
                break;
            default:
                nextToken = -1;
                break;
        }
    }

    // Checa se nextChar é um characterClass de dígito/letra/símbolo(unknown)/end of file
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

    //pega próximo char não vazio
    private void getNextNonBlankChar() throws IOException {
        while (Character.isWhitespace(currentChar)) {
            nextChar();
        }
    }

    // Descobre qual é o lexema a partir do characterClass
    public void getNextLexeme() throws IOException {
        // Limpa o ArrayList lexeme
        lexeme.clear();

        getNextNonBlankChar();
        switch (characterClass) {

            // Se começa com letra, automaticamente é um Identificador ou palavra reservada
            case LETTER:
                lexeme.add(currentChar);
                nextChar();

                while (characterClass == CharacterClass.LETTER || characterClass == CharacterClass.DIGIT) {
                    lexeme.add(currentChar);
                    nextChar();
                }

                switch (lexemeStringified()) {
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

                    case "float":
                        nextToken = FLOAT_PAL;
                        break;

                    case "int":
                        nextToken = INT_PAL;
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

    public int getNextToken() {
        return nextToken;
    }

    private String lexemeStringified() {
        StringBuilder lexemeCastedToString = new StringBuilder();

        for (Character ch : lexeme) {
            lexemeCastedToString.append(ch);
        }

        return lexemeCastedToString.toString();
    }
}
