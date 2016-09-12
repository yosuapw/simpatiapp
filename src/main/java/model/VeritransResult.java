package model;

import java.io.Serializable;

public class VeritransResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orderId;
	private String statusCode;
	private String transactionStatus;

	public VeritransResult() {
		super();
	}
	
	public VeritransResult(String orderId, String statusCode,
			String transactionStatus) {
		super();
		this.orderId = orderId;
		this.statusCode = statusCode;
		this.transactionStatus = transactionStatus;
	}
	
	public String getOrderId() {
		return orderId;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getTransactionStatus() {
		return transactionStatus;
	}
	
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

}
