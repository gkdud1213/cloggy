package com.ezen.dog.product;

import java.util.ArrayList;

public interface Pservice {
	//��ǰ ��ü����(��з���)
	public ArrayList<ProductDTO> productouttotal(int a);
	//��з�, �ߺз�
	public ArrayList<ProductDTO> productout(int a, int b);
	//��ǰ ��������
	public ArrayList<ProductDTO> productdetail(int a);
}
