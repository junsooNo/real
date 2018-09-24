package com.level.toon.dao;

import com.level.toon.dto.UseDTO;

public interface UseDAO {
	
	public int use_count(UseDTO udto);
	public int add_use(UseDTO udto);

}
