package co.gov.banrep.neteo.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.gov.banrep.neteo.simulacion.Simulator;

@Component
public class ModelUpdateService {
	Logger logger = LogManager.getLogger(ModelUpdateService.class);
	
	@Autowired
	private Simulator simulator;
	
	private boolean autogeneracion=true;

	public void executeUpdateJob() {
		if(autogeneracion) {
			simulator.incluirTransacciones();
		}
	}

	public boolean isAutogeneracion() {
		return autogeneracion;
	}

	public void setAutogeneracion(boolean autogeneracion) {
		this.autogeneracion = autogeneracion;
	}
}
