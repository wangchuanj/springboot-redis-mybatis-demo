package com.github.demo.component.task;

import com.zhaoonline.zhaotask.component.redis.RedisClient;
import com.zhaoonline.zhaotask.component.redis.RedisLock;
import com.zhaoonline.zhaotask.core.constants.PlatFormConstants;
import com.zhaoonline.zhaotask.mapper.AuctionMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: zhao-task
 * @description: 清理redis中的历史出价数据
 * @author: WangChuanJ
 * @create: 2018-12-11 17:03
 **/
@Component
@Lazy(false)
@EnableScheduling
@EnableAsync
public class CleanRedisHistroyData {
    private static final Logger LOG = LoggerFactory.getLogger(CleanRedisHistroyData.class);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    private AuctionMapper auctionMapper;

    @Autowired
    private RedisLock redisLock;
    @Autowired
    private RedisClient redisClient;


    @Scheduled(cron = "0 0 1 ? * SUN")
    public void dataClean() throws ParseException {
        boolean lock = redisLock.getLock(PlatFormConstants.REDIS_CLEAN_BIDDING_LOCK_KEY, 1000, 1000, 1000 * 60 * 60);
        if (!lock) {
            LOG.info("batch clean redis history data no have get lock with continue");
            return;
        }
        Map<String, Object> paramMap = new HashMap<>();
        //当前时间 - 半年 ,存入redis
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -6);
        String endTime = sdf.format(c.getTime());
        //获取上一次执行数据清理的时间节点
        String lastCleanTime = redisClient.get(PlatFormConstants.REDIS_LAST_CLEAN_TIME);
        LOG.info("清理开始:" + System.currentTimeMillis() / 1000);
        try {
            if (StringUtils.isEmpty(lastCleanTime)) {//第一次进行数据清理
                String[] timeArray = new String[]{  "2010-01-01","2010-06-01",
                                                    "2011-01-01","2011-06-01",
                                                    "2012-01-01","2012-06-01",
                                                    "2013-01-01","2013-06-01",
                                                    "2014-01-01","2014-06-01",
                                                    "2015-01-01","2015-06-01",
                                                    "2016-01-01","2016-06-01",
                                                    "2017-01-01","2017-06-01",
                                                    "2018-01-01","2018-06-01"
                                            };
                //查询Auctions表中历史数据的id,清除2010-01-01 到 2018-01-01
                for (int i = 1; i < timeArray.length ; i++) {
                    paramMap.put("startTime", timeArray[i-1]);
                    paramMap.put("endTime", timeArray[i]);
                    //清除
                   cleanRedisData(paramMap);
                }
            } else {
                /**
                 * 只需要删除< 当前时间-半年 ~上次清理时间> 的数据
                 * 例如:上一次清理时间为2018-12-01
                 *      清理的数据为 2018-05-28  : 2018-05-21
                 *      此次 2018-12-08 减去半年 2018-06-05
                 *      清理 2018-05-28 ~ 2018-06-05 的数据 (清理时间间隔=cron表达式执行间隔)
                 * */
                paramMap.put("startTime", lastCleanTime);
                paramMap.put("endTime", endTime);
                //清除
                cleanRedisData(paramMap);
            }
        } catch (Exception e) {
            LOG.info("batch redis history clean error");
        } finally {
            try {
                redisLock.releaseLock(PlatFormConstants.REDIS_CLEAN_BIDDING_LOCK_KEY);
            } catch (Exception e) {
            }
        }
        //保存当前清理时间
        redisClient.put(PlatFormConstants.REDIS_LAST_CLEAN_TIME, endTime);
        LOG.info("清理结束:" + System.currentTimeMillis() / 1000);

    }


    private void cleanRedisData(Map<String, Object> paramMap) {
        List<Long> ids = auctionMapper.selectByTimeDiff(paramMap);
        if (ids != null && ids.size() > 0) {
            /**
             * 删除
             * 拍品信息(key auction:{auctionId})
             * 出价记录(key bid_history:{auctionId})
             * 实时竞价信息(key bid_set:{auctionId})
             * 某一拍品用户出价信息(key bid:{auctionId})
             * */
            ids.forEach(auctionId -> {
                redisClient.delete("auction:" + auctionId);
                redisClient.delete("bid_history:" + auctionId);
                redisClient.delete("bid_set:" + auctionId);
                redisClient.delete("bid:" + auctionId);
            });

        }
    }


    @PostConstruct
    public void init() throws Exception {
        redisLock.releaseLock(PlatFormConstants.REDIS_CLEAN_BIDDING_LOCK_KEY);
    }
}
