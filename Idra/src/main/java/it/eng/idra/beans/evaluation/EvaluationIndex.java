package it.eng.idra.beans.evaluation;

import java.util.ArrayList;
import java.util.List;

public final class EvaluationIndex {
	
	private String indexName;
	private String targetName;
	private int percentageLevel;
	private List<String> problems;
	
	/* ------- Constructors ------- */
	public EvaluationIndex() {
		indexName = targetName = "unknown";
		percentageLevel = 0;
		problems = new ArrayList<String>();
	}		
	
	public EvaluationIndex(int maxLevel) {
		indexName = targetName = "unknown";
		percentageLevel = maxLevel;
		problems = new ArrayList<String>();
	}
	
	public EvaluationIndex(String indexName, String targetName, int percentageLevel) {
		this.indexName = indexName;
		this.targetName = targetName;
		this.percentageLevel = percentageLevel;
	}
	
	public EvaluationIndex(String indexName, String targetName, int percentageLevel, List<String> problems) {
		this.indexName = indexName;
		this.targetName = targetName;
		this.percentageLevel = percentageLevel;
		this.problems = problems;
	}

	
	public EvaluationIndex(String indexName, String targetName) {
		this.indexName = indexName;
		this.targetName = targetName;
		problems = new ArrayList<String>();
	}

	/* ------- Get & Set ------- */
	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public int getPercentageLevel() {
		return percentageLevel;
	}

	public void setPercentageLevel(int percentageLevel) {
		this.percentageLevel = percentageLevel;
	}

	public List<String> getProblems() {
		return problems;
	}

	public void setProblems(List<String> problems) {
		this.problems = problems;
	}			
	
}
