package it.eng.idra.beans.evaluation;

public class ResourceEval {
	
	private String datasetId;
	
	private int resourceLevel;
	private int semanticLevel;
	private int fiveStarLevel;	
	
	public ResourceEval() {}

	public ResourceEval(String datasetId, ResourceEvaluationModel evaluation) {
		this.datasetId = datasetId;
		this.resourceLevel = evaluation.getResourceLevel();
		this.semanticLevel = evaluation.getSemanticLevel();
		this.fiveStarLevel = evaluation.getFiveStarLevel();
	}

	public ResourceEval(String datasetId, int resourceLevel, int semanticLevel, int fiveStarLevel) {
		this.datasetId = datasetId;
		this.resourceLevel = resourceLevel;
		this.semanticLevel = semanticLevel;
		this.fiveStarLevel = fiveStarLevel;
	}

	public String getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public int getResourceLevel() {
		return resourceLevel;
	}

	public void setResourceLevel(int resourceLevel) {
		this.resourceLevel = resourceLevel;
	}

	public int getSemanticLevel() {
		return semanticLevel;
	}

	public void setSemanticLevel(int semanticLevel) {
		this.semanticLevel = semanticLevel;
	}

	public int getFiveStarLevel() {
		return fiveStarLevel;
	}

	public void setFiveStarLevel(int fiveStarLevel) {
		this.fiveStarLevel = fiveStarLevel;
	}

	
}
