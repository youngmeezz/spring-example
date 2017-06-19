package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

import springbook.user.domain.User;

public class UserDao {
	private JdbcContext jdbcContext;	
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		// JdbcContext 생성(IoC)
		jdbcContext = new JdbcContext();

		// 의존 오브젝트 주입(DI)
		jdbcContext.setDataSource(dataSource);

		// 아직 jdbcContex를 적용하지 않은 메소드를 위해 저장
		this.dataSource = dataSource;		
	}
	
	/**
	 * User 등록
	 * @param user
	 * @throws SQLException
	 */
	public void add(final User user) throws SQLException {		
		this.jdbcContext.workWithStatementStrategy(
				new StatementStrategy() {
					@Override
					public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
						PreparedStatement ps = 
									c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
						ps.setString(1, user.getId());
						ps.setString(2, user.getName());
						ps.setString(3, user.getPassword());

						return ps;
					}
				}
		);
	}
		
	private void close(Connection conn, PreparedStatement ps, ResultSet rs) {		
		if( rs != null ) { try{ rs.close();}catch(SQLException e){}};
		if( ps != null ) { try{ ps.close();}catch(SQLException e){}};
		if( conn != null ) { try{ conn.close();}catch(SQLException e){}};				
	}

	public User get(String id) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = this.dataSource.getConnection();
			ps = c.prepareStatement("select * from users where id = ?");
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			User user = null;
			if( rs.next() ) {
				user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
			}			
			
			if( user == null )
				throw new EmptyResultDataAccessException(1);			
			return user;			
		} finally {
			close(c,ps,rs);
		}		
	}
	
	public void deleteAll() throws SQLException {
		this.jdbcContext.executeSql("delete from users");
	}
		
	public int getCount() throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = this.dataSource.getConnection();
			ps = c.prepareStatement("select count(*) from users");
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} finally {
			close(c,ps,rs);
		}
	}
}
