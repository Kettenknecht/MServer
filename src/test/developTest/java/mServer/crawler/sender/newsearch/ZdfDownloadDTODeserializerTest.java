package mServer.crawler.sender.newsearch;

import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.Collection;
import mServer.test.JsonFileReader;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ZdfDownloadDTODeserializerTest {

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
      {"/zdf/zdf_download_sample_with_subtitle_nohd.json", "https://utstreaming.zdf.de/mtt/zdf/17/02/170212_sendung2_sof/3/F1011017_hoh_deu_Lindstroem_Liebesreigen_in_Samlund_120217.xml", "https://rodlzdf-a.akamaihd.net/none/zdf/17/02/170212_sendung2_sof/3/170212_sendung2_sof_476k_p9v13.mp4", "https://rodlzdf-a.akamaihd.net/none/zdf/17/02/170212_sendung2_sof/3/170212_sendung2_sof_1496k_p13v13.mp4", "", "", "", "", "", "", ""},
      {"/zdf/zdf_download_sample_without_subtitle_hd.json", "", "https://nrodlzdf-a.akamaihd.net/de/zdf/17/02/170212_kompakt_spo/1/170212_kompakt_spo_229k_p7v13.mp4", "https://rodlzdf-a.akamaihd.net/de/zdf/17/02/170212_kompakt_spo/1/170212_kompakt_spo_1496k_p13v13.mp4", "https://rodlzdf-a.akamaihd.net/de/zdf/17/02/170212_kompakt_spo/1/170212_kompakt_spo_476k_p9v13.mp4", "", "", "", "", "", ""},
      {"/zdf/zdf_download_sample_with_english.json", "https://utstreaming.zdf.de/mtt/zdf/18/04/180416_2215_sendung_hsn/7/Hard_Sun_Teil1_OmU.xml", "https://rodlzdf-a.akamaihd.net/de/zdf/18/04/180416_2215_sendung_hsn/7/180416_2215_sendung_hsn_a1a2_476k_p9v13.mp4", "https://rodlzdf-a.akamaihd.net/de/zdf/18/04/180416_2215_sendung_hsn/7/180416_2215_sendung_hsn_a1a2_1496k_p13v13.mp4", "", "https://rodlzdf-a.akamaihd.net/de/zdf/18/04/180416_2215_sendung_hsn/7/180416_2215_sendung_hsn_a3a4_476k_p9v13.mp4", "https://rodlzdf-a.akamaihd.net/de/zdf/18/04/180416_2215_sendung_hsn/7/180416_2215_sendung_hsn_a3a4_1496k_p13v13.mp4", "", "", "", ""},
      {"/zdf/zdf_download_sample_with_audiodescription.json", "https://utstreaming.zdf.de/mtt/zdf/19/07/190715_schatz_nimm_du_sie_mok/4/F1021200_hoh_deu_Schatz_nimm_du_sie_150719.xml", "https://rodlzdf-a.akamaihd.net/de/zdf/19/07/190715_schatz_nimm_du_sie_mok/4/190715_schatz_nimm_du_sie_mok_a1a2_776k_p11v14.mp4", "https://rodlzdf-a.akamaihd.net/de/zdf/19/07/190715_schatz_nimm_du_sie_mok/4/190715_schatz_nimm_du_sie_mok_a1a2_1496k_p13v14.mp4", "", "", "", "", "https://rodlzdf-a.akamaihd.net/de/zdf/19/07/190715_schatz_nimm_du_sie_mok/4/190715_schatz_nimm_du_sie_mok_a3a4_776k_p11v14.mp4", "https://rodlzdf-a.akamaihd.net/de/zdf/19/07/190715_schatz_nimm_du_sie_mok/4/190715_schatz_nimm_du_sie_mok_a3a4_1496k_p13v14.mp4", ""}
    });
  }

  private final String jsonFile;
  private final String subtitleUrl;
  private final String smallQualityUrl;
  private final String normalQualityUrl;
  private final String hdQualityUrl;
  private final String smallQualityUrlEng;
  private final String normalQualityUrlEng;
  private final String hdQualityUrlEng;
  private final String smallQualityUrlAd;
  private final String normalQualityUrlAd;
  private final String hdQualityUrlAd;

  public ZdfDownloadDTODeserializerTest(String jsonFile, String subtitleUrl, String smallQualityUrl, String normalQualityUrl, String hdQualityUrl, String smallQualityUrlEng, String normalQualityUrlEng, String hdQualityUrlEng, String smallQualityUrlAd, String normalQualityUrlAd, String hdQualityUrlAd) {
    this.jsonFile = jsonFile;
    this.subtitleUrl = subtitleUrl;
    this.smallQualityUrl = smallQualityUrl;
    this.normalQualityUrl = normalQualityUrl;
    this.hdQualityUrl = hdQualityUrl;
    this.smallQualityUrlEng = smallQualityUrlEng;
    this.normalQualityUrlEng = normalQualityUrlEng;
    this.hdQualityUrlEng = hdQualityUrlEng;
    this.smallQualityUrlAd = smallQualityUrlAd;
    this.normalQualityUrlAd = normalQualityUrlAd;
    this.hdQualityUrlAd = hdQualityUrlAd;
  }

  @Test
  public void testDeserialize() {

    JsonObject jsonObject = JsonFileReader.readJson(jsonFile);

    ZDFDownloadDTODeserializer target = new ZDFDownloadDTODeserializer();
    DownloadDTO actual = target.deserialize(jsonObject, DownloadDTO.class, null);

    assertThat(actual, notNullValue());
    assertThat(actual.getSubTitleUrl(), equalTo(subtitleUrl));
    assertThat(actual.getUrl(DownloadDTO.LANGUAGE_GERMAN, Qualities.SMALL), equalTo(smallQualityUrl));
    assertThat(actual.getUrl(DownloadDTO.LANGUAGE_GERMAN, Qualities.NORMAL), equalTo(normalQualityUrl));
    assertThat(actual.getUrl(DownloadDTO.LANGUAGE_GERMAN, Qualities.HD), equalTo(hdQualityUrl));
    assertThat(actual.getUrl(DownloadDTO.LANGUAGE_ENGLISH, Qualities.SMALL), equalTo(smallQualityUrlEng));
    assertThat(actual.getUrl(DownloadDTO.LANGUAGE_ENGLISH, Qualities.NORMAL), equalTo(normalQualityUrlEng));
    assertThat(actual.getUrl(DownloadDTO.LANGUAGE_ENGLISH, Qualities.HD), equalTo(hdQualityUrlEng));
    assertThat(actual.getUrl(DownloadDTO.LANGUAGE_GERMAN_AD, Qualities.SMALL), equalTo(smallQualityUrlAd));
    assertThat(actual.getUrl(DownloadDTO.LANGUAGE_GERMAN_AD, Qualities.NORMAL), equalTo(normalQualityUrlAd));
    assertThat(actual.getUrl(DownloadDTO.LANGUAGE_GERMAN_AD, Qualities.HD), equalTo(hdQualityUrlAd));
  }
}
