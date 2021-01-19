package classes;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandLineProcesses {

    public static void runCommand(String[] commands) throws Exception {
        ProcessBuilder builder = new ProcessBuilder(
                commands);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            System.out.println(line);
        }
    }
}