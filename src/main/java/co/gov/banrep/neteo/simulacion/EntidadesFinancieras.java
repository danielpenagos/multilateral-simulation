/**
Copyright 2020. Banco de la República. dpenagbo@banrep.gov.co
Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the authors and should not be interpreted as representing official policies, either expressed or implied, of the FreeBSD Project.
*/

package co.gov.banrep.neteo.simulacion;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.gov.banrep.neteo.algoritmos.Acumulacion;
import co.gov.banrep.neteo.algoritmos.Bilateral;
import co.gov.banrep.neteo.algoritmos.MatrizUtils;
import co.gov.banrep.neteo.algoritmos.Multilateral;
import co.gov.banrep.neteo.model.EntidadFinanciera;
import co.gov.banrep.neteo.model.NeteoMultilateral;
import co.gov.banrep.neteo.model.Transaccion;
import co.gov.banrep.neteo.websockets.EventNotifier;
import co.gov.banrep.neteo.websockets.model.NeteoNotification;

@Component
public class EntidadesFinancieras {
	private Map<String, Integer> idsEntidades = new HashMap<String, Integer>();
	private Map<Integer,EntidadFinanciera> entidadesCol = new ConcurrentHashMap<Integer,EntidadFinanciera>();
	private List<Transaccion> transacciones = new ArrayList<Transaccion>();
	private boolean estamosEnNeteo = false;
	static Logger logger = LogManager.getLogger(EntidadesFinancieras.class);
	
	private Long transaccionesCreadas=0L;

	@Autowired
	private UmbralesListener listener;
	
	@Autowired
	private Umbrales umbrales;
	
	@Autowired
	private EventNotifier notitier;
	
	private List<String> entidFinancSimuladas = new ArrayList<String>();
	
	@PostConstruct
	public void init() {
		this.listener.setEntidadesFinancieras(this);
		getEntidad("Bancolombia");
		getEntidad("Davivienda");
		getEntidad("BancodeBogota");
		getEntidad("Finandina");
		getEntidad("Banoccidente");
		getEntidad("Tuya");
		getEntidad("Citibank");
		getEntidad("BBVA");
	}
	
	public void reestablecer() {
		idsEntidades = new HashMap<String, Integer>();
		entidadesCol = new ConcurrentHashMap<Integer,EntidadFinanciera>();
		transacciones = new ArrayList<Transaccion>();
		estamosEnNeteo = false;
		transaccionesCreadas=0L;
		entidFinancSimuladas = new ArrayList<String>();
		init();
	}

	public Map<Integer, EntidadFinanciera> getEntidades() {
		return entidadesCol;
	}

	public Integer getEntidad(String entidad) {
		Integer entidadExistenteId = idsEntidades.get(entidad);
		if(entidadExistenteId==null) {
			int newId = idsEntidades.size()+1;
			idsEntidades.put(entidad, newId);
			EntidadFinanciera ef = new EntidadFinanciera(newId, entidad);			
			ef.addListener(listener).addUmbrales(umbrales);
			entidadesCol.put(ef.getId(), ef);
			entidFinancSimuladas.add(entidad);
			return Integer.valueOf(newId);
		}else {
			return entidadExistenteId;
		}
	}

