package com.mybatis.member4;

import java.io.Reader; //input, output
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.mybatis.member.MemberVO;

public class MemberDAO {
	//DB연동과 관련된 MyBatis에서 제공해주는 인터페이스
	private static SqlSessionFactory sqlMapper = null;
	public static SqlSessionFactory getInstance() {
		if(sqlMapper == null) {
			//최초 DB연결작업
			try {
				/* MyBatis를 이용해서 DB정보를 연동 (커넥션 이용 X) */
				//SQL문이 있는 SqlMapConfig.xml 경로를 설정
				String resource = "mybatis/SqlMapConfig.xml";
				//Resources import 시 ibatis 로 선택
				Reader reader = Resources.getResourceAsReader(resource);
				//sqlMapper에 DB연동정보 담았음
				sqlMapper = new SqlSessionFactoryBuilder().build(reader);
				//정보를 다 담았으므로 연결을 닫아줌 (다음에 또 열수 있도록)
				reader.close();
			} catch (Exception e) {
				System.out.println("DB연결 중 에러!!");
				e.printStackTrace();
			}			
		}//if End
		return sqlMapper;
	}
	
	//회원정보 목록 조회 selectAllMemberList()
	public List<MemberVO> selectAllMemberList() {
		sqlMapper = getInstance();
		//실제 sql문이 있는 member.xml의 SQL문을 호출하는데 사용되는 SqlSession 객체를 가져옴
		SqlSession session = sqlMapper.openSession();
		List<MemberVO> memList = null;
		//""안에 member.xml에 있는 namespace 이름.수행할 id 을 적어주어야 함
		memList = session.selectList("mapper.member.selectAllMemberList");
		/*//확인용 for문
		for(MemberVO mem:memList) {
			System.out.println(mem.getId());			
		}*/
		return memList;		
	}
	
	//ID로 회원 조회하기
	public MemberVO selectMemberById(String id) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		//member.xml 에 selectMemberById 를 다녀는데 가져갈 매개변수 id를 , 하고 적어줌 
		MemberVO memberVO = session.selectOne("mapper.member.selectMemberById",id);		
		return memberVO;
	}

	public List<MemberVO> selectMemberByName(String name) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		//member.xml 에 selectMemberById 를 다녀는데 가져갈 매개변수 id를 , 하고 적어줌 
		List<MemberVO> membersList = session.selectList("mapper.member.selectMemberByName",name);		
		return membersList;
	}
}