package traque.com.stockinvestment;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class StockinvestmentApplication {

	private static final Logger log = LogManager.getLogger(StockinvestmentApplication.class);
	
	public static void main(String[] args) {
		log.info("StockinvestmentApplication main start.");
		SpringApplication.run(StockinvestmentApplication.class, args);
	}	

	@Bean
	public LocaleResolver localeResolver() {
		log.info("StockinvestmentApplication localeResolver start.");
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(Locale.US);
		return sessionLocaleResolver;
	}
}
