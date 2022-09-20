package com.yiyulihua.order.conf;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

/**
 * @author sunbo
 * @since 2022/09/19 15:20
 */
@SpringBootTest
@Slf4j
public class AliPayConfTest {

    @Autowired
    private Environment conf;
    @Test
    public void test1() {
        log.info("                ****************************              ");
        log.info(conf.getProperty("alipay.app-id"));
    }
}
