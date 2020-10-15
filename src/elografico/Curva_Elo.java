/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elografico;

import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Curva_Elo {

    /**
     *
     * @param nomeArquivo
     * @param matriz
     * @param lin
     * @param col
     * @param elo
     */
    public Curva_Elo(String nomeArquivo, double[][] matriz, int lin, int col, Elo elo) {
        this.nomeArquivo = nomeArquivo;
        this.matrizCurvas = matriz;
        this.lin = lin;
        this.col = col;
        this.elo = elo;
    }

    String nomeArquivo;
    double[][] matrizCurvas;
    int lin;
    int col;
    Elo elo;

    /**
     *
     * @param fator
     */
    public void MultiplicaCorrente(double fator) {
        for (int k = 0; k < lin; k++) {
            for (int m = 0; m < col; m++) {
                if (m == 0) {
                    matrizCurvas[k][m] *= fator;
                }
            }
        }
    }

    /**
     *
     * @param fator
     */
    public void MultiplicaTempo(double fator) {
        for (int k = 0; k < lin; k++) {
            for (int m = 0; m < col; m++) {
                if (m == 1) {
                    matrizCurvas[k][m] *= fator;
                }
            }
        }
    }

    /**
     *
     */
    public void getMatriz() {
        for (int k = 0; k < lin; k++) {
            for (int m = 0; m < col; m++) {
                System.out.print(matrizCurvas[k][m] + " ");
            }
            System.out.println();
        }
    }

    /**
     *
     * @return
     */
    public double MenorTempo() {
      return encontraMenor(1);
    }

    /**
     *
     * @return
     */
    public double MaiorTempo() {
         return encontraMaior(1);
    }

    /**
     *
     * @return
     */
    public double MenorCorrente() {
        return encontraMenor(0);
    }

    private double encontraMenor(int coluna) {
        double menor = 99999;
        
        for (int k = 0; k < lin; k++) {
            if (matrizCurvas[k][coluna] < menor) {
                menor = matrizCurvas[k][coluna];
            }
        }
        return menor;
    }

    /**
     *
     * @return
     */
    public double MaiorCorrente() {
        return encontraMaior(0);
    }

    private double encontraMaior(int coluna) {
        double maior = 0;
        
        for (int k = 0; k < lin; k++) {
            if (matrizCurvas[k][coluna] > maior) {
                maior = matrizCurvas[k][coluna];
            }
        }
        return maior;
    }

    /**
     *
     * @param fator
     */
    public void AdicionaCorrenteFator(double fator) {

        for (int k = 0; k < lin; k++) {
            for (int m = 0; m < col; m++) {
                if (m == 0) {
                    matrizCurvas[k][m] += fator;
                }
            }
        }
    }

    /**
     *
     * @param fator
     */
    public void AdicionaTempoFator(double fator) {

        for (int k = 0; k < lin; k++) {
            for (int m = 0; m < col; m++) {
                if (m == 1) {
                    matrizCurvas[k][m] += fator;
                }
            }
        }
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
                    return corrente;
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

        //row[1] = tempo
        //Row[0] = corrente
        for (int k = 0; k < lin; k++) {
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
                return tempo;
            }
        }
        return tempo;

    }

}
