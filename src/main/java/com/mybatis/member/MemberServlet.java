package com.mybatis.member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member.do")
public class MemberServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	public void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
//		직접 출력 안하므로 필요 없음 - response.setContentType("text/html;charset=utf-8");
		MemberDAO dao = new MemberDAO();
		MemberVO memberVO = new MemberVO();
		
		String action = request.getParameter("action");
		String nextPage = "";
		
		//회원가입할 때 parameter action값을 받아서 insert 처리 할 것
		if(action == null || action.equals("listMembers")) {
			List<MemberVO> membersList = dao.selectAllMemberList();
			//돌아오면
			request.setAttribute("membersList", membersList);
			nextPage = "listMembers.jsp";
			
		} else if(action.equals("memberForm")) {
			//회원가입창으로 요청
			nextPage = "memberForm.jsp";
			
		} else if(action.equals("memberForm2")) {
			//회원가입창으로 요청
			nextPage = "memberForm2.jsp";			
		
		} else if(action.equals("insertMember")) {
			//회원가입 폼에서 insert 하러 가는 것
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			memberVO.setId(id);
			memberVO.setPwd(pwd);
			memberVO.setName(name);
			memberVO.setEmail(email);
			dao.insertMember(memberVO);
			nextPage = "/member.do?action=listMembers";
			
		} else if(action.equals("insertMember2")) {
			//회원가입 폼에서 insert 하러 가는 것
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			Map<String, String> memberMap = new HashMap<String, String>();
			memberMap.put("id", id);
			memberMap.put("pwd", pwd);
			memberMap.put("name", name);
			memberMap.put("email", email);
			dao.insertMember2(memberMap);
			nextPage = "/member.do?action=listMembers";
			
		} else if(action.equals("modMember")) {
			//정보수정 창으로 요청
			String id = request.getParameter("id");
			memberVO = dao.selectMemberById(id);
			request.setAttribute("member", memberVO);
			nextPage = "modMemberForm.jsp";	
			
		} else if(action.equals("updateMember")) {
			//수정하기 폼에서 업데이트 수행하러 가는 것
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			memberVO.setId(id);
			memberVO.setPwd(pwd);
			memberVO.setName(name);
			memberVO.setEmail(email);
			dao.updateMember(memberVO);

			nextPage = "/member.do?action=listMembers";
		} else if(action.equals("delMember")) {
			//삭제
			String id = request.getParameter("id");
			memberVO.setId(id);
			dao.deleteMember(memberVO);
			
			nextPage = "/member.do?action=listMembers";
		} else if(action.equals("searchMember")) {
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			memberVO.setName(name);
			memberVO.setEmail(email);
			List<MemberVO> membersList = dao.searchMember(memberVO);
			request.setAttribute("membersList", membersList);
			
			//직접 가져왔으므로 가져온 걸 가지고 listMembers.jsp로 직접 이동
			nextPage = "listMembers.jsp";
		} else if(action.equals("foreachSelect")) {
			List<String> nameList = new ArrayList();
			nameList.add("김해피");
			nameList.add("손흥민");
			nameList.add("홍길동");
			List<MemberVO> membersList = dao.foreachSelect(nameList);
			//foreachSelect() 갔다오면 위의 3사람에 대한 정보가 날아올 것
			request.setAttribute("membersList", membersList);
			
			nextPage = "listMembers.jsp";
		}
		
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("listMembers.jsp");
		dispatcher.forward(request, response);
	}
}