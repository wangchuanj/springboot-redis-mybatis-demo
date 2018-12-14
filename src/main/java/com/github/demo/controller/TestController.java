package com.github.demo.controller;

import com.zhaoonline.zhaotask.component.redis.RedisClient;
import com.zhaoonline.zhaotask.entity.Auctions;
import com.zhaoonline.zhaotask.service.AuctionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private RedisClient redisClient;
    @Autowired
    private AuctionService auctionService;

    @GetMapping(value = "/hello")
    public String hello() {
        Auctions auction = auctionService.selectById(2142L);
        LOG.error("5415645156465");
        return "hello World";
    }

}
