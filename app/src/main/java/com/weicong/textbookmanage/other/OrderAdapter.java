package com.weicong.textbookmanage.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.weicong.textbookmanage.activity.R;
import com.weicong.textbookmanage.model.OrderBean;

import java.util.List;

/**
 * @author: Frank
 * @time: 2018/4/30 21:03
 * @e-mail: 912220261@qq.com
 * Function:
 */
public class OrderAdapter extends BaseAdapter {
    private List<OrderBean> orderBeanList;
    private LayoutInflater layoutInflater;

    public OrderAdapter(Context context, List<OrderBean> orderBeanList) {
        this.orderBeanList = orderBeanList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return orderBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.order_item, parent, false);
            viewHolder.bookTv = convertView.findViewById(R.id.order_book_tv);
            viewHolder.courseTv = convertView.findViewById(R.id.order_course_tv);
            viewHolder.teacherTv = convertView.findViewById(R.id.order_teacher_tv);
            viewHolder.gradeTv = convertView.findViewById(R.id.order_grade_tv);
            viewHolder.numbersTv = convertView.findViewById(R.id.order_numbers_tv);
            viewHolder.totalTv = convertView.findViewById(R.id.order_total_tv);
            viewHolder.priceTv = convertView.findViewById(R.id.order_price_tv);
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.bookTv.setText("《" + orderBeanList.get(position).getBookName() + "》");
        viewHolder.courseTv.setText(orderBeanList.get(position).getCourseName());
        viewHolder.teacherTv.setText(orderBeanList.get(position).getTeacherName());
        viewHolder.gradeTv.setText(orderBeanList.get(position).getGrade());
        viewHolder.numbersTv.setText(orderBeanList.get(position).getNumber() + "");
        viewHolder.totalTv.setText(orderBeanList.get(position).getTotal() + "");
        viewHolder.priceTv.setText(orderBeanList.get(position).getTotal() / orderBeanList.get(position).getNumber() + "");
        return convertView;
    }

    private class ViewHolder {
        private TextView bookTv;
        private TextView courseTv;
        private TextView teacherTv;
        private TextView gradeTv;
        private TextView numbersTv;
        private TextView totalTv;
        private TextView priceTv;
    }
}
