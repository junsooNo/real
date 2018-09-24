package com.level.toon;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.level.toon.dao.CategoryDAO;
import com.level.toon.dao.EpisodeDAO;
import com.level.toon.dao.LibraryDAO;
import com.level.toon.dao.ToonDAO;
import com.level.toon.dto.LibraryDTO;

@Controller
public class LibraryController {
	
	@Autowired
	private SqlSessionTemplate sst;
	private LibraryDAO ldao;
	private ToonDAO tdao;
	private EpisodeDAO edao;
	private CategoryDAO cdao;
	
	@RequestMapping(value="/add_library")
	public String Add_library() {
		return "";
	}
	@RequestMapping(value="/my_library")
	public String my_library(Model m, HttpSession s) {
		ldao = sst.getMapper(LibraryDAO.class);
		cdao = sst.getMapper(CategoryDAO.class);
		String member_num_str = s.getAttribute("member_num").toString();
		int member_num = Integer.parseInt(member_num_str);
		ArrayList<LibraryDTO> llist = ldao.library_list(member_num);
		for(LibraryDTO ldto : llist) {
			int cate_num = ldto.getCate_num();
			String cate_name = cdao.cate_name(cate_num);
			ldto.setCate_name(cate_name);
		}
		m.addAttribute("llist", llist);
		m.addAttribute("center", "my_library.jsp");
		return "main";
	}
}