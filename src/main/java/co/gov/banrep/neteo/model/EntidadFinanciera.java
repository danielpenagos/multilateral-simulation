package co.gov.banrep.neteo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import co.gov.banrep.neteo.simulacion.Umbrales;
import co.gov.banrep.neteo.simulacion.UmbralesListener;

public class EntidadFinanciera {
	
	private Integer id;
	private String nombre;
	//private Long valorCredito;
	//private Long valorDebito;
	//private Long posicion;
	private UmbralesListener listener;
	private Umbrales umbrales;
	private List<Transaccion> transaccionesCredito;
	private List<Transaccion> transaccionesDebito;
	
	public EntidadFinanciera(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
		//this.valorCredito=0L;
		//this.valorDebito=0L;
		//this.posicion=0L;
		this.transaccionesCredito = new ArrayList<Transaccion>();
		this.transaccionesDebito = new ArrayList<Transaccion>();
		
	}
	
	public Integer getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	/*
	public Long getValorCredito(int inicioVentana, int finVentana) {
		//return valorCredito;
		int valorCredito=0;
		for(Transaccion x: transaccionesCredito) {
			if(inicioVentana<=x.getId() && x.getId()<=finVentana) {
				valorCredito+=x.getAmount();
			}
		}
		return Long.valueOf(valorCredito);
	}
	public Long getValorDebito(int inicioVentana, int finVentana) {
		//return valorDebito;
		int valorDebito=0;
		for(Transaccion x: transaccionesDebito) {
			if(inicioVentana<=x.getId() && x.getId()<=finVentana) {
				valorDebito+=x.getAmount();
			}
		}
		return Long.valueOf(valorDebito);
	}
	*/
	public void registrarTransaccion(Transaccion x) throws Exception{
		if(x.getIssuer().equals(id)) {
			transaccionesCredito.add(x);
		}else if(x.getOwner().equals(id)) {
			transaccionesDebito.add(x);
		}
		validarUmbrales(0,x.getId());
		
		/*
		if(id.equals(x.getIssuer()) && id.equals(x.getOwner())) {
			this.valorCredito-= x.getAmount();
			this.valorDebito-=x.getAmount();
			return;
		}else if(x.getIssuer().equals(id)){
			valorCredito+=x.getAmount();
			if(valorCredito>umbrales.getUmbralPorAcreencias()) {
				listener.onUmbralPorAcreenciasSuperado(this);
			}
		}else if(x.getOwner().equals(id)) {
			valorDebito+=x.getAmount();
			if(valorCredito>umbrales.getUmbralPorDeudas()) {
				listener.onUmbralPorDeudasSuperado(this);
			}
		}else if(!x.getIssuer().equals(id) && !x.getOwner().equals(id)) {
			throw new Exception("la entidad no esta relacionada con la transaccion");
		}
		
		if(getPosicion()>umbrales.getUmbralPorPosicion()) {
			listener.onUmbralPorPosicionSuperado(this);
		}
		*/
	}
	public void eliminarTransaccion(Transaccion x) throws Exception{
		/*
		if(x.getIssuer().equals(id)){
			valorCredito-=x.getAmount();
		}else if(x.getOwner().equals(id)) {
			valorDebito-=x.getAmount();
		}else if(!x.getIssuer().equals(id) && !x.getOwner().equals(id)) {
			throw new Exception("la entidad no esta relacionada con la transaccion");
		}
		*/
	}
	/*
	public void disminuirDebito(int debito) {
		valorDebito-=debito;
	}
	public void disminuirCredito(int credito) {
		valorCredito-=credito;
	}
	public Long getPosicion() {
		posicion = valorCredito - valorDebito;
		return posicion;
	}
	*/

	public EntidadFinanciera addListener(UmbralesListener listener) {
		this.listener = listener;
		return this;
		
	}

	public EntidadFinanciera addUmbrales(Umbrales umbrales) {
		this.umbrales=umbrales;
		return this;
		
	}

	public void eliminarTransacciones(List<Transaccion> transaccionesARemover) {
		transaccionesDebito.removeAll(transaccionesARemover.stream().filter(item -> item.getOwner().equals(id)).collect(Collectors.toList()));
		transaccionesCredito.removeAll(transaccionesARemover.stream().filter(item -> item.getIssuer().equals(id)).collect(Collectors.toList()));
	}
	public void adicionarTransacciones(List<Transaccion> transaccionesAAgregar) {
		transaccionesDebito.addAll(transaccionesAAgregar.stream().filter(item -> item.getOwner().equals(id)).collect(Collectors.toList()));
		transaccionesCredito.addAll(transaccionesAAgregar.stream().filter(item -> item.getIssuer().equals(id)).collect(Collectors.toList()));
	}
	public void validarUmbrales(Integer inicioVentana, Integer finVentana) {
		
		Integer valorDebito =transaccionesDebito.stream().filter(x -> inicioVentana<=x.getId()&& x.getId()<finVentana ).map(x -> x.getAmount()).reduce(0, (a, b) -> a + b);
		Integer valorCredito =transaccionesCredito.stream().filter(x -> inicioVentana<=x.getId()&& x.getId()<finVentana ).map(x -> x.getAmount()).reduce(0, (a, b) -> a + b);
		
		if(valorCredito>umbrales.getUmbralPorAcreencias()) {
			listener.onUmbralPorAcreenciasSuperado(this,valorCredito, inicioVentana,finVentana);
		}
		if(valorDebito>umbrales.getUmbralPorDeudas()) {
			listener.onUmbralPorDeudasSuperado(this);
		}
		if(valorDebito-valorCredito>umbrales.getUmbralPorPosicion()) {
			listener.onUmbralPorPosicionSuperado(this);
		}
		
		
		
	}
	

}
