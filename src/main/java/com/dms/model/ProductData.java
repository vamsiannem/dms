/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dms.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * Uploaded CSV data goes to this file.
 * @author vamsikrishna_a
 */
//@Entity
//@Table(name="product_data")
public class ProductData implements Serializable {
    private Long id;
    private Date date;
    
    
}
