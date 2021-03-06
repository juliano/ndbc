package io.trane.ndbc.value;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

public final class LocalTimeValue extends Value<LocalTime> {

  public LocalTimeValue(final LocalTime value) {
    super(value);
  }

  @Override
  public final LocalTime getLocalTime() {
    return get();
  }

  @Override
  public OffsetTime getOffsetTime() {
    return get().atOffset(ZoneOffset.UTC);
  }
}
