package com.test;

import java.util.ArrayList;
import java.util.Scanner;

public class ScoreProcess
{
	// 주요 객체 생성
	private ScoreDAO dao;
	
	// 사용자 정의 생성자 생성
	public ScoreProcess()
	{
		dao = new ScoreDAO();
	}
	
	// 성적 입력
	
	public void sungjukInsert()
	{
		try
		{
			// db 연결
			dao.connection();
			
			// 등록될  학생의 번호 확인
			int count = dao.seq();
			
			// 레코드 수 확인
			//int count = dao.count();
			
			Scanner sc = new Scanner(System.in);
			
			System.out.println(". 입력시 취소됩니다.");
			
			do
			{
				System.out.println();
				System.out.printf("%d번 학생 성적 입력(이름 국어 영어 수학)  : ", count++);
				String name = sc.next();
				
				if (name.equals("."))
					break;
				
				int kor = sc.nextInt();
				int eng = sc.nextInt();
				int mat = sc.nextInt();
				
				// 입력받은 항목들 토대로 ScoreDTO 객체 구성
				ScoreDTO dto = new ScoreDTO();
				dto.setName(name);
				dto.setKor(kor);
				dto.setEng(eng);
				dto.setMat(mat);
					
				// dao 의 add 호출
				int result = dao.add(dto);
				
				if (result > 0)
					System.out.println("성적 입력이 완료되었습니다.");

			} while (true);


		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				dao.close();
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}// end sungjukInsert()
	
	//	 성적 전체 출력
	public void sungjukSelectAll()
	{
		try
		{
			// dao 의 connection 메소드 호출 → db 연결
			dao.connection();
			
			// 전체 인원수 파악
			int count = dao.count();
			
			System.out.println();
			System.out.printf("전체 인원 : %d명\n", count);
			System.out.println("번호	이름	국어  영어  수학   총점   평균   석차");
			
			
			for (ScoreDTO dto : dao.lists())
			{
				System.out.printf("%3s  %4s  %4d  %5d  %5d  %5d  %5.1f  %5d\n"
						, dto.getSid(), dto.getName(), dto.getKor(), dto.getEng(), dto.getMat()
						, dto.getTot(), dto.getAvg(), dto.getRank());
			}

		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				dao.close();
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		} 
	} // end sungjukSelectAll()
	
	
	//	 이름 검색 출력
	public void sungjukSearchName()
	{
		try
		{
			// 검색할 이름 받기
			Scanner sc = new Scanner(System.in);
			System.out.print("검색할 이름 입력 : ");
			String name = sc.next();
			
			// db 연결
			dao.connection();
			
			// dao 의 lists() 메소드 호출 → 매개변수로 검색할 이름을 문자열 형태로 넣어주기
			ArrayList<ScoreDTO> arrayList = dao.lists(name);
			
			if (arrayList.size() > 0)
			{
				// 수신된 내용 출력
				System.out.println("번호	이름	국어  영어  수학   총점   평균   석차");
				for (ScoreDTO dto : arrayList)
				{
					
					System.out.printf("%3s  %4s  %4d  %5d  %5d  %5d  %5.1f  %5d\n"
							, dto.getSid(), dto.getName(), dto.getKor(), dto.getEng(), dto.getMat()
							, dto.getTot(), dto.getAvg(), dto.getRank());
				}
			}
			else
			{
				System.out.println("검색 결과가 존재하지 않습니다.");
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				dao.close();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
	}// end sungjukSearchName()
	
	
	//	 성적 수정
	public void sungjukUpdate()
	{
		try
		{
			// 수정할 대상의 번호 입력 받기
			Scanner sc = new Scanner(System.in);
			System.out.print("수정할 번호를 입력하세요 : ");
			String sidstr = sc.next();
			
			int sid = Integer.parseInt(sidstr);
			
			// db 연결 
			dao.connection();
			
			// 수정할 대상 수신 → 데이터 수정을 위한 대상 검색
			ArrayList<ScoreDTO> arrayList = dao.lists(sid);
			
			if (arrayList.size() > 0)
			{
				// 수신된 내용 출력
				System.out.println("번호	이름	국어  영어  수학   총점   평균   석차");
				for (ScoreDTO dto : dao.lists())
				{
					System.out.printf("%3s  %4s  %4d  %5d  %5d  %5d  %5.1f  %5d\n"
							, dto.getSid(), dto.getName(), dto.getKor(), dto.getEng(), dto.getMat()
							, dto.getTot(), dto.getAvg(), dto.getRank());
				}
				
				System.out.println();
				System.out.println("수정 데이터 입력 (이름 국어 영어 수학) : ");
				
				String name = sc.next();
				int kor = sc.nextInt();
				int eng = sc.nextInt();
				int mat = sc.nextInt();
				
				
				// dto  구성
				ScoreDTO dto = new ScoreDTO();
				dto.setName(name);
				dto.setKor(kor);
				dto.setEng(eng);
				dto.setMat(mat);
				dto.setSid(String.valueOf(sid));
				
				// 구성된 dto 를 넘겨주며 dao 의 modify () 메소드 호출
				int result = dao.modify(dto);
				if (result > 0)
						System.out.println("수정이 완료되었습니다.");		
			} 
			else
			{
				System.out.println("수정할 대상이 존재하지 않습니다.");	
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				dao.close();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	} // end sungjukUpdate()
	
	// 성적 삭제
	public void sungjukDelete()
	{
		try
		{
			// 삭제할 번호 받기
			Scanner sc = new Scanner(System.in);
			System.out.print("삭제할 번호를 입력하세요 : " );
			String sidstr = sc.next();
			
			int sid = Integer.parseInt(sidstr);
			
			// db 연결
			dao.connection();
			// dao의 lists() 메소드 호출 → 삭제 대상 검색 (숫자 매개변수)
			ArrayList<ScoreDTO> arrayList = dao.lists(sid);
			
			if (arrayList.size() > 0)
			{
				// 수신된 내용 출력
				System.out.println("번호	이름	국어  영어  수학   총점   평균   석차");
				for (ScoreDTO dto : dao.lists())
				{
					System.out.printf("%3s  %4s  %4d  %5d  %5d  %5d  %5.1f  %5d\n"
							, dto.getSid(), dto.getName(), dto.getKor(), dto.getEng(), dto.getMat()
							, dto.getTot(), dto.getAvg(), dto.getRank());
				}
				
				System.out.print(">> 정말 삭제하시겠습니까?? (Y/N) : ");
				String response = sc.next();
				
				if (response.equals("Y") || response.equals("y"))
				{
					int result = dao.remove(sid);
					if(result > 0)
					{
						System.out.println("삭제가 완료되었습니다.");
					}
				}
				else 
				{
					System.out.println("취소되었습니다.");
				}
			}
			else
			{
				// 수신된 내용이 존재하지 않는 상황 전달
				System.out.println("삭제할 대상이 존재하지 않습니다.");
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				dao.close();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	} // end sungjukDelete
	
} // end class 

