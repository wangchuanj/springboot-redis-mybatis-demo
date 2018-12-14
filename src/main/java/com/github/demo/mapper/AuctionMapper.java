package com.github.demo.mapper;

import com.zhaoonline.zhaotask.core.Mapper;
import com.zhaoonline.zhaotask.entity.Auctions;

import java.util.List;
import java.util.Map;

public interface AuctionMapper  extends Mapper<Auctions> {
    /**
    * @Description:  查询时间间隔中的历史拍品ID
    * @Param:  Map<String,Object>
    * @return:  List<Long>
    * @Author: WangChuanJ
    * @Date: 2018/12/12
    */
    List<Long> selectByTimeDiff(Map<String, Object> paramMap);
}
