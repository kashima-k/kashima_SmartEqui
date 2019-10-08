/**
 *
 */
package base.login.domain.service;

import base.login.domain.model.Account;

public interface AccountService {
	/**
	 * Find one account information with username.
	 *
	 * @param username
	 * @return Account
	 */
	Account findOne(String username);
}
