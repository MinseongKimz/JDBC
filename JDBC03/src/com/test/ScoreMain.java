/*========================
 	ScoreMain.java
=========================*/

/*  
○ 성적 처리 프로그램 구현 → DB연동 → ScoreDAO, ScoreDTO 클래스 활용
   
   여러 명의 이름, 국어점수, 영어점수, 수학점수를 입력받아
   총점, 평균을 연산하여 내용을 출력하는 프로그램을 구현한다.
   출력 시 번호(이름, 총점 등)을 오름차순 정렬하여 출력한다.
   
실행 예)

1번 학생 성적 입력(이름 국어 영어 수학) : 신시은 80 75 60
2번 학생 성적 입력(이름 국어 영어 수학) : 이호석 100 90 80
3번 학생 성적 입력(이름 국어 영어 수학) : 이연주 80 85 80
4번 학생 성적 입력(이름 국어 영어 수학) : .

--------------------------------------------------------
번호   이름     국어  영어   수학    총점    평균
--------------------------------------------------------
1     신시은    80     75     60    xxx      xx.xx
2     이호석   100     90     80    xxx      xx.xx
3     이연주    80     85     80    xxx      xx.xx   
--------------------------------------------------------
 */

package com.test;

import java.util.Scanner;

import com.util.DBConn;

public class ScoreMain
{
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			// ScoreDAO 객체 생성
			ScoreDAO dao = new ScoreDAO();
			
			// 총인원 용  count 생성
			int count = dao.count();
			
			// 안내문 시작
			do
			{
				System.out.printf("%d번 학생 성적 입력(이름 국어 영어 수학) : ", ++count);
				String name =sc.next();
				
				// 무한 반복 빠져 나오기
				if (name.equals("."))
					break;
				
				int kor = sc.nextInt();
				int eng = sc.nextInt();
				int mat = sc.nextInt();
				
				
				// DTO 속성값 가져오기
				ScoreDTO dto = new ScoreDTO(); // 이거 do while 문 밖에 쓰면 에러..
											   // Duplicate local variable dto
				
				dto.setName(name);
				dto.setKor(kor);
				dto.setEng(eng);
				dto.setMat(mat);
				
				// add 메소드 호출해서 DB에 입력정보 채우기 (총점 평균은 add 메소드에서 알아서)
				//int result =  dao.add(dto); // 반환값 (적용된 행 갯수 → int)
				dao.add(dto);
				
			} while (true);

			System.out.println();
			System.out.println("--------------------------------------------------------");
			System.out.println("번호   이름      국어  영어   수학   총점   평균");
			System.out.println("--------------------------------------------------------");
			// 리스트 출력하기
			
			for (ScoreDTO dto : dao.lists())
			{
				System.out.printf("%3s %6s %6d %6d %6d %6d %6.2f\n", dto.getSid(), dto.getName(), dto.getKor(), dto.getEng(), dto.getMat(), dto.getTot(), dto.getAvg() );
			}
			System.out.println("--------------------------------------------------------");
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				DBConn.close();
				System.out.println("데이터베이스 연결 닫힘~!!!");
				System.out.println("프로그램 종료됨~!!!");
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	
	}
}































































