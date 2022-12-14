package com.droplet.config;

import com.droplet.filter.JwtAuthenticationTokenFilter;
import com.droplet.handler.security.AccessDeniedHandlerImpl;
import com.droplet.handler.security.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    private final AuthenticationEntryPointImpl authenticationEntryPointImpl;

    private final AccessDeniedHandlerImpl accessDeniedHandlerImpl;

    @Autowired
    public SecurityConfig(JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter,
                          AuthenticationEntryPointImpl authenticationEntryPointImpl,
                          AccessDeniedHandlerImpl accessDeniedHandlerImpl) {
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
        this.authenticationEntryPointImpl = authenticationEntryPointImpl;
        this.accessDeniedHandlerImpl = accessDeniedHandlerImpl;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // ??????csrf
                .csrf().disable()
                // ?????????session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // ??????????????????????????????
                .antMatchers("/user/login").anonymous()
                // .antMatchers("/logout").authenticated()
                // .antMatchers("/user/userInfo").authenticated()
                .anyRequest().authenticated();

        // ?????????????????????
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPointImpl)
                .accessDeniedHandler(accessDeniedHandlerImpl);

        http.logout().disable();
        // ???JwtAuthenticationTokenFilter?????????SpringSecurity????????????
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // ????????????
        http.cors();
        return http.build();
    }
}
