package bean;

/**
 * カード用のbean
 * 
 * @author yukishi
 *
 */
public class Card {
  /**
   * コンストラクタ
   * 
   * @param トランプの柄情報(列挙型)
   * @param トランプの数字情報(列挙型)
   */
  public Card(Suite suite, Number number) {
    this.suite = suite;
    this.number = number;
  }

  /**
   * トランプの数字
   */
  private Number number;
  /**
   * トランプのマーク
   */
  private Suite suite;

  public Suite getSuite() {
    return suite;
  }

  public Number getNumber() {
    return number;
  }

  public void setNumber(Number number) {
    this.number = number;
  }

  /**
   * トランプの柄の列挙クラス
   * 
   * @author copper_dog
   */
  public enum Suite {
    SPADE("♠"), CLUB("♣"), DIAMOND("♦"), HEART("♥");
    // 文字化けするなら、上を消して下を使ってね!!
    // SPADE("S "), CLUB("C "), DIAMOND("D "), HEART("H ");

    /** トランプの柄 **/
    private String label;

    Suite(String label) {
      this.label = label;
    }

    public String getLabel() {
      return label;
    }
  }

  /**
   * トランプの数字の列挙クラス
   * 
   * @author copper_dog
   */
  public enum Number {
    n1_1(11, "A ", true), n1_2(1, "A ", false), n2(2, "2 ", true), n3(3, "3 ", true), n4(4, "4 ",
        true), n5(5, "5 ", true), n6(6, "6 ", true), n7(7, "7 ", true), n8(8, "8 ", true), n9(9,
            "9 ", true), n10(10, "10",
                true), n11(10, "J ", true), n12(10, "Q ", true), n13(10, "K ", true);

    /** トランプの数字(計算する値) **/
    private int num;
    /** トランプの数字(表示する値) **/
    private String displayNum;
    /** デッキを作る際に含めるか **/
    private boolean startCreate;

    Number(int num, String displayNum, boolean startCreate) {
      this.num = num;
      this.displayNum = displayNum;
      this.startCreate = startCreate;
    }

    public int getNum() {
      return num;
    }

    public String getDisplayNum() {
      return displayNum;
    }

    public boolean getStartCreate() {
      return startCreate;
    }
  }

  /**
   * インスタンスの文字列化
   * 
   * @return (♦A )とか(♥6 )とか(♣10)の文字
   */
  @Override
  public String toString() {
    // 例 : (♦A )とか(♥6 )とか(♣10)
    return "(" + suite.getLabel() + number.getDisplayNum() + ")";
  }
}
