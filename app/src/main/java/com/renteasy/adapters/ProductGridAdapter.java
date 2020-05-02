package com.renteasy.adapters;

import android.app.Activity;
import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.renteasy.R;
import com.renteasy.entity.ProductDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by RUPESH on 7/31/2016.
 */
public class ProductGridAdapter extends ArrayAdapter<ProductDetail> {

    private Context mContext;
    private int layoutResourceId;
    private List<ProductDetail> mGridData;

    public ProductGridAdapter(Context mContext, int layoutResourceId, List<ProductDetail> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;
    }

    public List<ProductDetail> getGridData() {
        return mGridData;
    }

    public void setGridData(List<ProductDetail> mGridData) {
        this.mGridData.clear();
        this.mGridData.addAll(mGridData);
        this.notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.productImage = (ImageView) row.findViewById(R.id.product_img);
            holder.productName=(TextView)row.findViewById(R.id.product_name);
            holder.startingPrice=(TextView)row.findViewById(R.id.product_price);
            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        ProductDetail item = mGridData.get(position);

        if (item.getImage() != null&&!item.getImage().isEmpty()) {
            Picasso.with(mContext).load(item.getImage()).placeholder(R.mipmap.ic_launcher).into(holder.productImage);
        } else {
            holder.productImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.ic_launcher));
        }

        holder.productName.setText(item.getName());
        //holder.startingPrice.setText(item.getActualPrice().toString());

        return row;
    }

    static class ViewHolder {
        ImageView productImage;
        TextView  productName;
        TextView  startingPrice;
    }
}
