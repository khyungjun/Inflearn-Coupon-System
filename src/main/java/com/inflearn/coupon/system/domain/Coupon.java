package com.inflearn.coupon.system.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
public class Coupon {

	@Getter
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long userId;

	// 1인당 발급 가능한 쿠폰 개수를 1개로 제한하는 방법에는 쿠폰 테이블에 userId와 couponType이라는 컬럼을 추가한 뒤에 userId와 couponType에 유니크키를 걸어서 1개만 생성되도록 데이터베이스 레벨에서 막는 방법이 있다.
	// 하지만 보통 서비스는 한 유저가 같은 타입의 쿠폰을 여러개 가질 수 있기 때문에 실용적인 방법이 아니다.
	// private Long couponType; 
	
	public Coupon() {
	}

	public Coupon(Long userId) {
		this.userId = userId;
	}
}
