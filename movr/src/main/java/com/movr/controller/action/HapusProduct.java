package com.movr.controller.action;

import com.movr.model.ProductDAO;

public class HapusProduct implements ProductAction {
    private ProductDAO productDAO;
    private int productId;

    public HapusProduct(ProductDAO productDAO, int productId) {
        this.productDAO = productDAO;
        this.productId = productId;
    }

    @Override
    public boolean execute() {
        return productDAO.deleteProduct(productId);
    }
}