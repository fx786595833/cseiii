package service;

import git.GitAdmin;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import bean.User;
import dao.GroupDao;

@Service
public class GroupServiceImpl implements GroupService {
	@Autowired
	private GroupDao groupDao;

	public String getGitURL(int group) {
		return groupDao.getURL(group);
	}

	public boolean uploadFiles(CommonsMultipartFile file, User user_info) {
		String path = utility.Server.REPOSITORY_PREFIX + user_info.getGid()
				+"/" +file.getOriginalFilename();
		File newFile = new File(path);
		// 通过CommonsMultipartFile的方法直接写文件（注意这个时候）
		try {
			file.transferTo(newFile);
			GitAdmin.upload(user_info);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (NoFilepatternException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
