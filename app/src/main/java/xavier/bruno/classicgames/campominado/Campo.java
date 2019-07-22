package xavier.bruno.classicgames.campominado;

import java.util.Date;
import java.util.Random;

public class Campo {

	private Celula[][] celulas;
	private int bombas;
	private int linhas;
	private int colunas;
	private int bandeiras;
	private int acertos;

	public Campo(int linhas, int colunas, int bombas, int x0, int y0) {

		this.bombas = bombas;
		this.linhas = linhas;
		this.colunas = colunas;
		celulas = new Celula[linhas][colunas];

		for (int y = 0; y < linhas; y++) {
			for (int x = 0; x < colunas; x++) {
				celulas[y][x] = new Celula();
			}
		}
		
		Random gerador = new Random(new Date().getTime());

		for (int b = 0; b < bombas;) {
			int linha = gerador.nextInt(linhas);
			int coluna = gerador.nextInt(colunas);
			if ((linha >= y0 - 1) && (linha <= y0 + 1) && (coluna >= x0 - 1) && (coluna <= x0 + 1)) {
				continue;
			}
			if (!celulas[linha][coluna].isBomba()) {
				celulas[linha][coluna].setBomba(true);
				b++;
			}
		}

		for (int y = 0; y < linhas; y++) {
			for (int x = 0; x < colunas; x++) {
				if (celulas[y][x].isBomba()) {
					continue;
				}
				int n = 0;
				n += ehBomba(x-1, y-1);
				n += ehBomba(x, y-1);
				n += ehBomba(x+1, y-1);
				n += ehBomba(x-1, y);
				n += ehBomba(x+1,y);
				n += ehBomba(x-1, y+1);
				n += ehBomba(x, y+1);
				n += ehBomba(x+1, y+1);
				celulas[y][x].setBombasRedor(n);
			}
		}
	}

	private int ehBomba(int x, int y) {
		if ((y < 0) || (x < 0) || (y == linhas) || (x == colunas)) {
			return 0;
		}
		return celulas[y][x].isBomba() ? 1 : 0;
	}
	
	public boolean marca(int x, int y) {
		celulas[y][x].setVisivel(true);
		acertos++;
		if (celulas[y][x].isBomba()) {
			return true;
		}
		
		if (celulas[y][x].getBombasRedor() == 0) {
			marcaVizinhos(x, y);
		}
		return false;
	}
	
	public boolean ganhou() {
		
		return acertos == ((linhas * colunas) - bombas);
	}
	
	public void marcaVizinho(int x, int y) {
		
		if ((y < 0) || (x < 0) || (y == linhas) || (x == colunas)) {
			return;
		}
		
		if (celulas[y][x].isVisivel() || celulas[y][x].isBomba()) {
			return;
		}
		
		celulas[y][x].setVisivel(true);
		acertos++;
		
		if (celulas[y][x].getBombasRedor() != 0) {
			return;
		}
		
		marcaVizinhos(x, y);
		
	}
	
	public void marcaVizinhos(int x, int y) {
		
		marcaVizinho(x-1, y-1);
		marcaVizinho(x, y-1);
		marcaVizinho(x+1, y-1);
		marcaVizinho(x-1, y);
		marcaVizinho(x+1, y);
		marcaVizinho(x-1, y+1);
		marcaVizinho(x, y+1);
		marcaVizinho(x+1, y+1);
		
	}

	public void marcaBomba(int x, int y) {
		if (celulas[y][x].isBandeira()) {
			bandeiras--;
		} else {
			bandeiras++;
		}
		celulas[y][x].setBandeira(!celulas[y][x].isBandeira());
		
	}

	public Celula[][] getCelulas() {
		return celulas;
	}

	public int getBandeiras() {
		return bandeiras;
	}

	public void setBandeiras(int bandeiras) {
		this.bandeiras = bandeiras;
	}

	public int getAcertos() {
		return acertos;
	}

	public void limpa() {
		for (int y = 0; y < linhas; y++) {
			for (int x = 0; x < colunas; x++) {
				celulas[y][x] = new Celula();
			}
		}
		acertos = 0;
		bandeiras = 0;
	}

}
