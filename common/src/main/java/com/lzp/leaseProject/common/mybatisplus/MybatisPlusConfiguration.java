package com.lzp.leaseProject.common.mybatisplus;

import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;

@Configuration
@MapperScan("com.atguigu.lease.web.*.mapper")

public class MybatisPlusConfiguration {

}
