package com.inflearn.coupon.system.service;

import org.springframework.stereotype.Service;

import com.inflearn.coupon.system.domain.Coupon;
import com.inflearn.coupon.system.producer.CouponCreateProducer;
import com.inflearn.coupon.system.repository.AppliedUserRepository;
import com.inflearn.coupon.system.repository.CouponCountRepository;
import com.inflearn.coupon.system.repository.CouponRepository;

@Service
public class ApplyService {

	private final CouponRepository couponRepository;

	// 추가
	private final CouponCountRepository couponCountRepository;
	
	// 추추가
	private final CouponCreateProducer couponCreateProducer;

	// 추추추가
	private final AppliedUserRepository appliedUserRepository; 
	
	public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository, CouponCreateProducer couponCreateProducer, AppliedUserRepository appliedUserRepository) {
		this.couponRepository = couponRepository;
		this.couponCountRepository = couponCountRepository;
		this.couponCreateProducer = couponCreateProducer;
		this.appliedUserRepository = appliedUserRepository;
	}
	
//	// 쿠폰 발급 메소드
//	public void apply(Long userId) {
//		// 1. 쿠폰의 개수를 가져옴
//		// long count = couponRepository.count();
//		Long count = couponCountRepository.increment(); // CouponCountRepository의 increment 메소드로 대체
//		
//		// 2. 쿠폰의 개수가 발급 가능한 개수가 초과되었을 경우에는 발급하지 않음
//		if(count > 100) {
//			return;
//		}
//		
//		// 3. 아직 발급이 가능한 경우에는 쿠폰을 새로 생성
//		// couponRepository.save(new Coupon(userId)); // 직접 쿠폰을 생성하는 이 로직을 삭제하고 CouponCreateProducer를 사용해서 Topic에 유저의 아이디를 전송하도록 변경
//		couponCreateProducer.create(userId);
//	}
	
	/**
	 * 1인당 발급 가능한 쿠폰 개수를 1개로 제한하는 방법 두번째로는 범위로 락을 잡고 처음에 쿠폰 발급 여부를 가져와서 판단하는 방식입니다.
	 * 보시는 것과 같이 범위로 락을 잡고 쿠폰 발급 여부를 가지고 온 뒤에 아직 발급이 안됐다면 쿠폰 발급을 하고 발급 됐다면 쿠폰을 발급하지 않는 방법입니다.
	 * 하지만 현재 저희는 api에서 쿠폰 발급 가능 여부만 판단하고 실제로 쿠폰 생성은 consumer에서 생성하고 있습니다.
	 * 이 사이에는 시간차가 존재하며 그 시간차이 때문에 한 명이 두 개가 발급되는 경우가 발생될 수 있습니다.
	 * api에서 직접 쿠폰을 발급한다고 쳐도 락 범위가 너무 넓어지게 됩니다.
	 * 다른 요청들은 이 락이 끝날 때까지 이 로직에 접근을 하지 못하게 되므로 성능이 안좋아지는 이슈가 발생할 수 있습니다.
	 */
//	// 쿠폰 발급 메소드
//	public void apply(Long userId) {
//		// lock start
//		// 쿠폰발급 여부
//		// if(발급됐다면) return
//		Long count = couponCountRepository.increment();
//		
//		if(count > 100) {
//			return;
//		}
//		
//		couponCreateProducer.create(userId);
//		// 쿠폰발급 
//		// lock end
//	}
	
	// 쿠폰 발급 메소드
	public void apply(Long userId) {
		Long apply = appliedUserRepository.add(userId);
		
		if(apply!= 1) {
			return;
		}
		
		// 1. 쿠폰의 개수를 가져옴
		// long count = couponRepository.count();
		Long count = couponCountRepository.increment(); // CouponCountRepository의 increment 메소드로 대체
		
		// 2. 쿠폰의 개수가 발급 가능한 개수가 초과되었을 경우에는 발급하지 않음
		if(count > 100) {
			return;
		}
		
		// 3. 아직 발급이 가능한 경우에는 쿠폰을 새로 생성
		// couponRepository.save(new Coupon(userId)); // 직접 쿠폰을 생성하는 이 로직을 삭제하고 CouponCreateProducer를 사용해서 Topic에 유저의 아이디를 전송하도록 변경
		couponCreateProducer.create(userId);
	}
}
