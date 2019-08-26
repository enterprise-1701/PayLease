package com.paylease.app.qa.framework.utility.database.client.dto;

import java.sql.Date;


public class ExternalBatchesDto
{

	private long id;
	private int depositId;
	private String status;
	private String externalId;
	private Date createdAt;
	private Date updatedAt;
	private Date closedAt;
	private Date deletedAt;


	//******************
	//Getter Methods
	//******************


	public long getId()
	{ 
		return id; 
	}
	public int getDepositId()
	{ 
		return depositId; 
	}
	public String getStatus()
	{ 
		return status; 
	}
	public String getExternalId()
	{ 
		return externalId; 
	}
	public Date getCreatedAt()
	{ 
		return createdAt; 
	}
	public Date getUpdatedAt()
	{ 
		return updatedAt; 
	}
	public Date getClosedAt()
	{ 
		return closedAt; 
	}
	public Date getDeletedAt()
	{ 
		return deletedAt; 
	}


	//******************
	//Setter Methods
	//******************


	public void setId(long id)
	{ 
		this.id = id; 
	}
	public void setDepositId(int depositId)
	{ 
		this.depositId = depositId; 
	}
	public void setStatus(String status)
	{ 
		this.status = status; 
	}
	public void setExternalId(String externalId)
	{ 
		this.externalId = externalId; 
	}
	public void setCreatedAt(Date createdAt)
	{ 
		this.createdAt = createdAt; 
	}
	public void setUpdatedAt(Date updatedAt)
	{ 
		this.updatedAt = updatedAt; 
	}
	public void setClosedAt(Date closedAt)
	{ 
		this.closedAt = closedAt; 
	}
	public void setDeletedAt(Date deletedAt)
	{ 
		this.deletedAt = deletedAt; 
	}

}