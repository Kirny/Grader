package gradingTools.comp999junit.assignment1.testcases.reflection.tags;


//import org.junit.Test;
import grader.junit.JUnitUtils;
import grader.util.IntrospectionUtil;
import gradingTools.comp999junit.assignment1.testcases.PointProxy;
import gradingTools.comp999junit.assignment1.testcases.reflection.AReflectivePointProxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import util.annotations.Explanation;
import util.annotations.Group;
import util.annotations.IsExtra;
import util.annotations.IsRestriction;
import util.annotations.MaxValue;
import util.introspect.ClassLoaderFactory;

@MaxValue(6)
//@Explanation("Radius and Angle Correctly Computed")
//@Group(CartesianPointSuite.ANGLE_TESTS)
public class ATaggingPointProxy extends AReflectivePointProxy implements PointProxy{
//	// weights
//	public static final double CORRECT_CLASS = 0.2;
//	public static final double CORRECT_CONSTRUCTOR = 0.2;
//	public static final double CORRECT_INSTANTIATION = 0.2;
//	public static final double CORRECT_ANGLE_METHOD = 0.2;
//	public static final double CORRECT_RADIUS_METHOD = 0.2;
//	public static final double ANGLE_0 = 0.1;
//	public static final double ANGLE_90 = 0.1;
//	public static final double ANGLE_MINUS_90 = 0.1;
//	public static final double ANGLE_180 = 0.1;
//	public static final double ANGLE_MINUS_180 = 0.1;
//	int testNumber = 0;
//	boolean checkStructure;
//	double fractionComplete = 0.0;	
//	public  ATaggingPointProxy() {
//		checkStructure = true;
//	}
//	public  AReflectivePointStructureTest(boolean aCheckStructure) {
//		checkStructure = aCheckStructure;
//	}
	@Test
	public void test() {
		super.test();
//		try {
//		createCartesianPoint(0, 0);
//		getRadius();
//		getAngle();
//		} catch (Exception e) {
//			JUnitUtils.assertTrue(e,fractionComplete);
//		}
	}	
//	static Class[] emptyClassArray = {};
//	static Object[] emptyObjectArray = {};
//	Class aCartesianPointClass;
//	Object point;
//
//	
//	boolean checkStructure() {
//		return checkStructure;
//	}
	
	public Class getCartesianPointClass() throws ClassNotFoundException  {
		return Class.forName("gradingTools.comp999junit.assignment1.sample1.ACartesianPoint", true, ClassLoaderFactory.getCurrentClassLoader());

	}
//	@Override
//	public void createCartesianPoint(int theX, int theY) throws Exception {
////		aCartesianPointClass = Class.forName("grader.junit.ACartesianPoint", true, ClassLoaderFactory.getCurrentClassLoader());
//		aCartesianPointClass = getCartesianPointClass();
//
//		if (checkStructure())
//			fractionComplete += CORRECT_CLASS;;
//		Constructor aConstructor = aCartesianPointClass.getConstructor(new Class[]{Integer.TYPE, Integer.TYPE});
//		if (checkStructure())
//			fractionComplete += CORRECT_CONSTRUCTOR;
//		point = aConstructor.newInstance(new Object[] {theX, theY});
//		if (checkStructure())
//			fractionComplete += CORRECT_INSTANTIATION;	
//		System.out.println ("Successfully Created Cartesian Point:" + point + " with parameters, x: " + theX + " y " + theY);
//	}
	
	public Method getRadiusMethod() throws Exception {
		return aCartesianPointClass.getMethod("getRadius", emptyClassArray);
	}
	
	public Method getAngleMethod() throws Exception {
		return aCartesianPointClass.getMethod("getAngle", emptyClassArray);
	}
//	@Override
//	public double getRadius() throws Exception {
//		Method aGetRadius = getRadiusMethod();
//		if (checkStructure())
//		   fractionComplete += CORRECT_RADIUS_METHOD;
//		Double retVal =  (Double) aGetRadius.invoke(point, emptyObjectArray);
//		
////		fractionComplete += 0.1;
//		return retVal;
//
//	}
//	@Override
//	public double getAngle() throws Exception {
//		Method aGetAngle = getAngleMethod();
//
//		if (checkStructure())
//			fractionComplete += CORRECT_ANGLE_METHOD;
//		Double retVal =  (Double) aGetAngle.invoke(point, emptyObjectArray);
//		fractionComplete += 0.1;
//		return retVal;
//
//	}


}