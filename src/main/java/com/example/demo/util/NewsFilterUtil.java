package com.example.demo.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
/**
 * 敏感词汇过滤替换为*
 */

public class NewsFilterUtil {
    private final static String WORDS = "WORDS";
    private final static String REPLACE_CHAR = "*";
    private static HashMap sensitiveWordMap;

    /**     * 最小匹配规则     */
    private static int minMatchTYpe = 1;
    /**     * 最大匹配规则     */
    private static int maxMatchType = 2;
    /**
     * 敏感词汇过滤替换为*     *
     * @param text 待检测文字
     * @return 替换后文字
     */
    public static String replaceWords(String text,List<String> words) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        if (CollectionUtils.isEmpty(words)) {
            return text;
        }
        //屏蔽敏感词汇
        return NewsFilterUtil.replaceSensitiveWord(words, text, NewsFilterUtil.maxMatchType);
    }

    /**
     * 替换敏感字字符
     *
     * @param data      敏感字集合
     * @param txt       待检查文本
     * @param matchType 匹配规则
     */
    private static String replaceSensitiveWord(List<String> data, String txt, int matchType) {
        if (sensitiveWordMap == null) {
            addSensitiveWord(data);
        }
        String resultTxt = txt;
        //获取所有的敏感词
        List<String> set = getSensitiveWord(txt, matchType);
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            resultTxt = resultTxt.replaceAll(iterator.next(), REPLACE_CHAR);
        }
        return resultTxt;
    }


    /**
     * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：
     */
    private static void addSensitiveWord(List<String> datas) {
        sensitiveWordMap = new HashMap(datas.size());
        Iterator<String> iterator = datas.iterator();
        Map<String, Object> now = null;
        Map now2 = null;
        while (iterator.hasNext()) {
            now2 = sensitiveWordMap;
            String word = iterator.next().trim(); //敏感词
            for (int i = 0; i < word.length(); i++) {
                char key_word = word.charAt(i);
                Object obj = now2.get(key_word);
                if (obj != null) { //存在
                    now2 = (Map) obj;
                } else { //不存在
                    now = new HashMap<String, Object>();
                    now.put("isEnd", "0");
                    now2.put(key_word, now);
                    now2 = now;
                }
                if (i == word.length() - 1) {
                    now2.put("isEnd", "1");
                }
            }
        }
    }

    /**
     * 获取内容中的敏感词
     * @param text      内容
     * @param matchType 匹配规则 1=不最佳匹配，2=最佳匹配
     * @return
     */
    private static List<String> getSensitiveWord(String text, int matchType) {
        List<String> words = new ArrayList<String>();
        Map now = sensitiveWordMap;
        int count = 0; //初始化敏感词长度
        int start = 0; //标志敏感词开始的下标
        for (int i = 0; i < text.length(); i++) {
            char key = text.charAt(i);
            now = (Map) now.get(key);
            if (now != null) { //存在
                count++;
                if (count == 1) {
                    start = i;
                }
                if ("1".equals(now.get("isEnd"))) { //敏感词结束
                    now = sensitiveWordMap; //重新获取敏感词库
                    words.add(text.substring(start, start + count)); //取出敏感词，添加到集合
                    count = 0; //初始化敏感词长度
                }
            } else { //不存在
                now = sensitiveWordMap;//重新获取敏感词库
                if (count == 1 && matchType == 1) { //不最佳匹配
                    count = 0;
                } else if (count == 1 && matchType == 2) { //最佳匹配
                    words.add(text.substring(start, start + count));
                    count = 0;
                }
            }
        }
        return words;
    }

}