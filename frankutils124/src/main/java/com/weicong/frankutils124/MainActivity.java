package com.weicong.frankutils124;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.weicong.frankutils124.base.BaseActivity;
import com.weicong.frankutils124.base.PeopleMessage;
import com.weicong.frankutils124.utils.BitmapUtils;
import com.weicong.frankutils124.utils.PermissionUtils;
import com.weicong.frankutils124.utils.PhoneUtils;
import com.weicong.frankutils124.utils.ToastUtils;

public class MainActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback{
    private String TAG = "测试";
    private Button button;
    private EditText editText;


    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        button = findViewById(R.id.click);
        editText = findViewById(R.id.textView);
    }

    @Override
    protected void initListener() {
        PeopleMessage.Builder peopleMessage = new PeopleMessage.Builder();
        peopleMessage.build();
        Log.i(TAG, "initListener: "+peopleMessage.toString());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PermissionUtils.READ_PHONE_STATE:
                ToastUtils.show(MainActivity.this,
                        PhoneUtils.getDeviceId(this) + "  "
                                + PhoneUtils.getMEID(this) + "  " + PhoneUtils.getIMSI(this), Toast.LENGTH_SHORT);
                break;
        }
    }

}
