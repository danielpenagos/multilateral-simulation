/**
Copyright 2020. Banco de la República. dpenagbo@banrep.gov.co
Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the authors and should not be interpreted as representing official policies, either expressed or implied, of the FreeBSD Project.
*/

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
