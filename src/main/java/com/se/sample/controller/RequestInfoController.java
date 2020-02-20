package com.se.sample.controller;

import com.se.sample.entity.RequestInfo;
import com.se.sample.service.RequestInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(value = "Producer request info management")
@RestController("/request-info")
public class RequestInfoController {

    private RequestInfoService requestInfoService;

    public RequestInfoController(@Autowired RequestInfoService requestInfoService) {
        this.requestInfoService = requestInfoService;
    }

    @ApiOperation(value = "Pageable information about threads that are out of range", response = List.class)
    @GetMapping("/requests")
    public Page<RequestInfo> getAllCommentsByPostId(Pageable pageable) {
        return requestInfoService.getRequestInfos(pageable);
    }
}
