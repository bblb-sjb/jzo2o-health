package com.jzo2o.health.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.health.mapper.CheckgroupMapper;
import com.jzo2o.health.model.domain.Checkgroup;
import com.jzo2o.health.service.ICheckgroupService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author itcast
 * @since 2023-10-31
 */
@Service
public class CheckgroupServiceImpl extends ServiceImpl<CheckgroupMapper, Checkgroup> implements ICheckgroupService {
}
