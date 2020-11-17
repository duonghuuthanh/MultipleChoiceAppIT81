/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplechoiceapp;

import com.dht.pojo.Category;
import com.dht.pojo.Choice;
import com.dht.pojo.Question;
import com.dht.services.CategoryServices;
import com.dht.services.QuestionServices;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Admin
 */
public class FXMLDocumentController implements Initializable {
    @FXML TextField txtContent;
    @FXML ComboBox<Category> cbCategories;
    @FXML TextField txtA;
    @FXML TextField txtB;
    @FXML TextField txtC;
    @FXML TextField txtD;
    @FXML CheckBox chkA;
    @FXML CheckBox chkB;
    @FXML CheckBox chkC;
    @FXML CheckBox chkD;
    @FXML TableView<Question> tbQuestions;
    @FXML TextField txtKeyword;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            cbCategories.getItems().addAll(CategoryServices.getCategories());
            loadQuestions();
            loadData("");
                    } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        txtKeyword.textProperty().addListener(et -> {
            try {
                loadData(txtKeyword.getText());
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
    public void addQuestionHandler(ActionEvent evt) {
        Question q = new Question(UUID.randomUUID().toString(), 
                txtContent.getText(), 
                cbCategories.getSelectionModel().getSelectedItem());
        List<Choice> choices = new ArrayList<>();
        choices.add(new Choice(UUID.randomUUID().toString(), 
                txtA.getText(), q, chkA.isSelected()));
        choices.add(new Choice(UUID.randomUUID().toString(), 
                txtB.getText(), q, chkB.isSelected()));
        choices.add(new Choice(UUID.randomUUID().toString(), 
                txtC.getText(), q, chkC.isSelected()));
        choices.add(new Choice(UUID.randomUUID().toString(), 
                txtD.getText(), q, chkD.isSelected()));
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (QuestionServices.addQuestion(q, choices) == true) {
            alert.setContentText("SUCCESSFUL");
            try {
                tbQuestions.getItems().clear();
                tbQuestions.setItems(FXCollections.observableArrayList(QuestionServices.getQuestions("")));
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            alert.setContentText("FAILED");
        }
        
        alert.show();
    }
    
    private void loadQuestions() throws SQLException {
        TableColumn colId = new TableColumn("Mã câu hỏi");
        colId.setCellValueFactory(new PropertyValueFactory("Id"));
        
        TableColumn colContent = new TableColumn("Nội dung câu hỏi");
        colContent.setCellValueFactory(new PropertyValueFactory("content"));
        
        
        tbQuestions.getColumns().addAll(colId, colContent);
    }
    
    private void loadData(String kw) throws SQLException {
        tbQuestions.getItems().clear();
        tbQuestions.setItems(FXCollections.observableArrayList(QuestionServices.getQuestions(kw)));
    }
    
}
