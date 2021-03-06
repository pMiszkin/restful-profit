package pl.pvkk.profit.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Qualifier("dataSource")
	@Autowired
	DataSource dataSource;
	
	//TODO on the second application startup i got a warn "Encoded password does not look like BCrypt"
	// PROBABLY because there was another PasswordEncoder Bean
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
        	.antMatchers("/user/print/**", "user/register").authenticated()
        	.antMatchers("/login").anonymous()
        	.anyRequest().permitAll()
        	.and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
		
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
	}
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.jdbcAuthentication()
			.dataSource(dataSource)
    		.usersByUsernameQuery("select login, password, 1 from User_account where login=?")
    		.authoritiesByUsernameQuery("select login, 'ROLE_USER' from User_account where login=?")
    		.passwordEncoder(passwordEncoder());
    }
    
    private CsrfTokenRepository csrfTokenRepository() {
    	HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
    	repository.setHeaderName("X-XSRF-TOKEN");
    	return repository;
    }
}
