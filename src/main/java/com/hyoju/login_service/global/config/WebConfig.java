package com.hyoju.login_service.global.config;

import com.hyoju.login_service.global.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer { // 인터셉터를 어디에 세운지 설정

    private final LoginCheckInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .order(1)
                .addPathPatterns("/**") // 모든 곳을 검문
                .excludePathPatterns(
                        "/", "/index.html", "/join.html",  "/member/join", "/member/login", "/logout",
                        "/css/**", "/js/**", "/images/**", "/fonts/**",
                        "/vendor/**", "/*.ico", "/favicon.ico", "/error", "/test.html",  "/h2-console/**"  );
    }


}
