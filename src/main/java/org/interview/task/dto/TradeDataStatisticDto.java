package org.interview.task.dto;

import java.math.BigDecimal;

public record TradeDataStatisticDto(BigDecimal sumVolume,
                                    BigDecimal avgHigh,
                                    BigDecimal minPrice) {
}
