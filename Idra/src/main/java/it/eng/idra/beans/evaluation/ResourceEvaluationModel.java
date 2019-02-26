package it.eng.idra.beans.evaluation;

import java.util.List;

public class ResourceEvaluationModel {
	
	private String title;
	private String format;
	
	private int resourceLevel;
	private int semanticLevel;
	private int fiveStarLevel;	
	
	private List<EvaluationIndex> detail;
	
	/* ------- Constructors ------- */
	public ResourceEvaluationModel() {}
	
	public ResourceEvaluationModel(String title, String format, int resourceLevel, int semanticLevel,
			int fiveStarLevel, List<EvaluationIndex> detail) {
		this.title = title;
		this.format = format;
		this.resourceLevel = resourceLevel;
		this.semanticLevel = semanticLevel;
		this.fiveStarLevel = fiveStarLevel;
		this.detail = detail;
	}
	
	public ResourceEvaluationModel(String title, String format, int semanticLevel,
			int fiveStarLevel, List<EvaluationIndex> detail) {
		this.title = title;
		this.format = format;
		this.resourceLevel = 0;
		this.semanticLevel = semanticLevel;
		this.fiveStarLevel = fiveStarLevel;
		this.detail = detail;
	}
	
	/* ------- Get & Set ------- */	

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getResourceLevel() {
		return resourceLevel;
	}

	public String getTitle() {
		return title;
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

	public List<EvaluationIndex> getDetail() {
		return detail;
	}

	public void setDetail(List<EvaluationIndex> detail) {
		this.detail = detail;
	}
	

	
}

