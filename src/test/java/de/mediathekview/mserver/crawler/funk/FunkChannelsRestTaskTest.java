package de.mediathekview.mserver.crawler.funk;

import de.mediathekview.mlib.daten.Sender;
import de.mediathekview.mserver.crawler.funk.json.FunkChannelDeserializer;
import de.mediathekview.mserver.crawler.funk.tasks.FunkRestEndpoint;
import de.mediathekview.mserver.crawler.funk.tasks.FunkRestTask;
import org.junit.Test;

import java.util.Optional;
import java.util.Set;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class FunkChannelsRestTaskTest extends FunkTaskTestBase {

  @Test
  public void testOverviewWithSinglePage() {
    final String requestUrl = "/api/v4.0/channels/";
    setupSuccessfulJsonResponse(requestUrl, "/funk/funk_channel_page_last.json");

    final Set<FunkChannelDTO> actual = executeTask(requestUrl);

    assertThat(actual, notNullValue());
    assertThat(actual.size(), equalTo(3));
  }

  @Test
  public void testOverviewWithMultiplePagesLimitSubpagesLargerThanSubpageCount() {

    rootConfig.getSenderConfig(Sender.FUNK).setMaximumSubpages(5);

    final String requestUrl = "/api/v4.0/channels/";
    setupSuccessfulJsonResponse(requestUrl, "/funk/funk_channel_page_1.json");
    setupSuccessfulJsonResponse(
        "/api/v4.0/channels/?page=1&size=100&sort=updateDate,desc",
        "/funk/funk_channel_page_last.json");

    final Set<FunkChannelDTO> actual = executeTask(requestUrl);

    assertThat(actual, notNullValue());
    assertThat(actual.size(), equalTo(103));
  }

  @Test
  public void testOverviewWithMultiplePagesLimitSubpagesSmallerThanSubpageCount() {
    rootConfig.getSenderConfig(Sender.FUNK).setMaximumSubpages(1);

    final String requestUrl = "/api/v4.0/channels/";
    setupSuccessfulJsonResponse(requestUrl, "/funk/funk_channel_page_1.json");
    setupSuccessfulJsonResponse(
        "/api/v4.0/channels/?page=1&size=100&sort=updateDate,desc",
        "/funk/funk_channel_page_last.json");

    final Set<FunkChannelDTO> actual = executeTask(requestUrl);

    assertThat(actual, notNullValue());
    assertThat(actual.size(), equalTo(100));
  }

  @Test
  public void testOverviewPageNotFound() {
    final String requestUrl = "/api/v4.0/channels/";

    wireMockRule.stubFor(
        get(urlEqualTo(requestUrl)).willReturn(aResponse().withStatus(404).withBody("Not Found")));

    final Set<FunkChannelDTO> actual = executeTask(requestUrl);
    assertThat(actual, notNullValue());
    assertThat(actual.size(), equalTo(0));
  }

  private Set<FunkChannelDTO> executeTask(final String aRequestUrl) {
    final FunkCrawler crawler = createCrawler();
    return new FunkRestTask<>(
            crawler,
            new FunkRestEndpoint<>(
                FunkApiUrls.CHANNELS,
                new FunkChannelDeserializer(
                    Optional.of(crawler), rootConfig.getSenderConfig(Sender.FUNK))),
            createCrawlerUrlDto(aRequestUrl))
        .invoke();
  }
}