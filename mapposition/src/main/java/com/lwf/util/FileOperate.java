package com.lwf.util;

import android.text.TextUtils;

import com.lwf.share.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class FileOperate {

    /**
     * 拷贝文件
     *
     * @param sourcePath
     * @param targetPath
     * @param delete     是否删除原文件
     * @return
     */
    public static String copyfile(String sourcePath, String targetPath, Boolean delete) {
        File fromFile = new File(sourcePath);
        File toFile = new File(targetPath);
        if (!fromFile.exists()) {
            return null;
        }
        if (!fromFile.isFile()) {
            return null;
        }
        if (!fromFile.canRead()) {
            return null;
        }
        if (!toFile.getParentFile().exists()) {
            toFile.getParentFile().mkdirs();
        }
        try {
            FileInputStream fosfrom = new FileInputStream(fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c); // 将内容写到新文件当中
            }
            fosfrom.close();
            fosto.close();
            if (fromFile.exists() && delete) {
                fromFile.delete();
            }
        } catch (Exception ex) {
            LogUtils.e("readfile", ex.getMessage());
        }
        return targetPath;
    }

    public static long getFileSizes(File f) {// 取得文件大小
        long s = 0;
        FileInputStream fis = null;
        try {
            if (f.exists()) {
                fis = new FileInputStream(f);
                f.createNewFile();
                s = fis.available();
            } else {
                LogUtils.e("cn.teamtone", "文件不存在");
            }
        } catch (IOException e) {
            LogUtils.e(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    LogUtils.e(e);
                }
            }
        }
        if (s <= 1024) {
            s = 1;
        } else {
            s = s / 1024;
        }
        return s;
    }

    /**
     * @param filePath
     * @since 2015年7月7日
     */
    public static boolean deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                return file.delete();
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return false;
    }

    public static void deleteDirectory(String dir) {
        if (TextUtils.isEmpty(dir)) {
            return;
        }
        File directoryOrFile = new File(dir);
        if (!directoryOrFile.exists()) {
            return;
        }

        if (!directoryOrFile.isDirectory()) {
            directoryOrFile.delete();
        } else {
            for (File child : directoryOrFile.listFiles()) {
                deleteDirectory(child.getAbsolutePath());
            }
        }

    }

    /**
     * 创建文件父目录
     *
     * @param file
     * @return
     */
    public static boolean createParentDir(File file) {
        boolean isMkdirs = true;
        if (!file.exists()) {
            File dir = file.getParentFile();
            if (!dir.exists()) {
                isMkdirs = dir.mkdirs();
            }
        }
        return isMkdirs;
    }
}
