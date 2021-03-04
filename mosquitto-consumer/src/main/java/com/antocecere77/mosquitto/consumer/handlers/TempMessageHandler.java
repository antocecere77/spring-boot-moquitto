package com.antocecere77.mosquitto.consumer.handlers;

import com.antocecere77.mosquitto.consumer.controller.model.TempMessage;
import com.antocecere77.mosquitto.consumer.statistics.TempStatistics;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TempMessageHandler implements MessageHandler {
    private static final Logger LOG = LoggerFactory.getLogger(TempMessageHandler.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TempStatistics tempStats;

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        try {
            LOG.info("Received Message: " + message.getPayload().toString());

            TempMessage tempMessage = mapper.readerFor(TempMessage.class).readValue(message.getPayload().toString());
            tempStats.addTemp(tempMessage.getTemp());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
