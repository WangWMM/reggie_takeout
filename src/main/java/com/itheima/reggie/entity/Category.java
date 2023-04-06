package com.itheima.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Wangmin
 * @date 2023/3/30 16:41
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private static final long serialVersionUID = 1L;

    private Long id;

    //类型 1菜品分类 2套餐分类
    private Integer type;

    //分类名称
    private String name;

    //顺序
    private Integer sort;

    //分类状态  0禁用 1启用
    private Integer status;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    //创建人id
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    //更新人id
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

}
