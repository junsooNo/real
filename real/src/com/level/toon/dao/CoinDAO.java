package com.level.toon.dao;

import java.util.ArrayList;

import com.level.toon.dto.CoinDTO;

public interface CoinDAO {

	public ArrayList<CoinDTO> coin_list();
	public CoinDTO coin_find(int coin_num);
}
