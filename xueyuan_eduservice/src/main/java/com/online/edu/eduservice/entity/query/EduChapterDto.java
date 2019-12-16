package com.online.edu.eduservice.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "章节信息")
public class EduChapterDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "章节ID")
    private String id;

    @ApiModelProperty(value = "章节名称")
    private String title;

    @ApiModelProperty(value = "小节集合")
    private List<EduVideoDto> children=new ArrayList<>();

}
