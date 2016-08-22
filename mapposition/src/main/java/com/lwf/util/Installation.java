package com.lwf.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.lwf.share.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * 在程序安装后第一次运行后生成一个ID实现的，但该方式跟设备唯一标识不一样，它会因为不同的应用程序而产生不同的ID，而不是设备唯一ID。
 *
 * @author liuweiping
 * @since 2015年5月29日
 */
public class Installation {
    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";

    /**
     * 获取设备的唯一标识码，如果不存在就生成。
     *
     * @param context
     * @return
     */
    public synchronized static String id(Context context) {
        if (TextUtils.isEmpty(sID)) {
            File installation = new File(AppUtils.getMainPath(context), INSTALLATION);
            try {
                if (!installation.exists())
                    sID = writeInstallationFile(installation);
                else
                    sID = readInstallationFile(installation);
            } catch (Exception e) {
                LogUtils.e(e);
            }
            if (TextUtils.isEmpty(sID))
                sID = getInstallationFromSP(context);
        }
        return sID;
    }

    private static String getInstallationFromSP(Context context) {
        SharedPreferences sp = context.getSharedPreferences(INSTALLATION, Context.MODE_PRIVATE);
        String id = sp.getString("id", null);
        if (TextUtils.isEmpty(id)) {
            SharedPreferences.Editor editor = sp.edit();
            id = UUID.randomUUID().toString();
            editor.putString("id", id);
            editor.apply();
        }
        return id;
    }

    private static String readInstallationFile(File installation) throws IOException {
        byte[] bytes;
        RandomAccessFile f = null;
        try {
            f = new RandomAccessFile(installation, "r");
            bytes = new byte[(int)f.length()];
            f.readFully(bytes);
        } finally {
            if (f != null) {
                f.close();
            }
        }
        return new String(bytes);
    }

    private static String writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = null;
        String id;
        try {
            out = new FileOutputStream(installation);
            id = UUID.randomUUID().toString();
            out.write(id.getBytes());
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return id;
    }
}
