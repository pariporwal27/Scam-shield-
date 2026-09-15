package com.scamshield.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds the output of a scan performed by the RiskEngine.
 */
public class ScanResult {
    private final String requestId;
    private final int totalScore;
    private final RiskLevel riskLevel;
    private final List<String> triggeredRules;
    private final LocalDateTime scannedAt;

    public ScanResult(String requestId, int totalScore, List<String> triggeredRules) {
        this.requestId = requestId;
        // Clamp score between 0 and 100
        this.totalScore = Math.min(100, Math.max(0, totalScore));
        this.riskLevel = RiskLevel.fromScore(this.totalScore);
        this.triggeredRules = new ArrayList<>(triggeredRules);
        this.scannedAt = LocalDateTime.now();
    }

    public String getRequestId() {
        return requestId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public List<String> getTriggeredRules() {
        return Collections.unmodifiableList(triggeredRules);
    }

    public LocalDateTime getScannedAt() {
        return scannedAt;
    }

    @Override
    public String toString() {
        return "ScanResult{" +
                "requestId='" + requestId + '\'' +
                ", score=" + totalScore +
                ", riskLevel=" + riskLevel +
                ", triggeredRules=" + triggeredRules.size() +
                '}';
    }
}
