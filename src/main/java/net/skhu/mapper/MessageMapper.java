package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.skhu.dto.MessageDTO;

@Mapper
public interface MessageMapper {
	MessageDTO findOneSentMessage(int u_sendId);// 받은 쪽지 하나 보기
	MessageDTO findOneReceivedMessage(int u_receiveId);
	List<MessageDTO> sentList(int u_sendId);
	List<MessageDTO> receivedList(int u_receiveId);
	void sendMessage(MessageDTO message);// 쪽지 보내기
	void removeReceivedMessage(int id);
	void removeSentMessage(int id);
	void read(int articleId, int userId);
	int count(int userId);
}
