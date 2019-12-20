package TowersOfHanoiSolver;

import java.util.LinkedList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This is the peg class that draws itself on constion. Holds a stack of discs that is can add to
 * remove or draw and draw on the screen.
 *
 * @author Dylan Ingram
 */
public class Peg {

  Rectangle shape;
  int xposition;
  LinkedList<Disc> discStack = new LinkedList<>();
  int pegNumber;
  Pane paneRef;

  /**
   * Constructor for the peg class. This constructor sets the peg number and adds the peg to the
   * containing plane.
   *
   * @param pegNumber The pegs positions (1 = left, 2 = mid, 3 = right).
   * @param pane      Reference to the containing plane.
   */
  public Peg(int pegNumber, Pane pane) {
    this.pegNumber = pegNumber;
    this.paneRef = pane;
    shape = new Rectangle(20, 250, Color.rgb(153, 102, 51));
    drawPeg();
    pane.getChildren().add(shape);
  }

  private void drawPeg() {
    switch (this.pegNumber) {
      case 1:
        xposition = paneRef.getWidth() == 0.0 ? 200 : (int) (paneRef.getWidth() / 4.3);
        break;
      case 2:
        xposition = paneRef.getWidth() == 0.0 ? 420 : (int) (paneRef.getWidth() / 2.04762);
        break;
      case 3:
        xposition = paneRef.getWidth() == 0.0 ? 640 : (int) (paneRef.getWidth() / 1.34375);
        break;
      default:
        break;
    }
    int pegHeight = (int) (paneRef.getHeight() / 1.66666);
    int pegWidth = (int) (paneRef.getWidth() / 43);
    shape.setHeight(pegHeight);
    shape.setWidth(pegWidth);
    shape.setY((int) paneRef.getHeight() - pegHeight);
    shape.setX(xposition);
  }

  /**
   * Draws the discs in the disc stack on the screen.
   */
  public void drawDiscs() {
    drawPeg();
    int stackSize = discStack.size();
    for (int i = 0; i < stackSize; i++) {
      discStack.get(i).draw(pegNumber, stackSize - i);
    }
  }

  /**
   * Used to get the top disc from the stack and remove it.
   *
   * @return Disc from the top of the stack.
   */
  public Disc popDisc() {
    Disc poppedDisc = discStack.pop();
    drawDiscs();
    return poppedDisc;
  }

  /**
   * Pushed a disc onto the stack.
   *
   * @param newDisc Disc to be added to the stack.
   * @throws TowerException Thrown if a larger disc is placed on-top of a smaller disc.
   */
  public void pushDisc(Disc newDisc) throws TowerException {
    if (discStack.isEmpty()) {
      discStack.push(newDisc);
    } else if (discStack.get(discStack.size() - 1).discNumber < newDisc.discNumber) {
      discStack.push(newDisc);
    } else {
      throw new TowerException("Cannot stack large disc on a small disc");
    }
    drawDiscs();
  }

  /**
   * Clears the discs from the pegs stack and the containing plane.
   *
   * @param pane Reference to the containing plane.
   */
  public void clearStack(Pane pane) {
    for (Disc disc : discStack) {
      pane.getChildren().remove(disc.getDiscNode());
    }
    discStack.clear();
  }

}
