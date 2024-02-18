package com.inflearn.coupon.system.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.inflearn.coupon.system.domain.Coupon;
import com.inflearn.coupon.system.domain.FailedEvent;
import com.inflearn.coupon.system.repository.CouponRepository;
import com.inflearn.coupon.system.repository.FailedEventRepository;

@Component
public class CouponCreatedConsumer {

	private final CouponRepository couponRepository;
	
	// 추가
	private final FailedEventRepository failedEventRepository;
	
	// 추가
	private final Logger logger = LoggerFactory.getLogger(CouponCreatedConsumer.class);
	
	public CouponCreatedConsumer(CouponRepository couponRepository, FailedEventRepository failedEventRepository) {
		this.couponRepository = couponRepository;
		this.failedEventRepository = failedEventRepository;
	}

	@KafkaListener(topics = "coupon_create", groupId = "group_1")
	public void listener(Long userId) {
//		System.out.println(userId);
//		couponRepository.save(new Coupon(userId)); // 쿠폰 발급을 하는 로직 수정

		// 쿠폰 발급을 진행하다가 에러가 발생한다면 로거를 사용해서 로그를 남겨준다.
		try {
			couponRepository.save(new Coupon(userId));
		} catch (Exception e) {
			logger.error("failed to create coupon");
			failedEventRepository.save(new FailedEvent(userId)); // FailedEventRepository를 사용해서 실패한 유저의 아이디를 저장
		}
		
	}
}
