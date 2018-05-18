package com.example.lucca.doeamor_apaetorres.adapters;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class ImageAsynkTask extends AsyncTask<String,Void,Bitmap> {

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        //ImageView imageView = (ImageView) view.findViewById(R.id.post_image);
        //imageView.setImageBitmap(bitmap);
        this.cancel(true);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        return null;
    }
}
