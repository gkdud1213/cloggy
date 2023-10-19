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
		
		// ??? λ³? κ°?? Έ?€κΈ?
		MemberDTO mdto = (MemberDTO) session.getAttribute("member");
		String userId = null;
		Cservice cs = sqlSession.getMapper(Cservice.class);

		// λ‘κ·Έ?Έ ??
		if (mdto != null) { 
			userId = mdto.getUserId();

			// μΉ΄νΈ? ?΄λ―? ???₯? ???Έμ§? μ²΄ν¬?? λ©μ?!
			int hasitem = cs.checkcart(userId, product_id, optId);
			System.out.println("?₯λ°κ΅¬? ?? :" + hasitem);

			if (hasitem > 0) { // ?₯λ°κ΅¬?? ?? ???Ό κ²½μ° ?? μ¦κ? method ?ΈμΆ?
				cs.increasequantity(userId, product_id, quantity, optId);
				return "success"; // ?? ?? μ¦κ? ?±κ³?
			} else { // ?? ???Ό κ²½μ° ? κ·? μΆκ?
				cs.addcart(userId, product_id, quantity, optId);
				return "success"; // ?? μΆκ? ?±κ³?
			}

		} else { // λΉλ‘κ·ΈμΈ ?? 
			//μΏ ν€ ? λ¬? μ‘°ν
			Cookie[] cookies = request.getCookies();
			String ckid = null;
			boolean foundCkid = false;

			//μΏ ν€ κ²??¬~!
			for (Cookie cookie : cookies) {
				  if(cookie.getName().equals("ckid")){
				   ckid = cookie.getValue();
				   foundCkid = true;
				   break; // ?΄λ―? μΏ ν€λ₯? μ°Ύμ?Όλ―?λ‘? λ£¨ν μ’λ£	
				  }
			}
			
			//ckidκ°? ?΄λ―? ?? κ²½μ°~
			if(foundCkid){
				// μΉ΄νΈ? ?΄λ―? ???₯? ???Έμ§? μ²΄ν¬?? λ©μ?!
				int hasitem = cs.checkcartwithcookie(ckid, product_id, optId);
				System.out.println("?₯λ°κ΅¬? ?? :" + hasitem);
					
				if (hasitem > 0) { // ?₯λ°κ΅¬?? ?? ???Ό κ²½μ° ?? μ¦κ? method ?ΈμΆ?
					cs.increasequantitywithcookie(ckid, product_id, quantity, optId);
					return "success"; // ?? ?? μ¦κ? ?±κ³?
				} else { // ?? ???Ό κ²½μ° ? κ·? μΆκ?
					cs.addcartwithcookie(ckid, product_id, quantity, optId);
					return "success"; // ?? μΆκ? ?±κ³?
				}
			   }//ckid ?? ?(λΉλ‘κ·ΈμΈ ?? μ΅μ΄ ???₯) : μΏ ν€ ? κ·? ??±
				
			else {  
			    // μΏ ν€κ°? ?? κ²½μ°, ?λ‘μ΄ μΏ ν€ ??΄? ??±
			    ckid = generateRandomString(); // μΏ ν€ ??΄? ??± λ©μ?λ₯? κ΅¬ν?΄?Ό ?©??€.
			    // ??€ λ¬Έμ?΄λ‘? ckid ??±
			    Cookie cookie = new Cookie("ckid", ckid);
			    cookie.setMaxAge(365 * 24 * 60 * 60); // μΏ ν€ ? ?¨κΈ°κ° ?€?  (?: 1?)
			    cookie.setPath("/"); // μΏ ν€? κ²½λ‘ ?€? 
			    // ?λ²? -> ?΄?Ό?΄?Έ?Έ(λΈλΌ?°??)λ‘? μΏ ν€ ? ?‘, ???₯
			    response.addCookie(cookie);
			    // ckid κ°μ ?¬?©??¬ DB? ???₯
			    cs.addcartwithcookie(ckid, product_id, quantity, optId);
			    System.out.println("μΏ ν€ κ°?:" + ckid);			    

			    return "success";
			}
		}
	}

	private String generateRandomString() {
		// ??? λ°©μ?Όλ‘? μΏ ν€ ??΄? ??± (?: ??€ λ¬Έμ?΄, ?κ°? ? λ³? ?±? ??©)
//		String ckid = RandomStringUtils.random(6, true, true);
		String ckid = "hahaha";

		return ckid;
	}

	@RequestMapping(value = "/cart-out")
	public String productout(HttpSession session, HttpServletRequest request, Model mo) {
		
		MemberDTO mdto = (MemberDTO) session.getAttribute("member");
		Cservice cs = sqlSession.getMapper(Cservice.class);
		if (mdto != null) {
			// Session? ???₯??΄?? ?¬?©? ID κ°?? Έ?€κΈ?
			String userId = mdto.getUserId();

			// ? ?DB? ? κ·Όν΄? product_idλ‘? ?? ? λ³? κ°?? Έ?€κΈ?
			ArrayList<CartProductDTO> list = cs.cartout(userId);

			mo.addAttribute("list", list);
			

			return "cart-out";
		} else {// λ‘κ·Έ?Έ κ°μ΄ ?? κ²½μ° (μΏ ν€λ‘? ???₯? κ²½μ°)
			Cookie[] cookies = request.getCookies();
			String ckvalue = null;
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("ckid")) {
					ckvalue = cookie.getValue();
					System.out.println("μΏ ν€ κ°?:" + ckvalue);
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
		// ?λ¨Έμ? μ½λ? κ·Έλ?λ‘? ? μ§?
		MemberDTO mdto = (MemberDTO) session.getAttribute("member");
		Cservice cs = sqlSession.getMapper(Cservice.class);
		String productIds = request.getParameter("productIds");
		String optIds = request.getParameter("optIds");
		System.out.println("!!!!? ?λ²νΈ!!!!" + productIds);// ??Έ?©
		String[] ProductIdss = productIds.split(",");
		String[] optIdss = optIds.split(",");

		if (mdto != null) { // λ‘κ·Έ?Έ ???Ό κ²½μ°
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
					break; // ?΄λ―? μΏ ν€λ₯? μ°Ύμ?Όλ―?λ‘? λ£¨ν μ’λ£
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
					break; // ?΄λ―? μΏ ν€λ₯? μ°Ύμ?Όλ―?λ‘? λ£¨ν μ’λ£
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
		// ??? λ³? κ°?? Έ?€κΈ?
		MemberDTO mdto = (MemberDTO) session.getAttribute("member");

		Cservice cs = sqlSession.getMapper(Cservice.class);

		if (mdto != null) { // λ‘κ·Έ?Έ ???Ό κ²½μ°
			String userId = mdto.getUserId();
			cs.changeqty(userId, product_id, quantity);
			return "success"; // ?? ?? μ¦κ? ?±κ³?

		} else { // λΉλ‘κ·ΈμΈ ???Ό κ²½μ° : μΏ ν€λ‘? ???₯? ??
			Cookie[] cookies = request.getCookies();
			String ckvalue = null;
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("ckid")) {
					ckvalue = cookie.getValue();
					break; // ?΄λ―? μΏ ν€λ₯? μ°Ύμ?Όλ―?λ‘? λ£¨ν μ’λ£
				}
			}
			cs.changeqtyforcookie(ckvalue, product_id, quantity);
			return "success";
		}
	}
}
