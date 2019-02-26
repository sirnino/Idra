package it.eng.idra.beans.evaluation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EvaluationModel {
	private Identifier id;
	
	private String title;
	private int datasetLevel;
	
	private int semanticLevel;
	private int internationalityLevel;
	private int formatLevel;
	private int resourceAvgLevel;
	private int updateLevel;
	private Date lastEdit;
	private String updateFrequency;
	
	
	private List <ResourceEvaluationModel> resourceEvaluation;
	private List<EvaluationIndex> detail;

	private int strikes;
	private boolean isNew;
	
	/* ------- Constructors ------- */
	public EvaluationModel() {}
	
	public EvaluationModel(Identifier id, String title,int datasetLevel, int semanticLevel, int internationalityLevel,int formatLevel,
			int resourceAvgLevel, int updateLevel,Date lastEdit, String updateFrequency, List<EvaluationIndex> detail) {
		this.id = id;
		this.title = title;
		this.datasetLevel = datasetLevel;
		this.semanticLevel = semanticLevel;
		this.internationalityLevel = internationalityLevel;
		this.formatLevel = formatLevel;
		this.resourceAvgLevel = resourceAvgLevel;
		this.updateLevel = updateLevel;
		this.lastEdit = lastEdit;
		this.updateFrequency = updateFrequency;
		
		this.resourceEvaluation = new ArrayList<ResourceEvaluationModel>();
		
		this.detail = detail;
		
		this.strikes = 0;
		this.isNew = true;
	}		
	
	public EvaluationModel(Identifier id, String title, int datasetLevel, int semanticLevel, int internationalityLevel,
			int formatLevel, int resourceAvgLevel, int updateLevel, Date lastEdit, String updateFrequency,
			List<ResourceEvaluationModel> resourceEvaluation, List<EvaluationIndex> detail, int strikes,
			boolean isNew) {
		this.id = id;
		this.title = title;
		this.datasetLevel = datasetLevel;
		this.semanticLevel = semanticLevel;
		this.internationalityLevel = internationalityLevel;
		this.formatLevel = formatLevel;
		this.resourceAvgLevel = resourceAvgLevel;
		this.updateLevel = updateLevel;
		this.lastEdit = lastEdit;
		this.updateFrequency = updateFrequency;
		this.resourceEvaluation = resourceEvaluation;
		this.detail = detail;
		this.strikes = strikes;
		this.isNew = isNew;
	}

	public EvaluationModel(Identifier id, String title, int datasetLevel, int semanticLevel, int internationalityLevel, int formatLevel,
			int resourceAvgLevel, int updateLevel, Date lastEdit, String updateFrequency,
			List<ResourceEvaluationModel> resourceEvaluation, List<EvaluationIndex> detail) {
		this.id = id;
		this.title = title;
		this.datasetLevel = datasetLevel;
		this.semanticLevel = semanticLevel;
		this.internationalityLevel = internationalityLevel;
		this.formatLevel = formatLevel;
		this.resourceAvgLevel = resourceAvgLevel;
		this.updateLevel = updateLevel;
		this.lastEdit = lastEdit;
		this.updateFrequency = updateFrequency;
		this.resourceEvaluation = resourceEvaluation;
		this.detail = detail;
		
		this.strikes = 0;
		this.isNew = true;
	}

	public EvaluationModel(Identifier id, String title, int semanticLevel, int internationalityLevel, int formatLevel,
			int resourceAvgLevel, int updateLevel, Date lastEdit, String updateFrequency,
			List<ResourceEvaluationModel> resourceEvaluation, List<EvaluationIndex> detail) {
		this.id = id;
		this.title = title;
		this.semanticLevel = semanticLevel;
		this.internationalityLevel = internationalityLevel;
		this.formatLevel = formatLevel;
		this.resourceAvgLevel = resourceAvgLevel;
		this.updateLevel = updateLevel;
		this.lastEdit = lastEdit;
		this.updateFrequency = updateFrequency;
		this.resourceEvaluation = resourceEvaluation;
		this.detail = detail;
		
		this.strikes = 0;
		this.isNew = true;
	}


	/* ------- Get & Set ------- */
	public Identifier getId() {
		return id;
	}

	public void setId(Identifier id) {
		this.id = id;
	}
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDatasetLevel() {
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

	public List<EvaluationIndex> getDetail() {
		return detail;
	}		

	public Date getLastEdit() {
		return lastEdit;
	}


	public void setLastEdit(Date lastEdit) {
		this.lastEdit = lastEdit;
	}


	public String getUpdateFrequency() {
		return updateFrequency;
	}


	public void setUpdateFrequency(String updateFrequency) {
		this.updateFrequency = updateFrequency;
	}


	public void setDetail(List<EvaluationIndex> detail) {
		this.detail = detail;
	}

	public List<ResourceEvaluationModel> getResourceEvaluation() {
		return resourceEvaluation;
	}

	public void setResourceEvaluation(List<ResourceEvaluationModel> resourceEvaluation) {
		this.resourceEvaluation = resourceEvaluation;
	}
	
	
	
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	public int getStrikes() {
		return strikes;
	}

	public void setStrikes(int strikes) {
		this.strikes = strikes;
	}
	
	public int incrementStrikes() {
		if(this.strikes!=3)
			this.strikes+=1;
		return this.strikes;
	}
	
	public void resetStrikes() {
		this.strikes = 0;
	}

	@Override
	public String toString() {
		return "EvaluationModel [id=" + id + ", title=" + title + ", datasetLevel=" + datasetLevel + ", semanticLevel="
				+ semanticLevel + ", internationalityLevel=" + internationalityLevel + ", formatLevel=" + formatLevel
				+ ", resourceAvgLevel=" + resourceAvgLevel + ", updateLevel=" + updateLevel + ", lastEdit=" + lastEdit
				+ ", updateFrequency=" + updateFrequency + ", resourceEvaluation=" + resourceEvaluation + ", detail="
				+ detail + ", strikes=" + strikes + ", isNew=" + isNew + "]";
	}


	
	
	
}
