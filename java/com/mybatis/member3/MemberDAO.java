package com.mybatis.member3;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;

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
	public List<HashMap<String, String>> selectAllMemberList() {
		sqlMapper=getInstance();
		//실제 sql문이 있는 member.xml의 SQL문을 호출하는데 사용되는 SqlSession 객체를 가져옴
		SqlSession session=sqlMapper.openSession();
		List<HashMap<String, String>> memList=null;
		//""안에 member.xml에 있는 namespace 이름.수행할 id를 적어줘야함
		memList=session.selectList("mapper.member.selectAllMemberList");
		return memList;
	}
}
