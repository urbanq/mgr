package pl.edu.agh;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.valkyriercp.application.splash.DefaultSplashScreenConfig;
import org.valkyriercp.application.splash.ProgressSplashScreen;
import org.valkyriercp.application.splash.SplashScreen;

@Configuration
public class SplashScreenConfig extends DefaultSplashScreenConfig {
    @Override
    public SplashScreen splashScreen() {
        ProgressSplashScreen progressSplashScreen = new ProgressSplashScreen();
        progressSplashScreen.setImageResourcePath(new ClassPathResource("/pl/edu/agh/images/splash/optimization.jpg"));
        return progressSplashScreen;
    }
}
