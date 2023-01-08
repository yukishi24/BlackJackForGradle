package bean;

import java.util.ArrayList;
import java.util.List;
import Constant.Constant;
import blackjack.ConsoleManager;
import common.CommonFunc;

/**
 * 人間の抽象クラス
 * 
 * @author yukishi
 *
 */
public abstract class Human {
  /**
   * 手札
   */
  protected List<Card> hand = new ArrayList<>();

  /**
   * 手札を返す
   * 
   * @return 手札
   */
  public List<Card> getHand() {
    return hand;
  }

  /**
   * 手札をセットする
   * 
   * @param hand 手札
   */
  public void setHand(List<Card> hand) {
    this.hand = hand;
  }

  /** 手札を開示する **/
  public abstract void open();

  /**
   * 手札のカード全て見せる
   * 
   * @param humanName 手札を所持している人名
   */
  public void allOpen(String humanName) {
    StringBuilder sb = new StringBuilder();
    hand.forEach(card -> {
      sb.append(card.toString());
      sb.append(Constant.SPACE);
    });

    // 手札が複数ある時のために半角スペースを追加する -> splitの時用の対策
    if (humanName.equals(Constant.DEALER) || humanName.equals(Constant.PLAYER))
      humanName = humanName + " ";
    ConsoleManager.gameInfoPrint(
        humanName + " (" + CommonFunc.getScore(true, hand) + "): " + sb.toString().trim(), true);
  }

  /**
   * メッセージを話す
   * 
   * @param humanName 話している人名
   * @param message メッセージ
   */
  public void say(String humanName, String message) {
    ConsoleManager.gameInfoPrint(humanName + "「" + message + "」", true);
  }

  /**
   * 手札の初期化
   */
  public void handClear() {
    hand = new ArrayList<>();
  }

  /**
   * 最初の行動を行う
   * 
   * @param deck デッキ
   */
  public void firstAction(Deck deck) {
    // 手札の初期化後、カードを2枚引いて手札に加える
    CommonFunc.firstAction(deck, this);
  };
}
