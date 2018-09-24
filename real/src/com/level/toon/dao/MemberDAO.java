package com.level.toon.dao;

import java.util.Map;

import com.level.toon.dto.MemberDTO;

public interface MemberDAO {

	public void join(MemberDTO mdto);
	public int overlap_id(String email);
	public void join_ok(MemberDTO mdto);
	public int login_ok(MemberDTO mdto);
	public int find_member_num(MemberDTO mdto);
	public void coin_update(Map<String, Object> map);
	
	public MemberDTO member_info(String email);
	public void coin_subtract(int view_coin, int member_num);
	public void lotto_update(Map<String, Object> map);
	public String lotto_date(int member_num);
}
