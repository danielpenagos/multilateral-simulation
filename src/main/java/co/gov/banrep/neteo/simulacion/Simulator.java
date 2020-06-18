/**
Copyright 2020. Banco de la República. dpenagbo@banrep.gov.co
Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the authors and should not be interpreted as representing official policies, either expressed or implied, of the FreeBSD Project.
*/

package co.gov.banrep.neteo.simulacion;

import java.util.ArrayList;
import java.util.Random;

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

	public void addTransaction(String issuer, String owner, Integer value) {
		Transaccion tx = entidades.registrarTransaccion(issuer, owner, value);
		logger.debug("No. Tx:{} --Nueva transaccion issuer:{}, owner:{}, value:{}", tx.getId(), issuer, owner, value);
		
	}
	public void addRandomTransaction() {
		if(entidades.getEntidFinancSimuladas().size()>0) {
			ArrayList<String> entidadesGeneracionRandom = new ArrayList<String>();
			entidadesGeneracionRandom.addAll(entidades.getEntidFinancSimuladas());
			/**
			 * Se usa un arreglo nuevo para transaccion aleatoria que se incorpora, justamente
			 * para poder hacer un random de la entidad destino de deuda, sin que vaya a ser la misma
			 * entidad origen de deuda. Otra alternativa era simplemente repetir el proceso validando
			 * que el origen no fuera el destino, pero opté por esta porque es un camino más expedito
			 * y determinístico.
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
