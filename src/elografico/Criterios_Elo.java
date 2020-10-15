package elografico;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author DanielaDonida
 */
public class Criterios_Elo {

    /**
     *
     * @param listaCurvasMax
     * @param circ
     * @param listaCurvasMin
     */
    public static void criterio_elo(
            ArrayList<Curva_Elo> listaCurvasMax,
            Circuito circ,
            ArrayList<Curva_Elo> listaCurvasMin) {
        int contador = 0, numeroDeElosAbaixo = 0;
        double I300, Ielo, Iinrush, IinrushMax, IFTmin, IcargaMax;
        Scanner sc = new Scanner(System.in);

        System.out.println("Quantos elos há abaixo do primeiro elo da rede? ");
        numeroDeElosAbaixo = Integer.parseInt(sc.nextLine());

        if (numeroDeElosAbaixo == 3) {
            do {
                Curva_Elo curvaMax = listaCurvasMax.get(contador);
                Curva_Elo curvaMin = listaCurvasMin.get(contador);
                I300 = curvaMax.CorrenteDoTempo(300);
                Ielo = curvaMax.elo.ielo;
                IinrushMax = curvaMin.CorrenteDoTempo(0.1);
                IFTmin = circ.getAlcCorrenteFTmin();
                IcargaMax = circ.getiMaxCirc();
                Iinrush = circ.getInRushCirc();

                if (IFTmin > I300 && Ielo > IcargaMax && IinrushMax > Iinrush) {

                    int proximoElo = curvaMax.elo.ielo;
                    System.out.println("Elo escolhido: " + proximoElo + "K, sem flexibilizar nenhum critério.");

                    System.exit(0);
                }
                contador++;

            } while (contador != 2);

            System.out.println("Nenhum elo foi escolhido como primeiro elo da rede utiizando os critérios segundo a norma,"
                    + "por isso, será flexibilizada a corrente fase-terra mínima!");
            
            contador = 0;
            do {
                Curva_Elo curvaMax = listaCurvasMax.get(contador);
                Curva_Elo curvaMin = listaCurvasMin.get(contador);
                I300 = curvaMax.CorrenteDoTempo(300);
                Ielo = curvaMax.elo.ielo;
                IinrushMax = curvaMin.CorrenteDoTempo(0.1);
                IFTmin = circ.getSelCorrenteFTmin();
                IcargaMax = circ.getiMaxCirc();
                Iinrush = circ.getInRushCirc();

                if (IFTmin > I300 && Ielo > IcargaMax && IinrushMax > Iinrush) {

                    int proximoElo = curvaMax.elo.ielo;
                    System.out.println("Elo escolhido: " + proximoElo + "K, flexibilizando a corrente fase-terra mínima.");

                    System.exit(0);
                }
                contador++;

            } while (contador != 2);

            System.out.println("Nenhum elo foi escolhido mesmo após a flexibilização da corrente fase-terra mínima.");
            System.exit(0);

        }

        // -------------- NUMERO DE ELOS ABAIXO = 2 ----------------------
        if (numeroDeElosAbaixo == 2) {
            do {
                Curva_Elo curvaMax = listaCurvasMax.get(contador);
                Curva_Elo curvaMin = listaCurvasMin.get(contador);
                I300 = curvaMax.CorrenteDoTempo(300);
                Ielo = curvaMax.elo.ielo;
                IinrushMax = curvaMin.CorrenteDoTempo(0.1);
                IFTmin = circ.getAlcCorrenteFTmin();
                IcargaMax = circ.getiMaxCirc();
                Iinrush = circ.getInRushCirc();

                if (IFTmin > I300 && Ielo > IcargaMax && IinrushMax > Iinrush) {

                    int proximoElo = curvaMax.elo.ielo;
                    System.out.println("Elo escolhido: " + proximoElo + "K, sem flexibilizar nenhum critério.");

                    System.exit(0);
                }
                contador++;

            } while (contador != 3);

            System.out.println("Nenhum elo foi escolhido como primeiro elo da rede utiizando os critérios segundo a norma,"
                    + " por isso, será flexibilizada a corrente fase-terra mínima!");
            
            contador = 0;
            do {
                Curva_Elo curvaMax = listaCurvasMax.get(contador);
                Curva_Elo curvaMin = listaCurvasMin.get(contador);
                I300 = curvaMax.CorrenteDoTempo(300);
                Ielo = curvaMax.elo.ielo;
                IinrushMax = curvaMin.CorrenteDoTempo(0.1);
                IFTmin = circ.getSelCorrenteFTmin();
                IcargaMax = circ.getiMaxCirc();
                Iinrush = circ.getInRushCirc();

                if (IFTmin > I300 && Ielo > IcargaMax && IinrushMax > Iinrush) {

                    int proximoElo = curvaMax.elo.ielo;
                    System.out.println("Elo escolhido: " + proximoElo + "K, flexibilizando a corrente fase-terra mínima.");

                    System.exit(0);
                }
                contador++;

            } while (contador != 3);

            System.out.println("Nenhum elo foi escolhido mesmo após a flexibilização da corrente fase-terra mínima.");
            System.exit(0);

        }

        // ----------------- NUMERO DE ELOS ABAIXO = 1 --------------------
        if (numeroDeElosAbaixo == 1) {
            do {
                Curva_Elo curvaMax = listaCurvasMax.get(contador);
                Curva_Elo curvaMin = listaCurvasMin.get(contador);
                I300 = curvaMax.CorrenteDoTempo(300);
                Ielo = curvaMax.elo.ielo;
                IinrushMax = curvaMin.CorrenteDoTempo(0.1);
                IFTmin = circ.getAlcCorrenteFTmin();
                IcargaMax = circ.getiMaxCirc();
                Iinrush = circ.getInRushCirc();

                if (IFTmin > I300 && Ielo > IcargaMax && IinrushMax > Iinrush) {

                    int proximoElo = curvaMax.elo.ielo;
                    System.out.println("Elo escolhido: " + proximoElo + "K, sem flexibilizar nenhum critério.");

                    System.exit(0);
                }
                contador++;

            } while (contador != 4);

            System.out.println("Nenhum elo foi escolhido como primeiro elo da rede utiizando os critérios segundo a norma,"
                    + "por isso, será flexibilizada a corrente fase-terra mínima!");
            
            contador = 0;
            do {
                Curva_Elo curvaMax = listaCurvasMax.get(contador);
                Curva_Elo curvaMin = listaCurvasMin.get(contador);
                I300 = curvaMax.CorrenteDoTempo(300);
                Ielo = curvaMax.elo.ielo;
                IinrushMax = curvaMin.CorrenteDoTempo(0.1);
                IFTmin = circ.getSelCorrenteFTmin();
                IcargaMax = circ.getiMaxCirc();
                Iinrush = circ.getInRushCirc();

                if (IFTmin > I300 && Ielo > IcargaMax && IinrushMax > Iinrush) {

                    int proximoElo = curvaMax.elo.ielo;
                    System.out.println("Elo escolhido: " + proximoElo + "K, flexibilizando a corrente fase-terra mínima.");

                    System.exit(0);
                }
                contador++;

            } while (contador != 4);

            System.out.println("Nenhum elo foi escolhido mesmo após a flexibilização da corrente fase-terra mínima.");
            System.exit(0);

        }
        // -------------- SE NÃO HOUVER NENHUM ELO ABAIXO ---------------
        if (numeroDeElosAbaixo == 0) {
            do {
                Curva_Elo curvaMax = listaCurvasMax.get(contador);
                Curva_Elo curvaMin = listaCurvasMin.get(contador);
                I300 = curvaMax.CorrenteDoTempo(300);
                Ielo = curvaMax.elo.ielo;
                IinrushMax = curvaMin.CorrenteDoTempo(0.1);
                IFTmin = circ.getAlcCorrenteFTmin();
                IcargaMax = circ.getiMaxCirc();
                Iinrush = circ.getInRushCirc();

                if (IFTmin > I300 && Ielo > IcargaMax && IinrushMax > Iinrush) {

                    int proximoElo = curvaMax.elo.ielo;
                    System.out.println("Elo escolhido: " + proximoElo + "K, sem flexibilizar nenhum critério.");

                    System.exit(0);
                }
                contador++;

            } while (contador != 5);

            System.out.println("Nenhum elo foi escolhido como primeiro elo da rede utiizando os critérios segundo a norma,"
                    + "por isso, será flexibilizada a corrente fase-terra mínima!");
            
            contador = 0;
            do {
                Curva_Elo curvaMax = listaCurvasMax.get(contador);
                Curva_Elo curvaMin = listaCurvasMin.get(contador);
                I300 = curvaMax.CorrenteDoTempo(300);
                Ielo = curvaMax.elo.ielo;
                IinrushMax = curvaMin.CorrenteDoTempo(0.1);
                IFTmin = circ.getSelCorrenteFTmin();
                IcargaMax = circ.getiMaxCirc();
                Iinrush = circ.getInRushCirc();

                if (IFTmin > I300 && Ielo > IcargaMax && IinrushMax > Iinrush) {

                    int proximoElo = curvaMax.elo.ielo;
                    System.out.println("Elo escolhido: " + proximoElo + "K, flexibilizando a corrente fase-terra mínima.");

                    System.exit(0);
                }
                contador++;

            } while (contador != 5);

            System.out.println("Nenhum elo foi escolhido mesmo após a flexibilização da corrente fase-terra mínima.");
            System.exit(0);

        }
    }

