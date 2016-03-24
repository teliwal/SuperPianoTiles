package fr.ups.sim.superpianotiles.util;

/**
 * Created by root on 24/03/16.
 */
public class Tile {
    private int numero;
    private int top;
    private int left;

    public Tile(int numero, int top, int left) {
        this.numero = numero;
        this.top = top;
        this.left = left;
    }

    public int getLeft() {
        return left;
    }

    public int getNumero() {
        return numero;
    }

    public int getTop() {
        return top;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Tile){
            Tile t = (Tile) o;
            return t.getTop()== getTop() && t.getLeft()==getLeft();
        }
        return false;

    }

    public String toString(){
        return String.valueOf(numero);
    }
}
