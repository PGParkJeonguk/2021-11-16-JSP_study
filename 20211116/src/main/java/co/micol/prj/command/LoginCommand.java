package co.micol.prj.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.micol.prj.comm.Command;
import co.micol.prj.member.service.MemberService;
import co.micol.prj.member.service.MemberServiceImpl;
import co.micol.prj.member.service.MemberVO;

public class LoginCommand implements Command {
	
	@Override
	public String run(HttpServletRequest request, HttpServletResponse response) {
		MemberService memberDao = new MemberServiceImpl();
		MemberVO vo = new MemberVO();
		HttpSession session = request.getSession();	//서버가 가지고있는 서버객체를 가져오기
		
		vo.setId(request.getParameter("id"));
		vo.setPassword(request.getParameter("password"));
		vo = memberDao.memberSelect(vo);
		String message = null;
		if(vo != null) {
			session.setAttribute("id", vo.getId());
			session.setAttribute("name", vo.getName());
			session.setAttribute("author", vo.getAuthor());
			message = vo.getName() + "님 환영합니다.";
		}else {
			message = "아이디 또는 비밀번호가 틀립니다.";
		}
		request.setAttribute("message", message); // 결과를 담아 보낼때는 request객체를 이용한다.
		
		return "member/memberLogin";	//보여줄 페이지.
	}

}
