package com.level.toon;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.level.toon.dao.MemberDAO;

@Controller
public class EventController {

	@Autowired
	private SqlSessionTemplate sst;
	
	private MemberDAO mdao;
	
	@RequestMapping(value="/event")
	public String event(Model m) {
		m.addAttribute("center","event.jsp");
		return "main";
	}
	
	@RequestMapping(value="lotto_possible")
	public void possible(HttpServletResponse resp, HttpSession s) {
		mdao = sst.getMapper(MemberDAO.class);
		String s_member_num = s.getAttribute("member_num").toString();
		int member_num = Integer.parseInt(s_member_num);
		String lotto_date = mdao.lotto_date(member_num);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String today = sdf.format(date);
		System.out.println(lotto_date);
		System.out.println(today);
		int possible = 0;
		if(lotto_date==null) {
			lotto_date="start";
		}
		if(lotto_date.equals(today)) {
			System.out.println(lotto_date);
			possible = 1;
		}
		try {
			resp.getWriter().print(possible);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="/lotto", produces="application/text; utf-8")
	public @ResponseBody String lotto(@RequestParam(value="my_number") List<Integer> my_number, HttpSession s) {
		mdao = sst.getMapper(MemberDAO.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String today = sdf.format(date);
		int answer = 0;
		String s_member_num = s.getAttribute("member_num").toString();
		int member_num = Integer.parseInt(s_member_num);
		Map<String,Object> coin_map = new HashMap<String, Object>();
		Map<String,Object> member_map = new HashMap<String, Object>();
		member_map.put("member_num", member_num);
		member_map.put("today", today);
		int[] lotto_number = new int[6];
		for(int i=0; i<lotto_number.length; i++) {
			int random = (int)(Math.random()*10)+1;
			lotto_number[i] = random;
		}
		for(int i=0; i<my_number.size(); i++) {
			if(my_number.get(i)==lotto_number[i]) {
				answer++;
			}
		}
		for(int i=0; i<lotto_number.length; i++) {
			for(int j=0; j<lotto_number.length; j++) {
				if(i!=j && lotto_number[i]==lotto_number[j]) {
					lotto_number[j] = (int)(Math.random()*10)+1;
				}
			}
		}
		
		if(answer==1) {
			int charge_coin = 1;
			coin_map.put("member_num", member_num);
			coin_map.put("coin", charge_coin);
			mdao.coin_update(coin_map);
		}else if(answer==2) {
			int charge_coin = 3;
			coin_map.put("member_num", member_num);
			coin_map.put("coin", charge_coin);
			mdao.coin_update(coin_map);
		}else if(answer==3) {
			int charge_coin = 10;
			coin_map.put("member_num", member_num);
			coin_map.put("coin", charge_coin);
			mdao.coin_update(coin_map);
		}else if(answer==4) {
			int charge_coin = 30;
			coin_map.put("member_num", member_num);
			coin_map.put("coin", charge_coin);
			mdao.coin_update(coin_map);
		}else if(answer==5) {
			int charge_coin = 50;
			coin_map.put("member_num", member_num);
			coin_map.put("coin", charge_coin);
			mdao.coin_update(coin_map);
		}else if(answer==6) {
			int charge_coin = 100;
			coin_map.put("member_num", member_num);
			coin_map.put("coin", charge_coin);
			mdao.coin_update(coin_map);
		}
		
		mdao.lotto_update(member_map);
		
		Map<String, Object> map = new HashMap<String, Object>();
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		map.put("lotto_number", lotto_number);
		map.put("my_number", my_number);
		map.put("answer", answer);
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
