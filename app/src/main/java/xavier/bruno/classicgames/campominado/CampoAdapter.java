package xavier.bruno.classicgames.campominado;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import xavier.bruno.classicgames.R;

public class CampoAdapter extends BaseAdapter {

	private CampoMinadoActivity contexto;
	private Button[] botoes;
	private int linhas;
	private int colunas;
	private int bombas;
	private Campo campo;
	private TextView placar;
	private TextView cronometro;
	private boolean jogando;

	public CampoAdapter(CampoMinadoActivity contexto, Button[] botoes, int linhas, int colunas, int bombas) {
		this.contexto = contexto;
		this.botoes = botoes;
		this.linhas = linhas;
		this.colunas = colunas;
		this.bombas = bombas;

		placar = (TextView) contexto.findViewById(R.id.tvPlacar);
		cronometro = (TextView) contexto.findViewById(R.id.tvCronometro);
	}

	@Override
	public int getCount() {
		return linhas * colunas;
	}

	@Override
	public Object getItem(int position) {
		return botoes[position];
	}

	@Override
	public long getItemId(int position) {
		return botoes[position].getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			botoes[position].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Button btn = (Button) v;
					int id = btn.getId();
					int x = id % colunas;
					int y = id / colunas;
					Log.i("CLASSICGAMES", String.format("click %dx%d", x, y));

					if (campo == null) {
						
						campo = new Campo(linhas, colunas, bombas, x, y);
						final Date horaInicial = new Date();
						jogando = true;

						final Handler h = new Handler();
						Runnable run = new Runnable() {

							@Override
							public void run() {
								Date horaAtual = new Date();
								long ms = horaAtual.getTime() - horaInicial.getTime();
								long seg = ms / 1000;
								long min = seg / 60;
								seg -= (min * 60);
								cronometro.setText(String.format("%02d:%02d", min, seg));
								if (jogando) {
									h.postDelayed(this, 1000);
								}
							}
						};
						h.postDelayed(run, 1000);
					}

					if (campo.getCelulas()[y][x].isBandeira()) {
						return;
					}

					boolean bomba = campo.marca(x, y);
					mostraCampo(botoes, campo);

					if (bomba) {
						fimJogo(false);
					} else {
						if (campo.ganhou()) {
							fimJogo(true);
						}
					}
				}
			});

			botoes[position].setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					
					if (campo == null) {
						return false;
					}

					Button btn = (Button) v;
					int id = btn.getId();
					int x = id % colunas;
					int y = id / colunas;
					Log.i("CLASSICGAMES", String.format("longclick %dx%d", x, y));

					if (campo.getCelulas()[y][x].isVisivel()) {
						return false;
					}

					ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);             
					toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 50);
					
					campo.marcaBomba(x, y);
					placar.setText(String.format("%d/%d", campo.getBandeiras(), bombas));
					mostraCampo(botoes, campo);

					return false;
				}
			});
		}
		return botoes[position];
	}

	protected void fimJogo(boolean venceu) {

		jogando = false;
		String mensagem = venceu ? 
				contexto.getResources().getString(R.string.venceu) :
					contexto.getResources().getString(R.string.perdeu);

		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					campo.limpa();
					placar.setText(String.format("%d/%d", campo.getBandeiras(), bombas));
					cronometro.setText("00:00");
					mostraCampo(botoes, campo);
					campo = null;
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					contexto.finish();
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
		builder.setMessage(mensagem + "\nJogar novamente?").setPositiveButton(R.string.sim, dialogClickListener)
				.setNegativeButton(R.string.nao, dialogClickListener).show();
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	protected void mostraCampo(Button[] botoes, Campo campo) {

		int[] cores = new int[] { android.R.color.holo_blue_dark, android.R.color.holo_green_dark,
				android.R.color.holo_red_dark, android.R.color.holo_purple, android.R.color.holo_orange_dark,
				android.R.color.darker_gray, android.R.color.black, android.R.color.holo_blue_bright };

		for (int y = 0; y < linhas; y++) {
			for (int x = 0; x < colunas; x++) {

				int b = y * colunas + x;

				botoes[b].setBackground(null);
				botoes[b].setText(null);
				botoes[b].setBackgroundColor(contexto.getResources().getColor(android.R.color.background_light));

				if (campo.getCelulas()[y][x].isBandeira()) {
					botoes[b].setBackground(contexto.getDrawable(R.drawable.ic_flag_black_18dp));
					continue;
				}

				if (!campo.getCelulas()[y][x].isVisivel()) {
					continue;
				}

				if (campo.getCelulas()[y][x].isBomba()) {
					botoes[b].setBackground(contexto.getDrawable(R.drawable.ic_all_out_black_18dp));
					continue;
				}

				botoes[b].setBackgroundColor(contexto.getResources().getColor(android.R.color.darker_gray));
				if (campo.getCelulas()[y][x].getBombasRedor() == 0) {
					botoes[b].setText(" ");

				} else {
					int n = campo.getCelulas()[y][x].getBombasRedor();
					botoes[b].setTextColor(contexto.getResources().getColor(cores[n - 1]));
					botoes[b].setText(String.valueOf(n));
				}

			}
		}
	}

}
