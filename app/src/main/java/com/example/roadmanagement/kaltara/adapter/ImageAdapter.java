package com.example.roadmanagement.kaltara.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.roadmanagement.kaltara.Interface.SendId;
import com.example.rifqy.kaltara.R;

import java.io.File;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private GradientDrawable mGradientDrawable;
    private ArrayList<File> mArrayList;
    private Context mContex;
    private SendId sendId;


    public ImageAdapter(ArrayList<File> mArrayList, Context mContex, SendId sendId) {
        this.mArrayList = mArrayList;
        this.mContex = mContex;
        this.sendId = sendId;

        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);

        Drawable drawable = null;
        if (drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
        }

    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.detailgambar, parent, false));
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder holder, int position) {
        File current = mArrayList.get(position);
        holder.bindTO(current);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.childgambar);
            itemView.setOnClickListener(this);

        }

        public void bindTO(File current) {


            Glide.with(mContex).load(current).apply(new RequestOptions()).into(imageView);

        }


        @Override
        public void onClick(View view) {
            final File file = mArrayList.get(getAdapterPosition());



            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            
            View mview = LayoutInflater.from(view.getContext()).inflate(R.layout.detailpopup, null);
            final ImageView gambar = mview.findViewById(R.id.popupgambar);
            Button hapus = mview.findViewById(R.id.hapuspopup);
            Glide.with(mContex).load(file).apply(new RequestOptions()).into(gambar);
            builder.setView(mview);
            final AlertDialog dialog = builder.create();
            //dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.show();

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface d) {
                    /*
                    Bitmap icon = BitmapFactory.decodeFile(file.getPath());
                    LinearLayout.LayoutParams layoutParams;


                        float imageWidthInPX = (float) gambar.getWidth();

                       layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                                Math.round(imageWidthInPX * (float) icon.getHeight() / (float) icon.getWidth()));

                    gambar.setLayoutParams(layoutParams);*/
                }
            });

           hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendId.hapusGambar(getAdapterPosition());
                    dialog.dismiss();
                }
            });

        }
    }
}

