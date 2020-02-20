package com.se.sample.repository;


import com.se.sample.entity.RequestInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestInfoRepository extends JpaRepository<RequestInfo, Long> {
    Page<RequestInfo> findAll(Pageable pageable);
}