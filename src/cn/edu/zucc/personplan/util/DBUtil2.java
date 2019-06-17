package cn.edu.zucc.personplan.util;



import java.sql.Connection;
import java.sql.SQLException;
import java.beans.PropertyVetoException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBUtil2 {
	private static DBUtil2 dbPool;
	private ComboPooledDataSource dataSource;

	static {
		dbPool = new DBUtil2();
	}

	public DBUtil2() {
		try {
			dataSource = new ComboPooledDataSource();
			dataSource.setUser("root");
			dataSource.setPassword("123456");
			dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/plan?autoReconnect=true&useUnicode=true&characterEncoding=utf-8");
			dataSource.setDriverClass("com.mysql.jdbc.Driver");
			dataSource.setInitialPoolSize(2);
			dataSource.setMinPoolSize(1);
			dataSource.setMaxPoolSize(10);
			dataSource.setMaxStatements(50);
			dataSource.setMaxIdleTime(60);
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}
	}

	public final static DBUtil2 getInstance() {
		return dbPool;
	}

	public final Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("无法从数据源获取连接 ", e);
		}
	}

	public static void main(String[] args) throws SQLException {
		Connection con = null;
		try {
			con = DBUtil2.getInstance().getConnection();
			java.sql.ResultSet rs=con.createStatement().executeQuery("select barcode from beanbook");
			while(rs.next())
				System.out.println(rs.getString(1));
		} catch (Exception e) {
		} finally {
			if (con != null)
				con.close();
		}
	}
}