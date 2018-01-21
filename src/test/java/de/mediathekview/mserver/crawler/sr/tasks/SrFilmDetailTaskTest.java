package de.mediathekview.mserver.crawler.sr.tasks;

import de.mediathekview.mlib.daten.Film;
import de.mediathekview.mlib.daten.Resolution;
import de.mediathekview.mlib.daten.Sender;
import de.mediathekview.mserver.testhelper.JsoupMock;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
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
public class SrFilmDetailTaskTest extends SrTaskTestBase {
 
  @Test
  public void test() throws IOException {
    String theme = "Meine Traumreise";
    String requestUrl = "https://www.sr-mediathek.de/index.php?seite=7&id=54623";
    JsoupMock.mock(requestUrl, "/sr/sr_film_page1.html");
    
    setupSuccessfulJsonResponse("/sr_player/mc.php?id=54623&tbl=&pnr=0&hd=0&devicetype=", "/sr/sr_film_video_details1.json");
    
    final Set<Film> actual = executeTask(theme, requestUrl);

    assertThat(actual, notNullValue());
    assertThat(actual.size(), equalTo(1));
    
    Film actualFilm = (Film) actual.toArray()[0];
    assertThat(actualFilm, notNullValue());
    assertThat(actualFilm.getSender(), equalTo(Sender.SR));
    assertThat(actualFilm.getThema(), equalTo(theme));
    assertThat(actualFilm.getTitel(), equalTo("Meine Traumreise vom Zürichsee an die Ostsee"));
    assertThat(actualFilm.getTime(), equalTo(LocalDateTime.of(2017, 9, 30, 0, 0, 0)));
    assertThat(actualFilm.getDuration(), equalTo(Duration.of(1695, ChronoUnit.SECONDS)));
    assertThat(actualFilm.getBeschreibung(), equalTo("Die Hochzeitsreise vor der Hochzeit - das ist zwar nicht die Regel, aber nicht wirklich ungewöhnlich. Speziell ist dagegen das Gefährt, das sich Anna und Thomas für ihren Trip ausgesucht haben. Die beiden reisen per Gleitschirm. Begleiten Sie das Paar vom Zürichsee zur Ostsee."));
    assertThat(actualFilm.getWebsite().get().toString(), equalTo(requestUrl));

    assertThat(actualFilm.getSubtitles().isEmpty(), equalTo(true));
    assertThat(actualFilm.getUrl(Resolution.SMALL).toString(), equalTo("https://srstorage01-a.akamaihd.net/Video/FS/MT/traumreise_20170926_124001_M.mp4"));
    assertThat(actualFilm.getUrl(Resolution.NORMAL).toString(), equalTo("https://srstorage01-a.akamaihd.net/Video/FS/MT/traumreise_20170926_124001_L.mp4"));
    assertThat(actualFilm.getUrl(Resolution.HD).toString(), equalTo("https://srstorage01-a.akamaihd.net/Video/FS/MT/traumreise_20170926_124001_P.mp4"));
  }
  
  private Set<Film> executeTask(String aTheme, String aRequestUrl) {
    return new SrFilmDetailTask(createCrawler(), createCrawlerUrlDto(aTheme, aRequestUrl)).invoke();    
  }
}
