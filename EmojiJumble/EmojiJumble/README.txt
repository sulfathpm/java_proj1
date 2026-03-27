============================================
  🧩 EMOJI JUMBLE - Java Swing Game
============================================

HOW TO COMPILE & RUN:
----------------------
1. Make sure you have Java JDK 8+ installed
2. Open terminal/command prompt in this folder
3. Compile:
     javac src/*.java -d out
4. Run:
     java -cp out Main

Or using a single command:
     javac src/*.java -d out && java -cp out Main

IDE (IntelliJ / Eclipse / NetBeans):
-------------------------------------
1. Create a new Java project
2. Copy all .java files from src/ into your project src folder
3. Run Main.java

============================================
  GAME FEATURES
============================================
🏠 Home Screen    - Rules & Start button
🎮 Game Screen    - 8 emoji puzzles to solve
🏆 Result Screen  - Score, accuracy & medal

SCORING:
  ✅ Correct answer   = 2 pts
  💡 Correct w/ hint  = 1 pt
  ❌ Wrong answer     = 0 pts

HOW TO PLAY:
  1. Tap emojis in the bottom panel to arrange them
  2. Tap placed emojis to remove them back
  3. Use Reset to start over, Hint for a clue
  4. Press Submit to check your answer

============================================
  FILE STRUCTURE
============================================
src/
  Main.java         - Entry point
  Puzzle.java       - All 8 puzzle data
  Theme.java        - Colors & fonts
  StyledButton.java - Custom button UI
  RoundPanel.java   - Rounded dark panels
  HomeScreen.java   - Welcome screen
  GameScreen.java   - Main game screen
  ResultScreen.java - Final score screen
