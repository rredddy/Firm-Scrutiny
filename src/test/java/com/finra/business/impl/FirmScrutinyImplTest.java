package com.finra.business.impl;

import com.finra.beans.Firm;
import com.finra.config.FirmConfig;
import com.finra.utils.Utils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by ravender on 2/20/2016.
 */
public class FirmScrutinyImplTest {

    private FirmScrutinyImpl scrutiny;
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    @Mock
    private Utils utils;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        scrutiny = new FirmScrutinyImpl();
        scrutiny.setUtils(utils);
    }

    @Test
    public void testIsFirmUnderScrutinyWhenGivenFirmUnderScrutiny() throws IOException {

        when(utils.getConfig()).thenReturn(getFakeConfig(
                createTempFileWithProvidedFirmIDs(new ArrayList<>(Arrays.asList("123456"))).getAbsolutePath()));

        assertTrue(scrutiny.isFirmUnderScrutiny(getFakeFirm(123456)));
    }

    @Test
    public void testIsFirmUnderScrutinyWhenGivenFirmNotUnderScrutiny() throws IOException {

        when(utils.getConfig()).thenReturn(getFakeConfig(
                createTempFileWithProvidedFirmIDs(new ArrayList<>(Arrays.asList("567812"))).getAbsolutePath()));

        assertFalse(scrutiny.isFirmUnderScrutiny(getFakeFirm(123456)));
    }

    @Test
    public void testIsFirmUnderScrutinyFileNotPresent() throws IOException {

        when(utils.getConfig()).thenReturn(getFakeConfigWithInvalidFilePath());

        assertFalse(scrutiny.isFirmUnderScrutiny(getFakeFirm(123456)));
    }

    @Test
    public void testIsFirmUnderScrutinyFirmsAlwaysUnderScrutiny() throws IOException {

        when(utils.getConfig()).thenReturn(getFakeConfigWithInvalidFilePath());

        assertTrue(scrutiny.isFirmUnderScrutiny(getFakeFirm(4390116)));
        assertTrue(scrutiny.isFirmUnderScrutiny(getFakeFirm(8675309)));
        assertTrue(scrutiny.isFirmUnderScrutiny(getFakeFirm(7365000)));
    }

    @Test
    public void testIsFirmUnderScrutinyNullFirm() throws IOException {

        when(utils.getConfig()).thenReturn(getFakeConfigWithInvalidFilePath());

        assertFalse(scrutiny.isFirmUnderScrutiny(null));
    }

    @Test
    public void testIsFirmUnderScrutinyNullFirmID() throws IOException {

        when(utils.getConfig()).thenReturn(getFakeConfigWithInvalidFilePath());

        assertFalse(scrutiny.isFirmUnderScrutiny(getFakeFirm(null)));
    }

    private File createTempFileWithProvidedFirmIDs(List<String> listOfFirms) throws IOException {
        File input = temporaryFolder.newFolder("junit")
                .toPath()
                .resolve("scrutinyFirms_" + getDate() + ".txt")
                .toFile();

        try (FileWriter writer = new FileWriter(input)) {
            for(String firm : listOfFirms) {
                writer.write(firm + "\n");
            }
        }
        return input;
    }

    private String getDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private FirmConfig getFakeConfig(String absolutePath) {
        FirmConfig config = new FirmConfig();
        config.setFileAbsolutePath(absolutePath);
        config.setFirmsAlwaysUnderScrutiny(new HashSet<>(Arrays.asList(4390116, 8675309, 7365000)));
        return config;
    }

    private FirmConfig getFakeConfigWithInvalidFilePath() {
        FirmConfig config = new FirmConfig();
        config.setFileAbsolutePath("/junit/nofile.txt");
        config.setFirmsAlwaysUnderScrutiny(new HashSet<>(Arrays.asList(4390116, 8675309, 7365000)));
        return config;
    }

    private Firm getFakeFirm(Integer firmID) {
        Firm firm = new Firm();
        firm.setFirmID(firmID);
        return firm;
    }
}
