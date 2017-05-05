package git;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabAccessLevel;
import org.gitlab.api.models.GitlabGroup;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabSession;

import bean.User;

public class GitAdmin {
	private static GitlabAPI api = GitlabAPI.connect(
			utility.Server.GIT_ADDRESS, utility.Server.ADMIN_TOKEN);

	public static boolean createUser(int id) {
		try {
			api.createUser(id + utility.Server.EMAIL_SUFFIX, id + "", id + "",
					id + "", "", "", "", "", 10, "", "", "", false, true, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean createGroup(int sid, int gid) {
		GitlabAPI memberApi = getConnection(sid);
		if (memberApi != null) {
			try {
				GitlabGroup group = memberApi.createGroup(
						utility.Server.PROJECT_PREFIX + gid,
						utility.Server.PROJECT_PREFIX + gid);
				GitlabProject project =  memberApi.createProject(utility.Server.PROJECT_PREFIX + gid,
						group.getId(), "", true, true, true, true, true, true,
						0, "");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}

	public static boolean addGroupMember(String path, int sid) {
		GitlabAPI memberApi = getConnection(sid);
		if (memberApi != null) {
			try {
				int gid = api.getGroup(path).getId();
				api.addGroupMember(gid, memberApi.getUser().getId(),
						GitlabAccessLevel.Developer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	private static GitlabAPI getConnection(int sid) {
		GitlabSession api;
		GitlabAPI gitlabAPI = null;
		try {
			api = GitlabAPI.connect(utility.Server.GIT_ADDRESS, sid + "", sid
					+ "");
			gitlabAPI = GitlabAPI.connect(utility.Server.GIT_ADDRESS,
					api.getPrivateToken());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gitlabAPI;
	}
	
	public static boolean createRepository(User user,String address){
		Git git;
		try {
			git = Git.cloneRepository().setURI(address).setDirectory(new File(utility.Server.REPOSITORY_PREFIX
					+ user.getGid())).setCredentialsProvider(new UsernamePasswordCredentialsProvider(user.getSid()+"",user.getSid()+"")).call();
			String message = "initial";
			upload(git,user,message);
			CreateBranchCommand command = git.branchCreate();
			command.setName("documents");
			command.call();
			message = "create branch";
			upload(git, user, message);
		} catch (InvalidRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private static void upload(Git git, User user, String message) throws NoFilepatternException, GitAPIException {
		AddCommand addCommand = git.add();
		addCommand.addFilepattern(".");
		addCommand.call();
		CommitCommand commitCommand = git.commit();
		commitCommand.setMessage(message);
		commitCommand.setAllowEmpty(true);
		commitCommand.call();
		PushCommand pushCommand = git.push();
		CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(
				user.getSid()+"",user.getSid()+"");
		pushCommand.setCredentialsProvider(credentialsProvider);
		pushCommand.setForce(true).setPushAll();
		pushCommand.call();
	}

	public static void upload(User user_info) throws NoFilepatternException, GitAPIException, IOException {
		Git git = Git.open(new File(utility.Server.REPOSITORY_PREFIX + user_info.getGid()));
		String message = "documents commit";
		CheckoutCommand checkoutCommand = git.checkout();
		checkoutCommand.setName("documents");
		checkoutCommand.call();
		upload(git,user_info,message);
	}
}
