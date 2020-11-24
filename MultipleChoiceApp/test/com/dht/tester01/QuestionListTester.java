/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.tester01;

import com.dht.pojo.Choice;
import com.dht.pojo.Question;
import com.dht.services.QuestionServices;
import com.dht.services.Utils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
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
public class QuestionListTester {
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
    @Test
    public void testNoFilters() {
        try {
            List<Question> kq1 = QuestionServices.getQuestions("  ");
            List<Question> kq2 = QuestionServices.getQuestions(null);
            
            Assert.assertEquals(kq1.size(), kq2.size());
        } catch (SQLException ex) {
            Logger.getLogger(QuestionListTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testFilter() {
        try {
            String kw = " o   ";
            List<Question> kq1 = QuestionServices.getQuestions(kw);
            
            Assert.assertEquals(2, kq1.size());
            for (Question q: kq1)
                Assert.assertTrue(q.getContent().contains(kw.trim()));
        } catch (SQLException ex) {
            Logger.getLogger(QuestionListTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testDeleteSuccessful() {
        String questionId = "24cd4383-ea97-4396-bb44-f7466b89e30b";
        
        try {
            boolean kq = QuestionServices.deleteQuestion(questionId);
            Assert.assertTrue(kq);
            
            Question q = QuestionServices.getQuestionById(questionId);
            Assert.assertNull(q);
            
            List<Choice> choices = QuestionServices.getChoicesByQuestionId(questionId);
            Assert.assertEquals(0, choices.size());
        } catch (SQLException ex) {
            Logger.getLogger(QuestionListTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
