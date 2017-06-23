package springbook.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import springbook.user.domain.User;

public class UserDao {		
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
	
	/**
	 * User ���
	 * @param user
	 * @throws SQLException
	 */
	public void add(final User user) throws SQLException {
		this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)",user.getId(), user.getName(), user.getPassword() );		
	}
		
	
	public User get(String id) throws SQLException {
		return jdbcTemplate.queryForObject("select * from users where id = ?",new Object[] {id},userMapper);		
	}
		
	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from users order by id", userMapper);
	}
	
	public void deleteAll() throws SQLException {		
//		this.jdbcTemplate.update( new PreparedStatementCreator() {
//			@Override
//			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {				
//				return con.prepareStatement("delete from users");
//			}
//			
//		});		
		this.jdbcTemplate.update("delete from users");
	}
		
	public int getCount() throws SQLException {
//		return this.jdbcTemplate.query(
//			new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//					return con.prepareStatement("select count(*) from users");
//				}
//			}, new ResultSetExtractor<Integer>() {
//				@Override
//				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
//					rs.next();
//					return rs.getInt(1);
//				}
//		});		
		return this.jdbcTemplate.queryForInt("select count(*) from users");		
	}
}
