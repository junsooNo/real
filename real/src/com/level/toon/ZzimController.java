package com.level.toon;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.level.toon.dao.ZzimDAO;
import com.level.toon.dto.ZzimDTO;

@Controller
public class ZzimController {
	@Autowired
	private SqlSessionTemplate sst;
	private ZzimDAO zdao;
	
	@RequestMapping(value="add_zzim")
	public @ResponseBody String Add_zzim(ZzimDTO zdto) {
		zdao = sst.getMapper(ZzimDAO.class);
		zdao.add_zzim(zdto);
		return "y";
	}
	
	@RequestMapping(value="remove_zzim")
	public @ResponseBody String Remove_zzim(ZzimDTO zdto) {
		zdao = sst.getMapper(ZzimDAO.class);
		zdao.remove_zzim(zdto);
		return "y";
	}
}
