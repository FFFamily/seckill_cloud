package com.tutu.socket;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenerateKLineTask implements Runnable {
    CreateKLineDto klineData;

    public GenerateKLineTask(CreateKLineDto _klineData) {
        klineData = _klineData;
    }

    @Override
    public void run() {
        log.info("异步任务 GenerateKLineTask--开始");
        for (KlineType klineType : KlineType.values()) {
            generateKLine(klineData, klineType);
        }
        log.info("异步任务 GenerateKLineTask--结束");
    }

    /**
     * 创建K线
     *
     * @param klineData
     * @param klineType
     */
    public void generateKLine(CreateKLineDto klineData, KlineType klineType) {

    }
}