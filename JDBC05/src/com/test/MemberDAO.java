/*====================
  	MemberDAO.java
=====================*/

package com.test;

import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

public class MemberDAO
{
	// 주요속성 구성
	private Connection conn;
	
	// 데이터베이스 연결
	public Connection connection()
	{
		conn = DBConn.getConnection();
		return conn;
	}
	
	// 데이터 베이스 연결 종료
	public void cloes()
	{
		DBConn.close();
	}
	
	// 직원 데이터 입력
	public int add(MemberDTO dto) throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		String sql = String.format("INSERT INTO TBL_EMP"
						+ "(EMP_ID, EMP_NAME, SSN, IBSADATE, CITY_ID"
						+ ", TEL, BUSEO_ID, JIKWI_ID, BASICPAY, SUDANG)"
						+ " VALUES (EMPSEQ.NEXTVAL, '%s', '%s', TO_DATE('%s', 'YYYY-MM-DD')"
						+ ", (SELECT CITY_ID FROM TBL_CITY WHERE CITY_NAME = '%s') , '%s' "
						+ ", (SELECT BUSEO_ID FROM TBL_BUSEO WHERE BUSEO_NAME = '%s')"
						+ ", (SELECT JIKWI_ID FROM TBL_JIKWI WHERE JIKWI_NAME = '%s')"
						+ ", %d, %d)"
						, dto.getEmpName(), dto.getSsn(), dto.getIbsaDate()
						, dto.getCityName(),dto.getTel(), dto.getBuseoName(), dto.getJikwiName()
						, dto.getBasicPay(), dto.getSudang());
		
		result = stmt.executeUpdate(sql);
		stmt.close();
		
