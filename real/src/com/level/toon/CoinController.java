package com.level.toon;

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

import com.level.toon.dao.CoinDAO;
import com.level.toon.dao.MemberDAO;
import com.level.toon.dto.CoinDTO;
import com.level.toon.dto.MemberDTO;

@Controller
public class CoinController {

	@Autowired
	private SqlSessionTemplate sst;
	private CoinDAO cdao;
	private MemberDAO mdao;
	
	@RequestMapping(value="/coin_charge")
	public String coin_charge(Model m) {
		cdao = sst.getMapper(CoinDAO.class);
		
		ArrayList<CoinDTO> clist = cdao.coin_list();
		for(CoinDTO cdto : clist) {
			cdto.setSale_price((int)(cdto.getPrice()-cdto.getPrice()*(cdto.getSale()*0.01)));
		}
		m.addAttribute("clist",clist);
		m.addAttribute("center","coin_charge.jsp");
		return "main";
	}
	
	@RequestMapping(value="/coin_charge_next")
	public String coin_charge_next(int coin_num, Model m) {
		cdao = sst.getMapper(CoinDAO.class);
		CoinDTO cdto = cdao.coin_find(coin_num);
		cdto.setSale_price((int)(cdto.getPrice()-cdto.getPrice()*(cdto.getSale()*0.01)));
		m.addAttribute("cdto",cdto);
		m.addAttribute("center","coin_charge_next.jsp");
		return "main";
	}
	
	@RequestMapping(value="/coin_charge_ok")
	public String coin_charge_ok(int coin_num, Model m, HttpSession s) {
		cdao = sst.getMapper(CoinDAO.class);
		mdao = sst.getMapper(MemberDAO.class);
		String s_member_num = s.getAttribute("member_num").toString();
		int member_num = Integer.parseInt(s_member_num);
		CoinDTO cdto = cdao.coin_find(coin_num);
		
		
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("coin", cdto.getQuantity());
		map.put("member_num", member_num);
		
		mdao.coin_update(map);
		
		
		String email = (String)s.getAttribute("email");
		MemberDTO mdto = mdao.member_info(email);
		s.removeAttribute("coin");
		s.setAttribute("coin", mdto.getCoin());
		m.addAttribute("center","center.jsp");
		return "redirect:main";
	}
	

}
