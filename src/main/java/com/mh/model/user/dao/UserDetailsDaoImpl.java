package com.mh.model.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Repository;

import com.mh.model.user.UserAttempts;

@Repository
public class UserDetailsDaoImpl extends JdbcDaoSupport implements
		UserDetailsDao {
	private static final String SQL_USERS_UPDATE_LOCKED = "UPDATE users SET accountNonLocked = ? WHERE username = ?";
	private static final String SQL_USERS_COUNT = "SELECT count(*) FROM users WHERE username = ?";

	private static final String SQL_USER_ATTEMPTS_GET = "SELECT * FROM user_attempts WHERE username = ?";
	private static final String SQL_USER_ATTEMPTS_INSERT = "INSERT INTO user_attempts (username, attempts, lastModified) VALUES(?,?,?)";
	private static final String SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS = "UPDATE user_attempts SET attempts = attempts + 1, lastModified = ? WHERE username = ?";
	private static final String SQL_USER_ATTEMPTS_RESET_ATTEMPTS = "UPDATE user_attempts SET attempts = 0 WHERE username = ?";

	private static final int MAX_ATTEMPTS = 3;

	@Autowired
	private DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Override
	public void updateFailAttempts(String username) {
		UserAttempts user = this.getUserAttempts(username);
		if (user == null) {
			if (isUserExists(username)) {
				this.getJdbcTemplate().update(SQL_USER_ATTEMPTS_INSERT,
						new Object[] { username, 1, new Date() });
			}
		} else {
			if (isUserExists(username)) {
				this.getJdbcTemplate().update(
						SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS,
						new Object[] { new Date(), username });
			}
			if (user.getAttempts() + 1 >= MAX_ATTEMPTS) {
				this.getJdbcTemplate().update(SQL_USERS_UPDATE_LOCKED,
						new Object[] { false, username });
				throw new LockedException("User Account is locked!");
			}
		}

	}

	@Override
	public void resetFailAttempts(String username) {
		this.getJdbcTemplate().update(SQL_USER_ATTEMPTS_RESET_ATTEMPTS,
				new Object[] { username });
		this.getJdbcTemplate().update(SQL_USERS_UPDATE_LOCKED,
				new Object[] { true, username });
	}

	@Override
	public UserAttempts getUserAttempts(String username) {
		try {
			UserAttempts userAttempts = getJdbcTemplate().queryForObject(
					SQL_USER_ATTEMPTS_GET, new Object[] { username },
					new RowMapper<UserAttempts>() {
						public UserAttempts mapRow(ResultSet rs, int rowNum)
								throws SQLException {

							UserAttempts user = new UserAttempts();
							user.setId(rs.getInt("id"));
							user.setUsername(rs.getString("username"));
							user.setAttempts(rs.getInt("attempts"));
							user.setLastModified(rs.getDate("lastModified"));

							return user;
						}

					});
			return userAttempts;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private boolean isUserExists(String username) {
		boolean result = false;
		int count = this.getJdbcTemplate().queryForObject(SQL_USERS_COUNT,
				new Object[] { username }, Integer.class);
		if (count > 0) {
			result = true;
		}
		return result;
	}
}
