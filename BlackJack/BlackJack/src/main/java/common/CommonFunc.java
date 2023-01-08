package common;

import java.util.List;
import java.util.Optional;
import Constant.Constant;
import bean.Card;
import bean.Dealer;
import bean.Deck;
import bean.Human;

/**
 * 共通関数
 * 
 * @author yukishi
 *
 */
public class CommonFunc {
  /**
   * 誰が勝ったかを判断する
   * 
   * @param dealer ディーラー
   * @param playerHand プレイヤーの手札
   * @return 1-> dealer, 2 -> player(nomal), 3 -> player(BJ), 4 -> draw(引き分け)
   */
  public static int judgementWinner(Dealer dealer, List<Card> playerHand) {
    int winnerNum = Constant.DEALER_WIN;
    if (overHand21(playerHand)) {
      // playerがバーストしてるため、ディーラーの勝ち
      winnerNum = Constant.DEALER_WIN;
    } else if (overHand21(dealer.getHand())) {
      // ディーラーがバーストしてるため、playerの勝ち
      int playerScore = getScore(false, playerHand);
      winnerNum = playerScore == 21 ? Constant.PLAYER_WIN_BJ : Constant.PLAYER_WIN;
    } else {
      int dealerScore = getScore(false, dealer.getHand());
      int playerScore = getScore(false, playerHand);
      if (playerScore > dealerScore) {
        // playerの勝ち
        winnerNum = playerScore == 21 ? Constant.PLAYER_WIN_BJ : Constant.PLAYER_WIN;
      } else if (playerScore == dealerScore) {
        // 引き分け
        winnerNum = Constant.NOTTING_WIN;
      } else {
        // ディーラー勝ち
        winnerNum = Constant.DEALER_WIN;
      }

    }
    return winnerNum;
  }

  /**
   * カードを引いて手札に加える
   * 
   * @param deck デッキ
   * @param hand 手札
   */
  public static void draw(Deck deck, List<Card> hand) {
    hand.add(deck.draw());
  }

  /**
   * 手札の初期化後、カードを2枚引いて手札に加える
   * 
   * @param deck デッキ
   * @param human humanオブジェクト
   */
  public static void firstAction(Deck deck, Human human) {
    human.handClear();
    human.getHand().add(deck.draw());
    human.getHand().add(deck.draw());
  }

  /**
   * 得点を取得する
   * 
   * @param aceChange エースの変換制御を行うかどうか
   * @param hand 手札
   * @return 手札の合計点
   */
  public static int getScore(boolean aceChange, List<Card> hand) {
    // Aの動きを制御する
    if (aceChange && haveAce11(hand))
      aceChange(hand);
    return hand.stream().mapToInt(c -> c.getNumber().getNum()).sum();
  }

  /**
   * バーストしている場合は、A11をA1に変更する
   * 
   * @param hand
   */
  public static void aceChange(List<Card> hand) {
    int haveAceInt = getManyAce11(hand);
    for (int i = 0; i < haveAceInt; i++) {
      if (getScore(false, hand) > 21) {
        Optional<Card> opt =
            hand.stream().filter(c -> Card.Number.n1_1 == c.getNumber()).findFirst();
        if (opt.isPresent()) {
          Card c = opt.get();
          c.setNumber(Card.Number.n1_2);
        }
      } else {
        break;
      }
    }
  }

  /**
   * 11と数えているAceの数を取得する
   * 
   * @param hand 手札
   * @return 11と数えているAceの数
   */
  public static int getManyAce11(List<Card> hand) {
    return (int) hand.stream().filter(c -> Card.Number.n1_1 == c.getNumber()).count();
  }

  /**
   * 11と数えているAceを持っているか
   * 
   * @param hand 手札
   * @return 11と数えているAceを持っているか
   */
  public static boolean haveAce11(List<Card> hand) {
    return getManyAce11(hand) != 0;
  }

  /**
   * 手札が21を超えているか調べる
   * 
   * @param hand
   * @return 手札が21を超えているか
   */
  public static boolean overHand21(List<Card> hand) {
    boolean over21 = false;
    int total = getScore(true, hand);
    if (total > 21) {
      over21 = true;
    }
    return over21;
  }
}
