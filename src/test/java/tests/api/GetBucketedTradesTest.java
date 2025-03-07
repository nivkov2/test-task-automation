package tests.api;

import lombok.SneakyThrows;
import org.interview.task.api.client.TradeApiClient;
import org.interview.task.helper.TradeHelpers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Map;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetBucketedTradesTest {
    private final TradeApiClient tradeApiClient = new TradeApiClient();

    @Test
    @SneakyThrows
    void shouldReturnResultsAccordingToSearchParams() {
        // TODO:
        //  I didnt have enough time to request all historical data
        //  so just worked with data retrieved from this enpoint, but we can read data in batches
        //  to get data for 7 days period and then calculate it

        // request data
        var params = Map.of("count", "1000",
                "binSize", "1d",
                "partial", "false",
                "reverse", "false");
        var tradesData = tradeApiClient.getTradesData(params);

        // calculate stat per symbol group
        var tradesDataStatistics = TradeHelpers.groupBySymbolAndCalculate(tradesData);
        // define top 10 instruments
        var top10liquidInstruments = TradeHelpers.getTop10LiquidSymbols(tradesDataStatistics);

        // print to console
        TradeHelpers.logToConsoleAsJson(top10liquidInstruments);
    }
}
