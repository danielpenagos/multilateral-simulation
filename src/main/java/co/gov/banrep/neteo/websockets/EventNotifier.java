package co.gov.banrep.neteo.websockets;

import javax.annotation.PreDestroy;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Component;

import co.gov.banrep.neteo.websockets.model.NeteoNotification;
import co.gov.banrep.neteo.websockets.model.WebSocketHelloMessage;

@Component
public class EventNotifier implements ApplicationListener<ContextRefreshedEvent> {
    private final MessageSendingOperations<String> messagingTemplate;

    @Autowired
    public EventNotifier( MessageSendingOperations<String> messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        
    }

    public void notify(String event) {
        messagingTemplate.convertAndSend("/topic/greetings", new WebSocketHelloMessage("Hello, " + event+ "!"));
    }
    public void notify(JSONArray event) {
        messagingTemplate.convertAndSend("/topic/greetings", event);
    }    

    @PreDestroy
    private void stopNotifier() {
    }

	public void notify(NeteoNotification notificacion) {
		messagingTemplate.convertAndSend("/topic/greetings", notificacion);		
	}
}
