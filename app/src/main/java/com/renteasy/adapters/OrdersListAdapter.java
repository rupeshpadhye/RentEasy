package com.renteasy.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.renteasy.R;
import com.renteasy.entity.ProductDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RUPESH on 10/9/2016.
 */
public class OrdersListAdapter  extends RecyclerView.Adapter<OrdersListAdapter.OrdersViewHolder> {

    private List<ProductDetail> mOrderList=new ArrayList<>();
    private Context mContext;



    public OrdersListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public List<ProductDetail> getAllOrders() {
        return mOrderList;
    }

    public void setOrdersList(List<ProductDetail> orders) {
        this.mOrderList.clear();
        this.mOrderList.addAll(orders);
        this.notifyDataSetChanged();
    }


    @Override
    public OrdersListAdapter.OrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.order_item, parent, false);
        return new OrdersViewHolder(rootView);

    }

    @Override
    public void onBindViewHolder(OrdersListAdapter.OrdersViewHolder holder, int position) {
        holder.bindOrder(mOrderList.get(position));
    }

    @Override
    public int getItemCount() {
        return mOrderList ==null ?0 :mOrderList.size();
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_name)
        TextView productName;
        @BindView(R.id.product_price)
        TextView productPrice;
        @BindView(R.id.product_tenure)
        TextView productTenure;
        @BindView(R.id.cart_product_img)
        ImageView cartProductImg;

        public OrdersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindOrder(ProductDetail productDetail) {
            productName.setText(productDetail.getName());
            productPrice.setText(String.valueOf(productDetail.getPricing().getPrice()));
            productTenure.setText(productDetail.getPricing().getDuration()+""+productDetail.getPricing().getPeriod());
            Picasso.with(mContext).load(productDetail.getImage()).placeholder(R.mipmap.ic_launcher).into(cartProductImg);

        }
    }
}


