
package elografico;


public class ReleFN {
    double AT_lenta, AC_lenta, AC_rapida, AT_rapida, A_NI, B_NI, P_NI, A_MI, B_MI, P_MI, A_EI, B_EI, P_EI, deltaT, topReleN;
    
    public ReleFN(){
         this.AT_lenta = 0.46;
         this.AC_lenta = 90;
         this.AT_rapida = 0.09;
         this.AC_rapida = 65;
         this.A_NI = 0.14;
         this.B_NI = 0;
         this.P_NI = 0.02;
         this.A_MI = 13.5;
         this.B_MI = 0;
         this.P_MI = 1;
         this.A_EI = 80;
         this.B_EI = 0;
         this.P_EI = 2;
         this.deltaT = 0.1;
        
    }

    public double getAC_rapida() {
        return AC_rapida;
    }

    public void setAC_rapida(double AC_rapida) {
        this.AC_rapida = AC_rapida;
    }

    public double getAT_rapida() {
        return AT_rapida;
    }

    public void setAT_rapida(double AT_rapida) {
        this.AT_rapida = AT_rapida;
    }

    public double getTopReleN() {
        return topReleN;
    }

    public void setTopReleN(double topReleN) {
        this.topReleN = topReleN;
    }

    public double getA_NI() {
        return A_NI;
    }

    public void setA_NI(double A_NI) {
        this.A_NI = A_NI;
    }

    public double getB_NI() {
        return B_NI;
    }

    public void setB_NI(double B_NI) {
        this.B_NI = B_NI;
    }

    public double getP_NI() {
        return P_NI;
    }

    public void setP_NI(double P_NI) {
        this.P_NI = P_NI;
    }

    public double getA_MI() {
        return A_MI;
    }

    public void setA_MI(double A_MI) {
        this.A_MI = A_MI;
    }

    public double getB_MI() {
        return B_MI;
    }

    public void setB_MI(double B_MI) {
        this.B_MI = B_MI;
    }

    public double getP_MI() {
        return P_MI;
    }

    public void setP_MI(double P_MI) {
        this.P_MI = P_MI;
    }

    public double getA_EI() {
        return A_EI;
    }

    public void setA_EI(double A_EI) {
        this.A_EI = A_EI;
    }

    public double getB_EI() {
        return B_EI;
    }

    public void setB_EI(double B_EI) {
        this.B_EI = B_EI;
    }

    public double getP_EI() {
        return P_EI;
    }

    public void setP_EI(double P_EI) {
        this.P_EI = P_EI;
    }

    public double getDeltaT() {
        return deltaT;
    }

    public void setDeltaT(double deltaT) {
        this.deltaT = deltaT;
    }

    public double getAT_lenta() {
        return AT_lenta;
    }

    public void setAT_lenta(double AT) {
        this.AT_lenta = AT;
    }

    public double getAC_lenta() {
        return AC_lenta;
    }

    public void setAC_lenta(double AC_lenta) {
        this.AC_lenta = AC_lenta;
    }

   
    public double topReleN_NI_lenta(Circuito circ, double iCC){
        return this.AT_lenta*(this.A_NI/(Math.pow((iCC/this.AC_lenta),this.P_NI)-1) + this.B_NI);
    }
    
     public double topReleN_NI_rapida(Circuito circ, double iCC){
        return this.AT_rapida*(this.A_NI/(Math.pow((iCC/this.AC_rapida),this.P_NI)-1) + this.B_NI);
    }
    
    public double topReleN_MI_lenta(Circuito circ, double iCC){
        return this.AT_lenta*(this.A_MI/(Math.pow((iCC/this.AC_lenta),this.P_MI)-1) + this.B_MI);
    }
    
     public double topReleN_MI_rapida(Circuito circ, double iCC){
        return this.AT_rapida*(this.A_MI/(Math.pow((iCC/this.AC_rapida),this.P_MI)-1) + this.B_MI);
    }
    
    
    public double topReleN_EI_lenta(Circuito circ, double iCC){
        return this.AT_lenta*(this.A_EI/(Math.pow((iCC/this.AC_lenta),this.P_EI)-1) + this.B_EI);
    }
    
     public double topReleN_EI_rapida(Circuito circ, double iCC){
        return this.AT_rapida*(this.A_EI/(Math.pow((iCC/this.AC_rapida),this.P_EI)-1) + this.B_EI);
    }
}


