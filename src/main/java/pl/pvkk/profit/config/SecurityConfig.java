package pl.pvkk.profit.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
	@Autowired
	DataSource dataSource;
	
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
        	.antMatchers("/user/print/**").authenticated()
        	.anyRequest().permitAll();
		
		http
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/user/print/login")
				.and()
			.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home")
                .and()
            .rememberMe()
            	.and()
            .csrf().disable();
	}
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.jdbcAuthentication()
			.dataSource(dataSource)
    		.usersByUsernameQuery("select login, password, 1 from User where login=?")
    		.authoritiesByUsernameQuery("select login, 'ROLE_USER' from User where login=?")
    		.passwordEncoder(new BCryptPasswordEncoder());
    }
}
