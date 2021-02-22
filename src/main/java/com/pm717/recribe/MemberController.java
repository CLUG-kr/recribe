package com.pm717.recribe;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pm717.recribe.beans.C;

@Controller
public class MemberController {

	private SqlSession sqlSession;

	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
		C.sqlSession = sqlSession;
	}

	@RequestMapping(value="/main", method = RequestMethod.GET)
	public String main(Model model) {
		return "/main"; 
	}
	
	@RequestMapping(value="/loginOrSignup", method = RequestMethod.GET)
	public String loginOrSignup(Model model) {
		return "/loginOrSignup"; 
	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
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
	
	@RequestMapping(value="/signUp", method = RequestMethod.GET)
	public String signUp(Model model) {
		return "/signUp"; 
	}
	

}
