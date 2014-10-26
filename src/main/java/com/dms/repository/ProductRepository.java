/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dms.repository;

import com.dms.model.ProductData;
import java.util.List;

/**
 *
 * @author Vamsi
 */
public interface ProductRepository {
    
    /**
     * 
     * @return  List of product names available in the product data table.
     */
    List<String> getAllAvailableProducts();
    
    /**
     * Get all the data for a company. This data is used to display
     * graphs, charts to the user.
     * @param companyName
     * @return list of product data.
     */
    List<ProductData> getDataForProduct(String companyName);
    
}
