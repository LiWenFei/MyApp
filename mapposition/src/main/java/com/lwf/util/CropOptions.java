package com.lwf.util;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.lwf.base.IShowToast;
import com.lwf.base.MyApplication;
import com.lwf.mapposition.R;
import com.lwf.share.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 图片裁切功能。
 *
 * @author LiuWeiping
 * @since 2014年11月18日
 */
public class CropOptions {
    /**
     * 裁剪的Action。
     */
    private static final String ACTION_CROP = "com.android.camera.action.CROP";
    public final static String FILE_TEMP_PATH = AppUtils.getMainPath(MyApplication.getInstance()) + "/crop/image.jpg";
    public final static String FILE_RESULT_PATH = AppUtils.getMainPath(MyApplication.getInstance()) + "/crop/crop.jpg";
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;
    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    private static final int CROP_BIG_HEIGHT = 360;
    private static final int CROP_BIG_WIDTH = 720;
    private Activity activity;
    private int height = CROP_BIG_HEIGHT;
    private int width = CROP_BIG_WIDTH;
    private int mRequestType;
    private boolean isFaceDetection;

    /**
     * 不限制裁切比例。
     *
     * @param activity Activity
     */
    public CropOptions(Activity activity) {
        this(activity, 0, 0);
    }

    /**
     * 根据宽高的值，按比例裁切。如果width和height都是0，则不限制裁切比例。
     *
     * @param activity Activity
     * @param width    裁切后的图片宽度。
     * @param height   裁切后的图片高度。
     */
    public CropOptions(Activity activity, int width, int height) {
        super();
        this.activity = activity;
        initFilePath();
        this.width = width;
        this.height = height;
    }

    private static void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    /**
     * 在Activity的onActivityResult中调用，在回调listener中获取裁切结果。
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @param listener    裁切图片的状态监听。
     * @since 2014年11月19日
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data, CropListener listener) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        Bitmap bitmap;
        switch (requestCode ^ mRequestType) {
            case REQUEST_CODE_GALLERY:
                InputStream inputStream = null;
                FileOutputStream fileOutputStream = null;
                try {
                    inputStream = activity.getContentResolver().openInputStream(data.getData());
                    fileOutputStream = new FileOutputStream(FILE_TEMP_PATH);
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();

                    startCropImage();

                } catch (Exception e) {
                    LogUtils.e(e);
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            LogUtils.e(e);
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            LogUtils.e(e);
                        }
                    }
                }
                break;
            case REQUEST_CODE_TAKE_PICTURE:
                startCropImage();
                break;
            case REQUEST_CODE_CROP_IMAGE:
                //			String path = data.getStringExtra(CropImage.IMAGE_PATH);
                //			if (path == null) {
                //				return;
                //			}
                bitmap = BitmapFactory.decodeFile(FILE_RESULT_PATH);
                if (listener != null) {
                    listener.afterCrop(FILE_RESULT_PATH, bitmap);
                }
                // 删除源文件，不删除裁切后的文件。
                File sourceFile = new File(FILE_TEMP_PATH);
                sourceFile.delete();
                break;
        }
    }

    /**
     * 使用照相机拍摄图片。
     *
     * @since 2014年11月19日
     */
    public void openCamera() {
        openCamera(0);
    }

