package com.bhole.shop.authn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author jokelin
 */

//@ServletComponentScan(basePackageClasses = {YcFilter.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication(scanBasePackages = {"com.bhole.shop"})
public class AuthnApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthnApplication.class, args);
	}

}
