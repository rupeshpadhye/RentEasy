package com.renteasy.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.renteasy.R;
import com.renteasy.entity.Address;
import com.renteasy.views.fragments.AddressFragment;

/**
 * Created by RUPESH on 8/14/2016.
 */
public class AddressCursorAdapter extends CursorAdapter {

    private final AddressFragment.AddressItemClickListener mItemListener;
    private boolean isDelete;

    public AddressCursorAdapter(Context context, AddressFragment.AddressItemClickListener clickListener, boolean isDelete) {
        super(context, null, 0);
        this.mItemListener = clickListener;
        this.isDelete = isDelete;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.address_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        final Address address = Address.from(cursor);

        if("Y".equals(address.getPrimaryAddress())) {
            viewHolder.primary_add_txt.setVisibility(View.VISIBLE);
            viewHolder.setPrimaryAddrBtn.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.primary_add_txt.setVisibility(View.INVISIBLE);
            viewHolder.setPrimaryAddrBtn.setVisibility(View.VISIBLE);
        }

        viewHolder.line1.setText(address.getLine1());
        if (address.getLine2() != null) {
            viewHolder.line2.setText(address.getLine2());
        }

        if (address.getLine3() != null) {
            viewHolder.line3.setText(address.getLine3());
        }

        viewHolder.line4.setText(address.getLine4());

        if (isDelete) {
            viewHolder.deleteAddressBtn.setVisibility(View.VISIBLE);
            viewHolder.deleteAddressBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemListener.deleteAddress(address);
                }
            });
        } else {
            viewHolder.deleteAddressBtn.setVisibility(View.GONE);
        }

        viewHolder.setPrimaryAddrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               viewHolder.primary_add_txt.setVisibility(View.VISIBLE);
               viewHolder.setPrimaryAddrBtn.setVisibility(View.GONE);
               mItemListener.selectAddress(address);

            }
        });
    }

    public static class ViewHolder {

        public final View rowView;
        public final ImageButton deleteAddressBtn;
        public final TextView line1;
        public final TextView line2;
        public final TextView line3;
        public final TextView line4;
        public final TextView primary_add_txt;
        public final Button setPrimaryAddrBtn;

        public ViewHolder(View view) {
            rowView = view;
            deleteAddressBtn = (ImageButton) view.findViewById(R.id.deleteAddressBtn);
            line1 = (TextView) view.findViewById(R.id.line1);
            line2 = (TextView) view.findViewById(R.id.line2);
            line3 = (TextView) view.findViewById(R.id.line3);
            line4 = (TextView) view.findViewById(R.id.pincode);
            primary_add_txt=(TextView)view.findViewById(R.id.primary_add_txt);
            setPrimaryAddrBtn=(Button)view.findViewById(R.id.primary_add_btn);
        }
    }
}
