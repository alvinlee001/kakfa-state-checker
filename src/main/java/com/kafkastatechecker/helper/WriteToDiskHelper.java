package com.kafkastatechecker.helper;

import java.io.FileWriter;
import java.io.IOException;

public class WriteToDiskHelper {
    public static void writeToDisk(String fileName, String content) {
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(content);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
