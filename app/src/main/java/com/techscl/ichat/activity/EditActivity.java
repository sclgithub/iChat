package com.techscl.ichat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.techscl.ichat.R;


public class EditActivity extends BaseGestureActivity {
    private EditText editText;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_edit);
    
        initView();

        String title = getIntent().getStringExtra("title");
        String data = getIntent().getStringExtra("data");
        if (title != null)
            toolbar.setTitle(title);
        if (data != null)
            editText.setText(data);
        editText.setSelection(editText.length());

    }

    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.Change_the_group_name));
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        editText = (EditText) findViewById(R.id.edittext);
    }


    public void save(View view) {
        setResult(RESULT_OK, new Intent().putExtra("data", editText.getText().toString()));
        finish();
    }
}
