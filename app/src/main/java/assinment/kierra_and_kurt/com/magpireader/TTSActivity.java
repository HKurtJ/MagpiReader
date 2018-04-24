package assinment.kierra_and_kurt.com.magpireader;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Kierra on 4/23/2018.
 */

public class TTSActivity  extends AppCompatActivity {
    TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texttospeech);

        findViewById(R.id.text_editText);
        findViewById(R.id.speak_button);
        findViewById(R.id.stop_button);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

            }
        });
    }
}
