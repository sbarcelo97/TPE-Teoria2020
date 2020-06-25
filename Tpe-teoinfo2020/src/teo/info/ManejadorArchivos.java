package teo.info;

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
/*Esta clase se utiliza para leer y escribir en archivos al codificar y decodificar*/
public class ManejadorArchivos {

    public void leer(ArrayList<Byte> codigos, Integer[] dimension, Map<Integer,Double> dis, String name) {
        /*Lee de un archivo*/
        File archivo = null;
        FileReader lee = null;
        BufferedReader buf = null;
        ArrayList<Byte> b = new ArrayList();
        String linea;
        try {
            archivo = new File(Main.path+"\\cod_"+name+".txt");
            if (!archivo.exists()) {
                System.out.println("el archivo no existe");
            }
            lee = new FileReader(archivo);
            buf = new BufferedReader(lee);
            Integer largo=Integer.parseInt(buf.readLine());
            Integer ancho=Integer.parseInt(buf.readLine());
            int tamaño=Integer.parseInt(buf.readLine());
            dimension[0]=largo;
            dimension[1]=ancho;
            dimension[2]=tamaño;
            for(int i=0;i<tamaño;i++){
                linea=buf.readLine();
                Byte A = (Byte.parseByte(linea.substring(0, linea.length())));
                codigos.add(A);
            }
            while((linea=buf.readLine())!=null){
                 Integer key= (Integer.parseInt(linea));
                 linea=buf.readLine();
                 Double valor = (Double.parseDouble(linea));
                 dis.put(key,valor);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (lee!=null)
                    lee.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    public void escribir(ArrayList<Byte> codigo, Map<Integer,Double> dis, Imagen im,String name) {
        /*Escribe el codigo y datos necesarios en el archivo*/
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(new File(Main.path+"\\cod_"+name+".txt"));
            pw = new PrintWriter(fichero);
            pw.println(im.getLargo());
            pw.println(im.getAncho());
            pw.println(codigo.size());
            for (byte b: codigo)
                pw.println(b);
            for(int i:dis.keySet()){
                pw.println(i);
                pw.println(dis.get(i));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}

