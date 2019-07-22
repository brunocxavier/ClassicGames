package xavier.bruno.classicgames.campominado;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.webkit.WebView;

import xavier.bruno.classicgames.R;

public class CampoMinadoAjudaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campo_minado_ajuda);

        String html = "<html>"
                + "<body>"

                + "<h2>Dica</h2>"

                + "<ul>"

                + "<li>Para marcar ou desmarcar uma bomba, aperte e segure até ouvir um som."

                + "</ul>"

                + "</body>"
                + "</html>";

        WebView wvRegras = (WebView) findViewById(R.id.wvRegras);
        wvRegras.loadData(html, "text/html", "UTF-8");




    }

}
