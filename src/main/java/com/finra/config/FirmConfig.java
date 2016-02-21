package com.finra.config;

import java.util.HashSet;
import java.util.Set;

/**
 * This class having accessor methods for config values.
 * Created by ravender on 2/19/2016.
 */
public class FirmConfig {

    private String fileAbsolutePath;
    private Set<Integer> firmsAlwaysUnderScrutiny = new HashSet<>();

    public String getFileAbsolutePath() {
        return fileAbsolutePath;
    }

    public void setFileAbsolutePath(String fileAbsolutePath) {
        this.fileAbsolutePath = fileAbsolutePath;
    }

    // Instead of reading "always under scrutiny firms" from config file, can be stored and read from DB
    public Set<Integer> getFirmsAlwaysUnderScrutiny() {
        return firmsAlwaysUnderScrutiny;
    }

    public void setFirmsAlwaysUnderScrutiny(Set<Integer> firmsAlwaysUnderScrutiny) {
        this.firmsAlwaysUnderScrutiny = firmsAlwaysUnderScrutiny;
    }
}
