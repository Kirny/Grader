package framework.navigation;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
//<<<<<<< HEAD
//=======
import java.util.TreeSet;
//>>>>>>> working

/**
 * This is a Sakai-specific implementation for handling bulk download folders.
 */
public class SakaiBulkDownloadFolder implements BulkDownloadFolder {

    private File folder;
    private File grades;

    public SakaiBulkDownloadFolder(String path) throws NotValidDownloadFolderException {
        folder = new File(path);

        // Find the grades.csv file. This is how we check that this is a valid location
        File[] foundFiles = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().equals("grades.csv");
            }
        });
        if (foundFiles.length == 0) {
            throw new NotValidDownloadFolderException();
        }

        grades = foundFiles[0];
    }

    /**
     * @return The grades.csv file
     */
    @Override
    public File getGrades() {
        return grades;
    }

    /**
     * @return The list of student folders
     */
    @Override
    public List<StudentFolder> getStudentFolders() {
        File[] folders = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        List<StudentFolder> studentFolders = new ArrayList<StudentFolder>();
        for (File studentFolder : folders) {
            studentFolders.add(new SakaiStudentFolder(studentFolder));
        }

        return studentFolders;
    }

    /**
     * Gets a segment of the student folder list
<<<<<<< HEAD
     *
=======
>>>>>>> working
     * @param start The onyen to start with
     * @param end The onyen to end with
     * @return The student folder that fall within the given onyen range
     */
    @Override
    public List<StudentFolder> getStudentFolders(String start, String end) {
//<<<<<<< HEAD
//        List<StudentFolder> filteredFolders = new ArrayList<StudentFolder>();
//        boolean collect = false;
//        for (StudentFolder studentFolder : getStudentFolders()) {
//            if (studentFolder.getOnyen().equals(start)) {
//                collect = true;
//            }
//            if (collect) {
//                filteredFolders.add(studentFolder);
//            }
//            if (studentFolder.getOnyen().equals(end)) {
//                collect = false;
//            }
//=======
    	
    	// Switch start and end if they are in reverse lexicographic order
    	if (start.compareTo(end) > 0) {
    		String temp = end;
    		end = start;
    		start = temp;
    	}
    	
        List<StudentFolder> filteredFolders = new ArrayList<StudentFolder>();
        boolean collect = false;
        for (StudentFolder studentFolder : new TreeSet<StudentFolder>(getStudentFolders())) {
            if (studentFolder.getOnyen().equals(start))
                collect = true;
            if (collect)
                filteredFolders.add(studentFolder);
            if (studentFolder.getOnyen().equals(end))
                collect = false;
//>>>>>>> working
        }
        return filteredFolders;
    }

    /**
     * Find's a particular student's folder
<<<<<<< HEAD
     *
=======
>>>>>>> working
     * @param onyen The student's Onyen
     * @return The student folder associated with the given Onyen
     */
    @Override
    public StudentFolder getStudentFolder(final String onyen) {
        File studentFolder = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().contains("(" + onyen + ")");
            }
        })[0];
        return new SakaiStudentFolder(studentFolder);
    }

    /**
     * @return The Sakai bulk download folder
     */
    @Override
    public File getFolder() {
        return folder;
    }
}
