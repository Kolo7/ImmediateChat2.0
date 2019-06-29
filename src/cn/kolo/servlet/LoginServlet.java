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
 * Servlet implementation class AnnotationServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Service service;
    private HttpSession session;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		session = request.getSession();
		User user = new User();
		user.setAccount(account);
		user.setPassword(password);
		System.out.println(user);
		if(service.checkLogin(user)) {
			String username = service.findUsernameByaccount(account);
			session.setAttribute("username", username);
			response.getWriter().append("{\"message\": \""+ "true" +"\","+"\"username\": \""+ username +"\""+"}");
		}else {
			response.getWriter().append("{\"message\": \""+ "false" +"\","+"\"username\": \""+ "null" +"\""+"}");
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
