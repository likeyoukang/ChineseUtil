package com.kang.chinesetool.util;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ChineseUtil {

    private static HashMap<String, List<String>> traditionalMap = new HashMap<>();
    private static HashMap<String, List<String>> simplifiedMap = new HashMap<>();

    private static Logger log = Logger.getLogger(ChineseUtil.class.getName());

    private ChineseUtil() {
    }

    static {
        loadData("traditional_init.txt", traditionalMap);
        loadData("traditional.txt", traditionalMap);
        loadData("simplified_init.txt", simplifiedMap);
        loadData("simplified.txt", simplifiedMap);
        log.info("如果你想使用自定义的繁体字典，请在resources目录下创建traditional.txt文件，格式为：繁体字 简体字");
        log.info("如果你想使用自定义的简体字典，请在resources目录下创建simplified.txt文件，格式为：简体字 繁体字");
    }


    //读取resources 下的文件,把内容放在traditionalMap中
    private static void loadData(String fileName, Map<String, List<String>> dictMap) {
        InputStream inputStream = ChineseUtil.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            log.info(fileName + "文件不存在");
            return;
        }
        // 获取资源文件的输入流
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int currentCount = 0;
            while ((line = reader.readLine()) != null) {
                currentCount++;
                // 假设文件中每行格式为 key=value，可以根据实际情况调整
                String[] parts = line.split(" ");

                if (parts.length != 2) {
                    log.warning("文件%s中第%s行字符%s没有对应的映射关系,请检查".formatted(fileName, currentCount, parts[0]));
                    throw new RuntimeException("文件格式不正确");
                }
                if (parts[0].length() != parts[1].length()) {
                    log.warning("文件%s中第%s行字符%s无法对应的映射成%s,因为字符的长度不相同".formatted(fileName, currentCount, parts[0], parts[1]));
                    throw new RuntimeException("文件格式不正确");
                }

                //忽略
                if (parts[0].equals("□") || parts[1].equals("□")) {
                    continue;
                }

                List<String> traditionalList = dictMap.getOrDefault(parts[0], new ArrayList<>());
                traditionalList.add(parts[1]);
                dictMap.put(parts[0], traditionalList);

            }
        } catch (IOException e) {
            log.warning(fileName + "读取失败");
        }
    }

    /**
     *
     * 简体转繁体
     * @param input
     * @return
     */
    public static String toTraditional(String input) {
        return translate(input, traditionalMap);
    }

    /**
     * 简体转繁体 一对多
     * @param input
     * @return
     */
    public static List<Character> toTraditional(Character input) {
        return translate(input, traditionalMap);
    }

    /**
     * 繁体转简体
     * @param input
     * @return
     */
    public static String toSimplified(String input) {
        return translate(input, simplifiedMap);
    }

    /**
     * 繁体转简体 一对多
     * @param input
     * @return
     */
    public static List<Character> toSimplified(Character input) {
        return translate(input, simplifiedMap);
    }

    //public boolean isSimplified(Character input) {
    //    return toTraditional(input).size() > 0;
    //}


    public boolean isTraditional(Character input) {
        return toSimplified(input).size() > 0;
    }


    /**
     * 简繁互相转换
     * @param input
     * @param dict
     * @return
     */
    public static String translate(String input, Map<String, List<String>> dict) {
        if (StringUtils.isBlank(input)) {
            return input;
        }
        String resultString = "";
        for (int i = 0; i < input.length(); i++) {
            char originChar = input.charAt(i);
            List<String> translateList = dict.get(originChar + "");
            if (CollectionUtils.isEmpty(translateList)) {
                resultString += originChar;
            } else {
                resultString += translateList.get(translateList.size() - 1);
            }
        }
        return resultString;
    }


    public static List<Character> translate(Character input, Map<String, List<String>> dict) {
        List<String> translateList = dict.get(input + "");
        List<Character> resultList = new ArrayList<>();
        for (String translate : translateList) {
            resultList.add(translate.charAt(0));
        }
        return resultList;
    }


}
