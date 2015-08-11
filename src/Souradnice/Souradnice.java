package Souradnice;

/**
 * Created with IntelliJ IDEA.
 * User: jiricaga
 * Date: 7/6/13
 * Time: 12:37 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 *  Trida reprezentujici 2d souradnice
 */
public class Souradnice {
    private int x;
    private int y;

    /**
     * Konstruktor, nastavi x a y souradnice na nulu
     */
   public Souradnice()
    {
        x=y=0;
    }

    /**
     * Funkce pro nastavovani souradnic na urcitou hodnotu
     * @param x souradnice x, bereme zleva do prava
     * @param y souradnice y, bereme shora dolu
     */
    public Souradnice(int x,int y)
    {
        this.x = x;
        this.y=y;
    }

    /**
     * Ziska souradnici
     * @return vraci souradnici x
     */
    public int getX(){return x;}

    /**
     * Ziska souradnici
     * @return vraci souradnici y
     */
    public int getY(){return y;}

    /**
     * Nastaveni souradnice na pozadovanou hodnotu(int)
     * @param x souradnice x
     */
    public void setX(int x) {this.x = x;}

    /**
     * Nastaveni souradnice na pozadovanou hodnotu(int)
     * @param y souradnice y
     */
    public void setY(int y){this.y = y;}
}
