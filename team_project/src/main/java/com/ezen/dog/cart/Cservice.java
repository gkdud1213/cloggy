package com.ezen.dog.cart;

import java.util.ArrayList;

public interface Cservice {
	//��ٱ��� �߰�
	public void addcart(CartDTO cdto);
	public ArrayList<CartDTO> cartout(String userId);
}
