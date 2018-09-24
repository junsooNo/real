package com.level.toon;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.level.toon.dao.CategoryDAO;
import com.level.toon.dao.DayDAO;
import com.level.toon.dao.EpisodeDAO;
import com.level.toon.dao.SawDAO;
import com.level.toon.dao.ToonDAO;
import com.level.toon.dao.ZzimDAO;
import com.level.toon.dto.CategoryDTO;
import com.level.toon.dto.DayDTO;
import com.level.toon.dto.ToonDTO;
import com.level.toon.dto.ZzimDTO;
import com.level.toon.dto.EpisodeDTO;
import com.level.toon.dto.ObjectDTO;
import com.level.toon.dto.SawDTO;

@Controller
public class ToonController {

	@Autowired
	private SqlSessionTemplate sst;
	private CategoryDAO cdao;
	private ToonDAO tdao;
	private DayDAO ddao;
	private EpisodeDAO edao;
	private ZzimDAO zdao;
	private SawDAO sdao;
	
	@RequestMapping(value="/toon_upload")
	public String toon_upload(Model m) {
		cdao = sst.getMapper(CategoryDAO.class);
		ArrayList<CategoryDTO> clist = cdao.cate_list();
		m.addAttribute("clist",clist);
		m.addAttribute("center","toon_upload.jsp");
		return "main";
	}
	
