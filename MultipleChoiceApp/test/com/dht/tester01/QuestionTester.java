/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.tester01;

import com.dht.pojo.Category;
import com.dht.pojo.Question;
import com.dht.services.QuestionServices;
import com.dht.services.Utils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Admin
 */
public class QuestionTester {
    private static Connection conn;
    
    @BeforeClass
    public static void setUp() {
        conn = Utils.getConn();
    }
    
    @AfterClass
    public static void tearDown() {
        try {
            Utils.getConn().close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test(expected = NullPointerException.class)
    public void testQuestionContestIsEmpty() {
        Question q = new Question(UUID.randomUUID().toString(), "", new Category(1, "abc"));
        QuestionServices.addQuestion(q, null);
    }
    
    @Test
    public void testWithChoiceIsNull() {
        
    }
    
    @Test
    public void testQuestionLessThan2Choice() {
        
    }
}
