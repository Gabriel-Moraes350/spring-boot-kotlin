package com.example.eventskt.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.*


@Configuration
class MessageBundlerConfig: WebMvcConfigurer {

    @Bean
    fun messageSource(): MessageSource? {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:/messages/messages")
        messageSource.setDefaultEncoding("UTF-8")
        messageSource.setCacheSeconds(10)
        return messageSource
    }


    @Bean
    fun validator(): LocalValidatorFactoryBean? {
        val bean = LocalValidatorFactoryBean()
        messageSource()?.let { bean.setValidationMessageSource(it) }
        return bean
    }

    @Bean
    fun localeResolver(): LocaleResolver? {
        val localeResolver = SessionLocaleResolver()
        localeResolver.setDefaultLocale(Locale("pt", "BR"))
        return localeResolver
    }

    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor? {
        val lci = LocaleChangeInterceptor()
        lci.paramName = "lang"
        return lci
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeChangeInterceptor()!!)
    }

}