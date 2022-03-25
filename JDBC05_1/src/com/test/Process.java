package com.test;

import java.util.ArrayList;
import java.util.Scanner;

public class Process
{
	// 주요 속성 구성	→ 데이터베이스 액션 처리 전담 객체 → MemberDAO
	private MemberDAO dao;
	
	// 생성자 정의(사용자 정의 생성자)
	public Process()
	{
		dao = new MemberDAO();
	}
	
	// 직원 정보 입력 메소드 → 입력 받은걸로 dto 객체 생성해서 DAO에 있는 add(dto) 해줄것
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
	public void memberInsert()
	{
		try
		{
			// db 연결
			dao.connection();
			Scanner sc = new Scanner(System.in);
			
			System.out.println();
			System.out.println("직원 정보 입력 ----------------------------------------------------");
			//1
			System.out.print("이름 : ");
			String name = sc.next();
			//2
			System.out.print("주민등록번호(yymmdd-nnnnnnn) : ");
			String ssn = sc.next();
			//3
			System.out.print("입사일(yyyy-mm-dd) : ");
			String ibsadate = sc.next();
			
			//4 지역 리스트 함수 불러와서 문자로 담아서 넣어줄꺼임
			ArrayList<String> city = dao.city();
			String  citystr = "";
			
			for (String str : city)
			{
				citystr += str + "/"; 
			}
			System.out.printf("지역(%s) : ", citystr);
			String cityname = sc.next();
			//5
			System.out.print("전화번호 : ");
			String tel = sc.next();
			
			//6 부서(개발부/기획부/영업부/인사부/자재부/총무부/홍보부/) :
			ArrayList<String> buseo = dao.buseo();
			String buseostr = "";
			for (String str : buseo)
			{
				buseostr += str + "/";
			}
			System.out.printf("부서(%s) : ", buseostr);
			String buseoname = sc.next();
			
			//7 직위(사장/전무/상무/이사/부장/차장/과장/대리/사원/) : 대리
			ArrayList<String> jik = dao.jikwi();
			
			String jikwistr = "";
			
			for (String str : jik)
			{
				jikwistr += str + "/";
			}
			
			System.out.printf("직위(%s) : ", jikwistr);
			String jikname = sc.next();
			 
			//8 기본급(최소 1800000 이상) :  → jikname 으로 판별
			
			int basicpay;
			do
			{
				System.out.printf("기본급 (최소 %d 이상) : ", dao.basicpay(jikname));
				basicpay = sc.nextInt();
				
				if (basicpay >= dao.basicpay(jikname))
					break;
				
			} while (true);
					
			//9
			System.out.print("수당 : ");
			int sudang = sc.nextInt();
			
			// dto 만들어서 dao.add(dto) 해줘야함
			MemberDTO dto = new MemberDTO();
			dto.setName(name);
			dto.setSsn(ssn);
			dto.setIbsadate(ibsadate);
			dto.setCity(cityname);
			dto.setTel(tel);
			dto.setBuseo(buseoname);
			dto.setJikwi(jikname);
			dto.setBasicpay(basicpay);
			dto.setSudang(sudang);
			
			int result = dao.add(dto);
			
			if (result > 0)
				System.out.println("직원 정보 입력 완료 ~!!!!! ");
			
			System.out.println("------------------------------------------------------ 직원 정보 입력 ");
			
			// db 종료
			dao.close();
			
		} catch (Exception e)
		{
			// db 종료
			dao.close();
			System.out.println(e.toString());
		}
		
	}
	
