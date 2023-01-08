package bean;

import java.util.ArrayList;
import java.util.List;
import Constant.Constant;
import blackjack.ConsoleManager;
import common.CommonFunc;

/**
 * プレイヤーのクラス
 * 
 * @author yukishi
 *
 */
public class Player extends Human {
  /** 手持ちのベル(残金) */
  private Integer pocketMoney = 0;
  /** ゲームでベットしたベル */
  private Integer betMoney = 0;
  /** スタンドしたかどうか */
  private boolean isStand = false;
  /** バーストしたかどうか */
  private boolean isBurst = false;
  /** splitを選択したかどうか */
  private boolean isSplit = false;
  /** split後の手札をスタンドしたか */
  private boolean isSplitStand = false;
  /** split後の手札がバーストしたかどうか */
  private boolean isSplitBurst = false;

  /** Split時に分けた2つ目の手札 */
  private List<Card> secondHand = new ArrayList<>();

  /**
   * コンストラクタ
   * 
   * @param pocketMoney 初期で持たせる所持ベル
   */
  public Player(int pocketMoney) {
    this.pocketMoney = pocketMoney;
  }

  /**
   * 手札の初期化後、カードを2枚引いて手札に加える
   * 
   * @param deck デッキ
   */
  @Override
  public void firstAction(Deck deck) {
    // 初期状態に戻す
    betMoney = 0;
    setStand(false);
    setBurst(false);
    setSplit(false);
    setSplitStand(false);
    setSplitBurst(false);

    super.firstAction(deck);
  }

  /**
   * 手札の初期化
   */
  @Override
  public void handClear() {
    this.secondHand = new ArrayList<>();
    super.handClear();
  }

  /**
   * 手札のカードを見せる
   */
  @Override
  public void open() {
    allOpen(Constant.PLAYER);
  }

  /**
   * 手札のカード全て見せる
   * 
   * @param humanName 手札を持っている人名
   */
  @Override
  public void allOpen(String humanName) {
    // second用の手札がある場合
    if (!secondHand.isEmpty()) {
      super.allOpen(Constant.PLAYER1);
      StringBuilder sb2 = new StringBuilder();
      secondHand.forEach(card -> {
        sb2.append(card.toString());
        sb2.append(Constant.SPACE);
      });

      ConsoleManager.gameInfoPrint(Constant.PLAYER2 + " (" + CommonFunc.getScore(true, secondHand)
          + "): " + sb2.toString().trim(), true);
    } else {
      super.allOpen(humanName);
    }
  }

  /**
   * splitが可能かどうか判定する
   * 
   * @return splitが可能かどうか
   */
  public boolean possibleSplit() {
    boolean possibleSplit = false;
    // 手札が二枚かどうか && スプリットが未選択か && ベットベルの二倍のベルを持っているか
    if (super.hand.size() == 2 && !isSplit && betMoney * 2 <= pocketMoney) {
      Card.Number firstCardNum = super.hand.get(0).getNumber();
      Card.Number secondCardNum = super.hand.get(1).getNumber();
      // 同じ数字(J,Q,Kは10扱い)かどうか(Aの1と11の考慮も入れる)
      if (firstCardNum.getNum() == secondCardNum.getNum()
          || firstCardNum.getDisplayNum().equals(secondCardNum.getDisplayNum())) {
        possibleSplit = true;
      }
    }

    return possibleSplit;
  }

  /**
   * double downが可能かどうか判定する
   * 
   * @return double downが可能かどうか
   */
  public boolean possibleDoubleDown() {
    boolean possibleDoubleDown = false;
    // 手札が二枚かどうか && スプリットが未選択か && ベットベルの二倍のベルを持っているか
    if (super.hand.size() == 2 && !isSplit && betMoney * 2 <= pocketMoney) {
      possibleDoubleDown = true;
    }

    return possibleDoubleDown;
  }

  /**
   * splitを選択した時の行動をする
   * 
   * @param deck デッキ
   */
  @SuppressWarnings("unchecked")
  public void split(Deck deck) {
    if (super.hand.size() == 2) {
      // ローカル変数にディープコピーを行う
      List<Card> localHand = (List<Card>) ((ArrayList<Card>) super.hand).clone();
      // 手札を二つに分ける
      super.hand = new ArrayList<>();
      super.hand.add(localHand.get(0));
      secondHand.add(localHand.get(1));

      // 各一枚だけ引く
      CommonFunc.draw(deck, secondHand);
      CommonFunc.draw(deck, super.hand);
    } ;
  }

  /**
   * @return 所持ベル
   */
  public Integer getPocketMoney() {
    return pocketMoney;
  }

  /**
   * @param pocketMoney セットする pocketMoney
   */
  public void setPocketMoney(Integer pocketMoney) {
    this.pocketMoney = pocketMoney;
  }

  /**
   * @return betMoney ベットするベル額
   */
  public Integer getBetMoney() {
    return betMoney;
  }

  /**
   * @param betMoney セットする betMoney
   */
  public void setBetMoney(Integer betMoney) {
    this.betMoney = betMoney;
  }

  /**
   * @return isStand satand状態か
   */
  public boolean isStand() {
    return isStand;
  }

  /**
   * @param isStand セットする isStand
   */
  public void setStand(boolean isStand) {
    this.isStand = isStand;
  }

  /**
   * @return isBurst バーストしているか
   */
  public boolean isBurst() {
    return isBurst;
  }

  /**
   * @param isBurst セットする isBurst
   */
  public void setBurst(boolean isBurst) {
    this.isBurst = isBurst;
  }

  /**
   * @return secondHand 分けた手札を取得する
   */
  public List<Card> getSecondHand() {
    return secondHand;
  }

  /**
   * @param secondHand セットする secondHand
   */
  public void setSecondHand(List<Card> secondHand) {
    this.secondHand = secondHand;
  }

  /**
   * @return isSplit splitを選択したか
   */
  public boolean isSplit() {
    return isSplit;
  }

  /**
   * @param isSplit セットする isSplit
   */
  public void setSplit(boolean isSplit) {
    this.isSplit = isSplit;
  }

  /**
   * @return isSplitStand 分けた手札をstandしたか
   */
  public boolean isSplitStand() {
    return isSplitStand;
  }

  /**
   * @param isSplitStand セットする isSplitStand
   */
  public void setSplitStand(boolean isSplitStand) {
    this.isSplitStand = isSplitStand;
  }

  /**
   * @return isSplitBurst 分けた手札がバーストしたかどうか
   */
  public boolean isSplitBurst() {
    return isSplitBurst;
  }

  /**
   * @param isSplitBurst セットする isSplitBurst
   */
  public void setSplitBurst(boolean isSplitBurst) {
    this.isSplitBurst = isSplitBurst;
  }
}
