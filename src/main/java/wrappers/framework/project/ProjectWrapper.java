package wrappers.framework.project;

import framework.navigation.SakaiStudentFolder;
import framework.navigation.StudentFolder;
import framework.project.StandardProject;
import framework.utils.GraderSettings;
import grader.project.AProject;
import grader.project.Project;
import grader.sakai.project.SakaiProject;
import grader.trace.file.load.RootZipFileFolderLoaded;
import grader.trace.file.load.FileUnzipped;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import tools.DirectoryUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;

/**
 * This transforms a "grader" project into a "framework" project.
 * This class and {@link wrappers.framework.grading.testing.TestCaseWrapper} are needed for the
 * {@link wrappers.grader.sakai.project.ProjectStepperDisplayerWrapper} to work properly.
 */
public class ProjectWrapper extends StandardProject {

    // changed to SakaiProject
    public ProjectWrapper(SakaiProject project, String name) throws FileNotFoundException {
        super(project, getDirectoryAndMaybeUnzip(project), name);
//        this.project = project;
    }

    public SakaiProject getProject() {
        return (SakaiProject) project;
    }

    /**
     * Given a grader project, this figures out what folder the project is in, extracting the zip file if necessary.
     *
     * @param project The grader project
     * @return The folder of the project
     * @throws FileNotFoundException
     */
    public static File getDirectoryAndMaybeUnzip(SakaiProject project) throws FileNotFoundException {
    	if (project.isNoProjectFolder()) {
            return null;
        }
    	
//    	File retVal = getDirectoryAndMaybeUnzip(project.getRootCodeFolder().getMixedCaseAbsoluteName());
    	File retVal = getDirectoryAndMaybeUnzip(project.getProjectZipFileOrFolderMixedCaseAbsoluteName());

    	project.setFilesUnzipped(true);
    	return retVal;
        
//        // Can be a path or a directory
//        //System.out.println(")()()()()( " + project.getRootCodeFolder().getAbsoluteName());
//        //File path = new File(project.getProjectFolderName());
//        File path = new File(project.getRootCodeFolder().getMixedCaseAbsoluteName());
//        //System.out.println("()()()()() " + path.getAbsolutePath());
//        if (path.isFile()) {
////            if (path.getName().endsWith(".zip")) {
//            if (path.getName().endsWith(AProject.ZIP_SUFFIX_1) || path.getName().endsWith(AProject.ZIP_SUFFIX_2)) {
//
//                // A zip file, so unzip
////                File dir = new File(path.getParentFile(), path.getName().replace(".zip", ""));
//            	String aFileName = path.getName().replace(AProject.ZIP_SUFFIX_1, "").replace(AProject.ZIP_SUFFIX_2, "");
////                File dir = new File(path.getParentFile(), path.getName().replace(AProject.ZIP_SUFFIX_1, ""));
//
//            	File dir = new File(path.getParentFile(), aFileName);
//
//                if (dir.exists()) {
//                    return dir;
//                }
//                dir.mkdir();
//
//                try {
//                    ZipFile zip = new ZipFile(path);
//                    zip.extractAll(dir.getAbsolutePath());
//                    FileUnzipped.newCase(path.getName(), ProjectWrapper.class);
//                    return dir;
//                } catch (ZipException e) {
//                    throw new FileNotFoundException();
//                }
//            } else {
//                throw new FileNotFoundException();
//            }
//        } else {
//            return path;
//            }
    }
    /**
     * Given a  zip file, unzips it and returns the file object
     *
     * @param project The grader project
     * @return The folder of the project
     * @throws FileNotFoundException
     */
    public static File getDirectoryAndMaybeUnzip(String aZipFileName) throws FileNotFoundException {
    	if (aZipFileName == null) {
            return null;
        }
        
        // Can be a path or a directory
        //System.out.println(")()()()()( " + project.getRootCodeFolder().getAbsoluteName());
        //File path = new File(project.getProjectFolderName());
        File path = new File(aZipFileName);
        System.out.println("got path:" + path);

        //System.out.println("()()()()() " + path.getAbsolutePath());
        if (path.isFile()) {
//            System.out.println("is File:" + path);

//            if (path.getName().endsWith(".zip")) {
            if (path.getName().endsWith(AProject.ZIP_SUFFIX_1) || path.getName().endsWith(AProject.ZIP_SUFFIX_2)) {
//                System.out.println("is Zip suffix:" + path);

                // A zip file, so unzip
//                File dir = new File(path.getParentFile(), path.getName().replace(".zip", ""));
            	String aFileName = path.getName().replace(AProject.ZIP_SUFFIX_1, "").replace(AProject.ZIP_SUFFIX_2, "");
//                File dir = new File(path.getParentFile(), path.getName().replace(AProject.ZIP_SUFFIX_1, ""));

            	File dir = new File(path.getParentFile(), aFileName);

                if (dir.exists()) {
//                    System.out.println("Directory exisrts:" + dir);

                    return dir;
                }
                dir.mkdir();
//                System.out.println("Made directory:" + dir);

                try {
                    ZipFile zip = new ZipFile(path);
                    zip.extractAll(dir.getAbsolutePath());
                    FileUnzipped.newCase(path.getName(), ProjectWrapper.class);
                    return dir;
                } catch (ZipException e) {
                    throw new FileNotFoundException();
                }
            } else {
                throw new FileNotFoundException();
            }
        } else {
            return path;
            }
    }

    /**
     * Given a grader project, this figures out where the student folder is.
     *
     * @param project The grader project
     * @return The student folder
     */
    public static StudentFolder getStudentFolder(Project project) {
        File path = new File(project.getProjectFolderName());
        return new SakaiStudentFolder(path.getParentFile().getParentFile());
    }

    public static StudentFolder getStudentFolder(final String onyen) {
        File folder = DirectoryUtils.find(new File(GraderSettings.get().get("path")), new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().contains("(" + onyen + ")");
            }
        }).get();
        return new SakaiStudentFolder(folder);
    }
}
