package io.github.jacksonbailey.ascii85;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;
import org.junit.Test;

/**
 * A simple test class for {@link Ascii85}
 */
public class Ascii85Test {

  private static final String SAMPLE_TEXT = "Man is distinguished, not only by his reason, but by "
      + "this singular passion from other animals, which is a lust of the mind, that by a "
      + "perseverance of delight in the continued and indefatigable generation of knowledge, "
      + "exceeds the short vehemence of any carnal pleasure.";

  @Test(expected = IllegalArgumentException.class)
  public void decodeShouldFailOnNullInput() {
    Ascii85.decode(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void decodeShouldFailOnEmptyInput() {
    Ascii85.decode("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void encodeShouldFailOnNullInput() {
    Ascii85.encode(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void encodeShouldFailOnEmptyInput() {
    Ascii85.encode(new byte[0]);
  }

  @Test
  public void fullChunkEncode() {
    byte[] input = new byte[] {0x0, 0x0, 0x0, 0x2};

    String output = Ascii85.encode(input);

    assertEquals("!!!!#", output);
  }

  @Test
  public void fullChunkDecode() {
    String input = "!!!!&";

    byte[] output = Ascii85.decode(input);

    assertArrayEquals(new byte[] {0x0, 0x0, 0x0, 0x5}, output);
  }

  @Test
  public void decodeShouldIgnoreNewLineCharacter() {
    String encodedString =
        "9jqo^BlbD-BleB1DJ+*+F(f,q/0JhKF<GL>Cj@.4Gp$d7F!,L7@<6@)/0JDEF<G%<+EV:2F!,\n"
            + "O<DJ+*.@<*K0@<6L(Df-\\0Ec5e;DffZ(EZee.Bl.9pF\"AGXBPCsi+DGm>@3BB/F*&OCAfu2/AKY\n"
            + "i(DIb:@FD,*)+C]U=@3BN#EcYf8ATD3s@q?d$AftVqCh[NqF<G:8+EV:.+Cf>-FD5W8ARlolDIa\n"
            + "l(DId<j@<?3r@:F%a+D58'ATD4$Bl@l3De:,-DJs`8ARoFb/0JMK@qB4^F!,R<AKZ&-DfTqBG%G\n"
            + ">uD.RTpAKYo'+CT/5+Cei#DII?(E,9)oF*2M7/c";

    assertEquals(SAMPLE_TEXT, new String(Ascii85.decode(encodedString), StandardCharsets.US_ASCII));
  }

  @Test
  public void decodeShouldIgnoreSpaceCharacter() {
    String encodedString =
        "9jqo^BlbD-BleB1DJ+*+F    (f,q/0JhKF<GL>Cj@.4Gp$d7F!,L7@<6@)/0JDEF<G%<+EV:2F!,\n"
            + "O<DJ+*.@<*K0@<6L(Df-\\0Ec5e;DffZ(EZee.  Bl.9pF\"AGXBPCsi+DGm>@3BB/F*&OCAfu2/AKY\n"
            + "i(DIb:@FD,*)+C]U=@3BN#EcYf8ATD3s@q?d$AftVqCh[NqF<G:8+EV:.+Cf>-FD5W8ARlolDIa\n"
            + "l(DId<j@<?3r@:F%a+D58'ATD4$Bl@l3De:,-DJs`8ARoFb/0JMK@qB4^F!,R<AKZ&-DfTqBG%G\n"
            + ">uD.RTpAKYo'+CT/5+Cei#DII?(E,9)oF*2M7/c";

    assertEquals(SAMPLE_TEXT, new String(Ascii85.decode(encodedString), StandardCharsets.US_ASCII));
  }

  @Test
  public void decodeParagraphTest() {
    String encodedString =
        "9jqo^BlbD-BleB1DJ+*+F(f,q/0JhKF<GL>Cj@.4Gp$d7F!,L7@<6@)/0JDEF<G%<+EV:2F!,"
            + "O<DJ+*.@<*K0@<6L(Df-\\0Ec5e;DffZ(EZee.Bl.9pF\"AGXBPCsi+DGm>@3BB/F*&OCAfu2/AKY"
            + "i(DIb:@FD,*)+C]U=@3BN#EcYf8ATD3s@q?d$AftVqCh[NqF<G:8+EV:.+Cf>-FD5W8ARlolDIa"
            + "l(DId<j@<?3r@:F%a+D58'ATD4$Bl@l3De:,-DJs`8ARoFb/0JMK@qB4^F!,R<AKZ&-DfTqBG%G"
            + ">uD.RTpAKYo'+CT/5+Cei#DII?(E,9)oF*2M7/c";

    assertEquals(SAMPLE_TEXT, new String(Ascii85.decode(encodedString), StandardCharsets.US_ASCII));
  }

  @Test
  public void encodeParagraphTest() {
    String solution = "9jqo^BlbD-BleB1DJ+*+F(f,q/0JhKF<GL>Cj@.4Gp$d7F!,L7@<6@)/0JDEF<G%<+EV:2F!,"
        + "O<DJ+*.@<*K0@<6L(Df-\\0Ec5e;DffZ(EZee.Bl.9pF\"AGXBPCsi+DGm>@3BB/F*&OCAfu2/AKY"
        + "i(DIb:@FD,*)+C]U=@3BN#EcYf8ATD3s@q?d$AftVqCh[NqF<G:8+EV:.+Cf>-FD5W8ARlolDIa"
        + "l(DId<j@<?3r@:F%a+D58'ATD4$Bl@l3De:,-DJs`8ARoFb/0JMK@qB4^F!,R<AKZ&-DfTqBG%G"
        + ">uD.RTpAKYo'+CT/5+Cei#DII?(E,9)oF*2M7/c";

    assertEquals(solution, Ascii85.encode(SAMPLE_TEXT.getBytes()));
  }

}
