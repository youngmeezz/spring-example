package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class NConnectionMaker implements ConnectionMaker {

	@Override
	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		// N ���� �������� Connection �� �����ϴ� �ڵ�
		return null;
	}
	

}
