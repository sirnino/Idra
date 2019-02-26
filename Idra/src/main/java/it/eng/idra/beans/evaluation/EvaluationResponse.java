package it.eng.idra.beans.evaluation;

import java.util.ArrayList;
import java.util.List;

public class EvaluationResponse {
	private int count;
	private List<EvaluationModel> datasets;

	public EvaluationResponse() {
		this.datasets = new ArrayList<EvaluationModel>();
	}
	
	public EvaluationResponse(List<EvaluationModel> datasets) {
		this.datasets = datasets;
		this.count = datasets.size();
	}

	public List<EvaluationModel> getDatasets() {
		return datasets;
	}

	public void setDatasets(List<EvaluationModel> datasets) {
		this.datasets = datasets;
		this.count = datasets.size();
	}
	
	public int getCount() {
		return this.count;
	}
		
}
