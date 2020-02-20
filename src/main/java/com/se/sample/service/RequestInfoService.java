package com.se.sample.service;

import com.se.sample.entity.RequestInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RequestInfoService {
    Page<RequestInfo> getRequestInfos(Pageable pageable);
    RequestInfo create(RequestInfo requestInfo);
}

