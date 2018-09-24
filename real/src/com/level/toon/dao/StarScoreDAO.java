package com.level.toon.dao;

import com.level.toon.dto.StarScoreDTO;

public interface StarScoreDAO {
	public void reg_star_score(StarScoreDTO ssdto);
	public int ck_star_score(StarScoreDTO ssdto);
	public int find_star_score(StarScoreDTO ssdto);
}
