import analyzer.LexicalAnalyzer;
import model.Token;
import utils.Reader;
import utils.Writer;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader("src/input/program3.in");
        ArrayList<String> lines =  reader.read();

        LexicalAnalyzer token = new LexicalAnalyzer(lines);
        List<Token> detectedTokens = token.parse();

        Writer writer = new Writer();
        writer.writeTokens(detectedTokens);
    }
}