	public Transaccion registrarTransaccion(String issuer, String owner, Integer value) {
		
		Integer idIssuer = getEntidad(issuer);
		Integer idOwner = getEntidad(owner);
		EntidadFinanciera efIssuer = entidadesCol.get(idIssuer);
		EntidadFinanciera efOwner = entidadesCol.get(idOwner);
		Transaccion tx = new Transaccion(idIssuer,idOwner, value);
		transacciones.add(tx);
		transaccionesCreadas++;
		try {
			efIssuer.registrarTransaccion(tx);
			efOwner.registrarTransaccion(tx);
			entidadesCol.put(efIssuer.getId(), efIssuer);
			entidadesCol.put(efOwner.getId(), efOwner);
			return tx;
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	} 

	public void netear() {
		if(!estamosEnNeteo&&transacciones.size()>100) {
			
			estamosEnNeteo=true;
			List<Transaccion> transaccionesANetear = new ArrayList<Transaccion>();
			transaccionesANetear.addAll(transacciones);
			
			int[][]acumulado = acumular(transaccionesANetear);
			int numTxAntesAcum,numTxPostRetiroAcum,numTxPostInclusionAcum=0;
			List<Transaccion> transaccionesAcumuladas = crearTransaccionesDeMatriz(acumulado);
			numTxAntesAcum=transacciones.size();
			removerTransacciones(transaccionesANetear);
			numTxPostRetiroAcum=transacciones.size();
			agregarTransacciones(transaccionesAcumuladas);
			numTxPostInclusionAcum=transacciones.size();
			logger.info("Cantidad de entidades:{}",entidFinancSimuladas.size());
			logger.info("Tamaño despues de acumular: antes:{} , despues de retiro:{}, despues de inclusion:{}",numTxAntesAcum,numTxPostRetiroAcum,numTxPostInclusionAcum);
			
			
			int[][]neteoBilateral = netearBilateral(acumulado);
			int numTxPostRetiroBil,numTxPostInclusionBil=0;
			List<Transaccion> transaccionesNeteoBilateral = crearTransaccionesDeMatriz(neteoBilateral);

			removerTransacciones(transaccionesAcumuladas);
			numTxPostRetiroBil=transacciones.size();
			agregarTransacciones(transaccionesNeteoBilateral);
			numTxPostInclusionBil=transacciones.size();
			logger.info("Tamaño despues de neteo bilateral: despues de retiro:{}, despues de inclusion:{}",numTxPostRetiroBil,numTxPostInclusionBil);
			StringBuffer sb = new StringBuffer("Neteos multilaterales: ");
			
			NeteoNotification notificacion = new NeteoNotification(acumulado, neteoBilateral, transaccionesCreadas, entidFinancSimuladas);  
			
			NeteoMultilateral objNeteoMulti = new NeteoMultilateral(neteoBilateral,transaccionesNeteoBilateral);
			int numEntidades = objNeteoMulti.getMatriz().length;
			long milis = ZonedDateTime.now().toInstant().toEpochMilli();
			for(int entidad=0;entidad<numEntidades;entidad++) {
				
				if(objNeteoMulti!=null) {
					List<Integer> camino = netearMultilateral(objNeteoMulti.getMatriz(),entidad);
					logger.debug("camino para la entidad {} : {}",entidad,camino);
					if(!camino.isEmpty()) {
						objNeteoMulti=realizarNeteoMultilateral(entidad, objNeteoMulti.getTransacciones(), camino,MatrizUtils.valorMenorCamino(objNeteoMulti.getMatriz(), camino));
						sb.append(entidad+1).append(" ");
						notificacion.agregarCaminoMultilateral(camino);
						notificacion.setMultilateral(objNeteoMulti.getMatriz());
					}
				}
			}
			milis = ZonedDateTime.now().toInstant().toEpochMilli()-milis;
			notificacion.setMilis(milis);
			this.notitier.notify(notificacion);
			logger.info("Neteos multilaterales realizados: {}",sb.toString());
			estamosEnNeteo=false;
			logger.info("FIN DEL NETEO");
		}else {
			logger.debug("Hay un neteo en curso.. pasando por alto");
		}
		
	}
	private NeteoMultilateral realizarNeteoMultilateral(int entidad, List<Transaccion> transaccionesAcumuladas, List<Integer> camino,
			int valorMenorCamino) {
		/**
		 * Se extraen todas las transacciones y se meten en un mapa para poder identificarlas por la
		 * relación de deuda x->ledbebe->y
		 */
		Map <String, Transaccion> mapa = new HashMap<String, Transaccion>();		
		for(Transaccion tx : transaccionesAcumuladas) {
			mapa.put(tx.getIssuer()+"->"+tx.getOwner(), tx);
			logger.debug("se agrego al mapa una tx: {}, valor:{}",tx.getIssuer()+"->"+tx.getOwner(), tx.getAmount());
		}
		
		List<Transaccion> txMatrizCompleta = new ArrayList<Transaccion>();
		txMatrizCompleta.addAll(transaccionesAcumuladas);
		
		List<Transaccion> txDeInteres = new ArrayList<Transaccion>();				
		
		/**
		 * Solo se meten en un nuevo arreglo aquellas txs que están en el camino de 
		 * neteo multilateral.
		 */
		for(int i=0;i<camino.size()-1;i++) {
			StringBuffer strCaminoBuscado = new StringBuffer();
			/**
			 * El camino tiene indice base 0. Se le debe agregar una unidad a cada entidad.
			 */
			strCaminoBuscado.append(camino.get(i)+1).append("->").append(camino.get(i+1)+1);
			logger.debug("Buscando tx:{}",strCaminoBuscado.toString());
			
			Transaccion txBuscada = mapa.get(strCaminoBuscado.toString());
			if(txBuscada!=null) {
				txDeInteres.add(txBuscada);
			} 
		}
		
		if(txDeInteres.size()<camino.size()-1) {
			logger.debug("error al intentar hacer el neteo multilateral. No están todas las transacciones. esperado {}, encontrado {}",txDeInteres.size(),camino.size());
			return null;
		}else {
			List<Transaccion> txNuevas = new ArrayList<Transaccion>();
			logger.debug("tamaño de las tx de interes:{}",txDeInteres.size());
			for(Transaccion tx : txDeInteres) {
				logger.debug("Transaccion:i:{},o:{},v:{}",tx.getIssuer(),tx.getOwner(),tx.getAmount());
				int montoDisminuido=tx.getAmount().intValue()-valorMenorCamino;
				if(montoDisminuido>0) {
					Transaccion nuevaTx = new Transaccion(tx.getIssuer().intValue(), tx.getOwner().intValue(), montoDisminuido);
					txNuevas.add(nuevaTx);
				}else {
					logger.debug("transaccion con valor 0.{}, issuer:{}, owner:{}",montoDisminuido,tx.getIssuer(),tx.getOwner());
				}
				
			}
			txMatrizCompleta.removeAll(txDeInteres);
			txMatrizCompleta.addAll(txNuevas);
			removerTransacciones(txDeInteres);
			agregarTransacciones(txNuevas);
			int[][] matrizAcumuladas= acumular(txMatrizCompleta);
			logger.info("multilateral:retiradas {} tx. agregadas {} tx",txDeInteres.size(),txNuevas.size());
			return new NeteoMultilateral(matrizAcumuladas, txMatrizCompleta);
		}		
	}

	private void removerTransacciones(List<Transaccion> transaccionesARemover) {
		transacciones.removeAll(transaccionesARemover);
		for(Map.Entry<Integer, EntidadFinanciera>ef: entidadesCol.entrySet()) {		
			ef.getValue().eliminarTransacciones(transaccionesARemover);
		}
		
	}
	private void agregarTransacciones(List<Transaccion> transaccionesAAgregar) {
		transacciones.addAll(transaccionesAAgregar);
		for(Map.Entry<Integer, EntidadFinanciera>ef: entidadesCol.entrySet()) {		
			ef.getValue().adicionarTransacciones(transaccionesAAgregar);
		}
	}


	private List<Transaccion> crearTransaccionesDeMatriz(int[][] acumulado) {
		List<Transaccion> transaccionesFromMatrix = new ArrayList<Transaccion>();
		for(int i = 0; i<acumulado.length;i++) {
			for(int j=0; j<acumulado.length;j++) {
				if(acumulado[i][j]!=0) {
					transaccionesFromMatrix.add( new Transaccion(i+1,j+1,acumulado[i][j]));
				}
			}
		}
		return transaccionesFromMatrix;
	}

	private int[][] acumular(List<Transaccion> transaccionesANetear) {
		return Acumulacion.calcular(transaccionesANetear, entidadesCol);
	}
	private int[][] netearBilateral(int[][] acumulado) {
		return Bilateral.netear(acumulado);
	}
	private List<Integer> netearMultilateral(int[][] acumulado, int entidad) {
		List<Integer>camino = Multilateral.encontrarCamino(acumulado, entidad);
		if(camino.isEmpty()) {
			logger.debug("No hay neteo multilateral para la entidad en la posicion {}", entidad);
		}
		return camino;
	}

	public Long getTransaccionesCreadas() {
		return transaccionesCreadas;
	}
	
	public List<String> getEntidFinancSimuladas() {
		return entidFinancSimuladas;
	}
	public void addEntidFinancSimuladas(String strEntidad) {
		entidFinancSimuladas.add(strEntidad);
	}

}
