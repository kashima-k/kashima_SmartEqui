/**
 *
 */
package base.login.domain.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AccountUserDetails extends User {

	private static final long serialVersionUID = 1L;

	private final Account account;

	public AccountUserDetails(Account account, Collection<GrantedAuthority> authorities) {

		super(account.getUsername(), account.getPassword(), account.isEnabled(), true, true, true, authorities);
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}
}