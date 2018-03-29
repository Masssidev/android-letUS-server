package net.skhu.mapper;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.LikeDTO;

@Mapper
public interface LikeMapper {
	void insert(LikeDTO ldto);
	void delete(LikeDTO ldto);
	Integer findLikeChecked(int aId, int uId);
}
