package cn.edu.zucc.personplan.model;

public class BeanUser {
	public static BeanUser currentLoginUser=null;
	private String user_id;
	private String user_pwd;
	private java.util.Date reg_time;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public java.util.Date getReg_time() {
		return reg_time;
	}
	public void setReg_time(java.util.Date reg_time) {
		this.reg_time = reg_time;
	}
	
	
	
	
	
}
