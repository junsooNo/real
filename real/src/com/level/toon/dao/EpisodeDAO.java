package com.level.toon.dao;

import java.util.ArrayList;
import java.util.Map;

import com.level.toon.dto.EpisodeDTO;
import com.level.toon.dto.ObjectDTO;
import com.level.toon.dto.StarScoreDTO;

public interface EpisodeDAO {
	public void episode_upload_ok(EpisodeDTO dto);
	public ArrayList<EpisodeDTO> episode_list(int toon_num);
	public ObjectDTO episode_info(EpisodeDTO dto);
	public ArrayList<EpisodeDTO> episode_nav1(Map<String, Object> map);
	public ArrayList<EpisodeDTO> episode_nav2(Map<String, Object> map);
	public void update_star_rating(StarScoreDTO ssdto);
	public int episode_count(EpisodeDTO edto);
	public int total_ep_count(int ep_count);
}
