package com.imcsk.service.impl;

import com.imcsk.entity.ThreadBean;
import com.imcsk.service.IThreadInfoService;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * @author csk
 * @date 2020-12-14
 */
@Service
public class ThreadInfoServiceImpl implements IThreadInfoService {

    @Override
    public ThreadBean get() {
        return init();
    }

    private ThreadBean init() {
        ThreadBean bean = new ThreadBean();
        ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
        bean.setCurrentTime(mxBean.getCurrentThreadUserTime());
        bean.setDaemonCount(mxBean.getDaemonThreadCount());
        bean.setCount(mxBean.getThreadCount());
        return bean;
    }
}
