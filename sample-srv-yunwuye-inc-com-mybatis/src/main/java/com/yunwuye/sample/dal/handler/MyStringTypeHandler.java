package com.yunwuye.sample.dal.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 *
 * @author Roy
 * @param <T>
 *          第三步，在我们需要使用的字段上指定，比如：插入值的时候，从Java 类型到JDBC 类型，在字段属性中指定typehandler：
 * 
 *          <pre>
 *  <insert id="insertBlog" parameterType="com.yunwuye.sample.dal.entity.TUser"> 
 *    insert into t_user (name, age)
 *     values (
 *      #{name,jdbcType=VARCHAR,typeHandler=com.yunwuye.sample.dal.handler.MyStringTypeHandler}, 
 *      #{age,jdbcType=INTEGER}
 *     )
 *  </insert>
 * </pre>
 * 
 *          返回值的时候，从JDBC 类型到Java 类型，在resultMap 的列上指定typehandler：
 * 
 *          <pre>
 *   <result column="name" property="name" jdbcType="VARCHAR"
 *       typeHandler="com.yunwuye.sample.dal.handler.MyStringTypeHandler"/>
 * </pre>
 * 
 * @date 2020年6月27日-下午6:09:37
 */
public class MyStringTypeHandler extends BaseTypeHandler<String> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
    // 设置 String 类型的参数的时候调用，Java类型到JDBC类型
    // 注意只有在字段上添加typeHandler属性才会生效
    // insertBlog name字段
    System.out.println("---------------setNonNullParameter1：" + parameter);
    ps.setString(i, parameter);
  }

  @Override
  public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
    // 根据列名获取 String 类型的参数的时候调用，JDBC类型到java类型
    // 注意只有在字段上添加typeHandler属性才会生效
    System.out.println("---------------getNullableResult1：" + columnName);
    return rs.getString(columnName);
  }

  @Override
  public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    // 根据下标获取 String 类型的参数的时候调用
    System.out.println("---------------getNullableResult2：" + columnIndex);
    return rs.getString(columnIndex);
  }

  @Override
  public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    System.out.println("---------------getNullableResult3：");
    return cs.getString(columnIndex);
  }

}
