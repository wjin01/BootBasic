package com.simple.basic;


import com.simple.basic.memo.MemoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestCode02 {

    @Autowired
    MemoMapper memoMapper;

    @Test
    public void testCode01() {
        String result = memoMapper.hello();
        System.out.println("현재시간:" + result);
    }


}
