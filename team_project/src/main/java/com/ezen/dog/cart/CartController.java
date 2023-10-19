package com.ezen.dog.cart;

import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.dog.member.MemberDTO;
import com.ezen.dog.product.OptionDTO;

@Controller
public class CartController {

	@Autowired
	SqlSession sqlSession;

	@ResponseBody
	@RequestMapping(value = "/addtocart", method = RequestMethod.POST)
	public String addToCart(@RequestParam("product_id") int product_id, @RequestParam("quantity") int quantity,
			@RequestParam("optId") int optId, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		// ?��?��?���? �??��?���?
		MemberDTO mdto = (MemberDTO) session.getAttribute("member");
		String userId = null;
		Cservice cs = sqlSession.getMapper(Cservice.class);

		// 로그?�� ?��?��
		if (mdto != null) { 
			userId = mdto.getUserId();

			// 카트?�� ?���? ???��?�� ?��?��?���? 체크?��?�� 메서?��!
			int hasitem = cs.checkcart(userId, product_id, optId);
			System.out.println("?��바구?�� ?��?�� :" + hasitem);

			if (hasitem > 0) { // ?��바구?��?�� ?��?�� ?��?��?�� 경우 ?��?�� 증�? method ?���?
				cs.increasequantity(userId, product_id, quantity, optId);
				return "success"; // ?��?�� ?��?�� 증�? ?���?
			} else { // ?��?�� ?��?��?�� 경우 ?���? 추�?
				cs.addcart(userId, product_id, quantity, optId);
				return "success"; // ?��?�� 추�? ?���?
			}

		} else { // 비로그인 ?��?�� 
			//쿠키 ?���? 조회
			Cookie[] cookies = request.getCookies();
			String ckid = null;
			boolean foundCkid = false;

			//쿠키 �??��~!
			for (Cookie cookie : cookies) {
				  if(cookie.getName().equals("ckid")){
				   ckid = cookie.getValue();
				   foundCkid = true;
				   break; // ?���? 쿠키�? 찾았?���?�? 루프 종료	
				  }
			}
			
			//ckid�? ?���? ?��?�� 경우~
			if(foundCkid){
				// 카트?�� ?���? ???��?�� ?��?��?���? 체크?��?�� 메서?��!
				int hasitem = cs.checkcartwithcookie(ckid, product_id, optId);
				System.out.println("?��바구?�� ?��?�� :" + hasitem);
					
				if (hasitem > 0) { // ?��바구?��?�� ?��?�� ?��?��?�� 경우 ?��?�� 증�? method ?���?
					cs.increasequantitywithcookie(ckid, product_id, quantity, optId);
					return "success"; // ?��?�� ?��?�� 증�? ?���?
				} else { // ?��?�� ?��?��?�� 경우 ?���? 추�?
					cs.addcartwithcookie(ckid, product_id, quantity, optId);
					return "success"; // ?��?�� 추�? ?���?
				}
			   }//ckid ?��?�� ?��(비로그인 ?��?�� 최초 ???��) : 쿠키 ?���? ?��?��
				
			else {  
			    // 쿠키�? ?��?�� 경우, ?��로운 쿠키 ?��?��?�� ?��?��
			    ckid = generateRandomString(); // 쿠키 ?��?��?�� ?��?�� 메서?���? 구현?��?�� ?��?��?��.
			    // ?��?�� 문자?���? ckid ?��?��
			    Cookie cookie = new Cookie("ckid", ckid);
			    cookie.setMaxAge(365 * 24 * 60 * 60); // 쿠키 ?��?��기간 ?��?�� (?��: 1?��)
			    cookie.setPath("/"); // 쿠키?�� 경로 ?��?��
			    // ?���? -> ?��?��?��?��?��(브라?��??)�? 쿠키 ?��?��, ???��
			    response.addCookie(cookie);
			    // ckid 값을 ?��?��?��?�� DB?�� ???��
			    cs.addcartwithcookie(ckid, product_id, quantity, optId);
			    System.out.println("쿠키 �?:" + ckid);			    

			    return "success";
			}
		}
	}

	private String generateRandomString() {
		// ?��?��?�� 방식?���? 쿠키 ?��?��?�� ?��?�� (?��: ?��?�� 문자?��, ?���? ?���? ?��?�� ?��?��)
//		String ckid = RandomStringUtils.random(6, true, true);
		String ckid = "hahaha";

		return ckid;
	}

