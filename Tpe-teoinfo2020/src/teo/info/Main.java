package teo.info;
import java.io.BufferedReader;
import java.util.*;

public class Main {
    public static String path;
	public static void main(String[] args) {
	//introduccion
		System.out.println("Durante los años ‘80, cosas extrañas ocurrieron en la ciudad de Hawkins cuando un niño llamado Will Byers desapareció en un universo paralelo.");
		System.out.println("Su madre Joyce acudió a la estación de policía con una fotografía de su hijo para realizar la denuncia, y obtener algún dato sobre el paradero del niño.");
        System.out.println("ingrese la ruta donde guardar los archivos ");
        Scanner in=new Scanner(System.in);
        path=in.nextLine();
		//ejercicio 1: lista ordenada
		System.out.println();
		System.out.println();
		System.out.print("Ejercicio 1: ordenaremos las fotos arrojadas por el sistema según");
		System.out.println(" su parecido con la imagen de Will");
		System.out.println("Las imagenes pueden verse en la carpeta especificada");
		Imagen original=new Imagen();
		original.getValoresMat("Will(Original)");Imagen will1=new Imagen();
		Calculador calc=new Calculador();
		System.out.println("El resultado fue el siguiente: ");
		ArrayList<NodoImagen> listaOrd= calc.ordenarImagenes(original);
		calc.mostrarLista(listaOrd);

		//ejercicio 2: histogramas, media, desvio
		System.out.println();
		System.out.println();
		System.out.print("Un policia amigo trajo otra foto para analizar, para no volver a usar el ");
		System.out.println("sistema, las analizaremos según su media, desvío y distribución");
		System.out.println("Ejercicio 2: analizaremos la imagen original, la que trajo el policia y la mejor obtenida por el sistema");
		System.out.println("Las distribuciones de probabilidades se veran en histogramas en la carpeta especificada");
		Distribuciones dis_original=new Distribuciones(original);
		Imagen policia=new Imagen();
		policia.getValoresMat("Will_ej2");
		Imagen mejor=listaOrd.get(0).getImagen();
		Distribuciones dis_policia=new Distribuciones(policia);
		Distribuciones dis_mejor=new Distribuciones(mejor);
		Graficos graficos=new Graficos();
		graficos.Get_histograma(dis_original.get_Distribucion(),"histograma_"+original.getNombre());
		graficos.Get_histograma(dis_policia.get_Distribucion(),"histograma_"+policia.getNombre());
		graficos.Get_histograma(dis_mejor.get_Distribucion(),"histograma_"+mejor.getNombre());
		System.out.println("Medias: ");
		System.out.println("Imagen original: "+dis_original.get_Media()+" Imagen policia: "+dis_policia.get_Media()+" Imagen del sistema: "+dis_mejor.get_Media());
		System.out.println("Desvios: ");
		System.out.println("Imagen original: "+dis_original.get_DesvioEstandar()+" Imagen policia: "+dis_policia.get_DesvioEstandar()+" Imagen del sistema: "+dis_mejor.get_DesvioEstandar());

		//Ejercicio 3: Huffman, compresión, descompresión y tasas de compresión
		System.out.println();
		System.out.println();
		System.out.println("Ejercicio 3: comprimiremos usando el metodo de Huffman distintas imagenes");
		System.out.println("Las imagenes descomprimidas pueden encontrarse en la carpeta especificada con nombres del tipo <imagen_comprimida>_<imagen de la distribucion>");
		System.out.println();
		System.out.println("Con la distribución de la imágen original comprimimos: ");
		Huffman huffman=new Huffman();
		String name="original_original";
		System.out.println("   -la imágen original obteniendo una tasa de compresión de: "+huffman.getTasaCompresion(dis_original.get_Distribucion(),original,name));
		huffman.decodificar(name);
		name="policia_original";
		System.out.println("   -la imágen del policia obteniendo una tasa de compresión de: "+huffman.getTasaCompresion(dis_original.get_Distribucion(),policia,name));
		huffman.decodificar(name);
		name="mejorsistema_original";
		System.out.println("   -la mejor imágen arrojada por el sistema obteniendo una tasa de compresión de: "+huffman.getTasaCompresion(dis_original.get_Distribucion(),mejor,name));
		huffman.decodificar(name);
		System.out.println();
		System.out.println("Luego comprimimos la imágen del policia con su propia distribución");
		name="policia_policia";
		System.out.print("obteniendo una tasa de compresión de: "+huffman.getTasaCompresion(dis_policia.get_Distribucion(),policia,name));
		huffman.decodificar(name);

		//Ejercicio 4:
		System.out.println();
		System.out.println();
		System.out.println("Tres canales de televisión distintos (Canal 2, Canal 8 y Canal 10) transmiten todos los días la fotografía de Will. ");
		System.out.println(" Sin embargo, un fenómeno extraño hace que cada canal distorsione las imágenes de diferente forma.");
		System.out.println(" Al notarlo, el comisario de policía decidió estudiar el ruido de cada uno, esperando que esta información permita encontrar al joven Will.");
		System.out.println("Ejercicio 4: calcularemos el ruido teórico y por muestreo de cada canal");
		Imagen canal2=new Imagen();
		canal2.getValoresMat("Will_Canal2");
		Canal c2=new Canal(original,canal2);
		Imagen canal8=new Imagen();
		canal8.getValoresMat("Will_Canal8");
		Canal c8=new Canal(original,canal8);
		Imagen canal10=new Imagen();
		canal10.getValoresMat("Will_Canal10");
		Canal c10=new Canal(original,canal10);
		System.out.println("Los ruidos obtenidos de forma teórica fueron: ");
		System.out.println("   -Canal 2: "+c2.getRuido());
		System.out.println("   -Canal 8: "+c8.getRuido());
		System.out.println("   -Canal 10: "+c10.getRuido());
		System.out.println("Los ruidos obtenidos por muestreo aleatorio, con un epsilon de 0.0001 fueron: ");
		System.out.println("   -Canal 2: "+c2.getRuidoMuestreo(0.0001));
		System.out.println("   -Canal 8: "+c8.getRuidoMuestreo(0.0001));
		System.out.println("   -Canal 10: "+c10.getRuidoMuestreo(0.0001));
		System.out.println("En la carpeta especificada se guardo la evolución del error en las primeras 500 muestras de cada canal");
	}
}





