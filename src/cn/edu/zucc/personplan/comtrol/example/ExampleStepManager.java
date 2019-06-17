package cn.edu.zucc.personplan.comtrol.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.personplan.itf.IStepManager;
import cn.edu.zucc.personplan.model.BeanPlan;
import cn.edu.zucc.personplan.model.BeanStep;
import cn.edu.zucc.personplan.util.BaseException;
import cn.edu.zucc.personplan.util.DBUtil;
import cn.edu.zucc.personplan.util.DBUtil2;

public class ExampleStepManager implements IStepManager {

	@Override
	public void add(BeanPlan plan, String name, String planstartdate,
			String planfinishdate) throws BaseException {
		Connection conn = null;
		int num=0;
		int sorder=0;
		try {
			java.text.SimpleDateFormat fs = new java.text.SimpleDateFormat("yyyy-mm-dd");
			java.util.Date psd = fs.parse(planstartdate);
			java.util.Date pfd = fs.parse(planfinishdate);
			conn = DBUtil2.getInstance().getConnection();
			String sql = "select max(step_id) from tbl_step";
			java.sql.PreparedStatement st = conn.prepareStatement(sql);
			
			java.sql.ResultSet rs = st.executeQuery();
			rs.next();
			num = rs.getInt(1);
			sql = "select step_count from tbl_plan where plan_id = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, plan.getPlan_id());
			rs = st.executeQuery();
			if(rs.next()) {
				sorder = rs.getInt(1)+1;
				sql = "insert into tbl_step values(?,?,?,?,?,?,null,null)";
			
				st = conn.prepareStatement(sql);
				st.setInt(1, num+1);
				st.setInt(2, plan.getPlan_id());
				st.setInt(3, sorder);
				st.setString(4, name);
				st.setObject(5, psd);
				st.setObject(6, pfd);
				st.execute();
			}
			sql = "update tbl_plan set step_count=? where plan_id = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, sorder);
			st.setInt(2, plan.getPlan_id());
			st.execute();
			
		}catch(SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public List<BeanStep> loadSteps(BeanPlan plan) throws BaseException {
		List<BeanStep> result=new ArrayList<BeanStep>();
		Connection conn = null;
		try {
			conn = DBUtil2.getInstance().getConnection();
			String sql = "select * from tbl_step where plan_id = ?";
			java.sql.PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, plan.getPlan_id());
			java.sql.ResultSet rs = st.executeQuery();
			while(rs.next()) {
				BeanStep b = new BeanStep();
				b.setStep_id(rs.getInt(1));
				b.setPlan_id(rs.getInt(2));
				b.setStep_order(rs.getInt(3));
				b.setStep_name(rs.getString(4));
				b.setPbtime(rs.getDate(5));
				b.setPetime(rs.getDate(6));
				b.setRbtime(rs.getDate(7));
				b.setRetime(rs.getDate(8));
				result.add(b);
			}
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void deleteStep(BeanStep step) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			conn = DBUtil2.getInstance().getConnection();
			int pid = step.getPlan_id();
			String sql = "delete from tbl_step where step_id = ?";
			java.sql.PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, step.getStep_id());
			st.execute();
			sql = "select step_count from tbl_plan where plan_id = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, pid);
			java.sql.ResultSet rs=st.executeQuery();
			if(rs.next()) {
				int count = rs.getInt(1);
				count--;
				sql = "update tbl_plan set step_count = ? where plan_id = ?";
				st = conn.prepareStatement(sql);
				st.setInt(1, count);
				st.setInt(2, pid);
				st.execute();
				sql = "update tbl_step set step_order = step_order-1 where step_order>?";
				st = conn.prepareStatement(sql);
				st.setInt(1, step.getStep_order());
				st.execute();
			}
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * 设置当前步骤的实际开始时间，及对应的计划表中已开始步骤数量
	 * 
	 * @param step
	 * @throws BaseException
	 */
	@Override
	public void startStep(BeanStep step) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			conn = DBUtil2.getInstance().getConnection();
			String sql = " update tbl_step set real_begin_time=NOW()  where step_id = ?";
			java.sql.PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, step.getStep_id());
			st.execute();
			sql="select start_step_count from tbl_plan where plan_id = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, step.getPlan_id());
			java.sql.ResultSet rs=st.executeQuery();
			if(rs.next()) {
				sql = " update tbl_plan set start_step_count =?  where plan_id = ?";
				st = conn.prepareStatement(sql);
				st.setInt(1, rs.getInt(1)+1);
				st.setInt(2, step.getPlan_id());
				st.execute();
			}

			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 设置当前步骤的实际完成时间，及对应的计划表中已完成步骤数量
	 * @param step
	 * @throws BaseException
	 */
	@Override
	public void finishStep(BeanStep step) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			conn = DBUtil2.getInstance().getConnection();
			String sql = " update tbl_step set real_end_time=NOW()  where step_id = ?";
			java.sql.PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, step.getStep_id());
			st.execute();
			sql="select finished_step_count from tbl_plan where plan_id = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, step.getPlan_id());
			java.sql.ResultSet rs=st.executeQuery();
			if(rs.next()) {
				sql = " update tbl_plan set finish_step_count =?  where plan_id = ?";
				st = conn.prepareStatement(sql);
				st.setInt(1, rs.getInt(1)+1);
				st.setInt(2, step.getPlan_id());
				st.execute();
			}

			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 调整当前步骤的顺序号
	 * 注意：数据库表中，plan_id,step_order上建立了唯一索引，调整当前步骤的序号值和上一步骤的序号值时不能出现序号值一样的情况
	 * @param step
	 * @throws BaseException
	 */
	@Override
	public void moveUp(BeanStep step) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			conn = DBUtil2.getInstance().getConnection();
			int pid = step.getPlan_id();
			int sorder = step.getStep_order();
			int so;
			String sql = " select Min(step_order) from tbl_step where plan_id = ?";
			java.sql.PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, step.getPlan_id());
			java.sql.ResultSet rs=st.executeQuery();
			rs.next();
			so = rs.getInt(1);
			if(so == step.getStep_order()) {
				throw new BaseException("已经为最小步骤！");
			}
			sql = " update tbl_step set step_order=?  where step_id = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, -1);
			st.setInt(2, step.getStep_id());
			st.execute();
			sql="update tbl_step set step_order=? where step_order=? and plan_id = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, step.getStep_order());
			st.setInt(2, step.getStep_order()-1);
			st.setInt(3, step.getPlan_id());
			st.execute();
			sql="update tbl_step set step_order=?  where step_id = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, step.getStep_order()-1);
			st.setInt(2, step.getStep_id());
			st.execute();
			

			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	
	}

	@Override
	public void moveDown(BeanStep step) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			conn = DBUtil2.getInstance().getConnection();
			int pid = step.getPlan_id();
			int sorder = step.getStep_order();
			int so;
			String sql = " select MAX(step_order) from tbl_step where plan_id = ?";
			java.sql.PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, step.getPlan_id());
			java.sql.ResultSet rs=st.executeQuery();
			rs.next();
			so = rs.getInt(1);
			if(so == step.getStep_order()) {
				throw new BaseException("已经为最大步骤！");
			}
			sql = " update tbl_step set step_order=?  where step_id = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, -1);
			st.setInt(2, step.getStep_id());
			st.execute();
			sql="update tbl_step set step_order=? where step_order=? and plan_id = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, step.getStep_order());
			st.setInt(2, step.getStep_order()+1);
			st.setInt(3, step.getPlan_id());
			st.execute();
			sql="update tbl_step set step_order=?  where step_id = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, step.getStep_order()+1);
			st.setInt(2, step.getStep_id());
			st.execute();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
