package controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

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

	// �ύ��ҵ
	@RequestMapping("/main")
	public String main(HttpServletRequest request, Model model)
			throws IOException {
		String username = (String) request.getParameter("id");
		User user_info = service.getUserInfo(username);
		// �û���
		request.setAttribute("person", user_info.getUsername());
		// ѧ��
		request.setAttribute("number", user_info.getSid());
		model.addAttribute("username", user_info.getUsername());
		model.addAttribute("password", user_info.getPassword());
		model.addAttribute("name", user_info.getName());
		model.addAttribute("sid", user_info.getSid());
		model.addAttribute("gid", user_info.getGid());
		return "main";

	}

	// ������Ϣ
	@RequestMapping("/person")
	public String person(HttpServletRequest request, Model model)
			throws IOException {
		String username = (String) request.getParameter("id");
		User user_info = service.getUserInfo(username);
		String address = groupService.getGitURL(user_info.getGid());
		// �û���
		request.setAttribute("person", user_info.getUsername());
		// ѧ��
		request.setAttribute("number", user_info.getSid());
		String uname = "�û���:" + user_info.getUsername();
		String password = "����:" + user_info.getPassword();
		String name = "����:" + user_info.getName();
		String student = "ѧ��:" + user_info.getSid();
		String group = "����:" + user_info.getGid();
		model.addAttribute("username", uname);
		model.addAttribute("password", password);
		model.addAttribute("name", name);
		model.addAttribute("studentid", student);
		model.addAttribute("group", group);
		model.addAttribute("gitlab", address);
		return "person";

	}

	// �ϴ��ļ�
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
		// �û���
		request.setAttribute("person", user_info.getUsername());
		// ѧ��
		request.setAttribute("number", user_info.getSid());
		model.addAttribute("username", user_info.getUsername());
		model.addAttribute("password", user_info.getPassword());
		model.addAttribute("name", user_info.getName());
		model.addAttribute("studentid", user_info.getSid());
		model.addAttribute("gid", user_info.getGid());
		return "main_success";
	}

}