    /**
     *
     * @param listaCurvasMax
     * @param curvaConhecida
     * @param circ
     * @param listaCurvasMin
     */
    public static void criterio_elo_elo(
            ArrayList<Curva_Elo> listaCurvasMax,
            ArrayList<Curva_Elo> curvaConhecida,
            Circuito circ,
            ArrayList<Curva_Elo> listaCurvasMin) {
        int contador = 0, proximoElo;
        double fatorMult = 0.75;
        double I300, Ielo, Tprotetor, Tprotegido, Iinrush, IinrushMax, IFTmin, IcargaMax, iFTminSel;
        Curva_Elo conhecida = curvaConhecida.get(0);
        int numeroDeElosAbaixo = 0;
        Scanner sc = new Scanner(System.in);

        // -------------------------------- CURVA CONHECIDA 65K ---------------------------------
        if (conhecida.elo.ielo == 65) {

            System.out.println("Quantos elos há abaixo do elo a ser determinado? ");
            numeroDeElosAbaixo = Integer.parseInt(sc.nextLine());

            if (numeroDeElosAbaixo == 3) { //NUMERO DE ELOS ABAIXO = 3
                contador = contador + 1;

                // ***** PRIMEIRO TESTE: CORRENTE FTmin de ALCANCE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getiMaxCC());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getiMaxCC());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    IFTmin = circ.getAlcCorrenteFTmin();
                    IcargaMax = circ.getiMaxCirc();
                    Iinrush = circ.getInRushCirc();

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");

                        System.exit(0);
                    }

                    contador++;

                } while (contador != 2);

                System.out.println("Nenhum elo fez seletividade com o elo anterior utilizando os critérios segundo a norma, a partir de agora "
                        + "os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                contador = 1;

                // ***** SEGUNDO TESTE: CORRENTE FTmin de SELETIVIDADE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getiMaxCC());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getiMaxCC());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    iFTminSel = circ.getSelCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && iFTminSel > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 2);

                System.out.println("Nenhum elo fez seletividade com o elo anterior, mesmo "
                        + "que a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, agora a seletividade será flexiblizada, sendo utilizada a corrente fase-terra máxima "
                        + "no local do elo a ser determinado, "
                        + "ao invés de utilizar a corrente trifásica. A corrente fase-terra mínima volta a ser a de Alcance.");

                contador = 1;

                // ***** TERCEIRO TESTE: CORRENTE trifásica de ALCANCE E FTMAX PARA SELETIVIDADE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    IFTmin = circ.getAlcCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima no local do dispositivo: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 2);

                System.out.println("Nenhum elo fez seletividade com o elo anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra mínima e a corrente trifásica, simultaneamente.");

                contador = 1;

                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    IFTmin = circ.getSelCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente trifásica "
                                + "e a corrente fase-terra mínima: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 2);

                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);
                //********************************************************************************************//
            } else if (numeroDeElosAbaixo == 2) {//NUMERO DE ELOS ABAIXO = 2
                contador = contador + 1;

                // ***** PRIMEIRO TESTE: CORRENTE FTmin de ALCANCE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getiMaxCC());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getiMaxCC());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    IFTmin = circ.getAlcCorrenteFTmin();
                    IcargaMax = circ.getiMaxCirc();
                    Iinrush = circ.getInRushCirc();

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");

                        System.exit(0);
                    }

