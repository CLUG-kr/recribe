package com.pm717.recribe;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.pm717.recribe.beans.C;
import com.pm717.recribe.beans.UserDTO;
import com.pm717.recribe.beans.dao.UserDAO;

@Controller
@RequestMapping("/")
public class UserController {

	private SqlSession sqlSession;

	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
		C.sqlSession = sqlSession;
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(Model model) {
		return "/main";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(Model model) {
		return "/search";
	}
	
	@RequestMapping(value = "/review", method = RequestMethod.GET)
	public String review(Model model) {
		return "/review";
	}
	
	@RequestMapping(value = "/community", method = RequestMethod.GET)
	public String community(Model model) {
		return "/community";
	}
	
	@RequestMapping(value = "/friend", method = RequestMethod.GET)
	public String friend(Model model) {
		return "/friend";
	}
	
	@RequestMapping(value = "/group", method = RequestMethod.GET)
	public String group(Model model) {
		return "/group";
	}
	
	@RequestMapping(value = "/loginOrSignup", method = RequestMethod.GET)
	public String loginOrSignup(Model model) {
		return "/loginOrSignup";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String id, String pw, HttpSession session) {
		model.addAttribute("id", id);
		model.addAttribute("pw", pw);

		/*
		 * MemberDAO dao = C.sqlSession.getMapper(MemberDAO.class); if(pe.matches(mb_pw,
		 * dao.login(mb_id))) { int mb_uid = dao.getMbUid(mb_id);
		 * model.addAttribute("mb_uid", mb_uid); int type = dao.getMbType(mb_id);
		 * model.addAttribute("type", type); if(type== 2) { int store_uid =
		 * dao.getStoreUid(mb_uid); model.addAttribute("store_uid", store_uid);
		 * 
		 * } }else { model.addAttribute("mb_uid", 0); model.addAttribute("type", -1); }
		 * 
		 * int mb_uid = (Integer)model.getAttribute("mb_uid"); if(mb_uid != 0) {
		 * session.setAttribute("mb_uid", mb_uid); }
		 */

		return "/loginOk";
	}

	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public String signUp(Model model) {
		return "/signUp";
	}

	@PostMapping(value = "/signUpOk")
	public void signUpOk(Model model, UserDTO user, HttpServletResponse response) throws Exception {
		model.addAttribute("user", user);

		UserDAO dao = C.sqlSession.getMapper(UserDAO.class);

		Map<String, Object> map = model.asMap();
		UserDTO dto = (UserDTO) map.get("user");

		if(dao.insertUser(dto) == 1) { response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter printwriter = response.getWriter(); printwriter.
		print("<script type='text/javascript'>alert('È¸¿ø°¡ÀÔÀÌ ¿Ï·áµÇ¾ú½À´Ï´Ù.');location.href='${pageContext.request.contextPath}/login';</script>"
		); printwriter.flush(); printwriter.close();

		 }else { response.setCharacterEncoding("UTF-8");
		 response.setContentType("text/html; charset=UTF-8");
 
		 PrintWriter printwriter = response.getWriter(); printwriter.
		  print("<script type='text/javascript'>alert('¹®Á¦°¡ ¹ß»ýÇß½À´Ï´Ù. ÀÚ¼¼ÇÑ »çÇ×Àº ¹®ÀÇ¹Ù¶ø´Ï´Ù.');location.href='${pageContext.request.contextPath}/signUp';</script>"
		 ); printwriter.flush(); printwriter.close(); }


	}

	@ResponseBody
	@RequestMapping(value = "/isNicknameDuplicate", method = RequestMethod.POST)
	public int isNicknameDuplicate(String nickname) {

		UserDAO dao = C.sqlSession.getMapper(UserDAO.class);
		int num = dao.isUserNicknameDuplicate(nickname);

		return num;
	}

	@ResponseBody
	@RequestMapping(value = "/isEmailDuplicate", method = RequestMethod.POST)
	public int isEmailDuplicate(String email) {

		UserDAO dao = C.sqlSession.getMapper(UserDAO.class);
		int num = dao.isUserEmailDuplicate(email);

		return num;
	}

	@ResponseBody
	@RequestMapping(value = "sendEmailNumber", method = RequestMethod.POST)
	public String sendEmailNumber(String email) {

		boolean sendEmailNum = false;

		final String SMTP_USERNAME = "b2nyb2st77@naver.com";
		final String SMTP_PASSWORD = "merry1125";
		String HOST = "smtp.naver.com";

		Properties props = new Properties();
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
			}
		});

		String emailNum = "";

		try {

			char pwCollection[] = new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

			for (int i = 0; i < 6; i++) {
				int selectRandomPw = (int) (Math.random() * (pwCollection.length));
				emailNum += pwCollection[selectRandomPw];
			}

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SMTP_USERNAME));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

			message.setSubject("ÀÎÁõ¹øÈ£ ¾È³» ¸ÞÀÏÀÔ´Ï´Ù.");
			message.setText("´ÙÀ½ÀÇ ÀÎÁõ¹øÈ£¸¦ ÀÔ·ÂÇØ È¸¿ø°¡ÀÔÀ» ¿Ï·áÇØÁÖ¼¼¿ä.\n\n" + emailNum);

			Transport.send(message);
			sendEmailNum = true;

		} catch (Exception e) {
			System.out.println(e);
		}

		if (sendEmailNum == true) {
			return emailNum;
		} else {
			return "0";
		}
	}

