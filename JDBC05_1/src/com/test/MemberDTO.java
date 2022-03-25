// MemberDTO.java
// 속성 클래스

package com.test;

public class MemberDTO
{
	private int empid, basicpay, sudang, pay; // pay 는 급여
	private String name, ssn, city, tel, buseo, jikwi, ibsadate;
	

	public String getIbsadate()
	{
		return ibsadate;
	}
	public void setIbsadate(String ibsadate)
	{
		this.ibsadate = ibsadate;
	}
	public int getEmpid()
	{
		return empid;
	}
	public void setEmpid(int empid)
	{
		this.empid = empid;
	}
	public int getBasicpay()
	{
		return basicpay;
	}
	public void setBasicpay(int basicpay)
	{
		this.basicpay = basicpay;
	}
	public int getSudang()
	{
		return sudang;
	}
	public void setSudang(int sudang)
	{
		this.sudang = sudang;
	}
	public int getPay()
	{
		return pay;
	}
	public void setPay(int pay)
	{
		this.pay = pay;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getSsn()
	{
		return ssn;
	}
	public void setSsn(String ssn)
	{
		this.ssn = ssn;
	}
	public String getCity()
	{
		return city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}
	public String getTel()
	{
		return tel;
	}
	public void setTel(String tel)
	{
		this.tel = tel;
	}
	public String getBuseo()
	{
		return buseo;
	}
	public void setBuseo(String buseo)
	{
		this.buseo = buseo;
	}
	public String getJikwi()
	{
		return jikwi;
	}
	public void setJikwi(String jikwi)
	{
		this.jikwi = jikwi;
	}
	
}
