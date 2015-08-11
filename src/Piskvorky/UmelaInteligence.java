package Piskvorky;
import Souradnice.Souradnice;

/**
 * Created with IntelliJ IDEA.
 * User: jiricaga
 * Date: 7/6/13
 * Time: 12:09 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Trida, ktera slouzi pro vyhodnocovani nejlepsiho tahu ve hre piskvorky
 */
public class UmelaInteligence {
    /** Nejlepsi tah pro utok */
    private Tah utok;

    /** Nejlepsi tah pro obranu */
    private Tah obrana;

    /** Velikost herni plochy */
    private int N;

    /**
     * Konstruktor nastavi velikost herni plochy
     * @param N velikost herni plochy
     */
    UmelaInteligence(int N)
    {
        utok = obrana =null;
        this.N = N;

    }

    /**
     * Funkce ktera,ktera ohodnoti dane herni pole pro utok a pro obranu
     * a vybere ty souradnice, ktere maji vetsi hodnoceni
     * @param pole  vstupni herni pole
     * @return souradnice nejlepsiho tahu
     */
    Souradnice Premyslej (int pole[][])
    {
        //vybere nejlepsi tah pro obranu
        obrana = Ohodnot(pole,1);

        //vybere nejlepsi tah pro utok
        utok = Ohodnot(pole,2);

        //rozhodovani mezi utokem a obranou
        Souradnice souradnice;
        if (utok.getHodnoceni()>=obrana.getHodnoceni())
            souradnice= new Souradnice(utok.getX(),utok.getY());
        else
            souradnice= new Souradnice(obrana.getX(),obrana.getY());


        return souradnice;
    }

    /**
     * Funkce ktera vraci nejlepsi mozny tah, pro dany mod
     * Ohodnocovani provadi paralelne ve vice vlaknech,
     * pole se rozdeli na ctyri zony a v techto zonach se najdou nejlepsi tahy,
     * pote se porovnaji ty ctyri nejlepsi tahy a vybere se ten s nejvetsim hodnocenim
     *
     * @param pole herni pole
     * @param mod mame dva mody: 1..obrana, 2..utok
     * @return  nejlepsi tah
     */
    Tah Ohodnot (int pole[][],int mod)
    {
        Vlakna vlakno [];

        vlakno = new Vlakna[4];

        //nastaveni pracovnich ploch pro vlakna
        int pulka = N/2;
        for (int i=0;i<vlakno.length;i++)
        {
            switch (i)
            {
                case 0: vlakno[0] = new Vlakna("Vlakno1",0,pulka,0,pulka);
                case 1: vlakno[1] = new Vlakna("Vlakno2",pulka,N,0,pulka);
                case 2: vlakno[2] = new Vlakna("Vlakno3",0,pulka,pulka,N);
                case 3: vlakno[3] = new Vlakna("Vlakno4",pulka,N,pulka,N);
            }
        }

        //vybere nejlepsi tah pro dany mod
        for (int i=0;i<vlakno.length;i++)
            vlakno[i].Ohodnot(pole,mod);

        //ceka na skonceni vsech vlaken
        while (vlakno[0].vlakno.isAlive() || vlakno[1].vlakno.isAlive() || vlakno[2].vlakno.isAlive() || vlakno[3].vlakno.isAlive());

        Tah nejlepsi_tah = vlakno[0].getNejlepsi_tah();

        for (int i=1;i<vlakno.length;i++)
        {
            Tah pom = vlakno[i].getNejlepsi_tah();

            if (pom.getHodnoceni() > nejlepsi_tah.getHodnoceni())
                nejlepsi_tah =vlakno[i].getNejlepsi_tah();
        }



        return nejlepsi_tah;
    }

}


/**
 * Trida ktera provadi vypocet nejlepsiho tahu
 * Dedi z Runnable, takze muze byt spoustena vicekrat
 */
class Vlakna implements Runnable{
    private Tah nejlepsi_tah;
    Thread vlakno;

    /** Souradnice omezujici pouze na urcity usek prohledavani v poli */
    int x1,x2,y1,y2;

    /** herni pole */
    int pole[][];

    /** mod v kterem se hleda, 1.. obrana 2..utok */
    int mod;

    /** velikost hraciho pole */
    int N;


    /**
     * Konstruktor
     * Nastavi jmeno vlakna a jeho vysec ve ktere bude provadet vypocet
     * @param jmeno jmeno vlakna
     * @param x1 souradnice vysece
     * @param x2 souradnice vysece
     * @param y1 souradnice vysece
     * @param y2 souradnice vysece
     */
    Vlakna(String jmeno,int x1, int x2,int y1,int y2)
    {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;



        nejlepsi_tah= null;
        pole = null;
        int N =0;
        mod = 0;
        vlakno = new Thread(this,jmeno);
    }

