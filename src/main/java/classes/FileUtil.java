package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {
    public static int countLines(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        return lines;
    }


    public static void waitForFileToExist(String filePath) throws InterruptedException {
        File file = new File(filePath);

        int i = 10;
        while (!file.exists()) {
            Thread.sleep(500);
            if (--i <= 0) {
                System.out.println("Timeout after 10 retries, file doesn't exist");
                break;
            }
        }
    }
}

