package service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import bean.User;

public interface GroupService {
	public String getGitURL(int group);

	public boolean uploadFiles(CommonsMultipartFile file, User user_info);
}
