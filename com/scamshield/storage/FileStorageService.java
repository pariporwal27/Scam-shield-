package com.scamshield.storage;

import com.scamshield.model.ScanResult;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Text-file based persistence implementation using FileReader/FileWriter.
 */
public class FileStorageService implements StorageService {
    private final String filePath;

    public FileStorageService(String filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

    private void ensureFileExists() {
        File file = new File(filePath);
        try {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not initialize storage file: " + e.getMessage());
        }
    }

    @Override
    public void saveResult(ScanResult result) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String record = String.format("[%s] ID: %s | Score: %d/100 | Level: %s | Triggers: %d",
                    result.getScannedAt(),
                    result.getRequestId(),
                    result.getTotalScore(),
                    result.getRiskLevel(),
                    result.getTriggeredRules().size());
            writer.write(record);
            writer.newLine();
        }
    }

    @Override
    public List<String> loadHistory() throws IOException {
        List<String> history = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return history;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.add(line);
            }
        }
        return history;
    }

    @Override
    public void clearHistory() throws IOException {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            writer.write("");
        }
    }
}
