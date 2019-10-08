package base.login.app;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestMapping;

import base.authentication.Constant;

@Controller
public class LoginController {

	@RequestMapping("/login")
	private String login(Model model) {

		Integer[] num = new Integer[3];
		num[0] = createRandomNumber();
		num[1] = createRandomNumber();
		num[2] = createRandomNumber();

		// question
		String question = StringUtils.join(num, " + ");
		model.addAttribute("question", question);
		// total random number
		int sum = num[0] + num[1] + num[2];
		model.addAttribute(Constant.TOTAL_RANDOM_NUM, sum);

		return "login";
	}

	@RequestMapping({ "/", "/auth" })
	private String auth(Model model) {
		return "success";
	}

	@RequestMapping("/error/400")
	private void missingParameter() throws MissingServletRequestParameterException {
		throw new MissingServletRequestParameterException("parameter error", "Missing request parameter");
	}

	private Integer createRandomNumber() {
		Random r = new Random();
		Integer num = r.nextInt(9) + 1;
		return num;
	}
}
