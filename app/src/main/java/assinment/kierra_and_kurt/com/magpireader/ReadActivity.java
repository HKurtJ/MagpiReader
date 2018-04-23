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



        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i == TextToSpeech.SUCCESS){
                    int result = tts.setLanguage(Locale.US);

                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.i("TTS", "Bad Data");
                    }else{
                        read_button.setEnabled(true);
                    }// end if
                }else{
                    Log.i("TTS","Fail");
                }// end if

            }// end onInit
        });// end Listener

        findViewById(R.id.read_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String to_read = editText.getText().toString().trim();

                tts.speak(to_read, TextToSpeech.QUEUE_FLUSH, null);
            }// end onClick
        });// end Listener


    }// end onCreate

    @Override
    protected void onDestroy() {
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }// end if

        super.onDestroy();
    }// end Destroy


}// end class



