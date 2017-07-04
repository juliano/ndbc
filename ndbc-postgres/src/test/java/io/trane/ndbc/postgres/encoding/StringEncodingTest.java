package io.trane.ndbc.postgres.encoding;

import static io.trane.ndbc.util.Collections.toImmutableSet;

import io.trane.ndbc.value.StringValue;

public class StringEncodingTest extends EncodingTest<StringValue, StringEncoding> {

  public StringEncodingTest() {
    super(
        new StringEncoding(),
        toImmutableSet(Oid.TEXT, Oid.NAME, Oid.VARCHAR, Oid.XML, Oid.JSON, Oid.BPCHAR),
        StringValue.class,
        r -> {
          final int length = r.nextInt(1000) + 1;
          final StringBuilder sb = new StringBuilder();
          while (sb.length() < r.nextInt(length)) {
            final char c = (char) (r.nextInt() & Character.MAX_VALUE);
            if (Character.isAlphabetic(c) || Character.isDigit(c))
              sb.append(c);
          }
          return new StringValue(sb.toString());
        });
  }

}
