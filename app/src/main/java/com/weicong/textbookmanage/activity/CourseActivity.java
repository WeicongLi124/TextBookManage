package com.weicong.textbookmanage.activity;

import android.app.Activity;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weicong.frankutils124.base.BaseActivity;
import com.weicong.frankutils124.base.BaseHandler;

import com.weicong.textbookmanage.model.CourseBean;
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
    private RelativeLayout courseRl;
    private ListView courseLv;
    private FloatingActionButton addBtn;
    private CourseAdapter courseAdapter;
    private List<CourseBean> courseBeanList;

    private MyHandler handler = new MyHandler(this);
    @Override
    protected int setLayout() {
        return R.layout.course_activity;
    }

    @Override
    protected void initView() {
        courseRl = findViewById(R.id.course_rl);
        courseLv = findViewById(R.id.course_content_list);
        courseLv.setDividerHeight(0);
        addBtn = findViewById(R.id.course_add_btn);
        getList();
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
        courseRl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                courseRl.requestFocus();
                hideSoftInput();
                return false;
            }
        });
    }

    private void getList(){
        Map<Object,Object> map = new HashMap<>();
        map.put("status","学生");
        //map.put("id","1001001");
        map.put("grade","软件工程2班");
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse(UrlValue.ENCODING),gson.toJson(map));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(UrlValue.GET_COURSE_LIST)
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
                    Log.i("onResponse",jsonObject.toString());
                    Gson gson1 = new Gson();
                    courseBeanList = new ArrayList<>();
                    courseBeanList = gson1.fromJson(jsonObject.getString("list"),
                            new TypeToken<List<CourseBean>>(){}.getType());
                    Message message = new Message();
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    class MyHandler extends BaseHandler{

        public MyHandler(Activity activity) {
            super(activity);
        }

        @Override
        public void handleMessage(Message message, int what) {
            courseAdapter = new CourseAdapter(CourseActivity.this,courseBeanList);
            courseLv.setAdapter(courseAdapter);
        }
    }
}
