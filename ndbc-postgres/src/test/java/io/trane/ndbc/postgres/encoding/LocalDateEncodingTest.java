package io.trane.ndbc.postgres.encoding;

import static io.trane.ndbc.util.Collections.toImmutableSet;

import io.trane.ndbc.value.LocalDateValue;

public class LocalDateEncodingTest
    extends EncodingTest<LocalDateValue, LocalDateEncoding> {

  public LocalDateEncodingTest() {
    super(
        new LocalDateEncoding(),
        toImmutableSet(Oid.DATE),
        LocalDateValue.class,
        r -> new LocalDateValue(randomLocalDateTime(r).toLocalDate()));
  }
}
