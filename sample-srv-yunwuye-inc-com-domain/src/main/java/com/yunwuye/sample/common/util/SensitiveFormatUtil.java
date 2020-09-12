/**
 *
 */
package com.yunwuye.sample.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunwuye.sample.common.base.enums.SensitiveFormatEnum;

/**
 * @author Roy
 *
 */
public class SensitiveFormatUtil {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFormatUtil.class);
    /** 处理关键字段的隐藏字符 */
    private static final String SUBSTITUE_CHARACTOR = "*";

    /**
     *
     * 按敏感信息格式化转换字符串信息
     *
     * @param sensitiveFormat
     * @param info
     * @return
     */
    public static String shield(SensitiveFormatEnum sensitiveFormat, String info) {
        // 屏蔽前六后四
        String shieldStr = StringUtils.EMPTY;
        switch (sensitiveFormat) {
        case SHIELDED_6_4:
            shieldStr = SensitiveFormatUtil.shieldCharacters_6_4(info);
            break;
        case SHIELDED__4:
            shieldStr = SensitiveFormatUtil.shieldCharacters__4(info);
            break;
        default:
            // 屏蔽全部字符串
            shieldStr = SensitiveFormatUtil.shieldAllCharacters(info);
            break;
        }
        logger.info("desensitization value={0}",shieldStr);
        return shieldStr;
    }

    /**
     *
     * 敏感信息处理,例如信用卡号和身份证号码都进行前六后四显示
     *
     * @param info  敏感信息
     * @return
     */
    public static String shieldCharacters_6_4(String info) {
        // 如果屏蔽信息不存在或长度小于10, 返回屏蔽所有信息
        if (info == null || info.length() <= 10) {
            return shieldAllCharacters(info);
        }
        StringBuffer sb = new StringBuffer();
        sb.append(StringUtils.substring(info, 0, 6));
        // 添加所需要的屏蔽的*号
        for (int i = 0; i < info.length() - 10; i++) {
            sb.append(SUBSTITUE_CHARACTOR);
        }
        sb.append(info.substring(info.length() - 4, info.length()));
        logger.info("desensitization value={0}",sb.toString());
        return sb.toString();
    }

    /**
     * 敏感信息处理,例如手机号都进行后四显示
     *
     * @param info 敏感信息
     * @return
     */
    public static String shieldCharacters__4(String info) {
        // 如果屏蔽信息不存在或长度小于10, 返回屏蔽所有信息
        if (info == null || info.length() <= 4) {
            return shieldAllCharacters(info);
        }
        StringBuffer sb = new StringBuffer();
        // 添加所需要的屏蔽的*号
        for (int i = 0; i < info.length() - 4; i++) {
            sb.append(SUBSTITUE_CHARACTOR);
        }
        sb.append(info.substring(info.length() - 4, info.length()));
        logger.info("desensitization value={0}",sb.toString());
        return sb.toString();
    }

    /**
     *
     * 屏蔽所有字符
     *
     * @param info   敏感信息
     * @return
     */
    public static String shieldAllCharacters(String info) {
        if (StringUtils.isEmpty(info)) {
            return StringUtils.EMPTY;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < info.length(); i++) {
            sb.append(SUBSTITUE_CHARACTOR);
        }
        logger.info("desensitization value={0}",sb.toString());
        return sb.toString();
    }
}
