package com.yunwuye.sample.dal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.yunwuye.sample.dal.entity.AccountUser;
import com.yunwuye.sample.dal.mapper.AccountUserMapper;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
  /**
   * Create the test case
   *
   * @param testName
   *          name of the test case
   */
  public AppTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(AppTest.class);
  }

  /**
   * Rigourous Test :-)
   */
  public void testApp() {
    assertTrue(true);
    try {
      // generator();
      testMapper();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unused")
  private void generator() throws Exception {
    List<String> warnings = new ArrayList<>();
    File configFile = new File("src/main/resources/generator/generatorConfig.xml");
    // "C:\\Users\\Roy\\workspace\\sample-srv-yunwuye-inc-com\\sample-srv-yunwuye-inc-com-mybatis\\src\\main\\resources\\generator\\generatorConfig.xml");
    ConfigurationParser confParser = new ConfigurationParser(warnings);
    Configuration config = confParser.parseConfiguration(configFile);
    DefaultShellCallback shellCallback = new DefaultShellCallback(true);
    MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
    myBatisGenerator.generate(null);
  }

  public void testMapper() throws IOException {
    String resource = "src/main/resources/mybatis/mybatis-config.xml";
    File configFile = new File(resource);
    InputStream inputStream = new FileInputStream(configFile);
    // InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mybatis-config.xml");
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    SqlSession session = sqlSessionFactory.openSession();
    try {
      AccountUserMapper mapper = session.getMapper(AccountUserMapper.class);
      AccountUser blog = mapper.selectByPrimaryKey(1L);
      System.out.println(blog);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
    }
  }
}
