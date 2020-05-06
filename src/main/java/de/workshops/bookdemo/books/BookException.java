package de.workshops.bookdemo.books;

public class BookException extends RuntimeException {

    private static final long serialVersionUID = -8071479025510145030L;

    
    public BookException(String msg) {
        super(msg);
    }

}
