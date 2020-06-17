package co.gov.banrep.neteo.model;

public class Transaccion {
	
	private Integer id=0;
	private Integer issuer = 0;
	private Integer owner = 0;
	private Integer amount = 0;
	private Integer type = 0;
	private Integer idTxCierre =0;
	private boolean activa=true;
	
	public Transaccion(Integer issuer, Integer owner, Integer amount) {
		super();
		this.id=TransactionIdGenerator.getId();
		this.issuer = issuer;
		this.owner = owner;
		this.amount = amount;		
	}

	public Integer getState() {
		return type;
	}

	public void setState(Integer state) {
		this.type = state;
	}

	public Integer getIssuer() {
		return issuer;
	}

	public void setIssuer(Integer issuer) {
		this.issuer = issuer;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}
	

}
