package net.hitechgroupindia.saveimageandretrive;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static android.content.ClipData.newIntent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Bitmap btmap;
    private static int RESULT_LOAD_IMAGE = 1;
    public static final String MYPROFILE = "profile";//file name
    SharedPreferences sharedpreferences;

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String picturePath = sharedpreferences.getString(MYPROFILE, "");
        if(!picturePath.equals("")){
            ImageView imageView = (ImageView) findViewById(R.id.image_button);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));



        }

      /*  ImageButton imgbtn=(ImageButton) findViewById(R.id.imageButton1);
        imgbtn.setOnClickListener(this);*/
        iv = (ImageView) findViewById(R.id.image_button);
        iv.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.image_button:
                try
                { //go to image library and pick the image
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, RESULT_LOAD_IMAGE);
                }


                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            iv.setImageBitmap(BitmapFactory.decodeFile(picturePath));



          //  SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor edit= sharedpreferences.edit();

            edit.putString(MYPROFILE, picturePath);
            edit.commit();

        }
    }

}
