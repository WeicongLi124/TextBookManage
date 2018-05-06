package com.weicong.textbookmanage.activity;

import android.app.Activity;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weicong.frankutils124.base.BaseActivity;
import com.weicong.frankutils124.base.BaseHandler;

import com.weicong.frankutils124.utils.ToastUtils;
import com.weicong.textbookmanage.model.CourseBean;
import com.weicong.textbookmanage.model.User;
import com.weicong.textbookmanage.other.CourseAdapter;
import com.weicong.textbookmanage.utils.UrlValue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 * @time: 2018/4/16 22:01
 * @e-mail: 912220261@qq.com
 * Function:
 */
public class CourseActivity extends BaseActivity {
    private ListView courseLv;
    private FloatingActionButton addBtn;
    private EditText searchEdt;
    private Button searchBtn;
    private CourseAdapter courseAdapter;
    private List<CourseBean> courseBeanList;

    private MyHandler handler = new MyHandler(this);
    @Override
    protected int setLayout() {
        return R.layout.course_activity;
    }

    @Override
    protected void initView() {
        courseLv = findViewById(R.id.course_lv);
        courseLv.setDividerHeight(0);
        addBtn = findViewById(R.id.course_add_btn);
        searchEdt = findViewById(R.id.course_search_edt);
        searchBtn = findViewById(R.id.course_search_btn);
        if (User.USER_STATUS.equals("学生")){
            addBtn.setVisibility(View.GONE);
        }
        searchCourse();
    }

    @Override
    protected void initListener() {
        courseLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("onItemClick",position+"");
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToActivity(CourseActivity.this,CourseAddActivity.class);
            }
        });
        courseLv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                courseLv.requestFocus();
                hideSoftInput();
                return false;
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCourse();
            }
        });
    }

    /**
     * 获取课程信息
     */
    private void searchCourse(){
        Map<Object,Object> map = new HashMap<>();
        map.put("keywords",searchEdt.getText().toString());
        map.put("status",User.USER_STATUS);
        map.put("id",User.USER_ID);
        if (User.USER_STATUS.equals("学生")) {
            map.put("grade", User.USER_GRADE);
        }
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse(UrlValue.ENCODING),gson.toJson(map));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(UrlValue.SEARCH_COURSE)
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
                    Log.i("onResponse",jsonObject.toString());
                    Gson gson1 = new Gson();
                    courseBeanList = new ArrayList<>();
                    courseBeanList = gson1.fromJson(jsonObject.getString("list"),
                            new TypeToken<List<CourseBean>>(){}.getType());
                    Message message = new Message();
                    if (jsonObject.getString("msg").equals("ok"))
                        message.what = SEARCH;
                    else message.what = UrlValue.MSG_ERROR;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchCourse();
    }

    private static final int GET_LIST = 1;
    private static final int SEARCH = 2;
    class MyHandler extends BaseHandler{

        public MyHandler(Activity activity) {
            super(activity);
        }

        @Override
        public void handleMessage(Message message, int what) {
            switch (what){
                case UrlValue.MSG_ERROR:
                    ToastUtils.show(CourseActivity.this,"操作失败！", Toast.LENGTH_LONG);
                    break;
                case GET_LIST:
                    courseAdapter = new CourseAdapter(CourseActivity.this,courseBeanList);
                    courseLv.setAdapter(courseAdapter);
                    break;
                case SEARCH:
                    courseAdapter = new CourseAdapter(CourseActivity.this,courseBeanList);
                    courseLv.setAdapter(courseAdapter);
                    break;
            }

        }
    }
}
