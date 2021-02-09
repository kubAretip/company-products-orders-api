package pl.kubaretip.cpo.api.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.TimeZone;

import static pl.kubaretip.cpo.api.config.AppConstants.TIME_ZONE_UTC;

@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    // Configuring default time zone and locale
    @PostConstruct
    public void setupDefaults() {
        TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE_UTC));
        LocaleContextHolder.setDefaultLocale(Locale.ENGLISH);
    }

    // Registration interceptor
    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(localeChangeInterceptor());
    }

    // Changing locale by param ?lang=en
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        var localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    // Configuring cookie lang
    @Bean
    public LocaleContextResolver localeResolver() {
        var cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return cookieLocaleResolver;
    }

    // Configuring ResourceBundle
    @Bean
    public MessageSource messageSource() {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        //messageSource.setUseCodeAsDefaultMessage(true); // TODO : only during development
        return messageSource;
    }

    // Configuring validation messages source
    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        var localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource());
        return localValidatorFactoryBean;
    }


}
