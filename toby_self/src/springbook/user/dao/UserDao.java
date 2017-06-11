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
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = this.dataSource.getConnection();
			ps = c.prepareStatement("delete from users");
			ps.executeUpdate();
		} finally {
			close(c,ps,null);
		}				
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
