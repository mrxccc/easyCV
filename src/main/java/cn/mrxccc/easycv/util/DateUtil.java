package cn.mrxccc.easycv.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 日期操作工具类
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss Z";
    public static final String DEFAULT_UTC_SSS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS Z";

    /**
     * 使用java8新线程安全time api
     */
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter
        .ofPattern(DEFAULT_DATETIME_PATTERN);
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter
        .ofPattern(DEFAULT_DATE_PATTERN);
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter
        .ofPattern(DEFAULT_TIME_PATTERN);
    public static final DateTimeFormatter YYYY_MM_DDTHH_MM_SSZ_FORMAT = DateTimeFormatter
        .ofPattern(DEFAULT_UTC_PATTERN);
    public static final DateTimeFormatter YYYY_MM_DDTHH_MM_SS_SSSZ_FORMAT = DateTimeFormatter
        .ofPattern(DEFAULT_UTC_SSS_PATTERN);

    /**
     * 将原非线程安全格式化改为线程私有，一定程度上保证线程安全性
     */
    private static final ThreadLocal<SimpleDateFormat> SIMPLE_DATETIME_FORMAT = ThreadLocal
        .withInitial(() -> new SimpleDateFormat(DEFAULT_DATETIME_PATTERN));
    private static final ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT = ThreadLocal
        .withInitial(() -> new SimpleDateFormat(DEFAULT_DATE_PATTERN));
    private static final ThreadLocal<SimpleDateFormat> SIMPLE_TIME_FORMAT = ThreadLocal
        .withInitial(() -> new SimpleDateFormat(DEFAULT_TIME_PATTERN));
    private static final ThreadLocal<SimpleDateFormat> SIMPLE_YYYY_MM_DDTHH_MM_SSZ_FORMAT = ThreadLocal
        .withInitial(() -> new SimpleDateFormat(DEFAULT_UTC_PATTERN));
    private static final ThreadLocal<SimpleDateFormat> SIMPLE_YYYY_MM_DDTHH_MM_SS_SSSZ_FORMAT = ThreadLocal
        .withInitial(() -> new SimpleDateFormat(DEFAULT_UTC_SSS_PATTERN));


    /**
     * 获取当前日期与时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDatetime() {
      return formatDatetime(System.currentTimeMillis());
    }

    /**
     * 获取当前日期
     *
     * @return yyyy-MM-dd
     */
    public static String getCurrentDate() {
      return formatDate(System.currentTimeMillis());
    }

    /**
     * 获取当前时间
     *
     * @return HH:mm:ss
     */
    public static String getCurrentTime() {
      return formatTime(System.currentTimeMillis());
    }

    /**
     * 格式化日期与时间 (默认时区)
     *
     * @param timestamp 时间戳
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDatetime(long timestamp) {
      return formatDatetime(timestamp, ZoneId.systemDefault());
    }

    public static String formatDatetime(long timestamp, ZoneId zoneId) {
      return DATETIME_FORMAT
          .format(ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), zoneId));
    }

    /**
     * 格式化日期（默认时区）
     *
     * @param timestamp 时间戳
     * @return yyyy-MM-dd
     */
    public static String formatDate(long timestamp) {
      return formatDate(timestamp, ZoneId.systemDefault());
    }

    public static String formatDate(long timestamp, ZoneId zoneId) {
      return DATE_FORMAT
          .format(ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), zoneId));
    }

    /**
     * 格式化时间  默认时区
     *
     * @param timestamp 时间戳
     * @return HH:mm:ss
     */
    public static String formatTime(long timestamp) {
      return formatTime(timestamp, ZoneId.systemDefault());
    }

    public static String formatTime(long timestamp, ZoneId zoneId) {
      return TIME_FORMAT
          .format(ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), zoneId));
    }

    /**
     * 格式为UTC格式
     *
     * @param timestamp 时间戳
     * @return 字符串 yyyy-MM-dd'T'HH:mm:ss Z
     */
    public static String formatYMDTHMSZ(long timestamp) {
      return formatYMDTHMSZ(timestamp, ZoneId.systemDefault());
    }

    public static String formatYMDTHMSZ(long timestamp, ZoneId zoneId) {
      return YYYY_MM_DDTHH_MM_SSZ_FORMAT
          .format(ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), zoneId));
    }

    /**
     * 格式为UTC 带纳秒格式
     *
     * @param timestamp 时间戳
     * @return 字符串 yyyy-MM-dd'T'HH:mm:ss:SSS Z
     */
    public static String formatYMDTHMSSSZ(long timestamp) {
      return formatYMDTHMSSSZ(timestamp, ZoneId.systemDefault());
    }

    public static String formatYMDTHMSSSZ(long timestamp, ZoneId zoneId) {
      return YYYY_MM_DDTHH_MM_SS_SSSZ_FORMAT
          .format(ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), zoneId));
    }

    // 字符串转Date接口

    /**
     * 解析日期与时间（基于默认时区）
     *
     * @param str yyyy-MM-dd HH:mm:ss
     * @return Date
     */
    @SneakyThrows
    public static Date parseDatetime(String str) {
      Date date = null;
      try {
        date = SIMPLE_DATETIME_FORMAT.get().parse(str);
      } catch (ParseException e) {
        logger.error("解析日期字符串出错！格式：{}", DEFAULT_DATETIME_PATTERN, e);
        throw e;
      }
      return date;
    }

    /**
     * 解析日期
     *
     * @param str yyyy-MM-dd
     * @return Date
     */
    @SneakyThrows
    public static Date parseDate(String str) {
      Date date = null;
      try {
        date = SIMPLE_DATE_FORMAT.get().parse(str);
      } catch (ParseException e) {
        logger.error("解析日期字符串出错！格式：{}", DEFAULT_DATE_PATTERN, e);
        throw e;
      }
      return date;
    }

    /**
     * 解析时间
     * 将使用1970-01-01 作为年份，使用时请注意
     * @param str HH:mm:ss
     * @return Date
     */
    @SneakyThrows
    public static Date parseTime(String str) {
      Date date = null;
      try {
        date = SIMPLE_TIME_FORMAT.get().parse(str);
      } catch (ParseException e) {
        logger.error("解析日期字符串出错！格式：{}", DEFAULT_TIME_PATTERN, e);
        throw e;
      }
      return date;
    }

    /**
     * 转换带时区的字符串为java对象
     * @param dateTimeStr yyyy-MM-dd'T'HH:mm:ss Z
     * @return
     */
    public static ZonedDateTime parse2YMDTHMSZ(String dateTimeStr) {
      return YYYY_MM_DDTHH_MM_SSZ_FORMAT.parse(dateTimeStr, ZonedDateTime::from);
    }

    /**
     * 转换带时区的字符串为java对象
     * @param dateTimeStr yyyy-MM-dd'T'HH:mm:ss:SSS Z
     * @return
     */
    public static ZonedDateTime parse2YMDTHMSSSZ(String dateTimeStr) {
      return YYYY_MM_DDTHH_MM_SS_SSSZ_FORMAT.parse(dateTimeStr, ZonedDateTime::from);
    }


    public static String now() {
      return format(LocalDateTime.now());
    }

    public static String now(String pattern) {
      return format(LocalDateTime.now(), pattern);
    }

    public static String nowDate() {
      return formatDate(LocalDate.now());
    }

    public static String nowDate(String pattern) {
      return formatDate(LocalDate.now(), pattern);
    }

    public static String nowTime() {
      return formatTime(LocalTime.now());
    }

    public static String nowTime(String pattern) {
      return formatTime(LocalTime.now(), pattern);
    }

    public static String format(LocalDateTime dateTime) {
      return format(dateTime, DEFAULT_DATETIME_PATTERN);
    }

    public static String format(LocalDateTime dateTime, String pattern) {
      return dateTime.format(formatter(pattern));
    }

    public static String format(Date date, String pattern) {
      return format(toLocalDateTime(date), pattern);
    }

    public static String formatDate(LocalDate date) {
      return formatDate(date, DEFAULT_DATE_PATTERN);
    }

    public static String formatDate(LocalDate date, String pattern) {
      return date.format(formatter(pattern));
    }

    public static String formatTime(LocalTime time) {
      return formatTime(time, DEFAULT_TIME_PATTERN);
    }

    public static String formatTime(LocalTime time, String pattern) {
      return time.format(formatter(pattern));
    }

    public static LocalDateTime parse(String dateStr, String pattern) {
      return LocalDateTime.parse(dateStr, formatter(pattern));
    }

    public static LocalDate parseDate(String dateStr, String pattern) {
      return LocalDate.parse(dateStr, formatter(pattern));
    }

    public static LocalTime parseTime(String dateStr, String pattern) {
      return LocalTime.parse(dateStr, formatter(pattern));
    }

    public static Date parseAndToDate(String dateStr, String pattern) {
      return toDate(parse(dateStr, pattern));
    }

    public static Date parseDateAndToDate(String dateStr, String pattern) {
      return toDate(parseDate(dateStr, pattern));
    }

    public static Date parseTimeAndToDate(String dateStr, String pattern) {
      return toDate(parseTime(dateStr, pattern));
    }

    public static LocalDateTime toLocalDateTime(Date date) {
      return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDate toLocalDate(Date date) {
      return toLocalDateTime(date).toLocalDate();
    }

    public static LocalTime toLocalTime(Date date) {
      return toLocalDateTime(date).toLocalTime();
    }

    public static Date toDate(LocalDateTime dateTime) {
      return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(LocalDate date) {
      return toDate(date.atStartOfDay());
    }

    public static Date toDate(LocalTime time) {
      return toDate(LocalDateTime.of(LocalDate.now(), time));
    }

    private static DateTimeFormatter formatter(String pattern) {
      return DateTimeFormatter.ofPattern(pattern);
    }

    public static String todayOfWeekDisplayName() {
      return dayOfWeekDisplayName(LocalDate.now());
    }

    private static String dayOfWeekDisplayName(LocalDate date) {
      return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINA);
    }

    public static boolean inSameWeek(LocalDate day, LocalDate other) {
      if (day.isEqual(other)) {
        return true;
      }
      LocalDate before;
      LocalDate after;
      if (day.isBefore(other)) {
        before = day;
        after = other;
      } else {
        before = other;
        after = day;
      }

      int diff = 0;
      switch (before.getDayOfWeek()) {
        case MONDAY:
          diff = 7;
          break;
        case TUESDAY:
          diff = 6;
          break;
        case WEDNESDAY:
          diff = 5;
          break;
        case THURSDAY:
          diff = 4;
          break;
        case FRIDAY:
          diff = 3;
          break;
        case SATURDAY:
          diff = 2;
          break;
        case SUNDAY:
          diff = 1;
          break;
        default:
      }
      return before.plusDays(diff).isAfter(after);
    }

    public static LocalDate firstDayOfCurrentMonth() {
      return LocalDate.now().withDayOfMonth(1);
    }

    public static LocalDate firstDayOfCurrentWeek() {
      return dayOfCurrentWeek(1);
    }

    public static LocalDate lastDayOfCurrentWeek() {
      return dayOfCurrentWeek(7);
    }

    public static LocalDate dayOfCurrentWeek(int dayOfWeek) {
      return LocalDate.now().with(WeekFields.ISO.dayOfWeek(), dayOfWeek);
    }
    public static String formatYMDTHMSZ(Date date) {
      if (null == date) {
        return "";
      }
      return formatYMDTHMSZ(date.getTime());
    }

    public static String formatYMDTHMSSSZ(Date date) {
      if (null == date) {
        return "";
      }
      return formatYMDTHMSSSZ(date.getTime());
    }


    @SneakyThrows
    public static Date parseYMDTHMSZ(String dateStr) {
      if (StringUtils.isBlank(dateStr)) {
        return null;
      }
      Date dateTime = null;
      try {
        dateTime = SIMPLE_YYYY_MM_DDTHH_MM_SSZ_FORMAT.get().parse(dateStr);
      } catch (ParseException e) {
        logger.error("解析日期字符串出错！格式：{}", DEFAULT_UTC_PATTERN, e);
          throw e;
      }
      return dateTime;
    }

    @SneakyThrows
    public static Date parseYMDTHMSSSZ(String dateStr) {
      if (StringUtils.isBlank(dateStr)) {
        return null;
      }
      Date dateTime = null;
      try {
        dateTime = SIMPLE_YYYY_MM_DDTHH_MM_SS_SSSZ_FORMAT.get().parse(dateStr);
      } catch (ParseException e) {
        logger.error("解析日期字符串出错！格式：{}", DEFAULT_UTC_SSS_PATTERN, e);
        throw e;
      }
      return dateTime;
    }
}
