package com.weicong.textbookmanage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.InputFilter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
 * @time: 2018/4/8 13:27
 * @e-mail: 912220261@qq.com
 * Function: 用于登记注册身份信息
 */
public class RegisterActivity extends BaseActivity {
    private String status = null;
    /**
     * 控件变量定义
     */
    private LinearLayout registerll;
    private TextView titleTv;
    private TextView idTv;
    private RelativeLayout gradeRl;
    private EditText idEdt;
    private EditText nameEdt;
    private EditText pswEdt;
    private RadioGroup sexRagroup;
    private RadioButton maleRabtn;
    private RadioButton femaleRabtn;
    private Spinner facultySp;
    private Spinner gradeSp;
    private TextView finishTv;

    /**
     * 保存页面数据
     */
    private String facultyStr = null;
    private String gradeStr = null;
    private String sexStr = "男";
    private int facultyIndex;
    /**
     * spinner数组数据写死
     */
    private String[] faculty = {"计算机工程学院","土木工程学院","管理学院","外国语学院"};
    private String[][] grade = {
            {"软件工程1班","软件工程2班","计算机与科学技术1班","信息技术与科学1班","网络工程1班","网络工程2班"},
            {"土木工程1班","土木工程2班","水利工程1班","水利工程2班"},
            {"会计学1班","会计学2班","工商管理1班","市场营销1班"},
            {"英语专业1班","英语专业2班","日语专业1班","日语专业2班"}};
    private MyHandler handler = new MyHandler(this);
    private String message;

    @Override
    protected int setLayout() {
        return R.layout.register_activity;
    }

    @Override
    protected void initView() {
        registerll = findViewById(R.id.register_ll);
        titleTv = findViewById(R.id.register_title_tv);
        idTv = findViewById(R.id.register_id_tv);
        gradeRl = findViewById(R.id.register_grade_rl);
        idEdt = findViewById(R.id.register_id_edt);
        nameEdt = findViewById(R.id.register_name_edt);
        pswEdt = findViewById(R.id.register_psw_edt);
        sexRagroup = findViewById(R.id.register_sex_rg);
        maleRabtn = findViewById(R.id.radio_male);
        femaleRabtn = findViewById(R.id.radio_female);
        facultySp = findViewById(R.id.register_faculty_sp);
        gradeSp = findViewById(R.id.register_grade_sp);
        finishTv = findViewById(R.id.register_finish_btn);
        //获取上个页面传来的身份信息
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (null != bundle) {
            status = bundle.getString("status");
        }
        if (status.equals("学生")){
            idTv.setText("学号：");
            idEdt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
            idEdt.setHint("请输入学号");
            gradeRl.setVisibility(View.VISIBLE);
        }else if (status.equals("教师")){
            idTv.setText("工号：");
            idEdt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});
            idEdt.setHint("请输入工号");
            gradeRl.setVisibility(View.GONE);
        }
        titleTv.setText(status+"登记");
        facultySp.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Arrays.asList(faculty)));
        gradeSp.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Arrays.asList(grade[0])));
    }

    @Override
    protected void initListener() {
        //外层布局触摸监听
        registerll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                registerll.requestFocus();
                hideSoftInput();
                return false;
            }
        });
        //性别选择监听
        sexRagroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()){
                    case R.id.radio_male:
                        sexStr = maleRabtn.getText().toString();
                        break;
                    case R.id.radio_female:
                        sexStr = femaleRabtn.getText().toString();
                        break;
                }
            }
        });
        //院系选择监听
        facultySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gradeSp.setAdapter(new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, Arrays.asList(grade[position])));
                facultyIndex = position;
                facultyStr = faculty[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //班级选择监听
        gradeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gradeStr = grade[facultyIndex][position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //完成按钮监听
        finishTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerll.requestFocus();
                if (idEdt.getText().toString().trim().equals("")||nameEdt.toString().trim().equals("")||pswEdt.toString().trim().equals("")){
                    ToastUtils.show(RegisterActivity.this,"不可为空",Toast.LENGTH_LONG);
                }else register();
            }
        });
    }

    /**
     * 对接登记接口
     */
    private void register(){
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);
        map.put("id",idEdt.getText().toString());
        map.put("psw",pswEdt.getText().toString());
        map.put("name",nameEdt.getText().toString());
        map.put("sex",sexStr);
        map.put("dept",facultyStr);
        map.put("grade",gradeStr);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse(UrlValue.ENCODING),gson.toJson(map));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(UrlValue.REGISTER)
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
                        RegisterActivity.this.message = jsonObject.getString("message");
                        message.what = UrlValue.MSG_ERROR;
                    }
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 开启线程处理登记成功与否
     */
    private class MyHandler extends BaseHandler{

        MyHandler(Activity activity) {
            super(activity);
        }

        @Override
        public void handleMessage(Message message, int what) {
            switch (what){
                case UrlValue.MSG_OK:
                    ToastUtils.show(RegisterActivity.this,"登记成功！", Toast.LENGTH_LONG);
                    finish();
                    break;
                case UrlValue.MSG_ERROR:
                    //if (status.equals("教师"))
                        ToastUtils.show(RegisterActivity.this,RegisterActivity.this.message, Toast.LENGTH_LONG);
                    //else
                        //ToastUtils.show(RegisterActivity.this,"登记失败，学号可能已存在！", Toast.LENGTH_LONG);
                    //break;
            }
        }
    }
}
