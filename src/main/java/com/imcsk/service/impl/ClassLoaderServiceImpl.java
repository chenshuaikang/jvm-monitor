package com.imcsk.service.impl;

import com.imcsk.entity.ClassLoaderBean;
import com.imcsk.service.IClassLoaderService;
import org.springframework.stereotype.Service;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;

/**
 * @author csk
 * @date 2020-12-14
 */
@Service
public class ClassLoaderServiceImpl implements IClassLoaderService {

    @Override
    public ClassLoaderBean get() {
        return init();
    }

    private ClassLoaderBean init() {
        ClassLoaderBean bean = new ClassLoaderBean();
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        bean.setLoaded(classLoadingMXBean.getTotalLoadedClassCount());
        bean.setCount(classLoadingMXBean.getLoadedClassCount());
        bean.setUnLoaded(classLoadingMXBean.getUnloadedClassCount());
        bean.setIsVerbose(classLoadingMXBean.isVerbose());
        return bean;
    }
}
