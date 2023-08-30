package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * 
 */
public class Galgo extends Thread {
	private int paso;
	private final Carril carril;
	RegistroLlegada regl;
	private boolean isPaused = false;
	private final Object pauseLock;

	public Galgo(Carril carril, String name, RegistroLlegada reg, Object pauseLock) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl=reg;
		this.pauseLock = pauseLock;
	}

	public void corra() throws InterruptedException {
		while (paso < carril.size()) {

			synchronized (pauseLock) {
				while (isPaused) {
					pauseLock.wait(); // Espera mientras esté pausado
				}
			}

			Thread.sleep(100);
			carril.setPasoOn(paso++);
			carril.displayPasos(paso);
			
			if (paso == carril.size()) {						
				carril.finish();
				int ubicacion=regl.getUltimaPosicionAlcanzada();
				synchronized (regl) {
					ubicacion = regl.getUltimaPosicionAlcanzada();
					regl.setUltimaPosicionAlcanzada(ubicacion + 1);
				}
				System.out.println("El galgo "+this.getName()+" llegó en la posición "+ubicacion);
				if (ubicacion==1){
					regl.setGanador(this.getName());
				}
				
			}
		}
	}


	@Override
	public void run() {
		
		try {
			corra();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void pausar() {
		isPaused = true;
	}

	public void reanudar() {
		isPaused = false;
		synchronized (pauseLock) {
			pauseLock.notifyAll(); // Notifica a los hilos en espera
		}
	}

}
