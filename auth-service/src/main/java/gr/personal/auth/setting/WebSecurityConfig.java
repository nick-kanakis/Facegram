package gr.personal.auth.setting;

import gr.personal.auth.service.UserService;
import gr.personal.auth.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * Created by Nick Kanakis on 3/6/2017.
 */

@Configuration
@EnableWebSecurity
@Order(101)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Configuration
    @EnableWebSecurity
    protected static class webSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserService userDetailsService;

        /*
        * Authenticate all urls except from home {/}
        * */
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/**").authorizeRequests()
                    .antMatchers("/","/login/**").permitAll()
                    .anyRequest().authenticated();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService)
                    .passwordEncoder(new BCryptPasswordEncoder());
        }

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }
}
