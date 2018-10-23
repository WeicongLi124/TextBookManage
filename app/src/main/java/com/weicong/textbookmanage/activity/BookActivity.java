package com.weicong.textbookmanage.activity;

import android.app.Activity;
import android.content.Intent;
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
import com.weicong.textbookmanage.model.BookBean;
import com.weicong.textbookmanage.model.CourseBean;
import com.weicong.textbookmanage.model.User;
import com.weicong.textbookmanage.other.BookAdapter;
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
 * @time: 2018/4/16 22:03
 * @e-mail: 912220261@qq.com
 * Function:
 */
public class BookActivity extends BaseActivity {
    private EditText searchEdt;
    private Button searchBtn;
    private ListView bookLv;
    private FloatingActionButton addBtn;

    private List<BookBean> bookBeanList;
    private BookAdapter bookAdapter;
    private MyHandler handler = new MyHandler(this);

    @Override
    protected int setLayout() {
        return R.layout.book_activity;
    }

    @Override
    protected void initView() {
        searchEdt = findViewById(R.id.book_search_edt);
        searchBtn = findViewById(R.id.book_search_btn);
        bookLv = findViewById(R.id.book_lv);
        bookLv.setDividerHeight(0);
        addBtn = findViewById(R.id.book_add_btn);
        if (User.USER_STATUS.equals("学生")) {
            addBtn.setVisibility(View.GONE);
        }
        searchBook();
    }

    @Override
    protected void initListener() {
        bookLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BookActivity.this, BookDetailActivity.class);
                intent.putExtra("bookBean", bookBeanList.get(position));
                startActivity(intent);
            }
        });
        bookLv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                bookLv.requestFocus();
                hideSoftInput();
                return false;
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToActivity(BookActivity.this, BookAddActivity.class);
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBook();
            }
        });
    }

    /**
     * 获取教材列表
     */
    private void searchBook() {
        Map<Object, Object> map = new HashMap<>();
        map.put("keywords", searchEdt.getText().toString().trim());
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse(UrlValue.ENCODING), gson.toJson(map));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(UrlValue.SERVICE + UrlValue.SEARCH_BOOK)
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
                    Gson gson1 = new Gson();
                    bookBeanList = new ArrayList<>();
                    bookBeanList = gson1.fromJson(jsonObject.getString("list"),
                            new TypeToken<List<BookBean>>() {
                            }.getType());
                    Message message = new Message();
                    if (jsonObject.getString("msg").equals("ok"))
                        message.what = UrlValue.MSG_OK;
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
        searchBook();
    }

    private class MyHandler extends BaseHandler {

        public MyHandler(Activity activity) {
            super(activity);
        }

        @Override
        public void handleMessage(Message message, int what) {
            switch (what) {
                case UrlValue.MSG_ERROR:
                    ToastUtils.show(BookActivity.this, "操作失败！", Toast.LENGTH_LONG);
                    break;
                case UrlValue.MSG_OK:
                    bookAdapter = new BookAdapter(BookActivity.this, bookBeanList);
                    bookLv.setAdapter(bookAdapter);
                    break;
            }
        }
    }
}
