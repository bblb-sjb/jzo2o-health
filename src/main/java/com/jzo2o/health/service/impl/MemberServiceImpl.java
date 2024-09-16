package com.jzo2o.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.health.mapper.MemberMapper;
import com.jzo2o.health.model.domain.Member;
import com.jzo2o.health.service.IMemberService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 普通用户 服务实现类
 * </p>
 *
 * @author itcast
 * @since 2023-11-02
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {
    /**
     * 默认头像
     */
    private static final String DEFAULT_AVATAR = "https://yjy-oss-videos.oss-accelerate.aliyuncs.com/tx.png";

    /**
     * 根据手机号查询普通用户
     *
     * @param phone 手机号
     * @return 普通用户
     */
    @Override
    public Member findByPhone(String phone) {
        LambdaQueryWrapper<Member> queryWrapper = Wrappers.<Member>lambdaQuery().eq(Member::getPhone, phone);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 新增普通用户
     *
     * @param phone 手机号
     * @return 普通用户
     */
    @Override
    public Member add(String phone) {
        Member member = new Member();
        member.setNickname("普通用户");
        member.setPhone(phone);
        //设置默认头像
        member.setAvatar(DEFAULT_AVATAR);
        baseMapper.insert(member);
        return member;
    }
}
