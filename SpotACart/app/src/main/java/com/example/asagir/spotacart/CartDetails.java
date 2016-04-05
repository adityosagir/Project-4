package com.example.asagir.spotacart;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CartDetails extends Fragment {

    ImageView mImageView;
    TextView mCartName;
    TextView mCartAddress;
    TextView mCartDescription;
    FoodCart mfoodCart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.content_cart_details, container, false);

        mfoodCart = (FoodCart) getArguments().getSerializable("FoodCart");

        mImageView = (ImageView) v.findViewById(R.id.imageViewDetails);
        mCartName = (TextView) v.findViewById(R.id.cartNameDetails);
        mCartAddress = (TextView) v.findViewById(R.id.cartAddressDetails);
        mCartDescription = (TextView) v.findViewById(R.id.cartDescriptionDetails);

        mfoodCart.getName();
        mfoodCart.getAddress();
        mfoodCart.getDescription();
        mfoodCart.getImageURL();

        mCartName.setText(mfoodCart.getName());
        mCartAddress.setText(mfoodCart.getAddress());
        mCartDescription.setText(mfoodCart.getDescription());
        Picasso.with(getActivity()).load(mfoodCart.getImageURL()).into(mImageView);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabFav);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Added to Favorites", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return v;
    }

}
