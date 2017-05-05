package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import service.UserService;
import bean.User;

@org.springframework.stereotype.Controller
public class UserController {
	private static final Log logger = LogFactory.getLog(UserController.class);
	@Autowired
	private UserService service;

	@RequestMapping(value = {"","sign_in"})
	public String signIn(Model model) {
		logger.info("sign in called");
		model.addAttribute("user", new User());
		return "login";
	}

	// ��¼
	@RequestMapping("/login")
	public String login(User user, HttpServletResponse response,
			HttpServletRequest request, Model model) throws IOException {
		boolean result = service.checkUserInfo(user);
		if (result == false) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script language=\"javascript\">alert('�û������������!');</script>");
			return "login";
		} else {
			User user_info = service.getUserInfo(user.getUsername());
			// �û���
			request.setAttribute("person", user.getUsername());
			// ѧ��
			request.setAttribute("number", user_info.getSid());
			model.addAttribute("username", user.getUsername());
			model.addAttribute("password", user.getPassword());
			model.addAttribute("name", user_info.getName());
			model.addAttribute("sid", user_info.getSid());
			model.addAttribute("gid", user_info.getGid());
			return "main";
		}
	}

	// ��¼ҳ��תע��
	@RequestMapping("/register-turn")
	public String register_turn(Model model) {
		model.addAttribute("user_register", new User());
		return "register";

	}

	@RequestMapping("/register")
	public String register(User user_register, HttpServletResponse response, Model model)
			throws IOException {
		String result = service.addUser(user_register);
		// if (result.equals("����Ϊ��")) {
		// response.setContentType("text/html;charset=utf-8");
		// PrintWriter out = response.getWriter();
		// model.addAttribute("username", username);
		// model.addAttribute("password", password);
		// model.addAttribute("name", name);
		// model.addAttribute("studentid", studentid);
		// return "register";
		if (result.equals("username_sid_failed")) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script language=\"javascript\">alert('�û�����ѧ���ѱ�ע��,��ֱ�ӵ�¼!');window.location.href='/TSS-SpringMVC/login.jsp'</script>");
			return null;
		} else {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script language=\"javascript\">alert('ע��ɹ�,���¼!');window.location.href='/TSS-SpringMVC/login.jsp'</script>");
			return null;
		}
	}

	@RequestMapping("/login-turn")
	public String login_turn(HttpServletResponse response) throws IOException {
		return "login";
	}
}
