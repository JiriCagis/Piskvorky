/**
 * Created with IntelliJ IDEA.
 * User: jiricaga
 * Date: 7/6/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
package Piskvorky;
import Souradnice.Souradnice;


/**
 * Trida ktera zaznamena tah
 * Dedi ze tridy souradnice a navic pridava hodnoceni tahu
 */
class Tah extends Souradnice
{
    private int hodnoceni;

    Tah(int x, int y, int hodnoceni)
    {
        super(x,y);
        this.hodnoceni = hodnoceni;
    }
    int getHodnoceni(){return hodnoceni;}
    void setHodnoceni(int hodnoceni){this.hodnoceni = hodnoceni;}
}

/**
 * Trida ve ktere se odehrava hra piskvorky
 * Vyhodnocuje mozne tahy a zapisuje do pole
 */
public class Piskvorky {
    /** rozmer herni mrizky */
    private int N;

    /**
     * promenna pro zaznamenani poctu provedenych tahu
     */
    private int pocet_tahu;

    /**
     * Pocet maximalnich tahu, kdyz uz neni misto v poli pro dalsi tah
     */
    private int maxtahu;

    /**
     * Pole pro ulozeni tahu
     * 0..volne misto, 1..hrac, 2..pocitac
     */
    private int pole [][];

    /**
     * Kontruktor
     * Vytvori hraci pole pozadovane velikosti, nastavi
     * maximalni pocet tahu, vynuluje pocet tahu
     * @param N velikost herni plochy
     */
    public Piskvorky(int N)
    {
        if (N%2 !=0) N--; //osetreni aby byla herni plocha vzdy sudy pocet poli


        this.N = N;
        maxtahu = (N*N);
        pocet_tahu=0;
        pole = new int[N][N];
    }

    /**
     * Konstruktor
     * Vytvori herni pole, z jiz vytvoreneho pole
     * @param pole herni pole
     */
    public Piskvorky(int pole[][])
    {

        this.pole = pole;
        N=pole.length;
        maxtahu = (N*N);
    }


    /**
     * Funkce ktera se pokusi provest dany tah
     * @param x souradnice x, bereme z leva do prava, zaciname od nuly
     * @param y souracnice y, bereme shora dolu, zaciname od nuly
     * @return vraci jestli je tah mozny nebo ne (true, false)
     */
    public boolean Tah(int x, int y)
    {
        if (pocet_tahu >= maxtahu) //osetreni chyby preplneni mrizky
        {
            pole = new int[N][N];
            pocet_tahu=0;
        }

        if (x>=N || x<0 || y>=N || y<0 || pole[x][y]>0)
            return false;

        pole[x][y] = 1;
        pocet_tahu++;
        return true;
    }

    /**
     * Funkce ktera preda herni plochu tride, ktera implementuje umelou inteligenci
     * Umela inteligence vypocte nejlepsi tah v dane situaci
     * @return souradnice nejlepsiho tahu
     */
    public Souradnice Tah_PC()
    {
        if (pocet_tahu >= maxtahu) //osetreni preplneni mrizky
        {
            pole = new int[N][N];
            pocet_tahu=0;
        }

        Souradnice souradnice= null;

        if (pocet_tahu == 0)  //pokud zacina hrat pocitac, vlozi kolecko doprostred mrizky
        {
           souradnice = new Souradnice(N/2,N/2);
        }
        else //v dalsich tazich provede premysleni
        {
            UmelaInteligence UI = new UmelaInteligence(N);
            souradnice = UI.Premyslej(pole);

        }

        pole[souradnice.getX()][souradnice.getY()] = 2;
        pocet_tahu++;
        return souradnice;
    }



    /**
     * Funkce testujici zda dany hrac vyhral (5 znaku vedle sebe), posilaji se souradnice posledniho tahu
     * @param hrac  identifikuje o ktereho hrace jde (1..hrac, 2..pocitac)
     * @param x souradnice posledniho tahu hrace
     * @param y souradnice posledniho tahu hrace
     * @return  vraci jestli dany hrac vyhral nebo ne (true, false)
     */
    public boolean Vyhra(int hrac,int x, int y)
    {
        int pocet = 0;

        for (int opakuj = 0;opakuj<8;opakuj++)
        {
            int ii=0,jj=0;
            if (opakuj%2==0)
                pocet = 0;

            switch (opakuj)
            {
                case 0: ii=1; jj=0; break; //vertikalne
                case 1: ii=-1; jj=0;break;
                case 2: ii=0; jj=1; break;//horizontalne
                case 3: ii=0; jj=-1;break;

                case 4: ii=1; jj=1;  break;
                case 5: ii=-1; jj=-1;break;
                case 6: ii=1; jj=-1; break;
                case 7: ii=-1; jj=1; break;
            }


            int x2 = x;
            int y2 = y;
            while (x2<N && y2<N && x2>=0 && y2>=0 && pole[x2][y2]==hrac)
            {
                pocet++;
                x2+=ii; y2+=jj;
            }

            if (pocet>5)
                return true;

        }


        return false;
    }

    /**
     * Funkce ziska aktualni stav herniho pole
     * @return herni pole
     */
    public int[][] Aktualni_stav()
    {
        //prekopiruje vnitrni pole do pomocneho kvuli zamezeni meneni vnitrniho pole
        int navrat[][] = new int[N][N];

        for (int i=0;i<N;i++)
            for (int j=0;j<N;j++)
                navrat[i][j] = pole[i][j];


        return navrat;
    }

    /**
     * Funkce zobrazi na terminal aktualni stav herniho pole
     * urcena pro testovani v negraficke prostredi
     */
    public void Zobraz()
    {
        System.out.println("Piskvorky aktualni stav: ");
        for (int i=0;i<N;i++)
        {
            for (int j=0;j<N;j++)
                System.out.print(pole[j][i] + " ");

            System.out.println(" ");
        }
    }
}


