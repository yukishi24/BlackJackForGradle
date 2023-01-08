package blackjack;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import Constant.Constant;
import bean.Card;
import bean.Dealer;
import bean.Deck;
import bean.Player;
import common.CommonFunc;

/**
 * ブラックジャックを管理するクラス
 * 
 * @author yukishi
 *
 */
public class GameManage {
  /** ディーラーの作成 **/
  private Dealer dealer;
  /** プレイヤーの作成 **/
  private Player player;
  /** デッキ **/
  private Deck deck;

  /**
   * Gameに必要なものを作成
   * 
   * @param pocketMoney 所持するベル
   */
  public GameManage(int pocketMoney) {
    // 挨拶をおこなう
    ConsoleManager.gameStartUp();

    /** ディーラーの作成 **/
    this.dealer = Dealer.getInstance();
    /** プレイヤーの作成 **/
    this.player = new Player(pocketMoney);
    /** デッキの作成 **/
    this.deck = new Deck();
  }

  /**
   * ゲームを終了する
   */
  public void close() {
    // 儲けの表示
    ConsoleManager.resultMoney(player.getPocketMoney());
    // 挨拶をおこなう
    ConsoleManager.gameFinish();
  }

  /**
   * ブラックジャックを始める
   * 
   * @return ゲームを強制終了するか
   */
  public boolean play() {
    int gameStatus = Constant.BATTLE_NOW;

    // ゲーム開始の合図
    ConsoleManager.gameStart(player.getPocketMoney());

    // ブラックジャックの最初の行動を行う
    firstAction();

    // ベット金を選択させる
    boolean exeEnd = choiceBetMoneyOrEnd();
    if (exeEnd)
      return exeEnd;

    // 今の手札を表示する
    ConsoleManager.handOpen(dealer, player, false);

    // プレイヤーに次の行動を選択させる
    boolean nowSplitTurn = false;
    do {
      String userInputStr = ConsoleManager.choicePlayerAction(player, nowSplitTurn);
      if (playerAction(userInputStr, nowSplitTurn) && (player.isStand() || player.isBurst())) {
        // splitしている場合
        if (player.isSplit()) {
          nowSplitTurn = true;
          if (player.isSplitStand() || player.isSplitBurst())
            break;
        } else {
          break;
        }
      }
    } while (true);

    // プレイヤーの手札によって、ディーラーの行動を決める
    if (player.isBurst() && player.isSplitBurst()) {
      gameStatus = Constant.DEALER_WIN;
    } else {
      // ディーラーが行動する
      dealer.drawOver17(deck);
    }

    // 勝敗を判定と、それによる行動を行う
    judgementAction(gameStatus, player);

    return player.getPocketMoney() == 0;
  }

  /**
   * ベットするベルを選択してもらうメソッド
   * 
   * @return ゲームを強制終了するか
   */
  private boolean choiceBetMoneyOrEnd() {
    do {
      String userInputStr = ConsoleManager.choiceBetMoney();
      if (Constant.END.equals(userInputStr.toUpperCase()))
        return true;

      try {
        int betMoney = Integer.parseInt(userInputStr);
        if (betMoney <= 0) {
          // 0円以下をベット場合
          ConsoleManager.gameInfoPrint(Constant.ONE_MORE_INPUT_UNDER0, true);
        } else if (betMoney > player.getPocketMoney()) {
          // 持っているベルより、多くをベット場合
          ConsoleManager.gameInfoPrint(Constant.ONE_MORE_INPUT_LESS_MONEY, true);
        } else {
          player.setBetMoney(betMoney);
          player.setPocketMoney(player.getPocketMoney() - betMoney);
          break;
        }
      } catch (NumberFormatException e) {
        ConsoleManager.gameInfoPrint(Constant.ONE_MORE_INPUT, true);
      }
    } while (true);

    return false;
  }

  /**
   * ブラックジャックの最初の行動を行う
   */
  private void firstAction() {
    // カードを引く前に、シャッフルの必要性があるか確認
    if (deck.shouldResetDeck()) {
      // 半数以上カードが使用されている場合ｈ、シャッフルする
      deck.reset();
    }

    // プレイヤーとディーラーの手札初期化し、カードを二枚ひく
    player.firstAction(deck);
    dealer.firstAction(deck);
  }

