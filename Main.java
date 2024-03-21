import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Main {
    
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("front.in"));
            AnalisadorLexico analisadorLexico = new AnalisadorLexico(reader);

            analisadorLexico.getChar();
            do {
                analisadorLexico.lex();
            } while (analisadorLexico.getNextToken() != AnalisadorLexico.EOF);

            reader.close();

        } catch (IOException e) {
            System.out.println("ERROR - cannot open front.in \n");
        }
    }
}
