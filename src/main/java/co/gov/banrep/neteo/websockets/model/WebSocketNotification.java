package co.gov.banrep.neteo.websockets.model;

import org.springframework.web.util.HtmlUtils;

public class WebSocketNotification {
	private String content;

	  public WebSocketNotification() {
	  }
	  
	  public WebSocketNotification(WebSocketHelloMessage content) {
		    this.content = "Bienvenido, " + HtmlUtils.htmlEscape(content.getName()) + "!";
	  }

	  public WebSocketNotification(String content) {
	    this.content = "Notificacion, " + HtmlUtils.htmlEscape(content) + "!";
	  }

	  public String getContent() {
	    return content;
	  }

}