	//2. 직원 전체 출력 메소드 	- 사번 정렬- 이름 정렬- 부서 정렬- 직위 정렬
	public void memberlist()
	{
		// 서브 메뉴 위한 스캐너 구성
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("1. 사번 정렬"); // EMP_ID
		System.out.println("2. 이름 정렬");	// EMP_NAME
		System.out.println("3. 부서 정렬");	// BUSEO_NAME
		System.out.println("4. 직위 정렬");	// JIKWI_NAME
		System.out.println("5. 급여 내림차순 정렬");// PAY DESC
		System.out.print(">> 항목 선택(1~5, -1종료) : ");
		String menus = sc.next();	
		
		try
		{
			int menu = Integer.parseInt(menus);
			
			if (menu == -1)
				return;
			
			// 정렬 기준 담을 변수
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
			
			dao.connection();
			
			int count = dao.count();
			// 출력하기
			System.out.println();
			System.out.printf("전체 인원 : %d명\n" , count);
			System.out.println("사번   이름       주민번호     입사일 지역      전화번호   부서 직위  기본급   수당    급여");
			System.out.println("--------------------------------------------------------------------------------------------");
			
			ArrayList<MemberDTO> memList = dao.lists(key);
			
			for (MemberDTO dto : memList)
			{
				System.out.printf("%d %s %s %s %s %13s %s %s %7d %d %7d \n" , dto.getEmpid(), dto.getName(), dto.getSsn(), dto.getIbsadate(), dto.getCity()
								             , dto.getTel(), dto.getBuseo(), dto.getJikwi(), dto.getBasicpay()
								             , dto.getSudang(), dto.getPay());
			}
			
			
			dao.close();
			
			
			
		} catch (Exception e)
		{
			dao.close();
			System.out.println(e.toString());
		}
		
	}
	
	// 직원 검색 
	public void memSearch()
	{
		// 서브 메뉴 구성 위한 스캐너 생성
		Scanner sc = new Scanner(System.in);
		
		
		System.out.println();
		System.out.println("1. 사번 검색"); // EMP_ID
		System.out.println("2. 이름 검색");	// EMP_NAME
		System.out.println("3. 부서 검색");	// BUSEO_NAME
		System.out.println("4. 직위 검색");	// JIKWI_NAME 
		System.out.print(">>항목 선택(1~4, -1종료) : ");
		String menus = sc.next();
		
		try
		{
			int menu = Integer.parseInt(menus);
			
			if (menu == -1)
				return;
			
			// 검색 기준 담을 변수
			String key = "";
			// 검색 값 담을 변수
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
			System.out.println("사번   이름       주민번호     입사일 지역      전화번호   부서 직위  기본급   수당    급여");
			System.out.println("--------------------------------------------------------------------------------------------");
			ArrayList<MemberDTO> memList = dao.lists(key, value);
			
			for (MemberDTO dto : memList)
			{
				System.out.printf("%d %s %s %s %s %13s %s %s %7d %d %7d \n" , dto.getEmpid(), dto.getName(), dto.getSsn(), dto.getIbsadate(), dto.getCity()
			             , dto.getTel(), dto.getBuseo(), dto.getJikwi(), dto.getBasicpay()
			             , dto.getSudang(), dto.getPay());
			}
			dao.close();
			
		} catch (Exception e)
		{
			dao.close();
			System.out.println(e.toString());
		}
		
	}
	
	// 수정 메소드
	public void memUpdate()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			System.out.print("수정할 직원의 사원번호를 입력하세요 : ");
			String sid = sc.next();
			
			// db 연결 
			dao.connection();
			String key = "EMP_ID";
			ArrayList<MemberDTO> arrayList = dao.lists(key, sid);
			
