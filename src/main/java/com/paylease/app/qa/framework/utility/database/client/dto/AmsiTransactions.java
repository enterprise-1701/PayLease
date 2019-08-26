package com.paylease.app.qa.framework.utility.database.client.dto;

import java.sql.Date;


public class AmsiTransactions
{

	private int transId;
	private int pmId;
	private int evolutionReference;
	private int esiteBatchNo;
	private String esiteBankbookId;
	private int clientJournalNo;
	private String addPaymentResult;
	private Date addPaymentDate;
	private String processingStatus;
	private short attempts;
	private Date createdOn;
	private String reviewed;


	//******************
	//Getter Methods
	//******************


	public int getTransId()
	{ 
		return transId; 
	}
	public int getPmId()
	{ 
		return pmId; 
	}
	public int getEvolutionReference()
	{ 
		return evolutionReference; 
	}
	public int getEsiteBatchNo()
	{ 
		return esiteBatchNo; 
	}
	public String getEsiteBankbookId()
	{ 
		return esiteBankbookId; 
	}
	public int getClientJournalNo()
	{ 
		return clientJournalNo; 
	}
	public String getAddPaymentResult()
	{ 
		return addPaymentResult; 
	}
	public Date getAddPaymentDate()
	{ 
		return addPaymentDate; 
	}
	public String getProcessingStatus()
	{ 
		return processingStatus; 
	}
	public short getAttempts()
	{ 
		return attempts; 
	}
	public Date getCreatedOn()
	{ 
		return createdOn; 
	}
	public String getReviewed()
	{ 
		return reviewed; 
	}


	//******************
	//Setter Methods
	//******************


	public void setTransId(int transId)
	{ 
		this.transId = transId; 
	}
	public void setPmId(int pmId)
	{ 
		this.pmId = pmId; 
	}
	public void setEvolutionReference(int evolutionReference)
	{ 
		this.evolutionReference = evolutionReference; 
	}
	public void setEsiteBatchNo(int esiteBatchNo)
	{ 
		this.esiteBatchNo = esiteBatchNo; 
	}
	public void setEsiteBankbookId(String esiteBankbookId)
	{ 
		this.esiteBankbookId = esiteBankbookId; 
	}
	public void setClientJournalNo(int clientJournalNo)
	{ 
		this.clientJournalNo = clientJournalNo; 
	}
	public void setAddPaymentResult(String addPaymentResult)
	{ 
		this.addPaymentResult = addPaymentResult; 
	}
	public void setAddPaymentDate(Date addPaymentDate)
	{ 
		this.addPaymentDate = addPaymentDate; 
	}
	public void setProcessingStatus(String processingStatus)
	{ 
		this.processingStatus = processingStatus; 
	}
	public void setAttempts(short attempts)
	{ 
		this.attempts = attempts; 
	}
	public void setCreatedOn(Date createdOn)
	{ 
		this.createdOn = createdOn; 
	}
	public void setReviewed(String reviewed)
	{ 
		this.reviewed = reviewed; 
	}

}