
package teo.info;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
/*Esta clase realiza el manejo de distribuciones de probabilidad, calculo de media y desvio
* de una imagen dada*/
public class Distribuciones {
    private Imagen imagen;
    private int[][] M_imagen;
    double cantidad_digitos;

    public Distribuciones(Imagen imagen) {
        this.imagen = imagen;
        this.M_imagen=imagen.getMatriz();
        this.cantidad_digitos = (imagen.getLargo() *imagen.getAncho());
    }

    private Map<Integer,Double> get() {
        /*Cuenta las ocurrencias de cada simbolo y las inserta en un mapa*/
        Map <Integer,Double> distribuciones = new HashMap<Integer, Double>();
        for (int i = 0; i < imagen.getLargo(); i++) {
            for (int j = 0; j < imagen.getAncho(); j++) {
                if (distribuciones.containsKey(M_imagen[i][j])) {
                    Double cantidad = distribuciones.get(M_imagen[i][j]) + 1.0;
                    distribuciones.put(M_imagen[i][j], cantidad);
                } else
                    distribuciones.put((M_imagen[i][j]), 1.0); // si no existe el valor lo inserto en el mapa
            }

        }
        return distribuciones;
    }

    public Map<Integer,Double> get_Distribucion() {
        /*Calcula la distribucion de probabilidad de cada simbolo*/
        Map<Integer, Double> distribuciones = this.get();
        Map porcentaje = new HashMap<Integer, Double>();
        Iterator it = distribuciones.keySet().iterator();
        while (it.hasNext()) {
            Integer key = (Integer) it.next();
            porcentaje.put(key, (distribuciones.get(key) / cantidad_digitos));
        }
        return porcentaje;
    }

    public double get_Media() {
        /*Calcula la media de la imagen*/
        Double resultado = 0.0;
        Map distribucion = this.get();
        Iterator it = distribucion.keySet().iterator();
        while (it.hasNext()) {
            Integer clave = (Integer) it.next();
            resultado=resultado+((Double)((clave) * (double)distribucion.get(clave)));
        }
        return resultado/cantidad_digitos;
    }

    public double get_DesvioEstandar(){
        /*Calcula el desvio de la imagen*/
        double media=this.get_Media();
        double resultado =0;
        Map distribucion = this.get();
        Iterator it = distribucion.keySet().iterator();
        while (it.hasNext()) {// hago la sumatoria del desvio
            Integer clave = (Integer) it.next();
            resultado+=(Math.pow((clave-media),2)* (Double)(distribucion.get(clave))) ;
        }
        resultado=resultado/cantidad_digitos;
        return ( Math.sqrt(resultado));
    }

}


