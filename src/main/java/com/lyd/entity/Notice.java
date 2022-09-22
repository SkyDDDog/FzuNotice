package com.lyd.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("notice")
public class Notice {

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    private String notice_id;
    private String section_id;
    private String content;
    private String link;
    private Date date;

    @TableLogic
    private boolean is_deleted;
    @TableField(fill = FieldFill.INSERT)
    private Date gmt_created;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmt_modified;

}
