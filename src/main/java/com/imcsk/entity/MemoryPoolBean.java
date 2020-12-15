package com.imcsk.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 内存池信息
 *
 * @author csk
 * @date 2020-12-14
 */
@Data
public class MemoryPoolBean implements Serializable {

    /**
     * 内存区名称
     */
    private String name;

    /**
     * 所属内存管理者
     */
    private String manageNames;

    /**
     * 已申请内存
     */
    private Long committed;

    /**
     * 最大内存量
     */
    private Long max;

    /**
     * 已使用内存（字节）
     */
    private Long used;
}
