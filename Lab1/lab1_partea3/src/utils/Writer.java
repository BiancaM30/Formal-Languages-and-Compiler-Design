package utils;

import analyzer.Pair;
import model.Token;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Writer {
    Path fileName = Paths.get("src/output/atomi.out");
    Path fileName2 = Paths.get("src/output/fip.out");

    public void writeTokens(List<Token> tokenList) throws IOException {
        List<String> tokenWithTypes = new ArrayList<>();
        for (Token t : tokenList) {
            tokenWithTypes.add(t.getName() + " --> " + t.getType());
        }
        Files.write(fileName, tokenWithTypes, StandardCharsets.UTF_8);
    }

//    public void writeTS_FIP(Map<String, Pair> tsConst, Map<String, Pair> tsIds, List<Pair> fip) throws IOException {
//        List<String> lines = new ArrayList<>();
//        lines.add("Tabel constante");
//        for (Map.Entry<String, Pair> entry : tsConst.entrySet()) {
//            lines.add("Key = " + entry.getKey() +
//                    ", stanga = " + entry.getValue().getLeft() +
//                    ", dreapta= " + entry.getValue().getRight());
//        }
//
//
//        lines.add("\n\nTabel identificatori");
//        for (Map.Entry<String, Pair> entry : tsIds.entrySet()) {
//            lines.add("Key = " + entry.getKey() +
//                    ", stanga = " + entry.getValue().getLeft() +
//                    ", dreapta= " + entry.getValue().getRight());
//        }
//
//        lines.add("\n\nForma interna a programului");
//        lines.add("Cod atom  " + "  Cod TS");
//        for (Pair entry : fip) {
//            String right = "";
//            if (entry.getRight() != -1) right = String.valueOf(entry.getRight());
//            lines.add(entry.getLeft() + "            " + right);
//        }
//        Files.write(fileName2, lines, StandardCharsets.UTF_8);
//    }

    // for lab 3
    public void writeTS_FIP(Map<String, Pair> tsConst, Map<String, Pair> tsIds, List<Pair> fip) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("Tabel constante");
        int i=0;
        for (Map.Entry<String, Pair> entry : tsConst.entrySet()) {
            lines.add("Key = " + entry.getKey() +
                    ", poz = " + i);
            i++;
        }


        lines.add("\n\nTabel identificatori");
        i=0;
        for (Map.Entry<String, Pair> entry : tsIds.entrySet()) {
            lines.add("Key = " + entry.getKey() +
                    ", poz = " + i);
            i++;
        }

        lines.add("\n\nForma interna a programului");
        lines.add("Cod atom  " + "  Cod TS");
        for (Pair entry : fip) {
            String right = "";
            if (entry.getRight() != -1) right = String.valueOf(entry.getRight());
            lines.add(entry.getLeft() + "            " + right);
        }
        Files.write(fileName2, lines, StandardCharsets.UTF_8);
    }

//    public void writeTSIds(Map<String, Pair> ts) throws IOException {
//        List<String> lines = new ArrayList<>();
//        lines.add("Tabel constante");
//        for (Map.Entry<String, Pair> entry : ts.entrySet()) {
//            lines.add("Key = " + entry.getKey() +
//                    ", stanga = " + entry.getValue().getLeft() +
//                    ", dreapta= " + entry.getValue().getRight());
//        }
//        Files.write(fileName2, lines, StandardCharsets.UTF_8);
//    }
//
//    public void writeFIP(List<Pair> fip) throws IOException {
//        List<String> lines = new ArrayList<>();
//        lines.add("Forma interna a programului");
//        lines.add("Cod atom  " + "  Cod TS");
//        for (Pair entry : fip) {
//            String right = "";
//            if (entry.getRight() != -1) right = String.valueOf(entry.getRight());
//            lines.add(entry.getLeft() + "            " + right);
//        }
//        Files.write(fileName2, lines, StandardCharsets.UTF_8);
//    }
}