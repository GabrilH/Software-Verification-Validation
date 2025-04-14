package slides.dicegame;

import static org.mockito.Mockito.*;
import org.mockito.*;

import org.junit.jupiter.api.*;

import java.util.Random;

/**
 * Game testing in which only class Bet is integrated.
 * We use mocks for Player and Dice.
 * 
 * @author Eduardo Marques, DI/FCUL, 2013/14.
 * 
 * TROUBLESHOOT:
 *
 * If this error occurs: "Mockito cannot mock this class: class java.util.Random"
 * try add this to the VM args (menu Run | Run Configurations | tab arguments | VM arguments) 
 *   --add-exports=java.base/jdk.internal.util.random=ALL-UNNAMED
 * ref: https://github.com/mockito/mockito/issues/2560
 * 
 * Also, if needed, try using java 1.8 at Project Properties 
 */
public class GameTest {

  // Mock objects used by all tests (they will be injected below)
  @Mock Random rng;
  @Mock Player player1;
  @Mock Player player2;

  // Mockito object to test call order
  InOrder callOrder;

  // Game object
  Game game;

  //Setup method executed before each  test
  @BeforeEach
  public void setup() {
    // Instantiate all fields annotated with @Mock
	//  no need to call Mockito.mock manually.
    MockitoAnnotations.initMocks(this);
    
    // Shared stubbing for all tests
    when(player1.getName()).thenReturn(NAME_P1);
    when(player1.take(BET_VALUE_P1)).thenReturn(true);

    when(player2.getName()).thenReturn(NAME_P2);
    when(player2.take(BET_VALUE_P2)).thenReturn(true);

    when(rng.nextInt(6)).thenReturn(WINNING_BET-1);
    
    // monitor the order of method calls for these objects
    callOrder = inOrder(rng, player1, player2);
    
    game = new Game(rng); // inject dependency
  }

  @Test
  public final void testGameNoBets() {
    game.proceed();
    // verify dice "did not roll", ie, rng.nextInt() was not invoked
    callOrder.verify(rng, never()).nextInt();
  }
  
  @Test
  public final void testGamePlayer1Wins() {
   testGameWithBets(player1, WINNING_BET, LOSING_BET_2);
  }
  
  @Test
  public final void testGamePlayer2Wins() {
   testGameWithBets(player2, LOSING_BET_1, WINNING_BET);
  }
  
  @Test
  public final void testGameNoWinners() {
    testGameWithBets(null, LOSING_BET_1, LOSING_BET_2);
  }
  
  
  // Parameterized test utility method
  private void testGameWithBets(Player winner, int p1Bet, int p2Bet) {
    game.bet(player1, p1Bet, BET_VALUE_P1);
    game.bet(player2, p2Bet, BET_VALUE_P2);
    game.proceed();

    // Verify interactions and the order in which they took place.    
    callOrder.verify(player1).take(BET_VALUE_P1);
    callOrder.verify(player2).take(BET_VALUE_P2);

    // This is not so relevant, but we can 
    // ensure that nextInt(6) was called for the RNG
    callOrder.verify(rng).nextInt(6);
    
    if (winner != null) {
      callOrder.verify(winner).award(BET_VALUE_P1+BET_VALUE_P2);
    } 
    
    // there were not more calls for these methods
    callOrder.verify(rng,never()).nextInt();
    callOrder.verify(player1,never()).take(anyInt());
    callOrder.verify(player2,never()).take(anyInt());
    callOrder.verify(player1,never()).award(anyInt());
    callOrder.verify(player2,never()).award(anyInt());
  }

  ////////////////////////////////////////////////
  // Literal values used in tests
  private static String NAME_P1 = "Alberto";
  private static String NAME_P2 = "Manuel";
  private static final int WINNING_BET = 1;
  private static final int LOSING_BET_1 = 2;
  private static final int LOSING_BET_2 = 3;
  private static final int BET_VALUE_P1 = 200;
  private static final int BET_VALUE_P2 = 300;
}
