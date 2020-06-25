package teo.info;
import java.util.ArrayList;

/* Esta clase se utilizará para realizar calculos de Correlacion entre imágenes
* y ordenarlas segun su correlación con la imágen original*/
public class Calculador{
	public Calculador() {}

	public int getCorrelacionCruzada(int[][] matrizA, int [][] matrizB, int ancho, int largo) {
		/*Devuelve la correlacion entre dos matrices*/
		int cc=0;
		for(int i=0;i<ancho;i++) {
			for(int j=0; j<ancho;j++) {
				cc+=matrizA[i][j]*matrizB[i][j];
			}
		}
		return cc;
	}

	public void mostrarLista(ArrayList<NodoImagen>lista){
		/*Muestra una lista de NodoImagen*/
		int i=0;
		for(NodoImagen nodo:lista){
			System.out.print("la imagen en la posicion "+i+" es: "+nodo.getImagen().getNombre());
			System.out.println(" y su correlacion cruzada es: "+nodo.getCorrelacion());
			i++;
		}
	}

	public ArrayList<NodoImagen> ordenarImagenes(Imagen original){
		/*Crea las 5 imagenes y las inserta en una lista ordenada segun su correlacion
		* con la imagen original*/
		ArrayList<NodoImagen> lista =new ArrayList<NodoImagen>();
		int[][] morig= original.getMatriz();//cargo la imagen original
		for (int i=1;i<=5;i++) {
			Imagen obtenida= new Imagen(); //creo una imagen
			int[][] mob=obtenida.getValoresMat("Will_"+i); //cargo una nueva imagen
			NodoImagen nodo=new NodoImagen(obtenida,this.getCorrelacionCruzada(morig, mob, original.getAncho(), original.getAncho()));//creo un nodo con la imagen y la correlacion
			lista.add(nodo);
		}
		lista.sort(null);
		return lista;
	}
}
