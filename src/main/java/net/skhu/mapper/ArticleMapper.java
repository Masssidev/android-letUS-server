package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.ArticleDTO;
import net.skhu.dto.FileDTO;

@Mapper
public interface ArticleMapper {
	void insertStudy(ArticleDTO adto);
	void insertFair(ArticleDTO adto);
	void editMyArticle(int id, String title, String content);
	void deleteMyArticle(int id);
	List<ArticleDTO> findAllStudy();
	List<ArticleDTO> findAllNotice();
	List<ArticleDTO> findMainStudy();
	List<ArticleDTO> findMainNotice();
	List<ArticleDTO> findLikeStudy(int id);
	List<ArticleDTO> findLikeNotice(int id);
	List<ArticleDTO> findStudyByUser(int id);
	List<ArticleDTO> findNoticeByUser(int id);
	List<ArticleDTO> searchStudy(String search);
	List<ArticleDTO> searchNotice(String search);
	List<FileDTO> findAllStudyFile();
	ArticleDTO findOneFair(int id);
	ArticleDTO findOneStudy(int id);
	void hit(int id);
	ArticleDTO getMemberAndExpire(int id);
	String findTitle(int id);
}
