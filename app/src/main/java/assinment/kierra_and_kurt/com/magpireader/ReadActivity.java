package assinment.kierra_and_kurt.com.magpireader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by hkurt on 4/23/2018.
 */



public class ReadActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private int thing = 5; // ignore this
    int imageRequest = 1;
    int RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Log.i("TTS", "Start process");

        /*PERMISSION TO TAKE IMAGE --->>>> ignore for now*/
       /* if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }*/

        // initialize TTS object
        tts = new TextToSpeech(ReadActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                Log.i("TTS", "Start initialization i = " + i);
                if(i == TextToSpeech.SUCCESS){
                    Log.i("TTS", "Success");
                    int result = tts.setLanguage(Locale.US);

                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.i("TTS", "Language not supported");

                        Toast.makeText(ReadActivity.this,
                                "Language not supported",
                                Toast.LENGTH_LONG).show();
                    }else{
                        ToSpeech();
                    }// end if
                }else{
                    Log.i("TTS","Fail");

                    Toast.makeText(ReadActivity.this,
                            "Failed to Load Data",
                            Toast.LENGTH_LONG).show();
                }// end if

            }// end onInit
        });// end Listener

        // click the Read Button
        findViewById(R.id.read_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ToSpeech();
            }// end onClick
        });// end Listener

    }// end onCreate

    private void ToSpeech(){ // talks here
        Log.i("TTS", "Talking");
        EditText editText = findViewById(R.id.read_this_text);
        String to_read = editText.getText().toString().trim();

        if(to_read.equals("")){
            Log.i("TTS", "No Text");

            Toast.makeText(ReadActivity.this,
                    "No Text Available",
                    Toast.LENGTH_LONG).show();
        }else{
            Log.i("TTS", "Has Text");
            tts.speak(to_read, TextToSpeech.QUEUE_FLUSH, null);
        }// end if

    }// end ToSpeech


    @Override
    protected void onPause() { // stops talking once paused
        if(tts != null){
            Log.i("TTS", "Pausing");
            tts.stop();
            tts.shutdown();
        }// end if

        super.onPause();
    }// end onPause

    /*====MENU=====================================*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_camera) {
            return true;

        }else if (id == R.id.action_gallery) {
            getImage();

        }else if (id == R.id.action_about) {
            Log.i("MAIN", "Go to About");
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    /*====END OF MENU======================================================================*/

    /*==GET IMAGE FROM GALLERY====================================================================
    * SPURCES: http://programmerguru.com/android-tutorial/how-to-pick-image-from-gallery/*/
    private void getImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, imageRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            if (requestCode == imageRequest && resultCode == RESULT_CODE && null != data) {

                Uri select = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};

                //cursor
                Cursor cursor = getContentResolver().query(select, filePath, null, null, null);
                cursor.moveToFirst();

                int colIndex = cursor.getColumnIndex(filePath[0]);
                String imgString = cursor.getString(colIndex);
                cursor.close();
                ImageView imageView = findViewById(R.id.picture_imageView);

                imageView.setImageBitmap(BitmapFactory.decodeFile(imgString));

            } else {
                Toast.makeText(getApplicationContext(), "An image was not picked",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }


}// end class



