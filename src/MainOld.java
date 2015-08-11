import Piskvorky.Piskvorky;
import Souradnice.Souradnice;

import java.awt.*;
import java.util.Stack;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

/**
 * Trida ktera slouzi k nastaveni a ovladani okna
 */
class Okno extends JFrame{
    /** Aktualni stav krizku a kolecek na herni plose */
    int[][] aktualni_stav;
    /** Zasobnik pro ulozeni predchozich tahu */
    Stack<int [][]> zasobnik;

    /**konstanta velikost hraciho pole */
    int konstanta = 20;

    /** Objekt piskvorky, kteremu se budou odesilat tahy a on je vyhodnoti */
    Piskvorky piskvorky;

    JMenuBar menuBar;
    Platno herni_scena;
    Panel panel;

    JMenuItem svetly;
    JMenuItem tmavy;
    JMenuItem festival;

    /**
     * Konstruktor tridy
     * Nastavi okno a rozmisti objekty v okne
     */
    public Okno()
    {
       setTitle("Piskvorky 1.0");
       setLayout(new BorderLayout());

       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       Dimension rozmery = new Dimension(400,468);
       setSize(rozmery);
       setLocationRelativeTo(null);
       setResizable(false);


       /**jadro programu */
       piskvorky = new Piskvorky(konstanta);
       zasobnik = new Stack();
       aktualni_stav = piskvorky.Aktualni_stav();

       /**Vlozi menu */
        Menu();

       /**herni scena*/
       herni_scena = new Platno();
       add (herni_scena, BorderLayout.CENTER);
       herni_scena.addMouseListener(new Mys());


       /**spodni radek */
       panel = new Panel();
       add(panel,BorderLayout.SOUTH);

        setVisible(true);

    }


