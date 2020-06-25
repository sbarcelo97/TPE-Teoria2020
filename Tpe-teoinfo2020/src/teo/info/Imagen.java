package teo.info;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
/*Esta clase realiza el manejo de todas las imagenes utilizadas*/
public class Imagen {
	private int ancho;
	private int largo;
	private String nombre;
	private int[][] matriz;
	private static int numero;
    public Imagen() {
    	largo=0;
    	ancho=0;
    }

    private BufferedImage getImage(String filename) {
        /*Carga la imagen en un buffer*/
        try {
            InputStream in = getClass().getResourceAsStream(filename);// ingresa el directorio en la entrada que luego es cargada en la
           return ImageIO.read(in);
        } catch (IOException e) {
            System.out.println("The image was not loaded.");
        }
        return null;
    }

    public static void crearImagen(int [][] matriz,int alto, int ancho,String name){
        /*Crea una imagen a partir de una matriz y sus dimensiones*/
    File fichero = new File(Main.path+"\\"+name+".bmp");
    String formato = "bmp";
    // Creamos la imagen para dibujar en ella.
    BufferedImage imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
       for (int i=0;i<1700;i++) {
            for (int j = 0; j < 1310; j++) {
                int rgb=new Color(matriz[i][j],matriz[i][j],matriz[i][j]).getRGB();
                imagen.setRGB(j,i,rgb);
            }
        }
    // Escribimos la imagen en el archivo.
        try {
        ImageIO.write(imagen, formato, fichero);
        }
        catch (IOException e) {
        System.out.println("Error de escritura");
        }

    }

    public int[][] getValoresMat(String nombrefoto) {
        /*Carga los valores de la imagen dado su ubicacion*/
    	this.nombre=nombrefoto;
        BufferedImage imagen = this.getImage("/"+nombrefoto+".bmp");//lee lo que hay en la carpeta imagenes
        this.ancho=imagen.getWidth();
        this.largo=imagen.getHeight();
        this.matriz = new int[largo][ancho];
        for (int fila = 0; fila < largo; fila++) {
            for (int col = 0; col < ancho; col++) {
                Color Tpixel =new Color(imagen.getRGB(col, fila),true); //devuelve el tono de color del pixel
                matriz[fila][col] =Tpixel.getBlue() ;
            }
        }
        return matriz;
    }
    
    public int[][] getMatriz(){
        /*Devuelve la matriz asociada a la imagen*/
    	return this.matriz;
    }
    
    public String getNombre() {
    	return nombre;
    }
    
    public int getAncho() {
    	return ancho;
    }

    public int getLargo() {
    	return largo;
    }

    public double getTamanio(){return (ancho*largo);}
}






