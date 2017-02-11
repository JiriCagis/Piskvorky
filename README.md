<h1> Piskvorky </H1>
Piskvorky is a strategic game for two players. In our case human and computer. Players sparingly draw symbolrs on a grid. Symbols are a barrow and a cross. Player which as first create series of five his symnols win the game. Series can be a vertical, a horizontal or an obliquely.
<br/>
<a href="http://www.piskvorky.cz/federace/oficialni-pravidla-piskvorek/">Official rules Czech game Piskorky</a>

<p align="center">
<img src="https://github.com/JiriCagis/Piskvorky/blob/master/piskvorky_dark.jpg" height="300" width="300"/> ...
<img src="https://github.com/JiriCagis/Piskvorky/blob/master/piskvorky_white.jpg"  height="300" width="300"/>
</p>
<br>

<H3> Description intelligence </H3>
Intelligence calculates best move on base actually situation on the grid. It use algorithm Minimax. The algorithm calculate a best move on two parts, first get a best move for a defense and second get a best move for a attack. When the best move for attack is greater than the best move for defence, it return the best for attack else return the best move for defense. Best moves (attack and defense) are obtain pass all blank fields and save field with max value after calculate score according situation around blank space.

<H3>Main functions</H3>
<ul>
	<li>You can play againts a computer</li>
	<li>When you perform confuse click on board, you can move back</li>
	<li>Save game before the close application and return back after reopen the application</li>
	<li>A window is dynamic stretchable</li>
	<li>Aplication remember your favorite theme, position and size the window.</li>
</ul>

<H3>Technologie</H3>
- Programming language; Java 7
- Graphical interface: Swing, draw on Canvas
- Persist data: XML format
- Unit testing: JUnit
