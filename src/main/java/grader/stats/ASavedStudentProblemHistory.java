package grader.stats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ASavedStudentProblemHistory  implements SavedStudentProblemGradingHistory{
	List<String> graderNames;	
	String moduleName;
	String problemName;
	String onyen;
	String name;
	int numVisits = 1;
	Double manualOverallScore;
	String manualOverallNotes = "";
	Map<String, Double> featureToManualScore = new HashMap();
	Map<String, String> featureToManualNotes = new HashMap();
//	long visitPeriod;
	double totalScore;
	
	long visitEndTime;
	long autoVisitTime;
	
	long manualVisitTime;
	String sourceComments;
	double multiplier;
	double sourcePoints;
	
	

	

	public  ASavedStudentProblemHistory(String aModuleName, String aProblemName, String anOnyen) {
		moduleName = aModuleName;
		problemName = aProblemName;
		onyen = anOnyen;
//		name = aName;
	}
	
	public String getOnyen() {
		return onyen;
	}

	public void setOnyen(String onyen) {
		this.onyen = onyen;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}

	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getProblemName() {
		return problemName;
	}
	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}
	public int getNumVisits() {
		return numVisits;
	}
	public void setNumVisits(int numVisits) {
		this.numVisits = numVisits;
	}
	public Double getManualOverallScore() {
		return manualOverallScore;
	}
	public void setManualOverallScore(Double manualOverallScore) {
		this.manualOverallScore = manualOverallScore;
	}
	public String getManualOverallNotes() {
		return manualOverallNotes;
	}
	public void setManualOverallNotes(String manualOverallNotes) {
		this.manualOverallNotes = manualOverallNotes;
	}
	public Map<String, Double> getFeatureToManualScore() {
		return featureToManualScore;
	}
	public void setFeatureToManualScore(Map<String, Double> featureToManualScore) {
		this.featureToManualScore = featureToManualScore;
	}
	public Map<String, String> getFeatureToManualNotes() {
		return featureToManualNotes;
	}
	public void setFeatureToManualNotes(Map<String, String> featureToManualNotes) {
		this.featureToManualNotes = featureToManualNotes;
	}
//	public long getVisitPeriod() {
//		return visitPeriod;
//	}
//	public void setVisitPeriod(long visitPeriod) {
//		this.visitPeriod = visitPeriod;
//	}
//	@Override
//	public void addVisitPeriod(long aNewPeriod) {
//		visitPeriod += aNewPeriod;
//	}
	@Override
	public void incNumVisits() {
		numVisits++;
	}
	public double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}
	@Override
	public long getVisitEndTime() {
		return visitEndTime;
	}
	@Override
	public void setVisitEndTime(long visitEndTime) {
		this.visitEndTime = visitEndTime;
	}
	@Override
	public void merge(SavedStudentProblemGradingHistory other) {
		incNumVisits();
		if (!other.getManualOverallNotes().isEmpty() && other.getVisitEndTime() > getVisitEndTime())
			manualOverallNotes = other.getManualOverallNotes();
		if (other.getManualOverallScore() != null && other.getVisitEndTime() > getVisitEndTime())
			manualOverallScore = other.getManualOverallScore();
		Map<String, Double> otherFeatureToManualScore = other.getFeatureToManualScore();
		Set<String> otherFeatures = otherFeatureToManualScore.keySet();
		for (String onyen:otherFeatures) {
			featureToManualScore.put(onyen, otherFeatureToManualScore.get(onyen));			
		}
		Map<String, String> otherFeatureToManualNotes = other.getFeatureToManualNotes();
		otherFeatures = otherFeatureToManualNotes.keySet();
		for (String onyen:otherFeatures) {
			featureToManualNotes.put(onyen, otherFeatureToManualNotes.get(onyen));			
		}
//		visitPeriod += other.getVisitPeriod();	
		manualVisitTime += other.getManualVisitTime();
		autoVisitTime += other.getAutoVisitTime();
		
	}
	@Override
	public void addGraderName(String newName) {
		if (graderNames.contains(newName))
			return;
		graderNames.add(newName);
	}
	public List<String> getGraderNames() {
		return graderNames;
	}

	public void setGraderNames(List<String> newValue) {
		this.graderNames = newValue;
	}
	
	public long getAutoVisitTime() {
		return autoVisitTime;
	}

	public void addAutoVisitTime(long autoVisitTime) {
		this.autoVisitTime += autoVisitTime;
	}

	public long getManualVisitTime() {
		return manualVisitTime;
	}

	public void addManualVisitTime(long manualVisitTime) {
		this.manualVisitTime += manualVisitTime;
	}

	public String getSourceComments() {
		return sourceComments;
	}

	public void setSourceComments(String sourceComments) {
		this.sourceComments = sourceComments;
	}

	public double getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(double multiplier) {
		this.multiplier = multiplier;
	}

	public double getSourcePoints() {
		return sourcePoints;
	}

	public void setSourcePoints(double sourcePoints) {
		this.sourcePoints = sourcePoints;
	}
	


}