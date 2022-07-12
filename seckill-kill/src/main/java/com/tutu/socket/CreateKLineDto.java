package com.tutu.socket;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
@Data
@EqualsAndHashCode
public class CreateKLineDto {
    /**
     * 交易对名称
     */
    private String symbol;

    /**
     * 交易的价格
     */
    private BigDecimal price;

    /**
     * 交易的数量
     */
    private BigDecimal volume;

    //    开盘价
    private BigDecimal open;

    //    最高价
    private BigDecimal high;

    //    最低价
    private BigDecimal low;

    //    收盘价
    private BigDecimal close;

    //    交易时间
    private Long date;
}
