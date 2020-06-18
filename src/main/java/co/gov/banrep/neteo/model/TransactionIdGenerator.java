package co.gov.banrep.neteo.model;

import org.springframework.stereotype.Component;

@Component
public class TransactionIdGenerator {
	private static int id = 0;
	
	public static synchronized int getId() {
		return ++id;
	}

	public static void reestablecer() {
		id = 0;
	}

}
