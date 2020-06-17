package co.gov.banrep.neteo.simulacion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import co.gov.banrep.neteo.model.EntidadFinanciera;

@Component
public class UmbralesListener {
	
	Logger logger = LogManager.getLogger(UmbralesListener.class);
	private EntidadesFinancieras entidadesFinancieras;
	public void onUmbralPorDeudasSuperado(EntidadFinanciera ef) {
		/*
		logger.info("Umbral por Deudas superado {}. Valor {}", ef.getNombre(), ef.getValorDebito());
		
		for(String s : this.entidadesFinancieras.getRelaciones().keySet()) {
			logger.info("Relacion de deuda :{} ; valor {}", s, this.entidadesFinancieras.getRelaciones().get(s).getValor());	
		}
		*/
		
	}
	public void onUmbralPorAcreenciasSuperado(EntidadFinanciera ef, Integer valor, Integer inicioVentana, Integer finVentana) {
		//logger.info("Umbral por Acreencias superado {}. Valor {} . Ventana=txInicio:{},txFin:{}", ef.getNombre(), valor, inicioVentana, finVentana);
		entidadesFinancieras.netear();
	}
	public void onUmbralPorPosicionSuperado(EntidadFinanciera ef) {
		//logger.info("Umbral por Posicion superado {}. Valor {}", ef.getNombre(), ef.getPosicion());		
		
	}
	public void setEntidadesFinancieras(EntidadesFinancieras entidadesFinancieras) {
		this.entidadesFinancieras=entidadesFinancieras;
		
	}

}
