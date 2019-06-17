package cn.edu.zucc.personplan.model;

public class BeanStep {
	public static final String[] tblStepTitle={"序号","名称","计划开始时间","计划完成时间","实际开始时间","实际完成时间"};
	/**
	 * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
	 */
	
	private int step_id;
	private int plan_id;
	private int step_order;
	private String step_name;
	private java.util.Date pbtime;
	private java.util.Date petime;
	private java.util.Date rbtime;
	private java.util.Date retime;
//	java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
	
	public String getCell(int col){
		if(col==0) return String.valueOf(step_order);
		else if(col==1) return step_name;
		else if(col==2) return String.valueOf(pbtime);
		else if(col==3) return String.valueOf(petime);
		else if(col==4)
			return String.valueOf(rbtime);
		else if(col==5) 
			return String.valueOf(retime);
		else return "";
	}
	public int getStep_id() {
		return step_id;
	}
	public void setStep_id(int step_id) {
		this.step_id = step_id;
	}
	public int getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(int plan_id) {
		this.plan_id = plan_id;
	}
	public int getStep_order() {
		return step_order;
	}
	public void setStep_order(int step_order) {
		this.step_order = step_order;
	}
	public String getStep_name() {
		return step_name;
	}
	public void setStep_name(String step_name) {
		this.step_name = step_name;
	}
	public java.util.Date getPbtime() {
		return pbtime;
	}
	public void setPbtime(java.util.Date pbtime) {
		this.pbtime = pbtime;
	}
	public java.util.Date getPetime() {
		return petime;
	}
	public void setPetime(java.util.Date petime) {
		this.petime = petime;
	}
	public java.util.Date getRbtime() {
		return rbtime;
	}
	public void setRbtime(java.util.Date rbtime) {
		this.rbtime = rbtime;
	}
	public java.util.Date getRetime() {
		return retime;
	}
	public void setRetime(java.util.Date retime) {
		this.retime = retime;
	}
	
	
	
	
}
