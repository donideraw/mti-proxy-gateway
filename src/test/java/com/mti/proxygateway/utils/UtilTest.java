package com.mti.proxygateway.utils;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

class UtilTest {

    @Test
    void givenValidUri_whenGetPath_thenCorrect() {
        assertThat(UriUtil.getPath("http://localhost:8080/path"), is("/path"));
        assertThat(UriUtil.getPath("invalid uri input"), is(""));
    }
}
