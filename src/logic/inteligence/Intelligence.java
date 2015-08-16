package logic.inteligence;

import java.awt.Point;

/**
 * Intelligence for Czech game Piskvorky.
 * Calculate best move on base actually situation on grid.
 * Using algorithm Minimax. Algorithm calculate best move on
 * two parts, first get best move for defense and second get best
 * move for attack. When attack best have greater evaluate return
 * best move for attack else return best move for defense.
 * Best moves(attack and defense) are obtain passing all 
 * blank field and save field with max value after calculate
 * number according situation around blank space.
 * @author adminuser
 */
public class Intelligence {
    
    private Move bestMoveAttack;
    private Move bestMoveDefence;
   
    private final int COUNT_ROWS;
    private final int COUNT_COLUMNS;
    private final int COMPUTER_PLAYER;
    private final int HUMAN_PLAYER;
    private final int BLANK_SPACE;

    public Intelligence(int COUNT_ROWS, int COUNT_COLUMNS, int COMPUTER_PLAYER, int HUMAN_PLAYER, int BLANK_SPACE) {
        this.COUNT_ROWS = COUNT_ROWS;
        this.COUNT_COLUMNS = COUNT_COLUMNS;
        this.COMPUTER_PLAYER = COMPUTER_PLAYER;
        this.HUMAN_PLAYER = HUMAN_PLAYER;
        this.BLANK_SPACE = BLANK_SPACE;
    }
    
    public Point getBestMove(int array[][]){
        bestMoveDefence = evaluate(array,HUMAN_PLAYER);
        bestMoveAttack = evaluate(array,COMPUTER_PLAYER);
        if(bestMoveAttack.getEvaluate()>=bestMoveDefence.getEvaluate())
            return new Point(bestMoveAttack.x,bestMoveAttack.y);
        else
            return new Point(bestMoveDefence.x,bestMoveDefence.y);
    }
    
    private Move evaluate(int array[][], int player){
        Move bestMove = null;
        for(int row=0;row<COUNT_ROWS;row++)
            for(int column=0;column<COUNT_COLUMNS;column++)
            {
                if(array[row][column] == BLANK_SPACE)
                {
                    int evaluate = calculateEvaluate(row,column,array,player);
                    if(bestMove==null || evaluate>bestMove.getEvaluate())
                        bestMove = new Move(evaluate, row, column);
                    
                    //okamzite ukonceni a vraceni nejvetsi hodnoty, kdyz nalezne 4 vedle sebe
                    if (evaluate == 99999999)
                        break;
                }
            }
        return bestMove;
    }

    
    /*
    !!!!
    NOT REFACTORED OLD PART OF Piskovorky version 1.2
    !!!!
    */
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
     * @param mod 1.. pro obranu, 2.. pro utok
     * @return hodnoty pro jednotlive smery, serazene a vracene jako jedno cislo
     */
    private int calculateEvaluate(int x, int y,int pole[][], int mod)
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

                if (x2>=COUNT_ROWS || y2>=COUNT_COLUMNS || x2<0 || y2<0)
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
