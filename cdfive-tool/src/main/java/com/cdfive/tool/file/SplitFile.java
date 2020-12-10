package com.cdfive.tool.file;

import java.io.*;

/**
 * java -classpath cdfive-tool-0.0.1-SNAPSHOT.jar com.cdfive.tool.file.SplitFile xxx.txt 8
 *
 * @author cdfive
 */
public class SplitFile {

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        String fileName = args[0];
        Integer splitNum = Integer.parseInt(args[1]);

//        String fileFolderPath = SplitFile.class.getResource("").getPath();
        String fileFolderPath = System.getProperty("user.dir") + File.separator;
        String filePath = fileFolderPath + fileName;
        System.out.println(String.format("filePath=%s,splitNum=%s", filePath, splitNum));

        File file = new File(filePath);

        String line;
        int total = 0;
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)), 5 * 1024 * 1024)) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(file))))) {
            while ((line = br.readLine()) != null) {
                total++;
            }
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(file))))) {
            int splitSize = total / splitNum;
            System.out.println(String.format("total line=%s,splitSize=%s", total, splitSize));

            String[] tokens = fileName.split("\\.");
            String fileNameReal = tokens[0];
            String fileNameSuffix = tokens.length > 1 ? tokens[1] : "";
            int index = 0;
            int splitIndex = 0;
            int fileNum = 1;
            long splitStartTime = System.currentTimeMillis();
            String spitFilePath = fileFolderPath + fileNameReal + "_" + fileNum + "." + fileNameSuffix;
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(spitFilePath)));
            while ((line = br.readLine()) != null) {
                index++;
                splitIndex++;
                bw.write(line + System.lineSeparator());
                if (fileNum < splitNum && splitIndex >= splitSize || index >= total) {
                    System.out.println(String.format("split file %s done,line=%s,cost=%sms,total cost=%sms", fileNum
                            , splitIndex, System.currentTimeMillis() - splitStartTime, System.currentTimeMillis() - startTime));
                    bw.close();
                    fileNum++;
                    splitIndex = 0;
                    if (index < total) {
                        spitFilePath = fileFolderPath + fileNameReal + "_" + fileNum + "." + fileNameSuffix;
                        bw = new BufferedWriter(new FileWriter(new File(spitFilePath)));
                        splitStartTime = System.currentTimeMillis();
                    }
                }
            }

            System.out.println(String.format("total cost=%sms", System.currentTimeMillis() - startTime));
        }
    }
}
