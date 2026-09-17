import com.scamshield.engine.RiskEngine;
import com.scamshield.rule.KeywordRule;
import com.scamshield.rule.UrlRule;
import com.scamshield.rule.UrgencyRule;
import com.scamshield.service.BackgroundScanService;
import com.scamshield.storage.FileStorageService;
import com.scamshield.storage.StorageService;
import com.scamshield.ui.ScamShieldCLI;

public class Main {
    public static void main(String[] args) {
        
        RiskEngine engine = new RiskEngine();
        engine.registerRule(new KeywordRule());
        engine.registerRule(new UrgencyRule());
        engine.registerRule(new UrlRule());

        
        StorageService storage = new FileStorageService("data/scan_history.txt");


        BackgroundScanService bgService = new BackgroundScanService(engine, storage);

      
        ScamShieldCLI cli = new ScamShieldCLI(engine, storage, bgService);
        cli.start();
    }
}
