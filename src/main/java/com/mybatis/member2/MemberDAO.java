package com.mybatis.member2;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

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
	
	// ID 찾기 메서드 selectID()
	public String selectID() {
		sqlMapper  = getInstance();
		SqlSession session = sqlMapper.openSession();
		String id = session.selectOne("mapper.member.selectID");		
		return id;
	}

	public String selectPwd() {
		sqlMapper  = getInstance();
		SqlSession session = sqlMapper.openSession();
		String pwd = session.selectOne("mapper.member.selectPwd");		
		return pwd;
	}
}
