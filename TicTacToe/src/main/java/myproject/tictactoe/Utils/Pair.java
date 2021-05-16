/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.tictactoe.Utils;

/**
 *
 * @author son
 */
public class Pair<X, Y> extends Object{
    private X x;
    private Y y;

    public Pair(){

    }

    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getFirst() {
        return x;
    }

    public void setFirst(X x) {
        this.x = x;
    }

    public Y getSecond() {
        return y;
    }

    public void setSecond(Y y) {
        this.y = y;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pair) {
            Pair other = (Pair) obj;
            
            if(other.getFirst().equals(this.getFirst()) 
                    && other.getSecond().equals(this.getSecond())){
                return true;
            }
        }
        return false;
    }
    
    public String toString(){
        return this.getFirst() + " " + this.getSecond();
    }
    
    
    
}
