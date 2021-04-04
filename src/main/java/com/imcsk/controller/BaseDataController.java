package com.imcsk.controller;

import com.imcsk.utils.ResultCode;
import com.imcsk.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseDataController {

    @Autowired
    private IClassLoaderService IClassLoaderService;

    @Autowired
    private ICompilationService ICompilationService;

    @Autowired
    private IGarbageCollectorService IGarbageCollectorService;

    @Autowired
    private IMemoryService IMemoryService;

    @Autowired
    private IRuntimeInfoService IRuntimeInfoService;

    @Autowired
    private ISystemInfoService ISystemInfoService;

    @Autowired
    private IThreadInfoService IThreadInfoService;

    @Autowired
    private IRedisService IRedisService;

    @GetMapping("/class/get")
    public ResultCode getClassLoader() {
        try {
            return new ResultCode(200, IClassLoaderService.get());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultCode(500, e.getMessage());
        }
    }

    @GetMapping("/compilation/get")
    public ResultCode getCompilation() {
        try {
            return new ResultCode(200, ICompilationService.get());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultCode(500, e.getMessage());
        }
    }

    @GetMapping("/memory/get")
    public ResultCode getMemory() {
        try {
            return new ResultCode(200, IMemoryService.get());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultCode(500, e.getMessage());
        }
    }

    @GetMapping("/runtime/get")
    public ResultCode getRuntimeInfo() {
        try {
            return new ResultCode(200, IRuntimeInfoService.get());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultCode(500, e.getMessage());
        }
    }

    @GetMapping("/system/get")
    public ResultCode getSystemInfo() {
        try {
            return new ResultCode(200, ISystemInfoService.get());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultCode(500, e.getMessage());
        }
    }

    @GetMapping("/thread/get")
    public ResultCode getThreadInfo() {
        try {
            return new ResultCode(200, IThreadInfoService.get());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultCode(500, e.getMessage());
        }
    }

    @GetMapping("/gc/get")
    public ResultCode getGC() {
        try {
            return new ResultCode(200, IGarbageCollectorService.get());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultCode(500, e.getMessage());
        }
    }

    @GetMapping("/gc/getPools")
    public ResultCode getGCPools() {
        try {
            return new ResultCode(200, IGarbageCollectorService.getPools());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultCode(500, e.getMessage());
        }
    }

    @GetMapping("/dbsize/get")
    public ResultCode getRedisDbSize() {
        try {
            return new ResultCode(200, IRedisService.getRedisDbSize());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultCode(500, e.getMessage());
        }
    }

    @GetMapping("/redismemory/get")
    public ResultCode getRedisMemory() {
        try {
            return new ResultCode(200, IRedisService.getRedisMemory());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultCode(500, e.getMessage());
        }
    }

    @GetMapping("/redisinfo/get")
    public ResultCode getRedisInfo() {
        try {
            return new ResultCode(200, IRedisService.getRedisInfo());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultCode(500, e.getMessage());
        }
    }
}
