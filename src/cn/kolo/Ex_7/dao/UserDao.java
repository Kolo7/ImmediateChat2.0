package cn.kolo.Ex_7.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import cn.kolo.Ex_7.model.*;

public class UserDao {
	Configuration configuration;
	ServiceRegistry serviceRegistry;
	SessionFactory sessionFactory;
	Session session=null;
	Transaction transaction=null;
	public UserDao() {
		configuration = new Configuration().configure("hibernate.cfg.xml");
		serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
		sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
	}
	
	public void saveUser(User user) {

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		try {
			session.save(user);
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
	
	public User findUserByaccount(String account) {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		User user=null;
		try {
			user = session.get(User.class, account);
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			session.close();
		}
		return user;
	}
	
	public List<User> findUsers(){
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		List<User> users=null;
		User user=null;
		try {
			String queryString = "from User";
			Query query =  session.createQuery(queryString);
			users = (List<User>)query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			session.close();
		}
		return users;
	}
	public void clean() {
		sessionFactory.close();
	}
}