    /**
     * Funkce vraci nejlepsi tah
     * @return nejlepsi tah
     */
    Tah getNejlepsi_tah() {return nejlepsi_tah;}


    /**
     * Funkce pro ohodnoceni hraciho pole v danem modu
     * Spousti vypocetni vlakno
     * @param pole  hraci pole
     * @param mod mod: 1.. obranna, zkouma hracovy znaky, 2.. utok zkouma svoje znaky
     */
    public void Ohodnot(int pole[][],int mod)
    {
        this.pole = pole;
        this.mod = mod;
        this.N = pole.length;
        vlakno.start();
    }

    /**
     * Beh vlakna
     */
    public void run()
    {
        for (int x=x1;x<x2;x++)
            for (int y=y1;y<y2;y++)
            {
                int hodnoceni;
                if (pole[x][y] == 0)
                {
                    hodnoceni = Vyvypocet(x,y);

                    if (nejlepsi_tah == null)
                        nejlepsi_tah = new Tah(x,y,hodnoceni);
                    else
                    if (hodnoceni > nejlepsi_tah.getHodnoceni())
                        nejlepsi_tah = new Tah(x,y,hodnoceni);

                    //okamzite ukonceni a vraceni nejvetsi hodnoty, kdyz nalezne 4 vedle sebe
                    if (hodnoceni == 99999999)
                        break;
                }
            }
    }

    /**
     * Funkce vypocte pro danou pozici hodnoceni
     * Projde od souradnic (x,y) vsemi smery,(doprava, doleva, nahoru, dolu, zesikma, zesikma
     * kazdy smer si ohodnoti, pokud je prvni znak znak ktery hledame pricteme k vypoctu trojku,
     * pokud je to prvni volne misto tak 1. tak to projdeme max do delky 5, zleve i zprava pro dany
     * smer.
     *
     * Vypoctene hodnoty pro jednotlive smery seradime podle velikosti a spojime do hromady a vracime jedno cislo
     * @param x souradnice x stredu, odkud budu prohledavat
     * @param y souradnice y stredu, odkud budu prohledavat
     * @return hodnoty pro jednotlive smery, serazene a vracene jako jedno cislo
     */
    private int Vyvypocet(int x, int y)
    {
        int vypocet[] = new int[4];
        int ukazatel = 0;
        int pocet = 0;
        
        for (int opakuj = 0;opakuj<8;opakuj++)
        {
            int ii=0,jj=0;
            if (opakuj>0 && opakuj%2==0)
            {
                if (pocet<4)
                    vypocet[ukazatel]=0;

                pocet=0;
                ukazatel++;
            }

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


            int pocet_na_ctyry = 0; //kriticky,tah pri vyskytu 4 znaku vedle sebe, vraci 9999...
            int priznak = 3;
            boolean prvni_bile_misto = true;

            for (int krok=0;krok<4;krok++)
            {
                x2+=ii; y2+=jj;

                if (x2>=N || y2>=N || x2<0 || y2<0)
                    break;

                if (mod == 1 && pole[x2][y2]==2)
                    break;

                if (mod == 2 && pole[x2][y2]==1)
                    break;


                if (pole[x2][y2]==mod)
                {
                    vypocet[ukazatel] = vypocet[ukazatel] + priznak;
                    if (priznak==3)
                    {
                        pocet_na_ctyry++;
                        if (pocet_na_ctyry==4)
                            return 99999999;  //vyskyt 4 vedle sebe nutno rychle zareagovat, utokem nebo obranou
                    }

                }

                if (pole[x2][y2]==0 && prvni_bile_misto)
                {
                    vypocet[ukazatel] = vypocet[ukazatel] + 1;
                    priznak--;
                    prvni_bile_misto = false;
                }



                pocet++;


            }
        }

        //serazeni pole od nejvetsiho
        boolean pokracuj = true;
        while (pokracuj)
        {
            pokracuj = false;

            for (int i=0;i<vypocet.length-1;i++)
                if (vypocet[i]>vypocet[i+1])
                {
                    int pom = vypocet[i];
                    vypocet[i] = vypocet[i+1];
                    vypocet[i+1] = pom;
                    pokracuj = true;
                }

        }

        //sestaveni navratove hodnoty s serazeneho pole
        return  1000000*vypocet[3] + 10000*vypocet[2] + 100*vypocet[1] + vypocet[0];
    }
}