//	@RequestMapping(value = "sendPassword.do", method=RequestMethod.POST)
//	public ModelAndView sendPassword(HttpServletRequest request, Model model, @RequestParam("email") String email) {
//		ModelAndView mv = new ModelAndView("jsonView");
//		
//		EgovMap paramMap = paramMap(request);
//		paramMap.put("id", email);
//		
//		boolean isEmailExist = false;
//		boolean updatePassword = false;
//		boolean sendPassword = false;
//		
//		if(userService.isEmailDuplicate(paramMap) != 0) {
//			
//			isEmailExist = true;
//			
//			// ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½Ð¹ï¿½È£ï¿½ï¿? ï¿½Ù²ã¼­ ï¿½ï¿½ï¿? ï¿½ï¿½ï¿½ï¿½
//			String newPassword = "";
//			
//			char pwCollection[] = new char[] {
//					'1','2','3','4','5','6','7','8','9','0',
//					'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
//					'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
//			
//			
//			for (int i = 0; i < 8; i++) {
//				int selectRandomPw = (int)(Math.random()*(pwCollection.length));
//				newPassword += pwCollection[selectRandomPw];
//			}
//			
//			PasswordEncoding pe = new PasswordEncoding();
//			
//			paramMap.put("password", pe.encode(newPassword));
//			
//			if(userService.updatePassword(paramMap) != 0) {
//				
//				updatePassword = true;
//				
//				final String SMTP_USERNAME = "dongjakin@gmail.com";
//				final String SMTP_PASSWORD = "developer1Q";
//				
//				Properties props = new Properties();  		
//				props.put("mail.smtp.starttls.enable", "true");
//				props.put("mail.smtp.host", "smtp.gmail.com");
//				props.put("mail.smtp.auth", "true");
//				props.put("mail.smtp.port", "587");
//				
//				Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
//					protected PasswordAuthentication getPasswordAuthentication() {
//						return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
//					}   
//				});
//				
//				
//				try {			
//					MimeMessage message = new MimeMessage(session);
//					message.setFrom(new InternetAddress(SMTP_USERNAME));
//					message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
//					
//					message.setSubject("ï¿½Ó½ï¿½ ï¿½ï¿½Ð¹ï¿½È? ï¿½ß¼ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Ô´Ï´ï¿½.");
//					message.setText("ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½Ð¹ï¿½È£ï¿½ï¿? ï¿½Î±ï¿½ï¿½ï¿½ ï¿½Ï½ï¿½ ï¿½ï¿½ ï¿½ï¿½Ð¹ï¿½È£ï¿½ï¿? ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ö¼ï¿½ï¿½ï¿½.\n\n" + newPassword);
//					
//					Transport.send(message);
//					sendPassword = true;			
//				}catch (Exception e) {
//					System.out.println(e);
//					sendPassword = false;				
//				}	
//
//			} else {
//				updatePassword = false;
//			}
//			
//		}else {
//			isEmailExist = false;
//		}
//		
//		mv.addObject("sendPassword", sendPassword);
//		mv.addObject("isEmailExist", isEmailExist);
//		mv.addObject("updatePassword", updatePassword);
//		return mv;
//		
//	}
//	
	@ResponseBody
	@RequestMapping(value = "/findEmail", method = RequestMethod.POST)
	public String findEmail(String nickname) {

		boolean isNicknameExist = false;
		String newEmail = "";

		UserDAO dao = C.sqlSession.getMapper(UserDAO.class);

		if (dao.isUserNicknameDuplicate(nickname) != 0) {
			isNicknameExist = true;
			String email = dao.findEmailByNickname(nickname);
			String[] emailArr = email.split("@");

			for (int i = 0; i < emailArr[0].length(); i++) {
				if (i < 5) {
					newEmail += emailArr[0].charAt(i);
				} else {
					newEmail += "*";
				}
			}
			newEmail += "@";
			newEmail += emailArr[1];

		} else {
			isNicknameExist = false;
		}

		if (isNicknameExist == true) {
			return newEmail;
		} else {
			return "0";
		}
	}
