package co.gov.banrep.neteo.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.gov.banrep.neteo.scheduler.ModelUpdateService;
import co.gov.banrep.neteo.simulacion.Simulator;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class InformacionSimulacion {
	
	Logger logger = LogManager.getLogger(InformacionSimulacion.class);
	
	@Autowired
	private Simulator simulator;
	
	@Autowired
	private ModelUpdateService simuladorAutomatico;
	
	@RequestMapping("/nuevasTransacciones")
	public void enviarTransacciones() {
		simulator.incluirTransacciones();
		logger.info("se incluyen nuevas transacciones manualmente");
	}
	
	@RequestMapping("/iniciarSimulacionAutomatica")
	public void iniciarSimulacionAutomatica() {
		simuladorAutomatico.setAutogeneracion(true);
		logger.info("se inicia la simuacion automatica");
	}
	
	@RequestMapping("/detenerSimulacionAutomatica")
	public void detenerSimulacionAutomatica() {
		simuladorAutomatico.setAutogeneracion(false);
		logger.info("se detiene la simuacion automatica");
	}
	
	@RequestMapping("/consultarSimulacionAutomatica")
	public boolean consultarSimulacionAutomatica() {
		logger.info("se retorna la configuracion de simulacion automatica {}",simuladorAutomatico.isAutogeneracion());
		return simuladorAutomatico.isAutogeneracion();
	}
	
	@RequestMapping("/nuevaSimulacion")
	public void nuevaSimulacion() {
		logger.info("se reestablece a una nueva simulacion ");
		simuladorAutomatico.setAutogeneracion(false);
		simulator.reestablecerSimulacion();
		simuladorAutomatico.setAutogeneracion(true);
	}

}
