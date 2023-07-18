package com.example.dogstar;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class test {

    @DisplayName("1 + 2 = 3")
    @Test
    public void Test(){
        int a =1;
        int b =2;
        Assertions.assertThat(a+b).isEqualTo(3);
    }

    @DisplayName("1 + 2 = 4")
    @Test
    public void failedTest(){
        int a =1;
        int b =2;
        Assertions.assertThat(a+b).isEqualTo(4);
    }

}
