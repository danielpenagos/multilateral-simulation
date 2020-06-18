/**
Copyright 2020. Banco de la República. dpenagbo@banrep.gov.co
Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the authors and should not be interpreted as representing official policies, either expressed or implied, of the FreeBSD Project.
*/

package co.gov.banrep.neteo.simulacion;

import org.springframework.stereotype.Component;

@Component
public class Umbrales {
	
	private Integer umbralPorPosicion = 2000000;
	private Integer umbralPorDeudas = 2000000;
	private Integer umbralPorAcreencias = 2000000;
	public Integer getUmbralPorPosicion() {
		return umbralPorPosicion;
	}
	public void setUmbralPorPosicion(Integer umbralPorPosicion) {
		this.umbralPorPosicion = umbralPorPosicion;
	}
	public Integer getUmbralPorDeudas() {
		return umbralPorDeudas;
	}
	public void setUmbralPorDeudas(Integer umbralPorDeudas) {
		this.umbralPorDeudas = umbralPorDeudas;
	}
	public Integer getUmbralPorAcreencias() {
		return umbralPorAcreencias;
	}
	public void setUmbralPorAcreencias(Integer umbralPorAcreencias) {
		this.umbralPorAcreencias = umbralPorAcreencias;
	}

}
