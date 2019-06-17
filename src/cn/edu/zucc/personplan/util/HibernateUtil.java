package cn.edu.zucc.personplan.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import  org.hibernate.cfg.Configuration;

import cn.edu.zucc.personplan.model.BeanStep;

import java.util.List;

public class HibernateUtil {
    private static SessionFactory sessionFactory
            = new Configuration().configure().buildSessionFactory();
    public static Session getSession(){
        Session session = sessionFactory.openSession();
        return session;
    }
    public static void main(String[] args){

		Session session=getSession();
		
		List<BeanStep> steps=session.createQuery("from tbl_step where plan_id=1").list();
		System.out.println("计划的数量"+steps.size()+"��");
		for(BeanStep s:steps) {
			
			System.out.println(s.getStep_id());
		}
        session.close();
        System.exit(0);
    }

}
