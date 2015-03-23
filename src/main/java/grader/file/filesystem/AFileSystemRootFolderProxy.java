package grader.file.filesystem;

import edu.emory.mathcs.backport.java.util.Collections;
import grader.file.AnAbstractRootFolderProxy;
import grader.file.RootFolderProxy;
import grader.trace.file.load.RootFileSystemFolderLoaded;
import grader.trace.file.load.RootFolderProxyLoaded;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import util.misc.Common;

public class AFileSystemRootFolderProxy extends AnAbstractRootFolderProxy
        implements RootFolderProxy {
    protected File rootFolder;
    String rootName;
    String localName;

    public AFileSystemRootFolderProxy(String aRootFolderName) {
        super();
        rootFolder = new File(aRootFolderName);
        if (!rootFolder.exists()) {
            System.out.println("File:" + aRootFolderName + "  does not exist");
            rootFolder = null;
            System.exit(-1);
            return;
        }

//        rootName = aRootFolderName;
//        try {
			rootName = Common.toCanonicalFileName(rootFolder.getAbsolutePath());
//		} catch (IOException e) {
//			rootName = aRootFolderName;
//		}

        localName = Common.toCanonicalFileName(rootFolder.getName());
        initEntries(rootFolder);
        initChildrenRootData(); // I moved this out of init entries because it only needs to be called once and significantly reduces the loading time. --Josh
        RootFileSystemFolderLoaded.newCase(getAbsoluteName(), this);

    }
    
    private boolean containsOnyen(File file ){
    	int openParenPos = file.getName().lastIndexOf('(');
    	int closeParenPos = file.getName().lastIndexOf(')');
    	return openParenPos >= 0 && closeParenPos >= 0 && openParenPos + 1 < closeParenPos;
    }
    
    private File[] sortFiles(File[] files) {
    	List<File> studentDirectories = new ArrayList<File>();
    	List<File> otherFiles = new ArrayList<File>();
    	
    	for (File file : files) {
    		if (file.isDirectory() && containsOnyen(file)) {
    			studentDirectories.add(file);
    		} else {
    			otherFiles.add(file);
    		}
    	}
    	
    	Collections.sort(studentDirectories, new Comparator<File>() {

			@Override
			public int compare(File f1, File f2) {
				String onyen1 = f1.getName().substring(f1.getName().lastIndexOf('(') + 1,
						f1.getName().lastIndexOf(')'));
				String onyen2 = f2.getName().substring(f2.getName().lastIndexOf('(') + 1,
						f2.getName().lastIndexOf(')'));
				return onyen1.compareTo(onyen2);
			}
		});
    	
    	otherFiles.addAll(studentDirectories);
    	return otherFiles.toArray(new File[0]);
    	
    }

    void initEntries(File aFolder) {
        File[] files = aFolder.listFiles();
        
		if (aFolder.equals(rootFolder)) {
			files = sortFiles(files);
//			Arrays.sort(files, new Comparator<Object>() {
//
//				@Override
//				public int compare(Object o1, Object o2) {
//					if (!(o1 instanceof File && o2 instanceof File)) {
//						throw new RuntimeException("Invalid Type.  Must be of type File.");
//					}
//					File f1 = (File) o1;
//					File f2 = (File) o2;
//					if (!containsOnyen(f1) || !containsOnyen(f2)) {
//						return f1.compareTo(f2);
//					}
//					String onyen1 = f1.getName().substring(f1.getName().lastIndexOf('(') + 1,
//							f1.getName().lastIndexOf(')'));
//					String onyen2 = f2.getName().substring(f2.getName().lastIndexOf('(') + 1,
//							f2.getName().lastIndexOf(')'));
//					return onyen1.compareTo(onyen2);
//				}
//			});
		}
        
        String rootName = Common.toCanonicalFileName(rootFolder.getAbsolutePath());
        for (File aFile : files) {
            add(new AFileSystemFileProxy(this, aFile, rootName));
            if (aFile.isDirectory()) {
                initEntries(aFile);
            }
        }
    }

    @Override
    public boolean exists() {
        return rootFolder != null && rootFolder.exists();
    }

    @Override
    public String getMixedCaseAbsoluteName() {
        return rootName;
    }

    @Override
    public String getMixedCaseLocalName() {
        return localName;
    }

    @Override
    public String getAbsoluteName() {
        return rootName;
    }

    @Override
    public String getLocalName() {
        return localName;
    }

}
