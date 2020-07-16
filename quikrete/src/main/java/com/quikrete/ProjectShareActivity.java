package com.quikrete;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.share.widget.ShareDialog;
import com.quikrete.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ProjectShareActivity extends Activity {

    int TAKE_PHOTO_CODE = 0;
    public static int count = 0;
    Uri filePath;
    Uri outputFileUri;
    ImageView img_captured;
    String dir = "";
    Context context;
    Button bt_cancel, bt_share;




    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_share);
        context=this;


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());



        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        bt_share = (Button) findViewById(R.id.bt_share);

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("pavestone", "bt_cancel--setOnClickListener" + "----setOnClickListener");

                finish();

            }
        });
        bt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("pavestone", "bt_share----setOnClickListener" + "----setOnClickListener");
                img_captured.setDrawingCacheEnabled(true);
                img_captured.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

                img_captured.buildDrawingCache(true);
                Bitmap bmap = Bitmap.createBitmap(img_captured.getDrawingCache());
                img_captured.setDrawingCacheEnabled(false); // clear drawing cache
                Log.e("pavestone", bmap + "----bmap");
                if (bmap != null) {

                    ShareDialog shareDialog = Utils.initFacebook(ProjectShareActivity.this, ProjectShareActivity.this);
                    Log.e("pavestone", shareDialog + "----shareDialog");
                    if (shareDialog != null) {
                        Utils.shareImageOnFacebook(bmap, ProjectShareActivity.this, shareDialog);
                        finish();
                    }


                }

            }
        });

        img_captured = (ImageView) findViewById(R.id.img_captured);

       /* findViewById(R.id.bt_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SHARE HERE

            }
        });*/

        dir = Environment.getExternalStorageDirectory() + "/" + "Pavestone" + "/" + "ProjectShare" + "/";
        File newdir = new File(dir);
        newdir.mkdirs();


        // here,counter will be incremented each time,and the picture taken by camera will be stored as 1.jpg,2.jpg and likewise.
        count++;
        filePath = Uri.parse(dir + count + ".jpg");
        File newfile = new File(String.valueOf(filePath));
        try {
            newfile.createNewFile();
        } catch (IOException e) {
        }

        outputFileUri = Uri.fromFile(newfile);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

      // startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);

        startActivityForResult(getPickImageChooserIntent(), TAKE_PHOTO_CODE);

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            Log.e("pavestone", "Pic saved");

          //  rotateImage(filePath);

            if (getPickImageResultUri(data) != null) {
                filePath = getPickImageResultUri(data);

                try {
                    Bitmap scaledBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                    scaledBitmap = rotateImageIfRequired(scaledBitmap, filePath);
                    img_captured.setImageBitmap(scaledBitmap);



                } catch (IOException e) {
                    e.printStackTrace();
                }

            }




        } else {
            Log.e("pavestone", "Pic NOT saved");

            finish();
        }
    }


    private Bitmap decodeFileFromPath(String path) {
        Uri uri = outputFileUri;
        InputStream in = null;
        try {
            in = getContentResolver().openInputStream(uri);

            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            int inSampleSize = 1024;
            if (o.outHeight > inSampleSize || o.outWidth > inSampleSize) {
                scale = (int) Math.pow(2, (int) Math.round(Math.log(inSampleSize / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            in = getContentResolver().openInputStream(uri);
            Bitmap b = BitmapFactory.decodeStream(in, null, o2);
            in.close();

            return b;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

////    private void rotateImage(final String path) {
////        runOnUiThread(new Runnable() {
////            @Override
////            public void run() {
////                Bitmap b = decodeFileFromPath(path);
////                try {
////                    ExifInterface ei = new ExifInterface(path);
////                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
////                    Matrix matrix = new Matrix();
////                    switch (orientation) {
////                        case ExifInterface.ORIENTATION_ROTATE_90:
////                            matrix.postRotate(90);
////                            b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
////                            break;
////                        case ExifInterface.ORIENTATION_ROTATE_180:
////                            matrix.postRotate(180);
////                            b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
////                            break;
////                        case ExifInterface.ORIENTATION_ROTATE_270:
////                            matrix.postRotate(270);
////                            b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
////                            break;
////                        default:
////                            b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
////                            break;
////                    }
////                } catch (Throwable e) {
////                    e.printStackTrace();
////                }
////
////                FileOutputStream out1 = null;
////                File file;
////                try {
////                    String state = Environment.getExternalStorageState();
////                    if (Environment.MEDIA_MOUNTED.equals(state)) {
////                        file = new File(filePath);
////                    } else {
////                        file = new File(getFilesDir(), "image" + new Date().getTime() + ".jpg");
////                    }
////                    out1 = new FileOutputStream(file);
////                    b.compress(Bitmap.CompressFormat.JPEG, 90, out1);
////                    img_captured.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
////                } catch (Exception e) {
////                    e.printStackTrace();
////                } finally {
////                    try {
////                        out1.close();
////                    } catch (Throwable ignore) {
////
////                    }
////                }
////
////            }
////        });
//
//    }

    public void onClickFinish(View view) {
        finish();
    }



    //Camera--


    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }


    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }


        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }


    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    public Intent getPickImageChooserIntent() {


        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }
        // the main intent is the last in the list so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);
        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }




}
