package com.lzp.leaseProject.web.admin.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.lzp.leaseProject.common.constant.RedisConstant;
import com.lzp.leaseProject.common.exception.LeaseException;
import com.lzp.leaseProject.common.result.ResultCodeEnum;
import com.lzp.leaseProject.common.utils.JwtUtils;
import com.lzp.leaseProject.model.entity.SystemUser;
import com.lzp.leaseProject.model.enums.BaseStatus;
import com.lzp.leaseProject.web.admin.mapper.SystemUserMapper;
import com.lzp.leaseProject.web.admin.service.LoginService;
import com.lzp.leaseProject.web.admin.vo.login.CaptchaVo;
import com.lzp.leaseProject.web.admin.vo.login.LoginVo;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

//    注入验证码缓存
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public CaptchaVo getCaptcha() {
        //图形验证码创建逻辑
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        //设置验证码字体
        specCaptcha.setFont(new Font("Verdana",Font.PLAIN, 32));
        //设置验证码类型
        specCaptcha.setCharType(Captcha.TYPE_NUM_AND_UPPER);

        String key = RedisConstant.ADMIN_LOGIN_PREFIX + UUID.randomUUID();
        String code = specCaptcha.text().toLowerCase();
        String image = specCaptcha.toBase64();

        stringRedisTemplate.opsForValue().set(key, code, RedisConstant.ADMIN_LOGIN_CAPTCHA_TTL_SEC, TimeUnit.SECONDS);

        return new CaptchaVo(image, key);
    }


    //校验账号密码是否正确
    @Override
    public String login(LoginVo loginVo) {
        //校验验证码
        if(!StringUtils.hasText(loginVo.getCaptchaCode())){
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_NOT_FOUND);
        }

        //校验验证码是否过期
        String code = stringRedisTemplate.opsForValue().get(loginVo.getCaptchaCode());
        if(code == null){
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_EXPIRED);
        }

        if(!code.equals(loginVo.getCaptchaCode().toLowerCase())){
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        }

        //校验用户名是否存在
        SystemUser systemUser = systemUserMapper.selectOneByUsername(loginVo.getUsername());
        if(systemUser == null){
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR);
        }

        //用户是否被禁用
        if(systemUser.getStatus()  ==  BaseStatus.DISABLE){
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
        }

        // 判断用户密码是否正确
        String inputPassword = loginVo.getPassword();
        if (!StringUtils.hasText(inputPassword)) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
        }
        
        String encodedPassword = DigestUtils.md5DigestAsHex(inputPassword.getBytes());
        if (!systemUser.getPassword().equals(encodedPassword)) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
        }

        return JwtUtils.createToken(systemUser.getId(), systemUser.getUsername());
    }
}
