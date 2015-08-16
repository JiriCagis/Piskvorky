<h1> Piskvorky </H1>
Piskvorky is strategic game for two players. In this case human and computer. 
Players sparingly draw symbols on grid. Symbols are barrow and cross. 
Player which as first create series of five his symbols win game. Series can
be vertical,horizontal or obliquely.

Official rules Czech game Piskorky:
- http://www.piskvorky.cz/federace/oficialni-pravidla-piskvorek/

<H3> Description intelligence </H3>
Calculate best move on base actually situation on grid.
Using algorithm Minimax. Algorithm calculate best move on
two parts, first get best move for defense and second get best
move for attack. When attack best have greater evaluate return
best move for attack else return best move for defense.
Best moves(attack and defense) are obtain passing all 
blank field and save field with max value after calculate
number according situation around blank space.

<H3>Main function</H3>
- You can play against computer
- When you confuse with click on boad, you can move back
- Save game after close application and return game with again open application
- Window is dynamic stretchable
- Remember your favourite theme, position and size window

<H3>Technologie</H3>
- Programming language; Java 7
- Graphical interface: Swing, draw on Canvas
- Persist data: XML format
