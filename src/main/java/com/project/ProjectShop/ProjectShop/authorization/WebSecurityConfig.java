package com.project.ProjectShop.ProjectShop.authorization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomerDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
////                .antMatchers("/menu").permitAll()
////                .antMatchers("/menu", "/picture/**", "/login", "/register_form", "/save_user").permitAll()
////                .antMatchers( "/all_resorts", "/all_hotels").hasAnyAuthority("USER","ADMIN")
//                .antMatchers("/").hasAnyAuthority("customer")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().loginPage("/shop/customerLogin").permitAll()
////                .defaultSuccessUrl("/menu", true)
//
//                .and()
//                .logout().permitAll()
//                .logoutSuccessUrl("/")
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID");

    }
}
