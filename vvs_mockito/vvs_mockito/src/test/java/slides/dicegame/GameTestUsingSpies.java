package slides.dicegame;

import static org.mockito.Mockito.*;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.Random;

/**
 * Game testing in which classes Bet and Player are integrated.
 * Using players on mockito's spy objects
 * 
 * @author Eduardo Marques, DI/FCUL, 2013/14, Joao Neto
 *
 */
public class GameTestUsingSpies {

  // Mock objects used by all tests
  @Mock Random  rng;
  @Spy  Player  player1 = new Player(NAME_P1, INITIAL_MONEY);
  @Spy  Player  player2 = new Player(NAME_P2, INITIAL_MONEY);
  
  InOrder callOrder;  // Mockito object to test call order
  Game    game;       // Game object

  @BeforeEach
  public void setup() {
	  
    // Instantiate all fields annotated with @Mock
    MockitoAnnotations.initMocks(this);
    
    // Shared stubbing for all tests
    when(rng.nextInt(6)).thenReturn(WINNING_BET - 1);
    
    callOrder = inOrder(rng, player1, player2);
    game = new Game(rng); // inject dependency
  }

  @Test
  public final void testGameNoBets() {
    game.proceed();
    callOrder.verify(rng, never()).nextInt();    // verify dice "did not roll"
  }

  @Test
  public final void testGamePlayer1Wins() {
    testGameWithBets(player1, 
        WINNING_BET, LOSING_BET_2, 
        INITIAL_MONEY + BET_VALUE_P2, 
        INITIAL_MONEY - BET_VALUE_P2);
  }

  @Test
  public final void testGamePlayer2Wins() {
    testGameWithBets(player2, 
        LOSING_BET_1, WINNING_BET, 
        INITIAL_MONEY - BET_VALUE_P1, 
        INITIAL_MONEY + BET_VALUE_P1);
  }

  @Test
  public final void testGameNoWinners() {
    testGameWithBets(null, 
        LOSING_BET_1, LOSING_BET_2, 
        INITIAL_MONEY - BET_VALUE_P1, 
        INITIAL_MONEY - BET_VALUE_P2);
  }

  // Parameterized test utility method
  private void testGameWithBets(Player winner, int p1Bet, int p2Bet,
      int expectedMoney1, int expectedMoney2) {
	  
    game.bet(player1, p1Bet, BET_VALUE_P1);
    game.bet(player2, p2Bet, BET_VALUE_P2);
    game.proceed();

    // Verify spy objects state
    assertEquals(expectedMoney1, player1.getMoney(), "player 1 - money");
    assertEquals(expectedMoney2, player2.getMoney(), "player 2 - money");

    // Verify interactions and the order in which they took place.
    callOrder.verify(player1).take(BET_VALUE_P1);
    callOrder.verify(player2).take(BET_VALUE_P2);

    // This is not so relevant, but we can
    // ensure that nextInt(6) was called for the RNG
    callOrder.verify(rng).nextInt(6);

    if (winner != null) 
      callOrder.verify(winner).award(BET_VALUE_P1 + BET_VALUE_P2);

    callOrder.verify(rng, never()).nextInt();
    callOrder.verify(player1, never()).take(anyInt());
    callOrder.verify(player2, never()).take(anyInt());
    callOrder.verify(player1, never()).award(anyInt());
    callOrder.verify(player2, never()).award(anyInt());
  }

  ////////////////////////////////////////////////////////
  // Literal values used in tests
  private static String    NAME_P1       = "Alberto";
  private static String    NAME_P2       = "Manuel";
  private static final int WINNING_BET   = 1;
  private static final int LOSING_BET_1  = 2;
  private static final int LOSING_BET_2  = 3;
  private static final int INITIAL_MONEY = 500;
  private static final int BET_VALUE_P1  = 200;
  private static final int BET_VALUE_P2  = 300;
}
