package teo.info;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.util.Map;

/*Realiza el manejo de todos los graficos generados*/
public class Graficos {
    public Graficos(){};

    public static void getGraficoEvolucion(double[] evolucion, String nombre){
        /*Guarda un grafico de evolucion de error de un canal dado un arreglo de valores
        * de error para 500 muestras*/
        try {
            DefaultCategoryDataset data = new DefaultCategoryDataset();
            for (Integer i=0;i<=500;i++){
                data.addValue(evolucion[i],"",i);
            }
            JFreeChart J = ChartFactory.createLineChart("Grafico de evolucion","muestras","error",data,PlotOrientation.VERTICAL,true,true,true);
            ChartUtilities.saveChartAsJPEG(new File(Main.path+"\\Graficoevolucion"+nombre + ".jpg"), 1,J, 600, 400);
        } catch (Exception e) {
            System.out.println("error en el grafico" + e);
        }
    }




    public void Get_histograma (Map<Integer,Double> mapa,String nombre){
        /*Guarda un histograma dada una distribucion de probabilidades*/
        try {
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        for (Integer i=0;i<=255;i++){
            if (mapa.containsKey(i)) {
                data.addValue(mapa.get(i),i,"");
            }
        }
            JFreeChart J = ChartFactory.createBarChart3D(nombre, "Escala de grises", "cantidad", data, PlotOrientation.VERTICAL, true, true, true);
            ChartUtilities.saveChartAsJPEG(new File(Main.path+"\\"+nombre + ".jpg"), 1,J, 600, 400);
        } catch (Exception e) {
            System.out.println("error en el histograma" + e);
            }
        }
    }
