package dao;

public interface GroupDao {
	public boolean isGroupExist(int gid);
	public boolean createGroup(int id,String address);
	public boolean updateAddress(int gid,String address);
	public String getURL(int group);
}
