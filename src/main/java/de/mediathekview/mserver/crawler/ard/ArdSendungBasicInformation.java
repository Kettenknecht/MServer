package de.mediathekview.mserver.crawler.ard;

import de.mediathekview.mserver.crawler.basic.CrawlerUrlDTO;

/**
 * Basic information about ARD Sendungen.
 */
public class ArdSendungBasicInformation extends CrawlerUrlDTO
{
    private String sendezeitAsText;

    public ArdSendungBasicInformation(final String aUrl, final String aSendezeitAsText)
    {
        super(aUrl);
        sendezeitAsText = aSendezeitAsText;
    }

    public String getSendezeitAsText() {
        return sendezeitAsText;
    }
}