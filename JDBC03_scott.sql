select USER
FROM DUAL;
--==>> SCOTT

--○ 실습 테이블 생성
CREATE TABLE TBL_SCORE
( SID   NUMBER
, NAME  VARCHAR2(30) 
, KOR   NUMBER(3)
, ENG   NUMBER(3)
, MAT   NUMBER(3)
);
--==>> Table TBL_SCORE이(가) 생성되었습니다.

--○ 제약 조건 추가
ALTER TABLE TBL_SCORE
ADD CONSTRAINT SCORE_SID_PK PRIMARY KEY(SID);
--==>> Table TBL_SCORE이(가) 변경되었습니다.


--○ 제약 조건 추가
ALTER TABLE TBL_SCORE
ADD ( CONSTRAINT SCORE_KOR_CK CHECK (KOR BETWEEN 0 AND 100) 
    , CONSTRAINT SCORE_ENG_CK CHECK (ENG BETWEEN 0 AND 100) 
    , CONSTRAINT SCORE_MAT_CK CHECK (MAT BETWEEN 0 AND 100) 
    );
--==>> Table TBL_SCORE이(가) 변경되었습니다.


--○ 시퀸스 생성
CREATE SEQUENCE SCORESEQ
NOCACHE;
--==>> Sequence SCORESEQ이(가) 생성되었습니다.

-- 정보 입력 
INSERT INTO TBL_SCORE(SID, NAME, KOR, ENG, MAT) VALUES (SCORESEQ.NEXTVAL, '김민성', 100, 100, 100)
;

-- 전체 인원수 출력
SELECT COUNT(*) AS COUNT FROM TBL_SCORE
;

-- 전체 정보 출력 
SELECT SID, NAME, KOR, ENG, MAT FROM TBL_SCORE
;

SELECT  *
FROM TBL_SCORE;

rollback;

TRUNCATE TABLE TBL_SCORE;

commit;



