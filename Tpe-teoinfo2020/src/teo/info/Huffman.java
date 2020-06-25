package teo.info;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Byte.parseByte;
/*Realiza todas las acciones referidas al Metodo Huffman*/
public class Huffman {
    ManejadorArchivos files;
    public Huffman (){
        files=new ManejadorArchivos();
    }

    private ArrayList<NodoArbolH> listaOrdenada(Map<Integer,Double> probabilidades){
        /*Genera una lista de nodos de arbol ordenadas segun su probabilidad*/
        ArrayList<NodoArbolH> lista=new ArrayList<NodoArbolH>();
        for(Integer clave: probabilidades.keySet())
        {
            NodoArbolH nodo =new NodoArbolH(clave,probabilidades.get(clave));
            lista.add(nodo);
        }
        lista.sort(null);
        return lista;
    }

    public NodoArbolH generarArbol(Map<Integer,Double> probabilidades){
        /*Genera el arbol de huffman*/
        ArrayList<NodoArbolH> lista= listaOrdenada( probabilidades);
        while(lista.size()>1){
            NodoArbolH mayor=lista.remove(1);
            NodoArbolH menor=lista.remove(0);
            double probabilidad= mayor.getProbabilidad()+menor.getProbabilidad();
            NodoArbolH nodo =new NodoArbolH(-1,probabilidad);
            nodo.setMenor(menor);
            nodo.setMayor(mayor);
            lista.add(nodo);
            lista.sort(null);
        }
        return lista.get(0);
    }
    public void generarTabla(String codigo, int lon,NodoArbolH arbol, HashMap<Integer, Pair<Integer,Byte>>hash){
        /*A partir del arbol, genera una tabla para codificar*/
       if(!arbol.esHoja()){
           generarTabla(codigo+"1",lon+1,arbol.getMenor(),hash);
           generarTabla(codigo+"0",lon+1,arbol.getMayor(),hash);
       }else
           hash.put(arbol.getSimbolo(),new Pair<Integer,Byte>(lon,Byte.parseByte(codigo,2)));

    }


    public double codificar(Map<Integer,Double> probabilidades, Imagen imagen,String name){
        /*Codifica una imagen a partir de una distribucion de probabilidades e inserta el codigo
        * y otros datos necesarios en un archivo. Devuelve el tamaño en bytes de la codificacion*/
        int pos=0;
        int libre=8;
        int lon=0;
        byte actual;
        ArrayList<Byte> arreglo= new ArrayList<Byte>();
        HashMap<Integer,Pair<Integer,Byte>> hash=new HashMap<>();
        generarTabla("",0,this.generarArbol(probabilidades),hash);
        int[][] matriz=imagen.getMatriz();
        for(int i=0;i<imagen.getLargo();i++){
            for(int j=0;j<imagen.getAncho();j++){
                int s= matriz[i][j];
                lon=hash.get(s).getKey();
                actual=hash.get(s).getValue();
                if(libre>=lon){              //       hay espacio para el codigo entero
                    actual= (byte) (actual<<libre-lon);  //desplazamos el codigo
                    if(pos>=arreglo.size())
                        arreglo.add(pos, (byte) 0);
                    arreglo.add(pos,(byte) (actual|arreglo.remove(pos)));
                    libre-=lon;
                    if(libre==0)  // ocupe todo el byte actual
                    {
                        libre=8;
                        pos++;
                        arreglo.add(pos, (byte) 0);
                    }
                }else { //si no hay espacio para el codigo entero
                    byte aux=(byte) (actual>>lon-libre);
                    libre=8-(lon-libre);
                    actual= (byte) (actual<<libre);
                    arreglo.add(pos,(byte)(aux|arreglo.remove(pos)));
                    pos++;
                    arreglo.add(pos,(byte)actual);
                }

            }
        }
        files.escribir(arreglo,probabilidades,imagen,name);
        return arreglo.size()+probabilidades.size()*((Integer.SIZE+Double.SIZE)/8);
    }

   public void decodificar(String name){
       /*Toma un archivo con una codificacion y genera a partir de el una imagen*/
        ArrayList<Byte> codigo =new ArrayList<>();
        HashMap<Integer,Double> probabilidades=new HashMap<>();
        String nombre="";
        Integer dimension[]= new Integer[3];
        files.leer(codigo,dimension,probabilidades,name);
        int largo=dimension[0];
        int ancho=dimension[1];
        int tamaño=dimension[2];
        int[][] matriz=new int[largo][ancho];
        NodoArbolH arbol=generarArbol(probabilidades);
        NodoArbolH arbolaux= arbol;
        int pos=0;
        byte actual=codigo.get(pos);
        pos++;
        int bit=0;
        for(int i=0;i<largo;i++){
            for(int j=0;j<ancho;j++){
                while((!arbolaux.esHoja())){
                    if(actual>=0)
                        arbolaux=arbolaux.getMayor();
                    else
                        arbolaux=arbolaux.getMenor();
                    bit++;
                    actual=(byte) (actual<<1);
                    if(bit>=8) {
                        bit = 0;
                        if(pos<codigo.size())
                            actual = codigo.get(pos);
                        pos++;
                    }
                }
                matriz[i][j]=arbolaux.getSimbolo();
                arbolaux=arbol;
                }
           }

          Imagen.crearImagen(matriz,largo,ancho,name);
        }

    public double getTasaCompresion(Map<Integer,Double> probabilidades, Imagen imagen,String name){
        /*Calcula la tasa de compresion para una imagen con una distribucion de probabilidades*/
        double tnuevo=codificar(probabilidades,imagen,name);
        double toriginal=imagen.getTamanio();
        return ((double)(toriginal/tnuevo));
    }

}
