package com.jzo2o.health.model.domain;

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
@TableName("setmeal_checkgroup")
public class SetmealCheckgroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 套餐id
     */
    private Integer setmealId;

    /**
     * 检查组id
     */
    private Integer checkgroupId;


}
