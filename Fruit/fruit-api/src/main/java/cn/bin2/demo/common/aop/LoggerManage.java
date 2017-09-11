package cn.bin2.demo.common.aop;

import java.lang.annotation.*;

/**
 * Created by bing on 2017/9/8.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoggerManage {

    public String description();
}
