package com.yunwuye.sample.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSON;

/**
 *
 * @author Roy
 *
 * @date 2022年11月11日-下午3:53:03
 */
public class JavaFileAnalysis{

    public static final String FILE_SUFFIX = "java";

    public static void analysisFile (File file) {
        JavaFilePaserVO vo = new JavaFilePaserVO ();
        List<String> blankLines = new ArrayList<> ();
        List<String> codeLines = new ArrayList<> ();
        List<String> codeComments = new ArrayList<> ();
        String fileName = file.getName ();
        vo.setFileName (fileName);
        String filePath = file.getAbsolutePath ();
        vo.setFilePath (filePath);
        StringBuilder blockCommonts = new StringBuilder ();
        boolean isCommontReading = false;
        try (BufferedReader input = new BufferedReader (new FileReader (filePath));) {
            String lineContents = "";
            int i = 1;
            while ( (lineContents = input.readLine ()) != null) {
                if (lineContents.length () == 0) {
                    // 空行
                    blankLines.add (i + "空行是：" + lineContents);
                    i++;
                    continue;
                }
                String tmp = lineContents.trim ();
                if (StringUtils.isBlank (tmp)) {
                    // 空行
                    blankLines.add (i + "行是空白：" + lineContents);
                    i++;
                    continue;
                } else if (tmp.startsWith ("//")) {
                    codeComments.add (i + "单行注释：" + lineContents);
                    i++;
                    continue;
                } else if (tmp.startsWith ("/*") && !tmp.endsWith ("*/")) {
                    isCommontReading = true;
                    blockCommonts.append (i + "行是多行注释：" + lineContents);
                    i++;
                    continue;
                } else if (isCommontReading && (tmp.startsWith ("*") || tmp.endsWith ("*/") || tmp.startsWith ("*/"))) {
                    blockCommonts.append (i + "行是多行注释：" + lineContents);
                    if (tmp.endsWith ("*/") || tmp.startsWith ("*/")) {
                        isCommontReading = false;
                        codeComments.add (blockCommonts.toString ());
                        blockCommonts.delete (0, blockCommonts.length ());
                    }
                    i++;
                    continue;
                } else {
                    // TODO 如果代码后注释需要处理
                    if (lineContents.split ("//").length > 0) {
                    }
                    codeLines.add (i + "行是代码：" + lineContents);
                    i++;
                }
            }
            vo.setBlankLines (blankLines);
            vo.setCodeComments (codeComments);
            vo.setCodeLines (codeLines);
            input.close ();
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        } finally {
            System.out.println (JSON.toJSONString (vo));
        }
    }

    public static void findFileBySuffix (File file, String fileSuffix) {
        if (file == null || !file.exists ()) {
            return;
        }
        if (file.isFile ()) {
            analysisFile (file);
        }
        for (File subFile : file.listFiles ()) {
            if (isjavaFile (subFile.getName (), fileSuffix)) {
                analysisFile (subFile);
                continue;
            }
            if (subFile.isDirectory ()) {
                findFileBySuffix (subFile, fileSuffix);
            }
        }
    }

    public static boolean isjavaFile (String fileName, String fileSuffix) {
        String ext = fileName.substring (fileName.lastIndexOf (".") + 1);
        if (fileSuffix.equalsIgnoreCase (ext)) {
            return true;
        }
        return false;
    }

    public static void main (String[] args) {
        File file = new File ("F:\\loader");
        findFileBySuffix (file, FILE_SUFFIX);
    }

    @SuppressWarnings ("unused")
    private static class JavaFilePaserVO{

        /**
         * 文件名
         */
        private String fileName;

        /**
         * @return the fileName
         */
        public String getFileName () {
            return fileName;
        }

        /**
         * @param fileName the fileName to set
         */
        public void setFileName (String fileName) {
            this.fileName = fileName;
        }

        /**
         * @return the filePath
         */
        public String getFilePath () {
            return filePath;
        }

        /**
         * @param filePath the filePath to set
         */
        public void setFilePath (String filePath) {
            this.filePath = filePath;
        }

        /**
         * @return the codeLines
         */
        public List<String> getCodeLines () {
            return codeLines;
        }

        /**
         * @param codeLines the codeLines to set
         */
        public void setCodeLines (List<String> codeLines) {
            this.codeLines = codeLines;
        }

        /**
         * @return the codeComments
         */
        public List<String> getCodeComments () {
            return codeComments;
        }

        /**
         * @param codeComments the codeComments to set
         */
        public void setCodeComments (List<String> codeComments) {
            this.codeComments = codeComments;
        }

        /**
         * @return the blankLines
         */
        public List<String> getBlankLines () {
            return blankLines;
        }

        /**
         * @param blankLines the blankLines to set
         */
        public void setBlankLines (List<String> blankLines) {
            this.blankLines = blankLines;
        }

        /**
         * 路径
         */
        private String       filePath;
        /**
         * 有效代码行
         */
        private List<String> codeLines;
        /**
         * 注释行
         */
        private List<String> codeComments;
        /**
         * 空白行
         */
        private List<String> blankLines;
    }
}
