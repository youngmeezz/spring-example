package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

import springbook.user.domain.User;

public class UserDao {
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void add(User user) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = this.dataSource.getConnection();
			ps = c.prepareStatement(
					"insert into users(id, name, password) values(?,?,?)");
			
			ps.setString(1, user.getId());
			ps.setString(2, user.getName());
			ps.setString(3, user.getPassword());
			ps.executeUpdate();
		} finally {
			close(c,ps,null);
		}		 		
	}
	
	private void close(Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
		if( rs != null ) {rs.close();}
		if( ps != null ) {ps.close();}
		if( conn != null ) {conn.close();}		
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
		// 선정한 전략 클래스의 오브젝트 생성
		StatementStrategy st = new DeleteAllStatement();
		// 컨텍스트 호출. 전략 오브젝트 전달
		jdbcContextWithStatementStrategy(st);
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
	
	
	public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = this.dataSource.getConnection();
						
			ps = stmt.makePreparedStatement(c);
			
			ps.executeUpdate();
		} catch(SQLException e) {
			throw e;
		} finally {
			if( ps!= null ) try{ps.close();}catch(SQLException e){}
			if( c != null ) try{c.close();}catch(SQLException e){}
		}	
	}

	

}
