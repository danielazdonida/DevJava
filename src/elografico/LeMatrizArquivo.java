package elografico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class LeMatrizArquivo {

    double linha[] = new double[9];

    StringTokenizer leLinhaArquivo;

    public static void main(String args[]) {

        new LeMatrizArquivo();

    }

    LeMatrizArquivo() {

        Elo elo10k = new Elo(10);
        Elo elo15k = new Elo(15);
        Elo elo25k = new Elo(25);
        Elo elo40k = new Elo(40);
        Elo elo65k = new Elo(65);
          
        Curva_Elo curvaMax10k = leArquivoEImprimeInstanciasCurvaDeMax("C:\\Users\\danie\\Documents\\Bolsa\\ElosNovo\\Fus_10K_max.txt", elo10k);
        Curva_Elo curvaMax15k = leArquivoEImprimeInstanciasCurvaDeMax("C:\\Users\\danie\\Documents\\Bolsa\\ElosNovo\\Fus_15K_max.txt", elo15k);
        Curva_Elo curvaMax25k = leArquivoEImprimeInstanciasCurvaDeMax("C:\\Users\\danie\\Documents\\Bolsa\\ElosNovo\\Fus_25K_max.txt", elo25k);
        Curva_Elo curvaMax40k = leArquivoEImprimeInstanciasCurvaDeMax("C:\\Users\\danie\\Documents\\Bolsa\\ElosNovo\\Fus_40K_max.txt", elo40k);
        Curva_Elo curvaMax65k = leArquivoEImprimeInstanciasCurvaDeMax("C:\\Users\\danie\\Documents\\Bolsa\\ElosNovo\\Fus_65K_max.txt", elo65k);
       
        Curva_Elo curvaMin10k = leArquivoEImprimeInstanciasCurvaDeMin("C:\\Users\\danie\\Documents\\Bolsa\\ElosNovo\\Fus_10K_min.txt", elo10k);
        Curva_Elo curvaMin15k = leArquivoEImprimeInstanciasCurvaDeMin("C:\\Users\\danie\\Documents\\Bolsa\\ElosNovo\\Fus_15K_min.txt", elo15k);
        Curva_Elo curvaMin25k = leArquivoEImprimeInstanciasCurvaDeMin("C:\\Users\\danie\\Documents\\Bolsa\\ElosNovo\\Fus_25K_min.txt", elo25k);
        Curva_Elo curvaMin40k = leArquivoEImprimeInstanciasCurvaDeMin("C:\\Users\\danie\\Documents\\Bolsa\\ElosNovo\\Fus_40K_min.txt", elo40k);
        Curva_Elo curvaMin65k = leArquivoEImprimeInstanciasCurvaDeMin("C:\\Users\\danie\\Documents\\Bolsa\\ElosNovo\\Fus_65K_min.txt", elo65k);
           
        ArrayList listaCurvasMax = new ArrayList<Curva_Elo>();
        ArrayList listaCurvasMin = new ArrayList<Curva_Elo>();
       
        listaCurvasMax.add(curvaMax65k); //posicao 0
        listaCurvasMax.add(curvaMax40k);
        listaCurvasMax.add(curvaMax25k);
        listaCurvasMax.add(curvaMax15k);
        listaCurvasMax.add(curvaMax10k);

        listaCurvasMin.add(curvaMin65k);
        listaCurvasMin.add(curvaMin40k);
        listaCurvasMin.add(curvaMin25k);
        listaCurvasMin.add(curvaMin15k);
        listaCurvasMin.add(curvaMin10k);

        Curva_Elo conhecida = leArquivoEImprimeInstanciasCurvaConhecida("C:\\Users\\danie\\Documents\\Bolsa\\ElosNovo\\Fus_15K_min.txt", elo15k);
        ArrayList curvaConhecida = new ArrayList<Curva_Elo>();
        curvaConhecida.add(0,conhecida);
        
        Circuito circ = new Circuito();
        ReleFF releff = new ReleFF();
        ReleFN relefn = new ReleFN();
        
       //Criterios_Elo.criterio_elo(listaCurvasMax, circ, listaCurvasMin);
       //Criterios_Elo.criterio_elo_elo(listaCurvasMax, curvaConhecida, circ, listaCurvasMin);
       //Criterios_Rele_Elo.criterio_rele_elo(releff, circ, relefn, listaCurvasMax, listaCurvasMin);
       //Criterios_Rele.criterio_rele_fase(circ, releff);
       //Criterios_Rele.criterio_rele_neutro(circ, relefn);
       //Criterios_Rele_Relig.criterio_rele_religador_fase_QUEIMAFUSIVEL(releff, circ);
       //Criterios_Rele_Relig.criterio_rele_religador_neutro_QUEIMAFUSIVEL(relefn, circ);
       //Criterios_Rele_Relig.criterio_rele_religador_fase_SALVAFUSIVEL_curvaLenta(releff, circ);
       //Criterios_Rele_Relig.criterio_rele_religador_fase_SALVAFUSIVEL_curvaRapida(releff, circ);
       //Criterios_Rele_Relig.criterio_rele_religador_neutro_SALVAFUSIVEL_curvaLenta(relefn, circ);
       //Criterios_Rele_Relig.criterio_rele_religador_neutro_SALVAFUSIVEL_curvaRapida(relefn, circ);
       //NI
       //Criterios_Religador_Elo.criterio_religador_elo_QUEIMAFUSIVEL(releff, circ, relefn, listaCurvasMax, listaCurvasMin);
       Criterios_Religador_Elo.criterio_religador_elo_NI_SALVAFUSIVEL(releff, circ, relefn, listaCurvasMax, listaCurvasMin);
       //Criterios_Religador_Elo.criterio_religador_elo_MI_SALVAFUSIVEL(releff, circ, relefn, listaCurvasMax, listaCurvasMin);
       //Criterios_Religador_Elo.criterio_religador_elo_EI_SALVAFUSIVEL(releff, circ, relefn, listaCurvasMax, listaCurvasMin);
       //Criterios_Religador.criterio_religador_neutro_SALVAFUSIVEL(circ, relefn);
       //Criterios_Religador.criterio_religador_neutro_QUEIMAFUSIVEL(circ, relefn);
       //Criterios_Religador.criterio_religador_fase_QUEIMAFUSIVEL(circ, releff);
       //Criterios_Religador.criterio_religador_fase_SALVAFUSIVEL(circ, releff);
    
    }

    public static Curva_Elo leArquivoEImprimeInstanciasCurvaDeMax(String nomeArquivo, Elo elo) {    //CARREGAR O ARQUIVO

        double[][] curvaMax = null;
        int lin = 0, col = 0;
        try {
            BufferedReader arquivo = new BufferedReader(new FileReader(nomeArquivo));
            ArrayList<String> linhas = new ArrayList();

            //CARREGAR OS DADOS NUMA MATRIZ
            while (arquivo.ready()) {
                linhas.add(arquivo.readLine());
            }
            lin = linhas.size();
            col = 2;
            curvaMax = new double[lin][col];

            for (int k = 0; k < lin; k++) {
                String[] linha = linhas.get(k).split(",");

                curvaMax[k][0] = Double.parseDouble(linha[0]);
                curvaMax[k][1] = Double.parseDouble(linha[1]);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return new Curva_Elo(nomeArquivo, curvaMax, lin, col, elo);

    }

    public static CurvaConhecida_Elo leArquivoEImprimeInstanciasCurvaEscolhida(String nomeArquivo, Elo elo) {    //CARREGAR O ARQUIVO

        double[][] curvaConhecida = null;
        int lin = 0, col = 0;
        try {
            BufferedReader arquivo = new BufferedReader(new FileReader(nomeArquivo));
            ArrayList<String> linhas = new ArrayList();

            //CARREGAR OS DADOS NUMA MATRIZ
            while (arquivo.ready()) {
                linhas.add(arquivo.readLine());
            }
            lin = linhas.size();
            col = 2;
            curvaConhecida = new double[lin][col];

            for (int k = 0; k < lin; k++) {
                String[] linha = linhas.get(k).split(",");

                curvaConhecida[k][0] = Double.parseDouble(linha[0]);
                curvaConhecida[k][1] = Double.parseDouble(linha[1]);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return new CurvaConhecida_Elo(nomeArquivo, curvaConhecida, lin, col, elo);

    }

    public static Curva_Elo leArquivoEImprimeInstanciasCurvaDeMin(String nomeArquivo, Elo elo) {    //CARREGAR O ARQUIVO

        double[][] curvaMin = null;
        int lin = 0, col = 0;
        try {
            BufferedReader arquivo = new BufferedReader(new FileReader(nomeArquivo));
            ArrayList<String> linhas = new ArrayList();

            //CARREGAR OS DADOS NUMA MATRIZ
            while (arquivo.ready()) {
                linhas.add(arquivo.readLine());
            }
            lin = linhas.size();
            col = 2;
            curvaMin = new double[lin][col];

            for (int k = 0; k < lin; k++) {
                String[] linha = linhas.get(k).split(",");

                curvaMin[k][0] = Double.parseDouble(linha[0]);
                curvaMin[k][1] = Double.parseDouble(linha[1]);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Curva_Elo(nomeArquivo, curvaMin, lin, col, elo);

    }
    
    public static Curva_Elo leArquivoEImprimeInstanciasCurvaConhecida(String nomeArquivo, Elo elo) {    //CARREGAR O ARQUIVO

        double[][] curvaConhecida = null;
        int lin = 0, col = 0;
        try {
            BufferedReader arquivo = new BufferedReader(new FileReader(nomeArquivo));
            ArrayList<String> linhas = new ArrayList();

            //CARREGAR OS DADOS NUMA MATRIZ
            while (arquivo.ready()) {
                linhas.add(arquivo.readLine());
            }
            lin = linhas.size();
            col = 2;
            curvaConhecida = new double[lin][col];

            for (int k = 0; k < lin; k++) {
                String[] linha = linhas.get(k).split(",");

                curvaConhecida[k][0] = Double.parseDouble(linha[0]);
                curvaConhecida[k][1] = Double.parseDouble(linha[1]);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return new Curva_Elo(nomeArquivo, curvaConhecida, lin, col, elo);

    }
  

}
