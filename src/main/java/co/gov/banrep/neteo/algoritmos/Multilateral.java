/**
Copyright 2020. Banco de la República. dpenagbo@banrep.gov.co
Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the authors and should not be interpreted as representing official policies, either expressed or implied, of the FreeBSD Project.
*/

package co.gov.banrep.neteo.algoritmos;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
