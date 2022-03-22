/*==========================================
 	Test003.java
 	- 데이터베이스 연결 실습
 	- 데이터 입력 실습
 ====================================== */

package com.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.util.DBConn;

public class Test003
{
	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
		// 연결 객체 생성(구성)(광산 와이어..)
		Connection conn = DBConn.getConnection();
		
		if (conn == null)
		{
			System.out.println("데이터 베이스 연결 실패~~!!");
			System.exit(0);
		}
		
		//System.out.println("데이터베이스 연결 성공~!!!");
		
		try
		{	
			// 작업 객체 구성(준비) (광산 와이어에 수레를 달았다..)
			Statement stmt = conn.createStatement();
			
			// ※ 데이터 입력 쿼리 실행 과정
			//    한 번 실행하면 다시 실행하지 못하는 상황이다.
			//	  기본키 제약조건이 설정되어 있으므로
			//    동일한 키 값이 중복될 수 없기 때문이다.
						
			// 쿼리문 구성(준비)(수레에 담을 쪽지..)
			String sql = "INSERT INTO TBL_MEMBER(SID, NAME, TEL) VALUES(2, '김민성', '010-5154-6311')";
			//-- 주의. 쿼리문 끝에 『;』를 붙이지 않는다.
			//-- 주의. 자바에서 실행한 DML 구문은 내부적으로 자동 COMMIT 된다.
			//-- 주의. 오라클에서 트랜잭션 처리가 끝나지 않으면 (ex. 오라클에서 테이블 업데이트 후 커밋안함)
			//         데이터 액션 처리가 이루어지지 않는다.
			
			
			//stmt.executeQuery();  → 오라클에서 데이터들이 변화가 없다.. (단순 셀렉문 등.)
			//stmt.executeUpdate(); → 오라클에서 데이터들이 변화가 있다.. (insert, delete, update, 등...)
			
			// 실은 쪽지를 오라클(광산안)한테 보내줌
			int result = stmt.executeUpdate(sql);
			// 바뀐 행의 갯수 만큼 숫자를 반환(result에)해줌
			
			if (result > 0)
			{
				System.out.println("데이터 입력 성공~!!!");
			}
			else
			{
				System.out.println("입력 실패 ~ ㅠ.ㅠ");
			}
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		DBConn.close();
	}
}
//실행결과        
//데이터 입력 성공~!!!





























































