package com.imcsk.service;

import com.imcsk.entity.GarbageCollectorBean;
import com.imcsk.entity.MemoryPoolBean;

import java.util.List;

/**
 * @author csk
 * @date 2020-12-14
 */
public interface IGarbageCollectorService {

    GarbageCollectorBean get();

    List<MemoryPoolBean> getPools();
}
