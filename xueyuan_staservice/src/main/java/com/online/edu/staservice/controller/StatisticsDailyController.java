package com.online.edu.staservice.controller;


import com.online.edu.common.R;
import com.online.edu.staservice.service.StatisticsDailyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author liulong
 * @since 2019-12-17
 */
@RestController
@RequestMapping("/staservice/daily")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    StatisticsDailyService statisticsDailyService;

    @ApiOperation(value="统计日注册人数")
    @PostMapping("{day}")
    public R createStatisticsByDate(@PathVariable String day){
        statisticsDailyService.createStatisticsByDate(day);
        return  R.ok();
    }

    @ApiOperation(value = "显示echarts图表")
    @GetMapping("showChart/{begin}/{end}/{type}")
    public R showChart(@PathVariable String begin,@PathVariable String end,@PathVariable String type){
        Map<String,Object>map=statisticsDailyService.showChart(begin,end,type);
        return R.ok().data("dataMap",map);
    }
}

