package com.test;

import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

public class MemberDAO
{
	// 연결 객체 생성
	private Connection conn;
	// 연결용 메소드 생성
	public Connection connection()
	{
		conn = DBConn.getConnection();
		return conn;
	}
	
	//직원 데이터 입력 용 메소드
	public int add(MemberDTO dto) throws SQLException 
	{
		int result = 0;
		
		Statement stmt = conn.createStatement();
		
		String sql = String.format("INSERT INTO TBL_EMP"
								 + "(EMP_ID, EMP_NAME, SSN, IBSADATE, CITY_ID"
								 + ", TEL, BUSEO_ID, JIKWI_ID, BASICPAY, SUDANG)"
							     + " VALUES(EMPSEQ.NEXTVAL, '%s', '%s', TO_DATE('%s', 'YYYY-MM-DD')"
							     + ", (SELECT CITY_ID FROM TBL_CITY WHERE CITY_NAME = '%s') , '%s'"
							     + ", (SELECT BUSEO_ID FROM TBL_BUSEO WHERE BUSEO_NAME = '%s')"
							     + ", (SELECT JIKWI_ID FROM TBL_JIKWI WHERE JIKWI_NAME = '%s')"
							     + ", %d, %d)"
							     , dto.getName(), dto.getSsn(), dto.getIbsadate(), dto.getCity(), dto.getTel(), dto.getBuseo()
							     , dto.getJikwi(), dto.getBasicpay(), dto.getSudang());
		
		
		result = stmt.executeUpdate(sql);
		stmt.close();
		
		return result;
	}
	
	
	//전체 직원 수 출력
	public int count() throws SQLException 
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_EMP";
		
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			result = rs.getInt("COUNT");
		}
		
		rs.close();
		stmt.close();
		
		return result;
	}
	
	
	//직원 전체리스트 조회(정렬 기준)
	public ArrayList<MemberDTO> lists() throws SQLException
	{
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		
		Statement stmt = conn.createStatement();
		String sql = "SELECT EMP_ID, EMP_NAME, SSN, IBSADATE, CITY_NAME, TEL, BUSEO_NAME, JIKWI_NAME, BASICPAY, SUDANG, PAY FROM EMPVIEW";
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			MemberDTO dto = new MemberDTO();		
			dto.setEmpid(rs.getInt("EMP_ID"));
			dto.setName(rs.getString("EMP_NAME"));
			dto.setSsn(rs.getString("SSN"));
			dto.setIbsadate(rs.getString("IBSADATE"));
			dto.setCity(rs.getString("CITY_NAME"));
			dto.setTel(rs.getString("TEL"));
			dto.setBuseo(rs.getString("BUSEO_NAME"));
			dto.setJikwi(rs.getString("JIKWI_NAME"));
			dto.setBasicpay(rs.getInt("BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setPay(rs.getInt("PAY"));
			
			result.add(dto);
		}
		
		rs.close();
		stmt.close();
		return result;
		
	}
	
	
	//직원  전체 리스트  정렬 조회(사번/이름/부서/직위/ 급여 내림차순)  ex) key : EMP_ID
	public ArrayList<MemberDTO> lists(String key) throws SQLException
	{
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		
		Statement stmt = conn.createStatement();
		String sql = String.format("SELECT EMP_ID, EMP_NAME, SSN, IBSADATE, CITY_NAME, TEL, BUSEO_NAME"
				+ ", JIKWI_NAME, BASICPAY, SUDANG, PAY"
				+ " FROM EMPVIEW"
				+ " ORDER BY %s", key); 
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			MemberDTO dto = new MemberDTO();		
			dto.setEmpid(rs.getInt("EMP_ID"));
			dto.setName(rs.getString("EMP_NAME"));
			dto.setSsn(rs.getString("SSN"));
			dto.setIbsadate(rs.getString("IBSADATE"));
			dto.setCity(rs.getString("CITY_NAME"));
			dto.setTel(rs.getString("TEL"));
			dto.setBuseo(rs.getString("BUSEO_NAME"));
			dto.setJikwi(rs.getString("JIKWI_NAME"));
			dto.setBasicpay(rs.getInt("BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setPay(rs.getInt("PAY"));
			
			result.add(dto);
		}
		
		rs.close();
		stmt.close();
		return result;
	}
	
	// 직원 검색 리스트 (사번/ 이름/ 부서/ 직위) ex) key : EMP_ID  value : '1001'
	public ArrayList<MemberDTO> lists(String key, String value) throws SQLException
	{
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		
		Statement stmt = conn.createStatement();
		String sql = String.format("SELECT EMP_ID, EMP_NAME, SSN, IBSADATE, CITY_NAME, TEL, BUSEO_NAME"
				+ ", JIKWI_NAME, BASICPAY, SUDANG, PAY"
				+ " FROM EMPVIEW"
				+ " WHERE %s = '%s' ", key, value); 
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			MemberDTO dto = new MemberDTO();		
			dto.setEmpid(rs.getInt("EMP_ID"));
			dto.setName(rs.getString("EMP_NAME"));
			dto.setSsn(rs.getString("SSN"));
			dto.setIbsadate(rs.getString("IBSADATE"));
			dto.setCity(rs.getString("CITY_NAME"));
			dto.setTel(rs.getString("TEL"));
			dto.setBuseo(rs.getString("BUSEO_NAME"));
			dto.setJikwi(rs.getString("JIKWI_NAME"));
			dto.setBasicpay(rs.getInt("BASICPAY"));
			dto.setSudang(rs.getInt("SUDANG"));
			dto.setPay(rs.getInt("PAY"));
			
			result.add(dto);
		}
		
		rs.close();
		stmt.close();
		return result;
	}
	
	//직원 정보 수정
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
								 + ", BASICPAY = %d, SUDANG = %d"
								 + " WHERE EMP_ID = %d, args"
								 , dto.getName(), dto.getSsn(), dto.getIbsadate(), dto.getCity(), dto.getTel(), dto.getBuseo(), dto.getJikwi(), dto.getBasicpay(), dto.getSudang(), dto.getEmpid());
		
		result = stmt.executeUpdate(sql);
		stmt.close();
		
		return result;		
	}
	
	
	//직원 정보 삭제
	public int delete(int empid) throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		
		String sql = String.format("DELETE FROM TBL_EMP WHERE EMP_ID = %d", empid);
		
		
		result = stmt.executeUpdate(sql);
		stmt.close();
		
		return result;	
	}
	
	// 지역(강원/경기/경남/경북/부산/서울/인천/전남/전북/제주/충남/충북/) 을 만들기 위한 메소드
	public ArrayList<String> city() throws SQLException
	{
		ArrayList<String> result = new ArrayList<String>();
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT CITY_NAME FROM TBL_CITY";
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			result.add(rs.getString("CITY_NAME"));
		}
		
		rs.close();
		stmt.close();
		
		return result;
	}
	
	// 부서(개발부/기획부/영업부/인사부/자재부/총무부/홍보부/) 
	public ArrayList<String> buseo() throws SQLException
	{
		ArrayList<String> result = new ArrayList<String>();
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT BUSEO_NAME FROM TBL_BUSEO";
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			result.add(rs.getString("BUSEO_NAME"));
		}
		
		rs.close();
		stmt.close();
		
		return result;
	}
	
	// 직위(사장/전무/상무/이사/부장/차장/과장/대리/사원/)
	public ArrayList<String> jikwi() throws SQLException
	{
		ArrayList<String> result = new ArrayList<String>();
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT JIKWI_NAME FROM TBL_JIKWI";
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			result.add(rs.getString("JIKWI_NAME"));
		}
		
		rs.close();
		stmt.close();
		
		return result;
	}
	
	// 기본급 조회 쿼리
	public int basicpay(String jikwi) throws SQLException
	{
		int result = 0;
		Statement stmt = conn.createStatement();
		
		String sql = String.format("SELECT MIN_BASICPAY FROM TBL_JIKWI WHERE JIKWI_NAME = '%s'", jikwi);
		
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			result = rs.getInt("MIN_BASICPAY");
		}
		rs.close();
		stmt.close();
		
		return result;
	}
	
	
	// 연결 종료 
	public void close()
	{
		DBConn.close();
	}
	
	
}
