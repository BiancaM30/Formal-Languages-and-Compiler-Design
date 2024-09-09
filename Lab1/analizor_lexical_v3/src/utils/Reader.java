package utils;

import java.io.*;
import java.util.ArrayList;

public class Reader {
    private String fileName;

    public Reader(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<String> read() throws IOException {
        FileInputStream fstream = new FileInputStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        ArrayList<String> lines = new ArrayList<String>();
        String currentLine = br.readLine();
        while (currentLine != null) {
            lines.add(currentLine.trim());
            currentLine = br.readLine();
        }
        br.close();
        return lines;
    }
}
