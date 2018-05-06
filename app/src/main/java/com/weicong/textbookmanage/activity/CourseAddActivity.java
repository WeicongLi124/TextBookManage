package com.weicong.textbookmanage.activity;

import android.app.Activity;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    private Spinner courseNameSp;
    private Spinner facultySp;
    private Spinner gradeSp;
    private TextView creditTv;
    private TextView finishTv;

    private String[] faculty = {"计算机工程学院","土木工程学院","管理学院","外国语学院"};
    private String[][] grade = {
            {"软件工程1班","软件工程2班","计科1班","信科1班","网络工程1班","网络工程2班"},
            {"土木工程1班","土木工程2班","水利工程1班","水利工程2班"},
            {"会计学1班","会计学2班","工商管理1班","市场营销1班"},
            {"英语专业1班","英语专业2班","日语专业1班","日语专业2班"}};
    private String[] courseNameList = {"高等数学(上)","高等数学(下)","离散数学","线性代数","概率论","大学英语",
            "计算机英语","马克思主义哲学","C语言程序设计","PS设计基础","基础日语"};
    private int[] creditList = {4,4,3,3,3,4,4,4,4,2,2};
    private String[] courseIdList = {"01","02","03","04","05","06","07","08","09","10","11"};
    private String gradeStr = grade[0][0];
    private String typeStr = "必修";
    private String courseName = courseNameList[0];
    private String courseId = courseIdList[0];
    private int credit = creditList[0];
    private int facultyIndex;

    private MyHandler handler = new MyHandler(this);

    @Override
    protected int setLayout() {
        return R.layout.course_add_activity;
    }

    @Override
    protected void initView() {
        addLl = findViewById(R.id.course_add_ll);
        courseNameSp = findViewById(R.id.course_add_name_sp);
        typeRagroup = findViewById(R.id.course_add_type_rg);
        requireRabtn = findViewById(R.id.course_radio_require);
        electRabtn = findViewById(R.id.course_radio_elect);
        facultySp = findViewById(R.id.course_add_faculty_sp);
        gradeSp = findViewById(R.id.course_add_grade_sp);
        creditTv = findViewById(R.id.course_add_credit_tv);
        finishTv = findViewById(R.id.course_add_finish_btn);

        courseNameSp.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Arrays.asList(courseNameList)));
        facultySp.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Arrays.asList(faculty)));
        gradeSp.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Arrays.asList(grade[0])));
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
        courseNameSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                creditTv.setText(creditList[position]+"分");
                courseName = courseNameList[position];
                credit = creditList[position];
                courseId = courseIdList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //院系选择监听
        facultySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gradeSp.setAdapter(new ArrayAdapter(CourseAddActivity.this, android.R.layout.simple_spinner_dropdown_item, Arrays.asList(grade[position])));
                facultyIndex = position;
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
        //课程类型选择
        typeRagroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()){
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
                insertCourse();
            }
        });
    }

    /**
     * 添加课程信息
     */
    private void insertCourse(){
        Map<Object,Object> map = new HashMap<>();
        map.put("courseId",courseId);
        map.put("courseName",courseName);
        map.put("teacherId", User.USER_ID);
        map.put("grade",gradeStr);
        map.put("type",typeStr);
        map.put("credit",credit);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse(UrlValue.ENCODING),gson.toJson(map));
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(UrlValue.INSERT_COURSE)
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
                    ToastUtils.show(CourseAddActivity.this,"录入成功！", Toast.LENGTH_LONG);
                    finish();
                    break;
                case UrlValue.MSG_ERROR:
                    ToastUtils.show(CourseAddActivity.this,"错误，请检查班级或类型", Toast.LENGTH_LONG);
                    break;
            }

        }
    }
}
