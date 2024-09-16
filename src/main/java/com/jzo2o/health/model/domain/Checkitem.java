package com.jzo2o.health.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author itcast
 * @since 2023-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("checkitem")
public class Checkitem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 检查项编码
     */
    private String code;

    /**
     * 检查项名称
     */
    private String name;

    /**
     * 检查项性别，0：不限，1：男，2：女
     */
    private String sex;

    /**
     * 检查项适用年龄
     */
    private String age;

    /**
     * 检查项价格
     */
    private Float price;

    /**
     * 查检项类型,分为检查和检验两种
     */
    private String type;

    /**
     * 检查项注意事项
     */
    private String attention;

    /**
     * 检查项说明
     */
    private String remark;


}
