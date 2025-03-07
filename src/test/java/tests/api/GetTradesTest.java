package tests.api;

import org.interview.task.api.client.TradeApiClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.stream.Stream;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetTradesTest {
    private final TradeApiClient tradeApiClient = new TradeApiClient();

    private static Stream<Arguments> searchParamValues() {
        return Stream.of(
                Arguments.of("true", Comparator.reverseOrder()),
                Arguments.of("false", Comparator.naturalOrder())
        );
    }

    private static Stream<Arguments> invalidCountParameterData() {
        return Stream.of(
                Arguments.of(-1, "Count must be a positive integer."),
                Arguments.of(1001, "Maximum result count is 1000. Please use the start & count params to paginate."),
                Arguments.of("ethereum", "'count' must be a number.")
        );
    }

    @Test
    void shouldReturnResultsAccordingToSearchParams() {
        var params = Map.of("count", "1000",
                "symbol", "ETH",
                "reverse", "true");
        var trades = tradeApiClient.getTrades(params);
        assertThat(trades.size()).isEqualTo(1000);
        trades.forEach(tradeDto -> assertThat(tradeDto.symbol()).contains("ETH"));
        assertThat(trades).extracting("timestamp", Date.class)
                .isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Test
    void shouldNotReturnValuesInCaseInvalidSymbolParam() {
        var params = Map.of(
                "symbol", "someInvalidParam");
        var trades = tradeApiClient.getTrades(params);
        assertThat(trades.size()).isEqualTo(0);
    }

    @ParameterizedTest
    @MethodSource("searchParamValues")
    void shouldSortResultsAccordingToSearchParams(String value, Comparator comparator) {
        var params = Map.of("reverse", value);
        var trades = tradeApiClient.getTrades(params);

        assertThat(trades).extracting("timestamp", Date.class)
                .isSortedAccordingTo(comparator);
    }

    @ParameterizedTest
    @MethodSource("invalidCountParameterData")
    void shouldReturn400ForInvalidCountParameter(Object value, String expectedMessage) {
        var params = Map.of("count", value);
        var actualErrorMessage = tradeApiClient.getTradesResponse(params)
                .then()
                .assertThat().statusCode(HTTP_BAD_REQUEST)
                .extract().jsonPath().get("error.message");

        assertThat(actualErrorMessage).isEqualTo(expectedMessage);
    }
}
