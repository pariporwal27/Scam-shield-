package com.scamshield.rule;

import com.scamshield.model.ScanRequest;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Rule that inspects URLs for suspicious indicators (IP addresses, dangerous TLDs, URL shorteners).
 */
public class UrlRule implements ScamRule {
    // Regex pattern for IP address hostnames (e.g. http://192.168.1.1/login)
    private static final Pattern IP_PATTERN = Pattern.compile("https?://\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}.*");
    private final List<String> suspiciousTlds;
    private final List<String> urlShorteners;
    private String lastTriggeredDescription = "";

    public UrlRule() {
        this.suspiciousTlds = Arrays.asList(".xyz", ".top", ".club", ".info", ".kim", ".work");
        this.urlShorteners = Arrays.asList("bit.ly", "tinyurl.com", "t.co", "is.gd", "cutt.ly");
    }

    @Override
    public String getRuleName() {
        return "Suspicious URL Structure Rule";
    }

    @Override
    public int evaluate(ScanRequest request) {
        String content = request.getRawContent().toLowerCase();
        int score = 0;
        StringBuilder reasons = new StringBuilder();

        // Check 1: Raw IP Address Hostname
        if (IP_PATTERN.matcher(content).find()) {
            score += 40;
            reasons.append("Raw IP address used in link (+40 points). ");
        }

        // Check 2: Suspicious TLDs
        for (String tld : suspiciousTlds) {
            if (content.contains(tld)) {
                score += 25;
                reasons.append("Suspicious Top-Level Domain '").append(tld).append("' (+25 points). ");
                break;
            }
        }

        // Check 3: URL Shortener
        for (String shortener : urlShorteners) {
            if (content.contains(shortener)) {
                score += 20;
                reasons.append("Obfuscated URL shortener '").append(shortener).append("' (+20 points). ");
                break;
            }
        }

        if (score > 0) {
            this.lastTriggeredDescription = reasons.toString().trim();
        } else {
            this.lastTriggeredDescription = "No suspicious URL patterns detected.";
        }

        return score;
    }

    @Override
    public String getTriggerDescription() {
        return lastTriggeredDescription;
    }
}
