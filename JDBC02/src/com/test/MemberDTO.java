/*=================
 	MemberDTO.java
 	-데이터 위한 객체
 ==================*/

package com.test;

public class MemberDTO
{
	//변수 선언
	private String sid;
	private String name, tel;
	
	// getter/ setter
	public String getSid()
	{
		return sid;
	}
	public void setSid(String sid)
	{
		this.sid = sid;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getTel()
	{
		return tel;
	}
	public void setTel(String tel)
	{
		this.tel = tel;
	}

}
