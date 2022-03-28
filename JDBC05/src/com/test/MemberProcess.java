/*=======================================
   MemberProcess.java
   콘솔 기반 서브 메뉴 입출력 전용 클래스
 =======================================*/

package com.test;

import java.util.ArrayList;
import java.util.Scanner;

public class MemberProcess
{
	// 주요 속성 구성
	private MemberDAO dao;
	
	// 생성자 정의 (사용자 정의) 
	public MemberProcess()
	{
		dao = new MemberDAO();
	}
	
	public void memberInsert()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			// db 연결
			dao.connection();
			
			// 지역 리스트 구성
			 ArrayList<String> citys = dao.searchCity();
			 StringBuilder cityStr = new StringBuilder();
			 for (String city : citys)
				cityStr.append(city + "/");
			 
			 
			// 부서 리스트 구성
			ArrayList<String> buseos  = dao.searchBuseo();
			StringBuilder buseoStr = new StringBuilder();
			for (String buseo : buseos)
				buseoStr.append(buseo + "/");
				
			// 직위 리스트 구성
			ArrayList<String> jikwis = dao.searchJikwi();
			StringBuilder jikwiStr = new StringBuilder();
			for (String jikwi : jikwis)
				jikwiStr.append(jikwi + "/");
			
			
			
			// 사용자에게 보여지는 화면 처리
			/*
			직원 정보 입력 ----------------------------------------------------
			이름 : 김정용
			주민등록번호(yymmdd-nnnnnnn) : 960608-2345678
			입사일(yyyy-mm-dd) : 2019-06-08
			지역(강원/경기/경남/경북/부산/서울/인천/전남/전북/제주/충남/충북/) : 경기
			전화번호 : 010-2731-3333
			부서(개발부/기획부/영업부/인사부/자재부/총무부/홍보부/) : 개발부
			직위(사장/전무/상무/이사/부장/차장/과장/대리/사원/) : 대리
			기본급(최소 1800000 이상) : 5000000
			수당 : 2000000   
			
			직원 정보 입력 완료 ~!!!!! 
			------------------------------------------------------ 직원 정보 입력 
			*/
			System.out.println("직원 정보 입력 ----------------------------------------------------"   );
			System.out.print("이름 : ");
			String empName = sc.next();
			System.out.print("주민등록번호(yymmdd-nnnnnnn) : ");
			String ssn = sc.next();
			System.out.print("입사일(yyyy-mm-dd) : ");
			String ibsaDate = sc.next();
			System.out.printf("지역(%s) : ", cityStr.toString());
			String cityName = sc.next();
			System.out.print("전화번호 : ");
			String tel = sc.next();
			System.out.printf("부서(%s) : ", buseoStr.toString());
			String buseoName = sc.next();
			System.out.printf("직위(%s) : ", jikwiStr.toString());
			String jikwiName = sc.next();
			System.out.printf("기본급(최소 %d 이상) : ", dao.serchBasicPay(jikwiName));
			int basicpay = sc.nextInt();
			System.out.print("수당 : ");
			int sudang = sc.nextInt();
			System.out.println();
			
			MemberDTO dto = new MemberDTO();
			dto.setEmpName(empName);
			dto.setSsn(ssn);
			dto.setIbsaDate(ibsaDate);
			dto.setCityName(cityName);
			dto.setTel(tel);
			dto.setBuseoName(buseoName);
			dto.setJikwiName(jikwiName);
			dto.setBasicPay(basicpay);
			dto.setSudang(sudang);
			
			int result = dao.add(dto);
			
