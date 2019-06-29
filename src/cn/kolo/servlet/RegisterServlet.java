package cn.kolo.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.kolo.Ex_7.model.User;
import cn.kolo.Ex_7.service.Service;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HttpSession session;
    private Service service;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		service = context.getBean("service", Service.class);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String account = request.getParameter("account");
		User user = new User();
		user.setAccount(account);
		user.setPassword(password);
		user.setUserName(username);
		System.out.println(user);
		if (service.register(user)) {
			session.setAttribute("username", username);
			response.getWriter().append("{\"stute\": \""+ "true" +"\"}");
		}else {
			response.getWriter().append("{\"stute\": \""+ "fasle" +"\"}");
		}		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

