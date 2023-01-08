package bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import Constant.SettingConst;

/**
 * デッキクラス
 * 
 * @author yukishi
 *
 */
public class Deck {
  // デッキ
  private List<Card> deck = null;
  // 次に引くカードの要素位置
  private int nextDrawIndex = 0;

  /**
   * コンストラクタ デッキの生成を行う(シャッフルもする)
   */
  public Deck() {
    this.deck = createDeck();
    // デッキの状態をリセットする
    reset();
  }

  /**
   * 交換するべきかどうか判定する
   * 
   * @return カードをリセットするべきか
   */
  public boolean shouldResetDeck() {
    // 交換するべきデッキの使用率
    int changeDeckPer = 50;
    // 範囲内に指定が無い場合は、使用率50%で交換
    if (1 <= SettingConst.CHANGE_DECK_PER && SettingConst.CHANGE_DECK_PER <= 100)
      changeDeckPer = SettingConst.CHANGE_DECK_PER;

    // (デッキ数 * 数字数 * マーク数) < 現在引いているカードのindex * (100 / 交換すべきデッキの使用率)
    return ((SettingConst.DECK_NUM * 13 * 4) <= nextDrawIndex * (100 / changeDeckPer));
  }

  /**
   * デッキの状態をリセットする
   */
  public void reset() {
    // デッキのシャッフルをする
    Collections.shuffle(this.deck);

    // 次に引くカードの要素位置を0に戻す
    nextDrawIndex = 0;
  }

  /**
   * カードを1枚引く
   * 
   * @return 引いたカード
   */
  public Card draw() {
    if (this.deck.size() <= nextDrawIndex) {
      // ***********カードが足りないため、引くカードがありません***********
      // デッキの状態をリセットする
      reset();
    }
    // 代入と後置インクリメント
    int drawIndex = nextDrawIndex++;
    return deck.get(drawIndex);
  }

  /**
   * 設定されたデッキ数setする
   * 
   * @return デッキ
   */
  @SuppressWarnings("unchecked")
  private List<Card> createDeck() {
    List<Card> allDeck = new ArrayList<>();

    // 1デッキ作成
    ArrayList<Card> oneDeck = createOneDeck();
    // 設定されたデッキ数setする
    for (int i = 0; i < SettingConst.DECK_NUM; i++) {
      allDeck.addAll((List<Card>) oneDeck.clone());
    }

    return allDeck;
  }

  /**
   * １デッキ＝52枚(１～13のトランプ、ジョーカー抜き)を作成
   * 
   * @return １デッキ
   */
  private ArrayList<Card> createOneDeck() {
    ArrayList<Card> deck = new ArrayList<>();

    // numberのenum loop 1～13 (注:filterあり)
    Arrays.stream(Card.Number.values()).filter(e -> e.getStartCreate()).forEach(number -> {
      // suiteのenum loop
      Arrays.stream(Card.Suite.values()).forEach(suite -> {
        deck.add(new Card(suite, number));
      });
    });

    return deck;
  }
}
