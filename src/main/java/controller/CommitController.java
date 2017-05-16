package controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import bean.User;
import service.GroupService;
import service.UserService;

@Controller
public class CommitController {
	private static final Log logger = LogFactory.getLog(CommitController.class);
	@Autowired
	UserService service;
	@Autowired
	GroupService groupService;

	// 提交作业
	@RequestMapping("/main")
	public String main(HttpServletRequest request, HttpSession session,Model model)
			throws IOException {
		return "main";

	}

	// 个人信息
	@RequestMapping("/person")
	public String person(HttpServletRequest request,HttpSession session, Model model)
			throws IOException {
		String uname = "用户名:" + session.getAttribute("username");
		String password = "密码:" + session.getAttribute("password");
		String name = "姓名:" + session.getAttribute("name");
		String student = "学号:" + session.getAttribute("studentid");
		String group = "分组:" + session.getAttribute("groupid");
		String gitlab = "项目地址:" + session.getAttribute("gitlab");
		model.addAttribute("username", uname);
		model.addAttribute("password", password);
		model.addAttribute("name", name);
		model.addAttribute("studentid", student);
		model.addAttribute("group", group);
		model.addAttribute("gitlab", gitlab);
		return "person";

	}

	// 上传文件
	@RequestMapping("/upload")
	public String fileUpload(@RequestParam("file") CommonsMultipartFile file,
			@RequestParam("type") String type, HttpServletRequest request,
			Model model, @RequestParam("username") String username)
			throws IOException {
		// String tem = "";
		// if (type.equals("0") || type.equals("1")) {
		// tem = "Document";
		// } else {
		// tem = "Testcase";
		// }
		User user_info = service.getUserInfo(username);

		groupService.uploadFiles(file, user_info);
		// 用户名
		request.setAttribute("person", user_info.getUsername());
		// 学号
		request.setAttribute("number", user_info.getSid());
		model.addAttribute("username", user_info.getUsername());
		model.addAttribute("password", user_info.getPassword());
		model.addAttribute("name", user_info.getName());
		model.addAttribute("studentid", user_info.getSid());
		model.addAttribute("gid", user_info.getGid());
		return "main_success";
	}

}
