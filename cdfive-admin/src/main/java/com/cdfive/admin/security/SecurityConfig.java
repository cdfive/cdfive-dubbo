package com.cdfive.admin.security;

import com.cdfive.admin.security.Md5PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author cdfive
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and();

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginProcess")
//                .defaultSuccessUrl("/main")
                .defaultSuccessUrl("/main", true)
                .failureUrl("/loginError")
                .permitAll()
                .and();

        http.authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/loginImageCheckCode").permitAll()
//                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated()
                .and();

        http.logout().logoutUrl("/logout").logoutSuccessUrl("/").and();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();
    }
}
