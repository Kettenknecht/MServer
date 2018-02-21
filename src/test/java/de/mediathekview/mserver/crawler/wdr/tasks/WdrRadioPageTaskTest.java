package de.mediathekview.mserver.crawler.wdr.tasks;

import de.mediathekview.mserver.crawler.basic.CrawlerUrlDTO;
import de.mediathekview.mserver.crawler.wdr.WdrConstants;
import de.mediathekview.mserver.crawler.wdr.WdrTopicUrlDTO;
import de.mediathekview.mserver.testhelper.JsoupMock;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.hamcrest.Matchers;
import org.jsoup.Jsoup;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Jsoup.class})
@PowerMockIgnore("javax.net.ssl.*")
public class WdrRadioPageTaskTest extends WdrTaskTestBase {
  @Test
  public void test() throws IOException {
    final String requestUrl = WdrConstants.URL_RADIO_WDR4;
    JsoupMock.mock(requestUrl, "/wdr/wdr4_overview.html");
    
    WdrTopicUrlDTO[] expected = new WdrTopicUrlDTO[] {
      new WdrTopicUrlDTO("WDR 4 Events", "https://www1.wdr.de/mediathek/video/radio/wdr4/wdr4-videos-events-100.html", false),
      new WdrTopicUrlDTO("Kuttler Digital", "https://www1.wdr.de/mediathek/video/radio/wdr4/wdr4-videos-kuttler-digital-100.html", false),
      new WdrTopicUrlDTO("Ullas Lieblingsrezepte", "https://www1.wdr.de/mediathek/video/radio/wdr4/wdr4-videos-ullas-lieblingsrezepte-100.html", false),
      new WdrTopicUrlDTO("WDR 4 Studiogäste", "https://www1.wdr.de/mediathek/video/radio/wdr4/wdr4-videos-studiogaeste-102.html", false),
      new WdrTopicUrlDTO("WDR 4 Aktionen", "https://www1.wdr.de/mediathek/video/radio/wdr4/wdr4-videos-aktionen-100.html", false),
    };
    
    ConcurrentLinkedQueue<CrawlerUrlDTO> queue = new ConcurrentLinkedQueue<>();
    queue.add(new CrawlerUrlDTO(requestUrl));
    
    WdrRadioPageTask target = new WdrRadioPageTask(createCrawler(), queue);
    Set<WdrTopicUrlDTO> actual = target.invoke();
    
    assertThat(actual, notNullValue());
    assertThat(actual.size(), equalTo(expected.length));
    assertThat(actual, Matchers.containsInAnyOrder(expected));
    
  }  
}
