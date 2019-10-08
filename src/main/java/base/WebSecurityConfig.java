/**
 *
 */
package base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import base.authentication.ExtUsernamePasswordAuthenticationFilter;
import base.login.domain.service.AccountUserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AccountUserDetailsServiceImpl userDetailsService;

	/**
	 * Password encoder settings
	 *
	 * @return PasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();
		return passwordEncoder;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**", "/css/**", "/javascript/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterAt(new ExtUsernamePasswordAuthenticationFilter(authenticationManagerBean()),
				UsernamePasswordAuthenticationFilter.class);
		http.authorizeRequests().antMatchers("/login", "/error/*").permitAll().anyRequest().authenticated();
		http.formLogin().loginPage("/login");
		http.logout().logoutSuccessUrl("/login?logout").invalidateHttpSession(true).permitAll();
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

}
