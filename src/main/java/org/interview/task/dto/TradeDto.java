package org.interview.task.dto;

import java.math.BigDecimal;
import java.util.Date;

public record TradeDto(
        Date timestamp,
        String symbol,
        String side,
        Long size,
        BigDecimal price,
        String tickDirection,
        String trdMatchID,
        BigDecimal grossValue,
        BigDecimal homeNotional,
        BigDecimal foreignNotional,
        String trdType
) {
}
