package com.github.nan.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author NanNan Wang
 */
public class PinyinUtil {

    /**
     * 获取拼音，不带音调，全部小写
     *
     * @param chinese 中文
     * @return 拼音
     */
    public static String getPinyinWithoutTone(String chinese) {
        // 配置拼音输出格式，不带音调，全部小写
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE); // 不带音调
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE); // 小写

        StringBuilder pinyin = new StringBuilder();

        for (char ch : chinese.toCharArray()) {
            try {
                // 只转换中文字符，其他字符保持不变
                if (Character.toString(ch).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(ch, format);
                    if (pinyins != null) {
                        pinyin.append(pinyins[0]);  // 使用第一个拼音
                    }
                } else {
                    pinyin.append(ch);  // 非中文字符保留原样
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
            pinyin.append(" ");  // 各个拼音之间添加空格
        }

        return pinyin.toString().trim();
    }

}
