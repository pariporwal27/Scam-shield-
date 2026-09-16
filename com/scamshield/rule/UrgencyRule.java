package com.scamshield.rule;

import com.scamshield.model.ScanRequest;
import java.util.Arrays;
import java.util.List;

public class UrgencyRule implements ScamRule {
    private final List<String> urgencyPhrases;
    private String lastTriggeredDescription = "";

    public UrgencyRule() {
        this.urgencyPhrases = Arrays.asList(
            "immediately", "urgent", "within 24 hours", "within 2 hours",
            "account suspended", "action required", "legal action", "final notice"
        );
    }

    @Override
    public String getRuleName() {
        return "Urgency & Coercion Rule";
    }

    @Override
    public int evaluate(ScanRequest request) {
        String content = request.getRawContent().toLowerCase();
        int count = 0;
        StringBuilder matched = new StringBuilder();

        for (String phrase : urgencyPhrases) {
            if (content.contains(phrase)) {
                count++;
                if (matched.length() > 0) matched.append(", ");
                matched.append("'").append(phrase).append("'");
            }
        }

        int score = count * 15;
        if (score > 0) {
            this.lastTriggeredDescription = "High pressure urgency language found: " + matched.toString();
        } else {
            this.lastTriggeredDescription = "No urgent pressure language detected.";
        }

        return score;
    }

    @Override
    public String getTriggerDescription() {
        return lastTriggeredDescription;
    }
}
