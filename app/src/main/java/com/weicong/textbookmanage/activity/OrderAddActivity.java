package com.weicong.textbookmanage.activity;

import android.app.Activity;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weicong.frankutils124.base.BaseActivity;
import com.weicong.frankutils124.base.BaseHandler;
import com.weicong.frankutils124.utils.ToastUtils;
import com.weicong.textbookmanage.model.BookBean;
import com.weicong.textbookmanage.model.User;
import com.weicong.textbookmanage.utils.UrlValue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
 * @time: 2018/4/30 21:00
 * @e-mail: 912220261@qq.com
 * Function:
 */
public class OrderAddActivity extends BaseActivity {
    private LinearLayout orderLl;
    private Spinner bookSp;
    private Spinner courseSp;
    private TextView teacherTv;
    private Spinner facultySp;
    private Spinner gradeSp;
    private EditText numbersEdt;
    private TextView priceTv;
    private TextView totalTv;
    private TextView submitBtn;

    private String[] faculty = {"计算机工程学院","土木工程学院","管理学院","外国语学院"};
    private String[][] grade = {
            {"软件工程1班","软件工程2班","计科1班","信科1班","网络工程1班","网络工程2班"},
            {"土木工程1班","土木工程2班","水利工程1班","水利工程2班"},
            {"会计学1班","会计学2班","工商管理1班","市场营销1班"},
            {"英语专业1班","英语专业2班","日语专业1班","日语专业2班"}};
    private String[] courseNameList = {"高等数学(上)","高等数学(下)","离散数学","线性代数","概率论","大学英语",
            "计算机英语","马克思主义哲学","C语言程序设计","PS设计基础","基础日语"};
    private int facultyIndex = 0;
    private int bookIndex = 0;
    private String bookId;
    private String courseName = courseNameList[0];
    private String bookName;
    private String gradeStr = grade[0][0];
    private int numbers = 0;
    private double total = 0;
    private List<BookBean> bookBeanList;
    private List<String> bookList;
    private MyHandler handler = new MyHandler(this);

    @Override
    protected int setLayout() {
        return R.layout.order_add_activity;
    }

    @Override
    protected void initView() {
        orderLl = findViewById(R.id.order_add_ll);
        bookSp = findViewById(R.id.order_add_book_sp);
        courseSp = findViewById(R.id.order_add_course_sp);
        teacherTv = findViewById(R.id.order_add_teacher_tv);
        facultySp = findViewById(R.id.order_add_faculty_sp);
        gradeSp = findViewById(R.id.order_add_grade_sp);
        numbersEdt = findViewById(R.id.order_add_numbers_edt);
        priceTv = findViewById(R.id.order_add_price_edt);
        totalTv = findViewById(R.id.order_add_total_edt);
        submitBtn = findViewById(R.id.order_add_submit_btn);

        teacherTv.setText(User.USER_NAME);
        totalTv.setText(total+"¥");
        courseSp.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Arrays.asList(courseNameList)));
        facultySp.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Arrays.asList(faculty)));
        gradeSp.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Arrays.asList(grade[0])));
        getBookList();
    }

    @Override
    protected void initListener() {
        orderLl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                orderLl.requestFocus();
                hideSoftInput();
                return false;
            }
        });
        courseSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseName = courseNameList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        facultySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gradeSp.setAdapter(new ArrayAdapter(OrderAddActivity.this, android.R.layout.simple_spinner_dropdown_item, Arrays.asList(grade[position])));
                facultyIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gradeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gradeStr = grade[facultyIndex][position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        bookSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bookIndex = position;
                bookName = bookBeanList.get(position).getBookName();
                bookId = bookBeanList.get(position).getBookISBN();
                priceTv.setText(bookBeanList.get(position).getBookPrice()+"¥");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //输入征订书本的数量进行处理
        numbersEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (numbersEdt.getText().toString().equals("")){
                    numbers = 0;
                }else numbers = Integer.parseInt(numbersEdt.getText().toString());
                total = numbers * bookBeanList.get(bookIndex).getBookPrice();
                totalTv.setText(total+"¥");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numbers == 0){
                    ToastUtils.show(OrderAddActivity.this,"数量不可为空",Toast.LENGTH_LONG);
                }else insertOrder();
            }
        });
    }

    /**
     * 获取书名
     */
    private void getBookList(){
        Map<Object,Object> map = new HashMap<>();
        map.put("keywords","");
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse(UrlValue.ENCODING),gson.toJson(map));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(UrlValue.GET_BOOK_LIST)
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
                            new TypeToken<List<BookBean>>(){}.getType());
                    bookList = new ArrayList<>();
                    for (int i = 0; i < bookBeanList.size(); i++){
                        bookList.add("《"+bookBeanList.get(i).getBookName()+"》");
                    }
                    bookName = bookBeanList.get(0).getBookName();
                    bookId = bookBeanList.get(0).getBookISBN();
                    Message message = new Message();
                    if (jsonObject.getString("msg").equals("ok"))
                        message.what = GET_BOOK_LIST;
                    else message.what = UrlValue.MSG_ERROR;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 添加订单
     */
    private void insertOrder(){
        Map<Object,Object> map = new HashMap<>();
        map.put("bookId",bookId);
        map.put("teacherId",User.USER_ID);
        map.put("courseName",courseName);
        map.put("bookName",bookName);
        map.put("teacherName",User.USER_NAME);
        map.put("grade",gradeStr);
        map.put("total",total);
        map.put("numbers",numbers);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse(UrlValue.ENCODING),gson.toJson(map));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(UrlValue.INSERT_ORDER)
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

    private static final int GET_BOOK_LIST = 2;
    class MyHandler extends BaseHandler{

        public MyHandler(Activity activity) {
            super(activity);
        }

        @Override
        public void handleMessage(Message message, int what) {
            switch (what){
                case UrlValue.MSG_OK:
                    ToastUtils.show(OrderAddActivity.this,"征订成功！", Toast.LENGTH_LONG);
                    finish();
                    break;
                case UrlValue.MSG_ERROR:
                    ToastUtils.show(OrderAddActivity.this,"发生错误！", Toast.LENGTH_LONG);
                    break;
                case GET_BOOK_LIST:
                    bookSp.setAdapter(new ArrayAdapter(OrderAddActivity.this, android.R.layout.simple_spinner_dropdown_item,bookList));
                    break;
            }
        }
    }
}
