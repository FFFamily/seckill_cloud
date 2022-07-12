package com.tutu.common.utils;

/**
 * 雪花算法
 *
 * @author 涂涂
 * @DateTime: 2022/1/5 21:05
 */
public class IdWorkerUtil {
    ///起始的时间戳
    private final static long START_STAMP = 1480166465631L;
    //每一部分占用的位数
    private final static long SEQUENCE_BIT = 12; //序列号占用的位数
    private final static long MACHINE_BIT = 5; //机器标识占用的位数
    private final static long DATA_CENTER_BIT = 5;//数据中心占用的位数
    //每一部分的最大值
    private final static long MAX_DATA_CENTER_NUM = -1L ^ (-1L << DATA_CENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);
    //每一部分向左的位移
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;
    private static long dataCenterId = 2L; //数据中心
    private static long machineId = 3L; //机器标识
    private static long sequence = 0L; //序列号
    private static long lastStamp = -1L;//上一次时间戳

    private static String Prefix() {
        String randomPrefix = "";
        for (int i = 0; i < 2; i++) {
            char c = (char) (Math.random() * 26 + 'A');
            randomPrefix += c;
        }
        return randomPrefix;
    }

    //产生下一个ID
    public static synchronized String nextId() {
        long currStamp = getNewStamp();
        if (currStamp < lastStamp) {
            throw new RuntimeException("时间戳错误 . 拒绝生成id");
        }
        if (currStamp == lastStamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStamp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }
        lastStamp = currStamp;
        return Prefix() + ((currStamp - START_STAMP) << TIMESTAMP_LEFT //时间戳部分
                | dataCenterId << DATA_CENTER_LEFT //数据中心部分
                | machineId << MACHINE_LEFT //机器标识部分
                | sequence); //序列号部分
    }

    private static long getNextMill() {
        long mill = getNewStamp();
        while (mill <= lastStamp) {
            mill = getNewStamp();
        }
        return mill;
    }

    private static long getNewStamp() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        // 1642386067924
        // 1642386081253
        System.out.println(nextId());
    }
}
