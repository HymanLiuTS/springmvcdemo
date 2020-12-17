package springmvcdemo.authentication.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import springmvcdemo.authentication.service.AuthUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthUserDetailsService authUserDetailsService;
	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		/*auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("root123").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("dba").password("root123").roles("ADMIN","DBA");*/
		auth.authenticationProvider(authenticationProvider());
	
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  
	  http
	    .authorizeRequests()
	  	.antMatchers("/").permitAll()
	  	.antMatchers("/home").permitAll()
		.antMatchers("/cookie").permitAll()
		.antMatchers("/activiti/**").permitAll()
	  	.antMatchers("/user/**").permitAll()
	  	.antMatchers("/websocket/**").permitAll()
	  	.antMatchers("/hello").permitAll()
	  	.antMatchers("/group/**").hasAuthority("ADMIN")
	  	.antMatchers("/admin/**").hasAuthority("ADMIN")
	  	.antMatchers("/db/**").hasAnyAuthority("ADMIN", "DBA")
	  	.and().formLogin().loginPage("/login")
	  	.usernameParameter("ssoId").passwordParameter("password")
	  	.and().exceptionHandling().accessDeniedPage("/Access_Denied")
	  	.and().csrf().disable();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider
	      = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(authUserDetailsService);
	    authProvider.setPasswordEncoder(encoder());
	    return authProvider;
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}

}