//	
//	@RequestMapping(value = "/findEmail2.do", method=RequestMethod.POST)
//	public ModelAndView findEmail2(HttpServletRequest request, Model model) {
//		ModelAndView mv = new ModelAndView("jsonView");
//		EgovMap paramMap = this.paramMap(request);
//		
//		boolean isNicknameExist = false;
//		String newEmail = "";
//		
//		if(userService.isPhoneDuplicate(paramMap) != 0) {
//			isNicknameExist = true;
//			String email = userService.findEmailByPhone(paramMap);
//			String[] emailArr = email.split("@");
//			
//			for(int i = 0; i < emailArr[0].length(); i++) {
//				if(i < 5) {
//					newEmail +=emailArr[0].charAt(i);
//				}else {
//					newEmail +="*";
//				}
//			}
//			newEmail += "@";
//			newEmail += emailArr[1];
//			
//		} else {
//			isNicknameExist = false;
//		}
//		mv.addObject("isNicknameExist", isNicknameExist);
//		mv.addObject("email", newEmail);
//		return mv;
//	}
//	

//	@RequestMapping(value = "/joinUser2.do", method=RequestMethod.POST)
//	public ModelAndView join4(HttpServletRequest request) throws Exception{
//		String profileUploadPath = File.separator + "data" + File.separator + "nas" + File.separator + "user"+ File.separator;
//		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) request;
//		
//		MultipartFile imgFile = multi.getFile("img_upload");
//		EgovMap paramMap = paramMap(request);
//
//		try {
//    		String orgFileNm = imgFile.getOriginalFilename();
//    		String orgFileEx = orgFileNm.substring(orgFileNm.lastIndexOf(".") + 1);
//    		String saveName = (String) paramMap.get("email1") + "." + orgFileEx;
//			
//			File uploadDir = new File(profileUploadPath);
//			//ï¿½ï¿½ï¿½Í¸ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½Ë»ï¿½
//			if(!uploadDir.exists()) {
//				uploadDir.mkdirs();
//			}
//			
//			File target = new File(profileUploadPath, saveName);
//			imgFile.transferTo(target);  //ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
//
//			paramMap.put("imgurl", "/image/user/"+saveName);
//		}catch(Exception e) {
//			System.out.println(e);
//		}
//		
//		if(paramMap.get("email2") == null) {
//			paramMap.put("email", paramMap.get("email1")+"@"+paramMap.get("emailSelect"));
//			paramMap.put("id", paramMap.get("email1")+"@"+paramMap.get("emailSelect"));
//		}
//		else {
//			paramMap.put("email", paramMap.get("email1")+"@"+paramMap.get("email2"));
//			paramMap.put("id", paramMap.get("email1")+"@"+paramMap.get("email2"));
//		}
//		
//		paramMap.put("careers", ((String)paramMap.get("career[]")).split(","));
//		paramMap.put("active", true);
//		
//		String password = (String)paramMap.get("password");
//		PasswordEncoding pe = new PasswordEncoding();
//		password = pe.encode(password);
//		
//		paramMap.put("password", password);
//				
//				
//		if(userService.insertUser(paramMap)) {
//			return new ModelAndView("redirect:/joinComplete.do");
//		} else {
//			return new ModelAndView("redirect:/login.do");
//		}
//	}

	@RequestMapping(value = "/joinComplete.do")
	public ModelAndView joinComplete(HttpServletRequest request,
			@RequestParam(required = false) Map<String, String> params, ModelAndView mv, HttpSession session,
			Model model) throws Exception {
		String viewName = "main/join3";
		mv.setViewName(viewName);

		return mv;
	}

	@RequestMapping(value = "/findPw")
	public ModelAndView findPassword(HttpServletRequest request,
			@RequestParam(required = false) Map<String, String> params, ModelAndView mv, HttpSession session,
			Model model) throws Exception {
		String viewName = "findPw";
		mv.setViewName(viewName);

		return mv;
	}

	@RequestMapping(value = "/findId")
	public ModelAndView findID(HttpServletRequest request, @RequestParam(required = false) Map<String, String> params,
			ModelAndView mv, HttpSession session, Model model) throws Exception {
		String viewName = "findId";
		mv.setViewName(viewName);

		return mv;
	}

