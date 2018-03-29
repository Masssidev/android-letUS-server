package net.skhu.mapper;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.FileDTO;

@Mapper
public interface FileMapper {
	void insert(FileDTO fdto);
	String getImage(int id);
}
