/*==================
  DBConn.java
  try~catch 로 구성
 ==================*/

package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn
{
	// 연결위한 변수 dbConn 선언
	private static Connection dbConn;
	
	// 매개변수 없는 Connection 타입 getConnection() 메소드 생성
	public static Connection getConnection()
	{
		// 연결 상태 확인
		if (dbConn == null)
		{
			try
			{
				// url, user, pwd 구성
				String url = "jdbc:oracle:thin:@localhost:1521:xe";
				String user = "scott";
				String pwd = "tiger";
				
				// Class.forName 해오기
				Class.forName("oracle.jdbc.driver.OracleDriver");
				// dbConn에 담기
				dbConn = DriverManager.getConnection(url, user, pwd);
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
			
		}

		return dbConn;
	}
	
	// 매개변수 있는 Connection 타입 getConnection() 메소드 생성
	public static Connection getConnection(String url, String user, String pwd)
	{
		// 연결 확인
		if (dbConn == null)
		{
			try
			{
				// Class.forName
				Class.forName("oracle.jdbc.driver.OracleDriver");
				dbConn = DriverManager.getConnection(url, user, pwd);
				
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
			
		return dbConn;
	}
	
	// 종료 메소드 close()
	
	public static void close()
	{
		// 연결 확인
		if (dbConn != null)
		{
			// 연결 재확인
			try
			{
				if(dbConn.isClosed())
					dbConn.close(); // 연결 끊기
			} catch (SQLException e)
			{
				System.out.println(e.toString());
			} 
		}
		dbConn = null;
	}
}


































