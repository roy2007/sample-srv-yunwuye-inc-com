package com.yunwuye.sample.common.utils;

import java.util.Stack;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

/**
 *
 * @author Roy
 *
 * @date 2020年7月5日-下午3:12:15
 */
public class JavaCodeFormatReplateUtil {
  // 注释（仅限/** */和/* */）
  final static String COMMENTARY_REGULAR = "/\\*{1,2}[\\s\\S]*?\\*/";
  // 注释 （仅限/** */）
  final static String BLOCK_COMMENTARY_REGULAR = "(^\\s*\\/\\*\\*)|(^\\s*\\*.*)";
  // 注释 （仅限//）
  final static String LINE_COMMENTARY_REGULAR = "\\/\\/.*";
  // 空白行
  final static String IS_BLANK_LING_REGULAR = "^\\s*\\n";
  // 行后空格
  final static String line_end_space = "\\r\\n";
  // 空格
  final static String _space = "\\s";

  public static String replaceByPattern() {
    Pattern p = Pattern.compile("(\\d)(.*)(\\d)");
    String input = "6 example input 4 ";
    String commont = "/**" + "* 你好这是一段注释 " + "* @author Roy" + "*" + "* @date 2020年7月5日-下午3:12:15" + "*/ //这是单 行注释 ";
    String output = null;
    Matcher m = p.matcher(input);
    if (m.find()) {
      // replace first number with "number" and second number with the first
      output = m.replaceFirst("number $3$1"); // number 46
    }
    System.out.println(output);

    p = Pattern.compile(COMMENTARY_REGULAR);
    m = p.matcher(commont);
    if (m.find()) {
      commont = m.replaceAll("11111 ");
    }
    System.out.println(commont);
    return output;
  }

  public static String replaceTextOfMatchGroup(String sourceString, Pattern pattern, int groupToReplace,
      Function<String, String> replaceStrategy) {
    Stack<Integer> startPositions = new Stack<>();
    Stack<Integer> endPositions = new Stack<>();
    Matcher matcher = pattern.matcher(sourceString);

    while (matcher.find()) {
      startPositions.push(matcher.start(groupToReplace));
      endPositions.push(matcher.end(groupToReplace));
    }
    StringBuilder sb = new StringBuilder(sourceString);
    while (!startPositions.isEmpty()) {
      int start = startPositions.pop();
      int end = endPositions.pop();
      if (start >= 0 && end >= 0) {
        sb.replace(start, end, replaceStrategy.apply(sourceString.substring(start, end)));
      }
    }
    return sb.toString();
  }

  public static String replaceGroup(String regex, String source, int groupToReplace, String replacement) {
    return replaceGroup(regex, source, groupToReplace, 1, replacement);
  }

  public static String replaceGroup(String regex, String source, int groupToReplace, int groupOccurrence,
      String replacement) {
    Matcher m = Pattern.compile(regex).matcher(source);
    for (int i = 0; i < groupOccurrence; i++)
      if (!m.find())
        return source; // pattern not met, may also throw an exception here
    return new StringBuilder(source).replace(m.start(groupToReplace), m.end(groupToReplace), replacement).toString();
  }

  @SuppressWarnings("deprecation")
  public static void main(String[] args) {
    System.out.println(replaceByPattern());
    // replace with "%" what was matched by group 1
    // input: aaa123ccc
    // output: %123ccc
    System.out.println(replaceGroup("([a-z]+)([0-9]+)([a-z]+)", "aaa123ccc", 1, "%"));

    // replace with "!!!" what was matched the 4th time by the group 2
    // input: a1b2c3d4e5
    // output: a1b2c3d!!!e5
    System.out.println(replaceGroup("([a-z])(\\d)", "a1b2c3d4e5", 2, 4, "!!!"));

    String input = "{\"_csrf\":[\"9d90c85f-ac73-4b15-ad08-ebaa3fa4a005\"],\"originPassword\":[\"123\"],\"newPassword\":[\"456\"],\"confirmPassword\":[\"456\"]}";
    String expected = "{\"_csrf\":[\"9d90c85f-ac73-4b15-ad08-ebaa3fa4a005\"],\"originPassword\":[\"**\"],\"newPassword\":[\"**\"],\"confirmPassword\":[\"**\"]}";
    Assert.isTrue(expected.equals(replaceByRegular(input, "**")));

    final String sourceString = "hello world!";

    final String regex = "(hello) (world)(!)";
    final Pattern pattern = Pattern.compile(regex);

    String result = replaceTextOfMatchGroup(sourceString, pattern, 2, world -> world.toUpperCase());
    System.out.println(result); // output: hello WORLD!
  }

  /**
   * {"_csrf":["9d90c85f-ac73-4b15-ad08-ebaa3fa4a005"],"originPassword":["uaas"],"newPassword":["uaas"],
   * "confirmPassword":["uaas"]}
   */
  private static final Pattern PATTERN = Pattern.compile(".*?password.*?\":\\[\"(.*?)\"\\](,\"|}$)",
      Pattern.CASE_INSENSITIVE);

  private static String replaceByRegular(String input, String replacement) {
    Matcher m = PATTERN.matcher(input);
    StringBuffer sb = new StringBuffer();
    while (m.find()) {
      Matcher m2 = PATTERN.matcher(m.group(0));
      if (m2.find()) {
        StringBuilder stringBuilder = new StringBuilder(m2.group(0));
        String result = stringBuilder.replace(m2.start(1), m2.end(1), replacement).toString();
        m.appendReplacement(sb, result);
      }
    }
    m.appendTail(sb);
    System.out.println(sb.toString());
    return sb.toString();
  }

}