    /**
     * 自定义请求码打开照相机选择图片。会使用requestCode与{@link #REQUEST_CODE_TAKE_PICTURE}进行异或运算，便于同一个页面调用不同的裁切模块。
     *
     * @param requestType 0 - 使用默认请求码。
     * @since 2014年11月21日
     */
    public void openCamera(int requestType) {
        if (activity == null) {
            return;
        }

        // 添加拍照的权限
        // 添加读SD卡权限的权限
        if (!AppUtils.checkPermissionsForM(activity, 0, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            return;
        }
        this.mRequestType = requestType;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri mImageCaptureUri = Uri.fromFile(new File(FILE_TEMP_PATH));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        intent.putExtra("return-data", false);

        if (AppUtils.isIntentAvailable(activity, intent)) {
            try {
                activity.startActivityForResult(intent, requestType ^ REQUEST_CODE_TAKE_PICTURE);
                return;
            } catch (ActivityNotFoundException anfe) {
                LogUtils.e(anfe);
            }
        }
        AppUtils.showToast(activity, R.string.not_supported);
    }

    /**
     * 打开相册选择图片。
     *
     * @since 2014年11月19日
     */
    public void openGallery() {
        openGallery(0);
    }

    /**
     * 自定义请求码打开相册选择图片。会使用requestType与{@link #REQUEST_CODE_GALLERY}进行异或运算，便于同一个页面调用不同的裁切模块。
     *
     * @param requestType 0 - 使用默认请求码。
     * @since 2014年11月21日
     */
    public void openGallery(int requestType) {
        if (activity == null) {
            return;
        }
        this.mRequestType = requestType;
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        if (AppUtils.isIntentAvailable(activity, photoPickerIntent)) {
            try {
                activity.startActivityForResult(photoPickerIntent, requestType ^ REQUEST_CODE_GALLERY);
                return;
            } catch (ActivityNotFoundException anfe) {
                LogUtils.e(anfe);
            }
        }
        if (activity instanceof IShowToast) {
            ((IShowToast)activity).showMsg(R.string.not_supported);
        }
    }

    private void initFilePath() {
        FileOperate.createParentDir(new File(FILE_TEMP_PATH));
        FileOperate.createParentDir(new File(FILE_RESULT_PATH));
    }

    /**
     * 打开裁切页面。
     *
     * @since 2014年11月19日
     */
    private void startCropImage() {
        if (activity == null) {
            return;
        }
        // 使用系统裁切功能。
        Intent intent = new Intent(ACTION_CROP);
        Uri source = Uri.fromFile(new File(FILE_TEMP_PATH));
        Uri output = Uri.fromFile(new File(FILE_RESULT_PATH));
        intent.setDataAndType(source, "image/*");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        else
            intent.putExtra("outputFormat", Bitmap.CompressFormat.WEBP.toString());
        intent.putExtra("noFaceDetection", !isFaceDetection);
        intent.putExtra("return-data", false);
        intent.putExtra("scale", true);
        intent.putExtra("crop", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
        // 设置比例
        if (width > 0 && height > 0) {
            intent.putExtra("aspectX", width);
            intent.putExtra("aspectY", height);
            intent.putExtra("outputX", width);
            intent.putExtra("outputY", height);
        }
        if (AppUtils.isIntentAvailable(activity, intent)) {
            try {
                activity.startActivityForResult(intent, mRequestType ^ REQUEST_CODE_CROP_IMAGE);
            } catch (ActivityNotFoundException anfe) {
                LogUtils.e(anfe);
                if (activity instanceof IShowToast) {
                    ((IShowToast)activity).showMsg(R.string.not_supported);
                }
            }
        } else {
            if (activity instanceof IShowToast) {
                ((IShowToast)activity).showMsg(R.string.not_supported);
            }
        }
    }

    /**
     * 获取是否使用人脸识别。
     *
     * @return the isFaceDetection。
     */
    public boolean isFaceDetection() {
        return isFaceDetection;
    }

    /**
     * 设置是否使用人脸识别。
     *
     * @param isFaceDetection the isFaceDetection。
     */
    public void setFaceDetection(boolean isFaceDetection) {
        this.isFaceDetection = isFaceDetection;
    }

    /**
     * 裁切图片的状态监听。
     *
     * @author LiuWeiping
     * @since 2014年11月19日
     */
    public interface CropListener {
        /**
         * 裁切完成后，获取到最终图片的地址和Bitmap。如果是大图，只会获得imagePath。
         *
         * @param imagePath 图片地址。
         * @param bitmap    小图的Bitmap。
         * @since 2014年11月19日
         */
        void afterCrop(String imagePath, Bitmap bitmap);

        /**
         * 失败。
         *
         * @param msg 错误信息。
         * @since 2014年11月19日
         */
        void onError(String msg);

        /**
         * 获取到原始图片的地址，可能来自相册，也可能来自照相机。
         *
         * @param imagePath 图片地址。
         * @since 2014年11月19日
         */
        void receivedOriginalPic(String imagePath);
    }
}
