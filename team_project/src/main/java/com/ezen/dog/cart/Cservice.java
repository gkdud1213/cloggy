package com.ezen.dog.cart;

import java.util.ArrayList;


public interface Cservice {
	//��ٱ��� �߰�

	public void addcart(String userId, int product_id, int quantity);
	public ArrayList<CartProductDTO> cartout(String userId);
	public void cartdelete(String userId, String productIds);
}
