/*=================
 	MemberDAO.java
 	쿼리문 동작 위한 클래스
 ==================*/

// 데이터베이스에 액세스 하는 기능 
// → DBConn 활용(전담 계층)

// 데이터를 입력하는 기능 → insert
// 인원수 조회 기능(TBL_MEMBER) → select count(*) as COUNT 

// 전체 리스트 조회 기능(TBL_MEMBER) → select 

package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

public class MemberDAO
{
    // 주요 속성 구성 → DB 연결 객체
	private Connection conn;
    
	// 생성자 정의 (사용자 정의 생성자)
	public MemberDAO() throws ClassNotFoundException, SQLException
	{ 
		// 이걸 밖에서 못쓰기 때문에 생성자를 이용해서 사용
		conn = DBConn.getConnection(); 
	}
	
	// 메소드 정의 → 데이터를 입력하는 기능 → insert
	public int add(MemberDTO dto) throws SQLException
	{
		// 반환할 결과값을 담아낼 변수 (적용될 행의 갯수)
		int result = 0;
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비(insert)
		String sql = String.format("INSERT INTO TBL_MEMBER(SID, NAME, TEL)"
				                + " VALUES(MEMBERSEQ.NEXTVAL, '%s', '%s')", dto.getName(), dto.getTel());
	
		// 작업 객체를 활용하여 쿼리문 실행(전달)
	    result = stmt.executeUpdate(sql);	
		// 사용한 리소스 반납
		stmt.close();
	    
		// 최종 결과값 반납 
		return result;
	}
   
	// 메소드 정의 → 전체 인원 수 확인하는 기능 → select
	public int count() throws SQLException
	{
		// 결과값을 담을 변수 선언 및 초기화
		int result = 0;
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비 
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_MEMBER";
		
		// 생성된 작업 객체를 활용하여 쿼리문 실행 → select → executeQuery → ResultSet 반환 → 일반적으로 반복문을 통한 ResultSet 처리
		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet 처리 → 반복문 → 결과값 수신
		while (rs.next())			// if(rs.next()) 저 쿼리문은 결과가 1개이기 때문에 반복문 1번이라 이리 해도됨.
		{
			result = rs.getInt("COUNT"); //rs.getInt(1); 쿼리문의 컬럼의 인덱스 값 (※오라클 인덱스는 1부터 시작...)
		}
		
		// 리소스 반납
		rs.close();
		stmt.close();
		
		// 최종 결과값 반환
		return result;
		
	}//end count()
	
	// 메소드 정의 → 전체 리스트를 조회하는 기능 → select
	public ArrayList<MemberDTO> lists() throws SQLException
	{
	    // 결과값으로 반환할 변수 선언 및 초기화
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비 → select
		String sql = "SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID";
		
		// 생성된 작업 객체를 활용하여 쿼리문 실행 → select → executeQuery() → ResultSet 반환 → 일반적으로 반복문 처리
		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet 처리 → 일반적으로 반복문 처리 
		while (rs.next())
		{
			MemberDTO dto = new MemberDTO();
			
			dto.setSid(rs.getString("SID"));
			dto.setName(rs.getString("NAME"));
			dto.setTel(rs.getString("TEL"));
			
			result.add(dto);		
		}
		
		// 리소스 반납
		rs.close();
		stmt.close();
		
		// 최종 결과값 반환
		return result;
		
	}//end lists()
	
	public void close() throws SQLException
	{
		//주의 conn.close(); 이건 커낵션 클로즈임.
		DBConn.close();
	}
	
	
	
	
	
} //end class










































