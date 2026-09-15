package com.scamshield.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents an incoming message or URL submitted for scam detection.
 */
public class ScanRequest {
    private final String requestId;
    private final String rawContent;
    private final String sourceType; // e.g., "SMS", "EMAIL", "URL"
    private final LocalDateTime timestamp;

    public ScanRequest(String rawContent, String sourceType) {
        this.requestId = UUID.randomUUID().toString();
        this.rawContent = rawContent;
        this.sourceType = sourceType;
        this.timestamp = LocalDateTime.now();
    }

    public String getRequestId() {
        return requestId;
    }

    public String getRawContent() {
        return rawContent;
    }

    public String getSourceType() {
        return sourceType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "ScanRequest{" +
                "requestId='" + requestId + '\'' +
                ", sourceType='" + sourceType + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
