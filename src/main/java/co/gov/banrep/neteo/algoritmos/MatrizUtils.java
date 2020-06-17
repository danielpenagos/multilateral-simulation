package co.gov.banrep.neteo.algoritmos;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MatrizUtils {
	static Logger logger = LogManager.getLogger(MatrizUtils.class);
	public static void imprimir(int[][] matriz) {
		for(int i=0;i<matriz.length;i++) {
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for(int j=0;j<matriz.length;j++) {
				sb.append(StringUtils.leftPad(String.valueOf(matriz[i][j]), 7," "));
				sb.append(" ");
			}
			logger.debug("["+sb.toString()+"]");
		}
	}
	
	public static List<Integer> obtenerCamino(List<Integer> camino) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		List<Integer> caminoInt = new ArrayList<Integer>();
		for(int i=0;i<camino.size();i++) {
			sb.append(camino.get(i));
			sb.append(" ");
			caminoInt.add(camino.get(i));
		}
		sb.append("]");
		logger.info("Camino: {}",sb.toString());
		return caminoInt;
	}

	public static int valorMenorCamino(int[][] acumulado, List<Integer> camino) {
		
		int menorValor=0;
		for(int i=0;i<camino.size()-1;i++) {
			StringBuffer sb = new StringBuffer();
			if(i==0) {
				menorValor=acumulado[camino.get(i)][camino.get(i+1)];
			}
			sb.append("| {").append(camino.get(i)).append("}");
			sb.append(" -> {").append(camino.get(i+1)).append("}");
			sb.append(" : {").append(acumulado[camino.get(i)][camino.get(i+1)]).append("} |");
			logger.debug("Segmento {}",sb.toString());
			if(menorValor>acumulado[camino.get(i)][camino.get(i+1)]) {
				menorValor = acumulado[camino.get(i)][camino.get(i+1)];
			}
		}
		logger.info("Menor valor de segmento: {}",menorValor);
		return menorValor;
		
		
	}

}
