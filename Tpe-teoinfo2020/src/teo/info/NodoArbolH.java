package teo.info;
import java.lang.Comparable;
/*Clase que implementa un nodo del arbol de huffman, es comparable para poder crear el arbol*/
public class NodoArbolH implements Comparable{
    int simbolo;
    double probabilidad;
    NodoArbolH menor;
    NodoArbolH mayor;

    public NodoArbolH(int simbolo, double probabilidad) {
        this.simbolo = simbolo;
        this.probabilidad = probabilidad;
        this.menor=null;
        mayor=null;
    }

    public int getSimbolo() {
        return simbolo;
    }

    public double getProbabilidad() {
        return probabilidad;
    }

    public NodoArbolH getMenor() {
        return menor;
    }

    public void setMenor(NodoArbolH menor) {
        this.menor = menor;
    }

    public NodoArbolH getMayor() {
        return mayor;
    }

    public void setMayor(NodoArbolH mayor) {
        this.mayor = mayor;
    }

    public boolean esHoja(){
        return ((mayor==null)&&(menor==null));
    }

    @Override
    public int compareTo(Object otroNodo) {
        /*Compara al reves del orden natural para ordenar de mayor a menor*/
        double result= this.probabilidad-((NodoArbolH) otroNodo).getProbabilidad();
        if (result<0)
            return -1;
        else if (result>0)
            return 1;
        return (int) result;
    }

}
