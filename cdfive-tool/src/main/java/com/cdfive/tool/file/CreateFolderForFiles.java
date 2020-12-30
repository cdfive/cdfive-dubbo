package com.cdfive.tool.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * java -classpath cdfive-tool-0.0.1-SNAPSHOT.jar com.cdfive.tool.file.CreateFolderForFiles xxx 500
 *
 * @author cdfive
 */
public class CreateFolderForFiles {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        // Absolute path
        String folderPath = args[0];
        Integer perFolderFileSize = Integer.parseInt(args[1]);
        System.out.println(String.format("folderPath=%s", folderPath));

        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        int index = 0;
        int processFileIndex = 0;
        int createFolderIndex = 0;
        List<File> batchFiles = new ArrayList<>(perFolderFileSize);
        for (File file : files) {
            index++;
            String name = file.getName();
            // Only process image files
            if (name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".gif")) {
                processFileIndex++;
                batchFiles.add(file);

                if (batchFiles.size() >= perFolderFileSize || index >= files.length) {
                    createFolderIndex++;
                    File dir = new File(folderPath + File.separator + createFolderIndex);
                    dir.mkdir();

                    for (File batchFile : batchFiles) {
                        batchFile.renameTo(new File(dir.getAbsoluteFile() + File.separator + batchFile.getName()));
                    }
                    batchFiles.clear();
                }
            }
        }
        System.out.println(String.format("total process %d image files, and create %d folders", processFileIndex, createFolderIndex));
        System.out.println(String.format("CreateFolderForFiles done,cost=%sms", System.currentTimeMillis() - start));
    }
}
