package com.online.edu.staservice.service;

import com.online.edu.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author liulong
 * @since 2019-12-17
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void createStatisticsByDate(String day);
}
