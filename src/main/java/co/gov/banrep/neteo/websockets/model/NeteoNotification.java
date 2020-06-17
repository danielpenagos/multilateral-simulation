package co.gov.banrep.neteo.websockets.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import co.gov.banrep.neteo.model.EntidadFinanciera;

public class NeteoNotification {
	private int[][] acumulacion;
	private int[][] bilateral;
	private int[][] multilateral;
	private List<List<Integer>> caminoMultilateral;
	private Long numTransacciones;
	private List<String> entidades;
	private long milis;
	public NeteoNotification(int[][] acumulacion, int[][] bilateral, Long numTransacciones, List<String> entidades) {
		super();
		this.acumulacion = acumulacion;
		this.bilateral = bilateral;
		this.numTransacciones = numTransacciones;
		this.entidades=entidades;
		
		
	}
	public int[][] getMultilateral() {
		return multilateral;
	}
	public void setMultilateral(int[][] multilateral) {
		this.multilateral = multilateral;
	}
	public List<List<Integer>> getCaminoMultilateral() {
		return caminoMultilateral;
	}
	public void agregarCaminoMultilateral(List<Integer> caminoMultilateral) {
		if(this.caminoMultilateral==null ) {
			this.caminoMultilateral=new ArrayList();
		}
		this.caminoMultilateral.add(caminoMultilateral);
	}
	public int[][] getAcumulacion() {
		return acumulacion;
	}
	public int[][] getBilateral() {
		return bilateral;
	}
	public Long getNumTransacciones() {
		return numTransacciones;
	}
	public List<String> getEntidades() {
		return entidades;
	}
	
	public long getMilis() {
		return milis;
	}
	public void setMilis(long milis) {
		this.milis = milis;
	}
	
	

}
