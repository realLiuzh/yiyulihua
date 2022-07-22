package com.yiyulihua.oss;

import com.yiyulihua.oss.controller.OssController;
import com.yiyulihua.oss.service.OssService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunbo
 * @date 2022-07-22-20:34
 */
@SpringBootTest
public class TestService {
    @Autowired
    private OssController controller;

    @Test
    void test() {
      controller.deleteFile("https://edu-mogu.oss-cn-chengdu.aliyuncs.com/previewImage/2022/07/19/2e4d202d906644cf9c5d3ccc94abc1c5wallhaven-g7elo3.jpg");
    }
    @Test
    void testDeleteFiles(){

        List<String> list = new ArrayList<>();
        list.add("https://edu-mogu.oss-cn-chengdu.aliyuncs.com/2022/03/26/9664252468e7449d92c01983e618c74cfile.png");

        list.add("https://edu-mogu.oss-cn-chengdu.aliyuncs.com/2022/06/19/961c18f76d05439ca8fef5f8d4ed9554file.jpg/png");
        System.out.println(controller.deleteFiles(list));
    }
}
