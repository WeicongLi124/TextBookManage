package com.weicong.textbookmanage.activity;

import android.app.Activity;
import android.os.Message;
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
 * @time: 2018/4/25 22:08
 * @e-mail: 912220261@qq.com
 * Function:
 */
public class BookAddActivity extends BaseActivity {
    private LinearLayout bookLl;
    private EditText isbnEdt;
    private EditText nameEdt;
    private EditText pressEdt;
    private EditText authorEdt;
    private EditText priceEdt;
    private TextView submitBtn;
    private MyHandler handler = new MyHandler(this);

    @Override
    protected int setLayout() {
        return R.layout.book_add_activity;
    }

    @Override
    protected void initView() {
        bookLl = findViewById(R.id.book_add_ll);
        isbnEdt = findViewById(R.id.book_add_isbn_edt);
        nameEdt = findViewById(R.id.book_add_name_edt);
        pressEdt = findViewById(R.id.book_add_press_edt);
        authorEdt = findViewById(R.id.book_add_author_edt);
        priceEdt = findViewById(R.id.book_add_price_edt);
        submitBtn = findViewById(R.id.book_add_submit_btn);
    }

    @Override
    protected void initListener() {
        bookLl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                bookLl.requestFocus();
                hideSoftInput();
                return false;
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNull(isbnEdt)||isNull(nameEdt)||isNull(pressEdt)||isNull(authorEdt)||isNull(priceEdt)){
                    ToastUtils.show(BookAddActivity.this,"不可留空！",Toast.LENGTH_LONG);
                }else insertBook();
            }
        });
    }

    /**
     * 添加教材信息
     */
    private void insertBook(){
        Map<Object,Object> map = new HashMap<>();
        map.put("isbn",isbnEdt.getText().toString());
        map.put("name",nameEdt.getText().toString());
        map.put("press",pressEdt.getText().toString());
        map.put("author",authorEdt.getText().toString());
        map.put("price",Double.parseDouble(priceEdt.getText().toString()));
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse(UrlValue.ENCODING),gson.toJson(map));
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(UrlValue.SERVICE+UrlValue.INSERT_BOOK)
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

    /**
     * 判断输入是否为空
     * @param editText
     * @return
     */
    private boolean isNull(EditText editText){
        if (editText.getText().toString().trim().equals("")){
            return true;
        }else return false;
    }

    private class MyHandler extends BaseHandler {

        public MyHandler(Activity activity) {
            super(activity);
        }

        @Override
        public void handleMessage(Message message, int what) {
            switch (what){
                case UrlValue.MSG_OK:
                    ToastUtils.show(BookAddActivity.this,"录入成功！", Toast.LENGTH_LONG);
                    finish();
                    break;
                case UrlValue.MSG_ERROR:
                    ToastUtils.show(BookAddActivity.this,"错误，请检查ISBN号", Toast.LENGTH_LONG);
                    break;
            }

        }
    }
}
