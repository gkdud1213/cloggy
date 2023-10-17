package com.ezen.dog.member;


import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MembershipScheduler {

	@Autowired
	SqlSession sqlSession;
	
	
	//�ſ� 1�ϸ��� ������Ʈ!
	@Scheduled(cron = "0 0 0 1 * ?")
	public void updateMembership() {
		updateMembershipByPurchaseAmount();
		System.out.println("=========������Ʈ �Ϸ�!!!!=========");
	}
	
	public void updateMembershipByPurchaseAmount() {
		 
		 Mservice ms =sqlSession.getMapper(Mservice.class);
		 ms.updateMembershipByPurchaseAmount();
		 	
	}
	
	
}
