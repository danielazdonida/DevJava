/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elografico;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author DanielaDonida
 */
public class Criterios_Religador_Elo {

    public static void criterio_religador_elo_QUEIMAFUSIVEL( //ELO ABAIXO DO RELIGADOR
            ReleFF releff,
            Circuito circ,
            ReleFN relefn,
            ArrayList<Curva_Elo> listaCurvasMax,
            ArrayList<Curva_Elo> listaCurvasMin) {

        int contador = 0;
        double I300, Ielo, Iinrush;
        Scanner sc = new Scanner(System.in);
        String tipoDeCurvaFASE;
        String tipoDeCurvaNEUTRO;
        int numeroDeElosAbaixo = 0;
        double t1 = 0, t2 = 0, t3 = 0, t4 = 0;

        System.out.println("O religador de Fase possui a curva NI, MI ou EI?");
        tipoDeCurvaFASE = sc.nextLine();

        System.out.println("O religador de Neutro possui a curva NI, MI ou EI?");
        tipoDeCurvaNEUTRO = sc.next();

        System.out.println("Quantos elos há abaixo do elo a ser determinado?");
        numeroDeElosAbaixo = sc.nextInt();

        if (tipoDeCurvaFASE.equals("NI") && tipoDeCurvaNEUTRO.equals("NI")) {

            if (numeroDeElosAbaixo == 2) {                // ******** NUMERO DE ELOS ABAIXO == 2 *********
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
               System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 1) {           // ****** NUMERO DE ELOS ABAIXO == 1 ********

                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                           System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                           System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
               System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                           System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
               System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 0) {  // ******* NUMERO DE ELOS ABAIXO == 0 *********
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
               System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                           System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
               System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            }
        } // ----- ********************************** CURVA MI && MI ******************************** ------- //
        else if (tipoDeCurvaFASE.equals("MI") && tipoDeCurvaNEUTRO.equals("MI")) {

            if (numeroDeElosAbaixo == 2) {                // ******** NUMERO DE ELOS ABAIXO == 2 *********
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
               System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
               System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 1) {           // ****** NUMERO DE ELOS ABAIXO == 1 ********

                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                           System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
               System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
               System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                           System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
               System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 0) {  // ******* NUMERO DE ELOS ABAIXO == 0 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
               System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                           System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            }

            // ---------------- ************************** CURVAS FI && FI ****************************** ----------------------- //
        } else if (tipoDeCurvaFASE.equals("EI") && tipoDeCurvaNEUTRO.equals("EI")) {

            if (numeroDeElosAbaixo == 2) {                // ******** NUMERO DE ELOS ABAIXO == 2 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
               System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
               System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
               System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
               System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 1) {           // ****** NUMERO DE ELOS ABAIXO == 1 ********

                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
               System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
               System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 0) {  // ******* NUMERO DE ELOS ABAIXO == 0 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
               System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
               System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            }

            // ------------------- **************************** CURVAS NI && MI ********************** ------------------- //
        } else if (tipoDeCurvaFASE.equals("NI") && tipoDeCurvaNEUTRO.equals("MI")) {

            if (numeroDeElosAbaixo == 2) {                // ******** NUMERO DE ELOS ABAIXO == 2 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 1) {           // ****** NUMERO DE ELOS ABAIXO == 1 ********

                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 0) {  // ******* NUMERO DE ELOS ABAIXO == 0 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
               System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            }

            // ---------------------------- *********************** CURVAS NI && FI *************************** ------------------------- //
        } else if (tipoDeCurvaFASE.equals("NI") && tipoDeCurvaNEUTRO.equals("EI")) {

            if (numeroDeElosAbaixo == 2) {                // ******** NUMERO DE ELOS ABAIXO == 2 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                           System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
               System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 1) {           // ****** NUMERO DE ELOS ABAIXO == 1 ********

                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 0) {  // ******* NUMERO DE ELOS ABAIXO == 0 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
               System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            }

            // ------------------- ************************ CURVAS MI && NI *************************** ---------------------- //
        } else if (tipoDeCurvaFASE.equals("MI") && tipoDeCurvaNEUTRO.equals("NI")) {

            if (numeroDeElosAbaixo == 2) {                // ******** NUMERO DE ELOS ABAIXO == 2 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior utilizando os parametros fixos. Realizar"
                        + "testes para encontrar onde está o erro!");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo utilizando FT Min alterado.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum Elo fez seletividade com o religador flexibilizando t2 (FFFMAX PARA FTMAX).");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior flexibiizando t4. (FT MAX PARA FT MIN)");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo flexibiizando t2 e t4. (FT MAX PARA FT MIN)");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 1) {           // ****** NUMERO DE ELOS ABAIXO == 1 ********

                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior utilizando os parametros fixos. Realizar"
                        + "testes para encontrar onde está o erro!");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo utilizando FT Min alterado.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum Elo fez seletividade com o religador flexibilizando t2 (FFFMAX PARA FTMAX).");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior flexibiizando t4. (FT MAX PARA FT MIN)");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo flexibiizando t2 e t4. (FT MAX PARA FT MIN)");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 0) {  // ******* NUMERO DE ELOS ABAIXO == 0 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior utilizando os parametros fixos. Realizar"
                        + "testes para encontrar onde está o erro!");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo utilizando FT Min alterado.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum Elo fez seletividade com o religador flexibilizando t2 (FFFMAX PARA FTMAX).");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior flexibiizando t4. (FT MAX PARA FT MIN)");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo flexibiizando t2 e t4. (FT MAX PARA FT MIN)");
                System.exit(0);

            }

            // ---------------------- **************************** CURVAS MI && FI ************************** ----------------------- //
        } else if (tipoDeCurvaFASE.equals("MI") && tipoDeCurvaNEUTRO.equals("EI")) {

            if (numeroDeElosAbaixo == 2) {                // ******** NUMERO DE ELOS ABAIXO == 2 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior utilizando os parametros fixos. Realizar"
                        + "testes para encontrar onde está o erro!");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo utilizando FT Min alterado.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum Elo fez seletividade com o religador flexibilizando t2 (FFFMAX PARA FTMAX).");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior flexibiizando t4. (FT MAX PARA FT MIN)");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo flexibiizando t2 e t4. (FT MAX PARA FT MIN)");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 1) {           // ****** NUMERO DE ELOS ABAIXO == 1 ********

                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior utilizando os parametros fixos. Realizar"
                        + "testes para encontrar onde está o erro!");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo utilizando FT Min alterado.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum Elo fez seletividade com o religador flexibilizando t2 (FFFMAX PARA FTMAX).");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior flexibiizando t4. (FT MAX PARA FT MIN)");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo flexibiizando t2 e t4. (FT MAX PARA FT MIN)");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 0) {  // ******* NUMERO DE ELOS ABAIXO == 0 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior utilizando os parametros fixos. Realizar"
                        + "testes para encontrar onde está o erro!");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo utilizando FT Min alterado.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum Elo fez seletividade com o religador flexibilizando t2 (FFFMAX PARA FTMAX).");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior flexibiizando t4. (FT MAX PARA FT MIN)");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo flexibiizando t2 e t4. (FT MAX PARA FT MIN)");
                System.exit(0);

            }

            // ------------------- *********************** CURVAS FI && NI ********************** -------------------- //
        } else if (tipoDeCurvaFASE.equals("EI") && tipoDeCurvaNEUTRO.equals("NI")) {

            if (numeroDeElosAbaixo == 2) {                // ******** NUMERO DE ELOS ABAIXO == 2 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior utilizando os parametros fixos. Realizar"
                        + "testes para encontrar onde está o erro!");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo utilizando FT Min alterado.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum Elo fez seletividade com o religador flexibilizando t2 (FFFMAX PARA FTMAX).");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior flexibiizando t4. (FT MAX PARA FT MIN)");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo flexibiizando t2 e t4. (FT MAX PARA FT MIN)");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 1) {           // ****** NUMERO DE ELOS ABAIXO == 1 ********

                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior utilizando os parametros fixos. Realizar"
                        + "testes para encontrar onde está o erro!");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo utilizando FT Min alterado.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum Elo fez seletividade com o religador flexibilizando t2 (FFFMAX PARA FTMAX).");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior flexibiizando t4. (FT MAX PARA FT MIN)");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo flexibiizando t2 e t4. (FT MAX PARA FT MIN)");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 0) {  // ******* NUMERO DE ELOS ABAIXO == 0 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior utilizando os parametros fixos. Realizar"
                        + "testes para encontrar onde está o erro!");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo utilizando FT Min alterado.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum Elo fez seletividade com o religador flexibilizando t2 (FFFMAX PARA FTMAX).");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior flexibiizando t4. (FT MAX PARA FT MIN)");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo flexibiizando t2 e t4. (FT MAX PARA FT MIN)");
                System.exit(0);

            }

            // ---------------------- ********************* CURVAS FI && MI ***************************** ---------------------- //
        } else if (tipoDeCurvaFASE.equals("EI") && tipoDeCurvaNEUTRO.equals("MI")) {

            if (numeroDeElosAbaixo == 2) {                // ******** NUMERO DE ELOS ABAIXO == 2 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior utilizando os parametros fixos. Realizar"
                        + "testes para encontrar onde está o erro!");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo utilizando FT Min alterado.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum Elo fez seletividade com o religador flexibilizando t2 (FFFMAX PARA FTMAX).");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior flexibiizando t4. (FT MAX PARA FT MIN)");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo flexibiizando t2 e t4. (FT MAX PARA FT MIN)");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 1) {           // ****** NUMERO DE ELOS ABAIXO == 1 ********

                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior utilizando os parametros fixos. Realizar"
                        + "testes para encontrar onde está o erro!");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo utilizando FT Min alterado.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum Elo fez seletividade com o religador flexibilizando t2 (FFFMAX PARA FTMAX).");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior flexibiizando t4. (FT MAX PARA FT MIN)");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo flexibiizando t2 e t4. (FT MAX PARA FT MIN)");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 0) {  // ******* NUMERO DE ELOS ABAIXO == 0 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior utilizando os parametros fixos. Realizar"
                        + "testes para encontrar onde está o erro!");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo utilizando FT Min alterado.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum Elo fez seletividade com o religador flexibilizando t2 (FFFMAX PARA FTMAX).");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior flexibiizando t4. (FT MAX PARA FT MIN)");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        double tempoOpEloFF1 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpReleFF1 - tempoOpEloFF1;
                        t2 = tempoOpReleFF2 - tempoOpEloFF2;
                        t3 = tempoOpReleFN3 - tempoOpEloFN3;
                        t4 = tempoOpReleFN4 - tempoOpEloFN4;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println(proximoElo + "k");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum Elo fez seletividade com o religador anterior mesmo flexibiizando t2 e t4. (FT MAX PARA FT MIN)");
                System.exit(0);

            }
        }
    }

    public static void criterio_religador_elo_NI_SALVAFUSIVEL( //ELO ABAIXO DO RELÉ
            ReleFF releff,
            Circuito circ,
            ReleFN relefn,
            ArrayList<Curva_Elo> listaCurvasMax,
            ArrayList<Curva_Elo> listaCurvasMin) {

        int contador = 0;
        double I300, Ielo, Iinrush;
        Scanner sc = new Scanner(System.in);
        String tipoDeCurvaNEUTRO;
        int numeroDeElosAbaixo = 0;
        double t1 = 0, t2 = 0, t3 = 0, t4 = 0, t5 = 0, t6 = 0, t7 = 0, t8 = 0;

        System.out.println("O religador de Fase nesse caso sempre possuirá curva NI.");

        System.out.println("O religador de Neutro possui a curva NI, MI ou EI?");
        tipoDeCurvaNEUTRO = sc.next();

        System.out.println("Quantos elos há abaixo do elo a ser determinado?");
        numeroDeElosAbaixo = sc.nextInt();

        if (tipoDeCurvaNEUTRO.equals("NI")) {

            if (numeroDeElosAbaixo == 2) {                // ******** NUMERO DE ELOS ABAIXO == 2 *********
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX para FTMAX(T2) ---------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX PARA FT MIN (T4) ------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 1) {           // ****** NUMERO DE ELOS ABAIXO == 1 ********

                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                           System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            
System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 0) {  // ******* NUMERO DE ELOS ABAIXO == 0 *********
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
               System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            }

            // ------------------- **************************** CURVAS NI && MI ********************** ------------------- //
        } else if (tipoDeCurvaNEUTRO.equals("MI")) {

            if (numeroDeElosAbaixo == 2) {                // ******** NUMERO DE ELOS ABAIXO == 2 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
               System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                           System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 1) {           // ****** NUMERO DE ELOS ABAIXO == 1 ********

                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                           System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 0) {  // ******* NUMERO DE ELOS ABAIXO == 0 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            }

           
        } else if (tipoDeCurvaNEUTRO.equals("EI")) {

            if (numeroDeElosAbaixo == 2) {                // ******** NUMERO DE ELOS ABAIXO == 2 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                           System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                           System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 1) {           // ****** NUMERO DE ELOS ABAIXO == 1 ********

                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
               System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 0) {  // ******* NUMERO DE ELOS ABAIXO == 0 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                           System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                           System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_NI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);
            }

        }
    }
    
    public static void criterio_religador_elo_MI_SALVAFUSIVEL(ReleFF releff,
            Circuito circ,
            ReleFN relefn,
            ArrayList<Curva_Elo> listaCurvasMax,
            ArrayList<Curva_Elo> listaCurvasMin) {

        int contador = 0;
        double I300, Ielo, Iinrush;
        Scanner sc = new Scanner(System.in);
        String tipoDeCurvaNEUTRO;
        int numeroDeElosAbaixo = 0;
        double t1 = 0, t2 = 0, t3 = 0, t4 = 0, t5 = 0, t6 = 0, t7 = 0, t8 = 0;

        System.out.println("O religador de fase nesse caso sempre possuirá curva MI.");

        System.out.println("O religador de Neutro possui a curva NI, MI ou EI?");
        tipoDeCurvaNEUTRO = sc.next();

        System.out.println("Quantos elos há abaixo do elo a ser determinado?");
        numeroDeElosAbaixo = sc.nextInt();

        if (tipoDeCurvaNEUTRO.equals("MI")) {

            if (numeroDeElosAbaixo == 2) {                // ******** NUMERO DE ELOS ABAIXO == 2 *********
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);

                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                    + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                    + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                    + "corrente trifásica para"
                                    + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 1) {           // ****** NUMERO DE ELOS ABAIXO == 1 ********

                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                    + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                    + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                    + "corrente trifásica para"
                                    + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 0) {  // ******* NUMERO DE ELOS ABAIXO == 0 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                    + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                    + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                    + "corrente trifásica para"
                                    + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);
            }

        } else if (tipoDeCurvaNEUTRO.equals("NI")) {

            if (numeroDeElosAbaixo == 2) {                // ******** NUMERO DE ELOS ABAIXO == 2 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }
                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 1) {           // ****** NUMERO DE ELOS ABAIXO == 1 ********

                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 0) {  // ******* NUMERO DE ELOS ABAIXO == 0 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                           System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            }

        } else if (tipoDeCurvaNEUTRO.equals("EI")) {

            if (numeroDeElosAbaixo == 2) {                // ******** NUMERO DE ELOS ABAIXO == 2 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }
                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 1) {           // ****** NUMERO DE ELOS ABAIXO == 1 ********

                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                           System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 0) {  // ******* NUMERO DE ELOS ABAIXO == 0 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                           System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_MI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                           System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                   + "corrente trifásica para"
                                + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            }
        }

        // ------------------- *********************** CURVAS FI && NI ********************** -------------------- //
    }

    public static void criterio_religador_elo_EI_SALVAFUSIVEL(ReleFF releff,
            Circuito circ,
            ReleFN relefn,
            ArrayList<Curva_Elo> listaCurvasMax,
            ArrayList<Curva_Elo> listaCurvasMin) {
        int contador = 0;
        double I300, Ielo, Iinrush;
        Scanner sc = new Scanner(System.in);
        String tipoDeCurvaNEUTRO;
        int numeroDeElosAbaixo = 0;
        double t1 = 0, t2 = 0, t3 = 0, t4 = 0, t5 = 0, t6 = 0, t7 = 0, t8 = 0;

        System.out.println("O religador de Fase nesse caso sempre possui curva EI.");

        System.out.println("O religador de Neutro possui a curva NI, MI ou EI?");
        tipoDeCurvaNEUTRO = sc.next();

        System.out.println("Quantos elos há abaixo do elo a ser determinado?");
        numeroDeElosAbaixo = sc.nextInt();

        if (tipoDeCurvaNEUTRO.equals("EI")) {

            if (numeroDeElosAbaixo == 2) {                // ******** NUMERO DE ELOS ABAIXO == 2 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                    + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                    + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                    + "corrente trifásica para"
                                    + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 1) {           // ****** NUMERO DE ELOS ABAIXO == 1 ********

                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                    + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                    + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                    + "corrente trifásica para"
                                    + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 0) {  // ******* NUMERO DE ELOS ABAIXO == 0 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                    + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                    + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_EI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_EI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                    + "corrente trifásica para"
                                    + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            }
        } else if (tipoDeCurvaNEUTRO.equals("NI")) {

            if (numeroDeElosAbaixo == 2) {                // ******** NUMERO DE ELOS ABAIXO == 2 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                    + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                    + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                    + "corrente trifásica para"
                                    + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 1) {           // ****** NUMERO DE ELOS ABAIXO == 1 ********

                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                    + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                    + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                    + "corrente trifásica para"
                                    + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 0) {  // ******* NUMERO DE ELOS ABAIXO == 0 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                    + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }
                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                    + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_NI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_NI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                    + "corrente trifásica para"
                                    + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }
                    }
                } while (contador != 5);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            }

            // ---------------------- ********************* CURVAS FI && MI ***************************** ---------------------- //
        } else if (tipoDeCurvaNEUTRO.equals("MI")) {

            if (numeroDeElosAbaixo == 2) {                // ******** NUMERO DE ELOS ABAIXO == 2 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                    + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                    + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                    + "corrente trifásica para"
                                    + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 3);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 1) {           // ****** NUMERO DE ELOS ABAIXO == 1 ********

                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                    + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }
                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                    + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                    + "corrente trifásica para"
                                    + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 4);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } else if (numeroDeElosAbaixo == 0) {  // ******* NUMERO DE ELOS ABAIXO == 0 *********
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getAlcCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé utilizando os critérios segundo a norma, a partir de agora "
                        + "todos os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                //-------------------------------- TESTE 1 FT MIN --------------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                    + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior apenas "
                        + "com a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, a partir de agora será utilizada a corrente fase-terra máxima "
                        + "de seletividade ao invés de utilizar a corrente trifásica. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // -------------------------------- TESTE 2 FFFMAX (T2) ---------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmax());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                    + " a corrente fase-terra máxima de seletividade: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima. "
                        + "A corrente fase-terra mínima continua sendo a de seletividade.");

                // --------------------------------- TESTE 3 FT MAX (T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFFmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFFmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para"
                                    + " a corrente fase-terra mínima: " + proximoElo + "K.");
                            System.exit(0);
                        }

                    }
                } while (contador != 5);
                System.out.println("Nenhum elo fez seletividade com o relé anterior, mesmo a corrente fase-terra máxima"
                        + " tendo sido flexibilizada para fase-terra mínima. "
                        + "Para os próximos testes, será flexibilizada a corrente fase-terra máxima para a corrente fase-terra mínima e "
                        + "também será flexibilizada a corrente trifásica para a corrente fase-terra máxima, simultaneamente."
                        + " A corrente fase-terra mínima continua sendo a de seletividade.");

                // ------------------------ TESTE 4 (T2 E T4) ------------------------------
                contador = 0;
                do {

                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    Ielo = curvaMax.elo.ielo;
                    Iinrush = curvaMin.CorrenteDoTempo(0.1);
                    contador++;
                    //Testes para saber quais elos são possíveis 
                    if (circ.getSelCorrenteFTmin() > I300
                            && Ielo > circ.getiMaxCirc()
                            && Iinrush > circ.getInRushCirc()) {
                        double tempoOpReleFF1 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF2 = releff.topReleF_EI_rapida(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN3 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN4 = relefn.topReleN_MI_rapida(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFF5 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFFmin());
                        double tempoOpReleFF6 = releff.topReleF_EI_lenta(circ, circ.getSelCorrenteFTmax());
                        double tempoOpReleFN7 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());
                        double tempoOpReleFN8 = relefn.topReleN_MI_lenta(circ, circ.getSelCorrenteFTmin());

                        //CURVA RÁPIDA
                        double tempoOpEloFF1 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF2 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN3 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN4 = curvaMin.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t1 = tempoOpEloFF1 - tempoOpReleFF1;
                        t2 = tempoOpEloFF2 - tempoOpReleFF2;
                        t3 = tempoOpEloFN3 - tempoOpReleFN3;
                        t4 = tempoOpEloFN4 - tempoOpReleFN4;

                        //CURVA LENTA
                        double tempoOpEloFF5 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFFmin());
                        double tempoOpEloFF6 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmax());
                        double tempoOpEloFN7 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());
                        double tempoOpEloFN8 = curvaMax.TempoDaCorrente(circ.getSelCorrenteFTmin());

                        t5 = tempoOpReleFF5 - tempoOpEloFF5;
                        t6 = tempoOpReleFF6 - tempoOpEloFF6;
                        t7 = tempoOpReleFN7 - tempoOpEloFN7;
                        t8 = tempoOpReleFN8 - tempoOpEloFN8;

                        if (t1 >= circ.getCTI() && t2 >= circ.getCTI() && t3 >= circ.getCTI() && t4 >= circ.getCTI()
                                && t5 >= circ.getCTI() && t6 >= circ.getCTI() && t7 >= circ.getCTI() && t8 >= circ.getCTI()) {
                            int proximoElo = curvaMax.elo.ielo;
                            System.out.println("Elo escolhido flexibilizando a corrente fase-terra máxima para a fase-terra mínima e a"
                                    + "corrente trifásica para"
                                    + " a corrente fase-terra máxima: " + proximoElo + "K.");
                            System.exit(0);
                        }
                    }
                } while (contador != 5);
                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            }
        }
    }

}
