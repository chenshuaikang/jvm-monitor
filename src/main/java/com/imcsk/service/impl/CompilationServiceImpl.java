package com.imcsk.service.impl;

import com.imcsk.entity.CompilationBean;
import com.imcsk.service.ICompilationService;
import org.springframework.stereotype.Service;

import java.lang.management.CompilationMXBean;
import java.lang.management.ManagementFactory;

/**
 * @author csk
 * @date 2020-12-14
 */
@Service
public class CompilationServiceImpl implements ICompilationService {

    @Override
    public CompilationBean get() {
        return init();
    }

    private CompilationBean init() {
        CompilationBean bean = new CompilationBean();
        CompilationMXBean compilationMXBean = ManagementFactory.getCompilationMXBean();
        bean.setName(compilationMXBean.getName());
        bean.setTotalTime(compilationMXBean.getTotalCompilationTime());
        bean.setIsSupport(compilationMXBean.isCompilationTimeMonitoringSupported());
        return bean;
    }
}
