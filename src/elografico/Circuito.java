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
public class Circuito {

    private double iMaxCirc;
    private double inRushCirc;
    private double iMaxCC;
    private double CorrenteFTmaxNoLocal;
    private double AlcCorrenteFTmin;
    private double AlcCorrenteFFmin;
    private double SelCorrenteFTmin;
    private double SelCorrenteFFmin;
    private double SelCorrenteFFFmax;
    private double SelCorrenteFTmax;
    private double SelCorrenteFFmax;
    private double CTI;
   

    public Circuito(){
        this.iMaxCirc = 6; //corrente de carga máxima do elemento a determinar
        this.inRushCirc = 0; //corrente de inrush do elemento a determinar
        this.iMaxCC = 430; //corrente de cc máxima do elemento a determinar (no ponto do elemento)
        this.AlcCorrenteFTmin = 120; //corrente de cc ft-min no limite do alcance do elemento a determinar
        this.AlcCorrenteFFmin = 260; //corrente de cc ff no limite do alcance do elemento a determinar
        this.SelCorrenteFTmin = 120; //corrente de cc ft-min vista pelo elemento a determinar para obtenção de seletividde
        this.SelCorrenteFFFmax = 300; //corrente de cc fff máxima vista pelo elemento a determinar (no ponto do elemento)
        this.SelCorrenteFFmin = 260; //corrente de cc ft-min vista pelo elemento a determinar para obtenção de seletividde
        this.SelCorrenteFTmax = 260; //corrente de cc ff máxima vista pelo elemento a determinar (no ponto do elemento)
        this.CorrenteFTmaxNoLocal = 372;
        this.SelCorrenteFFmax = 260;
        this.CTI = 0.1; //intervalo de tempo mínimo para a seletividade
        
    }

    public double getCorrenteFTmaxNoLocal() {
        return CorrenteFTmaxNoLocal;
    }

    public void setCorrenteFTmaxNoLocal(double CorrenteFTmaxNoLocal) {
        this.CorrenteFTmaxNoLocal = CorrenteFTmaxNoLocal;
    }
    
      public double getSelCorrenteFFmax() {
        return SelCorrenteFFmax;
    }

    public void setSelCorrenteFFmax(double SelCorrenteFFmax) {
        this.SelCorrenteFFmax = SelCorrenteFFmax;
    }
    
    public double getiMaxCirc() {
        return iMaxCirc;
    }

    public void setiMaxCirc(double iMaxCirc) {
        this.iMaxCirc = iMaxCirc;
    }

    public double getInRushCirc() {
        return inRushCirc;
    }

    public void setInRushCirc(double inRushCirc) {
        this.inRushCirc = inRushCirc;
    }

    public double getiMaxCC() {
        return iMaxCC;
    }

    public void setiMaxCC(double iMaxCC) {
        this.iMaxCC = iMaxCC;
    }

    public double getAlcCorrenteFTmin() {
        return AlcCorrenteFTmin;
    }

    public void setAlcCorrenteFTmin(double AlcCorrenteFTmin) {
        this.AlcCorrenteFTmin = AlcCorrenteFTmin;
    }

    public double getSelCorrenteFTmin() {
        return SelCorrenteFTmin;
    }

    public void setSelCorrenteFTmin(double SelCorrenteFTmin) {
        this.SelCorrenteFTmin = SelCorrenteFTmin;
    }

    public double getSelCorrenteFFmin() {
        return SelCorrenteFFmin;
    }

    public void setSelCorrenteFFmin(double SelCorrenteFFmin) {
        this.SelCorrenteFFmin = SelCorrenteFFmin;
    }

    public double getAlcCorrenteFFmin() {
        return AlcCorrenteFFmin;
    }

    public void setAlcCorrenteFFmin(double AlcCorrenteFFmin) {
        this.AlcCorrenteFFmin = AlcCorrenteFFmin;
    }

    public double getSelCorrenteFFFmax() {
        return SelCorrenteFFFmax;
    }

    public void setSelCorrenteFFFmax(double SelCorrenteFFFmax) {
        this.SelCorrenteFFFmax = SelCorrenteFFFmax;
    }

    public double getSelCorrenteFTmax() {
        return SelCorrenteFTmax;
    }

    public void setSelCorrenteFTmax(double SelCorrenteFTmax) {
        this.SelCorrenteFTmax = SelCorrenteFTmax;
    }

    public double getCTI() {
        return CTI;
    }

    public void setCTI(double CTI) {
        this.CTI = CTI;
    }
   
    

    
}