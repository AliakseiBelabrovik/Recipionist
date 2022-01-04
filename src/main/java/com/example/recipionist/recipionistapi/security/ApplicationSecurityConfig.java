package com.example.recipionist.recipionistapi.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //configuration of the security and session management
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //Requests must be authenticated with the form based authentication
        http
                .csrf().disable()
                .authorizeRequests()
                //all users must be able to reach the following pages
                    .antMatchers("/", "/main", "/recipes", "/cocktails", "/css/*", "/jsfiles/*", "/images/*", "/api/**", "/static/**", "weather").permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                .formLogin() //to use the form based auth for everything else
                    .loginPage("/session/login").permitAll() //Use our own login page and grant access to it to everybody
                    .defaultSuccessUrl("/session/profile") //redirect after successful login
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .and()
                    .rememberMe() //defaults to 2 weeks
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                    .logoutUrl("/session/logout") //url to logout
                //if csrf is enabled, the logout must be a POST HTTP Request
                //if csrf is disabled, the logout mus be a GET HTTP Request. Since we disabled the csrf, we have to write the
                //below line with logoutRequestMatcher. If you enable csrf, just delete this lise.
                    .logoutRequestMatcher(new AntPathRequestMatcher("/session/logout", "GET"))
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/main"); //if successfully logged out -> return to the main page

    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails aliaksei = User.builder()
                .username("aliaksei@example.com")
                .password(passwordEncoder.encode("campuswien"))
                .roles(ApplicationUserRole.ADMIN.name())
                .build();
        UserDetails andy = User.builder()
                .username("andy@example.com")
                .password(passwordEncoder.encode("campuswien2"))
                .roles(ApplicationUserRole.ADMIN.name())
                .build();
        UserDetails injacio = User.builder()
                .username("injacio@example.com")
                .password(passwordEncoder.encode("campuswien3"))
                .roles(ApplicationUserRole.ADMIN.name())
                .build();
        UserDetails stefan = User.builder()
                .username("stefan@example.com")
                .password(passwordEncoder.encode("campuswien4"))
                .roles(ApplicationUserRole.ADMIN.name())
                .build();


        return new InMemoryUserDetailsManager(aliaksei, andy, injacio, stefan);


    }
}
