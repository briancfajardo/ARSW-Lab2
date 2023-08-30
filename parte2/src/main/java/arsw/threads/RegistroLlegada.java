package arsw.threads;

public class RegistroLlegada {

	private int ultimaPosicionAlcanzada=1;

	private String ganador=null;
	private final Object lock = new Object();
	
	public String getGanador() {
		return ganador;
	}

	public void setGanador(String ganador) {
		this.ganador = ganador;
	}

	public int getUltimaPosicionAlcanzada() {
		return ultimaPosicionAlcanzada;
	}

	public void setUltimaPosicionAlcanzada(int ultimaPosicionAlcanzada) {
		synchronized (lock) {
			this.ultimaPosicionAlcanzada = ultimaPosicionAlcanzada;
		}
	}

	
	
}
