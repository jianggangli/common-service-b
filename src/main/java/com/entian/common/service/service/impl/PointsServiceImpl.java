package com.entian.common.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entian.common.service.dto.CommodityOrderDTO;
import com.entian.common.service.entity.PointsEntity;
import com.entian.common.service.mapper.PointsMapper;
import com.entian.common.service.service.PointsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jianggangli
 * @version 1.0 2020/12/28 17:31
 * 功能:
 */
@Slf4j
@Service
public class PointsServiceImpl extends ServiceImpl<PointsMapper, PointsEntity> implements PointsService {
    @Autowired
    PointsMapper pointsMapper;


    @Override
    public void increasePoints(CommodityOrderDTO order) {
        Map<String, Object> where = new HashMap<>();
        where.put("order_no", order.getOrderNo());
        //入库之前先查询，实现幂等
        List<PointsEntity> list = this.listByMap(where);
        if (list != null && list.size() > 0) {
            log.info("积分添加完成，订单已处理。{}", order.getOrderNo());
        } else {
            PointsEntity points = new PointsEntity();
            points.setUserId("jianggangli");
            points.setOrderNo(order.getOrderNo());
            Double amount = 99.0;
            points.setPoints(amount.intValue() * 10);
            points.setRemarks("商品消费共【" + amount + "】元，获得积分" + points.getPoints());
            pointsMapper.insert(points);
            log.info("已为订单号码{}增加积分。", points.getOrderNo());
        }
    }
}
