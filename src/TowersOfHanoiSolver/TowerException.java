package TowersOfHanoiSolver;

/**
 * This is a custom exceptions to be thrown is a larger disc is placed on a smaller one.
 *
 * @author Dylan Ingram
 */
public class TowerException extends Exception {

  public TowerException(String message) {
    super(message);
  }
}
