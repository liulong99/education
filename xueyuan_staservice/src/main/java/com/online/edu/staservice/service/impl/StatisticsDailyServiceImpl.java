package com.online.edu.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.staservice.client.UcClient;
import com.online.edu.staservice.entity.StatisticsDaily;
import com.online.edu.staservice.mapper.StatisticsDailyMapper;
import com.online.edu.staservice.service.StatisticsDailyService;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author liulong
 * @since 2019-12-17
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcClient ucClient;

    //统计日注册人数
    @Override
    public void createStatisticsByDate(String day) {
        //删除已经存在的统计对象
        QueryWrapper<StatisticsDaily> wrapper=new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        //获取统计信息
        Integer registerNum  = (Integer)ucClient.registerNum(day).getData().get("countRegister");
        //TODO
        Integer loginNum= RandomUtils.nextInt((int)100.200);
        //TODO
        Integer videoViewNum=RandomUtils.nextInt((int)100.200);
        //TODO
        Integer courseNum = RandomUtils.nextInt((int)100.200);

        //创建统计对象
        StatisticsDaily statisticsDaily=new StatisticsDaily();
        statisticsDaily.setCourseNum(courseNum);
        statisticsDaily.setLoginNum(loginNum);
        statisticsDaily.setVideoViewNum(videoViewNum);
        statisticsDaily.setRegisterNum(registerNum);
        statisticsDaily.setDateCalculated(day);

        //添加到统计表中
        baseMapper.insert(statisticsDaily);
    }

    //显示图表的方法
    @Override
    public Map<String, Object> showChart(String begin, String end, String type) {
        QueryWrapper<StatisticsDaily> wrapper=new QueryWrapper<>();
        wrapper.orderByAsc("date_calculated");
        wrapper.select("date_calculated",type);
        wrapper.between("date_calculated",begin,end);
        List<StatisticsDaily> statisticsDailies = baseMapper.selectList(wrapper);

        //时间集合
        List<String>timeList=new ArrayList<>();
        //数据集合
        List<Integer>dataList=new ArrayList<>();
        for(int i=0;i<statisticsDailies.size();i++){
            //得到每个daily对象
            StatisticsDaily daily=statisticsDailies.get(i);
            //取时间集合
            timeList.add(daily.getDateCalculated());
            switch (type){
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:break;
            }
        }
        //最后将两个lis放进map中
        Map<String,Object>map=new HashMap<>();
        map.put("timeList",timeList);
        map.put("dataList",dataList);
        return map;
    }
}
