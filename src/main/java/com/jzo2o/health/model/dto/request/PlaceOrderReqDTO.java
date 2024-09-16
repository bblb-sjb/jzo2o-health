package com.jzo2o.health.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@ApiModel("下单请求信息")
public class PlaceOrderReqDTO {
    @ApiModelProperty(value = "套餐id", required = true)
    private Integer setmealId;

    @ApiModelProperty(value = "预约日期", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationDate;

    @ApiModelProperty(value = "体检人姓名", required = true)
    private String checkupPersonName;

    @ApiModelProperty(value = "体检人性别，0：不限，1：男，2女", required = true)
    private Integer checkupPersonSex;

    @ApiModelProperty(value = "体检人电话", required = true)
    private String checkupPersonPhone;

    @ApiModelProperty(value = "体检人身份证号", required = true)
    private String checkupPersonIdcard;
}
