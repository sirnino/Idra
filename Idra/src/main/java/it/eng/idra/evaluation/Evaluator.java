package it.eng.idra.evaluation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.idra.beans.ODFProperty;
import it.eng.idra.beans.evaluation.DatasetEval;
import it.eng.idra.beans.evaluation.EvaluationModel;
import it.eng.idra.beans.evaluation.EvaluationResponse;
import it.eng.idra.beans.evaluation.ResourceEval;
import it.eng.idra.beans.evaluation.ResourceEvaluationModel;
import it.eng.idra.utils.PropertyManager;

@SuppressWarnings("deprecation")
public final class Evaluator {
	
	private static String basePath = "";
	private static String evaluatorPath = PropertyManager.getProperty(ODFProperty.EVALUATION_BASEURL);
	
	private static Logger logger = LogManager.getLogger(Evaluator.class);
	
	private Evaluator() {}
	
	public static void setBasePath(String federatorBasePath) {
		basePath = federatorBasePath;
	}
	
	public static void setEvaluatorPath(String path) {
		evaluatorPath = path;
	}
	
	public static Set<DatasetEval> getDatasetEvaluation(Map<Integer, Set<String>> datasetIDs) {

		Set<DatasetEval> result = new HashSet<DatasetEval>();
		Set<EvaluationModel> evaluationList = new HashSet<EvaluationModel>();
		
		if(basePath.trim().length()==0) return result;
		
		evaluationList = getEvaluations(datasetIDs);
		for(EvaluationModel evaluation : evaluationList){
			DatasetEval dsEval = new DatasetEval(evaluation);
			result.add(dsEval);
		}
		
		return result;
	}
	
	public static DatasetEval getDatasetEvaluation(Integer nodeId,String datasetIDs) {
		EvaluationModel evaluation;
		DatasetEval dsEval = null;
		if(basePath.trim().length()==0) return dsEval;
		
		evaluation = getEvaluations(nodeId,datasetIDs);
		if(evaluation!=null)
			dsEval = new DatasetEval(evaluation);

		return dsEval;
	}	
	
	
	public static Map<String,DatasetEval> getDatasetEvaluationMap(Map<Integer, Set<String>> datasetIDs) {

		Map<String,DatasetEval> result = new HashMap<String,DatasetEval>();
		Set<EvaluationModel> evaluationList = new HashSet<EvaluationModel>();
		
		if(basePath.trim().length()==0) return result;
		
		evaluationList = getEvaluations(datasetIDs);
		
		for(EvaluationModel evaluation : evaluationList){
			DatasetEval dsEval = new DatasetEval(evaluation);
			result.put(dsEval.getDatasetId(), dsEval);
		}
		
		return result;
	}

	public static Set<ResourceEval> getResourceEvaluation(Map<Integer, Set<String>> datasetIDs) {
		Set<ResourceEval> result = new HashSet<ResourceEval>();
		Set<EvaluationModel> evaluationList = new HashSet<EvaluationModel>();
		
		if(basePath.trim().length()==0) return result;
		
		evaluationList = getEvaluations(datasetIDs);
		
		for(EvaluationModel evaluation : evaluationList) {
			for(ResourceEvaluationModel resourceEvaluation : evaluation.getResourceEvaluation()) {
				result.add(new ResourceEval(evaluation.getId().getId(),resourceEvaluation));
			}
		}	
		return result;
	}
	
	public static Set<ResourceEval> getResourceEvaluation(Integer nodeId,String datasetIDs) {
		EvaluationModel evaluation;
		Set<ResourceEval> result = new HashSet<ResourceEval>();
		if(basePath.trim().length()==0) return result;
		
		evaluation = getEvaluations(nodeId,datasetIDs);
		if(evaluation!=null)
			for(ResourceEvaluationModel resourceEvaluation : evaluation.getResourceEvaluation()) {
				result.add(new ResourceEval(evaluation.getId().getId(),resourceEvaluation));
			}

		return result;
	}	
	
	public static Map<String,Set<ResourceEval>> getResourceEvaluationMap(Map<Integer, Set<String>> datasetIDs) {
		Map<String,Set<ResourceEval>> result = new HashMap<String,Set<ResourceEval>>();
		Set<ResourceEval> evalSet;
		Set<EvaluationModel> evaluationList = new HashSet<EvaluationModel>();
		
		if(basePath.trim().length()==0) return result;
		
		evaluationList = getEvaluations(datasetIDs);
		
		for(EvaluationModel evaluation : evaluationList) {
			for(ResourceEvaluationModel resourceEvaluation : evaluation.getResourceEvaluation()) {
				String datasetId = evaluation.getId().getId();
				evalSet = result.get(datasetId);
				if(evalSet == null) {
					evalSet = new HashSet<ResourceEval>();
					evalSet.add(new ResourceEval(datasetId,resourceEvaluation));
					result.put(datasetId, evalSet);
				} else {
					evalSet.add(new ResourceEval(datasetId,resourceEvaluation));
				}
			}
		}
		
		return result;
	}
	
