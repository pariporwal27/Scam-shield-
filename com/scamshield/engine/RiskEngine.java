package com.scamshield.engine;

import com.scamshield.model.ScanRequest;
import com.scamshield.model.ScanResult;
import com.scamshield.rule.ScamRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RiskEngine {
    private final List<ScamRule> rules;

    public RiskEngine() {
        this.rules = new ArrayList<>();
    }

    public void registerRule(ScamRule rule) {
        if (rule != null) {
            this.rules.add(rule);
        }
    }

    public ScanResult analyze(ScanRequest request) {
        int totalScore = 0;
        List<String> triggeredRules = new ArrayList<>();

        for (ScamRule rule : rules) {
            int scoreContribution = rule.evaluate(request);
            if (scoreContribution > 0) {
                totalScore += scoreContribution;
                triggeredRules.add(rule.getRuleName() + ": " + rule.getTriggerDescription());
            }
        }

        return new ScanResult(request.getRequestId(), totalScore, triggeredRules);
    }

    public List<ScamRule> getRegisteredRules() {
        return Collections.unmodifiableList(rules);
    }
}
