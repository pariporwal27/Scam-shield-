package com.scamshield.rule;

import com.scamshield.model.ScanRequest;

/**
 * Strategy interface that all scam detection rules must implement.
 */
public interface ScamRule {
    /**
     * Unique name of the rule for reporting purposes.
     */
    String getRuleName();

    /**
     * Evaluates a scan request and returns a risk score contribution.
     * @param request The incoming scan request containing raw message or URL.
     * @return An integer risk score (e.g., 0 to 40 points).
     */
    int evaluate(ScanRequest request);

    /**
     * Detailed explanation of why the rule triggered.
     */
    String getTriggerDescription();
}
