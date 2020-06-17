package co.gov.banrep.neteo.algoritmos;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.gov.banrep.neteo.simulacion.EntidadesFinancieras;

public class Multilateral {
	
	static Logger logger = LogManager.getLogger(Multilateral.class);

	
	public static List<Integer> encontrarCamino(int[][] matriz, int a) {
		MatrizUtils.imprimir(matriz);
		if(tieneValor(matriz,a,true)&&tieneValor(matriz,a,false)) {
			
			List<Integer> caminoEncontrado = encontrarCaminos(matriz, new ArrayList<Integer>() , a, a);
			if(caminoEncontrado.size()>0) {
				List<Integer> caminoRetorno = new ArrayList<Integer>();
				caminoRetorno.add(a);
				caminoRetorno.addAll(caminoEncontrado);
				logger.info("Camino encontrado:{}",caminoRetorno);
				return caminoRetorno;
			}else {
				logger.debug("Camino vacio:{}",caminoEncontrado);
				return caminoEncontrado;
			}
			
		}else {
			return new ArrayList<Integer>();
		}
	}
	/**
	 * A partir de una matriz de adyacencia retorna el camino para recorrer desde el punto inicio hasta el punto objetivo.
	 * Es un algoritmo recursivo, que va recorriendo los puntos posibles (valor inicio va cambiando) hasta encontrar el 
	 * punto objetivo. Si dentro de la recursividad encuentra el valor (primer for), retorna y se va propagando a lo largo de
	 * la recursividad. Si la recursividad no encuentra un valor, se propaga el retorno de una arreglo vacío.
	 * @param matriz
	 * @param camino
	 * @param entidadInicio
	 * @param objetivo
	 * @return
	 */
	private static List<Integer> encontrarCaminos(int[][]matriz, List<Integer> camino, int entidadInicio, int objetivo) {
		List<Integer> posiblesCaminos = new ArrayList<Integer>();
		logger.debug("encontrandoCamino: camino.size: {} , inicio:{}, fin:{}",camino.size(),entidadInicio,objetivo);
		StringBuffer sb = new StringBuffer();
		for(int owner = 0; owner<matriz.length;owner++) {
			
			if(matriz[entidadInicio][owner]!=0) {
				if(owner==objetivo) {
					camino.add(objetivo);
					//camino.add(entidadInicio);
					logger.debug("Se encontro el camino: {}",objetivo);
					return camino;
				}else {
					posiblesCaminos.add(owner);
					sb.append(entidadInicio+"->"+owner);
					sb.append(" ");
				}
			}
		}	
		logger.debug("posibles caminos. {}",sb.toString());
		
		for(int i=0; i<posiblesCaminos.size();i++) {
			Integer nodoIncremento = posiblesCaminos.get(i);
			
			if(camino.contains(nodoIncremento)) {
				logger.debug("condicion de ruptura, el nodo {} , ya existe en el camino {}",nodoIncremento,camino);
				return new ArrayList<Integer>();
			}
			
			List <Integer> caminoIncrementado =new ArrayList<Integer>();
			caminoIncrementado.addAll(camino);
			
			
			caminoIncrementado.add(nodoIncremento);
			List<Integer> caminoEncontrado = encontrarCaminos(matriz, caminoIncrementado,nodoIncremento.intValue(),objetivo);
			if(caminoEncontrado.size()>0) {
				return caminoEncontrado;
			}
		}
		return new ArrayList<Integer>();
	}
	private static boolean tieneValor(int[][] matriz, int filaOColumna, boolean esFila) {
		if(esFila) {
			for(int i=0; i<matriz.length; i++) {
				if(matriz[filaOColumna][i]>0)return true;
			}
		}else {
			for(int i=0; i<matriz.length; i++) {
				if(matriz[i][filaOColumna]>0)return true;
			}
		}
		return false;
	}

}
