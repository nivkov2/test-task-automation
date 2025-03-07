package org.interview.task.dto;

import java.math.BigDecimal;
import java.util.Date;

public record TradeDataDto(
        Date timestamp,
        String symbol,
        BigDecimal open,
        BigDecimal high,
        BigDecimal low,
        BigDecimal close,
        Long trades,
        Long vwap,
        BigDecimal volume,
        BigDecimal lastSize,
        BigDecimal turnover,
        BigDecimal homeNotional,
        BigDecimal foreignNotional
) {
}