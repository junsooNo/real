package com.level.toon;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.level.toon.dao.MemberDAO;
import com.level.toon.dao.UseDAO;
import com.level.toon.dto.MemberDTO;
import com.level.toon.dto.UseDTO;

@Controller
public class MemberController {

	@Autowired
	private SqlSessionTemplate sst;
	private MemberDAO mdao;
	private UseDAO udao;
	
	@RequestMapping(value="/overlap_id")
	public void overlap_id(HttpServletResponse resp, String email) {
		mdao=sst.getMapper(MemberDAO.class);
		int overlap_id = mdao.overlap_id(email);
		System.out.println(overlap_id);
		try {
			resp.getWriter().print(overlap_id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/join_ok")
	public String join_ok(MemberDTO mdto, Model m) {
		mdao=sst.getMapper(MemberDAO.class);
		mdao.join_ok(mdto);
		m.addAttribute("center","center.jsp");
		return "main";
	}
	
	@RequestMapping(value="/login_ok")
	public void login_ok(HttpServletResponse resp, HttpSession s, MemberDTO mdto, Model m) {
		mdao=sst.getMapper(MemberDAO.class);
		int login=0;
		int overlap_id = mdao.overlap_id(mdto.getEmail());
		int login_ok = mdao.login_ok(mdto);
		if(overlap_id==0) {
			login=1;
		}else if(overlap_id==1 && login_ok==0) {
			login=2;
		}else if(overlap_id==1 && login_ok==1) {
			login=0;
			int member_num = mdao.find_member_num(mdto);
			
			s.setAttribute("member_num", member_num);
			
			String email = mdto.getEmail();			
			MemberDTO mdto2 = mdao.member_info(email);
			s.setAttribute("email", email);
			s.setAttribute("coin", mdto2.getCoin());
		}
		try {
			resp.getWriter().print(login);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession s, Model m) {
		s.invalidate();
		m.addAttribute("center","center.jsp");
		return "redirect:main";
	}
	
	@RequestMapping(value="/my_page")
	public String my_page(Model m) {
		m.addAttribute("center", "my_page.jsp");
		return "main";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/coin_subtract")
	public void coin_subtract(int view_coin, int member_num, UseDTO udto, HttpServletResponse resp, HttpSession s) {
		mdao = sst.getMapper(MemberDAO.class);
		udao = sst.getMapper(UseDAO.class);
		int success = 0;
		udao.add_use(udto);
		mdao.coin_subtract(view_coin, member_num);
			
		String email = (String) s.getAttribute("email");
		MemberDTO mdto = mdao.member_info(email);
		int coin = mdto.getCoin();
		s.removeAttribute("coin");
		s.setAttribute("coin", coin);
		success = 1;
		try {
			resp.getWriter().print(success);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
