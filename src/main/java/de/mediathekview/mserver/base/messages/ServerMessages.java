package de.mediathekview.mserver.base.messages;

import de.mediathekview.mlib.messages.Message;
import de.mediathekview.mlib.messages.MessageTypes;

/**
 * The server messages.
 */
public enum ServerMessages implements Message
{
    CRAWLER_START("crawlerStart", MessageTypes.INFO),
    CRAWLER_PROGRESS("crawlerProgress", MessageTypes.INFO),
    CRAWLER_END("crawlerEnd", MessageTypes.INFO),
    CRAWLER_ERROR("crawlerError", MessageTypes.ERROR),
    CRAWLER_TIMEOUT("crawlerTimeout",MessageTypes.ERROR),
    SERVER_TIMEOUT("serverTimeout",MessageTypes.FATAL_ERROR),
    SERVER_ERROR("serverError",MessageTypes.FATAL_ERROR);

    private String messageKey;
    private MessageTypes messageType;

    ServerMessages(String aMessageKey, MessageTypes aMessageType)
    {
        messageKey = aMessageKey;
        messageType = aMessageType;
    }

    @Override
    public String getMessageKey()
    {
        return messageKey;
    }

    @Override
    public MessageTypes getMessageType()
    {
        return messageType;
    }
}
