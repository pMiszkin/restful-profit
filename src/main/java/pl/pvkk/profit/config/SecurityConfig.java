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
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

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
        	.antMatchers("/login").anonymous()
        	.anyRequest().permitAll()
        	.and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);;
		
		http
			.formLogin()
				.loginPage("/login")
				.and()
			.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
            .rememberMe()
            	.and()
            .csrf().csrfTokenRepository(csrfTokenRepository());
		
		/*http.addFilterAfter(new CsrfHeaderFilter(), CsrfHeaderFilter.class);*/
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
    
    private CsrfTokenRepository csrfTokenRepository() {
    	HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
    	repository.setHeaderName("X-XSRF-TOKEN");
    	return repository;
    }
}
