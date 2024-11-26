package com.github.angry_bird;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LevelStatusManager {
    private static final String FILE_NAME = "level_status.txt";

    // Write function: Updates the status of a specific level in the file
    public static void writeLevelStatus(String level, boolean status) {
        Map<String, Boolean> levelStatus = readAllLevels(); // Read existing statuses
        levelStatus.put(level, status); // Update or add the specific level's status

        // Write the updated statuses back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, Boolean> entry : levelStatus.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Read function: Reads the status of a specific level from the file
    public static boolean readLevelStatus(String level) {
        Map<String, Boolean> levelStatus = readAllLevels(); // Read all statuses
        return levelStatus.getOrDefault(level, false); // Return the status or false if not found
    }

    // Helper method: Reads all levels' statuses from the file into a Map
    private static Map<String, Boolean> readAllLevels() {
        Map<String, Boolean> levelStatus = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    levelStatus.put(parts[0], Boolean.parseBoolean(parts[1]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return levelStatus;
    }
}