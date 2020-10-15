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
public class Rele {
    double FM;
    double AT;
    double AC;

    /**
     *
     * @param FM
     * @param AT
     * @param AC
     */
    public Rele(double FM, double AT, double AC) {
        this.FM = FM;
        this.AT = AT;
        this.AC = AC;
    }
     
    /**
     *
     * @return
     */
    public double getFM() {
        return FM;
    }

    /**
     *
     * @param FM
     */
    public void setFM(double FM) {
        this.FM = FM;
    }

    /**
     *
     * @return
     */
    public double getAT() {
        return AT;
    }

    /**
     *
     * @param AT
     */
    public void setAT(double AT) {
        this.AT = AT;
    }

    /**
     *
     * @return
     */
    public double getAC() {
        return AC;
    }

    /**
     *
     * @param AC
     */
    public void setAC(double AC) {
        this.AC = AC;
    }

   


}
