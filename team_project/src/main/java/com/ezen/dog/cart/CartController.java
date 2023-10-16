package com.ezen.dog.cart;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.dog.member.MemberDTO;


@Controller
public class CartController {

	@Autowired
	SqlSession sqlSession;

	
//	//īƮ �߰� : �񵿱�(view�� ��ȯ ���� ���� â����)
//	@ResponseBody
//	@RequestMapping(value = "/addcart", method = RequestMethod.POST)
//	public void addcart(HttpSession session, HttpServletRequest request, HttpServletResponse response, CartDTO cdto){
//		int quantity = Integer.parseInt(request.getParameter("quantity"));
//		
//		MemberDTO mdto = (MemberDTO)session.getAttribute("member");
//		cdto.setUserId(mdto.getUserId());
//		cdto.setCart_quantity(quantity);
//		
//		Cservice cv = sqlSession.getMapper(Cservice.class);
//		cv.addcart(cdto);
//		
//	}
//	
//	@ResponseBody
//	@RequestMapping(value = "/addcart", method = RequestMethod.POST)
//	public void addcart(HttpSession session, @RequestBody Map<String, Object> requestData) {
//	    int quantity = Integer.parseInt(requestData.get("quantity").toString());
//	    int productId = Integer.parseInt(requestData.get("product_id").toString());
//
//	    MemberDTO mdto = (MemberDTO) session.getAttribute("member");
//	    CartDTO cdto = new CartDTO();
//	    cdto.setUserId(mdto.getUserId());
//	    cdto.setProduct_id(productId);
//	    cdto.setCart_quantity(quantity);
//
//	    Cservice cv = sqlSession.getMapper(Cservice.class);
//	    cv.addcart(cdto);
//	}

	
	@ResponseBody
	@RequestMapping(value = "/addcart", method = RequestMethod.POST)
	public String addcart(HttpSession session, @RequestParam("product_id") int productId, @RequestParam("quantity") int quantity) {
	    try {
	        MemberDTO mdto = (MemberDTO) session.getAttribute("member");

	        if (mdto == null) {
	            // Handle the case where the user is not logged in
	            return "User not logged in";
	        }

	        CartDTO cdto = new CartDTO();
	        cdto.setUserId(mdto.getUserId());
	        cdto.setProduct_id(productId);
	        cdto.setCart_quantity(quantity);

	        Cservice cv = sqlSession.getMapper(Cservice.class);

	        // Log values for debugging
	        System.out.println("Debug: mdto.getUserId() = " + mdto.getUserId());
	        System.out.println("Debug: productId = " + productId);
	        System.out.println("Debug: quantity = " + quantity);

	        // Attempt to add the item to the cart
	        cv.addcart(cdto);

	        // Optionally, you can return a success message
	        return "Item added to cart successfully";
	    } catch (Exception e) {
	        // Log the exception and stack trace
	        e.printStackTrace();
	        return "Error adding item to cart: " + e.getMessage();
	    }
	}


	
	//��ٱ��� ���1 : userId�� cart id ��ȸ(���� ��� ���ϴ� �ӽù���) -> cartid�� �ִ� ��ǰid�� ��ǰ �󼼳��� ��ȸ(���� ����)
	@RequestMapping(value = "/cart-out")
	public String productout(HttpSession session,HttpServletRequest request, Model mo) {
		MemberDTO mdto = (MemberDTO) session.getAttribute("member");
		
		mo.addAttribute("mdto", mdto);
		
		String userId = mdto.getUserId();
		
		Cservice cs = sqlSession.getMapper(Cservice.class);
		ArrayList<CartDTO> list = cs.cartout(userId);
		mo.addAttribute("list", list);
		
		return "cart-out";
	}
}
