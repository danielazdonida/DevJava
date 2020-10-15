/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elografico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author DanielaDonida
 */
public class Criterios_Relig_Relig {
    
    public static void criterio_religador_religador_fase_QUEIMAFUSIVEL( //CÓDIGO ALTERADO E TESTADO: OK
            ReleFF releff,
            Circuito circ) {

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
            if (ac < releff.getAC_lenta() && ac > circ.getiMaxCirc()) {        //Verificar
                lista.add(ac);
            }

        }
        //System.out.println(lista); 
        int index = lista.indexOf(Collections.max(lista));
        double maior = (double) lista.get(index);

        int index1 = lista.indexOf(Collections.min(lista));
        double menor = (double) lista.get(index1);

        ArrayList<Rele> listaFM_NI = new ArrayList();
        ArrayList<Rele> listaFM_MI = new ArrayList();
        ArrayList<Rele> listaFM_EI = new ArrayList();

        double t0, t1, t2, t3, AT2, AT3, aux;
        String tipoDeCurvaFASE;
        Scanner sc = new Scanner(System.in);
        //Religador 1 (conhecido)

        System.out.println("A curva do religador à montante é NI, MI ou EI?");
        tipoDeCurvaFASE = sc.nextLine();

        if (tipoDeCurvaFASE.equals("NI")) {
            do {

                t0 = releff.getAT_lenta() * (releff.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFFmin() / releff.getAC_lenta()), releff.getP_NI()) - 1) + releff.getB_NI()); //Verificar

                t1 = releff.getAT_lenta() * (releff.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFFFmax() / releff.getAC_lenta()), releff.getP_NI()) - 1) + releff.getB_NI()); //Verificar
                //Religador que quero definir
                t2 = t0 - releff.getDeltaT();
                t3 = t1 - releff.getDeltaT();

                AT2 = t2 / (releff.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFFmin() / maior), releff.getP_NI()) - 1) + releff.getB_NI()); //Verificar

                AT3 = t3 / (releff.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFFFmax() / maior), releff.getP_NI()) - 1) + releff.getB_NI()); //Verificar

                if (AT2 == AT3) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 > 0 && AT3 > 0) {

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt) {
                        if (AT2 < AT3) {
                            aux = AT2 / ATstep;
                            AT2 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_NI.add(new Rele(0, AT2, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        } else if (AT3 < AT2) {
                            aux = AT3 / ATstep;
                            AT3 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_NI.add(new Rele(0, AT3, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        }
                    }

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 >= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 <= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if ((AT2 >= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 <= menorAt && AT3 <= maiorAt && AT3 >= menorAt)) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;

                    }
                    if (AT2 >= maiorAt || AT2 <= menorAt && AT3 >= maiorAt || AT3 <= menorAt) {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT3 >= 0 && AT2 <= 0) {
                    if (AT3 <= maiorAt && AT3 >= menorAt) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 >= 0 && AT3 <= 0) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT2 <= 0 && AT3 <= 0) {
                    index--;
                    maior = (double) lista.get(index);
                }

            } while (maior > menor);

            System.out.println("Curva normal inversa");
            for (Rele listaFM_ni : listaFM_NI) {

                System.out.println("FM: " + listaFM_ni.FM + ", AT: " + listaFM_ni.AT + ", AC: " + listaFM_ni.AC);
            }
        }   

          else if(tipoDeCurvaFASE.equals("MI")){
             do {

                t0 = releff.getAT_lenta() * (releff.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFFmin() / releff.getAC_lenta()), releff.getP_MI()) - 1) + releff.getB_MI()); //Verificar

                t1 = releff.getAT_lenta() * (releff.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFFFmax() / releff.getAC_lenta()), releff.getP_MI()) - 1) + releff.getB_MI()); //Verificar
                //Religador que quero definir
                t2 = t0 - releff.getDeltaT();
                t3 = t1 - releff.getDeltaT();

                AT3 = t3 / (releff.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFFFmax() / maior), releff.getP_MI()) - 1) + releff.getB_MI()); //Verificar

                AT2 = t2 / (releff.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFFmin() / maior), releff.getP_MI()) - 1) + releff.getB_MI()); //Verificar

                if (AT2 == AT3) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 > 0 && AT3 > 0) {

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt) {
                        if (AT2 < AT3) {
                            aux = AT2 / ATstep;
                            AT2 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_MI.add(new Rele(0, AT2, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        } else if (AT3 < AT2) {
                            aux = AT3 / ATstep;
                            AT3 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_MI.add(new Rele(0, AT3, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        }
                    }

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 >= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 <= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if ((AT2 >= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 <= menorAt && AT3 <= maiorAt && AT3 >= menorAt)) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;

                    }
                    if (AT2 >= maiorAt || AT2 <= menorAt && AT3 >= maiorAt || AT3 <= menorAt) {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT3 >= 0 && AT2 <= 0) {
                    if (AT3 <= maiorAt && AT3 >= menorAt) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 >= 0 && AT3 <= 0) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT2 <= 0 && AT3 <= 0) {
                    index--;
                    maior = (double) lista.get(index);
                }

            } while (maior > menor);

            System.out.println("");
            System.out.println("Curva muito inversa");
            for (Rele listaFM_mi : listaFM_MI) {

                System.out.println("FM: " + listaFM_mi.FM + ", AT: " + listaFM_mi.AT + ", AC: " + listaFM_mi.AC);
            }
            
        }else if(tipoDeCurvaFASE.equals("EI")){
               do {

                t0 = releff.getAT_lenta() * (releff.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFFmin() / releff.getAC_lenta()), releff.getP_EI()) - 1) + releff.getB_EI()); //Verificar

                t1 = releff.getAT_lenta() * (releff.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFFFmax() / releff.getAC_lenta()), releff.getP_EI()) - 1) + releff.getB_EI()); //Verificar
                //Religador que quero definir
                t2 = t0 - releff.getDeltaT();
                t3 = t1 - releff.getDeltaT();

                AT3 = t3 / (releff.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFFFmax() / maior), releff.getP_EI()) - 1) + releff.getB_EI()); //Verificar

                AT2 = t2 / (releff.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFFmin() / maior), releff.getP_EI()) - 1) + releff.getB_EI()); //Verificar

                if (AT2 == AT3) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 > 0 && AT3 > 0) {

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt) {
                        if (AT2 < AT3) {
                            aux = AT2 / ATstep;
                            AT2 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_EI.add(new Rele(0, AT2, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        } else if (AT3 < AT2) {
                            aux = AT3 / ATstep;
                            AT3 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_EI.add(new Rele(0, AT3, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        }
                    }

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 >= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 <= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if ((AT2 >= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 <= menorAt && AT3 <= maiorAt && AT3 >= menorAt)) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;

                    }
                    if (AT2 >= maiorAt || AT2 <= menorAt && AT3 >= maiorAt || AT3 <= menorAt) {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT3 >= 0 && AT2 <= 0) {
                    if (AT3 <= maiorAt && AT3 >= menorAt) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 >= 0 && AT3 <= 0) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT2 <= 0 && AT3 <= 0) {
                    index--;
                    maior = (double) lista.get(index);
                }

            } while (maior > menor);

            System.out.println("");
            System.out.println("Curva extremamente inversa");
            for (Rele listaFM_ei : listaFM_EI) {

                System.out.println("FM: " + listaFM_ei.FM + ", AT: " + listaFM_ei.AT + ", AC: " + listaFM_ei.AC);
            }
        
        }
        System.out.println("");

         // **************** C U R V A  R Á P I D A *********************
        double ac_ins = 0;
        for (int k = 0; k < AC.length; k++) {
            ac_ins = AC[k];
            if (ac_ins > circ.getInRushCirc() && ac_ins > circ.getSelCorrenteFFmax()) {     //Verificar
                System.out.println("AC da curva instantânea de fase: " + ac_ins);
                break;
            }
        }
    }
    
    public static void criterio_religador_religador_neutro_QUEIMAFUSIVEL( //CÓDIGO ALTERADO E TESTADO: OK
            ReleFN relefn,
            Circuito circ) {

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
            if (ac > circ.getiMaxCirc() * 0.3 && ac < relefn.getAC_lenta()) {        //Verificar
                lista.add(ac);
            }

        }
        //System.out.println(lista); 
        int index = lista.indexOf(Collections.max(lista));
        double maior = (double) lista.get(index);

        int index1 = lista.indexOf(Collections.min(lista));
        double menor = (double) lista.get(index1);

        ArrayList<Rele> listaFM_NI = new ArrayList();
        ArrayList<Rele> listaFM_MI = new ArrayList();
        ArrayList<Rele> listaFM_EI = new ArrayList();

        double t0, t1, t2, t3, AT2, AT3, aux;
        String tipoDeCurvaNEUTRO;
        Scanner sc = new Scanner(System.in);
        //Religador 1 (conhecido)

        System.out.println("A curva de neutro do religador à montante é NI, MI ou EI?");
        tipoDeCurvaNEUTRO = sc.nextLine();

        if (tipoDeCurvaNEUTRO.equals("NI")) {
            do {
                t0 = relefn.getAT_lenta() * (relefn.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFTmin() / relefn.getAC_lenta()), relefn.getP_NI()) - 1) + relefn.getB_NI()); //Verificar

                t1 = relefn.getAT_lenta() * (relefn.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFTmax() / relefn.getAC_lenta()), relefn.getP_NI()) - 1) + relefn.getB_NI()); //Verificar
                //Religador que quero definir
                t2 = t0 - relefn.getDeltaT();
                t3 = t1 - relefn.getDeltaT();

                AT2 = t2 / (relefn.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFTmin() / maior), relefn.getP_NI()) - 1) + relefn.getB_NI()); //Verificar

                AT3 = t3 / (relefn.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFTmax() / maior), relefn.getP_NI()) - 1) + relefn.getB_NI()); //Verificar

                if (AT2 == AT3) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 > 0 && AT3 > 0) {

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt) {
                        if (AT2 < AT3) {
                            aux = AT2 / ATstep;
                            AT2 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_NI.add(new Rele(0, AT2, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        } else if (AT3 < AT2) {
                            aux = AT3 / ATstep;
                            AT3 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_NI.add(new Rele(0, AT3, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        }
                    }

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 >= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 <= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if ((AT2 >= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 <= menorAt && AT3 <= maiorAt && AT3 >= menorAt)) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;

                    }
                    if (AT2 >= maiorAt || AT2 <= menorAt && AT3 >= maiorAt || AT3 <= menorAt) {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT3 >= 0 && AT2 <= 0) {
                    if (AT3 <= maiorAt && AT3 >= menorAt) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 >= 0 && AT3 <= 0) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT2 <= 0 && AT3 <= 0) {
                    index--;
                    maior = (double) lista.get(index);
                }

            } while (maior > menor);

            System.out.println("Curva normal inversa");
            for (Rele listaFM_ni : listaFM_NI) {

                System.out.println("FM: " + listaFM_ni.FM + ", AT: " + listaFM_ni.AT + ", AC: " + listaFM_ni.AC);
            }
                   
        // TIPO DE CURVA MI *************************************************************************************************************
        
        }else if(tipoDeCurvaNEUTRO.equals("MI")){
              do {

                t0 = relefn.getAT_lenta() * (relefn.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFTmin() / relefn.getAC_lenta()), relefn.getP_MI()) - 1) + relefn.getB_MI()); //Verificar

                t1 = relefn.getAT_lenta() * (relefn.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFTmax() / relefn.getAC_lenta()), relefn.getP_MI()) - 1) + relefn.getB_MI()); //Verificar
                //Religador que quero definir
                t2 = t0 - relefn.getDeltaT();
                t3 = t1 - relefn.getDeltaT();

                AT3 = t3 / (relefn.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFTmax() / maior), relefn.getP_MI()) - 1) + relefn.getB_MI()); //Verificar

                AT2 = t2 / (relefn.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFTmin() / maior), relefn.getP_MI()) - 1) + relefn.getB_MI()); //Verificar

                if (AT2 == AT3) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 > 0 && AT3 > 0) {

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt) {
                        if (AT2 < AT3) {
                            aux = AT2 / ATstep;
                            AT2 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_MI.add(new Rele(0, AT2, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        } else if (AT3 < AT2) {
                            aux = AT3 / ATstep;
                            AT3 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_MI.add(new Rele(0, AT3, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        }
                    }

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 >= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 <= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if ((AT2 >= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 <= menorAt && AT3 <= maiorAt && AT3 >= menorAt)) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;

                    }
                    if (AT2 >= maiorAt || AT2 <= menorAt && AT3 >= maiorAt || AT3 <= menorAt) {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT3 >= 0 && AT2 <= 0) {
                    if (AT3 <= maiorAt && AT3 >= menorAt) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 >= 0 && AT3 <= 0) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT2 <= 0 && AT3 <= 0) {
                    index--;
                    maior = (double) lista.get(index);
                }

            } while (maior > menor);

            System.out.println("");
            System.out.println("Curva muito inversa");
            for (Rele listaFM_mi : listaFM_MI) {

                System.out.println("FM: " + listaFM_mi.FM + ", AT: " + listaFM_mi.AT + ", AC: " + listaFM_mi.AC);
            }
                    
           //  CURVA EXTREMAMENTE INVERSA ********************************************************************************************
            
        }else if(tipoDeCurvaNEUTRO.equals("EI")){
            do {

                t0 = relefn.getAT_lenta() * (relefn.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFTmin() / relefn.getAC_lenta()), relefn.getP_EI()) - 1) + relefn.getB_EI()); //Verificar

                t1 = relefn.getAT_lenta() * (relefn.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFTmax() / relefn.getAC_lenta()), relefn.getP_EI()) - 1) + relefn.getB_EI()); //Verificar
                //Religador que quero definir
                t2 = t0 - relefn.getDeltaT();
                t3 = t1 - relefn.getDeltaT();

                AT3 = t3 / (relefn.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFTmax() / maior), relefn.getP_EI()) - 1) + relefn.getB_EI()); //Verificar

                AT2 = t2 / (relefn.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFTmin() / maior), relefn.getP_EI()) - 1) + relefn.getB_EI()); //Verificar

                if (AT2 == AT3) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 > 0 && AT3 > 0) {

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt) {
                        if (AT2 < AT3) {
                            aux = AT2 / ATstep;
                            AT2 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_EI.add(new Rele(0, AT2, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        } else if (AT3 < AT2) {
                            aux = AT3 / ATstep;
                            AT3 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_EI.add(new Rele(0, AT3, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        }
                    }

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 >= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 <= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if ((AT2 >= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 <= menorAt && AT3 <= maiorAt && AT3 >= menorAt)) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;

                    }
                    if (AT2 >= maiorAt || AT2 <= menorAt && AT3 >= maiorAt || AT3 <= menorAt) {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT3 >= 0 && AT2 <= 0) {
                    if (AT3 <= maiorAt && AT3 >= menorAt) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 >= 0 && AT3 <= 0) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT2 <= 0 && AT3 <= 0) {
                    index--;
                    maior = (double) lista.get(index);
                }

            } while (maior > menor);

            System.out.println("");
            System.out.println("Curva extremamente inversa");
            for (Rele listaFM_ei : listaFM_EI) {

                System.out.println("FM: " + listaFM_ei.FM + ", AT: " + listaFM_ei.AT + ", AC: " + listaFM_ei.AC);
            }
        
        }
        System.out.println("");

         // **************** C U R V A  R Á P I D A *********************
        double ac_ins = 0;
        for (int k = 0; k < AC.length; k++) {
            ac_ins = AC[k];
            if (ac_ins > circ.getSelCorrenteFTmax()) {     //Verificar
                System.out.println("AC da curva instantânea de neutro: " + ac_ins);
                break;
            }
        }
    }
    
    public static void criterio_religador_religador_fase_SALVAFUSIVEL_curvaLenta( //CÓDIGO ALTERADO E TESTADO: OK
            ReleFF releff,
            Circuito circ) {

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
            if (ac < releff.getAC_lenta() && ac > circ.getiMaxCirc()) {        //Verificar
                lista.add(ac);
            }

        }
        //System.out.println(lista); 
        int index = lista.indexOf(Collections.max(lista));
        double maior = (double) lista.get(index);

        int index1 = lista.indexOf(Collections.min(lista));
        double menor = (double) lista.get(index1);

        ArrayList<Rele> listaFM_NI = new ArrayList();
        ArrayList<Rele> listaFM_MI = new ArrayList();
        ArrayList<Rele> listaFM_EI = new ArrayList();

        double t0, t1, t2, t3, AT2, AT3, aux;
        String tipoDeCurvaFASE;
        Scanner sc = new Scanner(System.in);
        //Religador 1 (conhecido)

        System.out.println("A curva do religador à montante é NI, MI ou EI?");
        tipoDeCurvaFASE = sc.nextLine();

        if (tipoDeCurvaFASE.equals("NI")) {
            do {

                t0 = releff.getAT_lenta() * (releff.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFFmin() / releff.getAC_lenta()), releff.getP_NI()) - 1) + releff.getB_NI()); //Verificar

                t1 = releff.getAT_lenta() * (releff.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFFFmax() / releff.getAC_lenta()), releff.getP_NI()) - 1) + releff.getB_NI()); //Verificar
                //Religador que quero definir
                t2 = t0 - releff.getDeltaT();
                t3 = t1 - releff.getDeltaT();

                AT2 = t2 / (releff.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFFmin() / maior), releff.getP_NI()) - 1) + releff.getB_NI()); //Verificar

                AT3 = t3 / (releff.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFFFmax() / maior), releff.getP_NI()) - 1) + releff.getB_NI()); //Verificar

                if (AT2 == AT3) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 > 0 && AT3 > 0) {

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt) {
                        if (AT2 < AT3) {
                            aux = AT2 / ATstep;
                            AT2 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_NI.add(new Rele(0, AT2, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        } else if (AT3 < AT2) {
                            aux = AT3 / ATstep;
                            AT3 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_NI.add(new Rele(0, AT3, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        }
                    }

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 >= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 <= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if ((AT2 >= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 <= menorAt && AT3 <= maiorAt && AT3 >= menorAt)) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;

                    }
                    if (AT2 >= maiorAt || AT2 <= menorAt && AT3 >= maiorAt || AT3 <= menorAt) {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT3 >= 0 && AT2 <= 0) {
                    if (AT3 <= maiorAt && AT3 >= menorAt) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 >= 0 && AT3 <= 0) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT2 <= 0 && AT3 <= 0) {
                    index--;
                    maior = (double) lista.get(index);
                }

            } while (maior > menor);

            System.out.println("Curva normal inversa");
            for (Rele listaFM_ni : listaFM_NI) {

                System.out.println("FM: " + listaFM_ni.FM + ", AT: " + listaFM_ni.AT + ", AC: " + listaFM_ni.AC);
            }
        }   

          else if(tipoDeCurvaFASE.equals("MI")){
             do {

                t0 = releff.getAT_lenta() * (releff.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFFmin() / releff.getAC_lenta()), releff.getP_MI()) - 1) + releff.getB_MI()); //Verificar

                t1 = releff.getAT_lenta() * (releff.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFFFmax() / releff.getAC_lenta()), releff.getP_MI()) - 1) + releff.getB_MI()); //Verificar
                //Religador que quero definir
                t2 = t0 - releff.getDeltaT();
                t3 = t1 - releff.getDeltaT();

                AT3 = t3 / (releff.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFFFmax() / maior), releff.getP_MI()) - 1) + releff.getB_MI()); //Verificar

                AT2 = t2 / (releff.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFFmin() / maior), releff.getP_MI()) - 1) + releff.getB_MI()); //Verificar

                if (AT2 == AT3) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 > 0 && AT3 > 0) {

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt) {
                        if (AT2 < AT3) {
                            aux = AT2 / ATstep;
                            AT2 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_MI.add(new Rele(0, AT2, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        } else if (AT3 < AT2) {
                            aux = AT3 / ATstep;
                            AT3 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_MI.add(new Rele(0, AT3, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        }
                    }

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 >= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 <= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if ((AT2 >= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 <= menorAt && AT3 <= maiorAt && AT3 >= menorAt)) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;

                    }
                    if (AT2 >= maiorAt || AT2 <= menorAt && AT3 >= maiorAt || AT3 <= menorAt) {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT3 >= 0 && AT2 <= 0) {
                    if (AT3 <= maiorAt && AT3 >= menorAt) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 >= 0 && AT3 <= 0) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT2 <= 0 && AT3 <= 0) {
                    index--;
                    maior = (double) lista.get(index);
                }

            } while (maior > menor);

            System.out.println("");
            System.out.println("Curva muito inversa");
            for (Rele listaFM_mi : listaFM_MI) {

                System.out.println("FM: " + listaFM_mi.FM + ", AT: " + listaFM_mi.AT + ", AC: " + listaFM_mi.AC);
            }
            
        }else if(tipoDeCurvaFASE.equals("EI")){
               do {

                t0 = releff.getAT_lenta() * (releff.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFFmin() / releff.getAC_lenta()), releff.getP_EI()) - 1) + releff.getB_EI()); //Verificar

                t1 = releff.getAT_lenta() * (releff.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFFFmax() / releff.getAC_lenta()), releff.getP_EI()) - 1) + releff.getB_EI()); //Verificar
                //Religador que quero definir
                t2 = t0 - releff.getDeltaT();
                t3 = t1 - releff.getDeltaT();

                AT3 = t3 / (releff.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFFFmax() / maior), releff.getP_EI()) - 1) + releff.getB_EI()); //Verificar

                AT2 = t2 / (releff.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFFmin() / maior), releff.getP_EI()) - 1) + releff.getB_EI()); //Verificar

                if (AT2 == AT3) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 > 0 && AT3 > 0) {

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt) {
                        if (AT2 < AT3) {
                            aux = AT2 / ATstep;
                            AT2 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_EI.add(new Rele(0, AT2, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        } else if (AT3 < AT2) {
                            aux = AT3 / ATstep;
                            AT3 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_EI.add(new Rele(0, AT3, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        }
                    }

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 >= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 <= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if ((AT2 >= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 <= menorAt && AT3 <= maiorAt && AT3 >= menorAt)) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;

                    }
                    if (AT2 >= maiorAt || AT2 <= menorAt && AT3 >= maiorAt || AT3 <= menorAt) {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT3 >= 0 && AT2 <= 0) {
                    if (AT3 <= maiorAt && AT3 >= menorAt) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 >= 0 && AT3 <= 0) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT2 <= 0 && AT3 <= 0) {
                    index--;
                    maior = (double) lista.get(index);
                }

            } while (maior > menor);

            System.out.println("");
            System.out.println("Curva extremamente inversa");
            for (Rele listaFM_ei : listaFM_EI) {

                System.out.println("FM: " + listaFM_ei.FM + ", AT: " + listaFM_ei.AT + ", AC: " + listaFM_ei.AC);
            }
        
        }
        System.out.println("");


    }
    
    public static void criterio_religador_religador_fase_SALVAFUSIVEL_curvaRapida( //CÓDIGO ALTERADO E TESTADO: OK
            ReleFF releff,
            Circuito circ) {

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
            if (ac < releff.getAC_rapida() && ac > circ.getiMaxCirc()) {        //Verificar
                lista.add(ac);
            }

        }
        //System.out.println(lista); 
        int index = lista.indexOf(Collections.max(lista));
        double maior = (double) lista.get(index);

        int index1 = lista.indexOf(Collections.min(lista));
        double menor = (double) lista.get(index1);

        ArrayList<Rele> listaFM_NI = new ArrayList();
        ArrayList<Rele> listaFM_MI = new ArrayList();
        ArrayList<Rele> listaFM_EI = new ArrayList();

        double t0, t1, t2, t3, AT2, AT3, aux;
        String tipoDeCurvaFASE;
        Scanner sc = new Scanner(System.in);
        //Religador 1 (conhecido)

        System.out.println("A curva do religador à montante é NI, MI ou EI?");
        tipoDeCurvaFASE = sc.nextLine();

        if (tipoDeCurvaFASE.equals("NI")) {
            do {

                t0 = releff.getAT_rapida() * (releff.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFFmin() / releff.getAC_rapida()), releff.getP_NI()) - 1) + releff.getB_NI()); //Verificar

                t1 = releff.getAT_rapida() * (releff.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFFFmax() / releff.getAC_rapida()), releff.getP_NI()) - 1) + releff.getB_NI()); //Verificar
                //Religador que quero definir
                t2 = t0 - releff.getDeltaT();
                t3 = t1 - releff.getDeltaT();

                AT2 = t2 / (releff.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFFmin() / maior), releff.getP_NI()) - 1) + releff.getB_NI()); //Verificar

                AT3 = t3 / (releff.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFFFmax() / maior), releff.getP_NI()) - 1) + releff.getB_NI()); //Verificar

                if (AT2 == AT3) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 > 0 && AT3 > 0) {

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt) {
                        if (AT2 < AT3) {
                            aux = AT2 / ATstep;
                            AT2 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_NI.add(new Rele(0, AT2, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        } else if (AT3 < AT2) {
                            aux = AT3 / ATstep;
                            AT3 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_NI.add(new Rele(0, AT3, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        }
                    }

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 >= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 <= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if ((AT2 >= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 <= menorAt && AT3 <= maiorAt && AT3 >= menorAt)) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;

                    }
                    if (AT2 >= maiorAt || AT2 <= menorAt && AT3 >= maiorAt || AT3 <= menorAt) {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT3 >= 0 && AT2 <= 0) {
                    if (AT3 <= maiorAt && AT3 >= menorAt) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 >= 0 && AT3 <= 0) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT2 <= 0 && AT3 <= 0) {
                    index--;
                    maior = (double) lista.get(index);
                }

            } while (maior > menor);

            System.out.println("Curva normal inversa");
            for (Rele listaFM_ni : listaFM_NI) {

                System.out.println("FM: " + listaFM_ni.FM + ", AT: " + listaFM_ni.AT + ", AC: " + listaFM_ni.AC);
            }
        }   

          else if(tipoDeCurvaFASE.equals("MI")){
             do {

                t0 = releff.getAT_rapida() * (releff.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFFmin() / releff.getAC_rapida()), releff.getP_MI()) - 1) + releff.getB_MI()); //Verificar

                t1 = releff.getAT_rapida() * (releff.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFFFmax() / releff.getAC_rapida()), releff.getP_MI()) - 1) + releff.getB_MI()); //Verificar
                //Religador que quero definir
                t2 = t0 - releff.getDeltaT();
                t3 = t1 - releff.getDeltaT();

                AT3 = t3 / (releff.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFFFmax() / maior), releff.getP_MI()) - 1) + releff.getB_MI()); //Verificar

                AT2 = t2 / (releff.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFFmin() / maior), releff.getP_MI()) - 1) + releff.getB_MI()); //Verificar

                if (AT2 == AT3) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 > 0 && AT3 > 0) {

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt) {
                        if (AT2 < AT3) {
                            aux = AT2 / ATstep;
                            AT2 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_MI.add(new Rele(0, AT2, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        } else if (AT3 < AT2) {
                            aux = AT3 / ATstep;
                            AT3 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_MI.add(new Rele(0, AT3, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        }
                    }

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 >= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 <= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if ((AT2 >= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 <= menorAt && AT3 <= maiorAt && AT3 >= menorAt)) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;

                    }
                    if (AT2 >= maiorAt || AT2 <= menorAt && AT3 >= maiorAt || AT3 <= menorAt) {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT3 >= 0 && AT2 <= 0) {
                    if (AT3 <= maiorAt && AT3 >= menorAt) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 >= 0 && AT3 <= 0) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT2 <= 0 && AT3 <= 0) {
                    index--;
                    maior = (double) lista.get(index);
                }

            } while (maior > menor);

            System.out.println("");
            System.out.println("Curva muito inversa");
            for (Rele listaFM_mi : listaFM_MI) {

                System.out.println("FM: " + listaFM_mi.FM + ", AT: " + listaFM_mi.AT + ", AC: " + listaFM_mi.AC);
            }
            
        }else if(tipoDeCurvaFASE.equals("EI")){
               do {

                t0 = releff.getAT_rapida() * (releff.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFFmin() / releff.getAC_rapida()), releff.getP_EI()) - 1) + releff.getB_EI()); //Verificar

                t1 = releff.getAT_rapida() * (releff.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFFFmax() / releff.getAC_rapida()), releff.getP_EI()) - 1) + releff.getB_EI()); //Verificar
                //Religador que quero definir
                t2 = t0 - releff.getDeltaT();
                t3 = t1 - releff.getDeltaT();

                AT3 = t3 / (releff.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFFFmax() / maior), releff.getP_EI()) - 1) + releff.getB_EI()); //Verificar

                AT2 = t2 / (releff.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFFmin() / maior), releff.getP_EI()) - 1) + releff.getB_EI()); //Verificar

                if (AT2 == AT3) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 > 0 && AT3 > 0) {

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt) {
                        if (AT2 < AT3) {
                            aux = AT2 / ATstep;
                            AT2 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_EI.add(new Rele(0, AT2, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        } else if (AT3 < AT2) {
                            aux = AT3 / ATstep;
                            AT3 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_EI.add(new Rele(0, AT3, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        }
                    }

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 >= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 <= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if ((AT2 >= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 <= menorAt && AT3 <= maiorAt && AT3 >= menorAt)) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;

                    }
                    if (AT2 >= maiorAt || AT2 <= menorAt && AT3 >= maiorAt || AT3 <= menorAt) {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT3 >= 0 && AT2 <= 0) {
                    if (AT3 <= maiorAt && AT3 >= menorAt) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 >= 0 && AT3 <= 0) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT2 <= 0 && AT3 <= 0) {
                    index--;
                    maior = (double) lista.get(index);
                }

            } while (maior > menor);

            System.out.println("");
            System.out.println("Curva extremamente inversa");
            for (Rele listaFM_ei : listaFM_EI) {

                System.out.println("FM: " + listaFM_ei.FM + ", AT: " + listaFM_ei.AT + ", AC: " + listaFM_ei.AC);
            }
        
        }
        System.out.println("");
 
    }
    
    public static void criterio_religador_religador_neutro_SALVAFUSIVEL_curvaLenta( //CÓDIGO ALTERADO E TESTADO: OK
            ReleFN relefn,
            Circuito circ) {

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
            if (ac > circ.getiMaxCirc() * 0.3 && ac < relefn.getAC_lenta()) {        //Verificar
                lista.add(ac);
            }

        }
        //System.out.println(lista); 
        int index = lista.indexOf(Collections.max(lista));
        double maior = (double) lista.get(index);

        int index1 = lista.indexOf(Collections.min(lista));
        double menor = (double) lista.get(index1);

        ArrayList<Rele> listaFM_NI = new ArrayList();
        ArrayList<Rele> listaFM_MI = new ArrayList();
        ArrayList<Rele> listaFM_EI = new ArrayList();

        double t0, t1, t2, t3, AT2, AT3, aux;
        String tipoDeCurvaNEUTRO;
        Scanner sc = new Scanner(System.in);
        //Religador 1 (conhecido)

        System.out.println("A curva de neutro do religador à montante é NI, MI ou EI?");
        tipoDeCurvaNEUTRO = sc.nextLine();

         if (tipoDeCurvaNEUTRO.equals("NI")) {
            do {
                t0 = relefn.getAT_lenta() * (relefn.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFTmin() / relefn.getAC_lenta()), relefn.getP_NI()) - 1) + relefn.getB_NI()); //Verificar

                t1 = relefn.getAT_lenta() * (relefn.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFTmax() / relefn.getAC_lenta()), relefn.getP_NI()) - 1) + relefn.getB_NI()); //Verificar
                //Religador que quero definir
                t2 = t0 - relefn.getDeltaT();
                t3 = t1 - relefn.getDeltaT();

                AT2 = t2 / (relefn.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFTmin() / maior), relefn.getP_NI()) - 1) + relefn.getB_NI()); //Verificar

                AT3 = t3 / (relefn.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFTmax() / maior), relefn.getP_NI()) - 1) + relefn.getB_NI()); //Verificar

                if (AT2 == AT3) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 > 0 && AT3 > 0) {

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt) {
                        if (AT2 < AT3) {
                            aux = AT2 / ATstep;
                            AT2 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_NI.add(new Rele(0, AT2, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        } else if (AT3 < AT2) {
                            aux = AT3 / ATstep;
                            AT3 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_NI.add(new Rele(0, AT3, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        }
                    }

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 >= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 <= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if ((AT2 >= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 <= menorAt && AT3 <= maiorAt && AT3 >= menorAt)) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;

                    }
                    if (AT2 >= maiorAt || AT2 <= menorAt && AT3 >= maiorAt || AT3 <= menorAt) {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT3 >= 0 && AT2 <= 0) {
                    if (AT3 <= maiorAt && AT3 >= menorAt) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 >= 0 && AT3 <= 0) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT2 <= 0 && AT3 <= 0) {
                    index--;
                    maior = (double) lista.get(index);
                }

            } while (maior > menor);

            System.out.println("Curva normal inversa");
            for (Rele listaFM_ni : listaFM_NI) {

                System.out.println("FM: " + listaFM_ni.FM + ", AT: " + listaFM_ni.AT + ", AC: " + listaFM_ni.AC);
            }
                   
        // TIPO DE CURVA MI *************************************************************************************************************
        
        }else if(tipoDeCurvaNEUTRO.equals("MI")){
              do {

                t0 = relefn.getAT_lenta() * (relefn.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFTmin() / relefn.getAC_lenta()), relefn.getP_MI()) - 1) + relefn.getB_MI()); //Verificar

                t1 = relefn.getAT_lenta() * (relefn.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFTmax() / relefn.getAC_lenta()), relefn.getP_MI()) - 1) + relefn.getB_MI()); //Verificar
                //Religador que quero definir
                t2 = t0 - relefn.getDeltaT();
                t3 = t1 - relefn.getDeltaT();

                AT3 = t3 / (relefn.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFTmax() / maior), relefn.getP_MI()) - 1) + relefn.getB_MI()); //Verificar

                AT2 = t2 / (relefn.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFTmin() / maior), relefn.getP_MI()) - 1) + relefn.getB_MI()); //Verificar

                if (AT2 == AT3) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 > 0 && AT3 > 0) {

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt) {
                        if (AT2 < AT3) {
                            aux = AT2 / ATstep;
                            AT2 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_MI.add(new Rele(0, AT2, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        } else if (AT3 < AT2) {
                            aux = AT3 / ATstep;
                            AT3 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_MI.add(new Rele(0, AT3, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        }
                    }

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 >= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 <= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if ((AT2 >= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 <= menorAt && AT3 <= maiorAt && AT3 >= menorAt)) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;

                    }
                    if (AT2 >= maiorAt || AT2 <= menorAt && AT3 >= maiorAt || AT3 <= menorAt) {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT3 >= 0 && AT2 <= 0) {
                    if (AT3 <= maiorAt && AT3 >= menorAt) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 >= 0 && AT3 <= 0) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT2 <= 0 && AT3 <= 0) {
                    index--;
                    maior = (double) lista.get(index);
                }

            } while (maior > menor);

            System.out.println("");
            System.out.println("Curva muito inversa");
            for (Rele listaFM_mi : listaFM_MI) {

                System.out.println("FM: " + listaFM_mi.FM + ", AT: " + listaFM_mi.AT + ", AC: " + listaFM_mi.AC);
            }
                    
           //  CURVA EXTREMAMENTE INVERSA ********************************************************************************************
            
        }else if(tipoDeCurvaNEUTRO.equals("EI")){
            do {

                t0 = relefn.getAT_lenta() * (relefn.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFTmin() / relefn.getAC_lenta()), relefn.getP_EI()) - 1) + relefn.getB_EI()); //Verificar

                t1 = relefn.getAT_lenta() * (relefn.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFTmax() / relefn.getAC_lenta()), relefn.getP_EI()) - 1) + relefn.getB_EI()); //Verificar
                //Religador que quero definir
                t2 = t0 - relefn.getDeltaT();
                t3 = t1 - relefn.getDeltaT();

                AT3 = t3 / (relefn.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFTmax() / maior), relefn.getP_EI()) - 1) + relefn.getB_EI()); //Verificar

                AT2 = t2 / (relefn.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFTmin() / maior), relefn.getP_EI()) - 1) + relefn.getB_EI()); //Verificar

                if (AT2 == AT3) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 > 0 && AT3 > 0) {

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt) {
                        if (AT2 < AT3) {
                            aux = AT2 / ATstep;
                            AT2 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_EI.add(new Rele(0, AT2, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        } else if (AT3 < AT2) {
                            aux = AT3 / ATstep;
                            AT3 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_EI.add(new Rele(0, AT3, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        }
                    }

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 >= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 <= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if ((AT2 >= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 <= menorAt && AT3 <= maiorAt && AT3 >= menorAt)) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;

                    }
                    if (AT2 >= maiorAt || AT2 <= menorAt && AT3 >= maiorAt || AT3 <= menorAt) {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT3 >= 0 && AT2 <= 0) {
                    if (AT3 <= maiorAt && AT3 >= menorAt) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 >= 0 && AT3 <= 0) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT2 <= 0 && AT3 <= 0) {
                    index--;
                    maior = (double) lista.get(index);
                }

            } while (maior > menor);

            System.out.println("");
            System.out.println("Curva extremamente inversa");
            for (Rele listaFM_ei : listaFM_EI) {

                System.out.println("FM: " + listaFM_ei.FM + ", AT: " + listaFM_ei.AT + ", AC: " + listaFM_ei.AC);
            }
        
        }
        System.out.println("");

       
    }
     
      public static void criterio_religador_religador_neutro_SALVAFUSIVEL_curvaRapida( //CÓDIGO ALTERADO E TESTADO: OK
            ReleFN relefn,
            Circuito circ) {

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
            if (ac > circ.getiMaxCirc() * 0.3 && ac < relefn.getAC_rapida()) {        //Verificar
                lista.add(ac);
            }

        }
        //System.out.println(lista); 
        int index = lista.indexOf(Collections.max(lista));
        double maior = (double) lista.get(index);

        int index1 = lista.indexOf(Collections.min(lista));
        double menor = (double) lista.get(index1);

        ArrayList<Rele> listaFM_NI = new ArrayList();
        ArrayList<Rele> listaFM_MI = new ArrayList();
        ArrayList<Rele> listaFM_EI = new ArrayList();

        double t0, t1, t2, t3, AT2, AT3, aux;
        String tipoDeCurvaNEUTRO;
        Scanner sc = new Scanner(System.in);
        //Religador 1 (conhecido)

        System.out.println("A curva de neutro do religador à montante é NI, MI ou EI?");
        tipoDeCurvaNEUTRO = sc.nextLine();

         if (tipoDeCurvaNEUTRO.equals("NI")) {
            do {
                t0 = relefn.getAT_rapida() * (relefn.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFTmin() / relefn.getAC_rapida()), relefn.getP_NI()) - 1) + relefn.getB_NI()); //Verificar

                t1 = relefn.getAT_rapida() * (relefn.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFTmax() / relefn.getAC_rapida()), relefn.getP_NI()) - 1) + relefn.getB_NI()); //Verificar
                //Religador que quero definir
                t2 = t0 - relefn.getDeltaT();
                t3 = t1 - relefn.getDeltaT();

                AT2 = t2 / (relefn.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFTmin() / maior), relefn.getP_NI()) - 1) + relefn.getB_NI()); //Verificar

                AT3 = t3 / (relefn.getA_NI()
                        / (Math.pow((circ.getSelCorrenteFTmax() / maior), relefn.getP_NI()) - 1) + relefn.getB_NI()); //Verificar

                if (AT2 == AT3) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 > 0 && AT3 > 0) {

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt) {
                        if (AT2 < AT3) {
                            aux = AT2 / ATstep;
                            AT2 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_NI.add(new Rele(0, AT2, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        } else if (AT3 < AT2) {
                            aux = AT3 / ATstep;
                            AT3 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_NI.add(new Rele(0, AT3, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        }
                    }

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 >= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 <= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if ((AT2 >= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 <= menorAt && AT3 <= maiorAt && AT3 >= menorAt)) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;

                    }
                    if (AT2 >= maiorAt || AT2 <= menorAt && AT3 >= maiorAt || AT3 <= menorAt) {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT3 >= 0 && AT2 <= 0) {
                    if (AT3 <= maiorAt && AT3 >= menorAt) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 >= 0 && AT3 <= 0) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_NI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT2 <= 0 && AT3 <= 0) {
                    index--;
                    maior = (double) lista.get(index);
                }

            } while (maior > menor);

            System.out.println("Curva normal inversa");
            for (Rele listaFM_ni : listaFM_NI) {

                System.out.println("FM: " + listaFM_ni.FM + ", AT: " + listaFM_ni.AT + ", AC: " + listaFM_ni.AC);
            }
                   
        // TIPO DE CURVA MI *************************************************************************************************************
        
        }else if(tipoDeCurvaNEUTRO.equals("MI")){
              do {

                t0 = relefn.getAT_rapida() * (relefn.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFTmin() / relefn.getAC_rapida()), relefn.getP_MI()) - 1) + relefn.getB_MI()); //Verificar

                t1 = relefn.getAT_rapida() * (relefn.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFTmax() / relefn.getAC_rapida()), relefn.getP_MI()) - 1) + relefn.getB_MI()); //Verificar
                //Religador que quero definir
                t2 = t0 - relefn.getDeltaT();
                t3 = t1 - relefn.getDeltaT();

                AT3 = t3 / (relefn.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFTmax() / maior), relefn.getP_MI()) - 1) + relefn.getB_MI()); //Verificar

                AT2 = t2 / (relefn.getA_MI()
                        / (Math.pow((circ.getSelCorrenteFTmin() / maior), relefn.getP_MI()) - 1) + relefn.getB_MI()); //Verificar

                if (AT2 == AT3) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 > 0 && AT3 > 0) {

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt) {
                        if (AT2 < AT3) {
                            aux = AT2 / ATstep;
                            AT2 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_MI.add(new Rele(0, AT2, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        } else if (AT3 < AT2) {
                            aux = AT3 / ATstep;
                            AT3 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_MI.add(new Rele(0, AT3, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        }
                    }

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 >= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 <= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if ((AT2 >= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 <= menorAt && AT3 <= maiorAt && AT3 >= menorAt)) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;

                    }
                    if (AT2 >= maiorAt || AT2 <= menorAt && AT3 >= maiorAt || AT3 <= menorAt) {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT3 >= 0 && AT2 <= 0) {
                    if (AT3 <= maiorAt && AT3 >= menorAt) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 >= 0 && AT3 <= 0) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_MI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT2 <= 0 && AT3 <= 0) {
                    index--;
                    maior = (double) lista.get(index);
                }

            } while (maior > menor);

            System.out.println("");
            System.out.println("Curva muito inversa");
            for (Rele listaFM_mi : listaFM_MI) {

                System.out.println("FM: " + listaFM_mi.FM + ", AT: " + listaFM_mi.AT + ", AC: " + listaFM_mi.AC);
            }
                    
           //  CURVA EXTREMAMENTE INVERSA ********************************************************************************************
            
        }else if(tipoDeCurvaNEUTRO.equals("EI")){
            do {

                t0 = relefn.getAT_rapida() * (relefn.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFTmin() / relefn.getAC_rapida()), relefn.getP_EI()) - 1) + relefn.getB_EI()); //Verificar

                t1 = relefn.getAT_rapida() * (relefn.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFTmax() / relefn.getAC_rapida()), relefn.getP_EI()) - 1) + relefn.getB_EI()); //Verificar
                //Religador que quero definir
                t2 = t0 - relefn.getDeltaT();
                t3 = t1 - relefn.getDeltaT();

                AT3 = t3 / (relefn.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFTmax() / maior), relefn.getP_EI()) - 1) + relefn.getB_EI()); //Verificar

                AT2 = t2 / (relefn.getA_EI()
                        / (Math.pow((circ.getSelCorrenteFTmin() / maior), relefn.getP_EI()) - 1) + relefn.getB_EI()); //Verificar

                if (AT2 == AT3) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 > 0 && AT3 > 0) {

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt) {
                        if (AT2 < AT3) {
                            aux = AT2 / ATstep;
                            AT2 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_EI.add(new Rele(0, AT2, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        } else if (AT3 < AT2) {
                            aux = AT3 / ATstep;
                            AT3 = Math.floor(aux) * ATstep;
                            //System.out.println("AT-Fase: " + AT1);
                            maior = (double) lista.get(index);
                            listaFM_EI.add(new Rele(0, AT3, maior));
                            index--;
                            maior = (double) lista.get(index);
                            continue;
                        }
                    }

                    if (AT2 <= maiorAt && AT2 >= menorAt && AT3 >= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 <= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else if ((AT2 >= maiorAt && AT2 >= menorAt && AT3 <= maiorAt && AT3 >= menorAt
                            || AT2 <= maiorAt && AT2 <= menorAt && AT3 <= maiorAt && AT3 >= menorAt)) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;

                    }
                    if (AT2 >= maiorAt || AT2 <= menorAt && AT3 >= maiorAt || AT3 <= menorAt) {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT3 >= 0 && AT2 <= 0) {
                    if (AT3 <= maiorAt && AT3 >= menorAt) {
                        aux = AT3 / ATstep;
                        AT3 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT3, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }

                if (AT2 >= 0 && AT3 <= 0) {
                    if (AT2 <= maiorAt && AT2 >= menorAt) {
                        aux = AT2 / ATstep;
                        AT2 = Math.floor(aux) * ATstep;
                        //System.out.println("AT-Fase: " + AT1);
                        maior = (double) lista.get(index);
                        listaFM_EI.add(new Rele(0, AT2, maior));
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    } else {
                        index--;
                        maior = (double) lista.get(index);
                        continue;
                    }
                }
                if (AT2 <= 0 && AT3 <= 0) {
                    index--;
                    maior = (double) lista.get(index);
                }

            } while (maior > menor);

            System.out.println("");
            System.out.println("Curva extremamente inversa");
            for (Rele listaFM_ei : listaFM_EI) {

                System.out.println("FM: " + listaFM_ei.FM + ", AT: " + listaFM_ei.AT + ", AC: " + listaFM_ei.AC);
            }
        
        }
        System.out.println("");

      }
      
    
}
