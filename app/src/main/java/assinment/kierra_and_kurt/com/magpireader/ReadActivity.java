package assinment.kierra_and_kurt.com.magpireader;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by hkurt on 4/23/2018.
 */



public class ReadActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private int thing = 5; // ignore this

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Log.i("TTS", "Start process");

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

}// end class



