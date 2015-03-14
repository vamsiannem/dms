/*
 * @author: Vamsi Krishna
 * Copyright (c) 2015. All Rights Reserved
 */

package com.dms.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created by VamsiKrishna on 7/3/15.
 */
public class DN6 {

    public static void main(String[] args)
            throws Exception {

        File file = new File("/home/support/Documents/trial_projects/dms/project_docs/redmswork/test1.vlog");

        BufferedReader br = new BufferedReader(new FileReader(file));
        Scanner sc = new Scanner(file);
        String lastString = "";

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            lastString = lastString + line;
        }

        StringBuffer result = new StringBuffer();
        for (int i = 0; i < lastString.length(); i += 8) {
            result.append((char) Integer.parseInt(lastString.substring(i, i + 8), 2));
        }
        System.out.println(result);
    }

}