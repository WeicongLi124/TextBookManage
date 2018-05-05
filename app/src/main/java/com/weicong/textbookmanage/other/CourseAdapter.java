package com.weicong.textbookmanage.other;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.weicong.textbookmanage.activity.R;
import com.weicong.textbookmanage.model.CourseBean;

import java.util.List;

/**
 * @author: Frank
 * @time: 2018/4/21 19:16
 * @e-mail: 912220261@qq.com
 * Function:
 */
public class CourseAdapter extends BaseAdapter {
    private List<CourseBean> courseBeanList;
    private LayoutInflater layoutInflater;
    public CourseAdapter(Context context,List<CourseBean> courseBeanList) {
        this.courseBeanList = courseBeanList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return courseBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.course_item,parent,false);
            viewHolder.nameTv = convertView.findViewById(R.id.course_name_tv);
            viewHolder.teacherTv = convertView.findViewById(R.id.course_teacher_tv);
            viewHolder.typeTv = convertView.findViewById(R.id.course_type_tv);
            viewHolder.creditTv = convertView.findViewById(R.id.course_credit_tv);
            viewHolder.gradeTv = convertView.findViewById(R.id.course_grade_tv);
            convertView.setTag(viewHolder);
        }else viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.creditTv.setText(courseBeanList.get(position).getCredit()+"");
        viewHolder.nameTv.setText(courseBeanList.get(position).getCourseName());
        viewHolder.teacherTv.setText(courseBeanList.get(position).getTeacherName());
        viewHolder.teacherTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        viewHolder.typeTv.setText(courseBeanList.get(position).getType());
        if (courseBeanList.get(position).getType().equals("必修")){
            viewHolder.typeTv.setTextColor(Color.parseColor("#ff0000"));
        }else viewHolder.typeTv.setTextColor(Color.parseColor("#1692f1"));
        viewHolder.gradeTv.setText(courseBeanList.get(position).getGrade());
        return convertView;
    }

    private class ViewHolder{
        private TextView nameTv;
        private TextView typeTv;
        private TextView teacherTv;
        private TextView creditTv;
        private TextView gradeTv;
    }
}
