package com.imcsk.service.impl;

import com.imcsk.entity.MemoryBean;
import com.imcsk.service.IMemoryService;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * @author csk
 * @date 2020-12-14
 */
@Service
public class MemoryServiceImpl implements IMemoryService {

    @Override
    public MemoryBean get() {
        return init();
    }

    private MemoryBean init() {
        MemoryBean bean = new MemoryBean();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        bean.setCommitted(heapMemoryUsage.getCommitted());
        bean.setInit(heapMemoryUsage.getInit());
        bean.setMax(heapMemoryUsage.getMax());
        bean.setUsed(heapMemoryUsage.getUsed());
        bean.setNonCommitted(nonHeapMemoryUsage.getCommitted());
        bean.setNonInit(nonHeapMemoryUsage.getInit());
        bean.setNonMax(nonHeapMemoryUsage.getMax());
        bean.setNonUsed(nonHeapMemoryUsage.getUsed());
        return bean;
    }
}
