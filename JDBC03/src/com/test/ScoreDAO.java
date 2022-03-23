package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

public class ScoreDAO
{
	// 주요 속성 구성 → 연결위한 conn....
	private Connection conn;
	
	// conn을 위한 사용자 정의 생성자 생성
	public ScoreDAO()
	{
		conn = DBConn.getConnection();
	}
	
	// 메소드 정의 → 정보 입력 → insert
	public int add(ScoreDTO dto) throws SQLException
	{
		// 결과 값을 받을 변수 선언
		int result = 0;
		
		// 작업 객체 생성
	    Statement stmt = conn.createStatement();
	    
	    // 쿼리문 준비 (담길값 입력값 이기 때문에 String.format() 이용)
	    String sql = String.format("INSERT INTO TBL_SCORE(SID, NAME, KOR, ENG, MAT)"
								+ " VALUES(SCORESEQ.NEXTVAL, '%s', %d, %d, %d)", dto.getName(), dto.getKor(), dto.getEng(), dto.getMat());
		// 쿼리문 날리기
	    result = stmt.executeUpdate(sql);
	    
	    // 리소스 반납
	    stmt.close();
	    	
		// 결과 값 반환
		return result;
	}
	
	// 메소드 정의 → 전체 인원 수 확인하는 기능 → select
	public int count() throws SQLException 
	{
		// 결과 값을 받을 변수 선언
		int result = 0;
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_SCORE";
		
		// 쿼리문 날리기 (select 구문이므로 executeQuery() → ResultSet 필요 )
		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet 처리 → 반복문
		while (rs.next())
		{
			result = rs.getInt("COUNT");	
		}
		
		// 리소스 반납 (조립은 분해의 역순)
		rs.close();
		stmt.close();
		
		
		// 결과 값 반환
		return result;
	}
	
	// 메소드 정의 → 전체 리스트 출력 → select 후 리스트에 담을거임
	public ArrayList<ScoreDTO> lists() throws SQLException
	{
		// 결과값으로 반환할 변수 선언 → Arraylist 타입을 반환
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = "SELECT SID, NAME, KOR, ENG, MAT, (KOR+ENG+MAT) AS TOT , (KOR+ENG+MAT)/3 AS AVG  FROM TBL_SCORE ORDER BY TOT DESC";
		
		// 쿼리문 날리기 (select 구문이므로 executeQuery() → ResultSet 필요 )
		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet 처리 → 반복문
		while (rs.next())
		{
			// dto 객체 생성
			ScoreDTO dto = new ScoreDTO();
			
			dto.setSid(rs.getString("SID"));
			dto.setName(rs.getString("NAME"));
			// 방법 2 ) 
			// String sql = "SELECT SID, NAME, KOR, ENG, MAT, (KOR+ENG+MAT) AS TOT , (KOR+ENG+MAT)/3 AS AVG FROM TBL_SCORE ORDER BY SID";
			// 이렇게 짜면 OREDER BY 절을통해 TOT , AVG 하면 등수별로 출력 할 수 도 있다..
			
			// 총점 평균 구해서 넣을꺼라 걍 국영수는 따로 받아서 넣겠음
			int kor = rs.getInt("KOR");
			int eng = rs.getInt("ENG");
			int mat = rs.getInt("MAT");
			
			int tot = rs.getInt("TOT");
			double avg = rs.getDouble("AVG"); 
			
			dto.setKor(kor);
			dto.setEng(eng);
			dto.setMat(mat);
			dto.setTot(tot);
			dto.setAvg(avg);
			
			// 어레이리스트에 추가
			result.add(dto);
		}
		
		// 리소스 반납
		rs.close();
		stmt.close();
		
		// 결과값 반환
		return result;
	}


} //end class..









































