package com.kafkastatechecker.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class WriteToDiskHelper {
    private static Pattern previousPattern = Pattern.compile("^.*-prev.json");
    private static Pattern latestPattern = Pattern.compile("^.*-latest.json");
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

    public static void renamePreviousFiles( File fileDirectory) {
        for (File file : fileDirectory.listFiles()) {
            if (previousPattern.matcher(file.getAbsoluteFile().getName()).matches()) {

                File newFile = new File(file.getAbsolutePath().replaceFirst("-prev", ""));
                file.renameTo(newFile);
            }
        }

        for (File file : fileDirectory.listFiles()) {
            if (latestPattern.matcher(file.getAbsoluteFile().getName()).matches()) {
                File newFile = new File(file.getAbsolutePath().replaceFirst("-latest", "-prev"));
                file.renameTo(newFile);
            }
        }
    }
}
