package com.level.toon.dao;

import com.level.toon.dto.ZzimDTO;

public interface ZzimDAO {
	public void add_zzim(ZzimDTO dto);
	public int ck_zzim(ZzimDTO dto);
	public void remove_zzim(ZzimDTO dto);
}
