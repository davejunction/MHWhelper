package cn.edu.zucc.personplan.comtrol.example;


import java.sql.Connection;
import java.sql.SQLException;

import cn.edu.zucc.personplan.itf.IUserManager;
import cn.edu.zucc.personplan.model.BeanUser;
import cn.edu.zucc.personplan.util.BaseException;
import cn.edu.zucc.personplan.util.DBUtil;
import cn.edu.zucc.personplan.util.DBUtil2;

public class ExampleUserManager implements IUserManager {

	@Override
	public BeanUser reg(String userid, String pwd,String pwd2) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		BeanUser user  = null;
		
		try {
			
			conn = DBUtil2.getInstance().getConnection();
			if(userid.isEmpty()) {
				throw new BaseException("userid is not exist!");
			}
			String sql = "select * from tbl_user where user_id = ?";
			java.sql.PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, userid);
			java.sql.ResultSet rs = st.executeQuery();
			if(rs.next()) {
				throw new BaseException("userid repeat!");
			}
			else {
				if(!pwd.isEmpty()&&pwd.equals(pwd2)) {
					java.sql.Timestamp d = new java.sql.Timestamp(System.currentTimeMillis());
					sql = "insert into tbl_user values(?,?,?)";
					st = conn.prepareStatement(sql);
					st.setString(1, userid);
					st.setString(2, pwd);
					st.setObject(3, d);
					st.execute();
					user  = new BeanUser();
					user.setUser_id(userid);
					user.setUser_pwd(pwd);
					user.setReg_time(d);
				}
				else {
					throw new BaseException("wrong pwd!");
				}
				
			}
			
			
		}catch(SQLException e) {
			
			e.printStackTrace();
			
		}
		
		return user;
	}

	
	@Override
	public BeanUser login(String userid, String pwd) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		BeanUser user  = null;
		try {
		//	conn.setAutoCommit(false);
			conn = DBUtil2.getInstance().getConnection();
			String sql = "select * from tbl_user where user_id = ?";
			java.sql.PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, userid);
			java.sql.ResultSet rs = st.executeQuery();
			if(!rs.next()) {
				throw new BaseException("userid repeat!");
			}
			else {
				if(rs.getString(2).equals(pwd)) {
					user  = new BeanUser();
					user.setUser_id(rs.getString(1));
					user.setUser_pwd(rs.getString(2));
					user.setReg_time(rs.getDate(3));
				}
				else {
					throw new BaseException("Wrong pwd123!");
				}
			}
			
	//		conn.commit();
			
		}catch(SQLException e) {
//			try {
//				conn.rollback();
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			e.printStackTrace();
			
		}
		
		return user;
	}


	@Override
	public void changePwd(BeanUser user, String oldPwd, String newPwd,
			String newPwd2) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
	//		conn.setAutoCommit(false);
			conn = DBUtil2.getInstance().getConnection();
			String sql = "select * from tbl_user where user_id = ?";
			java.sql.PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, user.getUser_id());
			java.sql.ResultSet rs = st.executeQuery();
			if(!rs.next()) {
				throw new BaseException("userid repeat!");
			}
			else {
				if(rs.getString(2).equals(oldPwd)) {
					if(newPwd.equals(newPwd2)) {
						sql = "update tbl_user set user_pwd = ? where user_id = ?";
						st = conn.prepareStatement(sql);
						st.setString(1, newPwd);
						st.setString(2, user.getUser_id());
						st.execute();
					}
					else {
						throw new BaseException("pwd repeat!");
					}
				}
				else {
					throw new BaseException("Wrong pwd123!");
				}
			}
			
			
//			conn.commit();
		}catch(SQLException e) {
//			try {
//				conn.rollback();
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			e.printStackTrace();
			
		}
		
		
		
	}
}
