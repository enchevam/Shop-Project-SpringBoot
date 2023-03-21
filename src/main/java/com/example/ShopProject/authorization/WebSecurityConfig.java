package com.example.ShopProject.authorization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
        return new UserDetailsServiceImpl();
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
        System.out.println("autheticated user from WebCesurity");

        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers( "/assets/**","/css/**","/js/**","/pictures/**","/shop","/shop/customerLogin","/shop/employeeLogin","/shop/register",
                        "/shop/all","/cart/**","/order/**","/shop/accountDetails","/","/shop/updateAddress","/shop/customerOrders","/shop/out").permitAll()
                .antMatchers( "/shop/home","/shop/products","shop/employees","/shop/orders/**","/shop/editProduct/**").hasAuthority("EMPLOYEE")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/shop/employeeLogin")
                .usernameParameter("id")
                .defaultSuccessUrl("/shop/home", true)
                .permitAll()

                .and()
                .logout().permitAll()
                .logoutSuccessUrl("/shop")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

    }
}
