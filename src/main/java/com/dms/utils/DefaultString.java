/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.utils;

import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

/**
 * Created by support on 6/12/14.
 */
public class DefaultString extends CellProcessorAdaptor {
    public DefaultString() {
        super();
    }

    public DefaultString(CellProcessor next) {
        // this constructor allows other processors to be chained after ParseDay
        super(next);
    }

    @Override
    public Object execute(Object value, CsvContext context) {
        String stringValue = value!=null ? value.toString() : null;
        return next.execute(stringValue, context);
    }
}
