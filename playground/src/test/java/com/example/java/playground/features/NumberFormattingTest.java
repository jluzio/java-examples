package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import java.text.NumberFormat;
import java.util.Locale;
import org.junit.jupiter.api.Test;

public class NumberFormattingTest extends AbstractTest {

  @Test
  void test() {
    log.info("Compact Formatting is:");
    NumberFormat upvotes = NumberFormat
        .getCompactNumberInstance(new Locale("en", "US"), NumberFormat.Style.SHORT);
    upvotes.setMaximumFractionDigits(1);

    log.info(upvotes.format(2592) + " upvotes");

    NumberFormat upvotes2 = NumberFormat
        .getCompactNumberInstance(new Locale("en", "US"), NumberFormat.Style.LONG);
    upvotes2.setMaximumFractionDigits(2);
    log.info(upvotes2.format(2011) + " upvotes");
  }
}
