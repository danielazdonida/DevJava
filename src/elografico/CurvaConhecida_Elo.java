/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elografico;

/**
 *
 * @author Usuario
 */
public class CurvaConhecida_Elo {
    
    /**
     *
     * @param nomeArquivo
     * @param matriz
     * @param lin
     * @param col
     * @param elo
     */
    public CurvaConhecida_Elo(String nomeArquivo, double[][] matriz, int lin, int col, Elo elo) {
        this.nomeArquivo = nomeArquivo;
        this.matrizCurvas = matriz;
        this.col = col;
        this.lin = lin;
        this.elo = elo;
    }

    String nomeArquivo;
    double[][] matrizCurvas;
    int col;
    int lin;
    Elo elo;

    /**
     *
     * @return
     */
    public double MenorTempo() {
        double menor = 99999;

        for (int m = 0; m < col; m++) {
            for (int k = 0; k < lin; k++) {
                double[] line = new double[matrizCurvas.length];
                double[] row = new double[2];
                line[k] = matrizCurvas[k][m];
                row[m] = matrizCurvas[k][1];
                if (row[m] < menor) {
                    menor = row[m];

                }
            }
        }

        return menor;

    }

    /**
     *
     * @return
     */
    public double MaiorTempo() {
        double maior = 0;

        for (int k = 0; k < lin; k++) {
            for (int m = 0; m < col; m++) {

                double[] line = new double[matrizCurvas.length];
                double[] row = new double[2];
                line[k] = matrizCurvas[k][m];
                row[m] = matrizCurvas[k][1];
                if (row[m] > maior) {
                    maior = row[m];

                }
            }
        }

        return maior;
    }

    /**
     *
     * @return
     */
    public double MenorCorrente() {
        double menor = 99999;
        for (int k = 0; k < lin; k++) {
            for (int m = 0; m < col; m++) {

                double[] line = new double[matrizCurvas.length];
                double[] row = new double[2];
                line[k] = matrizCurvas[k][m];
                row[m] = matrizCurvas[k][0];
                if (row[m] < menor) {
                    menor = row[m];

                }
            }
        }

        return menor;
    }

    /**
     *
     * @return
     */
    public double MaiorCorrente() {
        double maior = 0;

        for (int k = 0; k < lin; k++) {
            for (int m = 0; m < col; m++) {

                double[] line = new double[matrizCurvas.length];
                double[] row = new double[2];
                line[k] = matrizCurvas[k][m];
                row[m] = matrizCurvas[k][0];
                if (row[m] > maior) {
                    maior = row[m];

                }
            }
        }return maior;

    }
    
    /**
     *
     * @param tempoEntrada
     * @return
     */
    public double CorrenteDoTempo(double tempoEntrada) {
        double tempo = 0;
        double corrente = 0;

        if (tempoEntrada < MenorTempo()) {

            return 99999;
        } else if (tempoEntrada > MaiorTempo()) {

            return MaiorCorrente();
            
        }

        for (int m = 0; m < col; m++) {                      //Row[0] = corrente
            for (int k = 0; k < lin; k++) {                   //row[1] = tempo
                double[] line = new double[matrizCurvas.length];
                line[k] = matrizCurvas[k][m];
                corrente = matrizCurvas[k][0];
                tempo = matrizCurvas[k][1];

                if (tempo == tempoEntrada) {

                    return corrente;
                    

                } else if (tempo < tempoEntrada) {
                    double corrente2, tempo2;
                    if (k == 0) {
                        corrente2 = matrizCurvas[1][0]; //corrente Maior 
                        tempo2 = matrizCurvas[1][1]; //tempo Menor
                    } else {
                        corrente2 = matrizCurvas[k - 1][0]; //corrente Maior 
                        tempo2 = matrizCurvas[k - 1][1]; //tempo Menor
                    }
                    double a1 = ((tempo2 - tempo) / (corrente2 - corrente));
                    double b1 = (tempo - (((tempo2 - tempo) / (corrente2 - corrente)) * corrente));

                    corrente = (tempoEntrada - b1) / a1;

                    break;
                }

            }
        }

        return corrente;
        

    }

    /**
     *
     * @param correnteEntrada
     * @return
     */
    public double TempoDaCorrente(double correnteEntrada) {

        double tempo = 0;
        double corrente = 0;

        if (correnteEntrada < MenorCorrente()) {
            
            return 99999;
        } else if (correnteEntrada > MaiorCorrente()) {
            
            return MaiorTempo();
        }

        for (int m = 0; m < col; m++) {                      //Row[0] = corrente
            for (int k = 0; k < lin; k++) {                   //row[1] = tempo
                double[] line = new double[matrizCurvas.length];
                line[k] = matrizCurvas[k][m];
                corrente = matrizCurvas[k][0];
                tempo = matrizCurvas[k][1];

                if (corrente == correnteEntrada) {
                    
                    return tempo;

                } else if (corrente > correnteEntrada) {
                    double corrente2, tempo2;
                    if (k == 0) {
                        corrente2 = matrizCurvas[1][0]; //corrente Maior 
                        tempo2 = matrizCurvas[1][1]; //tempo Menor
                    } else {
                        corrente2 = matrizCurvas[k - 1][0]; //corrente Maior 
                        tempo2 = matrizCurvas[k - 1][1]; //tempo Menor
                    }
                    double a1 = ((tempo2 - tempo) / (corrente2 - corrente));
                    double b1 = (tempo - (((tempo2 - tempo) / (corrente2 - corrente)) * corrente));

                    tempo = ((correnteEntrada * a1) + b1);

                    break;
                }

            }
        }
        
        return tempo;

    }
    
    
}
