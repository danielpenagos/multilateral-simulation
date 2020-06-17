package co.gov.banrep.neteo.websockets.model;

public class WebSocketHelloMessage {
	private String name;

	  public WebSocketHelloMessage() {
	  }

	  public WebSocketHelloMessage(String name) {
	    this.name = name;
	  }

	  public String getName() {
	    return name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }

}
