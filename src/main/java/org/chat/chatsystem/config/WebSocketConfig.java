package org.chat.chatsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${message.size.limit}")
    private int messageSizeLimit;

    @Value("${buffer.size.limit}")
    private int bufferSizeLimit;

    @Value("${time.limit}")
    private int timeLimit;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        // Set maximum text message buffer size to 50MB (50 * 1024 * 1024)
        registration.setMessageSizeLimit(this.messageSizeLimit);
        // Set send buffer size limit to 50MB
        registration.setSendBufferSizeLimit(this.bufferSizeLimit);
        // Set maximum time to send a message frame (optional but recommended for big files)
        registration.setSendTimeLimit(this.timeLimit);
    }
}
