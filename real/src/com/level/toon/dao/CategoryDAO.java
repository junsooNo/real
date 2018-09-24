package com.level.toon.dao;

import java.util.ArrayList;

import com.level.toon.dto.CategoryDTO;

public interface CategoryDAO {
	public ArrayList<CategoryDTO> cate_list();
	public String cate_name(int cate_num);
}
