package com.scamshield.ui;

import com.scamshield.engine.RiskEngine;
import com.scamshield.model.ScanRequest;
import com.scamshield.model.ScanResult;
import com.scamshield.service.BackgroundScanService;
import com.scamshield.storage.StorageService;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Command-Line Interface controller for user interaction.
 */
public class ScamShieldCLI {
    private final RiskEngine engine;
    private final StorageService storage;
    private final BackgroundScanService bgService;
    private final Scanner scanner;

    public ScamShieldCLI(RiskEngine engine, StorageService storage, BackgroundScanService bgService) {
        this.engine = engine;
        this.storage = storage;
        this.bgService = bgService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        System.out.println("=================================================");
        System.out.println("   🛡️  ScamShield – Fraud & Scam Assistant  🛡️   ");
        System.out.println("=================================================");

        while (running) {
            printMenu();
            System.out.print("Select an option (1-5): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleSingleScan();
                    break;
                case "2":
                    handleViewHistory();
                    break;
                case "3":
                    handleClearHistory();
                    break;
                case "4":
                    handleSimulateBatchScan();
                    break;
                case "5":
                    running = false;
                    System.out.println("\nThank you for using ScamShield! Stay safe online.");
                    bgService.shutdown();
                    break;
                default:
                    System.out.println("Invalid option! Please enter a number between 1 and 5.\n");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n---------------- MAIN MENU ----------------");
        System.out.println("1. Scan Message or Link");
        System.out.println("2. View Scan History");
        System.out.println("3. Clear Scan History");
        System.out.println("4. Run Background Batch Scan (Simulated)");
        System.out.println("5. Exit Application");
        System.out.println("-------------------------------------------");
    }

    private void handleSingleScan() {
        System.out.print("\nEnter SMS text, Email content, or URL to analyze:\n> ");
        String content = scanner.nextLine().trim();

        if (content.isEmpty()) {
            System.out.println("Error: Input content cannot be empty.");
            return;
        }

        String type = content.startsWith("http://") || content.startsWith("https://") ? "URL" : "TEXT/SMS";
        ScanRequest request = new ScanRequest(content, type);
        ScanResult result = engine.analyze(request);

        displayReport(request, result);

        try {
            storage.saveResult(result);
            System.out.println("[INFO] Scan report successfully saved to storage.");
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to save scan report: " + e.getMessage());
        }
    }

    private void displayReport(ScanRequest request, ScanResult result) {
        System.out.println("\n================ SCAN REPORT ================");
        System.out.println(" Request ID   : " + result.getRequestId());
        System.out.println(" Timestamp    : " + result.getScannedAt());
        System.out.println(" Content Type : " + request.getSourceType());
        System.out.println(" Risk Score   : " + result.getTotalScore() + " / 100");
        System.out.println(" Risk Level   : " + result.getRiskLevel() + " (" + result.getRiskLevel().getDescription() + ")");
        System.out.println("---------------------------------------------");
        System.out.println(" Triggered Indicators (" + result.getTriggeredRules().size() + "):");

        if (result.getTriggeredRules().isEmpty()) {
            System.out.println("  • No suspicious indicators detected.");
        } else {
            for (String ruleDetail : result.getTriggeredRules()) {
                System.out.println("  • " + ruleDetail);
            }
        }
        System.out.println("=============================================");
    }

    private void handleViewHistory() {
        System.out.println("\n---------------- SCAN HISTORY ----------------");
        try {
            List<String> history = storage.loadHistory();
            if (history.isEmpty()) {
                System.out.println("No past scan records found.");
            } else {
                for (String record : history) {
                    System.out.println(record);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to read scan history: " + e.getMessage());
        }
        System.out.println("----------------------------------------------");
    }

    private void handleClearHistory() {
        try {
            storage.clearHistory();
            System.out.println("\n[SUCCESS] Scan history cleared successfully.");
        } catch (Exception e) {
            System.err.println("Failed to clear history: " + e.getMessage());
        }
    }

    private void handleSimulateBatchScan() {
        List<String> sampleBatch = Arrays.asList(
            "URGENT: Claim your lottery winner prize of 5000 at http://192.168.1.50/claim immediately",
            "Hey mom, I am running late for dinner",
            "Your account is blocked! Verify PIN at bit.ly/mybank within 2 hours or legal action will follow",
            "Project report deadline is extended to next Monday"
        );
        bgService.submitBatchScan(sampleBatch);
        System.out.println("Submitted 4 batch items for background processing. You can continue using the menu!");
    }
}
