import analyzer.LexicalAnalyzer;
import analyzer.Pair;
import model.Token;
import utils.Reader;
import utils.Writer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) throws IOException {
        Reader reader = new Reader("src/input/program4.in");
        ArrayList<String> lines = reader.read();

        LexicalAnalyzer token = new LexicalAnalyzer(lines);
        List<Token> detectedTokens = token.parse();

        Writer writer = new Writer();
        writer.writeTokens(detectedTokens);


        Map<String, Pair> tsConstants = token.createTSConstants(detectedTokens);
        Map<String, Pair> tsIds = token.createTSIds(detectedTokens);
        List<Pair> fip = token.createFIP(detectedTokens);

        writer.writeTS_FIP(tsConstants, tsIds, fip);
    }
}