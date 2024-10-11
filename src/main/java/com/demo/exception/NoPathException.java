package com.demo.exception;

public class NoPathException extends RuntimeException {

        private static final long serialVersionUID = 1L;
        public NoPathException(String msg) {
            super(msg);
        }
}
