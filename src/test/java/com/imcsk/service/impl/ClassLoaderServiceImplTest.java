package com.imcsk.service.impl;

import com.imcsk.entity.ClassLoaderBean;
import com.imcsk.service.IClassLoaderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author tycoding
 * @date 2019-05-10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClassLoaderServiceImplTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IClassLoaderService IClassLoaderService;

    @Test
    public void get() {
        ClassLoaderBean classLoaderBean = IClassLoaderService.get();
        logger.info("classLoader => {}", classLoaderBean);
    }
}