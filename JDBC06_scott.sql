Select user
from dual;
--==>> SCOTT


SELECT *
FROM TBL_MEMBER;
/*
2	���̻�	010-2222-2222
3	�Ӽҹ�	010-3333-3333
1	��ȣ��	010-1111-1111
*/


--�� ������ �Է� ������ ����
INSERT INTO TBL_MEMBER(SID, NAME, TEL)
VALUES(MEMBERSEQ.NEXTVAL, '����', '010-4444-4444');
-- ���� ����
INSERT INTO TBL_MEMBER(SID, NAME, TEL) VALUES(MEMBERSEQ.NEXTVAL, '����', '010-4444-4444')
;
--==>> 1 �� ��(��) ���ԵǾ����ϴ�.

--�� Ȯ��
SELECT *
FROM TBL_MEMBER;
/*
2	���̻�	010-2222-2222
3	�Ӽҹ�	010-3333-3333
1	��ȣ��	010-1111-1111
4	����	010-4444-4444
*/

--��
COMMIT;
--==>> Ŀ�� �Ϸ�.

--�� ������ ��ü ��ȸ ������ ����
SELECT SID, NAME, TEL
FROM TBL_MEMBER
ORDER BY SID;
-- ���� ����
SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID
;
/*
1	��ȣ��	010-1111-1111
2	���̻�	010-2222-2222
3	�Ӽҹ�	010-3333-3333
4	����	010-4444-4444
5	������	010-5555-5555
6	������	010-6666-6666
30	������	010-3030-3030
31	������	010-3131-3131
*/


SELECT *
FROM TBL_SCORE;





