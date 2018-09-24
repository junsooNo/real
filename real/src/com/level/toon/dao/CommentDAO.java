package com.level.toon.dao;

import java.util.ArrayList;
import java.util.Map;

import com.level.toon.dto.CommentDTO;
import com.level.toon.dto.CommentRecDTO;
import com.level.toon.dto.EpisodeDTO;
import com.level.toon.dto.ObjectDTO;

public interface CommentDAO {
	public void comment_write(CommentDTO dto);
	public ArrayList<ObjectDTO> best_comment(EpisodeDTO dto);
	public ArrayList<ObjectDTO> comment_list(EpisodeDTO dto);
	public ArrayList<ObjectDTO> comment_list2(CommentDTO dto);
	public int comment_count(EpisodeDTO dto);
	public EpisodeDTO comment_info(int comment_num);
	public void delete_comment(int comment_num);
	public void insert_rec_comment(CommentRecDTO dto);
	public void delete_rec_comment(CommentRecDTO dto);
	public int select_rec_count(CommentRecDTO dto);
	public void update_rec_count(Map<String, Object> map);
	public int check_rec_status(int member_num, int comment_num);
}
