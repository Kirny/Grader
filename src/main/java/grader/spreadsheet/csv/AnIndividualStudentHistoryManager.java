package grader.spreadsheet.csv;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.emory.mathcs.backport.java.util.Arrays;
import grader.assignment.GradingFeature;
import grader.file.FileProxy;
import grader.sakai.project.ProjectStepperFactory;
import grader.sakai.project.SakaiProjectDatabase;

public class AnIndividualStudentHistoryManager extends ASakaiCSVFeatureGradeManager implements IndividualStidentHistoryManager {
	public static final int RUN_INDEX = 0;
	public static final int DATE_INDEX = 1;
	public static final int SCORE_DELTA_INDEX = 2;
	protected double previousScore;
	
	public AnIndividualStudentHistoryManager(FileProxy aGradeSpreadsheet, List<GradingFeature> aGradingFeatures) {
		super(aGradeSpreadsheet, aGradingFeatures);
		maybeCreateTable();
//		resetTable();
//		writeTable();
	}
	public void resetHistory() {
		resetTable();
		writeTable();
	}
//	public AnIndividualStudentHistoryManager(SakaiProjectDatabase aSakaiProjectDatabase) {
//		super(aSakaiProjectDatabase);
//		maybeCreateTable();
//		resetTable();
//		writeTable();		
////		gradeSpreadsheet = aGradeSpreadsheet;		
//	}
	
	
	public void resetTable() {
		List<String[]>  newTable = new ArrayList();
		for (int i = 0; i <= TITLE_ROW; i++) {
			newTable.add(table.get(i));
		}
//		extendTable();
		String[] aHeaderRow = newTable.get(TITLE_ROW);
		aHeaderRow[RUN_INDEX]= "#";
		aHeaderRow[DATE_INDEX] = "Date";
		aHeaderRow[SCORE_DELTA_INDEX] = "Change";
		aHeaderRow[GRADE_COLUMN - 1] = "";
		table = newTable;
//		addNewRow(); // for the first entry
	}
	// should be called by all student manager
	public String[] addNewRow() {
		String[] aPreviousRow = table.get(table.size()-1);
		String[] aNewRow = new String[aPreviousRow.length];
		
		for (int i=GRADE_COLUMN - 1; i < aNewRow.length; i++) {
			aNewRow[i] = "";
		}
		int aRunNumber = (table.size() - 1 - TITLE_ROW);
		if (aRunNumber == 0) {
			previousScore = 0.0;
		} else {
			previousScore = Double.parseDouble(aPreviousRow[GRADE_COLUMN]);
		}

		aNewRow[RUN_INDEX]= "" + aRunNumber;
		aNewRow[DATE_INDEX] = new Date(System.currentTimeMillis()).toString();
		table.add(aNewRow);
		return aNewRow;
		
	}
	@Override
	public String[] getStudentRow(List<String[]> aSheet, String aStudentName, String anOnyen) {
		 return table.get(table.size() - 1);
		
	}
	// no error message, if size of table increases
	protected void checkSizes() {
		
	}
	public void setGrade(String aStudentName, String anOnyen, double aScore) {
		double aDelta = aScore - previousScore;
		table.get(table.size() - 1)[SCORE_DELTA_INDEX] = "" +aDelta;
		super.setGrade(aStudentName, anOnyen, aScore);
	}


//	@Override
//	public void setGrade(String aStudentName, String anOnyen, String aFeature, double aScore) {
//            try {
////                maybeCreateTable();
////                String[] row = getStudentRow(table, aStudentName, anOnyen);
//            	String[] row= table.get(table.size() - 1);
////                if (row == null) {
////                    System.out.println("Cannot find row for:" + aStudentName + " " + anOnyen);
////                    return;
////                }
//
//                recordGrade(row, aFeature, aScore);
//                writeTable();
//            } catch (Exception e) {
//                    e.printStackTrace();
//            }
//	}
	
//	@Override
//	public void setResultFormat(String aStudentName, String anOnyen, String aFeature,
//			String aResult) {
//		try {
//
////			maybeCreateTable();
//			
////		    String[] row = getStudentRow(table, aStudentName, anOnyen);
//        	String[] row= table.get(table.size() - 1);
//
////		    if (row == null) {
////				System.out.println("Cannot find row for:" + aStudentName + " " + anOnyen);
////				return;
////		    }
//		    
//		    recordResult(row, aFeature, aResult);
//		    writeTable();
//
//
//
//	} catch (Exception e) {
//		e.printStackTrace();
//		
//	}
//		
//	}

	

}
