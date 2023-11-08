package com.ezen.dog.cart;

import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
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
		
		// ȸ������ ��������
		MemberDTO mdto = (MemberDTO) session.getAttribute("member");
		String userId = null;
		Cservice cs = sqlSession.getMapper(Cservice.class);

		// �α��� ����
		if (mdto != null) { 
			userId = mdto.getUserId();

			// īƮ�� �̹� ����� ��ǰ���� üũ�ϴ� �޼���!
			int hasitem = cs.checkcart(userId, product_id, optId);
			System.out.println("��ٱ��� ���� :" + hasitem);

			if (hasitem > 0) { // ��ٱ��Ͽ� �ִ� ��ǰ�� ��� ���� ���� method ȣ��
				cs.increasequantity(userId, product_id, quantity, optId);
				return "success"; // ��ǰ ���� ���� ����
			} else { // ���� ��ǰ�� ��� �ű� �߰�
				cs.addcart(userId, product_id, quantity, optId);
				return "success"; // ��ǰ �߰� ����
			}

		} else { // ��α��� ���� 
			//��Ű ���� ��ȸ
			Cookie[] cookies = request.getCookies();
			String ckid = null;
			boolean foundCkid = false;

			//��Ű �˻�~!
			for (Cookie cookie : cookies) {
				  if(cookie.getName().equals("ckid")){
				   ckid = cookie.getValue();
				   foundCkid = true;
				   break; // �̹� ��Ű�� ã�����Ƿ� ���� ����	
				  }
			}
			
			//ckid�� �̹� �ִ� ���~
			if(foundCkid){
				// īƮ�� �̹� ����� ��ǰ���� üũ�ϴ� �޼���!
				int hasitem = cs.checkcartwithcookie(ckid, product_id, optId);
				System.out.println("��ٱ��� ���� :" + hasitem);
					
				if (hasitem > 0) { // ��ٱ��Ͽ� �ִ� ��ǰ�� ��� ���� ���� method ȣ��
					cs.increasequantitywithcookie(ckid, product_id, quantity, optId);
					return "success"; // ��ǰ ���� ���� ����
				} else { // ���� ��ǰ�� ��� �ű� �߰�
					cs.addcartwithcookie(ckid, product_id, quantity, optId);
					return "success"; // ��ǰ �߰� ����
				}
			   }//ckid ���� ��(��α��� ���� ���� ����) : ��Ű �ű� ����
				
			else {  
			    // ��Ű�� ���� ���, ���ο� ��Ű ���̵� ����
			    ckid = generateRandomString(); // ��Ű ���̵� ���� �޼��带 �����ؾ� �մϴ�.
			    // ���� ���ڿ��� ckid ����
			    Cookie cookie = new Cookie("ckid", ckid);
			    cookie.setMaxAge(365 * 24 * 60 * 60); // ��Ű ��ȿ�Ⱓ ���� (��: 1��)
			    cookie.setPath("/"); // ��Ű�� ��� ����
			    // ���� -> Ŭ���̾�Ʈ(������)�� ��Ű ����, ����
			    response.addCookie(cookie);
			    // ckid ���� ����Ͽ� DB�� ����
			    cs.addcartwithcookie(ckid, product_id, quantity, optId);
			    System.out.println("��Ű ��:" + ckid);			    

			    return "success";
			}
		}
	}

	private String generateRandomString() {
		// ���ϴ� ������� ��Ű ���̵� ���� (��: ���� ���ڿ�, �ð� ���� ���� Ȱ��)
		String ckid = RandomStringUtils.random(6, true, true);

		return ckid;
	}

	@RequestMapping(value = "/cart-out")
	public String productout(HttpSession session, HttpServletRequest request, Model mo) {
		
		MemberDTO mdto = (MemberDTO) session.getAttribute("member");
		Cservice cs = sqlSession.getMapper(Cservice.class);
		if (mdto != null) {
			// Session�� ����Ǿ��ִ� ����� ID ��������
			String userId = mdto.getUserId();

			// ��ǰDB�� �����ؼ� product_id�� ��ǰ ���� ��������
			ArrayList<CartProductDTO> list = cs.cartout(userId);

			mo.addAttribute("list", list);
			

			return "cart-out";
		} else {// �α��� ���� ���� ��� (��Ű�� ������ ���)
			Cookie[] cookies = request.getCookies();
			String ckvalue = null;
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("ckid")) {
					ckvalue = cookie.getValue();
					System.out.println("��Ű ��:" + ckvalue);
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
		// ������ �ڵ�� �״�� ����
		MemberDTO mdto = (MemberDTO) session.getAttribute("member");
		Cservice cs = sqlSession.getMapper(Cservice.class);
		String productIds = request.getParameter("productIds");
		String optIds = request.getParameter("optIds");
		System.out.println("!!!!��ǰ��ȣ!!!!" + productIds);// Ȯ�ο�
		String[] ProductIdss = productIds.split(",");
		String[] optIdss = optIds.split(",");

		if (mdto != null) { // �α��� ������ ���
			String userId = mdto.getUserId();

			for (int i = 0; i < ProductIdss.length; i++) {
				int product_id = Integer.parseInt(ProductIdss[i]);
				int opt_id = Integer.parseInt(optIdss[i]);
				cs.cartdelete(userId, product_id, opt_id);
			}
			return "redirect:/cart-out";
		} else { //��α��� ����
			Cookie[] cookies = request.getCookies();
			String ckvalue = null;
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("ckid")) {
					ckvalue = cookie.getValue();
					break; // �̹� ��Ű�� ã�����Ƿ� ���� ����
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
					break; // �̹� ��Ű�� ã�����Ƿ� ���� ����
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
		// ȸ������ ��������
		MemberDTO mdto = (MemberDTO) session.getAttribute("member");

		Cservice cs = sqlSession.getMapper(Cservice.class);

		if (mdto != null) { // �α��� ������ ���
			String userId = mdto.getUserId();
			cs.changeqty(userId, product_id, quantity);
			return "success"; // ��ǰ ���� ���� ����

		} else { // ��α��� ������ ��� : ��Ű�� ����� ����
			Cookie[] cookies = request.getCookies();
			String ckvalue = null;
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("ckid")) {
					ckvalue = cookie.getValue();
					break; // �̹� ��Ű�� ã�����Ƿ� ���� ����
				}
			}
			cs.changeqtyforcookie(ckvalue, product_id, quantity);
			return "success";
		}
	}
}