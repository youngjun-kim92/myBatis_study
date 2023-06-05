package com.mybatis.member;

import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MemberDAO {
	//DB연동과 관련된 MyBatis에서 제공해주는 인터페이스
	private static SqlSessionFactory sqlMapper=null;
	public static SqlSessionFactory getInstance() {
		if(sqlMapper==null) {
			try {
				// MyBatis를 이용해서 DB정보를 연동 (커넥션 이용x)
				//SQL 문이 있는 SqlMapConfig.xml 경로를 설정
				String resource="mybatis/SqlMapConfig.xml";
				//Resources import시 inatis로 선택
				Reader reader=Resources.getResourceAsReader(resource);
				//sqlMapper에 DB연동정보 담았음
				sqlMapper=new SqlSessionFactoryBuilder().build(reader);
				//정보를 다 담았으므로 연결을 닫아줌
				reader.close();
			} catch (Exception e) {
				System.out.println("DB연결 중 에러!");
				e.printStackTrace();
			}
		}
		return sqlMapper;
	}
	
	//selectAllMemberList()
	public List<MemberVO> selectAllMemberList() {
		sqlMapper=getInstance();
		//실제 sql문이 있는 member.xml의 SQL문을 호출하는데 사용되는 SqlSession 객체를 가져옴
		SqlSession session=sqlMapper.openSession();
		List<MemberVO> memList=null;
		//""안에 member.xml에 있는 namespace 이름.수행할 id를 적어줘야함
		memList=session.selectList("mapper.member.selectAllMemberList");
		return memList;
	}
	
	//회원 등록 메서드
	public void insertMember(MemberVO memberVO) {	
		//매퍼로 DB 연결
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		
		//insert
		session.insert("mapper.member.insertMember", memberVO);
		session.commit(); //commit안하면 수행 x(트랜잭션)		
	}
	
	//회원 등록 메서드2
	public void insertMember2(Map<String, String> memberMap) {
		//매퍼로 DB 연결
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		
		//insert
		session.insert("mapper.member.insertMember2", memberMap);
		session.commit(); 		
	}
	
	// ID로 회원 검색
	public MemberVO selectMemberById(String id) {
		//매퍼로 DB 연결
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		//자료찾기
		MemberVO memberVO = session.selectOne("mapper.member.selectMemberById", id);
		return memberVO;
	}
	
	//회원 수정 메서드
	public void updateMember(MemberVO memberVO) {
		//매퍼로 DB 연결
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		
		//update
		session.update("mapper.member.updateMember", memberVO);		
		session.commit();
	}
	
	//회원 삭제 메서드
	public void deleteMember(MemberVO memberVO) {
		//매퍼로 DB 연결
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		
		//delete
		session.delete("mapper.member.deleteMember", memberVO);		
		session.commit();
	}
	
	//동적 조회 메서드
	public List<MemberVO> searchMember(MemberVO memberVO) {
		//매퍼로 DB 연결
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		
		//동적 조회
		List<MemberVO> list = session.selectList("mapper.member.searchMember", memberVO);
		return list;
	}
	
	//foreach 태그 활용 메서드
	public List<MemberVO> foreachSelect(List<String> nameList) {
		//매퍼로 DB 연결
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		
		//foreach
		List<MemberVO> list = session.selectList("mapper.member.foreachSelect", nameList);
		return list;
	}
}
