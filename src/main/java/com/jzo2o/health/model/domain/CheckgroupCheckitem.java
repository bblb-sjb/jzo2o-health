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
@TableName("checkgroup_checkitem")
public class CheckgroupCheckitem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 检查组id
     */
    private Integer checkgroupId;

    /**
     * 检查项id
     */
    private Integer checkitemId;


}
