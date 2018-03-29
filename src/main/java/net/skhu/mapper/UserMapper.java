package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.FileDTO;
import net.skhu.dto.UserDTO;

@Mapper
public interface UserMapper {
	String login(UserDTO dto);
	void register(UserDTO dto);
	UserDTO checkId(UserDTO dto);
	UserDTO checkNickname(UserDTO dto);
	UserDTO bring(UserDTO dto);
	UserDTO findNicknameAndEmail(String info);
	List<UserDTO> findMember(int articleId, int userId);
	void change(UserDTO dto);
	UserDTO find(UserDTO dto);
	void savePw(UserDTO dto);
	void image(FileDTO fdto);
	int getIdByUserId(String user_id);
	String getNicknameBySendMessage(int id);
	String getNicknameByReceiveMessage(int id);
	Integer getIdByNickname(String nickname);
	Integer findUserId(String id);
}
