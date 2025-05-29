package com.lzp.leaseProject.web.admin.service;

import com.lzp.leaseProject.web.admin.vo.login.CaptchaVo;
import com.lzp.leaseProject.web.admin.vo.login.LoginVo;

public interface LoginService {

    CaptchaVo getCaptcha();

    String login(LoginVo loginVo);
}
