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
import com.example.rifqy.kaltara.R;
import com.example.roadmanagement.kaltara.GetSemuaImage.HapusImage;
import com.example.roadmanagement.kaltara.helper.Session;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {
    private GradientDrawable mGradientDrawable;
    private ArrayList<File> mArrayList;
    private Context mContex;
    private HapusImage hapusImage;
    private Integer segment;
    List<String> iconimage;
    List<String> lokasiimage;
    Session session;


    public HorizontalAdapter(ArrayList<File> mArrayList, String iconku, String lokasiku, Context mContex, Integer segment, HapusImage hapusImage) {
        this.mArrayList = mArrayList;
        this.mContex = mContex;
        this.hapusImage = hapusImage;
        this.segment = segment;


        iconimage = getList(iconku);
        lokasiimage = getList(lokasiku);

        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);

        session = new Session(mContex);

        Drawable drawable = null;
        if (drawable != null) {
            mGradientDrawable.setSize(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
        }

    }

    @Override
    public HorizontalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HorizontalAdapter.ViewHolder(LayoutInflater.from(mContex).inflate
                (R.layout.detailgambar, parent, false));
    }

    @Override
    public void onBindViewHolder(HorizontalAdapter.ViewHolder holder, int position) {
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
            final Button hapus = mview.findViewById(R.id.hapuspopup);
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

                    session.saveSPInt(Session.SP_NOSEGMENT, segment);

                    hapusImage.hapusImage(getHapusImage(getAdapterPosition()),getStringDelete("icon", getAdapterPosition()),
                            getStringDelete("lokasi", getAdapterPosition()), segment);
                    dialog.cancel();

                }
            });

        }


    }

    public  String  getHapusImage(int position){

        String fullgambar = null;


        if(mArrayList.size()!=0) {

            File file = mArrayList.get(position);
            file.delete();

            mArrayList.remove(position);


        }

        if(mArrayList.size()!=0) {

            for (int i = 0; i < mArrayList.size(); i++) {
                if (fullgambar==null) {
                    fullgambar = mArrayList.get(i).getAbsolutePath();
                } else {
                    fullgambar = fullgambar + "," + mArrayList.get(i).getAbsolutePath();
                }
            }
        }else{
            fullgambar = null;
        }


        return fullgambar;

    }


    public  String  getStringDelete(String tipe, int position){

        String fullicon = null;

        List<String> useList = new ArrayList<>();

        if(tipe.equals("icon")){
            useList =iconimage;
        }else{
            useList=lokasiimage;
        }


        if(useList.size()!=0) {

            if(tipe.equals("icon")){
                File file = new File(useList.get(position));
                file.delete();
            }

            useList.remove(position);
        }

        if(useList.size()!=0) {

            for (int i = 0; i < useList.size(); i++) {
                if (fullicon==null) {
                    fullicon = useList.get(i);
                } else {
                    fullicon = fullicon + "," + useList;
                }
            }
        }else{
            fullicon = null;
        }


        return fullicon;

    }


    private List<String> getList(String data){
        List<String> list = new ArrayList<>();

        if(data!=null) {
            String[] listku = data.split(",");
            for (int i = 0; i < listku.length; i++) {

                list.add(listku[i]);
            }
        }

        return list;
    }



}


