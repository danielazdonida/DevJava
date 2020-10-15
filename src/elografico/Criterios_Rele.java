/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template ni the editor.
 */
package elografico;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author DanielaDonida
 */
public class Criterios_Rele {

    /**
     *
     * @param circ
     * @param releff
     */
    public static void criterio_rele_fase( //RELÉ PRIMEIRO DA REDE FASE
            Circuito circ,
            ReleFF releff) {

        double menorAc = 1;
        double maiorAc = 1000;
        double ACstep = 1;
        int nElemAc = (int) (((maiorAc - menorAc) / ACstep) + 1);
        double[] AC = new double[nElemAc];
        for (int i = 0; i < AC.length; i++) {
            AC[i] = menorAc + (i * ACstep);
            // System.out.println(AC[i]);
        }

        double menorAt = 0.01;
        double maiorAt = 1;
        double ATstep = 0.01;
        int nElemAt = (int) (((maiorAt - menorAt) / ATstep) + 1);
        double[] AT = new double[nElemAt];
        for (int j = 0; j < AT.length; j++) {
            AT[j] = menorAt + (j * ATstep);
            // System.out.println(AT[j]);
        }

        ArrayList lista = new ArrayList();
        double ac = 0;
        for (int k = 0; k < AC.length; k++) {
            ac = AC[k];
            if (ac < circ.getAlcCorrenteFFmin() && ac > circ.getiMaxCirc()) {     //Verificar
                lista.add(ac);
            }
        }

        int index = lista.indexOf(Collections.max(lista));
        int index1 = lista.indexOf(Collections.min(lista));

        double tempoMaxPP = 5;
        double tempoMaxPR = 10;
        double IminFF_PP = circ.getSelCorrenteFFmin(); //bifasica mínima de seletividade
        double IminFF_PR = circ.getAlcCorrenteFFmin(); //bifásica mínima de alcance
        double AT1, AT2, aux, delta1, delta2, FM, t1, t0, t2, t3;
        boolean aux1 = false;

        double maior = (double) lista.get(index);
        double menor = (double) lista.get(index1);
        ArrayList<Rele> listaFM_NI = new ArrayList();
        ArrayList<Rele> listaFM_MI = new ArrayList();
        ArrayList<Rele> listaFM_EI = new ArrayList();
        ArrayList<Rele> listaFM_Menor = new ArrayList();
        ArrayList<Rele> listaFM_Rel_escolhido = new ArrayList();
        

        // **************** C U R V A  T E M P O R I Z A D A *********************
        
        // ****** NORMAL INVERSA ******
        do {
            AT1 = tempoMaxPP / (releff.getA_NI()
                    / (Math.pow((IminFF_PP / maior), releff.getP_NI()) - 1) + releff.getB_NI());
            AT2 = tempoMaxPR / (releff.getA_NI()
                    / (Math.pow((IminFF_PR / maior), releff.getP_NI()) - 1) + releff.getB_NI());
            
             
            if(AT1 == AT2) {
                if(AT1 <= maiorAt && AT1 >= menorAt){
                    aux = AT1 / ATstep;
                        AT1 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        //System.out.println("AC: " + maior);
                        t1 = tempoMaxPR;
                        t0 = AT1 * ((releff.getA_NI()
                                / (Math.pow((IminFF_PR / maior), releff.getP_NI()) - 1)) + releff.getB_NI());
                        delta1 = t1 - t0;
                        t3 = tempoMaxPP;
                        t2 = AT1 * ((releff.getA_NI()
                                / (Math.pow((IminFF_PP / maior), releff.getP_NI()) - 1)) + releff.getB_NI());
                        delta2 = t3 - t2;
                        FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                        listaFM_NI.add(new Rele(FM, AT1, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                }else{
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }

            if (AT1 > 0 && AT2 > 0) {

                if (AT1 <= maiorAt && AT1 >= menorAt && AT2 <= maiorAt && AT2 >= menorAt) {
                    if (AT1 < AT2) {
                        aux = AT1 / ATstep;
                        AT1 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        //System.out.println("AC: " + maior);
                        t1 = tempoMaxPR;
                        t0 = AT1 * ((releff.getA_NI()
                                / (Math.pow((IminFF_PR / maior), releff.getP_NI()) - 1)) + releff.getB_NI());
                        delta1 = t1 - t0;
                        t3 = tempoMaxPP;
                        t2 = AT1 * ((releff.getA_NI()
                                / (Math.pow((IminFF_PP / maior), releff.getP_NI()) - 1)) + releff.getB_NI());
                        delta2 = t3 - t2;
                        FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                        listaFM_NI.add(new Rele(FM, AT1, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if (AT2 < AT1) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        //System.out.println("AC: " + maior);
                        t1 = tempoMaxPR;
                        t0 = AT2 * ((releff.getA_NI()
                                / (Math.pow((IminFF_PR / maior), releff.getP_NI()) - 1)) + releff.getB_NI());
                        delta1 = t1 - t0;
                        t3 = tempoMaxPP;
                        t2 = AT2 * ((releff.getA_NI()
                                / (Math.pow((IminFF_PP / maior), releff.getP_NI()) - 1)) + releff.getB_NI());
                        delta2 = t3 - t2;
                        FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                        listaFM_NI.add(new Rele(FM, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT1 <= maiorAt && AT1 >= menorAt && AT2 >= maiorAt && AT2 >= menorAt
                        || AT1 <= maiorAt && AT1 >= menorAt && AT2 <= maiorAt && AT2 <= menorAt) {
                    aux = AT1 / ATstep;
                    AT1 = Math.floor(aux) * ATstep;
                    //System.out.println("AT-Fase: " + AT1);
                    maior = (double) lista.get(index);
                    //System.out.println("AC: " + maior);
                    t1 = tempoMaxPR;
                    t0 = AT1 * ((releff.getA_NI()
                            / (Math.pow((IminFF_PR / maior), releff.getP_NI()) - 1)) + releff.getB_NI());
                    delta1 = t1 - t0;
                    t3 = tempoMaxPP;
                    t2 = AT1 * ((releff.getA_NI()
                            / (Math.pow((IminFF_PP / maior), releff.getP_NI()) - 1)) + releff.getB_NI());
                    delta2 = t3 - t2;
                    FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                    listaFM_NI.add(new Rele(FM, AT1, maior));
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                } else if ((AT1 >= maiorAt && AT1 >= menorAt && AT2 <= maiorAt && AT2 >= menorAt
                        || AT1 <= maiorAt && AT1 <= menorAt && AT2 <= maiorAt && AT2 >= menorAt)) {
                    aux = AT2 / ATstep;
                    AT2 = Math.floor(aux) * ATstep;
                    //System.out.println("AT-Fase: " + AT1);
                    maior = (double) lista.get(index);
                    //System.out.println("AC: " + maior);
                    t1 = tempoMaxPR;
                    t0 = AT2 * ((releff.getA_NI()
                            / (Math.pow((IminFF_PR / maior), releff.getP_NI()) - 1)) + releff.getB_NI());
                    delta1 = t1 - t0;
                    t3 = tempoMaxPP;
                    t2 = AT2 * ((releff.getA_NI()
                            / (Math.pow((IminFF_PP / maior), releff.getP_NI()) - 1)) + releff.getB_NI());
                    delta2 = t3 - t2;
                    FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                    listaFM_NI.add(new Rele(FM, AT2, maior));
                    index--;
                    maior = (double) lista.get(index);
                    continue;

                }
                if (AT1 >= maiorAt || AT1 <= menorAt && AT2 >= maiorAt || AT2 <= menorAt) {
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }
            if (AT2 >= 0 && AT1 <= 0) {
                if (AT2 <= maiorAt && AT2 >= menorAt) {
                    aux = AT2 / ATstep;
                    AT2 = Math.floor(aux) * ATstep;
                    //System.out.println("AT-Fase: " + AT1);
                    maior = (double) lista.get(index);
                    //System.out.println("AC: " + maior);
                    t1 = tempoMaxPR;
                    t0 = AT2 * ((releff.getA_NI()
                            / (Math.pow((IminFF_PR / maior), releff.getP_NI()) - 1)) + releff.getB_NI());
                    delta1 = t1 - t0;
                    t3 = tempoMaxPP;
                    t2 = AT2 * ((releff.getA_NI()
                            / (Math.pow((IminFF_PP / maior), releff.getP_NI()) - 1)) + releff.getB_NI());
                    delta2 = t3 - t2;
                    FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                    listaFM_NI.add(new Rele(FM, AT2, maior));
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                } else {
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }

            if (AT1 >= 0 && AT2 <= 0) {
                if (AT1 <= maiorAt && AT1 >= menorAt) {
                    aux = AT1 / ATstep;
                    AT1 = Math.floor(aux) * ATstep;
                    //System.out.println("AT-Fase: " + AT1);
                    maior = (double) lista.get(index);
                    //System.out.println("AC: " + maior);
                    t1 = tempoMaxPR;
                    t0 = AT1 * ((releff.getA_NI()
                            / (Math.pow((IminFF_PR / maior), releff.getP_NI()) - 1)) + releff.getB_NI());
                    delta1 = t1 - t0;
                    t3 = tempoMaxPP;
                    t2 = AT1 * ((releff.getA_NI()
                            / (Math.pow((IminFF_PP / maior), releff.getP_NI()) - 1)) + releff.getB_NI());
                    delta2 = t3 - t2;
                    FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                    listaFM_NI.add(new Rele(FM, AT1, maior));
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                } else {
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }
            if (AT1 <= 0 && AT2 <= 0) {
                index--;
                maior = (double) lista.get(index);
            }

        } while (maior > menor);

        System.out.println("Curva normal inversa");
        for (Rele listaFM_ni : listaFM_NI) {

            System.out.println("FM: " + listaFM_ni.FM + ", AT: " + listaFM_ni.AT + ", AC: " + listaFM_ni.AC);
        }

        // ****** MUITO INVERSA ******
        index = lista.indexOf(Collections.max(lista));
        index1 = lista.indexOf(Collections.min(lista));
        maior = (double) lista.get(index);
        menor = (double) lista.get(index1);
        do {
            AT1 = tempoMaxPP / (releff.getA_MI()
                    / (Math.pow((IminFF_PP / maior), releff.getP_MI()) - 1) + releff.getB_MI());
            AT2 = tempoMaxPR / (releff.getA_MI()
                    / (Math.pow((IminFF_PR / maior), releff.getP_MI()) - 1) + releff.getB_MI());
            
            if(AT1 == AT2) {
                if(AT1 <= maiorAt && AT1 >= menorAt){
                    aux = AT1 / ATstep;
                        AT1 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        //System.out.println("AC: " + maior);
                        t1 = tempoMaxPR;
                        t0 = AT1 * ((releff.getA_MI()
                                / (Math.pow((IminFF_PR / maior), releff.getP_MI()) - 1)) + releff.getB_MI());
                        delta1 = t1 - t0;
                        t3 = tempoMaxPP;
                        t2 = AT1 * ((releff.getA_MI()
                                / (Math.pow((IminFF_PP / maior), releff.getP_MI()) - 1)) + releff.getB_MI());
                        delta2 = t3 - t2;
                        FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                        listaFM_MI.add(new Rele(FM, AT1, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                }else{
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }
            
            if (AT1 > 0 && AT2 > 0) {

                if (AT1 <= maiorAt && AT1 >= menorAt && AT2 <= maiorAt && AT2 >= menorAt) {
                    if (AT1 < AT2) {
                        aux = AT1 / ATstep;
                        AT1 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        //System.out.println("AC: " + maior);
                        t1 = tempoMaxPR;
                        t0 = AT1 * ((releff.getA_MI()
                                / (Math.pow((IminFF_PR / maior), releff.getP_MI()) - 1)) + releff.getB_MI());
                        delta1 = t1 - t0;
                        t3 = tempoMaxPP;
                        t2 = AT1 * ((releff.getA_MI()
                                / (Math.pow((IminFF_PP / maior), releff.getP_MI()) - 1)) + releff.getB_MI());
                        delta2 = t3 - t2;
                        FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                        listaFM_MI.add(new Rele(FM, AT1, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if (AT2 < AT1) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        //System.out.println("AC: " + maior);
                        t1 = tempoMaxPR;
                        t0 = AT2 * ((releff.getA_MI()
                                / (Math.pow((IminFF_PR / maior), releff.getP_MI()) - 1)) + releff.getB_MI());
                        delta1 = t1 - t0;
                        t3 = tempoMaxPP;
                        t2 = AT2 * ((releff.getA_MI()
                                / (Math.pow((IminFF_PP / maior), releff.getP_MI()) - 1)) + releff.getB_MI());
                        delta2 = t3 - t2;
                        FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                        listaFM_MI.add(new Rele(FM, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT1 <= maiorAt && AT1 >= menorAt && AT2 >= maiorAt && AT2 >= menorAt
                        || AT1 <= maiorAt && AT1 >= menorAt && AT2 <= maiorAt && AT2 <= menorAt) {
                    aux = AT1 / ATstep;
                    AT1 = Math.floor(aux) * ATstep;
                    //System.out.println("AT-Fase: " + AT1);
                    maior = (double) lista.get(index);
                    //System.out.println("AC: " + maior);
                    t1 = tempoMaxPR;
                    t0 = AT1 * ((releff.getA_MI()
                            / (Math.pow((IminFF_PR / maior), releff.getP_MI()) - 1)) + releff.getB_MI());
                    delta1 = t1 - t0;
                    t3 = tempoMaxPP;
                    t2 = AT1 * ((releff.getA_MI()
                            / (Math.pow((IminFF_PP / maior), releff.getP_MI()) - 1)) + releff.getB_MI());
                    delta2 = t3 - t2;
                    FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                    listaFM_MI.add(new Rele(FM, AT1, maior));
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                } else if ((AT1 >= maiorAt && AT1 >= menorAt && AT2 <= maiorAt && AT2 >= menorAt
                        || AT1 <= maiorAt && AT1 <= menorAt && AT2 <= maiorAt && AT2 >= menorAt)) {
                    aux = AT2 / ATstep;
                    AT2 = Math.floor(aux) * ATstep;
                    //System.out.println("AT-Fase: " + AT1);
                    maior = (double) lista.get(index);
                    //System.out.println("AC: " + maior);
                    t1 = tempoMaxPR;
                    t0 = AT2 * ((releff.getA_MI()
                            / (Math.pow((IminFF_PR / maior), releff.getP_MI()) - 1)) + releff.getB_MI());
                    delta1 = t1 - t0;
                    t3 = tempoMaxPP;
                    t2 = AT2 * ((releff.getA_MI()
                            / (Math.pow((IminFF_PP / maior), releff.getP_MI()) - 1)) + releff.getB_MI());
                    delta2 = t3 - t2;
                    FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                    listaFM_MI.add(new Rele(FM, AT2, maior));
                    index--;
                    maior = (double) lista.get(index);
                    continue;

                }
                if (AT1 >= maiorAt || AT1 <= menorAt && AT2 >= maiorAt || AT2 <= menorAt) {
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }
            if (AT2 >= 0 && AT1 <= 0) {
                if (AT2 <= maiorAt && AT2 >= menorAt) {
                    aux = AT2 / ATstep;
                    AT2 = Math.floor(aux) * ATstep;
                    //System.out.println("AT-Fase: " + AT1);
                    maior = (double) lista.get(index);
                    //System.out.println("AC: " + maior);
                    t1 = tempoMaxPR;
                    t0 = AT2 * ((releff.getA_MI()
                            / (Math.pow((IminFF_PR / maior), releff.getP_MI()) - 1)) + releff.getB_MI());
                    delta1 = t1 - t0;
                    t3 = tempoMaxPP;
                    t2 = AT2 * ((releff.getA_MI()
                            / (Math.pow((IminFF_PP / maior), releff.getP_MI()) - 1)) + releff.getB_MI());
                    delta2 = t3 - t2;
                    FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                    listaFM_MI.add(new Rele(FM, AT2, maior));
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                } else {
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }

            if (AT1 >= 0 && AT2 <= 0) {
                if (AT1 <= maiorAt && AT1 >= menorAt) {
                    aux = AT1 / ATstep;
                    AT1 = Math.floor(aux) * ATstep;
                    //System.out.println("AT-Fase: " + AT1);
                    maior = (double) lista.get(index);
                    //System.out.println("AC: " + maior);
                    t1 = tempoMaxPR;
                    t0 = AT1 * ((releff.getA_MI()
                            / (Math.pow((IminFF_PR / maior), releff.getP_MI()) - 1)) + releff.getB_MI());
                    delta1 = t1 - t0;
                    t3 = tempoMaxPP;
                    t2 = AT1 * ((releff.getA_MI()
                            / (Math.pow((IminFF_PP / maior), releff.getP_MI()) - 1)) + releff.getB_MI());
                    delta2 = t3 - t2;
                    FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                    listaFM_MI.add(new Rele(FM, AT1, maior));
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                } else {
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }
            if (AT1 <= 0 && AT2 <= 0) {
                index--;
                maior = (double) lista.get(index);
                continue;
            }

        } while (maior > menor);

        System.out.println("");
        System.out.println("Curva muito inversa");
        for (Rele listaFM_mi : listaFM_MI) {

            System.out.println("FM: " + listaFM_mi.FM + ", AT: " + listaFM_mi.AT + ", AC: " + listaFM_mi.AC);
        }

        // ****** CURVA EXTREMAMENTE INVERSA ******
        index = lista.indexOf(Collections.max(lista));
        index1 = lista.indexOf(Collections.min(lista));
        maior = (double) lista.get(index);
        menor = (double) lista.get(index1);
        do {
            AT1 = tempoMaxPP / (releff.getA_EI()
                    / (Math.pow((IminFF_PP / maior), releff.getP_EI()) - 1) + releff.getB_EI());
            AT2 = tempoMaxPR / (releff.getA_EI()
                    / (Math.pow((IminFF_PR / maior), releff.getP_EI()) - 1) + releff.getB_EI());

             
            if(AT1 == AT2) {
                if(AT1 <= maiorAt && AT1 >= menorAt){
                    aux = AT1 / ATstep;
                        AT1 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        //System.out.println("AC: " + maior);
                        t1 = tempoMaxPR;
                        t0 = AT1 * ((releff.getA_EI()
                                / (Math.pow((IminFF_PR / maior), releff.getP_EI()) - 1)) + releff.getB_EI());
                        delta1 = t1 - t0;
                        t3 = tempoMaxPP;
                        t2 = AT1 * ((releff.getA_EI()
                                / (Math.pow((IminFF_PP / maior), releff.getP_EI()) - 1)) + releff.getB_EI());
                        delta2 = t3 - t2;
                        FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                        listaFM_EI.add(new Rele(FM, AT1, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                }else{
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }
            
            if (AT1 > 0 && AT2 > 0) {

                if (AT1 <= maiorAt && AT1 >= menorAt && AT2 <= maiorAt && AT2 >= menorAt) {
                    if (AT1 < AT2) {
                        aux = AT1 / ATstep;
                        AT1 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        //System.out.println("AC: " + maior);
                        t1 = tempoMaxPR;
                        t0 = AT1 * ((releff.getA_EI()
                                / (Math.pow((IminFF_PR / maior), releff.getP_EI()) - 1)) + releff.getB_EI());
                        delta1 = t1 - t0;
                        t3 = tempoMaxPP;
                        t2 = AT1 * ((releff.getA_EI()
                                / (Math.pow((IminFF_PP / maior), releff.getP_EI()) - 1)) + releff.getB_EI());
                        delta2 = t3 - t2;
                        FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                        listaFM_EI.add(new Rele(FM, AT1, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if (AT2 < AT1) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        //System.out.println("AC: " + maior);
                        t1 = tempoMaxPR;
                        t0 = AT2 * ((releff.getA_EI()
                                / (Math.pow((IminFF_PR / maior), releff.getP_EI()) - 1)) + releff.getB_EI());
                        delta1 = t1 - t0;
                        t3 = tempoMaxPP;
                        t2 = AT2 * ((releff.getA_EI()
                                / (Math.pow((IminFF_PP / maior), releff.getP_EI()) - 1)) + releff.getB_EI());
                        delta2 = t3 - t2;
                        FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                        listaFM_EI.add(new Rele(FM, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT1 <= maiorAt && AT1 >= menorAt && AT2 >= maiorAt && AT2 >= menorAt
                        || AT1 <= maiorAt && AT1 >= menorAt && AT2 <= maiorAt && AT2 <= menorAt) {
                    aux = AT1 / ATstep;
                    AT1 = Math.floor(aux) * ATstep;
                    //System.out.println("AT-Fase: " + AT1);
                    maior = (double) lista.get(index);
                    //System.out.println("AC: " + maior);
                    t1 = tempoMaxPR;
                    t0 = AT1 * ((releff.getA_EI()
                            / (Math.pow((IminFF_PR / maior), releff.getP_EI()) - 1)) + releff.getB_EI());
                    delta1 = t1 - t0;
                    t3 = tempoMaxPP;
                    t2 = AT1 * ((releff.getA_EI()
                            / (Math.pow((IminFF_PP / maior), releff.getP_EI()) - 1)) + releff.getB_EI());
                    delta2 = t3 - t2;
                    FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                    listaFM_EI.add(new Rele(FM, AT1, maior));
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                } else if ((AT1 >= maiorAt && AT1 >= menorAt && AT2 <= maiorAt && AT2 >= menorAt
                        || AT1 <= maiorAt && AT1 <= menorAt && AT2 <= maiorAt && AT2 >= menorAt)) {
                    aux = AT2 / ATstep;
                    AT2 = Math.floor(aux) * ATstep;
                    //System.out.println("AT-Fase: " + AT1);
                    maior = (double) lista.get(index);
                    //System.out.println("AC: " + maior);
                    t1 = tempoMaxPR;
                    t0 = AT2 * ((releff.getA_EI()
                            / (Math.pow((IminFF_PR / maior), releff.getP_EI()) - 1)) + releff.getB_EI());
                    delta1 = t1 - t0;
                    t3 = tempoMaxPP;
                    t2 = AT2 * ((releff.getA_EI()
                            / (Math.pow((IminFF_PP / maior), releff.getP_EI()) - 1)) + releff.getB_EI());
                    delta2 = t3 - t2;
                    FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                    listaFM_EI.add(new Rele(FM, AT2, maior));
                    index--;
                    maior = (double) lista.get(index);
                    continue;

                }
                if (AT1 >= maiorAt || AT1 <= menorAt && AT2 >= maiorAt || AT2 <= menorAt) {
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }
            if (AT2 >= 0 && AT1 <= 0) {
                if (AT2 <= maiorAt && AT2 >= menorAt) {
                    aux = AT2 / ATstep;
                    AT2 = Math.floor(aux) * ATstep;
                    //System.out.println("AT-Fase: " + AT1);
                    maior = (double) lista.get(index);
                    //System.out.println("AC: " + maior);
                    t1 = tempoMaxPR;
                    t0 = AT2 * ((releff.getA_EI()
                            / (Math.pow((IminFF_PR / maior), releff.getP_EI()) - 1)) + releff.getB_EI());
                    delta1 = t1 - t0;
                    t3 = tempoMaxPP;
                    t2 = AT2 * ((releff.getA_EI()
                            / (Math.pow((IminFF_PP / maior), releff.getP_EI()) - 1)) + releff.getB_EI());
                    delta2 = t3 - t2;
                    FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                    listaFM_EI.add(new Rele(FM, AT2, maior));
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                } else {
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }

            if (AT1 >= 0 && AT2 <= 0) {
                if (AT1 <= maiorAt && AT1 >= menorAt) {
                    aux = AT1 / ATstep;
                    AT1 = Math.floor(aux) * ATstep;
                    //System.out.println("AT-Fase: " + AT1);
                    maior = (double) lista.get(index);
                    //System.out.println("AC: " + maior);
                    t1 = tempoMaxPR;
                    t0 = AT1 * ((releff.getA_EI()
                            / (Math.pow((IminFF_PR / maior), releff.getP_EI()) - 1)) + releff.getB_EI());
                    delta1 = t1 - t0;
                    t3 = tempoMaxPP;
                    t2 = AT1 * ((releff.getA_EI()
                            / (Math.pow((IminFF_PP / maior), releff.getP_EI()) - 1)) + releff.getB_EI());
                    delta2 = t3 - t2;
                    FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                    listaFM_EI.add(new Rele(FM, AT1, maior));
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                } else {
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }
            if (AT1 <= 0 && AT2 <= 0) {
                index--;
                maior = (double) lista.get(index);
            }

        } while (maior > menor);

        System.out.println("");
        System.out.println("Curva extremamente inversa");
        for (Rele listaFM_ei : listaFM_EI) {

            System.out.println("FM: " + listaFM_ei.FM + ", AT: " + listaFM_ei.AT + ", AC: " + listaFM_ei.AC);

        }

        System.out.println("");

        double menor_ni = 9999999;
        double menor_ac_ni = 0;
        double menor_at_ni = 0;

        for (Rele listaFM_ni : listaFM_NI) {
            if (listaFM_ni.getFM() < menor_ni) {
                menor_ni = listaFM_ni.getFM();
                menor_ac_ni = listaFM_ni.getAC();
                menor_at_ni = listaFM_ni.getAT();
                listaFM_Menor.add(new Rele(menor_ni, menor_at_ni, menor_ac_ni));

            }
        }
        System.out.println("Menor FM da curva NI: " + menor_ni + " AT: " + menor_at_ni + " AC: " + menor_ac_ni);

        double menor_mi = 99999999;
        double menor_ac_mi = 0;
        double menor_at_mi = 0;

        for (Rele listaFM_mi : listaFM_MI) {
            if (listaFM_mi.getFM() < menor_mi) {
                menor_mi = listaFM_mi.getFM();
                menor_ac_mi = listaFM_mi.getAC();
                menor_at_mi = listaFM_mi.getAT();
                listaFM_Menor.add(new Rele(menor_mi, menor_at_mi, menor_ac_mi));

            }
        }
        System.out.println("Menor FM da curva MI: " + menor_mi + " AT: " + menor_at_mi + " AC: " + menor_ac_mi);

        double menor_ei = 99999999;
        double menor_ac_ei = 0;
        double menor_at_ei = 0;

        for (Rele listaFM_ei : listaFM_EI) {
            if (listaFM_ei.getFM() < menor_ei) {
                menor_ei = listaFM_ei.getFM();
                menor_ac_ei = listaFM_ei.getAC();
                menor_at_ei = listaFM_ei.getAT();
                listaFM_Menor.add(new Rele(menor_ei, menor_at_ei, menor_ac_ei));

            }
        }
        System.out.println("Menor FM da curva EI: " + menor_ei + " AT: " + menor_at_ei + " AC: " + menor_ac_ei);
        System.out.println("");

        double escolhido_fm = 99999999;
        double escolhido_at = 0;
        double escolhido_ac = 0;
        for (Rele listaFM_menor : listaFM_Menor) {
            if (listaFM_menor.getFM() < escolhido_fm) {
                escolhido_fm = listaFM_menor.getFM();
                escolhido_ac = listaFM_menor.getAC();
                escolhido_at = listaFM_menor.getAT();
                listaFM_Rel_escolhido.add(new Rele(escolhido_fm, escolhido_at, escolhido_ac));

            }
        }

        System.out.println("Curva temporizada de fase:");
        System.out.println("FM: " + escolhido_fm + " AT: " + escolhido_at + " AC: " + escolhido_ac);
        System.out.println("");
        
        // **************** C U R V A  I N S T A N T Â N E A *********************
        
        double ac_ins = 0;
        for (int k = 0; k < AC.length; k++) {
            ac_ins = AC[k];
            if (ac_ins > circ.getInRushCirc() && ac_ins > circ.getSelCorrenteFFmax()) {     //Verificar
                System.out.println("AC da curva instantânea de fase: "+ac_ins);
                break;
            }
        }
    }

    /**
     *
     * @param circ
     * @param relefn
     */
    public static void criterio_rele_neutro( //corrigido, fazer mais testes.
            Circuito circ,
            ReleFN relefn) {

        double menorAc = 1;
        double maiorAc = 1000;
        double ACstep = 1;
        int nElemAc = (int) (((maiorAc - menorAc) / ACstep) + 1);
        double[] AC = new double[nElemAc];
        for (int i = 0; i < AC.length; i++) {
            AC[i] = menorAc + (i * ACstep);
            // System.out.println(AC[i]);
        }

        double menorAt = 0.01;
        double maiorAt = 1;
        double ATstep = 0.01;
        int nElemAt = (int) (((maiorAt - menorAt) / ATstep) + 1);
        double[] AT = new double[nElemAt];
        for (int j = 0; j < AT.length; j++) {
            AT[j] = menorAt + (j * ATstep);
            // System.out.println(AT[j]);
        }

        ArrayList lista = new ArrayList();
        double ac = 0;
        for (int k = 0; k < AC.length; k++) {
            ac = AC[k];
            if (ac > circ.getiMaxCirc() * 0.3 && ac < circ.getAlcCorrenteFTmin()) {
                lista.add(ac);
            }
        }

        int index = lista.indexOf(Collections.max(lista));
        int index1 = lista.indexOf(Collections.min(lista));

        ArrayList<Rele> listaFM_NI = new ArrayList();
        ArrayList<Rele> listaFM_MI = new ArrayList();
        ArrayList<Rele> listaFM_EI = new ArrayList();
        ArrayList<Rele> listaFM_Menor = new ArrayList();
        ArrayList<Rele> listaFM_Rel_escolhido = new ArrayList();

        double maior = (double) lista.get(index);
        double menor = (double) lista.get(index1);

        double tempoMaxPP = 2.5;
        double tempoMaxPR = 5;
        double IminPP = circ.getSelCorrenteFTmin(); //FTmin de seletividade
        double IminPR = circ.getAlcCorrenteFTmin(); //FTmin de alcance
        double AT1, AT2, aux, delta1, delta2, FM, t1, t0, t2, t3;
        boolean aux1 = false;

        do {

            if (maior > menor) {
                AT1 = tempoMaxPP / (relefn.getA_NI()
                        / (Math.pow((IminPP / maior), relefn.getP_NI()) - 1) + relefn.getB_NI());
                AT2 = tempoMaxPR / (relefn.getA_NI()
                        / (Math.pow((IminPR / maior), relefn.getP_NI()) - 1) + relefn.getB_NI());

                 
            if(AT1 == AT2) {
                if(AT1 <= maiorAt && AT1 >= menorAt){
                    aux = AT1 / ATstep;
                        AT1 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        //System.out.println("AC: " + maior);
                        t1 = tempoMaxPR;
                        t0 = AT1 * ((relefn.getA_NI()
                                / (Math.pow((IminPR / maior), relefn.getP_NI()) - 1)) + relefn.getB_NI());
                        delta1 = t1 - t0;
                        t3 = tempoMaxPP;
                        t2 = AT1 * ((relefn.getA_NI()
                                / (Math.pow((IminPP / maior), relefn.getP_NI()) - 1)) + relefn.getB_NI());
                        delta2 = t3 - t2;
                        FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                        listaFM_NI.add(new Rele(FM, AT1, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                }else{
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }
            
                if (AT1 > 0 && AT2 > 0) {

                    if (AT1 <= maiorAt && AT1 >= menorAt && AT2 <= maiorAt && AT2 >= menorAt) {
                        if (AT1 < AT2) {
                            aux = AT1 / ATstep;
                            AT1 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            //System.out.println("AC: " + maior);
                            t1 = tempoMaxPR;
                            t0 = AT1 * ((relefn.getA_NI()
                                    / (Math.pow((IminPR / maior), relefn.getP_NI()) - 1)) + relefn.getB_NI());
                            delta1 = t1 - t0;
                            t3 = tempoMaxPP;
                            t2 = AT1 * ((relefn.getA_NI()
                                    / (Math.pow((IminPP / maior), relefn.getP_NI()) - 1)) + relefn.getB_NI());
                            delta2 = t3 - t2;
                            FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                            listaFM_NI.add(new Rele(FM, AT1, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        } else if (AT2 < AT1) {
                            aux = AT2 / ATstep;
                            AT2 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            //System.out.println("AC: " + maior);
                            t1 = tempoMaxPR;
                            t0 = AT2 * ((relefn.getA_NI()
                                    / (Math.pow((IminPR / maior), relefn.getP_NI()) - 1)) + relefn.getB_NI());
                            delta1 = t1 - t0;
                            t3 = tempoMaxPP;
                            t2 = AT2 * ((relefn.getA_NI()
                                    / (Math.pow((IminPP / maior), relefn.getP_NI()) - 1)) + relefn.getB_NI());
                            delta2 = t3 - t2;
                            FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                            listaFM_NI.add(new Rele(FM, AT2, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        }
                    }

                    if (AT1 <= maiorAt && AT1 >= menorAt && AT2 >= maiorAt && AT2 >= menorAt
                            || AT1 <= maiorAt && AT1 >= menorAt && AT2 <= maiorAt && AT2 <= menorAt) {
                        aux = AT1 / ATstep;
                        AT1 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        //System.out.println("AC: " + maior);
                        t1 = tempoMaxPR;
                        t0 = AT1 * ((relefn.getA_NI()
                                / (Math.pow((IminPR / maior), relefn.getP_NI()) - 1)) + relefn.getB_NI());
                        delta1 = t1 - t0;
                        t3 = tempoMaxPP;
                        t2 = AT1 * ((relefn.getA_NI()
                                / (Math.pow((IminPP / maior), relefn.getP_NI()) - 1)) + relefn.getB_NI());
                        delta2 = t3 - t2;
                        FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                        listaFM_NI.add(new Rele(FM, AT1, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if ((AT1 >= maiorAt && AT1 >= menorAt && AT2 <= maiorAt && AT2 >= menorAt
                            || AT1 <= maiorAt && AT1 <= menorAt && AT2 <= maiorAt && AT2 >= menorAt)) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        //System.out.println("AC: " + maior);
                        t1 = tempoMaxPR;
                        t0 = AT2 * ((relefn.getA_NI()
                                / (Math.pow((IminPR / maior), relefn.getP_NI()) - 1)) + relefn.getB_NI());
                        delta1 = t1 - t0;
                        t3 = tempoMaxPP;
                        t2 = AT2 * ((relefn.getA_NI()
                                / (Math.pow((IminPP / maior), relefn.getP_NI()) - 1)) + relefn.getB_NI());
                        delta2 = t3 - t2;
                        FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                        listaFM_NI.add(new Rele(FM, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;

                    }
                    if (AT1 >= maiorAt || AT1 <= menorAt && AT2 >= maiorAt || AT2 <= menorAt) {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT2 >= 0 && AT1 <= 0) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        //System.out.println("AC: " + maior);
                        t1 = tempoMaxPR;
                        t0 = AT2 * ((relefn.getA_NI()
                                / (Math.pow((IminPR / maior), relefn.getP_NI()) - 1)) + relefn.getB_NI());
                        delta1 = t1 - t0;
                        t3 = tempoMaxPP;
                        t2 = AT2 * ((relefn.getA_NI()
                                / (Math.pow((IminPP / maior), relefn.getP_NI()) - 1)) + relefn.getB_NI());
                        delta2 = t3 - t2;
                        FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                        listaFM_NI.add(new Rele(FM, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT1 >= 0 && AT2 <= 0) {
                    if (AT1 <= maiorAt && AT1 >= menorAt) {
                        aux = AT1 / ATstep;
                        AT1 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        //System.out.println("AC: " + maior);
                        t1 = tempoMaxPR;
                        t0 = AT1 * ((relefn.getA_NI()
                                / (Math.pow((IminPR / maior), relefn.getP_NI()) - 1)) + relefn.getB_NI());
                        delta1 = t1 - t0;
                        t3 = tempoMaxPP;
                        t2 = AT1 * ((relefn.getA_NI()
                                / (Math.pow((IminPP / maior), relefn.getP_NI()) - 1)) + relefn.getB_NI());
                        delta2 = t3 - t2;
                        FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                        listaFM_NI.add(new Rele(FM, AT1, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT1 <= 0 && AT2 <= 0) {
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }

            }

        } while (maior > menor);

        System.out.println("Curva normal inversa");
        for (Rele listaFM_ni : listaFM_NI) {

            System.out.println("FM: " + listaFM_ni.FM + ", AT: " + listaFM_ni.AT + ", AC: " + listaFM_ni.AC);
        }

        // ****** MUITO INVERSA ******
        index = lista.indexOf(Collections.max(lista));
        index1 = lista.indexOf(Collections.min(lista));
        maior = (double) lista.get(index);
        menor = (double) lista.get(index1);
        do {
            AT1 = tempoMaxPP / (relefn.getA_MI()
                    / (Math.pow((IminPP / maior), relefn.getP_MI()) - 1) + relefn.getB_MI());
            AT2 = tempoMaxPR / (relefn.getA_MI()
                    / (Math.pow((IminPR / maior), relefn.getP_MI()) - 1) + relefn.getB_MI());

             
            if(AT1 == AT2) {
                if(AT1 <= maiorAt && AT1 >= menorAt){
                    aux = AT1 / ATstep;
                        AT1 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        //System.out.println("AC: " + maior);
                        t1 = tempoMaxPR;
                        t0 = AT1 * ((relefn.getA_MI()
                                / (Math.pow((IminPR / maior), relefn.getP_MI()) - 1)) + relefn.getB_MI());
                        delta1 = t1 - t0;
                        t3 = tempoMaxPP;
                        t2 = AT1 * ((relefn.getA_MI()
                                / (Math.pow((IminPP / maior), relefn.getP_MI()) - 1)) + relefn.getB_MI());
                        delta2 = t3 - t2;
                        FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                        listaFM_MI.add(new Rele(FM, AT1, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                }else{
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }
            
            if (AT1 > 0 && AT2 > 0) {

                if (AT1 <= maiorAt && AT1 >= menorAt && AT2 <= maiorAt && AT2 >= menorAt) {
                    if (AT1 < AT2) {
                        aux = AT1 / ATstep;
                        AT1 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        //System.out.println("AC: " + maior);
                        t1 = tempoMaxPR;
                        t0 = AT1 * ((relefn.getA_MI()
                                / (Math.pow((IminPR / maior), relefn.getP_MI()) - 1)) + relefn.getB_MI());
                        delta1 = t1 - t0;
                        t3 = tempoMaxPP;
                        t2 = AT1 * ((relefn.getA_MI()
                                / (Math.pow((IminPP / maior), relefn.getP_MI()) - 1)) + relefn.getB_MI());
                        delta2 = t3 - t2;
                        FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                        listaFM_MI.add(new Rele(FM, AT1, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if (AT2 < AT1) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        //System.out.println("AC: " + maior);
                        t1 = tempoMaxPR;
                        t0 = AT2 * ((relefn.getA_MI()
                                / (Math.pow((IminPR / maior), relefn.getP_MI()) - 1)) + relefn.getB_MI());
                        delta1 = t1 - t0;
                        t3 = tempoMaxPP;
                        t2 = AT2 * ((relefn.getA_MI()
                                / (Math.pow((IminPP / maior), relefn.getP_MI()) - 1)) + relefn.getB_MI());
                        delta2 = t3 - t2;
                        FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                        listaFM_MI.add(new Rele(FM, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT1 <= maiorAt && AT1 >= menorAt && AT2 >= maiorAt && AT2 >= menorAt
                        || AT1 <= maiorAt && AT1 >= menorAt && AT2 <= maiorAt && AT2 <= menorAt) {
                    aux = AT1 / ATstep;
                    AT1 = Math.floor(aux) * ATstep;
                    //System.out.println("AT-Fase: " + AT1);
                    maior = (double) lista.get(index);
                    //System.out.println("AC: " + maior);
                    t1 = tempoMaxPR;
                    t0 = AT1 * ((relefn.getA_MI()
                            / (Math.pow((IminPR / maior), relefn.getP_MI()) - 1)) + relefn.getB_MI());
                    delta1 = t1 - t0;
                    t3 = tempoMaxPP;
                    t2 = AT1 * ((relefn.getA_MI()
                            / (Math.pow((IminPP / maior), relefn.getP_MI()) - 1)) + relefn.getB_MI());
                    delta2 = t3 - t2;
                    FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                    listaFM_MI.add(new Rele(FM, AT1, maior));
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                } else if ((AT1 >= maiorAt && AT1 >= menorAt && AT2 <= maiorAt && AT2 >= menorAt
                        || AT1 <= maiorAt && AT1 <= menorAt && AT2 <= maiorAt && AT2 >= menorAt)) {
                    aux = AT2 / ATstep;
                    AT2 = Math.floor(aux) * ATstep;
                    //System.out.println("AT-Fase: " + AT1);
                    maior = (double) lista.get(index);
                    //System.out.println("AC: " + maior);
                    t1 = tempoMaxPR;
                    t0 = AT2 * ((relefn.getA_MI()
                            / (Math.pow((IminPR / maior), relefn.getP_MI()) - 1)) + relefn.getB_MI());
                    delta1 = t1 - t0;
                    t3 = tempoMaxPP;
                    t2 = AT2 * ((relefn.getA_MI()
                            / (Math.pow((IminPP / maior), relefn.getP_MI()) - 1)) + relefn.getB_MI());
                    delta2 = t3 - t2;
                    FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                    listaFM_MI.add(new Rele(FM, AT2, maior));
                    index--;
                    maior = (double) lista.get(index);
                    continue;

                }
                if (AT1 >= maiorAt || AT1 <= menorAt && AT2 >= maiorAt || AT2 <= menorAt) {
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }
            if (AT2 >= 0 && AT1 <= 0) {
                if (AT2 <= maiorAt && AT2 >= menorAt) {
                    aux = AT2 / ATstep;
                    AT2 = Math.floor(aux) * ATstep;
                    //System.out.println("AT-Fase: " + AT1);
                    maior = (double) lista.get(index);
                    //System.out.println("AC: " + maior);
                    t1 = tempoMaxPR;
                    t0 = AT2 * ((relefn.getA_MI()
                            / (Math.pow((IminPR / maior), relefn.getP_MI()) - 1)) + relefn.getB_MI());
                    delta1 = t1 - t0;
                    t3 = tempoMaxPP;
                    t2 = AT2 * ((relefn.getA_MI()
                            / (Math.pow((IminPP / maior), relefn.getP_MI()) - 1)) + relefn.getB_MI());
                    delta2 = t3 - t2;
                    FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                    listaFM_MI.add(new Rele(FM, AT2, maior));
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                } else {
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }

            if (AT1 >= 0 && AT2 <= 0) {
                if (AT1 <= maiorAt && AT1 >= menorAt) {
                    aux = AT1 / ATstep;
                    AT1 = Math.floor(aux) * ATstep;
                    //System.out.println("AT-Fase: " + AT1);
                    maior = (double) lista.get(index);
                    //System.out.println("AC: " + maior);
                    t1 = tempoMaxPR;
                    t0 = AT1 * ((relefn.getA_MI()
                            / (Math.pow((IminPR / maior), relefn.getP_MI()) - 1)) + relefn.getB_MI());
                    delta1 = t1 - t0;
                    t3 = tempoMaxPP;
                    t2 = AT1 * ((relefn.getA_MI()
                            / (Math.pow((IminPP / maior), relefn.getP_MI()) - 1)) + relefn.getB_MI());
                    delta2 = t3 - t2;
                    FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                    listaFM_MI.add(new Rele(FM, AT1, maior));
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                } else {
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }
            if (AT1 <= 0 && AT2 <= 0) {
                index--;
                maior = (double) lista.get(index);
                continue;
            }

        } while (maior > menor);

        System.out.println("");
        System.out.println("Curva muito inversa");
        for (Rele listaFM_mi : listaFM_MI) {

            System.out.println("FM: " + listaFM_mi.FM + ", AT: " + listaFM_mi.AT + ", AC: " + listaFM_mi.AC);
        }

        // ****** CURVA EXTREMAMENTE INVERSA ******
        index = lista.indexOf(Collections.max(lista));
        index1 = lista.indexOf(Collections.min(lista));
        maior = (double) lista.get(index);
        menor = (double) lista.get(index1);
        do {
            AT1 = tempoMaxPP / (relefn.getA_EI()
                    / (Math.pow((IminPP / maior), relefn.getP_EI()) - 1) + relefn.getB_EI());
            AT2 = tempoMaxPR / (relefn.getA_EI()
                    / (Math.pow((IminPR / maior), relefn.getP_EI()) - 1) + relefn.getB_EI());

             
            if(AT1 == AT2) {
                if(AT1 <= maiorAt && AT1 >= menorAt){
                    aux = AT1 / ATstep;
                        AT1 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        //System.out.println("AC: " + maior);
                        t1 = tempoMaxPR;
                        t0 = AT1 * ((relefn.getA_EI()
                                / (Math.pow((IminPR / maior), relefn.getP_EI()) - 1)) + relefn.getB_EI());
                        delta1 = t1 - t0;
                        t3 = tempoMaxPP;
                        t2 = AT1 * ((relefn.getA_EI()
                                / (Math.pow((IminPP / maior), relefn.getP_EI()) - 1)) + relefn.getB_EI());
                        delta2 = t3 - t2;
                        FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                        listaFM_MI.add(new Rele(FM, AT1, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                }else{
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }
            
            if (AT1 > 0 && AT2 > 0) {

                if (AT1 <= maiorAt && AT1 >= menorAt && AT2 <= maiorAt && AT2 >= menorAt) {
                    if (AT1 < AT2) {
                        aux = AT1 / ATstep;
                        AT1 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        //System.out.println("AC: " + maior);
                        t1 = tempoMaxPR;
                        t0 = AT1 * ((relefn.getA_EI()
                                / (Math.pow((IminPR / maior), relefn.getP_EI()) - 1)) + relefn.getB_EI());
                        delta1 = t1 - t0;
                        t3 = tempoMaxPP;
                        t2 = AT1 * ((relefn.getA_EI()
                                / (Math.pow((IminPP / maior), relefn.getP_EI()) - 1)) + relefn.getB_EI());
                        delta2 = t3 - t2;
                        FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                        listaFM_EI.add(new Rele(FM, AT1, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if (AT2 < AT1) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        //System.out.println("AC: " + maior);
                        t1 = tempoMaxPR;
                        t0 = AT2 * ((relefn.getA_EI()
                                / (Math.pow((IminPR / maior), relefn.getP_EI()) - 1)) + relefn.getB_EI());
                        delta1 = t1 - t0;
                        t3 = tempoMaxPP;
                        t2 = AT2 * ((relefn.getA_EI()
                                / (Math.pow((IminPP / maior), relefn.getP_EI()) - 1)) + relefn.getB_EI());
                        delta2 = t3 - t2;
                        FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                        listaFM_EI.add(new Rele(FM, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT1 <= maiorAt && AT1 >= menorAt && AT2 >= maiorAt && AT2 >= menorAt
                        || AT1 <= maiorAt && AT1 >= menorAt && AT2 <= maiorAt && AT2 <= menorAt) {
                    aux = AT1 / ATstep;
                    AT1 = Math.floor(aux) * ATstep;
                    //System.out.println("AT-Fase: " + AT1);
                    maior = (double) lista.get(index);
                    //System.out.println("AC: " + maior);
                    t1 = tempoMaxPR;
                    t0 = AT1 * ((relefn.getA_EI()
                            / (Math.pow((IminPR / maior), relefn.getP_EI()) - 1)) + relefn.getB_EI());
                    delta1 = t1 - t0;
                    t3 = tempoMaxPP;
                    t2 = AT1 * ((relefn.getA_EI()
                            / (Math.pow((IminPP / maior), relefn.getP_EI()) - 1)) + relefn.getB_EI());
                    delta2 = t3 - t2;
                    FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                    listaFM_EI.add(new Rele(FM, AT1, maior));
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                } else if ((AT1 >= maiorAt && AT1 >= menorAt && AT2 <= maiorAt && AT2 >= menorAt
                        || AT1 <= maiorAt && AT1 <= menorAt && AT2 <= maiorAt && AT2 >= menorAt)) {
                    aux = AT2 / ATstep;
                    AT2 = Math.floor(aux) * ATstep;
                    //System.out.println("AT-Fase: " + AT1);
                    maior = (double) lista.get(index);
                    //System.out.println("AC: " + maior);
                    t1 = tempoMaxPR;
                    t0 = AT2 * ((relefn.getA_EI()
                            / (Math.pow((IminPR / maior), relefn.getP_EI()) - 1)) + relefn.getB_EI());
                    delta1 = t1 - t0;
                    t3 = tempoMaxPP;
                    t2 = AT2 * ((relefn.getA_EI()
                            / (Math.pow((IminPP / maior), relefn.getP_EI()) - 1)) + relefn.getB_EI());
                    delta2 = t3 - t2;
                    FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                    listaFM_EI.add(new Rele(FM, AT2, maior));
                    index--;
                    maior = (double) lista.get(index);
                    continue;

                }
                if (AT1 >= maiorAt || AT1 <= menorAt && AT2 >= maiorAt || AT2 <= menorAt) {
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }
            if (AT2 >= 0 && AT1 <= 0) {
                if (AT2 <= maiorAt && AT2 >= menorAt) {
                    aux = AT2 / ATstep;
                    AT2 = Math.floor(aux) * ATstep;
                    //System.out.println("AT-Fase: " + AT1);
                    maior = (double) lista.get(index);
                    //System.out.println("AC: " + maior);
                    t1 = tempoMaxPR;
                    t0 = AT2 * ((relefn.getA_EI()
                            / (Math.pow((IminPR / maior), relefn.getP_EI()) - 1)) + relefn.getB_EI());
                    delta1 = t1 - t0;
                    t3 = tempoMaxPP;
                    t2 = AT2 * ((relefn.getA_EI()
                            / (Math.pow((IminPP / maior), relefn.getP_EI()) - 1)) + relefn.getB_EI());
                    delta2 = t3 - t2;
                    FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                    listaFM_EI.add(new Rele(FM, AT2, maior));
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                } else {
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }

            if (AT1 >= 0 && AT2 <= 0) {
                if (AT1 <= maiorAt && AT1 >= menorAt) {
                    aux = AT1 / ATstep;
                    AT1 = Math.floor(aux) * ATstep;
                    //System.out.println("AT-Fase: " + AT1);
                    maior = (double) lista.get(index);
                    //System.out.println("AC: " + maior);
                    t1 = tempoMaxPR;
                    t0 = AT1 * ((relefn.getA_EI()
                            / (Math.pow((IminPR / maior), relefn.getP_EI()) - 1)) + relefn.getB_EI());
                    delta1 = t1 - t0;
                    t3 = tempoMaxPP;
                    t2 = AT1 * ((relefn.getA_EI()
                            / (Math.pow((IminPP / maior), relefn.getP_EI()) - 1)) + relefn.getB_EI());
                    delta2 = t3 - t2;
                    FM = Math.pow(delta1, 2) + Math.pow(delta2, 2);
                    listaFM_EI.add(new Rele(FM, AT1, maior));
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                } else {
                    index--;
                    maior = (double) lista.get(index);
                    continue;
                }
            }
            if (AT1 <= 0 && AT2 <= 0) {
                index--;
                maior = (double) lista.get(index);
                continue;
            }

        } while (maior > menor);

        System.out.println("");
        System.out.println("Curva extremamente inversa");
        for (Rele listaFM_ei : listaFM_EI) {

            System.out.println("FM: " + listaFM_ei.FM + ", AT: " + listaFM_ei.AT + ", AC: " + listaFM_ei.AC);

        }

        System.out.println("");

        double menor_ni = 99999999;
        double menor_ac_ni = 0;
        double menor_at_ni = 0;

        for (Rele listaFM_ni : listaFM_NI) {
            if (listaFM_ni.getFM() < menor_ni) {
                if (menor_ni > 0) {
                    menor_ni = listaFM_ni.getFM();
                    menor_ac_ni = listaFM_ni.getAC();
                    menor_at_ni = listaFM_ni.getAT();
                    listaFM_Menor.add(new Rele(menor_ni, menor_at_ni, menor_ac_ni));
                }
            }
        }
        System.out.println("Menor FM da curva NI: " + menor_ni + " AT: " + menor_at_ni + " AC: " + menor_ac_ni);

        double menor_mi = 99999999;
        double menor_ac_mi = 0;
        double menor_at_mi = 0;

        for (Rele listaFM_mi : listaFM_MI) {
            if (listaFM_mi.getFM() < menor_mi) {
                menor_mi = listaFM_mi.getFM();
                menor_ac_mi = listaFM_mi.getAC();
                menor_at_mi = listaFM_mi.getAT();
                listaFM_Menor.add(new Rele(menor_mi, menor_at_mi, menor_ac_mi));

            }
        }
        System.out.println("Menor FM da curva MI: " + menor_mi + " AT: " + menor_at_mi + " AC: " + menor_ac_mi);

        double menor_ei = 99999999;
        double menor_ac_ei = 0;
        double menor_at_ei = 0;

        for (Rele listaFM_fi : listaFM_EI) {
            if (listaFM_fi.getFM() < menor_ei) {
                menor_ei = listaFM_fi.getFM();
                menor_ac_ei = listaFM_fi.getAC();
                menor_at_ei = listaFM_fi.getAT();
                listaFM_Menor.add(new Rele(menor_ei, menor_at_ei, menor_ac_ei));

            }
        }
        System.out.println("Menor FM da curva EI: " + menor_ei + " AT: " + menor_at_ei + " AC: " + menor_ac_ei);
        System.out.println("");

        double escolhido_fm = 9999999;
        double escolhido_at = 0;
        double escolhido_ac = 0;
        for (Rele listaFM_menor : listaFM_Menor) {
            if (listaFM_menor.getFM() < escolhido_fm) {
                escolhido_fm = listaFM_menor.getFM();
                escolhido_ac = listaFM_menor.getAC();
                escolhido_at = listaFM_menor.getAT();
                listaFM_Rel_escolhido.add(new Rele(escolhido_fm, escolhido_at, escolhido_ac));

            }
        }
        
        System.out.println("Curva temporizada de neutro:");
        System.out.println("FM: " + escolhido_fm + " AT: " + escolhido_at + " AC: " + escolhido_ac);
        System.out.println("");
        
        // **************** C U R V A  I N S T A N T Â N E A *********************
        
        double ac_ins = 0;
        for (int k = 0; k < AC.length; k++) {
            ac_ins = AC[k];
            if (ac_ins > circ.getSelCorrenteFTmax()) {     //Verificar
                System.out.println("AC da curva instantânea de neutro: "+ac_ins);
                break;
            }
        }
    }
    
}
