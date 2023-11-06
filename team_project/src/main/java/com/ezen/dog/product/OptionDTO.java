package com.ezen.dog.product;

public class OptionDTO {
	//�ɼ� ID
	int opt_id;
	//��ǰ�� : ����Ű
	int product_id;
	//�ɼǸ�
	String opt_name;
	//�ɼ� ����
	int opt_price;
	//�ɼǺ� ���
	int opt_stock;
	
	
	
	public OptionDTO() {
		super();
	}



	public OptionDTO(int opt_id, int product_id, String opt_name, int opt_price, int opt_stock) {
		super();
		this.opt_id = opt_id;
		this.product_id = product_id;
		this.opt_name = opt_name;
		this.opt_price = opt_price;
		this.opt_stock = opt_stock;
	}



	public int getOpt_id() {
		return opt_id;
	}



	public void setOpt_id(int opt_id) {
		this.opt_id = opt_id;
	}



	public int getProduct_id() {
		return product_id;
	}



	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}



	public String getOpt_name() {
		return opt_name;
	}



	public void setOpt_name(String opt_name) {
		this.opt_name = opt_name;
	}



	public int getOpt_price() {
		return opt_price;
	}



	public void setOpt_price(int opt_price) {
		this.opt_price = opt_price;
	}



	public int getOpt_stock() {
		return opt_stock;
	}



	public void setOpt_stock(int opt_stock) {
		this.opt_stock = opt_stock;
	}
	
	
	
	
}
