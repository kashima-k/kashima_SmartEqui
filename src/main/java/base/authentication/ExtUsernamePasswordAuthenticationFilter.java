/**
 *
 */
package base.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class ExtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public ExtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		// 成功時
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setTargetUrlParameter("/auth");
		successHandler.setDefaultTargetUrl("/auth");
		successHandler.setAlwaysUseDefaultTargetUrl(true);
		setAuthenticationSuccessHandler(successHandler);

		// 失敗時
		SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
		failureHandler.setDefaultFailureUrl("/login?error");
		setAuthenticationFailureHandler(failureHandler);

		// チェック
		int total  = NumberUtils.toInt(request.getParameter(Constant.TOTAL_RANDOM_NUM));
		int answer = NumberUtils.toInt(request.getParameter(Constant.ANSWER), -1);
		boolean isQuestion = isQuestion(total, answer);
		if (isQuestion) {
			failureHandler.setDefaultFailureUrl("/error/400");
			throw new AuthenticationServiceException("Incorrect answer.");
		}

		return super.attemptAuthentication(request, response);
	}

	/**
	 * 数字
	 *
	 * @param total
	 * @param answer
	 * @return boolean
	 */
	private boolean isQuestion(int total, int answer) {
		if (NumberUtils.compare(total, answer) != 0) {
			return true;
		}
		return false;
	}
}
