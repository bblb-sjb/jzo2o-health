package com.jzo2o.health.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.health.mapper.SetmealMapper;
import com.jzo2o.health.model.domain.Setmeal;
import com.jzo2o.health.model.dto.request.CommonPageQueryReqDTO;
import com.jzo2o.health.model.dto.response.SetmealDetailResDTO;
import com.jzo2o.health.service.ISetmealCheckgroupService;
import com.jzo2o.health.service.ISetmealService;
import com.jzo2o.mysql.utils.PageUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author itcast
 * @since 2023-10-31
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements ISetmealService {
    @Resource
    private ISetmealCheckgroupService setmealCheckgroupService;

    //新增套餐
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        baseMapper.insert(setmeal);
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            //绑定套餐和检查组的多对多关系
            setSetmealAndCheckGroup(setmeal.getId(), checkgroupIds);
        }
    }

    @Override
    public List<Setmeal> findAll() {
        return super.list();
    }

    @Override
    public Setmeal findById(int id) {
        return baseMapper.selectById(id);
    }

    /**
     * 查询套餐详情（包含关联检查组、检查项）
     *
     * @param id 套餐id
     * @return 套餐详情
     */
    @Override
    public SetmealDetailResDTO findDetail(Integer id) {
        return baseMapper.findDetail(id);
    }

    //绑定套餐和检查组的多对多关系
    private void setSetmealAndCheckGroup(Integer id, Integer[] checkgroupIds) {
        for (Integer checkgroupId : checkgroupIds) {
            setmealCheckgroupService.setSetmealAndCheckGroup(id, checkgroupId);
        }
    }

    @Override
    public PageResult<Setmeal> findPage(CommonPageQueryReqDTO commonPageQueryReqDTO) {
        //开始分页---设置分页条件
        Page<Setmeal> page = PageUtils.parsePageQuery(commonPageQueryReqDTO, Setmeal.class);

        LambdaQueryWrapper<Setmeal> queryWrapper = Wrappers.<Setmeal>lambdaQuery()
                .or().eq(StrUtil.isNotBlank(commonPageQueryReqDTO.getKeyword()), Setmeal::getCode, commonPageQueryReqDTO.getKeyword())
                .or().eq(StrUtil.isNotBlank(commonPageQueryReqDTO.getKeyword()), Setmeal::getName, commonPageQueryReqDTO.getKeyword())
                .or().eq(StrUtil.isNotBlank(commonPageQueryReqDTO.getKeyword()), Setmeal::getHelpCode, commonPageQueryReqDTO.getKeyword());
        Page<Setmeal> checkgroupPage = baseMapper.selectPage(page, queryWrapper);
        return PageUtils.toPage(checkgroupPage, Setmeal.class);
    }
}
