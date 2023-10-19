package com.ezen.dog.cart;

import java.util.ArrayList;


public interface Cservice {
	//��ٱ��� �߰�(�α���)
	public void addcart(String userId, int product_id, int quantity, int optId);
	//��ٱ��� �߰�(��Ű)
	public void addcartwithcookie(String ckId, int product_id, int quantity, int optId);
	//��ٱ��� ���(�α���)
	public ArrayList<CartProductDTO> cartout(String userId);
	//��ٱ��� ���(��Ű)
	public ArrayList<CartProductDTO> cartoutwithcookie(String userId);
	//��ٱ��� ���û���(�α���)
	public void cartdelete(String userId, int product_id, int opt_id);
	//��ٱ��� ���û���(��Ű)
	public void cartdeletewithcookie(String ckvalue, int product_id, int opt_id);
	//��ٱ��� ��ü����(�α���)
	public void deleteall(String userId);
	//��ٱ��� ��ü����(��Ű)
	public void deleteallwithcookie(String ckvalue);
	//��ٱ��� �߰� ���� Ȯ��(�α���)
	public int checkcart(String userId, int product_id, int optId);
	//��ٱ��� �߰� ���� Ȯ��(��Ű)
	public int checkcartwithcookie(String ckid, int product_id, int optId);
	//�̹� �ִ� ��ǰ ���� ����(�α���)
	public void increasequantity(String userId, int product_id, int quantity, int optId);
	//�̹� �ִ� ��ǰ ���� ����(��Ű)
	public void increasequantitywithcookie(String ckid, int product_id, int quantity, int optId);
	//��ٱ��� ���� ����(�α���)
	public void changeqty(String userId, int product_id, int quantity);
	//��ٱ��� ���� ����(��Ű)
	public void changeqtyforcookie(String ckvalue, int product_id, int quantity);
}
