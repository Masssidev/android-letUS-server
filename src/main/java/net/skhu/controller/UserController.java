package net.skhu.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.skhu.checkEmail.CheckEmail;
import net.skhu.dto.ArticleDTO;
import net.skhu.dto.FileDTO;
import net.skhu.dto.LikeDTO;
import net.skhu.dto.MessageDTO;
import net.skhu.dto.StudyGroupDTO;
import net.skhu.dto.UserDTO;
import net.skhu.mapper.ArticleMapper;
import net.skhu.mapper.FileMapper;
import net.skhu.mapper.LikeMapper;
import net.skhu.mapper.MessageMapper;
import net.skhu.mapper.StudyGroupMapper;
import net.skhu.mapper.UserMapper;
import net.skhu.random.NewPassWord;
import net.skhu.sendEmail.EmailService;

@RestController
public class UserController {
	@Autowired
	UserMapper userMapper;
	@Autowired
	FileMapper fileMapper;
	@Autowired
	ArticleMapper articleMapper;
	@Autowired
	MessageMapper messageMapper;
	@Autowired
	LikeMapper likeMapper;
	@Autowired
	StudyGroupMapper studyGroupMapper;

	ArticleDTO adto = new ArticleDTO();
	UserDTO dto = new UserDTO();
	FileDTO fdto = new FileDTO();
	MessageDTO mdto = new MessageDTO();
	LikeDTO ldto = new LikeDTO();

