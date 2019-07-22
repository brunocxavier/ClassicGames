package xavier.bruno.classicgames.campominado;

import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import xavier.bruno.classicgames.R;

public class CampoMinadoActivity extends AppCompatActivity {

    private static final int LINHAS = 9;
    private static final int COLUNAS = 9;
    private static final int BOMBAS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campo_minado);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);

        Button[] botoes = new Button[LINHAS * COLUNAS];

        for (int b=0; b<(LINHAS * COLUNAS); b++) {
            botoes[b] = new Button(this);
            botoes[b].setLayoutParams(params);
            botoes[b].setId(b);
            botoes[b].setTypeface(null, Typeface.BOLD);
        }

        LinearLayout tela = (LinearLayout) findViewById(R.id.LinearLayout1);
        tela.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));


        CampoAdapter adapter = new CampoAdapter(this, botoes, LINHAS, COLUNAS, BOMBAS);

        GridView campo = (GridView) findViewById(R.id.gvCampo);
        campo.setAdapter(adapter);
    }

}
