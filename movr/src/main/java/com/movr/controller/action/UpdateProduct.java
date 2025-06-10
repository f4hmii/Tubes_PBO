package com.movr.controller.action;

import com.movr.model.Product;
import com.movr.model.ProductDAO;

public class UpdateProduct implements ProductAction {
    private ProductDAO productDAO;
    private Product product;

    public UpdateProduct(ProductDAO productDAO, Product product) {
        this.productDAO = productDAO;
        this.product = product;
    }

    @Override
    public boolean execute() {
        return productDAO.updateProduct(product);
    }
}