			if(result > 0)
				System.out.println("직원 정보 입력 완료 ~!!!!!" );
			System.out.println("----------------------------------------------------직원 정보 입력"   );
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				dao.cloes();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	} // end memberInsert();
	
	// 직원 전체 출력 메소드 정의
	public void memberList()
	{
		Scanner sc = new Scanner(System.in);
		
		// 서브 메뉴 출력 
		System.out.println();
		System.out.println("1. 사번 정렬");				// EMP_ID
		System.out.println("2. 이름 정렬");				// EMP_NAME
		System.out.println("3. 부서 정렬");				// BUSEO_NAME
		System.out.println("4. 직위 정렬");				// JIKWI_NAME
		System.out.println("5 급여 내림차순 정렬");		// PAY DESC
		System.out.print(">> 항목 선택(1~5, -1 : 종료) : ");
		
		String meunStr = sc.next();
		
		try
		{
			int menu = Integer.parseInt(meunStr);
			if (menu == -1)
				return;
			
			String key = "";
			
			switch (menu)
			{
			case 1:
				key = "EMP_ID";
				break;
			case 2:
				key = "EMP_NAME";
				break;
			case 3:
				key = "BUSEO_NAME";
				break;
			case 4:
				key = "JIKWI_NAME";
				break;
			case 5:
				key = "PAY DESC";
				break;

			}
			
			// db 연결
			dao.connection();
			
			System.out.println();
			System.out.printf("전체 인원 : %d명\n", dao.memberCount());
			System.out.println("사번   이름       주민번호     입사일 지역      전화번호   부서 직위  기본급   수당    급여");
			ArrayList<MemberDTO> memList = dao.lists(key);
			for (MemberDTO dto : memList)
			{
				System.out.printf("%d %s %s %s %s %9s %s %s %7d %7d %7d \n", dto.getEmpId(), dto.getEmpName(), dto.getSsn(), dto.getIbsaDate()
						 , dto.getCityName() , dto.getTel(), dto.getBuseoName(), dto.getJikwiName(), dto.getBasicPay()
			             , dto.getSudang(), dto.getPay());
			}

		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				dao.cloes();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}
	
	// 직원 검색 출력 메소드 정의
	public void memberSearch()
	{
		Scanner sc = new Scanner(System.in);
		
		//서브 메뉴 구성
		System.out.println();
		System.out.println("1. 사번 검색"); // EMP_ID
		System.out.println("2. 이름 검색");	// EMP_NAME
		System.out.println("3. 부서 검색");	// BUSEO_NAME
		System.out.println("4. 직위 검색");	// JIKWI_NAME 
		System.out.print(">>항목 선택(1~4, -1종료) : ");
		String meunStr = sc.next();	
		
		try
		{
			int menu = Integer.parseInt(meunStr);
			if (menu == -1)
				return;
			
			String key = "";
			String value = "";
			
			switch (menu)
			{
			case 1: 
				key = "EMP_ID";
				System.out.print("검색할 사원 번호 입력 : ");
				value = sc.next();
				break;
				
			case 2:
				key = "EMP_NAME";
				System.out.print("검색할 이름 입력 : ");
				value = sc.next();
				break;
				
			case 3:
				key = "BUSEO_NAME";
				System.out.print("검색할 부서 입력 : ");
				value = sc.next();
				break;
				
			case 4:
				key = "JIKWI_NAME";
				System.out.print("검색할 직위 입력 : ");
				value = sc.next();
				break;
			}
			
			dao.connection();
			
			System.out.println();
			System.out.printf("검색 인원 : %d명\n", dao.memberCount(key, value));
			System.out.println("사번   이름       주민번호     입사일 지역      전화번호   부서 직위  기본급   수당    급여");
			ArrayList<MemberDTO> memList = dao.serchLists(key, value);
			for (MemberDTO dto : memList)
			{
				System.out.printf("%d %s %s %s %s %9s %s %s %7d %7d %7d \n", dto.getEmpId(), dto.getEmpName(), dto.getSsn(), dto.getIbsaDate()
						 , dto.getCityName() , dto.getTel(), dto.getBuseoName(), dto.getJikwiName(), dto.getBasicPay()
			             , dto.getSudang(), dto.getPay());
			}

			
 			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				dao.cloes();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
		
	} //end memberSearch()
	
	// 직원 정보 수정 메소드 정의
	public void memberUpdate()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			//수정할 대상 입력받기
			System.out.print("수정할 직원의 사원번호 입력 : ");
			String value = sc.next();
			
			
			
			dao.connection();
			
			ArrayList<MemberDTO> memList = dao.serchLists("EMP_ID", value);
			
			if (memList.size() > 0)
			{
				// 수정 대상을 찾은 경우
				// 지역 리스트 구성
				 ArrayList<String> citys = dao.searchCity();
				 StringBuilder cityStr = new StringBuilder();
				 for (String city : citys)
					cityStr.append(city + "/");
				 
				 
				// 부서 리스트 구성
				ArrayList<String> buseos  = dao.searchBuseo();
				StringBuilder buseoStr = new StringBuilder();
				for (String buseo : buseos)
					buseoStr.append(buseo + "/");
					
				// 직위 리스트 구성
				ArrayList<String> jikwis = dao.searchJikwi();
				StringBuilder jikwiStr = new StringBuilder();
				for (String jikwi : jikwis)
					jikwiStr.append(jikwi + "/");
				
				/*
				직원 정보 수정 ----------------------------------------------------
				기존 이름 : 김정용
				수정 이름 : 
				기존 주민등록번호 : 960608-2345678
				수정 주민등록번호 (yymmdd-nnnnnnn) : 
				기존 입사일 : 2019-06-08
				수정 입사일 (yyyy-mm-dd) : 
				기존 지역: 경기
				수정 지역(강원/경기/경남/경북/부산/서울/인천/전남/전북/제주/충남/충북/) : 강원
				기존 전화번호 : 010-2731-3333
				수정 전화번호 : 
				기존 부서 : 개발부
				수정 부서 (개발부/기획부/영업부/인사부/자재부/총무부/홍보부/) : 기획부
				기존 직위 : 대리
				수정 부서(사장/전무/상무/이사/부장/차장/과장/대리/사원/) : 사원
				기존 기본급 : 3000000
				수정 기본급(최소 1600000 이상) : 
				수당 : 1000000   
				
				직원 정보 입력 완료 ~!!!!! 
				------------------------------------------------------ 직원 정보 수정
				  
				기존값 그대로 쓸 꺼면 - 을 입력 받아서 처리하게 하자...  
				  */
				
				
				/*
				 * String name = "", ssn = "", ibsadate = "", city = "", tel = "", buseo = "" ,
				 * jikwi = "", basic = "", su = ""; int basicPay=0, sudang = 0;
				 * 
				 * 
				 * 
				 * for (MemberDTO dto : memList) { System.out.printf("기존 이름 : %s\n",
				 * dto.getEmpName()); System.out.print("수정 이름(- 입력시 기존값) : "); name = sc.next();
				 * if (name.equals("-")) name = dto.getEmpName();
				 * 
				 * System.out.printf("기존 주민등록번호 : %s\n", dto.getSsn());
				 * System.out.print("수정 주민등록번호 (yymmdd-nnnnnnn) : "); ssn = sc.next(); if
				 * (ssn.equals("-")) ssn = dto.getSsn();
				 * 
				 * System.out.printf("기존 입사일 : %s\n", dto.getIbsaDate());
				 * System.out.print("수정 입사일 (yyyy-mm-dd) : "); ibsadate = sc.next(); if
				 * (ibsadate.equals("-")) ibsadate = dto.getIbsaDate();
				 * 
				 * System.out.printf("기존 지역 : %s\n", dto.getCityName());
				 * System.out.printf("수정 지역(%s) : ", cityStr.toString()); city = sc.next(); if
				 * (city.equals("-")) city = dto.getCityName();
				 * 
				 * System.out.printf("기존 전화번호 : %s\n", dto.getTel());
				 * System.out.print("수정 전화번호 : "); tel = sc.next(); if (tel.equals("-")) tel =
				 * dto.getTel();
				 * 
				 * System.out.printf("기존 부서 : %s\n", dto.getBuseoName());
				 * System.out.printf("수정 부서(%s) : ", buseoStr.toString()); buseo = sc.next(); if
				 * (buseo.equals("-")) buseo = dto.getBuseoName();
				 * 
				 * System.out.printf("기존 직급 : %s\n", dto.getJikwiName());
				 * System.out.printf("수정 직급(%s) : ", jikwiStr.toString()); jikwi = sc.next(); if
				 * (jikwi.equals("-")) jikwi = dto.getJikwiName();
				 * 
				 * System.out.printf("기존 기본급 : %d\n", dto.getBasicPay());
				 * System.out.printf("수정 기본급 (최소 %d원): " ,dao.serchBasicPay(jikwi)); basic =
				 * sc.next(); if (basic.equals("-")) basicPay = dto.getBasicPay(); else basicPay
				 * = Integer.parseInt(basic);
				 * 
				 * System.out.printf("기존 수당 : %d\n", dto.getSudang());
				 * System.out.print("수정 수당 : "); su = sc.next(); if (su.equals("-")) sudang =
				 * dto.getSudang(); else sudang = Integer.parseInt(su);
				 * 
				 * }
				 * 
				 * int empId = Integer.parseInt(value);
				 * 
				 * MemberDTO dto = new MemberDTO(); dto.setEmpId(empId); dto.setEmpName(name);
				 * dto.setSsn(ssn); dto.setIbsaDate(ibsadate); dto.setCityName(city);
				 * dto.setTel(tel); dto.setBuseoName(buseo); dto.setJikwiName(jikwi);
				 * dto.setBasicPay(basicPay); dto.setSudang(sudang);
				 * 
				 * int result = dao.modify(dto);
				 */
				
				MemberDTO mMember = memList.get(0);
				
				int mEmpId = mMember.getEmpId();
				String mEmpName = mMember.getEmpName();
				String mSsn = mMember.getSsn();
				String mIbsaDate = mMember.getIbsaDate();
				String mCityName = mMember.getCityName();
				String mTel = mMember.getTel();
				String mBuseoName = mMember.getBuseoName();
				String mJikwiName = mMember.getJikwiName();
				int mBasicPay = mMember.getBasicPay();
				int mSudang = mMember.getSudang();
				
				System.out.println();
				System.out.println("직원 정보 수정--------------------------------------------");
				System.out.println("기존값을 유지하려면 - 를 입력하세요..");
				System.out.printf("기존 이름 : %s%n", mEmpName);
				System.out.print("수정 이름 : ");
				String empName = sc.next();
				if (empName.equals("-"))
					empName = mEmpName;
				System.out.printf("기존 주민번호(yymmdd-nnnnnnn) : %s%n", mSsn);
				System.out.print("수정 주민번호(yymmdd-nnnnnnn) : ");
				String ssn = sc.next();
				if (ssn.equals("-"))
					ssn = mSsn;
				System.out.printf("기존 입사일(YYYY-MM-DD) : %s%n", mIbsaDate);
				System.out.print("수정 입사일(YYYY-MM-DD) : ");
				String ibsaDate = sc.next();
				if (ibsaDate.equals("-"))
					ibsaDate = mIbsaDate;
				System.out.printf("기존 지역 : %s%n", mCityName);
				System.out.printf("수정 지역(%s) : ", cityStr.toString());
				String cityName = sc.next();
				if (cityName.equals("-"))
					cityName = mCityName;
				System.out.printf("기존 전화번호 : %s%n", mTel);
				System.out.print("수정 전화번호 : ");
				String tel = sc.next();
				if (tel.equals("-"))
					tel = mTel;
				System.out.printf("기존 부서 : %s%n", mBuseoName);
				System.out.printf("수정 부서(%s) : ",buseoStr.toString());
				String buseoName = sc.next();
				if (buseoName.equals("-"))
					buseoName = mBuseoName;
				System.out.printf("기존 직위 : %s%n", mJikwiName);
				System.out.printf("수정 직위(%s) : ",jikwiStr.toString());
				String jikwiName = sc.next();
				if (jikwiName.equals("-"))
					jikwiName = mJikwiName;
	
				
				 System.out.printf("기존 기본급 : %d\n", mBasicPay);
				 System.out.printf("수정 기본급 (최소 %d원): " ,dao.serchBasicPay(jikwiName)); 
				 int basicPay = 0;
				 String basicPayStr = sc.next();
				 if (basicPayStr.equals("-")) 
					 basicPay = mBasicPay;
				 else 
					 basicPay = Integer.parseInt(basicPayStr);

				
				System.out.printf("기존 수당 : %d%n", mSudang);
				System.out.print("수정 수당 : ");
				int sudang = 0;
				String sudangStr = sc.next();
				if (sudangStr.equals("-"))
					sudang = mSudang;
				else 
				    sudang = Integer.parseInt(sudangStr);
				
				// MemberDTO 생성 및 구성
				MemberDTO member = new MemberDTO();
				member.setEmpId(mEmpId);
				member.setEmpName(empName);
				member.setSsn(ssn);
				member.setIbsaDate(ibsaDate);
				member.setCityName(cityName);
				member.setTel(tel);
				member.setBuseoName(buseoName);
				member.setJikwiName(jikwiName);
				member.setBasicPay(basicPay);
				member.setSudang(sudang);
				
				int result = dao.modify(member);

				
				if (result > 0)
					System.out.println("직원 정보 수정 완료 ~!!!!! ");
				
			} else
			{
				System.out.println("수정 대상을 검색하지 못했습니다.");
			}
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				dao.cloes();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}

	} // end memberUpdate()
	
	public void memberDelete()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
		
			System.out.print("삭제할 직원의 사원번호 입력 : ");
			String value = sc.next();
			
			dao.connection();
			ArrayList<MemberDTO> members = dao.serchLists("EMP_ID", value);
			
			if (members.size() > 0)
			{
				System.out.println();
				System.out.println("사번   이름       주민번호     입사일 지역      전화번호   부서 직위  기본급   수당    급여");
				
				for (MemberDTO dto : members)
				{
					System.out.printf("%d %s %s %s %s %9s %s %s %7d %7d %7d \n", dto.getEmpId(), dto.getEmpName(), dto.getSsn(), dto.getIbsaDate()
							 , dto.getCityName() , dto.getTel(), dto.getBuseoName(), dto.getJikwiName(), dto.getBasicPay()
				             , dto.getSudang(), dto.getPay());
				}

				System.out.print(">> 정말 삭제하시겠습니까?? (Y/N) : ");
				String response = sc.next();
				
				if (response.equalsIgnoreCase("y"));
				{
					int result = dao.remove(Integer.parseInt(value));
					if (result > 0)
						System.out.println("직원 정보가 정상적으로 삭제되었습니다.");
				}
			}
			else
			{
				System.out.println("입력한 번호의 사원이 존재하지 않습니다.");
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				dao.cloes();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}

	}// end memberDelete()

} // end class
