package com.janaldous.sponsorshipwebcrawler;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.messaging.MessageChannel;

import com.janaldous.sponsorshipwebcrawler.service.domain.WebCrawlerRequest;

@Configuration
public class IntegrationConfig {

	
	@Bean
	public AbstractMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
		messageListenerContainer.setQueueNames("sponsorship.webCrawlerRequest");
		return messageListenerContainer;
	}
	
	@Bean
	public AmqpInboundChannelAdapter inboundChannelAdapter(AbstractMessageListenerContainer messageListenerContainer) {
		AmqpInboundChannelAdapter adapter = new AmqpInboundChannelAdapter(messageListenerContainer);
		adapter.setOutputChannelName("fromRabbit");
		return adapter;
	}
	
	@Bean
	public MessageChannel fromRabbit() {
		return new DirectChannel();
	}
	
	@Bean
	@Transformer(inputChannel = "fromRabbit", outputChannel = "webCrawlerRequest")
	public JsonToObjectTransformer jsonToObjectTransformer() {
		return new JsonToObjectTransformer(WebCrawlerRequest.class);
	}
	
	@Bean
	public MessageChannel webCrawlerRequest() {
		return new DirectChannel();
	}
	
}
