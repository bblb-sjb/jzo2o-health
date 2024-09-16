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
@TableName("setmeal")
public class Setmeal implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 套餐名称
     */
    private String name;

    /**
     * 套餐编码
     */
    private String code;

    /**
     * 套餐助记码
     */
    private String helpCode;

    /**
     * 套餐助记码
     */
    private Integer sex;

    /**
     * 套餐适用年龄
     */
    private String age;

    /**
     * 套餐价格
     */
    private Float price;

    /**
     * 套餐说明
     */
    private String remark;

    /**
     * 套餐注意事项
     */
    private String attention;

    /**
     * 套餐图片
     */
    private String img;


}
