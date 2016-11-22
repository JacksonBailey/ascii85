package io.github.jacksonbailey.ascii85;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;
import org.junit.Test;

/**
 * A simple test class for {@link Ascii85}
 */
public class Ascii85Test {

  @Test(expected = IllegalArgumentException.class)
  public void decodeShouldFailOnNullInput() {
    Ascii85.decode(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void decodeShouldFailOnEmptyInput() {
    Ascii85.decode("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void decodeShouldFailOnInputWithOutOfBoundsCharacters() {
    Ascii85.decode("!!!!v");
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
  public void partialChunkEncode() {
    byte[] input = new byte[] {0x0, 0x0, 0x5};

    String output = Ascii85.encode(input);

    assertEquals("!!!0", output);
  }

  @Test
  public void partialChunkDecode() {
    String input = "!!!0";

    byte[] output = Ascii85.decode(input);

    assertArrayEquals(new byte[] {0x0, 0x0, 0x5}, output);
  }

  @Test
  public void allZeroChunkEncode() {
    byte[] input = new byte[] {0x0, 0x0, 0x0, 0x0};

    String output = Ascii85.encode(input);

    assertEquals("z", output);
  }

  @Test
  public void allZeroChunkDecode() {
    String input = "z";

    byte[] output = Ascii85.decode(input);

    assertArrayEquals(new byte[] {0x0, 0x0, 0x0, 0x0}, output);
  }

  @Test(expected = IllegalArgumentException.class)
  public void improperZeroChunkDecode() {
    Ascii85.decode("!z");
  }

  @Test
  public void decodeShouldIgnoreNewLineCharacter() {
    String input = "!\n!\n!\n!\n\n\n&\n\n\n";

    byte[] output = Ascii85.decode(input);

    assertArrayEquals(new byte[] {0x0, 0x0, 0x0, 0x5}, output);
  }

  @Test
  public void decodeShouldIgnoreSpaceCharacter() {
    String input = "! ! !         !                   &";

    byte[] output = Ascii85.decode(input);

    assertArrayEquals(new byte[] {0x0, 0x0, 0x0, 0x5}, output);
  }

  @Test
  public void decodeShouldIgnoreTabCharacter() {
    String input = "\t\t\t\t\t\t!!!\t!&";

    byte[] output = Ascii85.decode(input);

    assertArrayEquals(new byte[] {0x0, 0x0, 0x0, 0x5}, output);
  }

  @Test
  public void decodeParagraphTest() {

    String encoded = "9jqo^BlbD-BleB1DJ+*+F(f,q/0JhKF<GL>Cj@.4Gp$d7F!,L7@<6@)/0JDEF<G%<+EV:2F!,O<DJ"
        + "+*.@<*K0@<6L(Df-\\0Ec5e;DffZ(EZee.Bl.9pF\"AGXBPCsi+DGm>@3BB/F*&OCAfu2/AKYi(DIb:@FD,*)+C]"
        + "U=@3BN#EcYf8ATD3s@q?d$AftVqCh[NqF<G:8+EV:.+Cf>-FD5W8ARlolDIal(DId<j@<?3r@:F%a+D58'ATD4$B"
        + "l@l3De:,-DJs`8ARoFb/0JMK@qB4^F!,R<AKZ&-DfTqBG%G>uD.RTpAKYo'+CT/5+Cei#DII?(E,9)oF*2M7/c";

    byte[] output = Ascii85.decode(encoded);

    String text = "Man is distinguished, not only by his reason, but by this singular passion from "
        + "other animals, which is a lust of the mind, that by a perseverance of delight in the "
        + "continued and indefatigable generation of knowledge, exceeds the short vehemence of any "
        + "carnal pleasure.";

    byte[] expectedOutput = asciiBytesFromString(text);

    assertArrayEquals(expectedOutput, output);
  }

  @Test
  public void encodeParagraphTest() {
    String text = "Man is distinguished, not only by his reason, but by this singular passion from "
        + "other animals, which is a lust of the mind, that by a perseverance of delight in the "
        + "continued and indefatigable generation of knowledge, exceeds the short vehemence of any "
        + "carnal pleasure.";

    byte[] input = asciiBytesFromString(text);

    String output = Ascii85.encode(input);

    String encoded = "9jqo^BlbD-BleB1DJ+*+F(f,q/0JhKF<GL>Cj@.4Gp$d7F!,L7@<6@)/0JDEF<G%<+EV:2F!,O<DJ"
        + "+*.@<*K0@<6L(Df-\\0Ec5e;DffZ(EZee.Bl.9pF\"AGXBPCsi+DGm>@3BB/F*&OCAfu2/AKYi(DIb:@FD,*)+C]"
        + "U=@3BN#EcYf8ATD3s@q?d$AftVqCh[NqF<G:8+EV:.+Cf>-FD5W8ARlolDIal(DId<j@<?3r@:F%a+D58'ATD4$B"
        + "l@l3De:,-DJs`8ARoFb/0JMK@qB4^F!,R<AKZ&-DfTqBG%G>uD.RTpAKYo'+CT/5+Cei#DII?(E,9)oF*2M7/c";

    assertEquals(encoded, output);
  }

  @Test
  public void failingTest() {
    org.junit.Assert.fail();
  }

  private static byte[] asciiBytesFromString(String s) {
    return s.getBytes(StandardCharsets.US_ASCII);
  }

}
