package com.wallet.pubsub;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Qualifier;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Slf4j
public class PubSubReceiver {

    @Bean
    public MessageChannel myInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
            MessageChannel inputChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "payment");
        adapter.setOutputChannel(inputChannel);

        return adapter;
    }


    @ServiceActivator(inputChannel = "payment-chanel")
    public void messageReceiver(String payload) {
        log.info("Message arrived! Payload: " + payload);

    }

}
