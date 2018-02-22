package AJSServer;

import javax.xml.ws.Endpoint;



public class Server {

	public static void main(String[] args) {
		System.out.println("Server is running");
		Endpoint.publish("http://localhost:8888/ajs", new AjsService());
	}

}
