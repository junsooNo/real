package com.level.toon;

import java.util.ArrayList;
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
import com.level.toon.dao.CommentDAO;
import com.level.toon.dto.CommentDTO;
import com.level.toon.dto.CommentRecDTO;
import com.level.toon.dto.EpisodeDTO;
import com.level.toon.dto.ObjectDTO;

@Controller
public class CommentController {
	
	@Autowired
	private SqlSessionTemplate sst;
	private CommentDAO cdao;
	
	@RequestMapping(value="/comment_write")
	public @ResponseBody String Comment_write(CommentDTO cdto) {
		System.out.println("toon_num" + cdto.getToon_num());
		cdao = sst.getMapper(CommentDAO.class);
		cdao.comment_write(cdto);
		
		String json = "";
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		
		ArrayList<ObjectDTO> clist = cdao.comment_list2(cdto);
		map.put("clist", clist);
		
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
		return json;
	}
	
	@RequestMapping(value="/comment_delete")
	public @ResponseBody String Comment_delete(int comment_num) {
		cdao = sst.getMapper(CommentDAO.class);
		cdao.delete_comment(comment_num);
		
		String json = "";
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		
		EpisodeDTO edto = cdao.comment_info(comment_num);
		ArrayList<ObjectDTO> clist = cdao.comment_list(edto);
		map.put("clist", clist);
		
		try {			
			json = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
		return json;
	}
	
	@RequestMapping(value="/rec_comment")
	public @ResponseBody int Rec_comment(CommentRecDTO crdto) {
		cdao = sst.getMapper(CommentDAO.class);
		cdao.insert_rec_comment(crdto);
		int rec_count = cdao.select_rec_count(crdto);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("rec_count", rec_count);
		map.put("comment_num", crdto.getComment_num());
		cdao.update_rec_count(map);
		return rec_count;
	}
	
	@RequestMapping(value="/cancel_rec_comment")
	public @ResponseBody int Cancel_rec_comment(CommentRecDTO crdto) {
		cdao = sst.getMapper(CommentDAO.class);
		cdao.delete_rec_comment(crdto);
		int rec_count = cdao.select_rec_count(crdto);
		Map<String, Object> map = new HashMap<String,Object>();
		System.out.println("can_rec_count : " +rec_count);
		map.put("rec_count", rec_count);
		map.put("comment_num", crdto.getComment_num());
		cdao.update_rec_count(map);
		return rec_count;
	}
	

	@RequestMapping(value="/view_best_comment", produces="application/text;charset=UTF-8")
	public @ResponseBody String View_Best_comment(CommentDTO cdto, EpisodeDTO edto, HttpSession s) {
		cdao = sst.getMapper(CommentDAO.class);
		ArrayList<ObjectDTO> clist = cdao.best_comment(edto);
		System.out.println("clist_size : " + clist.size());
		
		String json = "";
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		int member_num;
		if(s.getAttribute("member_num")!=null) {
			String member_num2 = s.getAttribute("member_num").toString();
			member_num = Integer.parseInt(member_num2);
			for(int i = 0; i < clist.size(); i++) {
				System.out.println("toon_num : " + clist.get(i).getToon_num());
				System.out.println("ep_num : " + clist.get(i).getEp_num());
				int comment_num = clist.get(i).getComment_num();
				int rec_status = cdao.check_rec_status(member_num, comment_num);
				if(rec_status>0) {
					clist.get(i).setRec_status("push");
				}
			}
		}
		map.put("clist", clist);
		
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}	
		return json;
	}
	

	
	@RequestMapping(value="/view_all_comment", produces="application/text;charset=UTF-8")
	public @ResponseBody String View_all_comment(CommentDTO cdto, EpisodeDTO edto, int page_no, HttpSession s) {
		cdao = sst.getMapper(CommentDAO.class);
		String navi = makeNavi(edto, page_no);
		ArrayList<ObjectDTO> clist = list_comment(cdto, page_no);
		
		String json = "";
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		int member_num;
		if(s.getAttribute("member_num")!=null) {
			String member_num2 = s.getAttribute("member_num").toString();
			member_num = Integer.parseInt(member_num2);
			for(int i = 0; i < clist.size(); i++) {
				System.out.println("toon_num : " + clist.get(i).getToon_num());
				System.out.println("ep_num : " + clist.get(i).getEp_num());
				int comment_num = clist.get(i).getComment_num();
				int rec_status = cdao.check_rec_status(member_num, comment_num);
				if(rec_status>0) {
					clist.get(i).setRec_status("push");
				}
			}
		}
		map.put("clist", clist);
		map.put("navi", navi);
		
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}	
		return json;
	}
	
	public String makeNavi(EpisodeDTO edto, int page_no) {
		cdao = sst.getMapper(CommentDAO.class);		
		int totalCount = cdao.comment_count(edto);
		int recordPerPage = 10;
		int pageTotalCount = 0;
				
		if (totalCount % recordPerPage > 0){
			pageTotalCount = totalCount / recordPerPage + 1;
		} else {
			pageTotalCount = totalCount / recordPerPage;
		}
		
		int naviCountPerPage = 5;
		
		if(page_no < 1) page_no = 1;
		else if(page_no > pageTotalCount) page_no = pageTotalCount;
		int startNavi = (page_no-1) / naviCountPerPage * naviCountPerPage + 1;
		int endNavi = startNavi + naviCountPerPage - 1;
				
		if (endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}
		
		boolean needPrev = true;
		boolean needNext = true;
		
		if (startNavi == 1){
			needPrev = false;
		}		
		if (endNavi == pageTotalCount) {
			needNext = false;
		}		
		
		StringBuilder sb = new StringBuilder();			
		if (needPrev) {
			sb.append("<button id='f_navi' class='navi_btn' style='background:#817EE4; color:white; width:42px;' param='" + (startNavi-1) + "'> < </button>");
		}
		for (int i = startNavi; i <= endNavi; i++){
			if(i == page_no) {
				sb.append("<button class='navi_btn' param='" + i + "' style='background:#333; color:white;'>" + i + "</button>");
			} else {
				sb.append("<button class='navi_btn' param='" + i + "'>" + i + "</button>");
			}
			
		}
		if (needNext) {
			sb.append("<button id='b_navi' class='navi_btn' style='background:#817EE4; color:white; width:42px;' param='" + (endNavi+1) + "'> > </button>");
		}
		return sb.toString();
	}
	
	public ArrayList<ObjectDTO> list_comment(CommentDTO cdto, int page_no){
		int end_num = page_no * 10;
		int start_num = end_num - 9;
		
		cdao = sst.getMapper(CommentDAO.class);
		cdto.setEnd_num(end_num);
		cdto.setStart_num(start_num);
		ArrayList<ObjectDTO> clist = cdao.comment_list2(cdto);
		
		return clist;
	}
}
