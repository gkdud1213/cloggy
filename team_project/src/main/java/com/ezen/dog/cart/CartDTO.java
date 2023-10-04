package com.ezen.dog.cart;

import java.sql.Date;

public class CartDTO {
	
	   private int cart_no; //��ٱ���PR
	   private String userId; //ȸ��PR
	   private int product_id; //��ǰPR
	   private Date cart_cklimit; //��Ű���ѽð�(������)
	   private String cart_ckid; //��Űvalue��
	   private String cart_option_content; //�ɼǳ���
	   private int cart_option_no; //�ɼ�PR
	   private int cart_quantity; //����
	   
		public CartDTO() {
			super();
		}



		

		public CartDTO(int cart_no, String userId, int product_id, Date cart_cklimit, String cart_ckid,
				String cart_option_content, int cart_option_no, int cart_quantity) {
			super();
			this.cart_no = cart_no;
			this.userId = userId;
			this.product_id = product_id;
			this.cart_cklimit = cart_cklimit;
			this.cart_ckid = cart_ckid;
			this.cart_option_content = cart_option_content;
			this.cart_option_no = cart_option_no;
			this.cart_quantity = cart_quantity;
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
		
	        
		
}
