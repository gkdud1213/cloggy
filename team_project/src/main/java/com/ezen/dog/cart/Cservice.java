package com.ezen.dog.cart;

import java.util.ArrayList;

public interface Cservice {
	//��ٱ��� �߰�
<<<<<<< HEAD
	public void addcart(String userId, int product_id, int quantity);
	//��ٱ��� ��ȸ
	public CartDTO cartout(String userId);
	public ArrayList<CartDTO> cartout2(int product_id);
	public void cartin_nocookie(CartDTO cdto);
	public void cartin_yescookie(CartDTO cdto);
	public void cartin_login(CartDTO cdto);
	public int cartCheck(CartDTO cdto);
=======
	public void addcart(CartDTO cdto);
	public ArrayList<CartDTO> cartout(String userId);
>>>>>>> parent of 934500c (cart completed)
}
