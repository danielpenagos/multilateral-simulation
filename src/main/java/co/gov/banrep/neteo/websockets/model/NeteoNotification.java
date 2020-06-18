/**
Copyright 2020. Banco de la República. dpenagbo@banrep.gov.co
Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the authors and should not be interpreted as representing official policies, either expressed or implied, of the FreeBSD Project.
*/

package co.gov.banrep.neteo.websockets.model;

import java.util.ArrayList;
import java.util.List;

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
