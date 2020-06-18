package co.gov.banrep.neteo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import co.gov.banrep.neteo.simulacion.Umbrales;
import co.gov.banrep.neteo.simulacion.UmbralesListener;

public class EntidadFinanciera {
	
	private Integer id;
	private String nombre;
	private UmbralesListener listener;
	private Umbrales umbrales;
	private List<Transaccion> transaccionesCredito;
	private List<Transaccion> transaccionesDebito;
	
	public EntidadFinanciera(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.transaccionesCredito = new ArrayList<Transaccion>();
		this.transaccionesDebito = new ArrayList<Transaccion>();
	}
	
	public Integer getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}

	public void registrarTransaccion(Transaccion x) throws Exception{
		if(x.getIssuer().equals(id)) {
			transaccionesCredito.add(x);
		}else if(x.getOwner().equals(id)) {
			transaccionesDebito.add(x);
		}
		validarUmbrales(0,x.getId());
	}
	
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
