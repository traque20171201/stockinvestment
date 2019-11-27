package traque.com.stockinvestment.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class StockinvestmentApplicationConfiguration implements WebMvcConfigurer {

	private static final Logger log = LogManager.getLogger(StockinvestmentApplicationConfiguration.class);
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		log.info("StockinvestmentApplicationConfiguration localeChangeInterceptor start.");
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		log.info("StockinvestmentApplicationConfiguration addInterceptors start.");
		registry.addInterceptor(localeChangeInterceptor());
	}
}