	@RequestMapping(value="/toon_upload_ok")
	public String toon_upload_ok(ToonDTO tdto, Model m,
			@RequestParam(value="toon_file") MultipartFile file, @RequestParam(value="toon_file2") MultipartFile file2) {
		tdao = sst.getMapper(ToonDAO.class);
		String up_path = "C:\\Users\\wnstn\\Desktop\\level_toon\\Lv_Toon (9)\\Lv_Toon\\src\\main\\webapp\\resources\\toon_main_img\\";
		// E:/spring2/Lv_Toon/src/main/webapp/resources/toon_main_img/
		// C:/spring/study/Lv_Toon/src/main/webapp/resources/toon_main_img/
		File f = new File(up_path+file.getOriginalFilename());
		tdto.setMain_image(file.getOriginalFilename());
		String up_path2 = "C:\\Users\\wnstn\\Desktop\\level_toon\\Lv_Toon (9)\\Lv_Toon\\src\\main\\webapp\\resources\\toon_main_img\\";
		// E:/spring2/Lv_Toon/src/main/webapp/resources/toon_main_img2/
		// C:/spring/study/Lv_Toon/src/main/webapp/resources/toon_main_img2/
		File f2 = new File(up_path2+file2.getOriginalFilename());
		tdto.setMain_image2(file2.getOriginalFilename());
		try {
			file.transferTo(f);
			file2.transferTo(f2);
			tdao.toon_upload_ok(tdto);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		m.addAttribute("center","center.jsp");
		return "redirect:main";
	}
	
	@RequestMapping(value="search_keyup", produces="application/text; charset=utf-8")			
	public @ResponseBody String search_keyup(String search) {
		search = "%"+search+"%";
		tdao = sst.getMapper(ToonDAO.class);
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		String json="";
		ArrayList<ToonDTO> tlist = tdao.toon_find(search);
		for(ToonDTO t : tlist) {
			System.out.println(t.getToon_title());
		}
		System.out.println(tlist.size());
		map.put("tlist", tlist);
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping(value="/weekly")										
	public String weekly(Model m, String today_param) {
		ddao = sst.getMapper(DayDAO.class);
		tdao = sst.getMapper(ToonDAO.class);
		ArrayList<DayDTO> dlist = new ArrayList<DayDTO>();
		String day="";
		for(int i=0; i<7; i++) {			//요일 집어넣기
			DayDTO ddto = new DayDTO();
			if(i==0) {
				day="월";
				ddto.setToday(day);
			}else if(i==1) {
				day="화";
				ddto.setToday(day);
			}else if(i==2) {
				day="수";
				ddto.setToday(day);
			}else if(i==3) {
				day="목";
				ddto.setToday(day);
			}else if(i==4) {
				day="금";
				ddto.setToday(day);
			}else if(i==5) {
				day="토";
				ddto.setToday(day);
			}else if(i==6) {
				day="일";
				ddto.setToday(day);
			}
			dlist.add(ddto);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("E",Locale.KOREAN);
		Date date = new Date();
		String today=sdf.format(date);
		for(DayDTO ddto : dlist) {				//占쏙옙占쏙옙占싱몌옙 today
			if(ddto.getToday().equals(today)) {
				ddto.setToday_status("today");
			}
		}
		
		ArrayList<ToonDTO> tlist = tdao.today_toon_list(today_param);
		m.addAttribute("tlist",tlist);
		m.addAttribute("dlist",dlist);
		m.addAttribute("center","weekly.jsp");
		return "main";
	}
	
	@RequestMapping(value="/weekly_all")								
	public String weekly_all(Model m) {
		ddao = sst.getMapper(DayDAO.class);
		tdao = sst.getMapper(ToonDAO.class);
		ArrayList<DayDTO> dlist = new ArrayList<DayDTO>();
		String day="";
		for(int i=0; i<7; i++) {			//요일 집어넣기
			DayDTO ddto = new DayDTO();
			if(i==0) {
				day="월";
				ddto.setToday(day);
			}else if(i==1) {
				day="화";
				ddto.setToday(day);
			}else if(i==2) {
				day="수";
				ddto.setToday(day);
			}else if(i==3) {
				day="목";
				ddto.setToday(day);
			}else if(i==4) {
				day="금";
				ddto.setToday(day);
			}else if(i==5) {
				day="토";
				ddto.setToday(day);
			}else if(i==6) {
				day="일";
				ddto.setToday(day);
			}
			dlist.add(ddto);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("E",Locale.KOREAN);
		Date date = new Date();
		String today=sdf.format(date);
		for(DayDTO ddto : dlist) {				
			if(ddto.getToday().equals(today)) {
				ddto.setToday_status("today");
			}
		}
		ArrayList<ToonDTO> today_list = tdao.day_toon_list(today);
		ArrayList<ToonDTO> sunday_list = tdao.day_toon_list("일");
		ArrayList<ToonDTO> monday_list = tdao.day_toon_list("월");
		ArrayList<ToonDTO> tuesday_list = tdao.day_toon_list("화");
		ArrayList<ToonDTO> wednesday_list = tdao.day_toon_list("수");
		ArrayList<ToonDTO> thursday_list = tdao.day_toon_list("목");
		ArrayList<ToonDTO> friday_list = tdao.day_toon_list("금");
		ArrayList<ToonDTO> Saturday_list = tdao.day_toon_list("토");
		
		m.addAttribute("today_list",today_list);
		m.addAttribute("sunday_list",sunday_list);
		m.addAttribute("monday_list",monday_list);
		m.addAttribute("tuesday_list",tuesday_list);
		m.addAttribute("wednesday_list",wednesday_list);
		m.addAttribute("thursday_list",thursday_list);
		m.addAttribute("friday_list",friday_list);
		m.addAttribute("Saturday_list",Saturday_list);
		m.addAttribute("dlist",dlist);
		m.addAttribute("center","weekly_all.jsp");
		return "main";
	}
	
	@RequestMapping(value="/episode_page")
	public String Episode_page(Model m, int toon_num, HttpSession s) {
		tdao = sst.getMapper(ToonDAO.class);
		sdao = sst.getMapper(SawDAO.class);
		ToonDTO tdto = tdao.toon_info(toon_num);
		edao = sst.getMapper(EpisodeDAO.class);
		ArrayList<EpisodeDTO> elist = edao.episode_list(toon_num);
		for(int i = 0; i < elist.size(); i++) {
			String upload_date = elist.get(i).getEp_upload_date();
			String upload_date2 = upload_date.substring(0, 10);
			String upload_date3 = upload_date2.replace('-', '.');
			elist.get(i).setEp_upload_date(upload_date3);
		}
		int ep_count = 0;
		if(elist.size()>0) {
			ep_count = elist.size() - 1;
		}
		
		zdao = sst.getMapper(ZzimDAO.class);
		int member_num;
		int ck_zzim = 0;
		if(s.getAttribute("member_num")!=null) {
			String member_num2 = s.getAttribute("member_num").toString();
			member_num = Integer.parseInt(member_num2);
			ZzimDTO zdto = new ZzimDTO();
			zdto.setMember_num(member_num);
			zdto.setToon_num(toon_num);
			ck_zzim = zdao.ck_zzim(zdto);
			
		}		
		
		SawDTO sdto = new SawDTO();
		if(s.getAttribute("member_num")!=null) {
			for(EpisodeDTO edto : elist) {
				String member_num3 = (String)s.getAttribute("member_num").toString();
				int member_num4 = Integer.parseInt(member_num3);
				sdto.setMember_num(member_num4);
				sdto.setToon_num(toon_num);
				sdto.setEp_num(edto.getEp_num());
				int count = sdao.saw_count(sdto);
				if(count>0) {
					edto.setSaw_status("o");
				}
			}	
		}			
				
		for(EpisodeDTO edto : elist) {
			Calendar today = Calendar.getInstance();
			Calendar d_day = Calendar.getInstance();
			if(edto.getFree_date()!=null) {
				String[] free_date = edto.getFree_date().split("-");
				int year = Integer.parseInt(free_date[0]);
				int month = Integer.parseInt(free_date[1]);
				int day = Integer.parseInt(free_date[2]);
				d_day.set(year, month-1, day);
				
				long l_today = today.getTimeInMillis() / (24*60*60*1000);
				long l_d_day = d_day.getTimeInMillis() / (24*60*60*1000);
				
				int last_d_day = (int)(l_d_day-l_today);
				edto.setD_day(last_d_day);
				
				if(last_d_day<=0) {
					edto.setFree("o");
				}
			}
		}
		if(tdto.getToon_info().length()>200) {
			String toon_info_sub = tdto.getToon_info().substring(0,200)+"...";
			tdto.setToon_info_sub(toon_info_sub);
		}		
		
		m.addAttribute("ep_count", ep_count);
		m.addAttribute("tdto", tdto);
		m.addAttribute("elist", elist);
		m.addAttribute("ck_zzim", ck_zzim);
		m.addAttribute("center", "episode_page.jsp");
		return "main";
	}
	@RequestMapping(value="/episode_page_monthly")
	public String episode_page_monthly(Model m, int toon_num, HttpSession s) {
		tdao = sst.getMapper(ToonDAO.class);
		sdao = sst.getMapper(SawDAO.class);
		ToonDTO tdto = tdao.toon_info(toon_num);
		edao = sst.getMapper(EpisodeDAO.class);
		ArrayList<EpisodeDTO> elist = edao.episode_list(toon_num);
		for(int i = 0; i < elist.size(); i++) {
			String upload_date = elist.get(i).getEp_upload_date();
			String upload_date2 = upload_date.substring(0, 10);
			String upload_date3 = upload_date2.replace('-', '.');
			elist.get(i).setEp_upload_date(upload_date3);
		}
		int ep_count = 0;
		if(elist.size()>0) {
			ep_count = elist.size() - 1;
		}
		
		zdao = sst.getMapper(ZzimDAO.class);
		int member_num;
		int ck_zzim = 0;
		if(s.getAttribute("member_num")!=null) {
			String member_num2 = s.getAttribute("member_num").toString();
			member_num = Integer.parseInt(member_num2);
			ZzimDTO zdto = new ZzimDTO();
			zdto.setMember_num(member_num);
			zdto.setToon_num(toon_num);
			ck_zzim = zdao.ck_zzim(zdto);
			
		}		
		
		SawDTO sdto = new SawDTO();
		if(s.getAttribute("member_num")!=null) {
			for(EpisodeDTO edto : elist) {
				String member_num3 = (String)s.getAttribute("member_num").toString();
				int member_num4 = Integer.parseInt(member_num3);
				sdto.setMember_num(member_num4);
				sdto.setToon_num(toon_num);
				sdto.setEp_num(edto.getEp_num());
				int count = sdao.saw_count(sdto);
				if(count>0) {
					edto.setSaw_status("o");
				}
			}	
		}			
				
		for(EpisodeDTO edto : elist) {
			Calendar today = Calendar.getInstance();
			Calendar d_day = Calendar.getInstance();
			if(edto.getFree_date()!=null) {
				String[] free_date = edto.getFree_date().split("-");
				int year = Integer.parseInt(free_date[0]);
				int month = Integer.parseInt(free_date[1]);
				int day = Integer.parseInt(free_date[2]);
				d_day.set(year, month-1, day);
				
				long l_today = today.getTimeInMillis() / (24*60*60*1000);
				long l_d_day = d_day.getTimeInMillis() / (24*60*60*1000);
				
				int last_d_day = (int)(l_d_day-l_today);
				edto.setD_day(last_d_day);
				
				if(last_d_day<=0) {
					edto.setFree("o");
				}
			}
		}
		
		String toon_info_sub = tdto.getToon_info().substring(0,200)+"...";
		tdto.setToon_info_sub(toon_info_sub);
		
		m.addAttribute("ep_count", ep_count);
		m.addAttribute("tdto", tdto);
		m.addAttribute("elist", elist);
		m.addAttribute("ck_zzim", ck_zzim);
		m.addAttribute("center", "episode_page_monthly.jsp");
		return "main";
	}
	
	@RequestMapping(value="/monthly")
	public String monthly(Model m) {
		tdao = sst.getMapper(ToonDAO.class);
		String upload_type = "월간";
	   	ArrayList<ToonDTO> tlist = tdao.monthly_toon_list(upload_type);
	   	for(ToonDTO tdto : tlist) {
	   		if(tdto.getToon_info().length()>300) {
	   			String toon_info = tdto.getToon_info().substring(0,300)+"...";
	   			tdto.setToon_info(toon_info);
	   		}
	   	}
	   	m.addAttribute("tlist", tlist);
	   	m.addAttribute("center", "monthly.jsp");
	   	return "main";
	 }
}
