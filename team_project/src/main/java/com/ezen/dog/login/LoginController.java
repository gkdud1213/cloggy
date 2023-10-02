package com.ezen.dog.login;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.LocalDataSourceJobStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.dog.member.MemberDTO;	

@Controller
public class LoginController {
	
	@Autowired
	SqlSession sqlSession;
	
	// �α��� ��
	@RequestMapping(value = "/login-input")
	public String logininput() {
		return "login-input";
	}
	
	// �α���
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		Lservice ms = sqlSession.getMapper(Lservice.class);
		MemberDTO mdto =ms.login(userId,password);
		
		if(mdto!=null){
		HttpSession hs = request.getSession();
		hs.setAttribute("member",mdto);
		hs.setAttribute("loginstate",true);
		hs.setMaxInactiveInterval(60*30);
		return "redirect:main";
		}
		else{
		return "login-input";
		}
	}
	//ReqeustParam���� code�� �޾ƿ���
	@RequestMapping(value = "/kakaoLogin", method = RequestMethod.GET)
	public String kakaoLogin(@RequestParam(value = "code", required = false) String code,HttpServletRequest request) throws Throwable {
		KakaoLoginService service = new KakaoLoginService();
		
		//code�� Token�� �޾ƿ���
		String access_Token = service.getAccessToken(code);
		//Token������ ����� ���� ��������
		HashMap<String, Object> userInfo = service.getUserInfo(access_Token);
		String nickname = (String)userInfo.get("nickname");
		String email = (String)userInfo.get("email");
		
		//����� ���� �� �̸���, �̸����� ����Ͽ� ȸ������ Ȯ�� �� �α��� 
		Lservice ls = sqlSession.getMapper(Lservice.class);
		MemberDTO mdto = ls.kakaologin(nickname,email);
		if(mdto!=null){
		HttpSession hs = request.getSession();
		hs.setAttribute("member",mdto);
		hs.setAttribute("loginstate",true);
		hs.setMaxInactiveInterval(60*30);
		return "redirect:main";
		}
		else{
		return "kakaoLogin";
		}
	}
	
	@RequestMapping(value = "/naverlogin")
	public String naverlogin(@RequestParam(value = "code", required = false) String code,HttpServletRequest request) throws Throwable {
		NaverLoginService service = new NaverLoginService();
		System.out.println("code##:"+code);
		String access_Token = service.getAccessToken(code);
		HashMap<String, Object> userInfo = service.getUserInfo(access_Token);
		String name = (String)userInfo.get("name");
		String email = (String)userInfo.get("email");
		
		//����� ���� �� �̸���, �̸����� ����Ͽ� ȸ������ Ȯ�� �� �α��� 
		Lservice ls = sqlSession.getMapper(Lservice.class);
		MemberDTO mdto = ls.naverlogin(name,email);
		if(mdto!=null){
		HttpSession hs = request.getSession();
		hs.setAttribute("member",mdto);
		hs.setAttribute("loginstate",true);
		hs.setMaxInactiveInterval(60*30);
		}
		return "redirect:main";
	}

	// �α׾ƿ�
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
		HttpSession hs =request.getSession();
		hs.removeAttribute("member");
		hs.setAttribute("loginstate",false);
		return "redirect:/";
	}
	
}
