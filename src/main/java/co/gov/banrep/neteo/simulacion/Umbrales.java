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
