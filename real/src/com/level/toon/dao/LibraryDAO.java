package com.level.toon.dao;

import java.util.ArrayList;

import com.level.toon.dto.LibraryDTO;

public interface LibraryDAO {
	public void add_library(LibraryDTO ldto);
	public int library_count(LibraryDTO ldto);
	public void update_library(LibraryDTO ldto);
	public void delete_library(LibraryDTO ldto);
	public LibraryDTO library_info(LibraryDTO ldto);
	public ArrayList<LibraryDTO> library_list(int member_num);
}
