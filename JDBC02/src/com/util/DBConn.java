/*======================d
 	DBConn.java
 	- 예외처리 throws
 */

package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn
{
	// 연결확인용 Connection 변수 dbConn 선언
	private static Connection dbConn;
	
	// 매개변수 없는 getConnection() 메소드 만들기
	public static Connection getConnection() throws ClassNotFoundException, SQLException
	{
		if (dbConn == null)
		{

				String url = "jdbc:oracle:thin:@localhost:1521:xe";
				String user = "scott";
				String pwd = "tiger";
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
				dbConn = DriverManager.getConnection(url, user, pwd);

		}
		return dbConn;
	}	
	
	//매개변수 있는 getConnection() 메소드 만들기
	public static Connection getConnection(String url, String user, String pwd) throws ClassNotFoundException, SQLException
	{
		if (dbConn == null)
		{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				dbConn = DriverManager.getConnection(url, user, pwd);
		}
		
		return dbConn;
	}
	
	// close() 종료 메소드 만들기
	public static void close() throws SQLException
	{
		if (dbConn != null)
		{
				if(!dbConn.isClosed())
					dbConn.close();
		}
		dbConn = null;
	}
}











