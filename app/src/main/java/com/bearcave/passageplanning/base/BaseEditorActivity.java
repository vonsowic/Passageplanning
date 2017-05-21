package com.bearcave.passageplanning.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bearcave.passageplanning.R;

import java.io.Serializable;

import butterknife.ButterKnife;

public abstract class BaseEditorActivity<DAO extends Parcelable> extends AppCompatActivity {

    public static final short EDITOR_REQUEST = 1;
    public static final String EDITOR_RESULT = "editor_result";
    public static final String EDITOR_MAIL = "editor_mail";
    public static final short EDITOR_CREATED = 10000;
    public static final short EDITOR_UPDATED = 10001;

    private boolean updateMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_editor);
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(getString(R.string.editor_title));

        Intent mail = getIntent();
        Parcelable tmp = mail.getParcelableExtra(EDITOR_RESULT);
        updateMode = tmp != null;

        if(updateMode) {
            setViewsContent((DAO) tmp);
        }

        getParcelableExtra(mail);

        findViewById(R.id.save_button).setOnClickListener(v -> onSaveButtonClicked());

        FrameLayout content_placeholder = (FrameLayout) findViewById(R.id.content_placeholder);
        content_placeholder.addView(getLayoutInflater().inflate(getContentLayoutId(), null));
    }

    protected void getParcelableExtra(Intent intent){

    }

    protected abstract void setViewsContent(DAO object);

    protected abstract int getContentLayoutId();

    public void onSaveButtonClicked(){
        if (!isAllFilled()){
            Toast.makeText(this, "Fill all gaps", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent returnIntent = new Intent();
        returnIntent.putExtra(EDITOR_RESULT, getFilledDAO());

        if (updateMode){
            setResult(BaseEditorActivity.EDITOR_UPDATED, returnIntent);
        } else {
            setResult(BaseEditorActivity.EDITOR_CREATED, returnIntent);
        }

        finish();
    }

    public abstract boolean isAllFilled();

    protected abstract DAO getFilledDAO();
    
    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        super.onBackPressed();
    }

}
