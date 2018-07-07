package com.renteasy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renteasy.R;
import com.renteasy.entity.ProductDetail;
import com.renteasy.views.fragments.CartFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RUPESH on 8/7/2016.
 */
public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartViewHolder> {

    private final CartFragment.CartItemClickListener cartItemClickListener  ;
    private final List<ProductDetail> mCartDetailList;
    private Context mContext;

    public CartListAdapter(List<ProductDetail> productDetailList, Context context,
                           CartFragment.CartItemClickListener recyclerClickListener) {

        mCartDetailList = productDetailList;
        mContext = context;
        cartItemClickListener = recyclerClickListener;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(rootView, cartItemClickListener);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        holder.bindCategory(mCartDetailList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCartDetailList.size();
    }

   /* public void setCart(List<ProductDetail> cart) {
        mCartDetailList.clear();
        mCartDetailList.addAll(cart);
        notifyDataSetChanged();
    }
*/
    public class CartViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_name) TextView productName;
        @BindView(R.id.product_price) TextView productPrice;
        @BindView(R.id.product_tenure) TextView productTenure;
        @BindView(R.id.removeFromCart) ImageButton removeFromCartBtn;
        @BindView(R.id.product_row) LinearLayout productDetailLayout;
       @BindView(R.id.price_row) LinearLayout priceDetailLayout;
       @BindView(R.id.tenure_row) LinearLayout tenureDetailLayout;
       @BindView(R.id.product_desc) TextView productDesc;
       @BindView(R.id.cart_product_img) ImageView cartProductImg;

        public CartViewHolder(View rootView, CartFragment.CartItemClickListener cartItemClickListener) {
            super(rootView);
            ButterKnife.bind(this, itemView);
            bindListener(itemView, cartItemClickListener);
        }

        private void bindListener(View itemView, final CartFragment.CartItemClickListener cartItemClickListener) {

            removeFromCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartItemClickListener.removeFromCart(getAdapterPosition());
                }
            });

            productDetailLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartItemClickListener.showProductDetails(getAdapterPosition());

                }
            });

        }

        public void bindCategory(ProductDetail productDetail) {
            productName.setText(productDetail.getName());
            if(productDetail.getPricing() !=null){
                productPrice.setText(String.valueOf(productDetail.getPricing().getPrice()));
                productTenure.setText(productDetail.getPricing().getDuration()+""+productDetail.getPricing().getPeriod());
            }
            else{
                priceDetailLayout.setVisibility(View.GONE);
                tenureDetailLayout.setVisibility(View.GONE);
            }
            productDesc.setText(productDetail.getDescription());
            Picasso.with(mContext).load(productDetail.getImage()).placeholder(R.mipmap.ic_launcher).into(cartProductImg);
        }
    }
}
