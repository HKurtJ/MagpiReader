package assinment.kierra_and_kurt.com.magpireader;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

/**
 * Created by hkurt on 4/23/2018.
 */

public class ReadActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private EditText editText;
    private Button read_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Log.i("TTS", "Start process");

        tts = new TextToSpeech(ReadActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                Log.i("TTS", "Start initalization i = " + i);
                if(i == TextToSpeech.SUCCESS){
                    Log.i("TTS", "Success, i = " + i);
                    int result = tts.setLanguage(Locale.US);

                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.i("TTS", "Language not supported");
                    }else{
                        ToSpeech();
                    }// end if
                }else{
                    Log.i("TTS","Fail");

                }// end if

            }// end onInit
        });// end Listener

        findViewById(R.id.read_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ToSpeech();
            }// end onClick
        });// end Listener


    }// end onCreate

    private void ToSpeech(){
        Log.i("TTS", "Talking");
        String to_read = editText.getText().toString().trim();
        if(to_read.equals(null) || to_read.equals("")){
            Log.i("TTS", "No Text");
            //to_read = "No Text Given";
            //tts.speak(to_read, TextToSpeech.QUEUE_FLUSH, null);
        }else{
            Log.i("TTS", "Has Text");
            tts.speak(to_read, TextToSpeech.QUEUE_FLUSH, null);
        }// end if
    }// end ToSpeech

    @Override
    protected void onDestroy() {
        if(tts != null){
            Log.i("TTS", "Destroing");
            tts.stop();
            tts.shutdown();
        }// end if

        super.onDestroy();
    }// end Destroy


}// end class



