package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.StudyGroupDTO;

@Mapper
public interface StudyGroupMapper {
	List<StudyGroupDTO> findAllByUserId(int studyUserId);
	List<StudyGroupDTO> findStudy(int studyUserId);
	void insert(int studyGruopId, int studyUserId);
	int count(int articleId);
	void delete(int articleId);
}
