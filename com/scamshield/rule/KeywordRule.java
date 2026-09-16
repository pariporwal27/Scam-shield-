package com.scamshield.rule;

import com.scamshield.model.ScanRequest;
import java.util.HashMap;
import java.util.Map;

public class KeywordRule implements ScamRule {
    private final Map<String, Integer> keywordWeights;
    private String lastTriggeredDescription = "";

    public KeywordRule() {
        this.keywordWeights = new HashMap<>();
    
        keywordWeights.put("lottery", 25);
        keywordWeights.put("winner", 20);
        keywordWeights.put("account blocked", 30);
        keywordWeights.put("verify pin", 35);
        keywordWeights.put("otp", 25);
        keywordWeights.put("claim prize", 30);
        keywordWeights.put("bank transfer", 20);
        keywordWeights.put("refund", 15);
    }

    @Override
    public String getRuleName() {
        return "Keyword Analysis Rule";
    }

    @Override
    public int evaluate(ScanRequest request) {
        String content = request.getRawContent().toLowerCase();
        int score = 0;
        StringBuilder matched = new StringBuilder();

        for (Map.Entry<String, Integer> entry : keywordWeights.entrySet()) {
            if (content.contains(entry.getKey())) {
                score += entry.getValue();
                if (matched.length() > 0) matched.append(", ");
                matched.append(entry.getKey()).append(" (+").append(entry.getValue()).append(")");
            }
        }

        if (score > 0) {
            this.lastTriggeredDescription = "Matched scam keywords: " + matched.toString();
        } else {
            this.lastTriggeredDescription = "No suspicious keywords detected.";
        }

        return score;
    }

    @Override
    public String getTriggerDescription() {
        return lastTriggeredDescription;
    }
}
