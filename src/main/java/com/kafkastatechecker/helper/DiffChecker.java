package com.kafkastatechecker.helper;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.regex.Pattern;

public class DiffChecker {
    public static void compareJSON(String leftJSON, String rightJSON) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();

        Map<String, Object> leftMap = gson.fromJson(leftJSON, type);
        Map<String, Object> rightMap = gson.fromJson(rightJSON, type);

        MapDifference<String, Object> difference = Maps.difference(leftMap, rightMap);

        System.out.println("Files are " + (difference.areEqual() ? "Identical" : "Distinct"));

        if (!difference.areEqual()) {
            if (difference.entriesOnlyOnLeft().size() > 0) {
                System.out.println("Entries only on the left\n--------------------------");
                difference.entriesOnlyOnLeft().forEach((key, value) -> System.out.println(key + ": " + value));
            }

            if (difference.entriesOnlyOnRight().size() > 0) {
                System.out.println("\n\nEntries only on the right\n--------------------------");
                difference.entriesOnlyOnRight().forEach((key, value) -> System.out.println(key + ": " + value));
            }

            if (difference.entriesDiffering().size() > 0) {
                System.out.println("\n\nEntries differing\n--------------------------");
                difference.entriesDiffering().forEach((key, value) -> System.out.println(key + ": " + value));
            }
        }
    }

    public static String readFile(File file, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Path.of(file.getPath()));
        return new String(encoded, encoding);
    }

    public static File locateFileWithPattern(File fileDirectory, Pattern pattern) {
        for (File file : fileDirectory.listFiles()) {
            if (pattern.matcher(file.getAbsoluteFile().getName()).matches()) {
                return file;
            }

        }
        return null;
    }


}
