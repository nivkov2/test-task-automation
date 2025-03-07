package org.interview.task.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.interview.task.dto.TradeDataDto;
import org.interview.task.dto.TradeDataStatisticDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class TradeHelpers {

    public Map<String, TradeDataStatisticDto> groupBySymbolAndCalculate(List<TradeDataDto> trades) {
        return trades.stream()
                .collect(Collectors.groupingBy(
                        TradeDataDto::symbol,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    // Sum of volume
                                    BigDecimal sumVolume = list.stream()
                                            .map(TradeDataDto::volume)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                                    // Average of high prices
                                    BigDecimal avgHigh = list.stream()
                                            .map(TradeDataDto::high)
                                            .filter(Objects::nonNull)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add)
                                            .divide(BigDecimal.valueOf(list.size()), BigDecimal.ROUND_HALF_UP);

                                    // Minimal price (lowest low value)
                                    BigDecimal minPrice = list.stream()
                                            .map(TradeDataDto::low)
                                            .filter(Objects::nonNull)
                                            .min(BigDecimal::compareTo)
                                            .orElse(BigDecimal.ZERO);
                                    return new TradeDataStatisticDto(sumVolume, avgHigh, minPrice);
                                }
                        )
                ));
    }

    public List<Map.Entry<String, TradeDataStatisticDto>> getTop10LiquidSymbols(Map<String, TradeDataStatisticDto> groupedStats) {
        return groupedStats.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().sumVolume().compareTo(entry1.getValue().sumVolume()))
                .limit(10)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public void logToConsoleAsJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
    }
}
