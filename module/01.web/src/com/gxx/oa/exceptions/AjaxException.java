package com.gxx.oa.exceptions;

/**
 * ajax�쳣
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 19:45
 */
public class AjaxException extends RuntimeException {
    /**
     * ajax�쳣
     * @param errorMessage
     */
    public AjaxException(String errorMessage){
        super(errorMessage);
    }
}
