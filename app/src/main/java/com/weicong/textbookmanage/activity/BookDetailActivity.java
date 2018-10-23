package com.weicong.textbookmanage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.weicong.frankutils124.base.BaseActivity;
import com.weicong.frankutils124.base.BaseHandler;
import com.weicong.frankutils124.utils.ToastUtils;
import com.weicong.textbookmanage.model.BookBean;
import com.weicong.textbookmanage.model.User;
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
 * @time: 2018/4/30 14:33
 * @e-mail: 912220261@qq.com
 * Function:
 */
public class BookDetailActivity extends BaseActivity {
    private LinearLayout detailLl;
    private TextView isbnTv;
    private EditText nameEdt;
    private EditText pressEdt;
    private EditText authorEdt;
    private EditText priceEdt;
    private TextView saveBtn;
    private TextView deleteBtn;

    private BookBean bookBean;
    private MyHandler handler = new MyHandler(this);
    private static final int SAVE_BOOK = 2;
    private static final int DELETE_BOOK = 3;

    @Override
    protected int setLayout() {
        return R.layout.book_detail_activity;
    }

    @Override
    protected void initView() {
        bookBean = (BookBean) getIntent().getSerializableExtra("bookBean");
        detailLl = findViewById(R.id.book_detail_ll);
        isbnTv = findViewById(R.id.book_detail_isbn_tv);
        isbnTv.setText(bookBean.getBookISBN());
        nameEdt = findViewById(R.id.book_detail_name_edt);
        nameEdt.setText(bookBean.getBookName());
        pressEdt = findViewById(R.id.book_detail_press_edt);
        pressEdt.setText(bookBean.getBookPress());
        authorEdt = findViewById(R.id.book_detail_author_edt);
        authorEdt.setText(bookBean.getBookAuthor());
        priceEdt = findViewById(R.id.book_detail_price_edt);
        priceEdt.setText(bookBean.getBookPrice() + "");
        saveBtn = findViewById(R.id.book_detail_save_btn);
        deleteBtn = findViewById(R.id.book_detail_delete_btn);
        if (User.USER_STATUS.equals("学生")) {
            nameEdt.setEnabled(false);
            pressEdt.setEnabled(false);
            authorEdt.setEnabled(false);
            priceEdt.setEnabled(false);
            saveBtn.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {
        detailLl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detailLl.requestFocus();
                hideSoftInput();
                return false;
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBook();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook();
            }
        });
    }

    /**
     * 修改教材信息
     */
    private void saveBook() {
        Map<Object, Object> map = new HashMap<>();
        map.put("isbn", bookBean.getBookISBN());
        map.put("name", nameEdt.getText().toString());
        map.put("press", pressEdt.getText().toString());
        map.put("author", authorEdt.getText().toString());
        map.put("price", Double.parseDouble(priceEdt.getText().toString()));
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse(UrlValue.ENCODING), gson.toJson(map));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(UrlValue.SERVICE + UrlValue.UPDATE_BOOK)
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
                    if (msg.equals("ok")) message.what = SAVE_BOOK;
                    else message.what = UrlValue.MSG_ERROR;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 删除教材
     */
    private void deleteBook() {
        Map<Object, Object> map = new HashMap<>();
        map.put("isbn", bookBean.getBookISBN());
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse(UrlValue.ENCODING), gson.toJson(map));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(UrlValue.SERVICE + UrlValue.DELETE_BOOK)
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
                    if (msg.equals("ok")) message.what = DELETE_BOOK;
                    else message.what = UrlValue.MSG_ERROR;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class MyHandler extends BaseHandler {

        public MyHandler(Activity activity) {
            super(activity);
        }

        @Override
        public void handleMessage(Message message, int what) {
            switch (what) {
                case SAVE_BOOK:
                    ToastUtils.show(BookDetailActivity.this, "修改信息成功！", Toast.LENGTH_LONG);
                    finish();
                    break;
                case DELETE_BOOK:
                    ToastUtils.show(BookDetailActivity.this, "删除教材成功！", Toast.LENGTH_LONG);
                    finish();
                    break;
            }
        }
    }
}
