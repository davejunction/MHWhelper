package cn.edu.zucc.personplan.model;

public class BeanPlan {
	public static final String[] tableTitles={"序号","名称","步骤数","已完成数"};
	/**
	 * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
	 */
	public String getCell(int col){
		if(col==0) return String.valueOf(plan_id);
		else if(col==1) return plan_name;
		else if(col==2) return String.valueOf(step_count);
		else if(col==3) return String.valueOf(fscount);
		else return "";
	}
	private int plan_id;
	private String user_id;
	private int plan_order;
	private String plan_name;
	private java.util.Date create_time;
	private int step_count;
	private int stcount;
	private int fscount;
	public int getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(int plan_id) {
		this.plan_id = plan_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getPlan_order() {
		return plan_order;
	}
	public void setPlan_order(int plan_order) {
		this.plan_order = plan_order;
	}
	public String getPlan_name() {
		return plan_name;
	}
	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}
	public java.util.Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(java.util.Date create_time) {
		this.create_time = create_time;
	}
	public int getStep_count() {
		return step_count;
	}
	public void setStep_count(int step_count) {
		this.step_count = step_count;
	}
	public int getStcount() {
		return stcount;
	}
	public void setStcount(int stcount) {
		this.stcount = stcount;
	}
	public int getFscount() {
		return fscount;
	}
	public void setFscount(int fscount) {
		this.fscount = fscount;
	}
	

}
