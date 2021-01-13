package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TablePanelProductController {

    public static final String JDBC_SQLITE_PRODUCT_MANAGEMENT_DB = "jdbc:sqlite:product_management.db";

    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, String> clName;
    @FXML
    private TableColumn<Product, String> clCode;
    @FXML
    private TableColumn<Product, String> clPrice;
    @FXML
    private TableColumn<Product, String> clQuantity;
    @FXML
    private TableColumn<Product, String> clTotal;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtProduct;
    @FXML
    private TextField txtQuantity;
    @FXML
    private Button btnSave;

    private ObservableList<Product> products;

    public void save (Product p) {
        final String sql = "INSERT INTO product (code, name, price, quantity) VALUES (?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(JDBC_SQLITE_PRODUCT_MANAGEMENT_DB);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Preenche os valores para (?,?,?,?)
            stmt.setString(1, p.getCode());
            stmt.setString(2, p.getName());
            stmt.setDouble(3, p.getPrice());
            stmt.setInt(4, p.getQuantity());

            // Execua o comando SQL
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> findAll(){
        List<Product> result = new ArrayList<>();
        final String sql = "SELECT * FROM product";

        try(Connection conn = DriverManager.getConnection(JDBC_SQLITE_PRODUCT_MANAGEMENT_DB);
            PreparedStatement stmt = conn.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Product p = new Product(rs.getString("code"), rs.getString("name"), rs.getDouble("price"), rs.getInt("quantity"));
                result.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Product find(String pKey){
        Product p = null;
        final String sql = "SELECT * FROM product WHERE code = ?";

        try(Connection conn = DriverManager.getConnection(JDBC_SQLITE_PRODUCT_MANAGEMENT_DB);
                PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, pKey);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                p = new Product(rs.getString("code"), rs.getString("name"), rs.getDouble("price"), rs.getInt("quantity"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public void update(Product p){
        final String sql = "UPDATE product SET name = ?, quantity = ?, price = ? WHERE code = ?";

        try(Connection conn = DriverManager.getConnection(JDBC_SQLITE_PRODUCT_MANAGEMENT_DB);
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, p.getName());
            stmt.setInt(2, p.getQuantity());
            stmt.setDouble(3, p.getPrice());
            stmt.setString(4, p.getCode());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String pKey){
        final String sql = "DELETE FROM product WHERE code = ?";

        try(Connection conn = DriverManager.getConnection(JDBC_SQLITE_PRODUCT_MANAGEMENT_DB);
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, pKey);
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize(){
        bindTableToList();
        bindEntityToColumn();
        loadTableFromDataBase();
    }

    private void bindTableToList() {
        products = FXCollections.observableArrayList();
        tableView.setItems(products);
    }

    private void bindEntityToColumn() {
        clCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        clName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        clQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        clTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

    }

    private void loadTableFromDataBase() {
        products.clear();
        products.addAll(findAll());
    }

    private Product getProductFromView() {
        String code = txtCode.getText();
        String name = txtProduct.getText();
        Double price = Double.valueOf(txtPrice.getText());
        Integer quantity = Integer.valueOf(txtQuantity.getText());
        Product p = new Product(code, name, price, quantity);
        return p;
    }

    private void clearInputFields() {
        txtCode.setText("");
        txtProduct.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
    }

    public void saveOrUpdate(ActionEvent actionEvent) {

        if(btnSave.getText().equals("Update")) {
            Product p = getProductFromView();
            update(p);
            clearInputFields();
            loadTableFromDataBase();
            btnSave.setText("Save");
            return;
        }

        save(getProductFromView());
        clearInputFields();
        loadTableFromDataBase();
    }

    public void delete(ActionEvent actionEvent) {
        Product p = tableView.getSelectionModel().getSelectedItem();

        if(p != null) {
            String code = p.getCode();
            delete(code);
        }
        loadTableFromDataBase();
    }

    public Product mouseClicked(MouseEvent mouseEvent) {
        Product p = null;
        try {
            p = tableView.getSelectionModel().getSelectedItem();

            txtCode.setText(p.getCode());
            txtProduct.setText(p.getName());
            txtPrice.setText(String.valueOf(p.getPrice()));
            txtQuantity.setText(String.valueOf(p.getQuantity()));

            btnSave.setText("Update");


        } catch (Exception e) {
            System.out.println("Não clicou em nada!");;
        }
        return p;
    }
}
