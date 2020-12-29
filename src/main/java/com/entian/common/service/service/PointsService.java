package com.entian.common.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entian.common.service.dto.CommodityOrderDTO;
import com.entian.common.service.entity.PointsEntity;

/**
 * @author jianggangli
 * @version 1.0 2020/12/28 17:31
 * 功能:
 */
public interface PointsService extends IService<PointsEntity> {
    void increasePoints(CommodityOrderDTO order);
}