			if (arrayList.size() > 0)
			{
				System.out.println("사번   이름       주민번호     입사일 지역      전화번호   부서 직위  기본급   수당    급여");
				System.out.println("--------------------------------------------------------------------------------------------");
				
				for (MemberDTO dto : arrayList)
				{
					System.out.printf("%d %s %s %s %s %13s %s %s %7d %d %7d \n" , dto.getEmpid(), dto.getName(), dto.getSsn(), dto.getIbsadate(), dto.getCity()
				             , dto.getTel(), dto.getBuseo(), dto.getJikwi(), dto.getBasicpay()
				             , dto.getSudang(), dto.getPay());
				}
				System.out.println("수정할 데이터 입력~!!");
				System.out.print("이름 : ");
				String name = sc.next();
				//2
				System.out.print("주민등록번호(yymmdd-nnnnnnn) : ");
				String ssn = sc.next();
				//3
				System.out.print("입사일(yyyy-mm-dd) : ");
				String ibsadate = sc.next();
				
				//4 지역 리스트 함수 불러와서 문자로 담아서 넣어줄꺼임
				ArrayList<String> city = dao.city();
				String  citystr = "";
				
				for (String str : city)
				{
					citystr += str + "/"; 
				}
				System.out.printf("지역(%s) : ", citystr);
				String cityname = sc.next();
				//5
				System.out.print("전화번호 : ");
				String tel = sc.next();
				
				//6 부서(개발부/기획부/영업부/인사부/자재부/총무부/홍보부/) :
				ArrayList<String> buseo = dao.buseo();
				String buseostr = "";
				for (String str : buseo)
				{
					buseostr += str + "/";
				}
				System.out.printf("부서(%s) : ", buseostr);
				String buseoname = sc.next();
				
				//7 직위(사장/전무/상무/이사/부장/차장/과장/대리/사원/) : 대리
				ArrayList<String> jik = dao.jikwi();
				
				
				String jikstr = "";
				for (String str : jik)
				{
					jikstr += str + "/";
				}
				System.out.printf("직위(%s) : ", jikstr);
				String jikname = sc.next();
				 
				//8 기본급(최소 1800000 이상) :  → jikname 으로 판별
				System.out.printf("기본급 (최소 %d 이상) : ", dao.basicpay(jikname));
				int basicpay = sc.nextInt();
				//9
				System.out.print("수당 : ");
				int sudang = sc.nextInt();
				
				// dto 만들어서 dao.add(dto) 해줘야함
				MemberDTO dto = new MemberDTO();
				dto.setName(name);
				dto.setSsn(ssn);
				dto.setIbsadate(ibsadate);
				dto.setCity(cityname);
				dto.setTel(tel);
				dto.setBuseo(buseoname);
				dto.setJikwi(jikname);
				dto.setBasicpay(basicpay);
				dto.setSudang(sudang);
				
				int result = dao.modify(dto);
				
				if (result > 0)
					System.out.println("직원 정보 수정 완료 ~!!!!! ");
								
			} else
			{
				System.out.println("수정할 대상이 존재하지 않습니다.");		
			}
			
			dao.close();
			
		} catch (Exception e)
		{
			dao.close();
			System.out.println(e.toString());
		}
		
	}
	
	// 삭제 메소드
	public void delete()
	{
		try
		{
			// 삭제할 번호 받기
			Scanner sc = new Scanner(System.in);
			System.out.print("삭제할 번호를 입력하세요 : " );
			String sid = sc.next();
			
			int rsid = Integer.parseInt(sid);

			dao.connection();
			String key = "EMP_ID";
			ArrayList<MemberDTO> arrayList = dao.lists(key, sid);
			
			
			if (arrayList.size()> 0)
			{
				System.out.println("사번   이름       주민번호     입사일 지역      전화번호   부서 직위  기본급   수당    급여");
				System.out.println("--------------------------------------------------------------------------------------------");
				
				
				for (MemberDTO dto : dao.lists(key, sid))
				{
					System.out.printf("%d %s %s %s %s %13s %s %s %7d %d %7d \n" , dto.getEmpid(), dto.getName(), dto.getSsn(), dto.getIbsadate(), dto.getCity()
				             , dto.getTel(), dto.getBuseo(), dto.getJikwi(), dto.getBasicpay()
				             , dto.getSudang(), dto.getPay());
				}
				
				System.out.print(">> 정말 삭제하시겠습니까?? (Y/N) : ");
				String response = sc.next();
				
				if (response.equals("Y") || response.equals("y"))
				{
					int result = dao.delete(rsid);
					if(result > 0)
					{
						System.out.println("삭제가 완료되었습니다.");
						dao.close();
					}
				}
				else 
				{
					System.out.println("취소되었습니다.");
					dao.close();
				}
				
				
			} else
			{
				// 수신된 내용이 존재하지 않는 상황 전달
				System.out.println("삭제할 대상이 존재하지 않습니다.");
				dao.close();
			}
			
			
		} catch (Exception e)
		{
			dao.close();
			System.out.println(e.toString());
		}
	}

	
}
