package cn.kolo.Ex_7.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.loader.plan.exec.process.spi.ReturnReader;

import cn.kolo.Ex_7.dao.UserDao;
import cn.kolo.Ex_7.model.User;
import net.bytebuddy.asm.Advice.OffsetMapping.Target.AbstractReadOnlyAdapter;

public class Service {
	private UserDao userDao;
	
	public boolean checkLogin(User user) {
		boolean result = false;
		User user2 = userDao.findUserByaccount(user.getAccount());
		
		if(user2!=null&&user.getAccount().equals(user2.getAccount()) && user.getPassword().equals(user2.getPassword())) {
			result = true;
		}
		return result;
	}
	
	public String findUsernameByaccount(String account) {
		User user = userDao.findUserByaccount(account);
		if(user!=null)
			return user.getUserName();
		return null;
	}
	public boolean register(User user) {
		User user2 = userDao.findUserByaccount(user.getAccount());
		if(user2!=null) 
			return false;
		userDao.saveUser(user);
		return true;
	}
	
	public List<User> findUsersByUser(User user){
		List<User> users = userDao.findUsers();
		Iterator<User> itr = users.iterator();
		while (itr.hasNext()) {
			User u = itr.next();
			if(u.getAccount().equals(user.getAccount()))
				itr.remove();
		}
		return users;
	}
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
		
	}
	
	

}
