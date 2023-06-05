package com.mybatis.member4;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mybatis.member.MemberVO;

@WebServlet("/member4.do")
public class MemberServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	public void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		MemberDAO dao = new MemberDAO();
		MemberVO memberVO = new MemberVO();
		
		String action = request.getParameter("action");
		String nextPage = "";
		
		if(action == null || action.equals("listMembers")) {
			List<MemberVO> membersList = dao.selectAllMemberList();
			request.setAttribute("membersList", membersList);
			nextPage = "listMembers.jsp";
			
		} else if(action.equals("selectMemberById")) {
			String id = request.getParameter("value");
			memberVO = dao.selectMemberById(id);
			//selectMemberById에서 담아온 값 memberVO를 member에 넣음
			request.setAttribute("member", memberVO);
			nextPage = "member.jsp";
			
		} else if(action.equals("selectMemberByName")) {
			String name = request.getParameter("value");
			List<MemberVO> membersList = dao.selectMemberByName(name);
			request.setAttribute("membersList", membersList);
			nextPage = "listMembers.jsp";
			
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);		
	}
}