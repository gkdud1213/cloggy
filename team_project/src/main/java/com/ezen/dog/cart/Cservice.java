package com.ezen.dog.cart;

import java.util.ArrayList;

public interface Cservice {
	//��ٱ��� �߰�
	public void addcart(String userId, int product_id, int quantity);
	//��ٱ��� ��ȸ
	public CartDTO cartout(String userId);
	public ArrayList<CartDTO> cartout2(int product_id);
}