  /**
   * プレイヤーの行動を分岐する
   * 
   * @param userInputStr userが入力した文字列
   * @param nowSplitTurn splitの手札での行動か
   * @return errorが無い場合はtrue
   */
  private boolean playerAction(String userInputStr, Boolean nowSplitTurn) {
    boolean isNotError = true;
    switch (userInputStr.toUpperCase()) {
      case Constant.HIT:
        List<Card> localHand = nowSplitTurn ? player.getSecondHand() : player.getHand();

        // 手札を一枚引く
        CommonFunc.draw(deck, localHand);

        // 21を超えていないか判定する
        if (CommonFunc.overHand21(localHand)) {
          // バースト状態に変更する
          if (nowSplitTurn) {
            player.setSplitBurst(true);
          } else {
            player.setBurst(true);
            // バースト && スプリット選択済み && スプリットターン以外
            if (player.isSplit()) {
              // 今の手札を表示する
              ConsoleManager.handOpen(dealer, player, false);
            }
          }
        } else {
          // 今の手札を表示する
          ConsoleManager.handOpen(dealer, player, false);
        }

        break;
      case Constant.STAND:
        // stand状態に変更する
        if (nowSplitTurn) {
          player.setSplitStand(true);
        } else {
          player.setStand(true);
        }

        break;
      case Constant.DOUBLE_DOWN:
        // DOUBLE_DOWNが可能かcheck
        if (player.possibleDoubleDown()) {
          // 所持ベルを減らして、bet増加
          player.setPocketMoney(player.getPocketMoney() - player.getBetMoney());
          player.setBetMoney(player.getBetMoney() + player.getBetMoney());
          // double downを行う
          CommonFunc.draw(deck, player.getHand());
          player.setStand(true);
        } else {
          // もう一度選択させる
          isNotError = false;
          ConsoleManager.gameInfoPrint(Constant.ONE_MORE_INPUT, true);
        }
        break;
      case Constant.SPLIT:
        // splitが可能かcheck
        if (player.possibleSplit()) {
          // ベットベルを減らす
          player.setPocketMoney(player.getPocketMoney() - player.getBetMoney());
          // splitを行う
          player.split(deck);
          // splitFlgを立てる
          player.setSplit(true);

          // 今の手札を表示する
          ConsoleManager.handOpen(dealer, player, false);
        } else {
          // もう一度選択させる
          isNotError = false;
          ConsoleManager.gameInfoPrint(Constant.ONE_MORE_INPUT, true);
        }

        break;
      default:
        // もう一度引かせる
        isNotError = false;
        ConsoleManager.gameInfoPrint(Constant.ONE_MORE_INPUT, true);
        break;
    }

    return isNotError;
  }

  /**
   * 勝敗を決める
   * 
   * @param gameStatus ゲームの状態 (勝負中:0, ディーラーの勝ち : 1, プレイヤー勝ち(21以外) : 2, プレイヤー勝ち(21で勝ち) : 3, 引き分け : 4,
   *        split mode:5)
   * @param player プレイヤーオブジェクト
   */
  private void judgementAction(int gameStatus, Player player) {
    int betMoney = player.getBetMoney();

    // Modeによって出力する文字を変える
    boolean isSplitMode = player.isSplit();
    Map<String, Integer> resultList = new LinkedHashMap<>();

    // 勝敗を決める
    int winnerNum =
        gameStatus == Constant.BATTLE_NOW ? CommonFunc.judgementWinner(dealer, player.getHand())
            : gameStatus;
    resultList.put(isSplitMode ? Constant.PLAYER1 : Constant.PLAYER, winnerNum);
    // splitの場合
    if (isSplitMode) {
      int winnerNum2 = gameStatus == Constant.BATTLE_NOW
          ? CommonFunc.judgementWinner(dealer, player.getSecondHand())
          : gameStatus;
      resultList.put(Constant.PLAYER2, winnerNum2);
    }

    // 今の手札を表示する
    ConsoleManager.handOpen(dealer, player, true);

    // 勝敗によって行動を変える
    resultList.forEach((key, val) -> {
      switch (val) {
        case Constant.DEALER_WIN:
          dealer.sayYouLose(key);
          break;
        case Constant.PLAYER_WIN:
          player.setPocketMoney(player.getPocketMoney() + (betMoney * 2));
          dealer.sayYouWin(key);
          break;
        case Constant.PLAYER_WIN_BJ:
          // 端数は切り捨て
          player.setPocketMoney(player.getPocketMoney() + (int) (betMoney * 2.5));
          dealer.sayYouWinBj(key);
          break;
        case Constant.NOTTING_WIN:
          player.setPocketMoney(player.getPocketMoney() + (betMoney));
          dealer.sayYouDraw(key);
          break;
      }
    });
    // 見やすいように区切りを入れる
    ConsoleManager.printSeparatorEnd();
  }
}
