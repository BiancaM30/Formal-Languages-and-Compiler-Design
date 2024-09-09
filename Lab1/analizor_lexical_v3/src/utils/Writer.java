package utils;

import model.Token;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Writer {
    Path fileName = Paths.get("src/output/atomi.out");

    public void writeTokens(List<Token> tokenList) throws IOException {
        List<String> tokenWithTypes = new ArrayList<>();
        for (Token t : tokenList) {
            tokenWithTypes.add(t.getName() + " --> " + t.getType());
        }
        Files.write(fileName, tokenWithTypes, StandardCharsets.UTF_8);
    }
}