/*===============================
 	Test005.java
	- 테이블 내의 데이터 읽어오기
 ===============================*/
package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.util.DBConn;

public class Test005
{
	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
		// 연결 객체 생성(광산 와이어)
		Connection conn = DBConn.getConnection();
		
		if (conn != null)
		{
			System.out.println("데이터 베이스 연결 성공~!!!");
			
			try
			{
				// 작업 객체 생성 (광산 수레)
				Statement stmt = conn.createStatement();
				
				// 쿼리문 준비(select) (광산 쪽지)
				String sql = "SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID";
				/*
				 String sql = "SELECT SID, NAME, TEL"
				  			   + " FROM TBL_MEMBER"
				  			   + " ORDER BY SID";
				 */
				// ※ 쿼리문을 구성하는 과정에서 공백이나 개행 처리 check~!!
				
				// ※ executeQuery() 메소드를 사용하면
				//    질의 결과를 ResultSet 객체로 가져올 수 있다.
				//    하지만, ResultSet 객체가 질의에 대한 결과물 모두를
				//    한꺼번에 갖고있는 구조는 아니다.
				//    단지, 데이터베이스로부터 획득한 질의 결과물에 대한
				//    관리가 가능한 상태가 되는 것이다.
				//    이 때문에 데이터베이스와의 연결을 끊게되면
				//    ResultSet 객체는 더이상 질의결과를 관리할 수 없게 된다.
			
				// 쿼리문 실행 (수레 보내서 가져옴)
				ResultSet rs = stmt.executeQuery(sql);
				
				//DBConn.close();
				
				// ResultSet 에 대한 처리 (반복문 구성)
				while (rs.next())
				{
					String sid = rs.getString("SID");
					String name = rs.getString("NAME");
					String tel = rs.getString("tel");
					
					String str = String.format("%3s %8s %12s", sid, name, tel);
					
					System.out.println(str);
				}
				
				// ResultSet 리소스 반납
				rs.close();
				// Statment 리소스 반납
				stmt.close(); 
				
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
			
		} 
		else
		{
			System.out.println("데이터 베이스 연결 실패~!!!");
		}
		
		DBConn.close();
		
		System.out.println("데이터베이스 연결 닫힘~!!!");
		System.out.println("프로그램 종료됨~!!!");
	}
}
/*
 데이터 베이스 연결 성공~!!!
  1      홍길동 010-1111-1111
  2      김민성 010-5154-6311
  3      김정용 010-3333-3333
  4      오이삭 010-4444-4444
  5      김태형 010-5555-5555
데이터베이스 연결 닫힘~!!!
프로그램 종료됨~!!!
 */
