package com.scamshield.storage;

import com.scamshield.model.ScanResult;

import java.io.IOException;
import java.util.List;

public interface StorageService {
    void saveResult(ScanResult result) throws IOException;
    List<String> loadHistory() throws IOException;
    void clearHistory() throws IOException;
}
