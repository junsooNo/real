package com.level.toon;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.level.toon.dao.UseDAO;
import com.level.toon.dto.UseDTO;

@Controller
public class UseController {

	@Autowired
	private SqlSessionTemplate sst;
	private UseDAO udao;
	
	
	@RequestMapping(value="find_use")
	public void fine_use(UseDTO udto,HttpServletResponse resp) {
		udao = sst.getMapper(UseDAO.class);
		int count = udao.use_count(udto);
		try {
			resp.getWriter().print(count);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
