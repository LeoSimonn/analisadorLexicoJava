/*
* PUCRS - 2024/01
* Analisador Lexico - Linguagem de Programação - Andreia Konzen 
*
* @author Arthur Henrique Henz, Guilherme Azevedo dos Santos, Leonardo Monteiro e Victor Hugo Boeira Leitte 
*
*/

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("front.in"));
            AnalisadorLexico analisadorLexico = new AnalisadorLexico(reader);

            // pega o proximo char (o próximo neste caso é o primeiro)
            analisadorLexico.nextChar();
            do {
                analisadorLexico.getNextLexeme();
            } while (analisadorLexico.getNextToken() != -1);

            reader.close();

        } catch (IOException e) {
            System.out.println("ERROR - cannot open front.in \n");
        }
    }
}
