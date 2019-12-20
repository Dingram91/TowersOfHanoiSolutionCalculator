package TowersOfHanoiSolver;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class represents a disc that can be drawn to the screen on any of the three pegs.
 *
 * @author Dylan Ingram
 */
public class Disc {

  Rectangle discShape;
  int discNumber;
  int xpos;
  int ypos;
  Pane refPane;

  /**
   * Constructor for the disc class.
   *
   * @param discNumber The size of the disc.
   * @param pane       Reference to the containing plane.
   */
  public Disc(int discNumber, Pane pane) {
    this.refPane = pane;
    this.discNumber = discNumber;
    discShape = new Rectangle(discNumber, 10, Color.BLUE);
    pane.getChildren().add(discShape);
  }


  /**
   * Returns a reference to the Rectangle class so that the disc can be removed from the scene.
   *
   * @return Rectangle reference for the disc.
   */
  public Rectangle getDiscNode() {
    return discShape;
  }

  /**
   * Draw method for rendering the disc on the screen.
   *
   * @param pegNumber     The peg number the disc is on.
   * @param stackPosition The position in the peg stack.
   */
  public void draw(int pegNumber, int stackPosition) {
    // recalculate disc height and width
    discShape.setHeight(refPane.getHeight() / 40);
    discShape.setWidth((refPane.getWidth() / 7) - ((refPane.getWidth() / 86) * discNumber));

    // Find discs x-axis offset
    switch (pegNumber) {
      case 1:
        xpos = refPane.getWidth() == 0.0 ? (int) (210 - (discShape.getWidth() / 2)) : (int) (
            refPane.getWidth() / 4.09523 - (discShape.getWidth() / 2));
        break;
      case 2:
        xpos = refPane.getWidth() == 0.0 ? (int) (430 - (discShape.getWidth() / 2)) : (int) (
            refPane.getWidth() / 2 - (discShape.getWidth() / 2));
        break;
      case 3:
        xpos = refPane.getWidth() == 0.0 ? (int) (650 - (discShape.getWidth() / 2)) : (int) (
            refPane.getWidth() / 1.32307 - (discShape.getWidth() / 2));
        break;
      default:
        break;
    }
    // find discs y-axis offset
    ypos = refPane.getHeight() == 0.0 ? 390 - ((stackPosition - 1) * 15) : (int) (
        refPane.getHeight() - (refPane.getHeight() / 40)
            - ((stackPosition - 1) * refPane.getHeight() / 26.66666));
    // set the discs position
    discShape.setX(xpos);
    discShape.setY(ypos);

  }
}
