package com.scamshield.service;

import com.scamshield.engine.RiskEngine;
import com.scamshield.model.ScanRequest;
import com.scamshield.model.ScanResult;
import com.scamshield.storage.StorageService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Multithreaded service for asynchronous batch scanning.
 */
public class BackgroundScanService {
    private final RiskEngine riskEngine;
    private final StorageService storageService;
    private final ExecutorService executorService;

    public BackgroundScanService(RiskEngine riskEngine, StorageService storageService) {
        this.riskEngine = riskEngine;
        this.storageService = storageService;
        this.executorService = Executors.newFixedThreadPool(2);
    }

    public void submitBatchScan(List<String> batchMessages) {
        executorService.submit(() -> {
            System.out.println("\n[BACKGROUND SCAN STARTED] Processing " + batchMessages.size() + " items...");
            int scanned = 0;
            for (String message : batchMessages) {
                ScanRequest request = new ScanRequest(message, "BATCH_FILE");
                ScanResult result = riskEngine.analyze(request);
                try {
                    storageService.saveResult(result);
                    scanned++;
                } catch (Exception e) {
                    System.err.println("Error saving background result: " + e.getMessage());
                }
            }
            System.out.println("[BACKGROUND SCAN COMPLETED] Successfully scanned and saved " + scanned + " items!\n");
        });
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