//	@RequestMapping(value = "/login2.do", method=RequestMethod.POST)
//	public ModelAndView login(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ModelAndView mv = new ModelAndView("main/login");
//		
//		String referer = (String)request.getHeader("REFERER");
//		
//		mv.addObject("title", "ï¿½Î±ï¿½ï¿½ï¿½");
//		mv.addObject("url", "login");
//		
//		String[] site = referer.split("/");
//		session.setAttribute("site", site.length >=4 ? site[site.length - 1] : "");
//		
//		EgovMap paramMap = paramMap(request);
//		paramMap.put("id", ((String)paramMap.get("userid")));	
//		String password = (String)paramMap.get("password");
//		PasswordEncoding pe = new PasswordEncoding();
//		
//		if(pe.matches(password, userService.selectPwById(paramMap))) { //ï¿½Î±ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
//
//			UserVO user = userService.selectUserById(paramMap);
//			if(user == null) {
//				return new ModelAndView("redirect:/join.do");				
//			} else if (!user.isActive()){
//				response.setCharacterEncoding("UTF-8");
//				response.setContentType("text/html; charset=UTF-8");
//				
//				PrintWriter printwriter = response.getWriter();
//				printwriter.print("<script type='text/javascript'>alert('ï¿½ï¿½È°ï¿½ï¿½È­ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Ô´Ï´ï¿½. ï¿½Ú¼ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½Ù¶ï¿½ï¿½Ï´ï¿½.');location.href='login.do';</script>");
//				printwriter.flush();
//				printwriter.close();
//				
//				return new ModelAndView("redirect:/login.do");
//			} 
//			else if (user.isBlocked()) {
//				response.setCharacterEncoding("UTF-8");
//				response.setContentType("text/html; charset=UTF-8");
//				
//				PrintWriter printwriter = response.getWriter();
//				printwriter.print("<script type='text/javascript'>alert('ï¿½ï¿½ï¿½ï¿½ï¿½Ú¿ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½Î±ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½Ñµï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Ô´Ï´ï¿½. ï¿½Ú¼ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½Ù¶ï¿½ï¿½Ï´ï¿½.');location.href='login.do';</script>");
//				printwriter.flush();
//				printwriter.close();
//				
//				return new ModelAndView("redirect:/login.do");
//			}
//			else {
//				session.setAttribute("userInfo", user);
//				EgovMap param = new EgovMap();
//		    	
//				SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
//				Date time = new Date();
//				
//				param.put("user", user.getId());
//				param.put("login", format.format(time));
//				userService.updateLastLogin(param);
//				return new ModelAndView("redirect:/"+session.getAttribute("site"));
//			}		
//			
//		}else { // ï¿½Î±ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
//			
//			response.setCharacterEncoding("UTF-8");
//			response.setContentType("text/html; charset=UTF-8");
//			
//			PrintWriter printwriter = response.getWriter();
//			printwriter.print("<script type='text/javascript'>alert('ï¿½ï¿½ï¿½Ìµï¿½ ï¿½Ç´ï¿½ ï¿½ï¿½Ð¹ï¿½È£ï¿½ï¿? ï¿½ï¿½ï¿½ï¿½ ï¿½Ê½ï¿½ï¿½Ï´ï¿½. ï¿½Ù½ï¿½ ï¿½Î±ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ö½Ã±ï¿½ ï¿½Ù¶ï¿½ï¿½Ï´ï¿½.');location.href='login.do';</script>");
//			printwriter.flush();
//			printwriter.close();
//			
//			return new ModelAndView("redirect:/login.do");
//		}
//		
//
//	}

	@RequestMapping("/logout.do")
	public ModelAndView logout(HttpServletRequest request, HttpSession session) {
		session.invalidate();
		String referer = request.getHeader("Referer");
		ModelAndView mv = new ModelAndView("redirect:" + referer);
		return mv;
	}

}
