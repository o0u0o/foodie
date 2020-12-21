package com.o0u0o.config;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 解决cookie根域名设置问题
 * @author mac
 * @date 2020/12/16 9:21 下午
 */
@Configuration
public class CookieConfig {

    /**
     * 解决问题：
     * There was an unexpected error (type=Internal Server Error, status=500).
     * An invalid domain [.xxx.com] was specified for this cookie
     *
     * @return
     */
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
        return (factory) -> factory.addContextCustomizers(
                (context) -> context.setCookieProcessor(new LegacyCookieProcessor()));
    }
}