	private static EvaluationModel getEvaluations(Integer nodeId, String datasetID) {
		String apiPath;
		EvaluationModel evaluation  = null;
		
		JSONObject requestBody = new JSONObject();

		Set<String> dataset = new HashSet<String>();
		dataset.add(datasetID);
		apiPath = evaluatorPath+"/nodes/"+nodeId+"/datasets?init="+1+"&offset=0&resource=true";
		requestBody.put("federatorPath", basePath);
		requestBody.put("ids", dataset);
			
		try {
			StringEntity input = new StringEntity(requestBody.toString());
						 input.setContentType(MediaType.APPLICATION_JSON);
			
			String returned_json = sendPostRequest(apiPath, input);
			
			EvaluationResponse evResponse = JSONDeserialize(returned_json);
			if(evResponse!=null) 
				if(evResponse.getDatasets().size()>0)evaluation = evResponse.getDatasets().get(0);
				
		} catch(Exception e) {
			logger.warn("Response deserializzation error: "+e);
		}			

		return evaluation;
	}

	
	private static Set<EvaluationModel> getEvaluations(Map<Integer, Set<String>> datasetIDs) {
		String apiPath;
		Set<EvaluationModel> evaluationList = new HashSet<EvaluationModel>();
		
		JSONObject requestBody = new JSONObject();
		
		for(Integer node : datasetIDs.keySet()) {
			
			Set<String> dataset = datasetIDs.get(node);
			apiPath = evaluatorPath+"/nodes/"+node+"/datasets?init="+dataset.size()+"&offset=0&resource=true";
			requestBody.put("federatorPath", basePath);
			requestBody.put("ids", dataset);
			
			try {
				
				StringEntity input = new StringEntity(requestBody.toString());
				input.setContentType(MediaType.APPLICATION_JSON);
				
				String returned_json = sendPostRequest(apiPath, input);
				
				EvaluationResponse evResponse = JSONDeserialize(returned_json);
				if(evResponse!=null) 
					if(evResponse.getDatasets().size()>0)evaluationList.addAll(evResponse.getDatasets());
				
			} catch(Exception e) {
				logger.warn("Response deserializzation error: "+e);
			}			
			
		}
		return evaluationList;
	}	
	
	private static EvaluationResponse JSONDeserialize(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		EvaluationResponse response;
		try { response = mapper.readValue(jsonString, EvaluationResponse.class); } 
		catch (IOException e) {
			e.printStackTrace();
			response = null;
		}
		
		return response;
	}
	
	private static boolean isSet(String string) {
		return string != null && string.length() > 0;
	}
	
	private static String sendPostRequest(String urlString, HttpEntity entity) throws IOException {
		URL url = null;

		try {
			url = new URL(urlString);
		} catch (MalformedURLException mue) {
			System.err.println(mue);
			return null;
		}

		String body = "";

		final HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000000);
		HttpConnectionParams.setSoTimeout(httpParams, 9000000);
		
		@SuppressWarnings("resource")
		HttpClient httpclient = new DefaultHttpClient(httpParams);

		if (Boolean.parseBoolean(PropertyManager.getProperty(ODFProperty.HTTP_PROXY_ENABLED).trim())
				&& StringUtils.isNotBlank(PropertyManager.getProperty(ODFProperty.HTTP_PROXY_HOST).trim())) {

			int port = 80;
			if (isSet(PropertyManager.getProperty(ODFProperty.HTTP_PROXY_PORT))) {
				port = Integer.parseInt(PropertyManager.getProperty(ODFProperty.HTTP_PROXY_PORT));
			}
			HttpHost proxy = new HttpHost(PropertyManager.getProperty(ODFProperty.HTTP_PROXY_HOST), port, "http");
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			if (isSet(PropertyManager.getProperty(ODFProperty.HTTP_PROXY_USER))) {
				((AbstractHttpClient) httpclient).getCredentialsProvider().setCredentials(
						new AuthScope(PropertyManager.getProperty(ODFProperty.HTTP_PROXY_HOST), port),
						(Credentials) new UsernamePasswordCredentials(
								PropertyManager.getProperty(ODFProperty.HTTP_PROXY_USER),
								PropertyManager.getProperty(ODFProperty.HTTP_PROXY_PASSWORD)));
			}
		}
		try {
			
			HttpPost postRequest = new HttpPost(url.toString());
			postRequest.setEntity(entity);
			postRequest.addHeader("accept", "application/json");

			HttpResponse response = httpclient.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			body = result.toString();

		} finally {
			httpclient.getConnectionManager().closeExpiredConnections();
			httpclient.getConnectionManager().shutdown();
		}

		return body;
	}
	
}
