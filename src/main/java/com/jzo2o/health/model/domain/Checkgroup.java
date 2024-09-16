package com.jzo2o.health.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("checkgroup")
public class Checkgroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 检查组编码
     */
    private String code;

    /**
     * 检查组名称
     */
    private String name;

    /**
     * 检查组助记码
     */
    private String helpCode;

    /**
     * 检查组性别
     */
    private String sex;

    /**
     * 检查组说明
     */
    private String remark;

    /**
     * 检查组注意事项
     */
    private String attention;


}
