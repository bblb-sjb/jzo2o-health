package com.jzo2o.health.model.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author itcast
 * @since 2023-10-31
 */
@Data
@ApiModel("套餐详情响应模型")
public class SetmealDetailResDTO {

    /**
     * 套餐id
     */
    @ApiModelProperty("套餐id")
    private Integer id;

    /**
     * 套餐名称
     */
    @ApiModelProperty("套餐名称")
    private String name;

    /**
     * 套餐编码
     */
    @ApiModelProperty("套餐编码")
    private String code;

    /**
     * 套餐助记码
     */
    @ApiModelProperty("套餐助记码")
    private String helpCode;

    /**
     * 性别，0：不限，1：男，2：女
     */
    @ApiModelProperty("性别，0：不限，1：男，2：女")
    private String sex;

    /**
     * 适用年龄
     */
    @ApiModelProperty("适用年龄")
    private String age;

    /**
     * 价格
     */
    @ApiModelProperty("价格")
    private Float price;

    /**
     * 说明
     */
    @ApiModelProperty("说明")
    private String remark;

    /**
     * 注意事项
     */
    @ApiModelProperty("注意事项")
    private String attention;

    /**
     * 图片
     */
    @ApiModelProperty("图片")
    private String img;

    /**
     * 检查组列表
     */
    @ApiModelProperty("检查组列表")
    private List<CheckGroupDetailResDTO> checkGroupList;

}