    /**
     * Funkce ktera vytvori menu
     * Nastavi posluchace na polozky v menu
     */
    void Menu()
    {
       menuBar = new JMenuBar();
       menuBar.setPreferredSize(new Dimension(this.getWidth(),20));
        this.add(menuBar,BorderLayout.NORTH);

       JMenu hra = new JMenu("Hra");
        JMenuItem novahra = new JMenuItem("Nova hra");
        JMenuItem tahzpet = new JMenuItem("Tah zpet");
        JMenuItem konec = new JMenuItem("Konec");

        hra.add(novahra);
        hra.add(tahzpet);
        hra.add(new JSeparator());
        hra.add(konec);
       menuBar.add(hra);

       JMenu vzhled = new JMenu("Vzhled");
         svetly= new JMenuItem("Svetly");
         tmavy= new JMenuItem("Tmavy");
         festival= new JMenuItem("Festival");

        vzhled.add(svetly);
        vzhled.add(tmavy);
        vzhled.add(festival);
       menuBar.add(vzhled);


       JMenu napoveda = new JMenu("Napoveda");
        JMenuItem ovladani = new JMenuItem("Ovladani");
        JMenuItem pravidla = new JMenuItem("Pravidla");
        JMenuItem oprogramu = new JMenuItem("O programu");
        napoveda.add(ovladani);
        napoveda.add(pravidla);
        napoveda.add(oprogramu);
       menuBar.add(napoveda);

       /**Po klepnuti na nova hra se spusti nova hra */
        novahra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                    Nova_hra();
            }
        });

        /** Po klepnuti na tah zpet, se vybere tah ze zasobniku a nastavi
         *  jako aktualni stav, nasledne se prekresli okno
         */
        tahzpet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!zasobnik.isEmpty())
                {
                    aktualni_stav = zasobnik.pop();
                    piskvorky = new Piskvorky(aktualni_stav);
                    repaint();
                }
            }
        });

        /** Po klepnuti na konec, se program korektne ukonci
         *
         * */
        konec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);

            }
        });

        /**
         * Trida ktera implementuje vychozi posluchac pro vsechny
         * polozky v nabidce vzhled
         */
       class Vzhled implements ActionListener{
           private Color vychozi = Color.white;
           private Color oznacena = Color.LIGHT_GRAY;

           /** Konstruktor nastavi vychozi nastaveni na svetly vzhled */
           Vzhled()
           {
               svetly.setBackground(oznacena);
           }

           /**
            * Vychozi posluchac pro vsechny moznosti vzhledu,
            * podle toho ktera se vybere tak se nastavi barevne schema
            * @param actionEvent  udalost klepnuti mysi
            */
           @Override
           public void actionPerformed(ActionEvent actionEvent) {

               tmavy.setBackground(vychozi);
               svetly.setBackground(vychozi);
               festival.setBackground(vychozi);

               if (actionEvent.getSource() == svetly)
               {
                   svetly.setBackground(oznacena);
                   herni_scena.SetColor(Color.red,Color.blue,Color.gray,Color.white);
               }
                else
               if (actionEvent.getSource() == tmavy)
               {
                   tmavy.setBackground(oznacena);
                   herni_scena.SetColor(Color.red,Color.green,Color.gray,Color.darkGray);
               }
                    else
               if (actionEvent.getSource() == festival)
               {
                   festival.setBackground(oznacena);
                   herni_scena.SetColor(Color.gray,Color.white,Color.yellow,Color.ORANGE);
               }

               repaint();
            }
       }

        /** Registrovani tridy Vzhled jako posluchace pro jednotlive moznosti vzhledu */
        tmavy.addActionListener(new Vzhled());
        svetly.addActionListener(new Vzhled());
        festival.addActionListener(new Vzhled());


        /**
         * Trida ktera implementuje vychozi posluchac pro vsechny
         * polozky v nabidce napoveda
         */
        class NapovedaLister implements ActionListener{

            /**
             * Posluchac ktery posloucha kdyz se klepne na polozku v nabidce menu
             * Pote se rozhodne jake menu ma spustit a otevre prislusny soubor
             * @param actionEvent udalost klepnuti na polozku v menu
             */
            public void actionPerformed(ActionEvent actionEvent) {
                String text;
                String nazev = new String("");
                String soubor = new String("");
                Object o = actionEvent.getActionCommand();

               // InputStream in= getClass().getResourceAsStream("/ovladani.txt");
                if (o == "Ovladani")
                {

                    nazev = new String("Ovladani");
                    soubor = new String("/Ovladani.txt");
                }

                if (o == "Pravidla")
                {
                    nazev = new String("Pravidla");
                    soubor = new String("/Pravidla.txt");
                }

                if (o == "O programu")
                {
                    nazev = new String("O programu");
                    soubor = new String("/Oprogramu.txt");
                }

                try{

                    InputStream in = getClass().getResourceAsStream(soubor);
                    BufferedReader ctecka2 = new BufferedReader(new InputStreamReader(in));

                    //FileReader ctecka = new FileReader(in.toString());
                    //BufferedReader ctecka2 = new BufferedReader(ctecka);


                    text = ctecka2.readLine();
                    new Napoveda(nazev,text);
                }
                catch (Exception e)
                {
                   new Napoveda("Chyba","Omlouvame se za neprijemnost, soubor s napovedou nelze nacist.");
                }

            }
        }

        /** Registrovani tridy NapovedaLister pro polozky Napovedy */
        ovladani.addActionListener(new NapovedaLister());
        pravidla.addActionListener(new NapovedaLister());
        oprogramu.addActionListener(new NapovedaLister());
        }

    /**
     * Funkce spusti znova hru od zacatku
     * Vytvori nove pole, novy zasobnik
     * prekresli obrazovku
     */
    void Nova_hra()
    {
        piskvorky = new Piskvorky(konstanta);
        zasobnik = new Stack<int[][]>();
        panel.Pozdrav();
        repaint();
    }

    /**
     * Trida ktera snima souradnice mysi po klepnuti a
     * posle je objektu piskvorky
     */
    class Mys extends MouseAdapter{
        boolean uloz_do_zasobniku = true;

        /**
         *  Funkce snima klepnuti mysi, pak to klepnuti
         *  vyhodnoti o jake policko v hracim poli se jedna
         *  a posle objektu piskvorky, kde se bud provede tah a pak
         *  zahraje i protitah pocitac, nebo ne pokud to pole je osazene
         * @param mouseEvent klepnuti mysi
         */
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            int x = mouseEvent.getX();
            int y = mouseEvent.getY();

            while (x%konstanta==0) x--;
            while (y%konstanta==0) y--;

            x/=konstanta;
            y/=konstanta;

            /** ulozi do zasobniku pro pripadne vraceni tahu */
            if (uloz_do_zasobniku)
            {
                zasobnik.push(piskvorky.Aktualni_stav());
                uloz_do_zasobniku = false;
            }

            if (piskvorky.Tah(x,y))
            {
                herni_scena.repaint(x*konstanta,y*konstanta,konstanta,konstanta);

                if (piskvorky.Vyhra(1,x,y))
                {
                    JOptionPane.showMessageDialog(new JFrame(),"Blahopreji, vyhral si.","Konec hry",JOptionPane.WARNING_MESSAGE);
                    Nova_hra();

                }



                Souradnice souradnicePC = piskvorky.Tah_PC();

                x = souradnicePC.getX();
                y = souradnicePC.getY();

                panel.Zobraz_souradnice(x,y);
                herni_scena.repaint(x*konstanta,y*konstanta,konstanta,konstanta);

                if (piskvorky.Vyhra(2,x,y))
                {
                    JOptionPane.showMessageDialog(new JFrame(),"Prohral si!!!","Konec hry",JOptionPane.ERROR_MESSAGE);
                    Nova_hra();
                }



                uloz_do_zasobniku = true;
            }
        }

    }

    /**
     * Trida, ktera slouzi jako herni plocha
     * Vykresli mrizku podle nastavene konstanty
     * a do mrizky kresli kolecka nebo krizky podle
     * stavu ve hre
     */
    public class Platno extends JPanel{
        Color barva_mrizka;
        Color barva_krizku;
        Color barva_kolecka;
        Color barva_pozadi;

        /**
         * Konstruktor, nastavi vychozi barvy a rozmer
         */
        Platno()
        {
            barva_mrizka = Color.gray;
            barva_kolecka = Color.red;
            barva_krizku = Color.blue;
            barva_pozadi = Color.white;

            Dimension rozmer = new Dimension(400,400);
            setPreferredSize(rozmer);
            setSize(rozmer);
        }

        /**
         * Vykresli na platno mrizku s krizky a kolecky
         * @param g graficky kontext
         */
        @Override
        public void paint(Graphics g) {
            //pozadi
            g.setColor(barva_pozadi);
            g.fillRect(0,0,400,400);
            //vykresleni mrizky
            g.setColor(barva_mrizka);
            for (int i=0;i<konstanta;i++)
            {   int posuv = i*konstanta;
                g.drawLine(posuv,0,posuv,400);
                g.drawLine(0,posuv,400,posuv);
            }

            int pole [][] = piskvorky.Aktualni_stav();

            for (int i=0;i<pole.length;i++)
                for (int j=0;j<pole.length;j++)
                {
                    switch (pole[i][j])
                    {
                        case 1:
                            g.setColor(barva_krizku);
                            g.drawLine(i*konstanta,j*konstanta,i*konstanta+konstanta,j*konstanta+konstanta);
                            g.drawLine(i*konstanta,j*konstanta+konstanta,i*konstanta+konstanta,j*konstanta);
                            break;
                        case 2:
                            g.setColor(barva_kolecka);
                            g.drawOval(i * konstanta, j * konstanta, konstanta, konstanta);
                            break;
                    }
                }


        }


        /**
         * Nastavuje barvy
         * @param kolecko barva kolecka
         * @param krizek barva krizku
         * @param mrizka barva mrizky
         * @param pozadi barva pozadi
         */
        void SetColor(Color kolecko,Color krizek,Color mrizka, Color pozadi)
        {
            barva_kolecka = kolecko;
            barva_krizku = krizek;
            barva_mrizka = mrizka;
            barva_pozadi = pozadi;
        }




    }

    /**
     * Trida Panel slouzi pro zobrazovani aktualniho deni ve hre
     */
    class Panel extends JPanel
    {
        private JLabel popisek;

        /**
         * Konstruktor
         * Vytvori label popisek, naplniho pozdravem a prida do panelu
         */
        Panel()
        {

            setBackground(Color.gray);
            popisek = new JLabel();
            Pozdrav();
            add(popisek);


        }

        /**
         * Nastavi pozdrav v labelu popisek
         */
        void Pozdrav()
        {
           popisek.setText("Ahoj, zacni hrat piskvorky !!!");
        }

        /**
         * Nastavi v labelu popisek aktualni souradnice tahu
         * @param x souradnice x, brane od leva do prava. zaciname nulou
         * @param y souradnice y, brane od shora dolu, zaciname nulou
         */
        void Zobraz_souradnice(int x, int y)
        {
            popisek.setText("Pocitac hral na souradnice: " + x + ":" + y);
        }
    }

    /**
     * Trida napoveda vytvari jednotny styl pro vsechny napovedy
     */
    class Napoveda {
        private String nazev;
        private String text;
        private JLabel nadpis;
        private JTextArea textove_pole;
        private JButton tlacitko;
        private JDialog okenko;

        /**
         * Konstruktor rozmisti prvky v okne a nastavi text a nazev
         * @param nazev titulek napovedy (teme)
         * @param text  vlastni text napovedy
         */
        Napoveda(String nazev, String text)
        {
             okenko = new JDialog(new JDialog(),nazev);
             this.nazev = nazev;
             this.text = text;


             Dimension rozmer = new Dimension(200,400);
             okenko.setSize(rozmer);
             okenko.setMaximumSize(rozmer);
             okenko.setResizable(false);
             okenko.setLayout(new BorderLayout());
             okenko.setLocationRelativeTo(null);
             okenko.setBackground(Color.white);

             nadpis = new JLabel(nazev);
             nadpis.setFont(new Font("SansSerif",Font.ITALIC,20));
             nadpis.setForeground(Color.BLUE);
             okenko.add(nadpis, BorderLayout.NORTH);

             textove_pole = new JTextArea(text);
             textove_pole.setEditable(false);

             textove_pole.setLineWrap(true);
             textove_pole.setWrapStyleWord(true);
             okenko.add(textove_pole, BorderLayout.CENTER);

             tlacitko = new JButton("OK");
             okenko.add(tlacitko,BorderLayout.SOUTH);
            tlacitko.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent actionEvent) {
                     okenko.dispose();
                 }
             });

            okenko.setVisible(true);
        }

    }
}


/**
 *   Trida pro spousteni celeho programu
 */
public class MainOld {

    /**
     * Vytvori nove okno s hrou piskvorky
     * @param args parametry prikazove radky (terminalu)
     */
    public  static void main(String[] args) {
        new Okno();




    }


}

