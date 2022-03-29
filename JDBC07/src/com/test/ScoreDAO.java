package com.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.util.DBConn;

public class ScoreDAO
{
	// 연결 객체 선언
	private Connection conn;
	
	// db 연결 메소드
	public Connection connection()
	{
		conn = DBConn.getConnection();
		return conn;
	}
	
	// db 연결 종료
	public void close()
	{
		DBConn.close();
	}
	
	
	// 데이터 입력 담당 메소드
	public int add(ScoreDTO dto)
	{
		int result = 0;
		
		try
		{
			// 쿼리문 준비
			String sql = "INSERT INTO TBL_SCORE(SID, NAME, KOR, ENG, MAT)"
						+ " VALUES(SCORESEQ.NEXTVAL, ?, ?, ?, ?)";
			
			// 작업객체 생성 
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getKor());
			pstmt.setInt(3, dto.getEng());
			pstmt.setInt(4, dto.getMat());
			
			result = pstmt.executeUpdate();
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}

		return result;
	}
	
	//전체 리스트 출력 메소드
	public ArrayList<ScoreDTO> lists()
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		
		try
		{
			
			// 쿼리문 준비
			String sql = "SELECT SID, NAME, KOR, ENG, MAT"
					+ ", (KOR+ENG+MAT) AS TOT"
					+ ", (KOR+ENG+MAT)/3 AS AVG"
					+ ", RANK() OVER(ORDER BY (KOR+ENG+MAT) DESC) AS RANK"
					+ " FROM TBL_SCORE"
					+ " ORDER BY SID";
			
			// 작업 객체 생성
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			// ResultSet 
			ResultSet rs = pstmt.executeQuery();
			
			// 반복문
			while (rs.next())
			{
				// dto 객체 선언
				ScoreDTO dto = new ScoreDTO();
			
				dto.setSid(rs.getString("SID"));
				dto.setName(rs.getString("NAME"));
				dto.setKor(rs.getInt("KOR"));
				dto.setEng(rs.getInt("ENG"));
				dto.setMat(rs.getInt("MAT"));
				dto.setTot(rs.getInt("TOT"));
				dto.setAvg(rs.getDouble("AVG"));
				dto.setRank(rs.getInt("RANK"));
				
				result.add(dto);
			}
			
			// 연결 종료
			rs.close();
			pstmt.close();

		} catch (Exception e)
		{
			System.out.println(e.toString());
		}

		return result; 
	}
	
	
	// 이름 검색 출력 메소드
	public ArrayList<ScoreDTO> lists(String name)
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		
		try
		{
			// 쿼리문 준비
			String sql = "SELECT SID, NAME, KOR, ENG, MAT, TOT, AVG, RANK"
					   + " FROM ( SELECT SID, NAME, KOR, ENG, MAT,"
					   + " (KOR+ENG+MAT) AS TOT, (KOR+ENG+MAT)/3 AS AVG,"
					   + " RANK() OVER(ORDER BY (KOR+ENG+MAT) DESC) AS RANK FROM TBL_SCORE)"
					   + " WHERE NAME = ?";
			
			// 작업 객체 생성
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			// ? 전달하기
			pstmt.setString(1, name);
			
			// ResultSet 
			ResultSet rs = pstmt.executeQuery();
			// 반복문
			while (rs.next())
			{
				// dto 객체
			    ScoreDTO dto = new ScoreDTO();
				
				dto.setSid(rs.getString("SID"));
				dto.setName(rs.getString("NAME"));
				dto.setKor(rs.getInt("KOR"));
				dto.setEng(rs.getInt("ENG"));
				dto.setMat(rs.getInt("MAT"));
				dto.setTot(rs.getInt("TOT"));
				dto.setAvg(rs.getDouble("AVG"));
				dto.setRank(rs.getInt("RANK"));
				
				result.add(dto);
				
			}
			
			// 종료
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		} 
				
		return result;
	}

	
	// 번호 검색 담당 메소드
	public ArrayList<ScoreDTO> lists(int sid)
	{
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		
		try
		{
			// 쿼리문 준비
			String sql = "SELECT SID, NAME, KOR, ENG, MAT, TOT, AVG, RANK"
					   + " FROM ( SELECT SID, NAME, KOR, ENG, MAT, (KOR+ENG+MAT) AS TOT"
					   + ", (KOR+ENG+MAT)/3 AS AVG, RANK() OVER(ORDER BY (KOR+ENG+MAT) DESC) AS RANK"
					   + " FROM TBL_SCORE) WHERE SID = ?";
			
			// 작업객체생성
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			// ? 넣을거 넣기
			pstmt.setInt(1, sid);
			
			// Result set
			ResultSet rs = pstmt.executeQuery();
			
			//반복문
			while (rs.next())
			{
				// dto 객체 선언
				ScoreDTO dto = new ScoreDTO();
				
				dto.setSid(rs.getString("SID"));
				dto.setName(rs.getString("NAME"));
				dto.setKor(rs.getInt("KOR"));
				dto.setEng(rs.getInt("ENG"));
				dto.setMat(rs.getInt("MAT"));
				dto.setTot(rs.getInt("TOT"));
				dto.setAvg(rs.getDouble("AVG"));
				dto.setRank(rs.getInt("RANK"));
				
				result.add(dto);
			}
			
			// 객체 닫기
			rs.close();
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}

		return result; 
	
	}
	
	// 인원수 확인 담당 메소드
	public int count()
	{
		int result = 0;
		try
		{
			String sql = "SELECT COUNT(*) AS COUNT FROM TBL_SCORE";
			
			//작업객체 생성
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			//Result set 
			ResultSet rs = pstmt.executeQuery();
			
			// 반복문
			while (rs.next())
				result = rs.getInt("COUNT");
			
			// 종료
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		} 
		return result;
	}
	
	// 성적 수정 메소드
	public int modify(ScoreDTO dto)
	{
		int result =0;
		
		try
		{
			// 쿼리문 준비
			String sql = "UPDATE TBL_SCORE SET NAME = ?, KOR=?, ENG=?, MAT=? WHERE SID = ?";
			
			// 작업객체
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			// ? 채우기
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getKor());
			pstmt.setInt(3, dto.getEng());
			pstmt.setInt(4, dto.getMat());
			pstmt.setString(5, String.valueOf(dto.getSid()));
			
			result = pstmt.executeUpdate();

			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	// 성적 삭제 메소드
	public int remove(int sid)
	{
		int result = 0;
		
		try
		{
			// 쿼리문 준비
			String sql = "DELETE FROM TBL_SCORE WHERE SID = ?";
			
			// 작업객체 생성
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			// ? 채우기
			pstmt.setInt(1, sid);
			
			result = pstmt.executeUpdate();
		
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		
		return result;
	}
	
	// 추가
	// 시퀀스 마지막 번호 알아오는 메소드
	public int seq()
	{
		int result = 0;
		
		try
		{
			// sql 구문 준비
			String sql = "SELECT LAST_NUMBER"
					+ " FROM USER_SEQUENCES"
					+ " WHERE SEQUENCE_NAME = ?";
			
			// 작업객체 선언
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			//? 채우기
			pstmt.setString(1, "SCORESEQ");
			
			// Resultset
			ResultSet rs = pstmt.executeQuery();
			
			// 반복문
			if (rs.next())
				result = rs.getInt("LAST_NUMBER");
			
			rs.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return result; 
	}
	
	
}
