import com.scamshield.engine.RiskEngine;
import com.scamshield.rule.KeywordRule;
import com.scamshield.rule.UrlRule;
import com.scamshield.rule.UrgencyRule;
import com.scamshield.service.BackgroundScanService;
import com.scamshield.storage.FileStorageService;
import com.scamshield.storage.StorageService;
import com.scamshield.ui.ScamShieldCLI;

/**
 * Application Entry Point. Bootstraps dependencies and starts CLI.
 */
public class Main {
    public static void main(String[] args) {
        // 1. Initialize Engine & Register Rules (Strategy Pattern)
        RiskEngine engine = new RiskEngine();
        engine.registerRule(new KeywordRule());
        engine.registerRule(new UrgencyRule());
        engine.registerRule(new UrlRule());

        // 2. Initialize Persistence Storage
        StorageService storage = new FileStorageService("data/scan_history.txt");

        // 3. Initialize Background Scan Service (Multithreading)
        BackgroundScanService bgService = new BackgroundScanService(engine, storage);

        // 4. Start CLI Menu
        ScamShieldCLI cli = new ScamShieldCLI(engine, storage, bgService);
        cli.start();
    }
}
