package xavier.bruno.classicgames.campominado;

public class Celula {
	
	private int bombasRedor;
	private boolean bomba;
	private boolean visivel;
	private boolean bandeira;
	
	public int getBombasRedor() {
		return bombasRedor;
	}
	public void setBombasRedor(int bombasRedor) {
		this.bombasRedor = bombasRedor;
	}
	public boolean isBomba() {
		return bomba;
	}
	public void setBomba(boolean bomba) {
		this.bomba = bomba;
	}
	public boolean isVisivel() {
		return visivel;
	}
	public void setVisivel(boolean visivel) {
		this.visivel = visivel;
	}
	public boolean isBandeira() {
		return bandeira;
	}
	public void setBandeira(boolean bandeira) {
		this.bandeira = bandeira;
	}

}
