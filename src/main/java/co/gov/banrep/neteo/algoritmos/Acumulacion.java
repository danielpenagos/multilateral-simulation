/**
Copyright 2020. Banco de la República. dpenagbo@banrep.gov.co
Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the authors and should not be interpreted as representing official policies, either expressed or implied, of the FreeBSD Project.
*/

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
