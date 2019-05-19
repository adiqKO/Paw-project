package pl.test.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return passwordEncoder;
    }

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                .antMatchers("/public/**").permitAll()
                    .antMatchers("/error").permitAll()
                    .antMatchers("/register").permitAll()
                .antMatchers("/items").permitAll()
                    .antMatchers("/item/show/**").permitAll()
                   // .antMatchers("/users").hasRole("ADMIN")
                    .antMatchers("/products").hasRole("ADMIN")
                    .antMatchers("/product/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin()
                    .loginPage("/login")
                   // .successForwardUrl("/")
                    .failureUrl("/login?error=true")
                    .permitAll()
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                .and().exceptionHandling()
                    .accessDeniedPage("/error");

    }

}