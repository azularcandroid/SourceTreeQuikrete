//package com.quikrete;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
//import android.media.ExifInterface;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.StrictMode;
//import android.provider.MediaStore;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.facebook.Request;
//import com.facebook.Response;
//import com.facebook.Session;
//import com.facebook.SessionState;
//import com.facebook.UiLifecycleHelper;
//import com.facebook.model.GraphUser;
//import com.facebook.widget.LoginButton;
//import com.facebook.widget.LoginButton.UserInfoChangedCallback;
//import com.quikrete.utils.ScreenUtils;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//import static android.Manifest.permission.CAMERA;
//
//public class FBActivity extends FragmentActivity {
//
//	private LoginButton loginBtn;
//	private Button postImageBtn;
//	private Button updateStatusBtn;
//
//	private TextView userName;
//
//	private UiLifecycleHelper uiHelper;
//
//	private static final List<String> PERMISSIONS = Arrays
//			.asList("publish_actions");
//
//	private static String message = "Sample status posted from android app";
//
//	private static final int CAMERA_REQUEST = 0;
//	private static final int RESULT_LOAD_IMAGE = 1;
//	final private int CAPTURE_IMAGE = 2;
//	final private int PICK_IMAGE = 1;
//
//	String photoname = "";
//
//	ImageView ivSite;
//
//	Context context;
//
//	static Bitmap bitmap;
//
//	private String imgPath;
//
//	private static final int PERMISSION_REQUEST_CAMERA = 1;
//	boolean cameraAccepted = false;
//
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		ScreenUtils.fullScreen(this);
//		context = this;
//
//		uiHelper = new UiLifecycleHelper(this, statusCallback);
//		uiHelper.onCreate(savedInstanceState);
//
//		setContentView(R.layout.activity_facebook);
//
//		StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//		StrictMode.setVmPolicy(builder.build());
//
//
//		ivSite = (ImageView) findViewById(R.id.iv);
//
//		userName = (TextView) findViewById(R.id.user_name);
//		loginBtn = (LoginButton) findViewById(R.id.fb_login_button);
//		loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
//			@Override
//			public void onUserInfoFetched(GraphUser user) {
//				if (user != null) {
//					userName.setText("Hello, " + user.getName());
//				} else {
//					userName.setText("You are not logged");
//				}
//			}
//		});
//
//		postImageBtn = (Button) findViewById(R.id.post_image);
//		postImageBtn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View view) {
//				postImage();
//			}
//		});
//
//		updateStatusBtn = (Button) findViewById(R.id.update_status);
//		updateStatusBtn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//			}
//		});
//
//		buttonsEnabled(false);
//
//
//		openCameraDriver();
//	}
//
//	private Session.StatusCallback statusCallback = new Session.StatusCallback() {
//		@Override
//		public void call(Session session, SessionState state,
//				Exception exception) {
//			if (state.isOpened()) {
//				buttonsEnabled(true);
//				Log.d("FacebookSampleActivity", "Facebook session opened");
//			} else if (state.isClosed()) {
//				buttonsEnabled(false);
//				Log.d("FacebookSampleActivity", "Facebook session closed");
//			}
//		}
//	};
//
//	public void buttonsEnabled(boolean isEnabled) {
//		postImageBtn.setEnabled(isEnabled);
//		updateStatusBtn.setEnabled(isEnabled);
//	}
//
//	public void postImage() {
//		if (checkPermissions()) {
//			// Bitmap img = BitmapFactory.decodeResource(getResources(),
//			// R.drawable.ic_launcher);
//			if (bitmap != null) {
//
//				Toast.makeText(FBActivity.this,
//						"Photo will be posted on your TimeLine !",
//						Toast.LENGTH_LONG).show();
//				Request uploadRequest = Request.newUploadPhotoRequest(
//						Session.getActiveSession(), bitmap,
//						new Request.Callback() {
//							@Override
//							public void onCompleted(Response response) {
//								Toast.makeText(FBActivity.this,
//										"Photo uploaded successfully",
//										Toast.LENGTH_LONG).show();
//							}
//
//						});
//				uploadRequest.executeAsync();
//
//				finish();
//			}
//		} else {
//			requestPermissions();
//		}
//	}
//
//	public void postStatusMessage() {
//		if (checkPermissions()) {
//			Request request = Request.newStatusUpdateRequest(
//					Session.getActiveSession(), message,
//					new Request.Callback() {
//						@Override
//						public void onCompleted(Response response) {
//							if (response.getError() == null)
//								Toast.makeText(FBActivity.this,
//										"Status updated successfully",
//										Toast.LENGTH_LONG).show();
//						}
//					});
//			request.executeAsync();
//		} else {
//			requestPermissions();
//		}
//	}
//
//	public boolean checkPermissions() {
//		Session s = Session.getActiveSession();
//		if (s != null) {
//			return s.getPermissions().contains("publish_actions");
//		} else
//			return false;
//	}
//
//	public void requestPermissions() {
//		Session s = Session.getActiveSession();
//		if (s != null)
//			s.requestNewPublishPermissions(new Session.NewPermissionsRequest(
//					this, PERMISSIONS));
//	}
//
//	@Override
//	public void onResume() {
//		super.onResume();
//		uiHelper.onResume();
//		buttonsEnabled(Session.getActiveSession().isOpened());
//	}
//
//	@Override
//	public void onPause() {
//		super.onPause();
//		uiHelper.onPause();
//	}
//
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//		uiHelper.onDestroy();
//	}
//
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		uiHelper.onActivityResult(requestCode, resultCode, data);
//
////		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
////
////			photoname = SharedPreferenceHelper.getPreference(this,
////					SharedPreferenceHelper.TEMP_DUP_FILE_NAME);
////
////			try {
////				File sdcardFile = Environment.getExternalStorageDirectory();
////				File imgFile = new File(sdcardFile.getAbsolutePath(), photoname);
////				if (imgFile.exists()) {
////
////					Bitmap bit = ScreenUtils.shrinkmethod(
////							Environment.getExternalStorageDirectory()
////									+ File.separator + photoname, 150, 150);
////					ivSite.setImageBitmap(bit);
////
////				}
////
////			} catch (Exception e) {
////				// TODO: handle exception
////				e.printStackTrace();
////				Log.e("newlog", e.toString() + "---***");
////			}
////
////			try {
////				BitmapFactory.Options options = new BitmapFactory.Options();
////				options.inSampleSize = 4;
////
////				bitmap = BitmapFactory.decodeFile(
////						Environment.getExternalStorageDirectory()
////								+ File.separator + photoname, options);
////				Log.e("newlog", bitmap + "--bitmap");
////				bitmap = Bitmap.createScaledBitmap(bitmap, 640, 480, false);
////				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
////				bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
////
////				photoname = photoname.replaceAll(".jpg", "_.jpg");
////				Log.e("newlog", photoname + "--photoname");
////				File file = new File(Environment.getExternalStorageDirectory()
////						.getPath() + "/" + photoname);
////				Log.e("newlog", file + "");
////				if (file.exists())
////					file.delete();
////				// file.createNewFile();
////				try {
////					FileOutputStream out = new FileOutputStream(file);
////					out.write(bytes.toByteArray());
////					out.flush();
////					out.close();
////
////				} catch (Exception e) {
////					e.printStackTrace();
////				}
////
////			} catch (Exception e) {
////				// TODO: handle exception
////				e.printStackTrace();
////				Log.e("newlog", e.toString() + "---***!!");
////			}
////
////		}
//
//
//		 if (resultCode == Activity.RESULT_OK) {
//	            if (requestCode == CAPTURE_IMAGE) {
//	                rotateImage(getImagePath());
//	            }
////	            else if (requestCode == PICK_IMAGE) {
////	            	ivSite.setImageBitmap(BitmapFactory.decodeFile(getAbsolutePath(data.getData())));
////	            }
//	        }
//	}
//
//	@Override
//	public void onSaveInstanceState(Bundle savedState) {
//		super.onSaveInstanceState(savedState);
//		uiHelper.onSaveInstanceState(savedState);
//	}
//
//	public void openCameraDriver() {
////		String fileName = returnName();
////		SharedPreferenceHelper.savePreferences(
////				SharedPreferenceHelper.TEMP_DUP_FILE_NAME, fileName, this);
////
////		Intent cameraIntent = new Intent(
////				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
////		cameraIntent.putExtra(
////				android.provider.MediaStore.EXTRA_OUTPUT,
////				Uri.parse("file://" + Environment.getExternalStorageDirectory()
////						+ "/" + fileName));
////		startActivityForResult(cameraIntent, CAMERA_REQUEST);
//		 FBActivity.bitmap=null;
//		 final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//         intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
//         startActivityForResult(intent, CAPTURE_IMAGE);
//	}
//
////	String returnName() {
////
////		Calendar calender = Calendar.getInstance();
////		SimpleDateFormat sdatef = new SimpleDateFormat("dd-MM-yy-HH-mm-ss",
////				Locale.ENGLISH);
////
////		String todaysdate = sdatef.format(calender.getTime()).trim();
////		String photoName = todaysdate + ".jpg";
////		photoname = photoName;
////		return photoName;
////	}
//
//
//	private void rotateImage(final String path) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Bitmap b = decodeFileFromPath(path);
//                try {
//                    ExifInterface ei = new ExifInterface(path);
//                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//                    Matrix matrix = new Matrix();
//                    switch (orientation) {
//                        case ExifInterface.ORIENTATION_ROTATE_90:
//                            matrix.postRotate(90);
//                            b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
//                            break;
//                        case ExifInterface.ORIENTATION_ROTATE_180:
//                            matrix.postRotate(180);
//                            b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
//                            break;
//                        case ExifInterface.ORIENTATION_ROTATE_270:
//                            matrix.postRotate(270);
//                            b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
//                            break;
//                        default:
//                            b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
//                            break;
//                    }
//                } catch (Throwable e) {
//                    e.printStackTrace();
//                }
//
//                FBActivity.bitmap =b;
//
//                FileOutputStream out1 = null;
//                File file;
//                try {
//                    String state = Environment.getExternalStorageState();
//                    if (Environment.MEDIA_MOUNTED.equals(state)) {
//                        file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".jpg");
//                    }
//                    else {
//                        file = new File(getFilesDir() , "image" + new Date().getTime() + ".jpg");
//                    }
//                    out1 = new FileOutputStream(file);
//                    b.compress(Bitmap.CompressFormat.JPEG, 90, out1);
//                    ivSite.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        out1.close();
//                    } catch (Throwable ignore) {
//
//                    }
//                }
//
//            }
//        });
//
//    }
//
//    private Bitmap decodeFileFromPath(String path){
//        Uri uri = getImageUri(path);
//        InputStream in = null;
//        try {
//            in = getContentResolver().openInputStream(uri);
//
//            //Decode image size
//            BitmapFactory.Options o = new BitmapFactory.Options();
//            o.inJustDecodeBounds = true;
//
//            BitmapFactory.decodeStream(in, null, o);
//            in.close();
//
//
//            int scale = 1;
//            int inSampleSize = 1024;
//            if (o.outHeight > inSampleSize || o.outWidth > inSampleSize) {
//                scale = (int) Math.pow(2, (int) Math.round(Math.log(inSampleSize / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
//            }
//
//            BitmapFactory.Options o2 = new BitmapFactory.Options();
//            o2.inSampleSize = scale;
//            in = getContentResolver().openInputStream(uri);
//            Bitmap b = BitmapFactory.decodeStream(in, null, o2);
//            in.close();
//
//            return b;
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
////    public String getAbsolutePath(Uri uri) {
////        if(Build.VERSION.SDK_INT >= 19){
////            String id = uri.getLastPathSegment().split(":")[1];
////            final String[] imageColumns = {MediaStore.Images.Media.DATA };
////            final String imageOrderBy = null;
////            Uri tempUri = getUri();
////            Cursor imageCursor = getContentResolver().query(tempUri, imageColumns,
////                    MediaStore.Images.Media._ID + "="+id, null, imageOrderBy);
////            if (imageCursor.moveToFirst()) {
////                return imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
////            }else{
////                return null;
////            }
////        }else{
////            String[] projection = { MediaStore.MediaColumns.DATA };
////            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
////            if (cursor != null) {
////                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
////                cursor.moveToFirst();
////                return cursor.getString(column_index);
////            } else
////                return null;
////        }
////
////    }
//
////    private Uri getUri() {
////        String state = Environment.getExternalStorageState();
////        if(!state.equalsIgnoreCase(Environment.MEDIA_MOUNTED))
////            return MediaStore.Images.Media.INTERNAL_CONTENT_URI;
////
////        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
////    }
//
//    private Uri getImageUri(String path) {
//        return Uri.fromFile(new File(path));
//    }
//
//    public Uri setImageUri() {
//
//        String state = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED.equals(state)) {
//            File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".jpg");
//            Uri imgUri = Uri.fromFile(file);
//            this.imgPath = file.getAbsolutePath();
//            return imgUri;
//        }
//        else {
//            File file = new File(getFilesDir() , "image" + new Date().getTime() + ".jpg");
//            Uri imgUri = Uri.fromFile(file);
//            this.imgPath = file.getAbsolutePath();
//            return imgUri;
//        }
//    }
//
//    public String getImagePath() {
//        return imgPath;
//    }
//
//
//
//
//}