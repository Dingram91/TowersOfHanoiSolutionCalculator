package TowersOfHanoiSolver;

import java.util.LinkedList;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * This is the controller for our single scene application.
 *
 * @author Dylan Ingram
 */
public class Controller {

  @FXML
  private Pane pane;

  @FXML
  private Pane uiPane;

  @FXML
  private ChoiceBox<Integer> cbbNumberOfDiscs;

  @FXML
  private Label lblStepsRequired;

  @FXML
  private Label lblCurrentStep;

  @FXML
  private Label lblUserMessage;

  // Array for the pegs
  Peg[] pegs = new Peg[3];

  // Linked list to hold the instructions for solving
  LinkedList<Integer[]> instructions = new LinkedList<>();


  /**
   * This method initializes the scene by loading the combo-box with ints, initializing the three
   * pegs in the scene and setting up a listener to setup the discs whenever the combo-box selection
   * changes.
   */
  public void initialize() {
    for (int i = 1; i <= 10; i++) {
      cbbNumberOfDiscs.getItems().add(i);
    }
    cbbNumberOfDiscs.setOnAction((event -> {
      setupDiscs(cbbNumberOfDiscs.getSelectionModel().getSelectedItem());
    }));

    // setup the pegs
    setupPegs();

    // set user label red and not visible until it is needed
    lblUserMessage.setStyle("-fx-background-color: Orangered");
    lblUserMessage.setVisible(false);


  }

  private void setupPegs() {
    pegs[0] = new Peg(1, pane);
    pegs[1] = new Peg(2, pane);
    pegs[2] = new Peg(3, pane);
  }

  /**
   * This method clears the scene of discs and then adds the desired amount of discs before
   * calculating the solution.
   *
   * @param numberOfDiscs The number of discs to place on teh first peg.
   */
  private void setupDiscs(int numberOfDiscs) {
    // clear all discs from the pegs
    for (Peg peg : pegs) {
      peg.clearStack(pane);
    }
    for (int i = 0; i < numberOfDiscs; i++) {
      try {
        pegs[0].pushDisc(new Disc(i + 1, pane));
      } catch (TowerException e) {
        System.out.println("Error pushing discs");
        break;
      }
    }
    // clear instructions and load new instructions for solving
    instructions.clear();
    towerOfHanoiSolver(numberOfDiscs, 1, 3, 2);

    // Show total steps and reset current step
    lblStepsRequired.setText(String.valueOf(instructions.size()));
    lblCurrentStep.setText(String.valueOf(0));
  }


  /**
   * This is the recursive method for solving the Towers of Hanoi puzzle. This method appends the
   * instructions to the instructions array.
   *
   * @param n        Number of discs.
   * @param fromRod The source peg.
   * @param toRod   The destination peg.
   * @param auxRod  The temp peg to be used.
   * Uses algorithm from https://www.geeksforgeeks.org/c-program-for-tower-of-hanoi/
   *
   */
  private void towerOfHanoiSolver(int n, int fromRod, int toRod, int auxRod) {
    if (n == 1) {
      Integer[] instruction = {fromRod, toRod};
      instructions.add(instruction);
      return;
    }
    towerOfHanoiSolver(n - 1, fromRod, auxRod, toRod);
    Integer[] instruction = {fromRod, toRod};
    instructions.add(instruction);
    towerOfHanoiSolver(n - 1, auxRod, toRod, fromRod);
  }

  /**
   * This method steps through the instructions and makes a move with each click. At each iteration
   * it updates the current step label on the screen.
   *
   * @throws TowerException Thrown if a disc is placed on-top of a smaller disc.
   */
  public void step() throws TowerException {
    if (cbbNumberOfDiscs.getSelectionModel().getSelectedItem() == null) {
      lblUserMessage.setText("Select the number of discs\nto use before stepping.");
      lblUserMessage.setVisible(true);
      fadeOut(lblUserMessage);
      return;
    }
    if (!instructions.isEmpty()) {
      Integer[] instruction = instructions.pop();
      Disc discToMove = pegs[instruction[0] - 1].popDisc();
      pegs[instruction[1] - 1].pushDisc(discToMove);
    } else {
      lblUserMessage
          .setText("End of instructions!\nClick reset or,\nSelect a new number of discs.");
      lblUserMessage.setVisible(true);
      fadeOut(lblUserMessage);
    }
    int currentStep = Integer.parseInt(lblCurrentStep.getText());
    currentStep++;
    lblCurrentStep.setText(String.valueOf(currentStep));
  }

  /**
   * This method fades an object used for showing an error message.
   *
   * @param x object to be faded from the scene.
   */
  private static void fadeOut(Object x) {
    FadeTransition ft = new FadeTransition(Duration.millis(5000), (Node) x);
    ft.setToValue(0);
    ft.setFromValue(1);
    ft.play();
  }

  /**
   * Resets the current step back to 0.
   */
  public void reset() {
    setupDiscs(cbbNumberOfDiscs.getSelectionModel().getSelectedItem());
  }

  /**
   * Redraws some of the components when the screen is resized.
   *
   */
  public void setScreenHeight() {
    for (Peg peg : pegs) {
      peg.drawDiscs();
    }
  }

  /**
   * Sets the screen width for use in resizing the items on screen.
   *
   * @param screenWith the width of the screen.
   */
  public void setScreenWith(double screenWith) {
    uiPane.setLayoutX(screenWith - uiPane.getWidth());
    for (Peg peg : pegs) {
      peg.drawDiscs();
    }
  }
}
