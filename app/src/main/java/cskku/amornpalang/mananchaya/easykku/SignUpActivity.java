package cskku.amornpalang.mananchaya.easykku;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;
import java.net.URL;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, phoneEditText,
            userEditText, passwordEditText;
    private ImageView imageView;
    private Button button;

    private String nameString, phoneString, userString, passwordString,
                    imagePathString, imageNameString;
    private Uri uri;
    private boolean aBoolean = true;
    private String urlAddUser = "http://swiftcodingthai.com/kku/add_user_manan.php";
    private String urlImage = "http://swiftcodingthai.com/kku/Image";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind widget
        nameEditText = (EditText) findViewById(R.id.editText);
        phoneEditText = (EditText) findViewById(R.id.editText2);
        userEditText = (EditText) findViewById(R.id.editText3);
        passwordEditText = (EditText) findViewById(R.id.editText4);
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button3);


        //SignUP Controller
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get Value From Edit Text
                nameString = nameEditText.getText().toString().trim();
                phoneString = phoneEditText.getText().toString().trim();
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                //Check Space
                if (nameString.equals("") || phoneString.equals("") ||
                        userString.equals("") || passwordString.equals("")) {
                    //Have space
                    Log.d("12novV1", "Have Space");
                    MyAlert myAlert = new MyAlert(SignUpActivity.this, R.drawable.kon48,
                            "มีช่องว่าง", "กรุณากรอกให้ครบทุกช่อง");
                    myAlert.myDialog();
                } else if (aBoolean) {
                    //Non Choose Image
                    MyAlert myAlert = new MyAlert(SignUpActivity.this, R.drawable.nobita48,
                            "ยังไม่เลือกรูป", "กรุณาเลือกรูปด้วยนะคะ");
                    myAlert.myDialog();
                } else {
                    //Choose Image OK
                    uploadImageToServer();
                    uploadStringToServer();
                }
            } //onClick
        });

        //Image Controller
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent ย้ายการทำงานจากอีกหน้าไป อีกหน้าหนึ่ง
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*"); //เปิดโปรแกรมทุกโปรแกรมที่สามารถเปิดดูรูปภาพได้
                startActivityForResult(Intent.createChooser(intent, "โปรเลือกแอปดูภาพ"), 0);
                // 0 ค่าของตัว request รูปภาพ
            } // onClick
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }// Main Method

    private void uploadStringToServer() {
        AddNewUser addNewUser = new AddNewUser(SignUpActivity.this);
        addNewUser.execute(urlAddUser);

    } // upload String

    // Create Inner Class
    private class AddNewUser extends AsyncTask<String, Void, String> {
        //Explicit
        private Context context;

        public AddNewUser(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                // RequestBody การมัดสตริงเป็นก้อนก่อนส่ง
                RequestBody requestBody = new FormEncodingBuilder()
                        .add("isAdd", "true")
                        .add("Name", nameString)
                        .add("Phone", phoneString)
                        .add("User", userString)
                        .add("Password", passwordString)
                        .add("Image", urlImage + imageNameString)
                        .build();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(params[0]).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();

                return response.body().string();

            } catch (Exception e) {
                Log.d("13novV1", "e doIn===>" + e.toString());
                return null;
            }


        } //doInBack ทำงงานอยู้เบื้องหลังพยายามต่อเน็ต

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("13novV1", "Result ==>" + s);


        } //onPost

    } // AddNewUser Class


    private void uploadImageToServer() {

        // Change Policy
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        try {

            SimpleFTP simpleFTP = new SimpleFTP();
            simpleFTP.connect("ftp.swiftcodingthai.com",21,"kku@swiftcodingthai.com",
                    "Abc12345");
            simpleFTP.bin();
            simpleFTP.cwd("Image");
            simpleFTP.stor(new File(imagePathString));
            simpleFTP.disconnect();


        } catch (Exception e) {
            Log.d("12novV1", "e simpleFTP ==>" + e.toString());

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 0) && (resultCode == RESULT_OK)) {
            Log.d("12novV1", "Result OK");
            aBoolean = false;

            //Show image
            uri = data.getData();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                        .openInputStream(uri));
                imageView.setImageBitmap(bitmap);

            }catch (Exception e){
                e.printStackTrace();
            }

            //Find Paht of Image
            imagePathString = myFindPath(uri);
            Log.d("12novV1", "imagePath ==>" + imagePathString);

            //Find Mane of Image
            imageNameString = imagePathString.substring(imagePathString.lastIndexOf("/"));
            Log.d("12novV1", "imageName ==>" + imageNameString);

        } // if


    }// onActivity

    private String myFindPath(Uri uri) {

        String result = null;
        String[] strings = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri,strings,null,null,null);

        if (cursor != null) {
            cursor.moveToFirst();
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            result=cursor.getString(index);
        } else {
            result = uri.getPath();
        }


        return result;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SignUp Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
} //Main class
