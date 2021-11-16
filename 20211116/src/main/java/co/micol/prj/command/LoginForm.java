package co.micol.prj.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.micol.prj.comm.Command;

public class LoginForm implements Command {

	@Override
	public String run(HttpServletRequest request, HttpServletResponse response) {
		//단숨 폼 호출
		return "member/loginForm";
	}

}
