package com.scamshield.model;

public enum RiskLevel {
    SAFE("Low ti no threat detected",0,29),
    SUSPICIOUS("Moderate risk detected. Proceed with caution.", 30, 59),
    HIGH_RISK("High likelihood of scam/fraud!", 60, 84),
    CRITICAL("Dangerous scam attempt! Do not click links or share details.", 85, 100);

    private final String description;
    private final int minScore;
    private final int maxScore;

    RiskLevel(String description, int minScore, int maxScore) {
        this.description = description;
        this.minScore = minScore;
        this.maxScore = maxScore;
    }

    public String getDescription() {
        return description;
    }
    public int getMinScore() {
        return minScore;
    }
    public int getMaxScore() {
        return maxScore;
    }

    public static RiskLevel fromScore(int score) {
        if (score >= 85) return CRITICAL;
        if (score >= 60) return HIGH_RISK;
        if (score >= 30) return SUSPICIOUS;
        return SAFE;
    }

}
