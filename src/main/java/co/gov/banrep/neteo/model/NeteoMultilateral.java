package co.gov.banrep.neteo.model;

import java.util.List;

public class NeteoMultilateral {
	private int[][] matriz;
	List<Transaccion> transacciones;
	public NeteoMultilateral(int[][] matriz, List<Transaccion> transacciones) {
		super();
		this.matriz = matriz;
		this.transacciones = transacciones;
	}
	public int[][] getMatriz() {
		return matriz;
	}
	public List<Transaccion> getTransacciones() {
		return transacciones;
	}

}
