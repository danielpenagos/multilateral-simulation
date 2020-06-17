package co.gov.banrep.neteo.simulacion;

import java.util.ArrayList;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.gov.banrep.neteo.model.Transaccion;
import co.gov.banrep.neteo.model.TransactionIdGenerator;

@Component
public class Simulator {
	
	Logger logger = LogManager.getLogger(Simulator.class);
	@Autowired
	private EntidadesFinancieras entidades;
	
	

	public Simulator() {
		super();
		
	}
	@PostConstruct
	public void init() {
		
		
	}
	
	

	public void addTransaction(String issuer, String owner, Integer value) {
		Transaccion tx = entidades.registrarTransaccion(issuer, owner, value);
		//transacciones.add(tx);
		logger.debug("No. Tx:{} --Nueva transaccion issuer:{}, owner:{}, value:{}", tx.getId(), issuer, owner, value);
		
	}
	public void addRandomTransaction() {
		if(entidades.getEntidFinancSimuladas().size()>0) {
			ArrayList<String> entidadesGeneracionRandom = new ArrayList<String>();
			entidadesGeneracionRandom.addAll(entidades.getEntidFinancSimuladas());
			/**
			 * 
			 */
			Random rand = new Random();
			String issuer =entidadesGeneracionRandom.get(rand.nextInt(entidadesGeneracionRandom.size()));
			entidadesGeneracionRandom.remove(issuer);
			String owner = entidadesGeneracionRandom.get(rand.nextInt(entidadesGeneracionRandom.size()));
			Integer value = 100*rand.nextInt(1000);
			addTransaction(issuer, owner, value);
		}
	}
	public void incluirTransacciones() {
		logger.info("incluyendo 100 transacciones nuevas");
		for(int i=0; i<100; i++) {
			addRandomTransaction();
		}
		logger.info("100 transacciones nuevas incluidas. Total: {}",entidades.getTransaccionesCreadas());
		
	}
	public void reestablecerSimulacion() {
		this.entidades.reestablecer();		
		TransactionIdGenerator.reestablecer();
	}
}
