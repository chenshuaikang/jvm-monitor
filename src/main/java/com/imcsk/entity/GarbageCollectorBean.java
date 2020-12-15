package com.imcsk.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * JVM垃圾回收信息
 *
 * @author csk
 * @date 2020-12-14
 */
@Data
public class GarbageCollectorBean implements Serializable {

    /**
     * GC回收次数
     */
    private Long count;

    /**
     * GC回收耗时
     */
    private Long time;
}