		return result;
	}// end add()
	
	// 전체 직원수 조회
	public int memberCount() throws SQLException
	{
		int result = 0;
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_EMP";

		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet → 반복문
		while (rs.next())
			result = rs.getInt("COUNT");		
		
		rs.close();
		stmt.close();
		
		return result;
	} // end memberCount()
	
	// 검색 결과 직원 수 조회 (memberCount() 메소드 오버로딩)
	// WHERE EMP_ID = 1001;			→ KEY : EMP_ID     	VALUE : 1001
	// WHERE EMP_NAME = '이호석'	→ KEY : EMP_NAME     	VALUE : '이호석'
	// WHERE BUSEO_NAME = '개발부'	→ KEY : BUSEO_NAME     VALUE : '개발부'
	// WHERE JIKWI_NAME = '대리'	→ KEY : JIKWI_NAME     VALUE : '대리'
	public int memberCount(String key, String value) throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		String sql = "";

		if (key.equals("EMP_ID"))
			sql = String.format("SELECT COUNT(*) AS COUNT FROM EMPVIEW WHERE %s = %s ", key, value);
		else
			sql = String.format("SELECT COUNT(*) AS COUNT FROM EMPVIEW WHERE %s = '%s' ", key, value);
	
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
			result = rs.getInt("COUNT");

		rs.close();
		stmt.close();
		
		
		return result;
	} // end memberCount();
	
	// 직원 데이터 전체 조회(사번/이름/부서/직위/급여내림차순)
	public ArrayList<MemberDTO> lists(String key) throws SQLException
	{
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		
		Statement stmt = conn.createStatement();
		String sql = String.format("SELECT EMP_ID, EMP_NAME, SSN, IBSADATE"
								 + ", CITY_NAME, NVL(TEL, '번호없음') AS TEL"
								 + ", BUSEO_NAME, JIKWI_NAME"
								 + ", BASICPAY, SUDANG, PAY FROM EMPVIEW"
								 + " ORDER BY %s", key);
		
		
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
		{
			MemberDTO dto = new MemberDTO();
			
			dto.setEmpId(rs.getInt("EMP_ID"));
			dto.setEmpName(rs.getString("EMP_NAME"));
			dto.setSsn(rs.getString("SSN"));
			dto.setIbsaDate(rs.getString("IBSADATE"));
			dto.setCityName(rs.getString("CITY_NAME"));
			dto.setTel(rs.getString("TEL"));
			dto.setBuseoName(rs.getString("BUSEO_NAME"));
			dto.setJikwiName(rs.getString("JIKWI_NAME"));
			dto.setBasicPay(rs.getInt("BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setPay(rs.getInt("PAY"));
			
			result.add(dto);
		}
	
		rs.close();
		stmt.close();
	
		return result;
		
	} // end lists()
	
	// 직원 데이터 검색 조회 (사번/이름/부서/직위) 
	public ArrayList<MemberDTO> serchLists(String key, String value) throws SQLException
	{
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		Statement stmt = conn.createStatement();
		String sql = "";
		
		if (key.equals("EMP_ID"))
		{
			sql = String.format("SELECT EMP_ID, EMP_NAME, SSN, IBSADATE"
					 + ", CITY_NAME, NVL(TEL, '번호없음') AS TEL"
					 + ", BUSEO_NAME, JIKWI_NAME"
					 + ", BASICPAY, SUDANG, PAY FROM EMPVIEW"
					 + " WHERE %s = %s"
					 + " ORDER BY EMP_ID", key, value);
		}
		else
		{
			sql = String.format("SELECT EMP_ID, EMP_NAME, SSN, IBSADATE"
					 + ", CITY_NAME, NVL(TEL, '번호없음') AS TEL"
					 + ", BUSEO_NAME, JIKWI_NAME"
					 + ", BASICPAY, SUDANG, PAY FROM EMPVIEW"
					 + " WHERE %s = '%s'"
					 + " ORDER BY EMP_ID", key, value);
		}
		
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
	MemberDTO dto = new MemberDTO();
			
			dto.setEmpId(rs.getInt("EMP_ID"));
			dto.setEmpName(rs.getString("EMP_NAME"));
			dto.setSsn(rs.getString("SSN"));
			dto.setIbsaDate(rs.getString("IBSADATE"));
			dto.setCityName(rs.getString("CITY_NAME"));
			dto.setTel(rs.getString("TEL"));
			dto.setBuseoName(rs.getString("BUSEO_NAME"));
			dto.setJikwiName(rs.getString("JIKWI_NAME"));
			dto.setBasicPay(rs.getInt("BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setPay(rs.getInt("PAY"));
			
			result.add(dto);
			
		}
		rs.close();
		stmt.close();
		
		return result;
	} // end searchList()
	
	
	// 지역 리스트 조회
	public ArrayList<String> searchCity() throws SQLException
	{
		ArrayList<String> result = new ArrayList<String>();
		
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT CITY_NAME FROM TBL_CITY";
		ResultSet rs = stmt.executeQuery(sql);
		while ( rs.next())
			result.add(rs.getString("CITY_NAME"));
		
		rs.close();
		stmt.close();
		
		return result;
		
	} //end searchCity()
	
	// 부서 리스트 조회
	public ArrayList<String> searchBuseo() throws SQLException
	{
		ArrayList<String> result = new ArrayList<String>();
		
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT BUSEO_NAME FROM TBL_BUSEO";
		ResultSet rs = stmt.executeQuery(sql);
		while ( rs.next())
			result.add(rs.getString("BUSEO_NAME"));
		
		rs.close();
		stmt.close();
		
		return result;
		
	} //end searchBuseo()
	
	// 직위 리스트 조회
	public ArrayList<String> searchJikwi() throws SQLException
	{
		ArrayList<String> result = new ArrayList<String>();
		
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT JIKWI_NAME FROM TBL_JIKWI";
		ResultSet rs = stmt.executeQuery(sql);
		while ( rs.next())
			result.add(rs.getString("JIKWI_NAME"));
		
		rs.close();
		stmt.close();
		
		return result;
		
	} //end searchJikwi()
	
	// 직위에 따른 최소 기본급 검색
	public int serchBasicPay(String jikwi) throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		
		String sql = String.format("SELECT MIN_BASICPAY FROM TBL_JIKWI WHERE JIKWI_NAME = '%s'", jikwi);
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
			result = rs.getInt("MIN_BASICPAY");
		
		rs.close();
		stmt.close();
			
		return result;
	}
	
	// 직원 데이터 수정 
	public int modify(MemberDTO dto) throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		String sql = String.format("UPDATE TBL_EMP"
								 + " SET EMP_NAME = '%s', SSN = '%s'"
								 + ", IBSADATE = TO_DATE('%s', 'YYYY-MM-DD')"
								 + ", CITY_ID = (SELECT CITY_ID FROM TBL_CITY WHERE CITY_NAME = '%s')"
								 + ", TEL = '%s'"
								 + ", BUSEO_ID = (SELECT BUSEO_ID FROM TBL_BUSEO WHERE BUSEO_NAME = '%s')"
								 + ", JIKWI_ID = (SELECT JIKWI_ID FROM TBL_JIKWI WHERE JIKWI_NAME = '%s')"
								 + ", BASICPAY = %d, SUDANG = %d WHERE EMP_ID = %d"
								 , dto.getEmpName(), dto.getSsn(), dto.getIbsaDate()
								 , dto.getCityName(), dto.getTel(), dto.getBuseoName(), dto.getJikwiName()
								 , dto.getBasicPay(), dto.getSudang(), dto.getEmpId());
		result = stmt.executeUpdate(sql);
		stmt.close();
		
		return result;
		
	}//end modify
	
	
	// 직원 데이터 삭제
	public int remove(int empId) throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		String sql = String.format("DELETE FROM TBL_EMP WHERE EMP_ID = %d", empId);
		result = stmt.executeUpdate(sql);
		
		stmt.close();

		return result;
	} // end remove()

	
} // end class
