package com.weicong.textbookmanage.activity;

import android.app.Activity;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.weicong.frankutils124.base.BaseActivity;
import com.weicong.frankutils124.base.BaseHandler;
import com.weicong.frankutils124.utils.ToastUtils;
import com.weicong.textbookmanage.model.User;
import com.weicong.textbookmanage.utils.UrlValue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
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
 * @time: 2018/4/22 12:02
 * @e-mail: 912220261@qq.com
 * Function:
 */
public class CourseAddActivity extends BaseActivity {
    private LinearLayout addLl;
    private RadioGroup typeRagroup;
    private RadioButton requireRabtn;
    private RadioButton electRabtn;
    private EditText courseNameEdt;
    private EditText creditEdt;
    private TextView finishTv;

    private String typeStr = "必修";

    private MyHandler handler = new MyHandler(this);

    @Override
    protected int setLayout() {
        return R.layout.course_add_activity;
    }

    @Override
    protected void initView() {
        addLl = findViewById(R.id.course_add_ll);
        courseNameEdt = findViewById(R.id.course_add_name_edt);
        typeRagroup = findViewById(R.id.course_add_type_rg);
        requireRabtn = findViewById(R.id.course_radio_require);
        electRabtn = findViewById(R.id.course_radio_elect);

        creditEdt = findViewById(R.id.course_add_credit_edt);
        finishTv = findViewById(R.id.course_add_finish_btn);
    }

    @Override
    protected void initListener() {
        addLl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                addLl.requestFocus();
                hideSoftInput();
                return false;
            }
        });
        //课程类型选择
        typeRagroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.course_radio_require:
                        typeStr = requireRabtn.getText().toString();
                        break;
                    case R.id.course_radio_elect:
                        typeStr = electRabtn.getText().toString();
                        break;
                }
            }
        });
        finishTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (courseNameEdt.getText().toString().equals("") || creditEdt.getText().toString().equals("")) {
                    ToastUtils.show(CourseAddActivity.this, "不可为空", Toast.LENGTH_LONG);
                } else {
                    int credit = Integer.parseInt(creditEdt.getText().toString());
                    if (credit < 0 || credit > 4) {
                        ToastUtils.show(CourseAddActivity.this, "学分范围：0~4", Toast.LENGTH_LONG);
                    } else insertCourse(credit);
                }
            }
        });
    }

    /**
     * 添加课程信息
     */
    private void insertCourse(int credit) {
        Map<Object, Object> map = new HashMap<>();
        map.put("courseName", courseNameEdt.getText().toString());
        map.put("type", typeStr);
        map.put("credit", credit);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse(UrlValue.ENCODING), gson.toJson(map));
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(UrlValue.SERVICE + UrlValue.INSERT_COURSE)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String msg = jsonObject.getString("msg");
                    Message message = new Message();
                    if (msg.equals("ok")) {
                        message.what = UrlValue.MSG_OK;
                    } else {
                        message.what = UrlValue.MSG_ERROR;
                    }
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private class MyHandler extends BaseHandler {

        public MyHandler(Activity activity) {
            super(activity);
        }

        @Override
        public void handleMessage(Message message, int what) {
            switch (what) {
                case UrlValue.MSG_OK:
                    ToastUtils.show(CourseAddActivity.this, "录入成功！", Toast.LENGTH_LONG);
                    finish();
                    break;
                case UrlValue.MSG_ERROR:
                    ToastUtils.show(CourseAddActivity.this, "此班级已存在此课程", Toast.LENGTH_LONG);
                    break;
            }

        }
    }
}
