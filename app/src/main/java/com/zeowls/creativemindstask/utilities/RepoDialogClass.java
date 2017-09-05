package com.zeowls.creativemindstask.utilities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.zeowls.creativemindstask.R;

/**
 * Created by root on 9/6/17.
 */

public class RepoDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    private final Context mContext;
    private final String mRepoUrl;
    private final String mUserUrl;
    public Button mRepoButton, mUserButton;

    public RepoDialogClass(Context context, String repoUrl, String userUrl) {
        super(context);
        mContext = context;
        mRepoUrl = repoUrl;
        mUserUrl = userUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);
        mRepoButton = findViewById(R.id.btn_repo_url);
        mUserButton = findViewById(R.id.btn_user_url);
        mRepoButton.setOnClickListener(this);
        mUserButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_repo_url:
                getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mRepoUrl)));
                break;
            case R.id.btn_user_url:
                getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mUserUrl)));
                break;
            default:
                break;
        }
        dismiss();
    }
}
