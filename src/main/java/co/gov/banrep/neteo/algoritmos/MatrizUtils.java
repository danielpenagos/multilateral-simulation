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
