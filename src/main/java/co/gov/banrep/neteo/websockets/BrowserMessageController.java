package co.gov.banrep.neteo.websockets;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import co.gov.banrep.neteo.websockets.model.WebSocketHelloMessage;
import co.gov.banrep.neteo.websockets.model.WebSocketNotification;

@Controller
@CrossOrigin(origins = "*")
public class BrowserMessageController {


  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public WebSocketNotification notify(WebSocketHelloMessage message) throws Exception {
    Thread.sleep(1000); // simulated delay
    return new WebSocketNotification(message.getName());
  }

}