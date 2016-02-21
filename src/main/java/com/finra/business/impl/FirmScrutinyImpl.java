package com.finra.business.impl;

import com.finra.beans.Firm;
import com.finra.business.FirmScrutiny;
import com.finra.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

/**
 * This class implements FirmScrutiny.
 * Determines whether the provided Firm is under scrutiny for today or not.
 * Created by ravender on 2/20/2016.
 */
public class FirmScrutinyImpl implements FirmScrutiny {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirmScrutinyImpl.class);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    // Utils can be injected here using DI
    private Utils utils = new Utils();

    /**
     * Returns whether provided Firm is under scrutiny OR not by reading the file contents.
     *
     * @param firm the firm which needs to be verified whether it's under scrutiny
     * @return whether the firm is under scrutiny or not
     */
    public boolean isFirmUnderScrutiny(final Firm firm) {

        if (null == firm || null == firm.getFirmID()) {
            LOGGER.debug("Provided Firm is null");
            return false;
        }

        // Checking provided firm is in the list of "always under scrutiny"
        if (utils.getConfig().getFirmsAlwaysUnderScrutiny().contains(firm.getFirmID())) {
            LOGGER.debug("Firm {} is always under scrutiny", firm.getFirmID());
            return true;
        }

        Path filePath = Paths.get(utils.getConfig().getFileAbsolutePath().replace(
                "today", LocalDate.now().format(formatter)));
        if (Files.exists(filePath)) {
            try (Stream<String> stream = Files.lines(filePath)) {
                return stream.anyMatch(id -> String.valueOf(firm.getFirmID()).equals(id));
            } catch (IOException ioException) {
                LOGGER.error("Problem reading from the file {}", ioException);
            }
        } else {
            LOGGER.info("Scrutiny file doesn't exist for today, Firm {} is not under scrutiny", firm.getFirmID());
        }
        return false;
    }

    public void setUtils(Utils utils) {
        this.utils = utils;
    }
}