                    contador++;

                } while (contador != 3);

                System.out.println("Nenhum elo fez seletividade com o elo anterior utilizando os critérios segundo a norma, a partir de agora "
                        + "os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                contador = 1;

                // ***** SEGUNDO TESTE: CORRENTE FTmin de SELETIVIDADE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getiMaxCC());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getiMaxCC());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    iFTminSel = circ.getSelCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && iFTminSel > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");

                        System.exit(0);
                    }

                    contador++;

                } while (contador != 3);

                System.out.println("Nenhum elo fez seletividade com o elo anterior, mesmo "
                        + "que a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, agora a seletividade será flexiblizada, sendo utilizada a corrente fase-terra máxima "
                        + "no local do elo a ser determinado, "
                        + "ao invés de utilizar a corrente trifásica. A corrente fase-terra mínima volta a ser a de alcance.");

                contador = 1;

                // ***** TERCEIRO TESTE: CORRENTE FTmin de ALCANCE E FTMAX PARA SELETIVIDADE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    IFTmin = circ.getAlcCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima no local do dispositivo: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 3);

                System.out.println("Nenhum elo fez seletividade com o elo anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra mínima e a corrente trifásica, simultaneamente.");

                contador = 1;

                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    IFTmin = circ.getSelCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente trifásica"
                                + " e a corrente fase-terra mínima: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 3);

                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);
                //********************************************************************************************//
            } else if (numeroDeElosAbaixo == 1) {//NUMERO DE ELOS ABAIXO = 1
                contador = contador + 1;

                // ***** PRIMEIRO TESTE: CORRENTE FTmin de ALCANCE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getiMaxCC());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getiMaxCC());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    IFTmin = circ.getAlcCorrenteFTmin();
                    IcargaMax = circ.getiMaxCirc();
                    Iinrush = circ.getInRushCirc();

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 4);

                System.out.println("Nenhum elo fez seletividade com o elo anterior utilizando os critérios segundo a norma, a partir de agora "
                        + "os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                contador = 1;

                // ***** SEGUNDO TESTE: CORRENTE FTmin de SELETIVIDADE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getiMaxCC());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getiMaxCC());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    iFTminSel = circ.getSelCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && iFTminSel > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");

                        System.exit(0);
                    }

                    contador++;

                } while (contador != 4);

                System.out.println("Nenhum elo fez seletividade com o elo anterior, mesmo "
                        + "que a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, agora a seletividade será flexiblizada, sendo utilizada a corrente fase-terra máxima "
                        + "no local do elo a ser determinado, "
                        + "ao invés de utilizar a corrente trifásica. A corrente fase-terra mínima volta a ser a de Alcance.");

                contador = 1;

                // ***** TERCEIRO TESTE: CORRENTE FTmin de ALCANCE E FTMAX PARA SELETIVIDADE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    IFTmin = circ.getAlcCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima no local do dispositivo: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 4);

                System.out.println("Nenhum elo fez seletividade com o elo anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra mínima e a corrente trifásica, simultaneamente.");

                contador = 1;

                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    IFTmin = circ.getSelCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente trifásica"
                                + " e a corrente fase-terra mínima: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 4);

                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

            } //*****************************************************************************************************//
            else if (numeroDeElosAbaixo == 0) {  //NUMERO DE ELOS ABAIXO = 0
                contador = contador + 1;

                // ***** PRIMEIRO TESTE: CORRENTE FTmin de ALCANCE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getiMaxCC());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getiMaxCC());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    IFTmin = circ.getAlcCorrenteFTmin();
                    IcargaMax = circ.getiMaxCirc();
                    Iinrush = circ.getInRushCirc();

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 5);

                System.out.println("Nenhum elo fez seletividade com o elo anterior utilizando os critérios segundo a norma, a partir de agora "
                        + "os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                contador = 1;

                // ***** SEGUNDO TESTE: CORRENTE FTmin de SELETIVIDADE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getiMaxCC());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getiMaxCC());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    iFTminSel = circ.getSelCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && iFTminSel > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");

                        System.exit(0);
                    }

                    contador++;

                } while (contador != 5);

                System.out.println("Nenhum elo fez seletividade com o elo anterior, mesmo "
                        + "que a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, agora a seletividade será flexiblizada, sendo utilizada a corrente fase-terra máxima "
                        + "no local do elo a ser determinado, "
                        + "ao invés de utilizar a corrente trifásica. A corrente fase-terra mínima volta a ser a de Alcance.");

                contador = 1;

                // ***** TERCEIRO TESTE: CORRENTE FTmin de ALCANCE E FTMAX PARA SELETIVIDADE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    IFTmin = circ.getAlcCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima no local do dispositivo: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 5);

                System.out.println("Nenhum elo fez seletividade com o elo anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra mínima e a corrente trifásica, simultaneamente.");

                contador = 1;

                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    IFTmin = circ.getSelCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente trifásica"
                                + " e a corrente fase-terra mínima: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 5);

                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);
                //********************************************************************************************//
            }

        } // ----------------------------- CURVA CONHECIDA 40K ----------------------------------- 
        else if (conhecida.elo.ielo == 40) {

            System.out.println("Quantos elos há abaixo do elo a ser determinado? ");
            numeroDeElosAbaixo = Integer.parseInt(sc.nextLine());

            if (numeroDeElosAbaixo == 3) {
                System.out.println("Como o elo acima é o de 40K, não é possível determinar um elo que permita"
                        + " seletividade com mais de 4 elos em sequência. {25K, 15K, 10K}");

            } //**********************************************************************************//
            else if (numeroDeElosAbaixo == 2) {   //NUMERO DE ELOS ABAIXO = 2
                contador = contador + 2;

                // ***** PRIMEIRO TESTE: CORRENTE FTmin de ALCANCE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getiMaxCC());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getiMaxCC());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    IFTmin = circ.getAlcCorrenteFTmin();
                    IcargaMax = circ.getiMaxCirc();
                    Iinrush = circ.getInRushCirc();

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 3);

                System.out.println("Nenhum elo fez seletividade com o elo anterior utilizando os critérios segundo a norma, a partir de agora "
                        + "os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                contador = 2;

                // ***** SEGUNDO TESTE: CORRENTE FTmin de SELETIVIDADE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getiMaxCC());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getiMaxCC());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    iFTminSel = circ.getSelCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && iFTminSel > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 3);

                System.out.println("Nenhum elo fez seletividade com o elo anterior, mesmo "
                        + "que a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, agora a seletividade será flexiblizada, sendo utilizada a corrente fase-terra máxima "
                        + "no local do elo a ser determinado, "
                        + "ao invés de utilizar a corrente trifásica. A corrente fase-terra mínima volta a ser a de Alcance.");

                contador = 2;

                // ***** TERCEIRO TESTE: CORRENTE FTmin de ALCANCE E FTMAX PARA SELETIVIDADE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    IFTmin = circ.getAlcCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima no local do dispositivo: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 3);

                System.out.println("Nenhum elo fez seletividade com o elo anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra mínima e a corrente trifásica, simultaneamente.");

                contador = 2;

                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    IFTmin = circ.getSelCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente trifásica"
                                + " e a corrente fase-terra mínima: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 3);

                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

                //************************************************************************************//
            } else if (numeroDeElosAbaixo == 1) {   //NUMERO DE ELOS ABAIXO = 1
                contador = contador + 2;

                // ***** PRIMEIRO TESTE: CORRENTE FTmin de ALCANCE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getiMaxCC());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getiMaxCC());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    IFTmin = circ.getAlcCorrenteFTmin();
                    IcargaMax = circ.getiMaxCirc();
                    Iinrush = circ.getInRushCirc();

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 4);

                System.out.println("Nenhum elo fez seletividade com o elo anterior utilizando os critérios segundo a norma, a partir de agora "
                        + "os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                contador = 2;

                // ***** SEGUNDO TESTE: CORRENTE FTmin de SELETIVIDADE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getiMaxCC());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getiMaxCC());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    iFTminSel = circ.getSelCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && iFTminSel > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 4);

                System.out.println("Nenhum elo fez seletividade com o elo anterior, mesmo "
                        + "que a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, agora a seletividade será flexiblizada, sendo utilizada a corrente fase-terra máxima "
                        + "no local do elo a ser determinado, "
                        + "ao invés de utilizar a corrente trifásica. A corrente fase-terra mínima volta a ser a de Alcance.");

                contador = 2;

                // ***** TERCEIRO TESTE: CORRENTE FTmin de ALCANCE E FTMAX PARA SELETIVIDADE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    IFTmin = circ.getAlcCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima no local do dispositivo: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 4);

                System.out.println("Nenhum elo fez seletividade com o elo anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra mínima e a corrente trifásica, simultaneamente.");

                contador = 2;

                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    IFTmin = circ.getSelCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente trifásica"
                                + " e a corrente fase-terra mínima: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 4);

                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

                //***************************************************************************************//
            } else if (numeroDeElosAbaixo == 0) {   //NUMERO DE ELOS ABAIXO = 0
                contador = contador + 2;

                // ***** PRIMEIRO TESTE: CORRENTE FTmin de ALCANCE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getiMaxCC());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getiMaxCC());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    IFTmin = circ.getAlcCorrenteFTmin();
                    IcargaMax = circ.getiMaxCirc();
                    Iinrush = circ.getInRushCirc();

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 5);

                System.out.println("Nenhum elo fez seletividade com o elo anterior utilizando os critérios segundo a norma, a partir de agora "
                        + "os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                contador = 2;

                // ***** SEGUNDO TESTE: CORRENTE FTmin de SELETIVIDADE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getiMaxCC());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getiMaxCC());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    iFTminSel = circ.getSelCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && iFTminSel > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 5);

                System.out.println("Nenhum elo fez seletividade com o elo anterior, mesmo "
                        + "que a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, agora a seletividade será flexiblizada, sendo utilizada a corrente fase-terra máxima "
                        + "no local do elo a ser determinado, "
                        + "ao invés de utilizar a corrente trifásica. A corrente fase-terra mínima volta a ser a de Alcance.");

                contador = 2;

                // ***** TERCEIRO TESTE: CORRENTE FTmin de ALCANCE E FTMAX PARA SELETIVIDADE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    IFTmin = circ.getAlcCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima no local do dispositivo: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 5);

                System.out.println("Nenhum elo fez seletividade com o elo anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra mínima e a corrente trifásica, simultaneamente.");

                contador = 2;

                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    IFTmin = circ.getSelCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente trifásica"
                                + " e a corrente fase-terra mínima: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 5);

                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);
                // ------------------------------ CURVA CONHECIDA 25K ------------------------------

            }
        } else if (conhecida.elo.ielo == 25) {

            System.out.println("Quantos elos há abaixo do elo a ser determinado? ");
            numeroDeElosAbaixo = Integer.parseInt(sc.nextLine());

            if (numeroDeElosAbaixo == 3) { //NUMERO DE ELOS ABAIXO = 3

                System.out.println("Como o elo acima é o de 25K, não é possível determinar um elo que permita"
                        + " seletividade com mais de 4 elos em sequência. {15K, 10K}");

                //******************************************************************************************************//  
            } else if (numeroDeElosAbaixo == 2) { //NUMERO DE ELOS ABAIXO = 2

                System.out.println("Como o elo acima é o de 25K, não é possível determinar um elo que permita"
                        + " seletividade com mais de 3 elos em sequência. {15K, 10K}");

                //*******************************************************************************************************//
            } else if (numeroDeElosAbaixo == 1) {   //NUMERO DE ELOS ABAIXO = 1
                contador = contador + 3;

                // ***** PRIMEIRO TESTE: CORRENTE FTmin de ALCANCE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getiMaxCC());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getiMaxCC());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    IFTmin = circ.getAlcCorrenteFTmin();
                    IcargaMax = circ.getiMaxCirc();
                    Iinrush = circ.getInRushCirc();

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 4);

                System.out.println("Nenhum elo fez seletividade com o elo anterior utilizando os critérios segundo a norma, a partir de agora "
                        + "os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                contador = 3;

                // ***** SEGUNDO TESTE: CORRENTE FTmin de SELETIVIDADE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getiMaxCC());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getiMaxCC());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    iFTminSel = circ.getSelCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && iFTminSel > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 4);

                System.out.println("Nenhum elo fez seletividade com o elo anterior, mesmo "
                        + "que a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, agora a seletividade será flexiblizada, sendo utilizada a corrente fase-terra máxima "
                        + "no local do elo a ser determinado, "
                        + "ao invés de utilizar a corrente trifásica. A corrente fase-terra mínima volta a ser a de Alcance.");

                contador = 3;

                // ***** TERCEIRO TESTE: CORRENTE FTmin de ALCANCE E FTMAX PARA SELETIVIDADE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    IFTmin = circ.getAlcCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima no local do dispositivo: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 4);

                System.out.println("Nenhum elo fez seletividade com o elo anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra mínima e a corrente trifásica, simultaneamente.");

                contador = 3;

                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    IFTmin = circ.getSelCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente trifásica"
                                + " e a corrente fase-terra mínima: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 4);

                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

                //************************************************************************************************************//
            } else if (numeroDeElosAbaixo == 0) {   //NUMERO DE ELOS ABAIXO = 0
                contador = contador + 3;

                // ***** PRIMEIRO TESTE: CORRENTE FTmin de ALCANCE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getiMaxCC());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getiMaxCC());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    IFTmin = circ.getAlcCorrenteFTmin();
                    IcargaMax = circ.getiMaxCirc();
                    Iinrush = circ.getInRushCirc();

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 5);

                System.out.println("Nenhum elo fez seletividade com o elo anterior utilizando os critérios segundo a norma, a partir de agora "
                        + "os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                contador = 3;

                // ***** SEGUNDO TESTE: CORRENTE FTmin de SELETIVIDADE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getiMaxCC());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getiMaxCC());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    iFTminSel = circ.getSelCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && iFTminSel > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 5);

                System.out.println("Nenhum elo fez seletividade com o elo anterior, mesmo "
                        + "que a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, agora a seletividade será flexiblizada, sendo utilizada a corrente fase-terra máxima "
                        + "no local do elo a ser determinado, "
                        + "ao invés de utilizar a corrente trifásica. A corrente fase-terra mínima volta a ser a de Alcance.");

                contador = 3;

                // ***** TERCEIRO TESTE: CORRENTE FTmin de ALCANCE E FTMAX PARA SELETIVIDADE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    IFTmin = circ.getAlcCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima no local do dispositivo: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 5);

                System.out.println("Nenhum elo fez seletividade com o elo anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra mínima e a corrente trifásica, simultaneamente.");

                contador = 3;

                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    IFTmin = circ.getSelCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente trifásica"
                                + " e a corrente fase-terra mínima: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 5);

                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

                // ----------------------------- CURVA CONHECIDA 15K ---------------------------------
            }
        } else if (conhecida.elo.ielo == 15) {

            System.out.println("Quantos elos há abaixo do elo a ser determinado? ");
            numeroDeElosAbaixo = Integer.parseInt(sc.nextLine());

            if (numeroDeElosAbaixo == 3) {
                System.out.println("Como o elo acima é o de 15K, não é possível determinar um elo que permita"
                        + " seletividade com mais de 4 elos em sequência. {10K}");

            } //*****************************************************************************************//      
            else if (numeroDeElosAbaixo == 2) {
                System.out.println("Como o elo acima é o de 15K, não é possível determinar um elo que permita"
                        + " seletividade com mais de 3 elos em sequência. {10K}");

            } //*****************************************************************************************// 
            else if (numeroDeElosAbaixo == 1) {
                System.out.println("Como o elo acima é o de 15K, não é possível determinar um elo que permita"
                        + " seletividade com mais de 2 elos em sequência. {10K}");

            } else if (numeroDeElosAbaixo == 0) {   //NUMERO DE ELOS ABAIXO = 0
                contador = contador + 4;

                // ***** PRIMEIRO TESTE: CORRENTE FTmin de ALCANCE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador);
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getiMaxCC());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getiMaxCC());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);
                    I300 = curvaMax.CorrenteDoTempo(300);
                    IFTmin = circ.getAlcCorrenteFTmin();
                    IcargaMax = circ.getiMaxCirc();
                    Iinrush = circ.getInRushCirc();

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido sem flexibilização de critérios: " + proximoElo + "K");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 5);

                System.out.println("Nenhum elo fez seletividade com o elo anterior utilizando os critérios segundo a norma, a partir de agora "
                        + "os testes irão utilizar a corrente fase-terra mínima de seletividade.");

                contador = 4;

                // ***** SEGUNDO TESTE: CORRENTE FTmin de SELETIVIDADE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getiMaxCC());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getiMaxCC());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    iFTminSel = circ.getSelCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && iFTminSel > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente fase-terra mínima de alcance para"
                                + " a corrente fase-terra mínima de seletividade: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 5);

                System.out.println("Nenhum elo fez seletividade com o elo anterior, mesmo "
                        + "que a corrente fase-terra mínima tenha sido flexibilizada. "
                        + "Dessa forma, agora a seletividade será flexiblizada, sendo utilizada a corrente fase-terra máxima "
                        + "no local do elo a ser determinado, "
                        + "ao invés de utilizar a corrente trifásica. A corrente fase-terra mínima volta a ser a de Alcance.");

                contador = 4;

                // ***** TERCEIRO TESTE: CORRENTE FTmin de ALCANCE E FTMAX PARA SELETIVIDADE ***** //
                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    IFTmin = circ.getAlcCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente trifásica para"
                                + " a corrente fase-terra máxima no local do dispositivo: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 5);

                System.out.println("Nenhum elo fez seletividade com o elo anterior, mesmo a corrente trifásica tendo sido flexibilizada para fase-terra máxima. "
                        + " Para os próximos testes, será flexibilizada a corrente fase-terra mínima e a corrente trifásica, simultaneamente.");

                contador = 4;

                do {
                    Curva_Elo curvaMax = listaCurvasMax.get(contador);
                    Curva_Elo curvaMin = listaCurvasMin.get(contador); //foi
                    Ielo = curvaMax.elo.ielo;
                    Tprotetor = curvaMax.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    Tprotegido = conhecida.TempoDaCorrente(circ.getCorrenteFTmaxNoLocal());
                    IinrushMax = curvaMin.CorrenteDoTempo(0.1);//foi
                    I300 = curvaMax.CorrenteDoTempo(300); //foi
                    IFTmin = circ.getSelCorrenteFTmin(); //foi
                    IcargaMax = circ.getiMaxCirc();//foi
                    Iinrush = circ.getInRushCirc();//foi

                    if (Tprotetor < fatorMult * Tprotegido
                            && IFTmin > I300
                            && Ielo > IcargaMax
                            && IinrushMax > Iinrush) {

                        proximoElo = curvaMax.elo.ielo;

                        System.out.println("Elo escolhido flexibilizando a corrente trifásica"
                                + " e a corrente fase-terra mínima: " + proximoElo + "K.");
                        System.exit(0);
                    }

                    contador++;

                } while (contador != 5);

                System.out.println("Nenhum elo foi retornado como resultado mesmo após realizar todas as flexbilizações previstas!");
                System.exit(0);

                // ---------------------- CURVA CONHECIDA 10K --------------------------
            }
        } else if (conhecida.elo.ielo == 10) {

            System.out.println("Nenhum elo pode ser escolhido para estar abaixo do elo de 10K.");
            System.exit(0);

        }
    }
}
