package com.online.edu.eduservice.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "课程发布信息")
@Data
public class EduAllCourseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程id")
    private String id;

    @ApiModelProperty(value = "课程题目")
    private String title;

    @ApiModelProperty(value = "课程封面")
    private String cover;

    @ApiModelProperty(value = "课时数")
    private String lessonNum;

    @ApiModelProperty(value = "课程价格")
    private String price;

    @ApiModelProperty(value = "购买数")
    private String buyCount;

    @ApiModelProperty(value = "观看数")
    private String viewCount;

    @ApiModelProperty(value="课程描述")
    private String description;

    @ApiModelProperty(value = "课程讲师")
    private String name;

    @ApiModelProperty(value = "一级分类")
    private String levelOne;

    @ApiModelProperty(value = "二级分类")
    private String levelTwo;

}
