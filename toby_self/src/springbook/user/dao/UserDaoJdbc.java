package springbook.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import springbook.user.domain.User;
import springbook.user.exception.DuplicatedUserIdException;

public class UserDaoJdbc implements UserDao {		
	private JdbcTemplate jdbcTemplate;
	private RowMapper<User> userMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			return user;
		}
	};
		
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void add(final User user) {
		this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)",
				user.getId(), user.getName(), user.getPassword() );
	}		
	
	@Override
	public User get(String id) {
		return jdbcTemplate.queryForObject("select * from users where id = ?",new Object[] {id},userMapper);		
	}
		
	@Override
	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from users order by id", userMapper);
	}
	
	@Override
	public void deleteAll() {	
		this.jdbcTemplate.update("delete from users");
	}
	
	@Override		
	public int getCount() {
		return this.jdbcTemplate.queryForInt("select count(*) from users");		
	}
}
