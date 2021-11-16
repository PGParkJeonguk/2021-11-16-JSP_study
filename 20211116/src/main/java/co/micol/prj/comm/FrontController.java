package co.micol.prj.comm;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.micol.prj.command.HomeCommand;
import co.micol.prj.command.LoginCommand;
import co.micol.prj.command.LoginForm;
import co.micol.prj.command.Logout;
import co.micol.prj.command.MemberList;

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<String, Command> map = new HashMap<String, Command>();
	
	public FrontController() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		//초기화값 집어넣는곳("/login.do" ,  new LoginCommand())=>키값과 밸류값형태로 만듬.
		map.put("/home.do", new HomeCommand());		//홈 페이지를 보여주는 Command
		map.put("/login.do", new LoginCommand());	//로그인 처리
		map.put("/memberList.do", new MemberList());	//멤버 목록
		map.put("/loginForm.do", new LoginForm());	//로그인 폼을 호출한다.
		map.put("/logout.do", new Logout());	//로그아웃 처리
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//요청을 분석하고 처리하는곳.
		request.setCharacterEncoding("UTF-8");	//한글 깨짐 처리
		String uri = request.getRequestURI();	//uri들고 오는것.
		String contextPath = request.getContextPath();	//contextPath 구하는것.
		String page = uri.substring(contextPath.length());	//실제요청을 구함(uri에서 contextPath를 뺀 나머지)
		
		//500번 에러가 주로 나는 곳.
		Command command = map.get(page);	//요청에 대한 수행할 command를 찾음.
		String viewPage = command.run(request, response);
		
		//서버가 외부페이지에서 접속이 가능하도록 만들어야함.
		//WEB-INF에 접근할수 있도록 viewResolve를 만듬.
		
		if(!viewPage.endsWith(".do")) {
			viewPage = "WEB-INF/views/" + viewPage + ".jsp";
		}
		
		//응답을 처리한다. 요청을 가지고 활동한다.
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
		
	}

}
