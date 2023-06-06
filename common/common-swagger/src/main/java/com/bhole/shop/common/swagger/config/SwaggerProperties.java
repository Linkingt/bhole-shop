package com.bhole.shop.common.swagger.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "springdoc")
public class SwaggerProperties {

    /**
     * 是否使用swagger
     */
    private Boolean enabled = Boolean.TRUE;

    /**
     * 组名
     */
    private String groupName = "";

    /**
     * 扫描包名
     */
    private String basePackage = "";

    /**
     * 标题
     */
    private String title = "";

    /**
     * 描述
     */
    private String description = "";

    /**
     * 版本
     */
    private String version = "";

}
