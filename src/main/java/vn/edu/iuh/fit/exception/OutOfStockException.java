package vn.edu.iuh.fit.exception;

import vn.edu.iuh.fit.models.Product;

public class OutOfStockException extends Exception{
    private static final String DEFAULT_MESSAGE = "Not enough product";
    public OutOfStockException() {
        super(DEFAULT_MESSAGE);
    }
    public OutOfStockException(Product product) {
        super(String.format("Not enough product %s only %s left",product.getName(),product.getQuantity()));
    }
}
