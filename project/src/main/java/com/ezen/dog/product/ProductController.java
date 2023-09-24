package com.ezen.dog.product;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProductController {
	
	@Autowired
	SqlSession sqlsession;
	
	String imgPath = "C:\\Users\\dywlr\\OneDrive\\����\\īī���� ���� ����\\3��\\project\\src\\main\\webapp\\image";
	
	
	//��ü����
	@RequestMapping(value = "/product-out-total")
	public String productouttotal(HttpServletRequest request, Model mo) {
		int a = Integer.parseInt(request.getParameter("category1_id"));

		Pservice ps = sqlsession.getMapper(Pservice.class);
		ArrayList<ProductDTO> list = ps.productouttotal(a);
		mo.addAttribute("list", list);
		
		return "product-out";
	}
	
	
	
	//��з�, �ߺз� ī�װ� ���͸�
	@RequestMapping(value = "/product-out")
	public String productout(HttpServletRequest request, Model mo) {
		int a = Integer.parseInt(request.getParameter("category1_id"));
		int b = Integer.parseInt(request.getParameter("category2_id"));

		Pservice ps = sqlsession.getMapper(Pservice.class);
		ArrayList<ProductDTO> list = ps.productout(a, b);
		mo.addAttribute("list", list);
		
		return "product-out";
	}
	
	@RequestMapping(value = "/product-detail")
	public String productdetail(HttpServletRequest request, Model mo) {
		int product_id = Integer.parseInt(request.getParameter("product_id"));
		
		Pservice ps = sqlsession.getMapper(Pservice.class);
		ProductDTO dto = ps.productdetail(product_id);
		mo.addAttribute("dto", dto);
		
		return "product-detail";
	}

	
}
