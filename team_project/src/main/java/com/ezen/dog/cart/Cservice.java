package com.ezen.dog.cart;

import java.util.ArrayList;

public interface Cservice {
	//��ٱ��� �߰�

	public void addcart(CartDTO cdto);
	public CartDTO cartout(String userId);
	public void addcart(String userId, int product_id, int quantity);
	
}
