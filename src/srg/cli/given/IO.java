package srg.cli.given;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class IO {
    private String stdin;
    private String stdout;
    private List<String> inputQueue;
    private List<String> inputRecord;

    public IO() {
        inputQueue = new LinkedList<>();
        inputRecord = new ArrayList<>();
    }

    public void writeLn(String line) {
        stdout += line + System.lineSeparator();
        System.out.println(line);
    }

    public void write(String line) {
        stdout += line;
        System.out.print(line);
    }

    public String readLine() {
        if (inputQueue.isEmpty()) {
            Scanner s = new Scanner(System.in);
            return s.nextLine();
        } else {
            return inputQueue.remove(0);
        }
    }

    public void addInputLine(String line) {
        inputQueue.add(line);
    }

    public void addInputLine(List<String> lines) {
        inputQueue.addAll(lines);
    }

    public String getStdin() {
        return stdin;
    }

    public String getStdout() {
        return stdout;
    }
}
