package com.techscl.ichat.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.easemob.chat.EMMessage;
import com.easemob.util.EMLog;

public class DownloadImageTask extends AsyncTask<EMMessage, Integer, Bitmap> {
    public boolean downloadThumbnail = false;
    Bitmap bitmap = null;
    EMMessage message;
    private DownloadFileCallback callback;
    private String remoteDir;

    public DownloadImageTask(String remoteDir, DownloadFileCallback callback) {
        this.callback = callback;
        this.remoteDir = remoteDir;
    }

    public static String getThumbnailImagePath(String imagePath) {
        String path = imagePath.substring(0, imagePath.lastIndexOf("/") + 1);
        path += "th" + imagePath.substring(imagePath.lastIndexOf("/") + 1, imagePath.length());
        EMLog.d("msg", "original image path:" + imagePath);
        EMLog.d("msg", "thum image path:" + path);
        return path;
    }

    @Override
    protected Bitmap doInBackground(EMMessage... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        callback.afterDownload(result);
    }

    @Override
    protected void onPreExecute() {
        callback.beforeDownload();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        callback.downloadProgress(values[0]);
    }


    public interface DownloadFileCallback {
        void beforeDownload();

        void downloadProgress(int progress);

        void afterDownload(Bitmap bitmap);
    }
}
