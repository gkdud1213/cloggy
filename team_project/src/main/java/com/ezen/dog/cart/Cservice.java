package com.ezen.dog.cart;

import java.util.ArrayList;


public interface Cservice {
	//��ٱ��� �߰�

	public void addcart(String userId, int product_id, int quantity);
	public ArrayList<CartProductDTO> cartout(String userId);
	public void cartdelete(String userId, String productIds);
	public int checkcart(String userId, int product_id);
	public void increasequantity(String userId, int product_id, int quantity);
}
