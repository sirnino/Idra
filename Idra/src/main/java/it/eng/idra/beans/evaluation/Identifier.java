package it.eng.idra.beans.evaluation;

public class Identifier {
	
	private String id;
	private String nodeId;
	private long federatorId;	
	
	/* ------- Constructors ------- */
	public Identifier (){}
	
	public Identifier (String id) {
		this.id = id;
	}
	
	public Identifier (String id, String nodeId) {
		this.id = id;
		this.nodeId = nodeId;
	}
	
	public Identifier (String id, String nodeId,long federatorId) {
		this.id = id;
		this.nodeId = nodeId;
		this.federatorId = federatorId;
	}
	/* ------- Get & Set ------- */

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
			
	public long getFederatorId() {
		return federatorId;
	}

	public void setFederatorId(long federatorId) {
		this.federatorId = federatorId;
	}

	public String toString() {
		return "id: "+id+" nodeId: "+ nodeId+" federatorId: "+federatorId;
	}	
	
	/* ------- Methods ------- */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (federatorId ^ (federatorId >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Identifier other = (Identifier) obj;
		if (federatorId != other.federatorId)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nodeId == null) {
			if (other.nodeId != null)
				return false;
		} else if (!nodeId.equals(other.nodeId))
			return false;
		return true;
	}
	
}
