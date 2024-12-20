package com.kang.chinesetool.util;

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

class ChineseUtilTest {

    Logger log = Logger.getLogger(ChineseUtilTest.class.getName());

    @Test
    void loadData() {
    }

    @Test
    void toTraditional() {
        long startTime = System.currentTimeMillis();
        String traditional = ChineseUtil.toTraditional("在IntelliJ IDEA中，可以使用快捷键快速生成单元测试类和方法。默认的快捷键是Ctrl+Shift+T，用于创建新的测试类");
        log.info("traditional:" + traditional);
        log.info("toTraditional spend time:" + (System.currentTimeMillis() - startTime) / 1000.0);
    }

    @Test
    void toSimplified() {
        long startTime = System.currentTimeMillis();
        String toSimplified = ChineseUtil.toSimplified("一顆年輕的終於在世俗中成熟起來。厭倦的枯燥乏味的生活都成了一種習慣。慵懶地呼吸著混凝土與柏油路吐出的空氣。眼睛在高樓大廈間穿梭，尋覓心走過的路");
        log.info("toSimplified:" + toSimplified);
        log.info("toSimplified spend time:" + (System.currentTimeMillis() - startTime) / 1000.0);
    }

    @Test
    void translate() {
    }
}