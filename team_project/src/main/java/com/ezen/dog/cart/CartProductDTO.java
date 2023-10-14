package com.ezen.dog.cart;

import java.sql.Date;

public class CartProductDTO {
	
	   private int cart_no; //��ٱ���PR
	   private String userId; //ȸ��PR
	   private int product_id; //��ǰPR
	   private Date cart_cklimit; //��Ű���ѽð�(������)
	   private String cart_ckid; //��Űvalue��
	   private String cart_option_content; //�ɼǳ���
	   private int cart_option_no; //�ɼ�PR
	   private int cart_quantity; //����
	   private int total_price; //�ѱ��űݾ�
	   private int finalprice; //�ѱ��űݾ�
	   
	   
	   int category_id;
		String p_name;
		int p_price;
		String p_info, p_image, p_thumbnail;
		int p_stock;
		String p_enroll;
		int p_sell, p_hits;
	   
	   
		public CartProductDTO() {
			super();
		}


	




		public CartProductDTO(int cart_no, String userId, int product_id, Date cart_cklimit, String cart_ckid,
				String cart_option_content, int cart_option_no, int cart_quantity, int category_id, String p_name,
				int p_price, String p_info, String p_image, String p_thumbnail, int p_stock, String p_enroll,
				int p_sell, int p_hits) {
			super();
			this.cart_no = cart_no;
			this.userId = userId;
			this.product_id = product_id;
			this.cart_cklimit = cart_cklimit;
			this.cart_ckid = cart_ckid;
			this.cart_option_content = cart_option_content;
			this.cart_option_no = cart_option_no;
			this.cart_quantity = cart_quantity;
			this.category_id = category_id;
			this.p_name = p_name;
			this.p_price = p_price;
			this.p_info = p_info;
			this.p_image = p_image;
			this.p_thumbnail = p_thumbnail;
			this.p_stock = p_stock;
			this.p_enroll = p_enroll;
			this.p_sell = p_sell;
			this.p_hits = p_hits;
		}







		public int getCart_no() {
			return cart_no;
		}

		public void setCart_no(int cart_no) {
			this.cart_no = cart_no;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		
		public Date getCart_cklimit() {
			return cart_cklimit;
		}

		public void setCart_cklimit(Date cart_cklimit) {
			this.cart_cklimit = cart_cklimit;
		}

		public String getCart_ckid() {
			return cart_ckid;
		}

		public void setCart_ckid(String cart_ckid) {
			this.cart_ckid = cart_ckid;
		}

		public String getCart_option_content() {
			return cart_option_content;
		}

		public void setCart_option_content(String cart_option_content) {
			this.cart_option_content = cart_option_content;
		}

		public int getCart_option_no() {
			return cart_option_no;
		}

		public void setCart_option_no(int cart_option_no) {
			this.cart_option_no = cart_option_no;
		}

		public int getCart_quantity() {
			return cart_quantity;
		}

		public void setCart_quantity(int cart_quantity) {
			this.cart_quantity = cart_quantity;
		}





		public int getProduct_id() {
			return product_id;
		}





		public void setProduct_id(int product_id) {
			this.product_id = product_id;
		}







		public int getCategory_id() {
			return category_id;
		}







		public void setCategory_id(int category_id) {
			this.category_id = category_id;
		}







		public String getP_name() {
			return p_name;
		}







		public void setP_name(String p_name) {
			this.p_name = p_name;
		}







		public int getP_price() {
			return p_price;
		}







		public void setP_price(int p_price) {
			this.p_price = p_price;
		}







		public String getP_info() {
			return p_info;
		}







		public void setP_info(String p_info) {
			this.p_info = p_info;
		}







		public String getP_image() {
			return p_image;
		}







		public void setP_image(String p_image) {
			this.p_image = p_image;
		}







		public String getP_thumbnail() {
			return p_thumbnail;
		}







		public void setP_thumbnail(String p_thumbnail) {
			this.p_thumbnail = p_thumbnail;
		}







		public int getP_stock() {
			return p_stock;
		}







		public void setP_stock(int p_stock) {
			this.p_stock = p_stock;
		}







		public String getP_enroll() {
			return p_enroll;
		}







		public int getTotal_price() {
			return total_price;
		}







		public void setTotal_price(int total_price) {
			this.total_price = total_price;
		}







		public void setP_enroll(String p_enroll) {
			this.p_enroll = p_enroll;
		}







		public int getP_sell() {
			return p_sell;
		}







		public void setP_sell(int p_sell) {
			this.p_sell = p_sell;
		}







		public int getP_hits() {
			return p_hits;
		}







		public void setP_hits(int p_hits) {
			this.p_hits = p_hits;
		}







		public int getFinalprice() {
			return finalprice;
		}







		public void setFinalprice(int finalprice) {
			this.finalprice = finalprice;
		}
		
	    
		
}
