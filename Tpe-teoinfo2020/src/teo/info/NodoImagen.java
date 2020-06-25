package teo.info;
/*Esta clase sirve de nodo para las listas ordenadas de correlacion cruzada
* Es comparable para ordenar la lista*/
public class NodoImagen implements Comparable{
	private Imagen imagen;
	private int correlacion;

	public NodoImagen(Imagen imagen, int correlacion) {
		this.correlacion=correlacion;
		this.imagen=imagen;
	}
	public Imagen getImagen() {
		return imagen;
	}
	
	public int getCorrelacion() {
		return correlacion;
	}
	
	@Override
	public int compareTo(Object otroNodo) {
		return (((NodoImagen) otroNodo).getCorrelacion())-correlacion; //esta puesto en este sentido para que el sort quede de mayor a menor
	}

}
