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
		http.authorizeRequests().anyRequest().permitAll();
		
		http
			.csrf().disable()
			.formLogin()
				.loginPage("/user/login")
				.defaultSuccessUrl("/user/print")
				.and()
			.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");				
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
    
    
    
    
    
    
    
    
    
    
    
