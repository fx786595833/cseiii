package dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GroupDaoImp implements GroupDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean createGroup(int id, String address) {
		int i = jdbcTemplate.update(utility.SqlQuery.ADD_GROUP, new Object[] {
				id, address });
		if (i > 0) {
			return true;
		}
		return false;
	}

	public boolean isGroupExist(int gid) {
		Object args[] = new Object[] { gid };
		List<Map<String, Object>> result = jdbcTemplate.queryForList(
				utility.SqlQuery.FIND_GROUP, args);
		int size = result.size();
		if (size == 1) {
			return true;
		}
		return false;
	}

	public boolean updateAddress(int gid, String address) {
		int i = jdbcTemplate.update(utility.SqlQuery.UPDATE_ADDRESS,
				new Object[] { address, gid });
		if (i > 0) {
			return true;
		}
		return false;
	}

	public String getURL(int group) {
		Object args[] = new Object[] { group };
		List<Map<String, Object>> result = jdbcTemplate.queryForList(
				utility.SqlQuery.FIND_GROUP, args);
		return result.get(0).get("git_address").toString();
	}

}
