package com.lzp.leaseProject.web.admin.controller.apartment;

import com.lzp.leaseProject.common.result.Result;
import com.lzp.leaseProject.web.admin.service.LoginService;
import com.lzp.leaseProject.web.admin.vo.login.CaptchaVo;
import com.lzp.leaseProject.web.admin.vo.login.LoginVo;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 李志平
 * @version 1.0
 * @data 2025/5/29 15:07
 * @desc
 */
@Tag(name = "登录管理")
@RestController
@RequestMapping("admin/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    //验证码请求接口
    @GetMapping("/captcha")
    @Operation(summary = "获取图形验证码")
    public Result<CaptchaVo> cpatcha(){
        CaptchaVo captcha = loginService.getCaptcha();
        return Result.ok(captcha);
    }

    //登录接口验证
    @GetMapping("login")
    @Operation(summary = "登录")
    private Result<String> login(@RequestBody LoginVo loginVo){
        String token = loginService.login(loginVo);
        return Result.ok(token);
    }
}
