package co.gov.banrep.neteo.algoritmos;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.gov.banrep.neteo.model.EntidadFinanciera;
import co.gov.banrep.neteo.model.Transaccion;

public class Acumulacion {
	static Logger logger = LogManager.getLogger(Acumulacion.class);
	public static int[][]  calcular(List<Transaccion> transacciones,Map<Integer,EntidadFinanciera> mapa) {
		int[][] arreglo = new int[mapa.size()][];
		for(int i =0; i<mapa.size();i++) {
			arreglo[i]=new int[mapa.size()];
		}
		for(Transaccion tx : transacciones) {
			logger.debug("issuer:{} ; owner: {} ; value: {}",tx.getIssuer().intValue()-1,tx.getOwner().intValue()-1,tx.getAmount().intValue());
			arreglo[tx.getIssuer().intValue()-1][tx.getOwner().intValue()-1]+=tx.getAmount().intValue();
		}
		logger.debug("matriz acumulación:");
		MatrizUtils.imprimir(arreglo);
		return arreglo;
		
	}
	

}
