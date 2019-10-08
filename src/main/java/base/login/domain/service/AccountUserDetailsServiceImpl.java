/**
 *
 */
package base.login.domain.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import base.login.domain.model.Account;
import base.login.domain.model.AccountUserDetails;

@Service
public class AccountUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	AccountService accountService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Account account = accountService.findOne(username);
			UserDetails userDetails = new AccountUserDetails(account, getAuthorities());

			return userDetails;
		} catch (UsernameNotFoundException e) {
			throw new UsernameNotFoundException("user not found", e);
		}
	}

	/**
	 * Permission acquisition
	 *
	 * @param account
	 * @return Collection<GrantedAuthority>
	 */
	private Collection<GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("ROLE_USER");
	}
}
