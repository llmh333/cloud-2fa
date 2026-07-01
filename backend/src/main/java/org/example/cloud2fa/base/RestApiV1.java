package org.example.cloud2fa.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.example.cloud2fa.constant.UrlConstant;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlConstant.API_VERSION_1)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestApiV1 {

}
