package com.ezen.dog.cart;

import java.util.ArrayList;


public interface Cservice {
	//��ٱ��� �߰�(�α���)
	public void addcart(String userId, int product_id, int quantity);
	//��ٱ��� �߰�(��Ű)
	public void addcartwithcookie(String ckId, int product_id, int quantity);
	//��ٱ��� ���(�α���)
	public ArrayList<CartProductDTO> cartout(String userId);
	//��ٱ��� ���(��Ű)
	public ArrayList<CartProductDTO> cartoutwithcookie(String userId);
	//��ٱ��� ����(�α���)
	public void cartdelete(String userId, int product_id);
	//��ٱ��� ����(��Ű)
	public void cartdeletewithcookie(String ckvalue, int product_id);
	//��ٱ��� �߰� ���� Ȯ��(�α���)
	public int checkcart(String userId, int product_id);
	//��ٱ��� �߰� ���� Ȯ��(��Ű)
	public int checkcartwithcookie(String ckid, int product_id);
	//�̹� �ִ� ��ǰ ���� ����(�α���)
	public void increasequantity(String userId, int product_id, int quantity);
	//�̹� �ִ� ��ǰ ���� ����(��Ű)
	public void increasequantitywithcookie(String ckid, int product_id, int quantity);
	//��ٱ��� ���� ����(�α���)
	public void changeqty(String userId, int product_id, int quantity);
	//��ٱ��� ���� ����(��Ű)
	public void changeqtyforcookie(String ckvalue, int product_id, int quantity);
}
