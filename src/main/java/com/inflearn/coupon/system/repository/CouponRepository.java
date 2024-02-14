package com.inflearn.coupon.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inflearn.coupon.system.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
