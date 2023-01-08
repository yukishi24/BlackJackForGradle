package Constant;

/**
 * システム定数クラス
 * 
 * @author yukishi
 *
 */
public class SettingConst {
  /** ユーザーが操作するか(コンソールに文字を出力するか) **/
  public static final boolean SHOULD_OPERATE = true;
  /** デッキ数 **/
  public static final int DECK_NUM = 8;
  /** カードを交換する際の使用率(1%～99%まで) -> 40なら、40%使用したタイミングでリセット **/
  public static final int CHANGE_DECK_PER = 50;
  /** 初期の所持ベル **/
  public static final int POCKET_MONEY = 1000;
}
