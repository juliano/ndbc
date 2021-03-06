package io.trane.ndbc.postgres.netty4;

import static org.junit.Assert.assertArrayEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import io.trane.future.CheckedFutureException;
import io.trane.ndbc.DataSource;
import io.trane.ndbc.PostgresDataSource;
import io.trane.ndbc.PostgresPreparedStatement;
import io.trane.ndbc.PostgresRow;
import io.trane.ndbc.PreparedStatement;
import io.trane.ndbc.Row;
import io.trane.ndbc.test.EncodingTest;

public class PostgresArrayEncodingTest extends EncodingTest<PostgresPreparedStatement, PostgresRow> {

  @Override
  protected PostgresPreparedStatement prepare(final String query) {
    return PostgresPreparedStatement.create(query);
  }

  @Parameters(name = "{1}")
  public static Collection<Object[]> data() {
    return PostgresEnv.dataSources.stream().map(array -> {
      @SuppressWarnings("unchecked")
      final PostgresDataSource ds = PostgresDataSource.create((DataSource<PreparedStatement, Row>) array[0]);
      return new Object[] { ds, array[1] };
    }).collect(Collectors.toList());
  }

  @Test
  public void offsetTimeArray() throws CheckedFutureException {
    testArray(offsetTimeColumnTypes(),
        PostgresPreparedStatement::setOffsetTimeArray,
        PostgresRow::getOffsetTimeArray,
        r -> {
          final OffsetTime[] array = new OffsetTime[r.nextInt(10)];
          for (int i = 0; i < array.length; i++)
            array[i] = randomLocalDateTime(r).toLocalTime().atOffset(randomZoneOffset(r));
          return array;
        });
  }

  @Test
  public void bigDecimalArray() throws CheckedFutureException {
    testArray(bigDecimalColumnTypes(), (ps, v) -> ps.setBigDecimalArray(v),
        PostgresRow::getBigDecimalArray, r -> {
          final BigDecimal[] array = new BigDecimal[r.nextInt(10)];
          for (int i = 0; i < array.length; i++)
            array[i] = BigDecimal.valueOf(r.nextLong(), r.nextInt(100));
          return array;
        });
  }

  @Test
  public void booleanArray() throws CheckedFutureException {
    testArray(booleanColumnTypes(), (ps, v) -> ps.setBooleanArray(v),
        PostgresRow::getBooleanArray, r -> {
          final Boolean[] array = new Boolean[r.nextInt(10)];
          for (int i = 0; i < array.length; i++)
            array[i] = r.nextBoolean();
          return array;
        });
  }

  @Test
  public void byteArrayArray() throws CheckedFutureException {
    testArray(byteArrayColumnTypes(), (ps, v) -> ps.setByteArrayArray(v),
        PostgresRow::getByteArrayArray, r -> {
          final byte[][] array = new byte[r.nextInt(10)][];
          for (int i = 0; i < array.length; i++) {
            final byte[] bytes = new byte[r.nextInt(5)];
            r.nextBytes(bytes);
            array[i] = bytes;
          }
          return array;
        });
  }

  @Test
  public void doubleArray() throws CheckedFutureException {
    testArray(doubleColumnTypes(), (ps, v) -> ps.setDoubleArray(v),
        PostgresRow::getDoubleArray, r -> {
          final Double[] array = new Double[r.nextInt(10)];
          for (int i = 0; i < array.length; i++)
            array[i] = r.nextDouble();
          return array;
        });
  }

  @Test
  public void floatArray() throws CheckedFutureException {
    test(floatColumnTypes(), (ps, v) -> ps.setFloatArray(v),
        PostgresRow::getFloatArray, r -> {
          final Float[] array = new Float[r.nextInt(10)];
          for (int i = 0; i < array.length; i++)
            array[i] = r.nextFloat();
          return array;
        }, (a, b) -> assertArrayEquals(toPrimitiveArray(a), toPrimitiveArray(b),
            0.001f));
  }

  private final float[] toPrimitiveArray(final Float[] a) {
    final float[] r = new float[a.length];
    for (int i = 0; i < a.length; i++)
      r[i] = a[i].floatValue();
    return r;
  }

