package com.ezen.dog.product;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class ProductController {

	@Autowired
	SqlSession sqlSession;
	String image_path = "C:\\6���Ư\\spring\\project\\src\\main\\webapp\\image";
	ArrayList<ProductDTO>list = new ArrayList<ProductDTO>();
	
	//��ǰ�Է�
	@RequestMapping(value = "/product-input")
	public String productinput() {
		return "product-input";
	}
	
	//��ǰ DB����
	@RequestMapping(value = "/product-save", method = RequestMethod.POST)
	public String product2(MultipartHttpServletRequest multi) throws IllegalStateException, IOException {
		int category_id = Integer.parseInt(multi.getParameter("category_id"));
		String p_name = multi.getParameter("p_name");
		int p_price = Integer.parseInt(multi.getParameter("p_price"));
		String p_info = multi.getParameter("p_info");
		MultipartFile mf_image = multi.getFile("p_image");
		String p_image = mf_image.getOriginalFilename(); 
		mf_image.transferTo(new File(image_path+"\\"+p_image));
		MultipartFile mf_thumnail = multi.getFile("p_thumbnail");
		String p_thumbnail = mf_thumnail.getOriginalFilename(); 
		mf_thumnail.transferTo(new File(image_path+"\\"+p_thumbnail));
		int p_stock = Integer.parseInt(multi.getParameter("p_stock"));
		PService ps = sqlSession.getMapper(PService.class);
		ps.productinput(category_id,p_name,p_price,p_info,p_image,p_thumbnail,p_stock);
		
		return "redirect:product-input";
	}

	//��ǰ����Ʈ
	@RequestMapping(value = "/product-out")
	public String productout(Model mo) {
		PService ps = sqlSession.getMapper(PService.class);
		list = ps.productout();
		mo.addAttribute("list", list);
		return "product-out";
	}
	
	//��ǰ ��������
	@RequestMapping(value = "/product-detail")
	public String productdetail(HttpServletRequest request, Model mo) {
		int product_id = Integer.parseInt(request.getParameter("product_id"));
		PService ps = sqlSession.getMapper(PService.class);
		list = ps.productdetail(product_id);
		ps.productcount(product_id);
		mo.addAttribute("list", list);
		return "product-detail";
	}
	
	//��ǰ �������� �Է�â
	@RequestMapping(value = "/product-modifyForm")
	public String productmodifyForm(HttpServletRequest request, Model mo) {
		int product_id = Integer.parseInt(request.getParameter("product_id"));
		PService ps = sqlSession.getMapper(PService.class);
		list = ps.productmodifyForm(product_id);
		mo.addAttribute("list", list);
		return "product-modifyForm";
	}

	//��ǰ ��������
	@RequestMapping(value = "/product-modifyView", method = RequestMethod.POST)
	public String productmodifyView(MultipartHttpServletRequest multi) throws IllegalStateException, IOException {
		int category_id = Integer.parseInt(multi.getParameter("category_id"));
		int product_id = Integer.parseInt(multi.getParameter("product_id"));
		String p_name = multi.getParameter("p_name");
		int p_price = Integer.parseInt(multi.getParameter("p_price"));
		String p_info = multi.getParameter("p_info");
		MultipartFile mf_image = multi.getFile("p_image");
		String p_image = mf_image.getOriginalFilename(); 
		mf_image.transferTo(new File(image_path+"\\"+p_image));
		MultipartFile mf_thumnail = multi.getFile("p_thumbnail");
		String p_thumbnail = mf_thumnail.getOriginalFilename(); 
		mf_thumnail.transferTo(new File(image_path+"\\"+p_thumbnail));
		int p_stock = Integer.parseInt(multi.getParameter("p_stock"));
		String p_enroll = multi.getParameter("p_enroll");

		PService ps = sqlSession.getMapper(PService.class);
		ps.productmodifyView(category_id,product_id, p_name,p_price,p_info,p_image,p_thumbnail,p_stock, p_enroll);
		
		return "redirect:product-out";
	}
	
	//��ǰ����
	@RequestMapping(value = "/product-delete")
	public String productdelete(HttpServletRequest request) {
		int product_id = Integer.parseInt(request.getParameter("product_id"));
		PService ps = sqlSession.getMapper(PService.class);
		ps.productdelete(product_id);
		
		return "redirect:product-out";
	}
}
