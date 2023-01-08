package Constant;

/**
 * 定数クラス
 * 
 * @author yukishi
 *
 */
public class Constant {
  /** 勝負中 **/
  public static final int BATTLE_NOW = 0;
  /** ディーラーの勝ち **/
  public static final int DEALER_WIN = 1;
  /** プレイヤー勝ち(21以外で勝ち) **/
  public static final int PLAYER_WIN = 2;
  /** プレイヤー勝ち(21で勝ち) **/
  public static final int PLAYER_WIN_BJ = 3;
  /** 引き分け **/
  public static final int NOTTING_WIN = 4;
  /** split mode **/
  public static final int SPLIT_MODE = 5;
  /** yes1 **/
  public static final String YES_1 = "YES";
  /** yes2 **/
  public static final String YES_2 = "Y";
  /** NO1 **/
  public static final String NO_1 = "NO";
  /** NO2 **/
  public static final String NO_2 = "N";
  /** END **/
  public static final String END = "END";
  /** ディーラー **/
  public static final String DEALER = "DEALER";
  /** プレイヤー **/
  public static final String PLAYER = "Player";
  /** プレイヤー1 **/
  public static final String PLAYER1 = "PlayerR";
  /** プレイヤー2 **/
  public static final String PLAYER2 = "PlayerL";
  /** 空文字 **/
  public static final String EMPTY = "";
  /** 区切り文字(途中用) **/
  public static final String SEPARATOR_STR = "-----------------------------------";
  /** 区切り文字(終わり用) **/
  public static final String SEPARATOR_STR_END = "****************************************";
  /** あなたの文字列定数 **/
  public static final String YOU_ARE = "You are";
  /** プレイヤーの勝ち(nomal)をたたえる **/
  public static final String YOU_ARE_WIN = "congratulation. You are win";
  /** プレイヤーの勝ち(BJ)をたたえる **/
  public static final String YOU_ARE_WIN_BJ = "congratulation. You are BLACK JACK!!";
  /** プレイヤーの負けを煽る **/
  public static final String YOU_ARE_LOSE = "You are lose.";
  /** 引き分けだと教える **/
  public static final String YOU_ARE_DRAW = "You are draw.";
  /** 謎のカード **/
  public static final String MYSTERIOUS_CARD = "(??)";
  /** スペース **/
  public static final String SPACE = "  ";
  /** ユーザーにbet金を選ばせる文字 **/
  public static final String CHOICE_USER_BET = "\nベットするベルを入力してください(end -> 終了)\n  >";
  /** ユーザーに次の行動を選ばせる文字 **/
  public static final String CHOICE_USER_ACTION = "もう一回、遊べるドン。\n  y: yes  n:no\n  >";
  /** プレイヤーに次の行動を選ばせる文字1 **/
  public static final String CHOICE_PLAYER_ACTION1 = "の行動を選択してください。\n  h: hit  s:stay";
  /** プレイヤーに次の行動を選ばせる文字2 **/
  public static final String CHOICE_PLAYER_ACTION2 = "  d:double down";
  /** プレイヤーに次の行動を選ばせる文字3 **/
  public static final String CHOICE_PLAYER_ACTION3 = "  p:split";
  /** プレイヤーに次の行動を選ばせる文字end **/
  public static final String CHOICE_PLAYER_ACTION_END = "\n  >";
  /** hit **/
  public static final String HIT = "H";
  /** stand **/
  public static final String STAND = "S";
  /** double down **/
  public static final String DOUBLE_DOWN = "D";
  /** Split **/
  public static final String SPLIT = "P";
  /** ゲーム起動の挨拶 **/
  public static final String HELLO = "    ブラックジャックへようこそ!!    ";
  /** 稼いだ報告 **/
  public static final String RESULT_EARNED = "ベル勝ちました。 ";
  /** 損した報告 **/
  public static final String RESULT_LOST = "ベル負けました。 ";
  /** ゲーム終了の挨拶 **/
  public static final String THANKS = "\n  copper_dog「Thank you for playing!! :)」    ";
  /** ゲーム開始の挨拶 **/
  public static final String GAME_START = "    ゲーム開始!!    ";
  /** ゲーム開始の挨拶 **/
  public static final String TEACH_POKET_MONEY = "の所持ベル  ->  ";
  /** もう一度入力してください **/
  public static final String ONE_MORE_INPUT = "\n    Error : もう一度入力してください    \n";
  /** ベルがたりません。もう一度入力してください。 **/
  public static final String ONE_MORE_INPUT_LESS_MONEY =
      "\n    Error : ベルがたりません。もう一度入力してください。    \n";
  /** 0以下が入力されました。もう一度入力してください。 **/
  public static final String ONE_MORE_INPUT_UNDER0 = "\n    Error : 0以下が入力されました。もう一度入力してください。    \n";
}
