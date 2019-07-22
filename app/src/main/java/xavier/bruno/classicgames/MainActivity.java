package xavier.bruno.classicgames;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import xavier.bruno.classicgames.campominado.CampoMinadoActivity;

//<div>Icons made by <a href="https://www.flaticon.com/authors/tutsplus" title="TutsPlus">TutsPlus</a> from <a href="https://www.flaticon.com/"                 title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/"                 title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
//Icon made by [author link] from www.flaticon.com
//TutsPlus

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-0381609228541841~8494761522");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    public void campoMinado(View v) {
        Intent intent = new Intent(this, CampoMinadoActivity.class);
        startActivity(intent);
    }
}
