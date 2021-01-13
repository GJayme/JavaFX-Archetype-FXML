package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.model.Product;
import org.example.view.ProductViewModel;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TablePanelProductController {
    @FXML
    private TableView<ProductViewModel> tableView;
    @FXML
    private TableColumn<ProductViewModel, String> clName;
    @FXML
    private TableColumn<ProductViewModel, String> clPrice;
    @FXML
    private TableColumn<ProductViewModel, String> clQuantity;
    @FXML
    private TableColumn<ProductViewModel, String> clTotal;
    @FXML
    private TextField txtProduct;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtQuantity;

    private ObservableList<ProductViewModel> products;

    @FXML
    private void initialize(){
        bindTableToList();
        bindEntityToColumn();
        setDataIntoTable();
    }

    private void bindTableToList() {
        products = FXCollections.observableArrayList();
        tableView.setItems(products);
    }

    private void bindEntityToColumn() {
        clName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        clQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        clTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

    }

    private void setDataIntoTable() {
        List<Product> productsFromDatabase = Arrays.asList(
                new Product("Corote", 3.40, 15),
                new Product("Paiero", 0.25, 20),
                new Product("38", 300.00, 1),
                new Product("Opala", 10000.00, 1)
        );

        List<ProductViewModel> productViewModels = productsFromDatabase.stream()
                .map(p -> ProductViewModel.of(p))
                .collect(Collectors.toList());

        products.addAll(productViewModels);
    }

    public void save(ActionEvent actionEvent) {
        String productName = txtProduct.getText();
        double productPrice = Double.parseDouble(txtPrice.getText());
        int productQuantity = Integer.parseInt(txtQuantity.getText());

        Product product = new Product(productName, productPrice, productQuantity);

        ProductViewModel productViewModel = ProductViewModel.of(product);

        products.add(productViewModel);
    }

    public void delete(ActionEvent actionEvent) {

        ProductViewModel productViewModel = tableView.getSelectionModel().getSelectedItem();

        if (productViewModel == null) {
            System.out.println("Nenhum item selecionado!");
            return;
        }

        txtProduct.setText(productViewModel.getName());
        txtPrice.setText(productViewModel.getPrice());
        txtQuantity.setText(productViewModel.getQuantity());

        products.remove(productViewModel);
    }

    public void clear(ActionEvent actionEvent) {
        products.removeAll();
    }
}
