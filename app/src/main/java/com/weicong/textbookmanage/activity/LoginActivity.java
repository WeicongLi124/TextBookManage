package com.weicong.textbookmanage.activity;

import android.app.Activity;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.weicong.frankutils124.base.BaseActivity;
import com.weicong.frankutils124.base.BaseHandler;
import com.weicong.frankutils124.utils.ToastUtils;
import com.weicong.textbookmanage.utils.UrlValue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author: Frank
 * @time: 2018/4/8 13:27
 * @e-mail: 912220261@qq.com
 * Function: 用于登陆操作
 */
public class LoginActivity extends BaseActivity {
    /**
     * 控件变量定义
     */
    private RelativeLayout loginLayout;
    private EditText idEdt;
    private EditText pswEdt;
    private TextView registerTv;
    private RadioGroup statusRg;
    private RadioButton teacherRbtn;
    private RadioButton studentRbtn;
    private TextView loginTv;
    /**
     * 身份字符串
     */
    private String status = "教师";
    /**
     * 线程handler
     */
    private MyHandler handler = new MyHandler(this);

    @Override
    protected int setLayout() {
        return R.layout.login_activity;
    }


    @Override
    protected void initView() {
        setSteepStatusBar();
        loginLayout = findViewById(R.id.login_rl);
        idEdt = findViewById(R.id.login_id_edt);
        pswEdt = findViewById(R.id.login_password_edt);
        registerTv = findViewById(R.id.register_tv);
        statusRg = findViewById(R.id.login_status_rg);
        teacherRbtn = findViewById(R.id.radio_teacher);
        studentRbtn = findViewById(R.id.radio_student);
        loginTv = findViewById(R.id.login_login_tv);
    }

    @Override
    protected void initListener() {
        //页面监听
        loginLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                loginLayout.requestFocus();
                hideSoftInput();
                return false;
            }
        });
        //注册按钮监听
        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToActivity(LoginActivity.this,StatusActivity.class);
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(new Intent(LoginActivity.this,StatusActivity.class), ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this).toBundle());
                }else jumpToActivity(LoginActivity.this,StatusActivity.class);*/
            }
        });
        //身份单选监听
        statusRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()){
                    case R.id.radio_teacher:
                        status = "教师";
                        break;
                    case R.id.radio_student:
                        status = "学生";
                        break;
                }
            }
        });
        //登陆按钮监听
        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginLayout.requestFocus();
                if (idEdt.getText().toString().trim().equals("")||pswEdt.getText().toString().trim().equals("")){
                    ToastUtils.show(LoginActivity.this,"不可为空", Toast.LENGTH_LONG);
                }else login();
            }
        });
    }

    /**
     * 登陆接口对接
     */
    private void login(){
        Map<Object,Object> map = new HashMap<>();
        map.put("status",status);
        map.put("id",idEdt.getText().toString());
        map.put("psw",pswEdt.getText().toString());
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse(UrlValue.ENCODING),gson.toJson(map));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(UrlValue.LOGIN)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String msg = jsonObject.getString("msg");
                    Message message = new Message();
                    if (msg.equals("ok")){
                        message.what = UrlValue.MSG_OK;
                    }else {
                        message.what = UrlValue.MSG_ERROR;
                    }
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class MyHandler extends BaseHandler{

        public MyHandler(Activity activity) {
            super(activity);
        }

        @Override
        public void handleMessage(Message message, int what) {
            switch (what){
                case UrlValue.MSG_OK:
                    ToastUtils.show(LoginActivity.this,"登陆成功！",Toast.LENGTH_LONG);
                    jumpToActivity(LoginActivity.this,MainActivity.class);
                    break;
                case UrlValue.MSG_ERROR:
                    ToastUtils.show(LoginActivity.this,"账号或密码错误！",Toast.LENGTH_LONG);
            }
        }
    }

}
