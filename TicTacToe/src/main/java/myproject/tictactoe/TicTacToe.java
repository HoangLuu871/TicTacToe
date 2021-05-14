package myproject.tictactoe;

import java.awt.Color;
import javax.swing.JButton;
import static myproject.tictactoe.UIGame.game;

public class TicTacToe {

    public int COUNT = 0;
    public int width = 16, height = 16, turn = 0; //size of board
    public JButton box[][] = new JButton[height][width];
    public String chatString = ""; //save all chat in in string and show in chatField
    int pos[][] = new int[height][width];

    /**
     *
     * @param x coordinate of box
     * @param y coordinate of box
     * @param boxName (X or O)
     * @return true if win
     */
    public boolean checkWin(int x, int y, String boxName) {
        int i, j;
        int d = 0;
        //check vertical
        for (i = -4; i <= 4; i++) {
            if (x + i >= 0 && x + i < height) {
                if (box[x + i][y].getText() == boxName) {
                    d++;
                } else if (d < 5) {
                    d = 0;
                }
            }
        }
        if (d >= 5) {
            for (i = -4; i <= 4; i++) {
                if (x + i >= 0 && x + i < height) {
                    if (box[x + i][y].getText() == boxName) {
                        box[x + i][y].setBackground(Color.yellow);
                    }
                }
            }
            return true;
        } else {
            d = 0;
        }
        //check horizontal
        for (i = -4; i <= 4; i++) {
            if (y + i >= 0 && y + i < width) {
                if (box[x][y + i].getText() == boxName) {
                    d++;
                } else if (d < 5) {
                    d = 0;
                }
            }
        }
        if (d >= 5) {
            for (i = -4; i <= 4; i++) {
                if (y + i >= 0 && y + i < height) {
                    if (box[x][y + i].getText() == boxName) {
                        box[x][y + i].setBackground(Color.yellow);
                    }
                }
            }
            return true;
        } else {
            d = 0;
        }
        //check diagonal
        for (i = -4, j = 4; i <= 4 && j >= -4; i++, j--) {
            if (y + i >= 0 && y + i < width && x + j >= 0 && x + j < height) {
                if (box[x + j][y + i].getText() == boxName) {
                    d++;
                } else if (d < 5) {
                    d = 0;
                }
            }
        }
        if (d >= 5) {
            for (i = -4, j = 4; i <= 4 && j >= -4; i++, j--) {
                if (y + i >= 0 && y + i < width && x + j >= 0 && x + j < height) {
                    if (box[x + j][y + i].getText() == boxName) {
                        box[x + j][y + i].setBackground(Color.yellow);
                    }
                }
            }
            return true;
        } else {
            d = 0;
        }
        for (i = -4; i <= 4; i++) {
            if (x + i >= 0 && x + i < width && y + i >= 0 && y + i < height) {
                if (box[x + i][y + i].getText() == boxName) {
                    d++;
                } else if (d < 5) {
                    d = 0;
                }
            }
        }
        if (d >= 5) {
            for (i = -4; i <= 4; i++) {
                if (x + i >= 0 && x + i < width && y + i >= 0 && y + i < height) {
                    if (box[x + i][y + i].getText() == boxName) {
                        box[x + i][y + i].setBackground(Color.yellow);
                    }
                }
            }
            return true;
        } else {
            d = 0;
        }
        return false;
    }

    public void clearMap() {
        for (int i1 = 0; i1 < game.height; i1++) {
            for (int j1 = 0; j1 < game.width; j1++) {
                this.box[i1][j1].setText("");
                this.box[i1][j1].setBackground(Color.white);
            }
        }
    }

}
