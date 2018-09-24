package com.level.toon;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.level.toon.dao.EpisodeDAO;
import com.level.toon.dao.StarScoreDAO;
import com.level.toon.dto.EpisodeDTO;
import com.level.toon.dto.ObjectDTO;
import com.level.toon.dto.StarScoreDTO;

@Controller
public class StarScoreController {
	@Autowired
	private SqlSessionTemplate sst;
	private StarScoreDAO ssdao;
	private EpisodeDAO edao;
	
	@RequestMapping(value="/reg_star_score")
	public @ResponseBody String Reg_star_score(StarScoreDTO ssdto, HttpSession s){
		/*System.out.println("s.member_num : " + s.getAttribute("member_num").toString());
		String member_num2 = s.getAttribute("member_num").toString();
		int member_num = Integer.parseInt(member_num2);
		ssdto.setMember_num(member_num);*/
		ssdao = sst.getMapper(StarScoreDAO.class);
		ssdao.reg_star_score(ssdto);
		edao = sst.getMapper(EpisodeDAO.class);
		edao.update_star_rating(ssdto);
		
		String json="";
		int my_star_score = ssdao.find_star_score(ssdto);
		EpisodeDTO edto = new EpisodeDTO();
		edto.setToon_num(ssdto.getToon_num());
		edto.setEp_num(ssdto.getEp_num());
		ObjectDTO odto = edao.episode_info(edto);
		System.out.println("odto.ep_star_rating : " + odto.getEp_star_rating());
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("ep_star_rating", odto.getEp_star_rating());
		map.put("my_star_score", my_star_score);
		ObjectMapper mapper = new ObjectMapper();
		
		try {			
			json = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
}