	@RequestMapping(value = "/cart-out")
	public String productout(HttpSession session, HttpServletRequest request, Model mo) {
		
		MemberDTO mdto = (MemberDTO) session.getAttribute("member");
		Cservice cs = sqlSession.getMapper(Cservice.class);
		if (mdto != null) {
			// Session?�� ???��?��?��?��?�� ?��?��?�� ID �??��?���?
			String userId = mdto.getUserId();

			// ?��?��DB?�� ?��근해?�� product_id�? ?��?�� ?���? �??��?���?
			ArrayList<CartProductDTO> list = cs.cartout(userId);

			mo.addAttribute("list", list);
			

			return "cart-out";
		} else {// 로그?�� 값이 ?��?�� 경우 (쿠키�? ???��?�� 경우)
			Cookie[] cookies = request.getCookies();
			String ckvalue = null;
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("ckid")) {
					ckvalue = cookie.getValue();
					System.out.println("쿠키 �?:" + ckvalue);
					ArrayList<CartProductDTO> list = cs.cartoutwithcookie(ckvalue);
					mo.addAttribute("list", list);
					break;
				}
			}
		}
		return "cart-out";
	}

	@RequestMapping(value = "/deletefromcart")
	public String deletefromcart(HttpServletRequest request, HttpSession session) {
		// ?��머�? 코드?�� 그�?�? ?���?
		MemberDTO mdto = (MemberDTO) session.getAttribute("member");
		Cservice cs = sqlSession.getMapper(Cservice.class);
		String productIds = request.getParameter("productIds");
		String optIds = request.getParameter("optIds");
		System.out.println("!!!!?��?��번호!!!!" + productIds);// ?��?��?��
		String[] ProductIdss = productIds.split(",");
		String[] optIdss = optIds.split(",");

		if (mdto != null) { // 로그?�� ?��?��?�� 경우
			String userId = mdto.getUserId();

			for (int i = 0; i < ProductIdss.length; i++) {
				int product_id = Integer.parseInt(ProductIdss[i]);
				int opt_id = Integer.parseInt(optIdss[i]);
				cs.cartdelete(userId, product_id, opt_id);
			}
			return "redirect:/cart-out";
		} else {
			Cookie[] cookies = request.getCookies();
			String ckvalue = null;
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("ckid")) {
					ckvalue = cookie.getValue();
					break; // ?���? 쿠키�? 찾았?���?�? 루프 종료
				}
			}
			for (int i = 0; i < ProductIdss.length; i++) {
				int product_id = Integer.parseInt(ProductIdss[i]);
				int opt_id = Integer.parseInt(optIdss[i]);
				cs.cartdeletewithcookie(ckvalue, product_id, opt_id);
			}
			return "redirect:/cart-out";
		}

	}

	@RequestMapping(value = "/delete-all")
	public String deleteall(HttpServletRequest request, HttpSession session) {
		MemberDTO mdto = (MemberDTO) session.getAttribute("member");
		Cservice cs = sqlSession.getMapper(Cservice.class);
		
		if (mdto != null) { 
			String userId =	mdto.getUserId();
			cs.deleteall(userId);
		
		}else {
		Cookie[] cookies = request.getCookies();
		String ckvalue = null;
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("ckid")) {
					ckvalue = cookie.getValue();
					break; // ?���? 쿠키�? 찾았?���?�? 루프 종료
				}
			}
		cs.deleteall(ckvalue);
		}
		
		return "redirect:/cart-out";
	}
		
	@ResponseBody
	@RequestMapping(value = "/changeqty", method = RequestMethod.POST)
	public String changeqty(@RequestParam("product_id") int product_id, @RequestParam("quantity") int quantity,
			HttpSession session, HttpServletRequest request) {
		// ?��?��?���? �??��?���?
		MemberDTO mdto = (MemberDTO) session.getAttribute("member");

		Cservice cs = sqlSession.getMapper(Cservice.class);

		if (mdto != null) { // 로그?�� ?��?��?�� 경우
			String userId = mdto.getUserId();
			cs.changeqty(userId, product_id, quantity);
			return "success"; // ?��?�� ?��?�� 증�? ?���?

		} else { // 비로그인 ?��?��?�� 경우 : 쿠키�? ???��?�� ?��?��
			Cookie[] cookies = request.getCookies();
			String ckvalue = null;
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("ckid")) {
					ckvalue = cookie.getValue();
					break; // ?���? 쿠키�? 찾았?���?�? 루프 종료
				}
			}
			cs.changeqtyforcookie(ckvalue, product_id, quantity);
			return "success";
		}
	}
}