	@Autowired
	EmailService emailService;
	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "login")
	public Map<String, Object> login(String id, String pw, HttpSession session) {

		HashMap<String, Object> obj = new HashMap<String, Object>();
		String result;
		Integer user = null;

		if ("".equals(id) || "".equals(pw)) {
			if ("".equals(id)) {
				obj.put("message", "아이디를 입력해주세요");
				obj.put("success", "no");
			} else if ("".equals(pw)) {
				obj.put("message", "패스워드를 입력해주세요");
				obj.put("success", "no");
			}
		} else {
			user = userMapper.findUserId(id);
			if (user != null) {
				dto.setUser_id(id);
				dto.setPassword(pw);

				result = userMapper.login(dto);

				if (result == null) {
					obj.put("message", "아이디와 패스워드가 일치하지 않습니다");
					obj.put("success", "no");
				} else {
					session.setAttribute("loginId", id);
					System.out.println(session.getAttribute("loginId").toString());
					session.setAttribute("loginPw", pw);
					obj.put("message", result + "님 환영합니다");
					obj.put("success", "yes");
				}
			} else {
				obj.put("message", "존재하지 않는 ID입니다");
				obj.put("success", "no");

			}

		}
		return obj;
	}

	@RequestMapping(value = "register")
	public Map<String, Object> register(String id, String pw, String nickname, String email) {

		HashMap<String, Object> obj = new HashMap<String, Object>();

		if ("".equals(id) || "".equals(pw) || "".equals(nickname) || "".equals(email)) {
			if ("".equals(id)) {
				obj.put("message", "아이디를 입력해주세요");
				obj.put("success", "no");
			} else if ("".equals(pw)) {
				obj.put("message", "패스워드를 입력해주세요");
				obj.put("success", "no");
			} else if ("".equals(nickname)) {
				obj.put("message", "닉네임을 입력해주세요");
				obj.put("success", "no");
			} else if ("".equals(email)) {
				obj.put("message", "이메일을 입력해주세요");
				obj.put("success", "no");
			}
		} else {
			boolean b = CheckEmail.checkEmail(email);
			if (b == true) {
				dto.setUser_id(id);
				dto.setPassword(pw);
				dto.setNickname(nickname);
				dto.setEmail(email);
				userMapper.register(dto);
				obj.put("message", "가입이 완료되었습니다.");
				obj.put("success", "yes");
			} else {
				obj.put("message", "이메일 형식이 올바르지 않습니다.");
				obj.put("success", "no");
			}
		}
		return obj;
	}

	@RequestMapping(value = "checkId")
	public Map<String, Object> checkId(String id) {

		HashMap<String, Object> obj = new HashMap<String, Object>();
		UserDTO result;

		if ("".equals(id)) {
			obj.put("message", "아이디를 입력해주세요");
			obj.put("success", "no");
		} else {
			dto.setUser_id(id);

			result = userMapper.checkId(dto);

			if (result == null) {
				obj.put("message", "사용 가능한 아이디입니다.");
				obj.put("success", "yes");
			} else {
				obj.put("message", "이미 사용중인 아이디입니다.");
				obj.put("success", "no");
			}
		}
		return obj;
	}

	@RequestMapping(value = "checkNickname")
	public Map<String, Object> checkNickname(String nickname) {

		HashMap<String, Object> obj = new HashMap<String, Object>();
		UserDTO result;

		if ("".equals(nickname)) {
			obj.put("message", "닉네임을 입력해주세요");
			obj.put("success", "no");
		} else {
			dto.setNickname(nickname);

			result = userMapper.checkNickname(dto);

			if (result == null) {
				obj.put("message", "사용 가능한 닉네임입니다.");
				obj.put("success", "yes");
			} else {
				obj.put("message", "이미 사용중인 닉네임입니다.");
				obj.put("success", "no");
			}
		}
		return obj;
	}

	@RequestMapping(value = "bring")
	public Map<String, Object> bring(HttpSession session) {

		HashMap<String, Object> obj = new HashMap<String, Object>();
		UserDTO result;

		if (session.getAttribute("loginId") == null) {
			obj.put("success", "no");
		} else {
			dto.setUser_id(session.getAttribute("loginId").toString());

			result = userMapper.bring(dto);

			obj.put("nickname", result.getNickname());
			obj.put("email", result.getEmail());
		}
		return obj;
	}

	@RequestMapping(value = "getNickname")
	public Map<String, Object> getNickname(HttpSession session) {

		HashMap<String, Object> obj = new HashMap<String, Object>();
		UserDTO result;

		if (session.getAttribute("loginId") == null) {
			obj.put("success", "no");
		} else {
			int id = userMapper.getIdByUserId(session.getAttribute("loginId").toString());
			int count = messageMapper.count(id);

			obj.put("count", count);

			dto.setUser_id(session.getAttribute("loginId").toString());
			result = userMapper.bring(dto);

			obj.put("nickname", result.getNickname());
		}
		return obj;
	}

	@RequestMapping(value = "change")
	public Map<String, Object> change(String nickname, String pw, String email, HttpSession session) {

		HashMap<String, Object> obj = new HashMap<String, Object>();

		if (session.getAttribute("loginId") == null) {
			obj.put("success", "no");
		} else {

			if ("".equals(nickname) || "".equals(email)) {
				if ("".equals(nickname)) {
					obj.put("message", "닉네임을 입력해주세요");
					obj.put("success", "no");
				} else if ("".equals(email)) {
					obj.put("message", "이메일을 입력해주세요");
					obj.put("success", "no");
				}
			} else {
				dto.setUser_id(session.getAttribute("loginId").toString());
				if ("".equals(pw)) {
					dto.setPassword(session.getAttribute("loginPw").toString());
				} else {
					dto.setPassword(pw);
				}
				dto.setNickname(nickname);
				dto.setEmail(email);

				userMapper.change(dto);
				obj.put("message", "수정이 완료되었습니다.");
				obj.put("success", "yes");
			}
		}

		return obj;
	}

	@RequestMapping(value = "find")
	public Map<String, Object> find(String nickname, String email) {

		HashMap<String, Object> obj = new HashMap<String, Object>();
		UserDTO result;
		if ("".equals(nickname) || "".equals(email)) {

			if ("".equals(nickname)) {
				obj.put("user_id", "null");
				obj.put("message", "닉네임을 입력해주세요");
				obj.put("success", "no");
			} else if ("".equals(email)) {
				obj.put("user_id", "null");
				obj.put("message", "이메일을 입력해주세요");
				obj.put("success", "no");
			}

		} else {
			dto.setNickname(nickname);
			dto.setEmail(email);
			result = userMapper.find(dto);

			if (result == null) {
				obj.put("user_id", "null");
				obj.put("message", "존재하지않는 정보입니다.");
				obj.put("success", "no");
			} else {
				obj.put("user_id", result.getUser_id());
				obj.put("message", "null");
				obj.put("success", "yes");
			}
		}
		return obj;
	}

	@RequestMapping(value = "send")
	public Map<String, Object> send(String user_id, String email) {

		HashMap<String, Object> obj = new HashMap<String, Object>();

		int newPw = NewPassWord.generateNumber(8);
		String newPassWord = Integer.toString(newPw);

		dto.setPassword(newPassWord);
		userMapper.savePw(dto);

		emailService.sendSimpleMessage(email, "안녕하세요~ 렛츠입니다!", "아이디: " + user_id + "\n임시비밀번호: " + newPw);
		obj.put("message", "해당 이메일로 아이디와 임시비밀번호가 발송되었습니다.");

		return obj;
	}

	@RequestMapping(value = "post")
	public Map<String, Object> post(String category, String title, String member, String start, String end, String text,
			String board, @RequestBody MultipartFile file, HttpServletRequest request, HttpSession session) {

		HashMap<String, Object> obj = new HashMap<String, Object>();
		if ("분야".equals(category) || "".equals(title) || "".equals(text) || "".equals(member)) {
			if ("분야".equals(category)) {
				obj.put("message", "카테고리를 설정해주세요");
				obj.put("success", "no");
			} else if ("".equals(title)) {
				obj.put("message", "제목을 입력해주세요");
				obj.put("success", "no");
			} else if ("".equals(member)) {
				obj.put("message", "모집인원을 입력해주세요");
				obj.put("success", "no");
			} else if ("".equals(text)) {
				obj.put("message", "내용을 입력해주세요");
				obj.put("success", "no");
			}
		} else if (file != null) {
			String relPath = "img/upload/";
			File upDirectory = new File(relPath);
			if (!upDirectory.exists()) {
				upDirectory.mkdirs();
			}

			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

			relPath += fileName;

			final File uploadFile = new File(relPath);
			if (uploadFile.exists()) {
				uploadFile.delete();
			}

			try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadFile))) {
				FileCopyUtils.copy(file.getInputStream(), stream);
			} catch (FileNotFoundException e) {
				return null;
			} catch (IOException ioe) {
				return null;
			}

			fdto.setPath(relPath);
			fileMapper.insert(fdto);

			int bo_id = Integer.parseInt(board);
			int u_id = userMapper.getIdByUserId(session.getAttribute("loginId").toString());

			adto.setBo_id(bo_id);
			adto.setTitle(title);
			adto.setStart_date(start);
			adto.setExpire_date(end);
			adto.setF_id(fdto.getId());
			adto.setU_id(u_id);
			adto.setCategory(category);
			adto.setContent(text);

			if (member != null) {
				int memberCount = Integer.parseInt(member);
				adto.setMember(memberCount);
				articleMapper.insertStudy(adto);
				studyGroupMapper.insert(adto.getId(), u_id);

			} else {
				articleMapper.insertFair(adto);
			}

			obj.put("message", "등록되었습니다.");
			obj.put("success", "yes");

		} else {
			fdto.setPath("img/upload/006742[1].jpg");
			fileMapper.insert(fdto);

			int bo_id = Integer.parseInt(board);
			int u_id = userMapper.getIdByUserId(session.getAttribute("loginId").toString());

			adto.setU_id(u_id);
			adto.setBo_id(bo_id);
			adto.setTitle(title);
			adto.setStart_date(start);
			adto.setExpire_date(end);
			adto.setF_id(fdto.getId());
			adto.setCategory(category);
			adto.setContent(text);

			if (member != null) {
				int memberCount = Integer.parseInt(member);
				adto.setMember(memberCount);
				articleMapper.insertStudy(adto);

				studyGroupMapper.insert(adto.getId(), u_id);
			} else {
				articleMapper.insertFair(adto);
			}

			obj.put("message", "등록되었습니다.");
			obj.put("success", "yes");
		}
		return obj;
	}

	@RequestMapping(value = "findMain")
	public Map<String, Object> findMain(HttpSession session) {

		HashMap<String, Object> obj = new HashMap<String, Object>();
		int user = -1;

		if (session.getAttribute("loginId") != null) {
			user = userMapper.getIdByUserId(session.getAttribute("loginId").toString());
			obj.put("id", user);

			List<ArticleDTO> result = articleMapper.findMainStudy();
			List<ArticleDTO> result2 = articleMapper.findMainNotice();

			obj.put("result", result);
			obj.put("result2", result2);

		} else {
			List<ArticleDTO> result = articleMapper.findMainStudy();
			List<ArticleDTO> result2 = articleMapper.findMainNotice();

			obj.put("id", user);
			obj.put("result", result);
			obj.put("result2", result2);
		}
		return obj;
	}

	@RequestMapping(value = "searchStudy")
	public Map<String, Object> searchStudy(String search, HttpSession session) {

		HashMap<String, Object> obj = new HashMap<String, Object>();
		List<ArticleDTO> result;
		int user = userMapper.getIdByUserId(session.getAttribute("loginId").toString());

		if ("".equals(search)) {
			obj.put("id", user);
			obj.put("result", "검색어를 입력해주세요");
		} else {
			obj.put("id", user);
			result = articleMapper.searchStudy(search);
			obj.put("result", result);
			System.out.println(user);
		}
		return obj;
	}

	@RequestMapping(value = "searchNotice")
	public Map<String, Object> searchNotice(String search, HttpSession session) {

		HashMap<String, Object> obj = new HashMap<String, Object>();
		List<ArticleDTO> result;
		int user = userMapper.getIdByUserId(session.getAttribute("loginId").toString());

		if ("".equals(search)) {
			obj.put("id", user);
			obj.put("result", "검색어를 입력해주세요");
		} else {
			result = articleMapper.searchNotice(search);
			obj.put("result", result);
			obj.put("id", user);
			System.out.println(user);
		}
		return obj;
	}

	@RequestMapping(value = "findAllStudy")
	public Map<String, Object> findAllStudy(HttpSession session) {

		HashMap<String, Object> obj = new HashMap<String, Object>();
		int user = -1;

		if (session.getAttribute("loginId") != null) {
			user = userMapper.getIdByUserId(session.getAttribute("loginId").toString());
			obj.put("id", user);

			List<ArticleDTO> result = articleMapper.findAllStudy();

			obj.put("result", result);

		} else {
			List<ArticleDTO> result = articleMapper.findAllStudy();

			obj.put("id", user);
			obj.put("result", result);
		}

		return obj;
	}

	@RequestMapping(value = "findAllNotice")
	public Map<String, Object> findAllNotice(HttpSession session) {

		HashMap<String, Object> obj = new HashMap<String, Object>();
		int user = -1;

		if (session.getAttribute("loginId") != null) {
			user = userMapper.getIdByUserId(session.getAttribute("loginId").toString());
			obj.put("id", user);

			List<ArticleDTO> result = articleMapper.findAllNotice();

			obj.put("result", result);

		} else {
			List<ArticleDTO> result = articleMapper.findAllNotice();

			obj.put("id", user);
			obj.put("result", result);
		}

		return obj;
	}

	@RequestMapping(value = "getimage")
	public ResponseEntity<byte[]> getImage(@RequestParam("id") int id) {

		String fileName = fileMapper.getImage(id);
		String filePath;
		if (fileName.equals("img/upload/006742[1].jpg")) {
			filePath = servletContext.getRealPath("/img/upload/");
			fileName = filePath + fileName.substring(11);
		}
		Path path = Paths.get(fileName);

		byte[] image = null;
		try {
			image = Files.readAllBytes(path);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
		}
	}

	@RequestMapping(value = "likeBox")
	public Map<String, Object> likeBox(HttpSession session) {
		HashMap<String, Object> obj = new HashMap<String, Object>();

		int id = userMapper.getIdByUserId(session.getAttribute("loginId").toString());
		List<ArticleDTO> result = articleMapper.findLikeStudy(id);
		List<ArticleDTO> result2 = articleMapper.findLikeNotice(id);

		obj.put("result", result);
		obj.put("result2", result2);
		return obj;
	}

	@RequestMapping("myArticle")
	public Map<String, Object> myArticle(HttpSession session) {
		HashMap<String, Object> obj = new HashMap<String, Object>();

		int id = userMapper.getIdByUserId(session.getAttribute("loginId").toString());
		List<ArticleDTO> result = articleMapper.findStudyByUser(id);
		List<ArticleDTO> result2 = articleMapper.findNoticeByUser(id);

		obj.put("result", result);
		obj.put("result2", result2);
		return obj;
	}

	@RequestMapping(value = "sendMessage")
	public Map<String, Object> sendMessage(String receive, String con, HttpSession session) {

		HashMap<String, Object> obj = new HashMap<String, Object>();

		if ("".equals(receive) || "".equals(con)) {
			if ("".equals(receive)) {
				obj.put("message", "받는사람을 입력해주세요");
				obj.put("success", "no");
			} else if ("".equals(con)) {
				obj.put("message", "메세지 내용을 입력해주세요");
				obj.put("success", "no");
			}
		} else {
			Integer receiver = userMapper.getIdByNickname(receive);
			if (receiver == null) {
				obj.put("message", "잘못된 수신 닉네임입니다");
				obj.put("success", "no");
			} else {
				int sender = userMapper.getIdByUserId(session.getAttribute("loginId").toString());
				mdto.setU_sendId(sender);
				mdto.setContent(con);
				mdto.setU_receiveId(receiver);

				messageMapper.sendMessage(mdto);
				obj.put("message", "메세지가 발송되었습니다");
				obj.put("success", "yes");
			}
		}
		return obj;
	}

	@RequestMapping(value = "editMyArticle")
	public Map<String, Object> editMyArticle(String id, String title, String content) {
		HashMap<String, Object> obj = new HashMap<String, Object>();

		if ("".equals(title) || "".equals(content)) {
			if ("".equals(title)) {
				obj.put("message", "제목을 입력해주세요");
			} else if ("".equals(content)) {
				obj.put("message", "내용을 입력해주세요");
			}
		} else {
			articleMapper.editMyArticle(Integer.parseInt(id), title, content);
			obj.put("message", "해당 게시글이 수정되었습니다");
		}
		return obj;
	}

	@RequestMapping(value = "findLikeChecked")
	public Map<String, Object> findLikeChecked(String id, HttpSession session) {
		HashMap<String, Object> obj = new HashMap<String, Object>();

		if (session.getAttribute("loginId") != null) {

			int user = userMapper.getIdByUserId(session.getAttribute("loginId").toString());
			Integer like = null;

			like = likeMapper.findLikeChecked(Integer.parseInt(id), user);

			if (like != null) {
				ldto.setA_id(Integer.parseInt(id));
				ldto.setUser_id(user);

				likeMapper.delete(ldto);

				obj.put("message", "찜목록에서 삭제되었습니다");
				obj.put("success", "삭제");
			} else {
				ldto.setA_id(Integer.parseInt(id));
				ldto.setUser_id(user);

				likeMapper.insert(ldto);

				obj.put("message", "찜목록에 추가되었습니다");
				obj.put("success", "추가");
			}
			return obj;
		} else {
			obj.put("success", "로그인해주세요");
			obj.put("message", "로그인해주세요");
			return obj;
		}
	}

	@RequestMapping(value = "deleteMyArticle")
	public Map<String, Object> deleteMyArticle(String id) {
		HashMap<String, Object> obj = new HashMap<String, Object>();

		studyGroupMapper.delete(Integer.parseInt(id));
		articleMapper.deleteMyArticle(Integer.parseInt(id));
		obj.put("message", "해당 게시글이 삭제되었습니다");

		return obj;
	}

	@RequestMapping(value = "findOneSentMessage")
	public Map<String, Object> findOneSentMessage(String id) {
		HashMap<String, Object> obj = new HashMap<String, Object>();
		MessageDTO result;

		result = messageMapper.findOneSentMessage(Integer.parseInt(id));
		String nickname = userMapper.getNicknameBySendMessage(Integer.parseInt(id));

		obj.put("messageReceiver", nickname);
		obj.put("messageContent", result.getContent());
		obj.put("messageTime", result.getWrite_date());

		return obj;
	}

	@RequestMapping(value = "findOneReceivedMessage")
	public Map<String, Object> findOneReceivedMessage(String id, HttpSession session) {

		HashMap<String, Object> obj = new HashMap<String, Object>();
		MessageDTO result;

		int userId = userMapper.getIdByUserId(session.getAttribute("loginId").toString());

		result = messageMapper.findOneReceivedMessage(Integer.parseInt(id));
		String nickname = userMapper.getNicknameByReceiveMessage(Integer.parseInt(id));
		messageMapper.read(Integer.parseInt(id), userId);

		obj.put("messageSender", nickname);
		obj.put("sendMessageContent", result.getContent());
		obj.put("messageTime", result.getWrite_date());

		return obj;
	}

	@RequestMapping(value = "removeReceivedMessage")
	public Map<String, Object> removeReceivedMessage(String id) {

		HashMap<String, Object> obj = new HashMap<String, Object>();

		messageMapper.removeReceivedMessage(Integer.parseInt(id));

		obj.put("message", "쪽지가 삭제되었습니다.");

		return obj;
	}

	@RequestMapping(value = "removeSentMessage")
	public Map<String, Object> removeSentMessage(String id) {

		HashMap<String, Object> obj = new HashMap<String, Object>();

		messageMapper.removeSentMessage(Integer.parseInt(id));

		obj.put("message", "쪽지가 삭제되었습니다.");

		return obj;
	}

	@RequestMapping(value = "findOneFair")
	public Map<String, Object> findOneFair(String id, HttpSession session) {

		HashMap<String, Object> obj = new HashMap<String, Object>();
		ArticleDTO result;
		int user = -1;

		if (session.getAttribute("loginId") != null) {
			user = userMapper.getIdByUserId(session.getAttribute("loginId").toString());

			obj.put("id", user);

			int articleId = Integer.parseInt(id);
			articleMapper.hit(articleId);
			result = articleMapper.findOneFair(articleId);

			obj.put("result", result);
		} else {
			int articleId = Integer.parseInt(id);
			articleMapper.hit(articleId);
			result = articleMapper.findOneFair(articleId);

			obj.put("result", result);
			obj.put("id", user);
		}

		return obj;
	}

	@RequestMapping(value = "findOneStudy")
	public Map<String, Object> findOneStudy(String id, HttpSession session) {

		HashMap<String, Object> obj = new HashMap<String, Object>();
		ArticleDTO result;

		int user = -1;

		if (session.getAttribute("loginId") != null) {
			user = userMapper.getIdByUserId(session.getAttribute("loginId").toString());

			obj.put("id", user);

			int articleId = Integer.parseInt(id);
			articleMapper.hit(articleId);
			result = articleMapper.findOneStudy(articleId);

			int count = studyGroupMapper.count(articleId);

			obj.put("result", result);
			obj.put("count", count);
		} else {
			int articleId = Integer.parseInt(id);
			articleMapper.hit(articleId);
			result = articleMapper.findOneStudy(articleId);

			int count = studyGroupMapper.count(articleId);

			obj.put("count", count);
			obj.put("result", result);
			obj.put("id", user);
		}

		return obj;
	}

	@RequestMapping(value = "like")
	public Map<String, Object> like(String id, HttpSession session) {

		HashMap<String, Object> obj = new HashMap<String, Object>();

		if (session.getAttribute("loginId") != null) {

			int userId = userMapper.getIdByUserId(session.getAttribute("loginId").toString());

			ldto.setA_id(Integer.parseInt(id));
			ldto.setUser_id(userId);

			likeMapper.insert(ldto);

			obj.put("message", "찜목록에 추가되었습니다");
		} else
			obj.put("message", "로그인해주세요");
		return obj;
	}

	@RequestMapping(value = "likeCancel")
	public Map<String, Object> likeCancel(String id, HttpSession session) {

		HashMap<String, Object> obj = new HashMap<String, Object>();

		int userId = userMapper.getIdByUserId(session.getAttribute("loginId").toString());

		ldto.setA_id(Integer.parseInt(id));
		ldto.setUser_id(userId);

		likeMapper.delete(ldto);

		obj.put("message", "찜목록에서 삭제되었습니다");

		return obj;
	}

	@RequestMapping("sentList") // 보낸쪽지 목록 보기
	public Map<String, Object> sentList(HttpSession session) {
		HashMap<String, Object> obj = new HashMap<String, Object>();
		int id = userMapper.getIdByUserId(session.getAttribute("loginId").toString());

		List<MessageDTO> messages = messageMapper.sentList(id);
		obj.put("result", messages);

		return obj;
	}

	@RequestMapping("receivedList") // 받은쪽지 목록 보기
	public Map<String, Object> receivedList(HttpSession session) {
		HashMap<String, Object> obj = new HashMap<String, Object>();
		int id = userMapper.getIdByUserId(session.getAttribute("loginId").toString());

		List<MessageDTO> messages = messageMapper.receivedList(id);
		obj.put("result", messages);

		return obj;
	}

	@RequestMapping("applyStudy")
	public Map<String, Object> applyStudy(String articleId, HttpSession session) {
		HashMap<String, Object> obj = new HashMap<String, Object>();

		if (session.getAttribute("loginId") == null) {
			obj.put("success", "no");
		} else {
			ArticleDTO article = articleMapper.getMemberAndExpire(Integer.parseInt(articleId));
			int member = article.getMember();
			int count = studyGroupMapper.count(Integer.parseInt(articleId));

			SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd");
			String expire = article.getExpire_date();
			Date today = new Date();
			Date expireDay = null;

			try {
				expireDay = format.parse(expire);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(expireDay);
			cal.add(Calendar.DATE, 1);

			expireDay = cal.getTime();

			int compare = today.compareTo(expireDay);

			if (compare > 0) {
				obj.put("success", "expire");

			} else {
				if (member <= count) {
					obj.put("success", "full");

				} else {
					int studyUserId = userMapper.getIdByUserId(session.getAttribute("loginId").toString());

					List<StudyGroupDTO> studyGroupDTO = studyGroupMapper.findAllByUserId(studyUserId);

					for (int i = 0; i < studyGroupDTO.size(); ++i) {
						if (Integer.parseInt(articleId) == studyGroupDTO.get(i).getStudy_group_id()) {
							obj.put("success", "same");
							return obj;
						}
					}
					studyGroupMapper.insert(Integer.parseInt(articleId), studyUserId);

					obj.put("success", "yes");
				}
			}
		}
		return obj;
	}

	@RequestMapping("logout")
	public Map<String, Object> logout(HttpSession session) {
		HashMap<String, Object> obj = new HashMap<String, Object>();
		if (session.getAttribute("loginId") != null) {
			session.removeAttribute("loginId");
			obj.put("message", "로그아웃되었습니다");
		}
		return obj;
	}

	@RequestMapping("myStudy")
	public Map<String, Object> myStudy(HttpSession session) {
		HashMap<String, Object> obj = new HashMap<String, Object>();

		int id = userMapper.getIdByUserId(session.getAttribute("loginId").toString());
		List<StudyGroupDTO> result = studyGroupMapper.findStudy(id);

		obj.put("result", result);

		return obj;
	}

	@RequestMapping("myMember")
	public Map<String, Object> myMember(HttpSession session, String id) {
		HashMap<String, Object> obj = new HashMap<String, Object>();

		int userId = userMapper.getIdByUserId(session.getAttribute("loginId").toString());

		List<UserDTO> result = userMapper.findMember(Integer.parseInt(id), userId);
		String title = articleMapper.findTitle(Integer.parseInt(id));

		obj.put("title", title);
		obj.put("result", result);

		return obj;
	}

	@RequestMapping("mypage")
	public Map<String, Object> mypage(HttpSession session) {
		HashMap<String, Object> obj = new HashMap<String, Object>();
		UserDTO result;

		if (session.getAttribute("loginId") != null) {
			result = userMapper.findNicknameAndEmail(session.getAttribute("loginId").toString());
			int id = userMapper.getIdByUserId(session.getAttribute("loginId").toString());
			int count = messageMapper.count(id);
			obj.put("count", count);
			obj.put("nickname", result.getNickname());
			obj.put("email", result.getEmail());
			obj.put("message", "yes");
		} else
			obj.put("message", "no");
		return obj;
	}

}