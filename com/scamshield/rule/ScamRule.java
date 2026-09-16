package com.scamshield.rule;

import com.scamshield.model.ScanRequest;

public interface ScamRule {
  
    String getRuleName();

    int evaluate(ScanRequest request);

    String getTriggerDescription();
}
