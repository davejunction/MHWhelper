package cn.edu.zucc.personplan.comtrol.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.personplan.itf.IPlanManager;
import cn.edu.zucc.personplan.model.BeanPlan;
import cn.edu.zucc.personplan.model.BeanUser;
import cn.edu.zucc.personplan.util.BaseException;
import cn.edu.zucc.personplan.util.DBUtil;
import cn.edu.zucc.personplan.util.DBUtil2;

public class ExamplePlanManager implements IPlanManager {

	@Override
	public BeanPlan addPlan(String name) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn=null;
		BeanPlan b = new BeanPlan();
		int num=0,num2=0;
		try {
			conn = DBUtil2.getInstance().getConnection();
			String sql = "select MAX(plan_order) from tbl_plan where user_id = ?";
			java.sql.PreparedStatement st = conn.prepareStatement(sql);
			System.out.println(BeanUser.currentLoginUser.getUser_id());
			
			if(BeanUser.currentLoginUser==null) {
				throw new BaseException("错误！");
			}
			st.setString(1, BeanUser.currentLoginUser.getUser_id());
			java.sql.ResultSet rs = st.executeQuery();
			if(rs.next())
				num = rs.getInt(1);
			num++;
			sql = "select MAX(plan_id) from tbl_plan";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			rs.next();
			num2 = rs.getInt(1);
			sql = "insert into tbl_plan values(?,?,?,?,?,?,?,?)";
			java.util.Date d = new java.util.Date(System.currentTimeMillis());
			st = conn.prepareStatement(sql);
			st.setInt(1, num2+1);
			st.setString(2, BeanUser.currentLoginUser.getUser_id());
			st.setInt(3, num);
			st.setString(4, name);
			st.setObject(5, d);
			st.setInt(6, 5);
			st.setInt(7, 3);
			st.setInt(8, 1);
			st.execute();
			
			b.setCreate_time(d);
			b.setFscount(1);
			b.setStcount(3);
			b.setStep_count(5);
			b.setPlan_id(num2+1);
			b.setPlan_order(num);
			b.setPlan_name(name);
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			
		} 
		
		
		return b;
	}

	@Override
	public List<BeanPlan> loadAll() throws BaseException {
		List<BeanPlan> result=new ArrayList<BeanPlan>();
		Connection conn = null;
		try {
			conn = DBUtil2.getInstance().getConnection();
			if(BeanUser.currentLoginUser==null) {
				throw new BaseException("错误！");
			}
			String sql = "select * from tbl_plan where user_id = ?";
			java.sql.PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, BeanUser.currentLoginUser.getUser_id());
			java.sql.ResultSet rs = st.executeQuery();
			while(rs.next()) {
				BeanPlan p=new BeanPlan();
				p.setPlan_id(rs.getInt(1));
				p.setUser_id(rs.getString(2));
				p.setPlan_order(rs.getInt(3));
				p.setPlan_name(rs.getString(4));
				p.setCreate_time(rs.getDate(5));
				p.setStep_count(rs.getInt(6));
				p.setStcount(rs.getInt(7));
				p.setFscount(rs.getInt(8));
				result.add(p);
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void deletePlan(BeanPlan plan) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil2.getInstance().getConnection();
			String sql = "select * from tbl_plan where plan_id = ?";
			java.sql.PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, plan.getPlan_id());
			java.sql.ResultSet rs = st.executeQuery();
			if(rs.next()) {
				throw new BaseException("存在步骤！");
			}
			else {
				sql = "delete from tbl_plan where plan_id = ?";
				st = conn.prepareStatement(sql);
				st.setInt(1, plan.getPlan_id());
				st.execute();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws BaseException {
		// TODO Auto-generated method stub
		ExamplePlanManager q = new ExamplePlanManager();
		q.addPlan("挖土");
	}
}
