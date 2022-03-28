/*=======================
 	Test001.java
 	- 쿼리문 전송 실습
=========================*/


package com.test;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.util.DBConn;

public class Test001
{
	public static void main(String[] args)
	{
		try
		{
			Connection conn = DBConn.getConnection();
			
			if (conn != null)
			{
				System.out.println("데이터 베이스 연결 성공~!!");
				
				try
				{
					/*
					Statement stmt = conn.createStatement();
					
					String sql = "INSERT INTO TBL_MEMBER(SID, NAME, TEL)"
							+ " VALUES(MEMBERSEQ.NEXTVAL, '한충희', '010-5555-5555')";
					
					int result = stmt.executeUpdate(sql);
					
					if (result > 0)
						System.out.println("데이터 입력 성공~!!");
					
					stmt.close();
					DBConn.close();
					*/
					
					String sql = "INSERT INTO TBL_MEMBER(SID, NAME, TEL)"
							+ " VALUES(MEMBERSEQ.NEXTVAL, ?, ?)";  // 쿼리문 먼저 정의 해놔야함. '?'아님
					
					PreparedStatement pstmt = conn.prepareStatement(sql); // 매개변수 있는거 check
					
					// IN 매개변수 넘겨주기
					pstmt.setString(1, "박현수");        // 1번 물음표에 대하여
					pstmt.setString(2, "010-6666-6666");
					
					int result = pstmt.executeUpdate();  // 매개변수 없는거 check
					
					if (result > 0)
						System.out.println("데이터 입력 성공~!!");
					
					pstmt.close();
					DBConn.close();
					
					
				} catch (Exception e)
				{
					System.out.println(e.toString());
				}
			}

		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
}
