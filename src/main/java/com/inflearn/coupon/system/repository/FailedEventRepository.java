package com.inflearn.coupon.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inflearn.coupon.system.domain.FailedEvent;

public interface FailedEventRepository extends JpaRepository<FailedEvent, Long> {

}
