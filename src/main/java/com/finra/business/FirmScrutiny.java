package com.finra.business;

import com.finra.beans.Firm;

/**
 * Determines whether the given Firm is under scrutiny for today or not.
 * Created by ravender on 2/20/2016.
 */
public interface FirmScrutiny {

     /**
     * Returns whether the given Firm is under scrutiny OR not by reading the file contents.
     *
     * @param firm the firm which needs to be verified whether it's under scrutiny
     * @return whether the Firm is under scrutiny or not
     */
    boolean isFirmUnderScrutiny(final Firm firm);
}
