package it.eng.idra.beans.evaluation;

public class DatasetEval {
	
	private String datasetId;
	
	private int datasetLevel;
	private int semanticLevel;
	private int internationalityLevel;
	private int formatLevel;
	private int resourceAvgLevel;
	private int updateLevel;
	
	public DatasetEval() {
		this.datasetLevel = 0;
		this.semanticLevel = 0;
		this.internationalityLevel = 0;
		this.formatLevel = 0;
		this.resourceAvgLevel = 0;
		this.updateLevel = 0;
	}
	
	public DatasetEval(EvaluationModel evaluation) {
		this.datasetId = evaluation.getId().getId();
		this.datasetLevel = evaluation.getDatasetLevel();
		this.semanticLevel = evaluation.getSemanticLevel();
		this.internationalityLevel = evaluation.getInternationalityLevel();
		this.formatLevel = evaluation.getFormatLevel();
		this.resourceAvgLevel = evaluation.getResourceAvgLevel();
		this.updateLevel = evaluation.getUpdateLevel();
	}

	public DatasetEval(String datasetId,int datasetLevel, int semanticLevel, int internationalityLevel, int formatLevel,
			int resourceAvgLevel, int updateLevel) {
		this.datasetId = datasetId;
		this.datasetLevel = datasetLevel;
		this.semanticLevel = semanticLevel;
		this.internationalityLevel = internationalityLevel;
		this.formatLevel = formatLevel;
		this.resourceAvgLevel = resourceAvgLevel;
		this.updateLevel = updateLevel;
	}

	public String getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}
	
	public int getDatasetcLevel() {
		return datasetLevel;
	}

	public void setDatasetLevel(int datasetLevel) {
		this.datasetLevel = datasetLevel;
	}

	public int getSemanticLevel() {
		return semanticLevel;
	}

	public void setSemanticLevel(int semanticLevel) {
		this.semanticLevel = semanticLevel;
	}

	public int getInternationalityLevel() {
		return internationalityLevel;
	}

	public void setInternationalityLevel(int internationalityLevel) {
		this.internationalityLevel = internationalityLevel;
	}

	public int getFormatLevel() {
		return formatLevel;
	}

	public void setFormatLevel(int formatLevel) {
		this.formatLevel = formatLevel;
	}

	public int getResourceAvgLevel() {
		return resourceAvgLevel;
	}

	public void setResourceAvgLevel(int resourceAvgLevel) {
		this.resourceAvgLevel = resourceAvgLevel;
	}

	public int getUpdateLevel() {
		return updateLevel;
	}

	public void setUpdateLevel(int updateLevel) {
		this.updateLevel = updateLevel;
	}
	
	

}
