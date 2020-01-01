package com.example.project1.MLthings;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.project1.R;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MLAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<ML_Image_Object> img;
    LayoutInflater inf;
    File imgFile;

    public MLAdapter(Context context, int layout, List<ML_Image_Object> img) {
        this.context = context;
        this.layout = layout;
        this.img = img;
        this.inf = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateImg(List<ML_Image_Object> nimg) {
        this.img = nimg;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return img.size();
    }

    @Override
    public Object getItem(int position) {
        return img.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) convertView = inf.inflate(layout, parent, false);
        ImageView iv = (ImageView) convertView.findViewById(R.id.ML_ImageView);
        Bitmap myBitmap;
        //Transferring path -> Bitmap -> ImageView

        imgFile = new File(img.get(position).getPath());

        if (imgFile.exists()) {
            Glide
                .with(context)
                .load(Uri.fromFile(imgFile))
                .thumbnail(0.01f)
                .into(iv);

        } else {
            iv.setImageResource(img.get(position).getImResource());
        }
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        convertView.getLayoutParams().height = convertView.getLayoutParams().width;

        TextView label = (TextView) convertView.findViewById(R.id.ML_Label_ID);
        label.setText(img.get(position).getLabel());
        //여따 labelling 결과값 출력!!!!
        return convertView;
    }


    // Glide 가 너무 잘 되어서, 이게 필요할 지 미지수. Glide 에서 아마 Async 로 돌리는 것이 아닐까..
    private class LoadImageTask extends AsyncTask<File,Integer,Void>{
        @Override
        protected Void doInBackground(File ... imgFile){
            //Bitmap myBitmap = BitmapFactory.decodeFile(imgFile[0].getAbsolutePath());
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer ... progress){
            //return progress;

        }
        @Override
        protected void onPostExecute(Void v){
            //모든 것이 끝나면, update 명령


        }
    }
}
