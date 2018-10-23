package com.weicong.textbookmanage.other;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.weicong.textbookmanage.activity.R;
import com.weicong.textbookmanage.model.BookBean;
import com.weicong.textbookmanage.model.CourseBean;

import java.util.List;

/**
 * @author: Frank
 * @time: 2018/4/21 19:16
 * @e-mail: 912220261@qq.com
 * Function:
 */
public class BookAdapter extends BaseAdapter {
    private List<BookBean> bookBeanList;
    private LayoutInflater layoutInflater;

    public BookAdapter(Context context, List<BookBean> bookBeanList) {
        this.bookBeanList = bookBeanList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bookBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookBeanList.get(position);
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
            convertView = layoutInflater.inflate(R.layout.book_item, parent, false);
            viewHolder.nameTv = convertView.findViewById(R.id.book_name_tv);
            viewHolder.authorTv = convertView.findViewById(R.id.book_author_tv);
            viewHolder.priceTv = convertView.findViewById(R.id.book_price_tv);
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.nameTv.setText("《" + bookBeanList.get(position).getBookName() + "》");
        viewHolder.authorTv.setText(bookBeanList.get(position).getBookAuthor());
        viewHolder.priceTv.setText(bookBeanList.get(position).getBookPrice() + "¥");
        return convertView;
    }

    private class ViewHolder {
        private TextView nameTv;
        private TextView authorTv;
        private TextView priceTv;
    }
}
