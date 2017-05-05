package utility;

public class SqlQuery {
	public static final String FIND_USER = "select * from user where username = ?";
	public static final String FIND_PSW = "select psw from user where username = ? and sid = ?";
	public static final String ADD_USER = "insert into user values(?,?,?,?,?)";
	public static final String ADD_GROUP_MEMBER = "insert into group_info values(?,?)";
	public static final String FIND_GROUP = "select * from group_info where gid = ?";
	public static final String ADD_GROUP = "insert into group_info values(?,?)";
	public static final String CHECK_SID = "select * from user where sid = ?";
	public static final String UPDATE_ADDRESS = "update group_info set git_address = ? where gid = ? ";
}
