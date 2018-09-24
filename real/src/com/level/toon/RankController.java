package com.level.toon;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.level.toon.dao.CategoryDAO;
import com.level.toon.dao.ToonDAO;
import com.level.toon.dto.CategoryDTO;
import com.level.toon.dto.ToonDTO;

@Controller
public class RankController {

	@Autowired
	private SqlSessionTemplate sst;
	private CategoryDAO cdao;
	private ToonDAO tdao;
	
	@RequestMapping(value="/rank")
	public String rank(Model m, String type, int cate_num) {
		cdao = sst.getMapper(CategoryDAO.class);
		tdao = sst.getMapper(ToonDAO.class);
		ArrayList<CategoryDTO> clist = cdao.cate_list();
		ArrayList<ToonDTO> tlist;
		if(type.equals("all")) {
			tlist = tdao.all_rank_list();
		}else {
			tlist = tdao.cate_rank_list(cate_num);
		}
		for(int i=0; i<tlist.size(); i++) {
			for(int j=0; j<tlist.size(); j++) {
				if(i!=j && tlist.get(i).getToon_num()==tlist.get(j).getToon_num()) {
					tlist.remove(j);
					i=0;
				}
			}
		}
		if(tlist.size()>0) {
			m.addAttribute("star_score", tlist.get(0).getStar_score());
		}
		m.addAttribute("type", type);
		m.addAttribute("tlist", tlist);
		m.addAttribute("clist", clist);
		m.addAttribute("center", "rank.jsp");
		return "main";
	}
}