  @Test
  public void integerArray() throws CheckedFutureException {
    testArray(integerColumnTypes(), (ps, v) -> ps.setIntegerArray(v),
        PostgresRow::getIntegerArray, r -> {
          final Integer[] array = new Integer[r.nextInt(10)];
          for (int i = 0; i < array.length; i++)
            array[i] = r.nextInt();
          return array;
        });
  }

  @Test
  public void localDateArray() throws CheckedFutureException {
    testArray(localDateColumnTypes(), (ps, v) -> ps.setLocalDateArray(v),
        PostgresRow::getLocalDateArray, r -> {
          final LocalDate[] array = new LocalDate[r.nextInt(10)];
          for (int i = 0; i < array.length; i++)
            array[i] = randomLocalDateTime(r).toLocalDate();
          return array;
        });
  }

  @Test
  public void localDateTimeArray() throws CheckedFutureException {
    testArray(localDateTimeColumnTypes(), (ps, v) -> ps.setLocalDateTimeArray(v),
        PostgresRow::getLocalDateTimeArray,
        r -> {
          final LocalDateTime[] array = new LocalDateTime[r.nextInt(10)];
          for (int i = 0; i < array.length; i++)
            array[i] = randomLocalDateTime(r);
          return array;
        });
  }

  @Test
  public void localTimeArray() throws CheckedFutureException {
    testArray(localTimeColumnTypes(), (ps, v) -> ps.setLocalTimeArray(v),
        PostgresRow::getLocalTimeArray, r -> {
          final LocalTime[] array = new LocalTime[r.nextInt(10)];
          for (int i = 0; i < array.length; i++)
            array[i] = randomLocalDateTime(r).toLocalTime();
          return array;
        });
  }

  @Test
  public void longArray() throws CheckedFutureException {
    testArray(longColumnTypes(), (ps, v) -> ps.setLongArray(v),
        PostgresRow::getLongArray, r -> {
          final Long[] array = new Long[r.nextInt(10)];
          for (int i = 0; i < array.length; i++)
            array[i] = r.nextLong();
          return array;
        });
  }

  @Test
  public void shortArray() throws CheckedFutureException {
    testArray(shortColumnTypes(), (ps, v) -> ps.setShortArray(v),
        PostgresRow::getShortArray, r -> {
          final Short[] array = new Short[r.nextInt(10)];
          for (int i = 0; i < array.length; i++)
            array[i] = (short) r.nextInt();
          return array;
        });
  }

  @Test
  public void stringArray() throws CheckedFutureException {
    testArray(stringColumnTypes(), (ps, v) -> ps.setStringArray(v),
        PostgresRow::getStringArray, r -> {
          final String[] array = new String[r.nextInt(10)];
          for (int i = 0; i < array.length; i++)
            array[i] = radomString(r, 100);
          return array;
        });
  }

  @Override
  protected List<String> bigDecimalColumnTypes() {
    return Arrays.asList("numeric[]");
  }

  protected List<String> bigIntegerColumnTypes() {
    return Arrays.asList("bigint[]");
  }

  @Override
  protected List<String> booleanColumnTypes() {
    return Arrays.asList("bool[]");
  }

  @Override
  protected List<String> byteArrayColumnTypes() {
    return Arrays.asList("bytea[]");
  }

  @Override
  protected List<String> doubleColumnTypes() {
    return Arrays.asList("float8[]");
  }

  @Override
  protected List<String> floatColumnTypes() {
    return Arrays.asList("float4[]");
  }

  @Override
  protected List<String> integerColumnTypes() {
    return Arrays.asList("int4[]");
  }

  @Override
  protected List<String> localDateColumnTypes() {
    return Arrays.asList("date[]");
  }

  @Override
  protected List<String> localDateTimeColumnTypes() {
    return Arrays.asList("timestamp[]");
  }

  @Override
  protected List<String> localTimeColumnTypes() {
    return Arrays.asList("time[]");
  }

  @Override
  protected List<String> longColumnTypes() {
    return Arrays.asList("int8[]");
  }

  protected List<String> offsetTimeColumnTypes() {
    return Arrays.asList("timetz[]");
  }

  @Override
  protected List<String> shortColumnTypes() {
    return Arrays.asList("int2[]");
  }

  @Override
  protected List<String> byteColumnTypes() {
    return Arrays.asList("smallint[]");
  }

  @Override
  protected List<String> stringColumnTypes() {
    return Arrays.asList("text[]", "varchar[]");
  }
}
