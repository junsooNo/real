package com.level.toon;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.level.toon.dao.EpisodeDAO;
import com.level.toon.dao.ToonDAO;
import com.level.toon.dto.EpisodeDTO;
import com.level.toon.dto.ObjectDTO;
import com.level.toon.dto.ToonDTO;

@Controller
public class MainController {

	@Autowired
	private SqlSessionTemplate sst;
	private ToonDAO tdao;
	private EpisodeDAO edao;
	
	@RequestMapping(value="/main")
	public String main(Model m) {
		tdao = sst.getMapper(ToonDAO.class);
		ArrayList<ToonDTO> tlist = tdao.toon_list();
		
		edao = sst.getMapper(EpisodeDAO.class);
		ArrayList<ObjectDTO> rlist = tdao.recent_list(); 
		for(int i = 0;  i < rlist.size(); i++) {
			for (int j = 0; j < rlist.size(); j++) {
				if(i!=j) {
					if(rlist.get(i).getToon_num() == rlist.get(j).getToon_num()) {
						rlist.remove(j);
						i=0;
					}
				}				
			}
		}		
		for(int i = 0; i < rlist.size(); i++) {
			int ep_count = edao.total_ep_count(rlist.get(i).getToon_num());
			rlist.get(i).setEp_count(ep_count);
		}
		
		ArrayList<ObjectDTO> mlist = tdao.monthly_list();
		ArrayList<ObjectDTO> nlist = tdao.new_list();
		for(int i = 0; i < nlist.size(); i++) {
			String reg_date1 = nlist.get(i).getReg_date();
			String reg_date2 = reg_date1.substring(6, 10);
			String reg_date = reg_date2.replace('-', '/');
			nlist.get(i).setReg_date(reg_date);
		}
		m.addAttribute("tlist", tlist);
		m.addAttribute("rlist", rlist);
		m.addAttribute("mlist", mlist);
		m.addAttribute("nlist", nlist);
		m.addAttribute("center", "center.jsp");
		return "main";
	}
	
	@RequestMapping(value="/today_info")							
	public void today_info(HttpServletResponse resp) {
		SimpleDateFormat sdf = new SimpleDateFormat("E",Locale.KOREAN);
		Date date = new Date();
		String today = sdf.format(date);
		try {
			resp.getWriter().print(today);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
