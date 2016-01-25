package com.ostan.prtest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ostan.prtest.utils.CommonUtils;
import com.ostan.prtest.R;
import com.ostan.prtest.data.DataProccessor;
import com.ostan.prtest.data.ImageDataObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GruppedImagesListAdapter extends BaseAdapter {

    public HashMap<String, List<DataProccessor.Presentable>> mData = new HashMap<>();

    public Context ctx = null;
    LayoutInflater inflater = null;//
    public final static int TYPE_SECTION_HEADER = 0;
    HashMap<String, View> recycledHeaderViews = new HashMap<>();
    HashMap<Integer, View> recycledChildViews = new HashMap<>();


    public GruppedImagesListAdapter(Context context) {
        ctx = context;
        inflater = LayoutInflater.from(ctx);
    }

    public void addNewImage(DataProccessor.Presentable src) {
        for(ImageDataObject.ImageCategory categoryIterator: src.getCategories()) {
            String category = categoryIterator.getName();
            if (mData.containsKey(category)) {
                mData.get(category).add(src);
            } else {
                List<DataProccessor.Presentable> newCategory = new ArrayList<>();
                newCategory.add(src);
                mData.put(category, newCategory);
            }
        }

        this.notifyDataSetChanged();
    }

    public void clearData(){
        mData.clear();
        notifyDataSetChanged();
    }

    public Object getItem(int position) {
        int counter = 0;
        for (String iterator : mData.keySet()) {
            if (counter == position) {
                return iterator;
            }
            counter++;
            for (DataProccessor.Presentable presentableIterator : mData.get(iterator)) {
                if (counter == position) {
                    return presentableIterator;
                }
                counter++;
            }
        }

        // should never happen
        return null;
    }

    public int getCount() {

        int total = 0;
        for (List<DataProccessor.Presentable> iterator : mData.values()) {
            total++;
            total += iterator.size();
        }
        return total;
    }

    @Override
    public int getViewTypeCount() {
        int total = 1 + mData.values().size();
        return total;
    }

    @Override
    public int getItemViewType(int position) {

        if (getItem(position) instanceof String)
            return 1;
        else
            return 2;
    }

    @Override
    public boolean isEnabled(int position) {
        return (getItemViewType(position) != TYPE_SECTION_HEADER);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object item = getItem(position);
        if(item == null) {
            return generateEmptyListView();
        }
        if (item instanceof String) {
            return generateHeaderView((String) item);// HEADER
        } else {
            return generateChildView(position, (DataProccessor.Presentable)item, convertView);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        ImageView imageView = null;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.img_presenter);
        }
    }

    public void bindItemData(ViewHolder holder, DataProccessor.Presentable presentable) {
        holder.imageView.setAdjustViewBounds(true);
        int screenWidth = CommonUtils.getScreenSize(ctx)[0];
        int desiredWidth = (int)(screenWidth * 0.95f);
        holder.imageView.getLayoutParams().width = desiredWidth;
        holder.imageView.setImageDrawable(presentable.getImageDrawable());
        holder.imageView.requestLayout();
    }

    private View generateChildView(int position, DataProccessor.Presentable src, View convertView) {
        if(!recycledChildViews.containsKey(position)) {
            convertView = inflater.inflate(R.layout.item_presentation_holder, null);
            recycledChildViews.put(position, convertView);
        } else {
            convertView = recycledChildViews.get(position);
        }

        ViewHolder holder = new ViewHolder(convertView);
        bindItemData(holder, src);
        return convertView;
    }


    private View generateHeaderView(String categoryName) {
        View view = null;
        if (!recycledHeaderViews.containsKey(categoryName)) {
            view = inflater.inflate(R.layout.category_layout, null);
            TextView tv = (TextView) view.findViewById(R.id.et_category_name);
            tv.setText(categoryName);
            recycledHeaderViews.put(categoryName, view);
        } else {
            view = recycledHeaderViews.get(categoryName);
        }

        return view;
    }
    private View generateEmptyListView(){
        return inflater.inflate(R.layout.empty_list_item, null);
    }

}
