package com.level.toon.dao;

import java.util.ArrayList;

import com.level.toon.dto.ObjectDTO;
import com.level.toon.dto.ToonDTO;

public interface ToonDAO {
	public void toon_upload_ok(ToonDTO tdto);
	public ArrayList<ToonDTO> toon_list();
	public ToonDTO toon_info(int toon_num);
	public ArrayList<ToonDTO> toon_find(String search);
	public ArrayList<ToonDTO> today_toon_list(String today_param);
	public ArrayList<ToonDTO> day_toon_list(String today_param);
	public ArrayList<ObjectDTO> recent_list();
	public ArrayList<ObjectDTO> monthly_list();
	public ArrayList<ObjectDTO> new_list();
	public ArrayList<ToonDTO> monthly_toon_list(String upload_type);
	public ArrayList<ToonDTO> all_rank_list();
	public ArrayList<ToonDTO> cate_rank_list(int cate_num);
}
