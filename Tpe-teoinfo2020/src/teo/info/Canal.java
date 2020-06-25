package teo.info;

import javafx.util.Pair;

import java.util.HashMap;
/*Esta clase se encarga del manejo de canales, creacion de matriz de transiciones
* y calculos de ruido*/
public class Canal {
    private static int CANT_TONOS = 256;
    private static int MIN_PRUEBAS=5000;
    private double[][] matriz;
    private double[]ocurrencias;
    private double[][] m_acum;
    private double[] probs;
    private  Imagen foto;
    private Imagen canal;

    public Canal(Imagen foto, Imagen canal) {
        /*Crea un canal para una imagen de entrada y una de salida*/
        this.foto=foto;
        this.canal=canal;
        this.ocurrencias = new double[CANT_TONOS];
        this.matriz = matrizTransicion();
        m_acum=getMatAcum();
        probs=getArrAcum();
    }

    ;

    private double[][] matrizTransicion() {
        /*Genera la matriz de transiciones de un canal
        * Por cada entrada de la matriz sumamos uno a las ocurrencias de dicha entrada,
        * Para cada par (entrada,salida) calculamos su probabilidad p(y/x)
        * dividiendo la cantidad de ocurrencias del par sobre la cantidad de ocurrencias de x */
        double[][] transiciones = new double[CANT_TONOS][CANT_TONOS];
        HashMap<Pair<Integer, Integer>, Double> probabilidades = new HashMap<>();
        int[][] m_foto = foto.getMatriz();
        int[][] m_canal = canal.getMatriz();
        for (int i = 0; i < foto.getLargo(); i++) {
            for (int j = 0; j < foto.getAncho(); j++) {
                int x = m_foto[i][j];
                int y = m_canal[i][j];
                ocurrencias[x]++;
                Pair<Integer, Integer> par = new Pair<Integer, Integer>(x, y);
                if (probabilidades.containsKey(par)) {
                    double valor = probabilidades.get(par);
                    valor++;
                    probabilidades.put(par, valor);
                } else
                    probabilidades.put(par, 1.0);
            }
        }
        for (Pair<Integer, Integer> par : probabilidades.keySet()) {
            int x = par.getKey();
            int y = par.getValue();
            transiciones[x][y] = ((double) probabilidades.get(par) / ocurrencias[x]);
        }
        /*Calculo de p(x)*/
        for (int i=0;i<CANT_TONOS;i++)
            ocurrencias[i]=((double)ocurrencias[i]/(foto.getLargo()*foto.getAncho()));
        return transiciones;

    }

    public double getRuido() {
        /*Calculo del ruido con la matriz de transiciones y la formula teórica*/
        double ruido = 0.0;
        double ruido_col=0.0;
        for (int i = 0; i < CANT_TONOS; i++) {
            ruido_col = 0.0;
            for (int j = 0; j < CANT_TONOS; j++) {
                if (matriz[i][j] != 0)
                    ruido_col += ((double) matriz[i][j] * (-Math.log(matriz[i][j]) / Math.log(2)));
            }
            ruido += ((double) ruido_col * ocurrencias[i]);
        }
        return ruido;
    }

    private double[] getArrAcum() {
        /*Obtiene el arreglo de probabilidades acumuladas de x*/
        double acum=0.0;
        double[] arr=new double[CANT_TONOS];
        for(int i=0;i<CANT_TONOS;i++){
            acum+=((double)ocurrencias[i]);
            arr[i]=acum;
        }
        return arr;
    }

    private double[][] getMatAcum(){
        /*Obtiene la matriz de probabilidades de transicion acumulada*/
        double m_acum[][]=new double[CANT_TONOS][CANT_TONOS];
        for(int i=0;i<CANT_TONOS;i++){
            double acum=0.0;
            for(int j=0;j<CANT_TONOS;j++){
                acum+=matriz[i][j];
                m_acum[i][j]=acum;
            }
        }
        return m_acum;
    }

    public int getX() {
        /*Genera aleatoriamente una entrada x basado en el arreglo de prob. acumulada*/
        double rand = Math.random();
        for (int i = 0; i < CANT_TONOS; i++) {
            if (rand <= probs[i])
                return i;
        }
        return -1;
    }

    public int getY(int x){
        /*Genera una salida aleatoria y basado en
        * la matriz de prob. acumulada y dado un x*/
        double rand=Math.random();
        for(int i=0;i<CANT_TONOS;i++){
            if(rand<=m_acum[x][i])
                return i;
        }
        return -1;
    }

    public double getRuidoMuestreo(double epsilon){
        /*Calculo del ruido por muestreo y genera el grafico de evolucion de error
        * -Genera x e y aleatoriamente
        * -Suma las ocurrencias de cada par y de cada x
        * -Calcula el ruido para cada x
        * -Calcula el ruido total*/
        int[] cant_x=new int[CANT_TONOS];
        int[][] aciertos = new int[CANT_TONOS][CANT_TONOS];
        int tiradas=0;
        double[] evolucion= new double[501];
        double ruido_ant=-1.0;
        double ruido_act=0.0;
        while((tiradas<MIN_PRUEBAS)||(!converge(ruido_act,ruido_ant,epsilon))){
            tiradas++;
            int x=getX();
            int y=getY(x);
            cant_x[x]++;
            aciertos[x][y]++;
            double temp=0.0;
            ruido_ant=ruido_act;
            ruido_act=0.0;
            for(int i=0;i<CANT_TONOS;i++){
                temp=0.0;
                for(int j=0;j<CANT_TONOS;j++) {
                    double prob = ((double) aciertos[i][j] / cant_x[i]);
                    if (prob > 0.0)
                        temp+= ((double) prob * (-Math.log(prob) / Math.log(2)));
                }
                ruido_act+=((double)(((double)cant_x[i])/((double)tiradas))*temp);

            }
           if(tiradas<=500){
               evolucion[tiradas]=Math.abs(ruido_act-ruido_ant);
           }
        }
        Graficos.getGraficoEvolucion(evolucion,canal.getNombre());
        return ((double)ruido_act);
    }


    private boolean converge(double ruido_act, double ruido_ant,double epsilon) {
        /*Devuelve true si el error es menor a epsilon*/
        return(Math.abs(ruido_act-ruido_ant)<epsilon);
    }


}



