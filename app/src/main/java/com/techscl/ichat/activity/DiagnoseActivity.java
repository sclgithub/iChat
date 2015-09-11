package com.techscl.ichat.activity;

import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChat;
import com.easemob.util.EMLog;
import com.techscl.ichat.R;

public class DiagnoseActivity extends BaseGestureActivity implements OnClickListener {
    private TextView currentVersion;
    private Button uploadLog;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose);

        initView();

        uploadLog.setOnClickListener(this);
        String strVersion = "";
        try {
            strVersion = getVersionName();
        } catch (Exception e) {
        }
        if (!TextUtils.isEmpty(strVersion))
            currentVersion.setText("V" + strVersion);
        else {
            String st = getResources().getString(R.string.Not_Set);
            currentVersion.setText(st);
        }
    }

    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle(R.string.diagnose);
        currentVersion = (TextView) findViewById(R.id.tv_version);
        uploadLog = (Button) findViewById(R.id.button_uploadlog);
    }

    public void back(View view) {
        finish();
    }

    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
                0);
        String version = packInfo.versionName;
        return version;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_uploadlog:
                uploadlog();
                break;

            default:
                break;
        }

    }

    public void uploadlog() {

        if (progressDialog == null)
            progressDialog = new ProgressDialog(this);
        String stri = getResources().getString(R.string.Upload_the_log);
        progressDialog.setMessage(stri);
        progressDialog.setCancelable(false);
        progressDialog.show();
        final String st = getResources().getString(R.string.Log_uploaded_successfully);
        EMChat.getInstance().uploadLog(new EMCallBack() {

            String st3 = getResources().getString(R.string.Log_Upload_failed);

            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(DiagnoseActivity.this, st,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onProgress(final int progress, String status) {
                // getActivity().runOnUiThread(new Runnable() {
                //
                // @Override
                // public void run() {
                // progressDialog.setMessage("上传中 "+progress+"%");
                //
                // }
                // });

            }

            @Override
            public void onError(int code, String message) {
                EMLog.e("###", message);
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(DiagnoseActivity.this, st3,
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

}
