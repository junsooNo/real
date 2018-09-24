package com.level.toon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.level.toon.dao.CommentDAO;
import com.level.toon.dao.EpisodeDAO;
import com.level.toon.dao.LibraryDAO;
import com.level.toon.dao.SawDAO;
import com.level.toon.dao.StarScoreDAO;
import com.level.toon.dao.ToonDAO;
import com.level.toon.dao.ZzimDAO;
import com.level.toon.dto.EpisodeDTO;
import com.level.toon.dto.LibraryDTO;
import com.level.toon.dto.ObjectDTO;
import com.level.toon.dto.SawDTO;
import com.level.toon.dto.StarScoreDTO;
import com.level.toon.dto.ToonDTO;
import com.level.toon.dto.ZzimDTO;

@Controller
public class EpisodeController {
	
	@Autowired
	private SqlSessionTemplate sst;
	private ToonDAO tdao;
	private EpisodeDAO edao;
	private CommentDAO cdao;
	private LibraryDAO ldao;
	private ZzimDAO zdao;
	private StarScoreDAO ssdao;
	private SawDAO sdao;
	
	@RequestMapping(value="/episode_upload")
	public String Episode_upload(Model m, int toon_num, int ep_num) {
		tdao = sst.getMapper(ToonDAO.class);
		ToonDTO tdto = tdao.toon_info(toon_num);		
		m.addAttribute("tdto", tdto);
		m.addAttribute("ep_num", ep_num);
		m.addAttribute("center", "episode_upload.jsp");
		return "main";
	}
	
	@RequestMapping(value="/episode_upload_ok")
	public String Episode_upload_ok(Model m, EpisodeDTO edto, 
			@RequestParam(value="ep_main_file") MultipartFile file1, @RequestParam(value="ep_toon_file") MultipartFile file2) {
		edao = sst.getMapper(EpisodeDAO.class);		
		String up_path = "C:\\Users\\wnstn\\Desktop\\level_toon\\Lv_Toon (9)\\Lv_Toon\\src\\main\\webapp\\resources\\episode_main_img\\";
		//E:/spring2/Lv_Toon/src/main/webapp/resources/episode_main_img/
		//C:/spring/study/Lv_Toon/src/main/webapp/resources/episode_main_img/
		File f = new File(up_path+file1.getOriginalFilename());
		edto.setEp_main_image(file1.getOriginalFilename());
		String up_path2 = "C:\\Users\\wnstn\\Desktop\\level_toon\\Lv_Toon (9)\\Lv_Toon\\src\\main\\webapp\\resources\\episode_toon_img";
		//E:/spring2/Lv_Toon/src/main/webapp/resources/episode_toon_img/
		//C:/spring/study/Lv_Toon/src/main/webapp/resources/episode_toon_img/
		File f2 = new File(up_path2+file2.getOriginalFilename());
		edto.setEp_toon_image(file2.getOriginalFilename());
		try {
			file1.transferTo(f);
			file2.transferTo(f2);
			edao.episode_upload_ok(edto);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return "redirect:episode_page?toon_num="+edto.getToon_num();
	}
	
	@RequestMapping(value="/toon_detail")
	public String Toon_detail(Model m, EpisodeDTO edto, HttpSession s, SawDTO sdto, LibraryDTO ldto) {
		edao = sst.getMapper(EpisodeDAO.class);
		ObjectDTO odto = edao.episode_info(edto);
		
		cdao = sst.getMapper(CommentDAO.class);
		ArrayList<ObjectDTO> clist = cdao.best_comment(edto);
		int comment_count = cdao.comment_count(edto);
		int member_num;
		if(s.getAttribute("member_num")!=null) {
			String member_num2 = s.getAttribute("member_num").toString();
			member_num = Integer.parseInt(member_num2);
			for(int i = 0; i < clist.size(); i++) {
				int comment_num = clist.get(i).getComment_num();
				int rec_status = cdao.check_rec_status(member_num, comment_num);
				if(rec_status>0) {
					System.out.println("comment_num : " + comment_num);
					clist.get(i).setRec_status("push");
				}
			}
		}		
		
		ldao = sst.getMapper(LibraryDAO.class);
		if(s.getAttribute("member_num")!=null) {
			String member_num2 = s.getAttribute("member_num").toString();
			member_num = Integer.parseInt(member_num2);
			ldto.setMember_num(member_num);
			int library_count = ldao.library_count(ldto);
			if(library_count==0) {
				ldao.add_library(ldto);
			}else {
				LibraryDTO ldto2 = ldao.library_info(ldto);
				int ep_num = ldto2.getEp_num();
				if(ldto.getEp_num()>ep_num) {
					ldao.update_library(ldto);
				}
			}
		}
		
		ssdao = sst.getMapper(StarScoreDAO.class);
		
		int ck_star = 0;
		int my_star_score = 0;
		if(s.getAttribute("member_num")!=null) {
			String member_num2 = s.getAttribute("member_num").toString();
			member_num = Integer.parseInt(member_num2);
			StarScoreDTO ssdto = new StarScoreDTO();
			ssdto.setToon_num(edto.getToon_num());
			ssdto.setEp_num(edto.getEp_num());
			ssdto.setMember_num(member_num);
			ck_star = ssdao.ck_star_score(ssdto);
			if(ck_star > 0) {
				my_star_score = ssdao.find_star_score(ssdto);
			}
		}
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("toon_num", edto.getToon_num());
		map.put("ep_num", edto.getEp_num());
		map.put("inequality", ">=");
		ArrayList<EpisodeDTO> nav_list = edao.episode_nav1(map);
		nav_list.get(0).setFirst_status("o");
		map.put("inequality", "<");
		ArrayList<EpisodeDTO> nav_list2 = edao.episode_nav2(map);
		for(int i=0; i<nav_list2.size(); i++) {
			nav_list.add(nav_list2.get(i));
		}
		
		sdao = sst.getMapper(SawDAO.class);
		if(s.getAttribute("member_num")!=null) {
			String member_num2 = s.getAttribute("member_num").toString();
			member_num = Integer.parseInt(member_num2);
			sdto.setMember_num(member_num);
			int count = sdao.saw_count(sdto);
			if(count<1) {
				sdao.saw(sdto);
			}
		}
		
		m.addAttribute("nav_list", nav_list);		
		m.addAttribute("odto", odto);
		m.addAttribute("clist", clist);
		m.addAttribute("comment_count", comment_count);
		m.addAttribute("ck_star", ck_star);
		m.addAttribute("my_star_score", my_star_score);
		return "toon_detail";
	}
	
	@RequestMapping(value="/next_ep")
	public void next_ep(HttpServletResponse resp, EpisodeDTO edto) {
		edao = sst.getMapper(EpisodeDAO.class);
		int count = edao.episode_count(edto);
		System.out.println(count);
		try {
			resp.getWriter().print(count);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
