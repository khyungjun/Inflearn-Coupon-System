package com.inflearn.coupon.system.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CouponCreateProducer { // 카프카 템플릿을 사용해서 토픽의 데이터를 전송 할 프로듀서 생성

	private final KafkaTemplate<String, Long> kafkaTemplate;

	public CouponCreateProducer(KafkaTemplate<String, Long> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void create(Long userId) {
		kafkaTemplate.send("coupon_create", userId);
	}
}
