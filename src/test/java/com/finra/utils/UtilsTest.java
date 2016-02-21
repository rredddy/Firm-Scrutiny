package com.finra.utils;

import com.finra.config.FirmConfig;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ravender on 2/20/2016.
 */
public class UtilsTest {

    @Test
    public void testUtils() {
        Set<Integer> preSet = new HashSet<>(Arrays.asList(4390116, 8675309, 7365000));
        Utils util = new Utils();
        FirmConfig config = util.getConfig();
        assertNotNull(config);
        assertEquals("/user/finra/scrutinyFirms_{today}.txt", config.getFileAbsolutePath());
        assertTrue(preSet.containsAll(config.getFirmsAlwaysUnderScrutiny()));
    }
}
