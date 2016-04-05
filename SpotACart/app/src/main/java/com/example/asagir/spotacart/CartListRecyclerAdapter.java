package com.example.asagir.spotacart;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by generalassembly on 3/31/16.
 */
public class CartListRecyclerAdapter extends FirebaseRecyclerAdapter<FoodCart, CartListRecyclerAdapter.ViewHolder> {

    Context mContext;

    public CartListRecyclerAdapter(Query ref) {
        super(FoodCart.class, R.layout.cart_view_holder, ViewHolder.class, ref);
    }

    public CartListRecyclerAdapter(Firebase ref, Context context) {
        super(FoodCart.class, R.layout.cart_view_holder, ViewHolder.class, ref);

        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewName;
        public TextView mTextViewAddress;
        public TextView mTextViewDescription;
        public ImageView mImageCart;

        public ViewHolder(View cartView) {
            super(cartView);

            mTextViewName = (TextView) cartView.findViewById(R.id.cartName);
            mTextViewAddress = (TextView) cartView.findViewById(R.id.cartAddress);
            mTextViewDescription = (TextView) cartView.findViewById(R.id.cartDescription);
            mImageCart = (ImageView) cartView.findViewById(R.id.cartImage);


        }

    }

    @Override
    public CartListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_view_holder, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    protected void populateViewHolder(final ViewHolder viewHolder, final FoodCart foodCart, int i) {

        viewHolder.mTextViewName.setText(foodCart.getName());
        viewHolder.mTextViewAddress.setText(foodCart.getAddress());
        viewHolder.mTextViewDescription.setText(foodCart.getDescription());
        Picasso.with(mContext).load(foodCart.getImageURL()).into(viewHolder.mImageCart);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CartDetails cartDetail = new CartDetails();
                Bundle bundle = new Bundle();
                bundle.putSerializable("FoodCart", foodCart);

                cartDetail.setArguments(bundle);

                ((Activity) mContext).getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                                R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                        .replace(R.id.fragmentHolder, cartDetail)
                        .addToBackStack(null).commit();
            }
        };

        viewHolder.mTextViewName.setOnClickListener(listener);
        viewHolder.mTextViewAddress.setOnClickListener(listener);
        viewHolder.mTextViewDescription.setOnClickListener(listener);
        viewHolder.itemView.setOnClickListener(listener);
        viewHolder.mImageCart.setOnClickListener(listener);

    }

}


