package com.weicong.textbookmanage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.weicong.frankutils124.base.BaseActivity;
import com.weicong.textbookmanage.utils.UrlValue;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
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
 * Function:
 */
public class RegisterActivity extends BaseActivity {
    private String status = null;

    private LinearLayout registerll;
    private TextView titleTv;
    private TextView idTv;
    private RelativeLayout gradeRl;
    private EditText idEdt;
    private EditText nameEdt;
    private RadioGroup sexRagroup;
    private RadioButton maleRabtn;
    private RadioButton femaleRabtn;
    private Spinner facultySp;
    private Spinner gradeSp;
    private TextView finishTv;

    private String facultyStr = null;
    private String gradeStr = null;
    private String sexStr = "男";
    private int facultyIndex;
    private String[] faculty = {"计算机工程学院","土木工程学院","管理学院","外国语学院"};
    private String[][] grade = {
            {"软件工程1班","软件工程2班","计算机与科学技术1班","信息技术与科学1班","网络工程1班","网络工程2班"},
            {"土木工程1班","土木工程2班","水利工程1班","水利工程2班"},
            {"会计学1班","会计学2班","工商管理1班","市场营销1班"},
            {"英语专业1班","英语专业2班","日语专业1班","日语专业2班"}};
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
            gradeRl.setVisibility(View.VISIBLE);
        }else if (status.equals("教师")){
            idTv.setText("工号：");
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
//                Log.i("registerTeacher",idEdt.getText()+" "+nameEdt.getText()+" "+sexStr+" "+facultyStr+" "+gradeStr);
                register();
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
        map.put("name",nameEdt.getText().toString());
        map.put("sex",sexStr);
        map.put("dept",facultyStr);
        map.put("grade",gradeStr);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse(UrlValue.ENCODING),gson.toJson(map));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(UrlValue.REGISTER_TEACHER)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("onFailure","Error:"+e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("onResponse",response.body().string());
            }
        });
    }
}
