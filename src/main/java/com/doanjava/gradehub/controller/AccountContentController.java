package com.doanjava.gradehub.controller;

import com.doanjava.gradehub.dto.AccountDto;
import com.doanjava.gradehub.dto.ApiResponse;
import com.doanjava.gradehub.dto.CreateAccountRequest;
import com.doanjava.gradehub.dto.UpdateAccountRequest;
import com.doanjava.gradehub.entity.NguoiDung;
import com.doanjava.gradehub.service.HttpClientService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import java.util.List;
import java.util.Optional;

public class AccountContentController {
    @FXML private TextField searchField;
    @FXML private ComboBox<String> roleFilterCombo;
    @FXML private Button addAccountBtn;
    @FXML private TableView<AccountDto> accountTable;
    @FXML private TableColumn<AccountDto, String> emailCol;
    @FXML private TableColumn<AccountDto, String> roleCol;
    @FXML private TableColumn<AccountDto, String> createdCol;
    @FXML private TableColumn<AccountDto, Void> actionsCol;
    @FXML private Label emptyStateLabel;

    private final ObservableList<AccountDto> accountList = FXCollections.observableArrayList();
    private HttpClientService httpClientService;

    @FXML
    private void initialize() {
        httpClientService = new HttpClientService();
        setupTable();
        setupSearchAndFilter();
        loadAccounts();
    }

    private void setupTable() {
        // Setup email column
        emailCol.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().email()));

        // Setup role column
        roleCol.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().vaiTro()));

        // Setup created date column
        createdCol.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().ngayTao() != null ?
                cellData.getValue().ngayTao().toString() : "N/A"));

        // Setup actions column
        actionsCol.setCellFactory(new Callback<TableColumn<AccountDto, Void>, TableCell<AccountDto, Void>>() {
            @Override
            public TableCell<AccountDto, Void> call(TableColumn<AccountDto, Void> param) {
                return new TableCell<AccountDto, Void>() {
                    private final Button editBtn = new Button("Sửa");
                    private final Button deleteBtn = new Button("Xóa");
                    private final Button resetBtn = new Button("Đặt lại MK");
                    private final HBox hbox = new HBox(5, editBtn, deleteBtn, resetBtn);

                    {
                        hbox.setAlignment(Pos.CENTER);
                        hbox.setPadding(new Insets(2));

                        editBtn.setOnAction(e -> {
                            AccountDto account = getTableView().getItems().get(getIndex());
                            handleEditAccount(account);
                        });

                        deleteBtn.setOnAction(e -> {
                            AccountDto account = getTableView().getItems().get(getIndex());
                            handleDeleteAccount(account);
                        });

                        resetBtn.setOnAction(e -> {
                            AccountDto account = getTableView().getItems().get(getIndex());
                            handleResetPassword(account);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : hbox);
                    }
                };
            }
        });

        accountTable.setItems(accountList);
    }

    private void setupSearchAndFilter() {
        // Populate role filter combo
        roleFilterCombo.getItems().addAll("Tất cả", "sinh_vien", "giang_vien", "quan_tri");
        roleFilterCombo.setValue("Tất cả");

        // Add listeners
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            loadAccounts();
        });

        roleFilterCombo.setOnAction(e -> {
            loadAccounts();
        });
    }

    private void loadAccounts() {
        try {
            String search = searchField.getText();
            String role = roleFilterCombo.getValue();
            if ("Tất cả".equals(role)) {
                role = null;
            }

            String url = "http://localhost:8080/api/accounts";
            if (search != null && !search.trim().isEmpty()) {
                url += "?search=" + search.trim();
                if (role != null) {
                    url += "&vaiTro=" + role;
                }
            } else if (role != null) {
                url += "?vaiTro=" + role;
            }

            ApiResponse<List<AccountDto>> response = httpClientService.getList(url, AccountDto.class);

            if ("SUCCESS".equals(response.getResult())) {
                accountList.clear();
                accountList.addAll(response.getData());
                updateEmptyState();
            } else {
                showValidationMessage("Lỗi khi tải danh sách tài khoản: " + response.getMessage());
            }
        } catch (Exception e) {
            showValidationMessage("Lỗi kết nối: " + e.getMessage());
        }
    }

    private void updateEmptyState() {
        if (accountList.isEmpty()) {
            emptyStateLabel.setVisible(true);
            accountTable.setVisible(false);
        } else {
            emptyStateLabel.setVisible(false);
            accountTable.setVisible(true);
        }
    }

    @FXML
    private void handleAddAccount() {
        // Create dialog for adding account
        Dialog<CreateAccountRequest> dialog = new Dialog<>();
        dialog.setTitle("Thêm tài khoản mới");
        dialog.setHeaderText("Nhập thông tin tài khoản");

        // Set the button types
        ButtonType saveButtonType = new ButtonType("Lưu", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the custom content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mật khẩu");
        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("sinh_vien", "giang_vien", "quan_tri");
        roleCombo.setValue("sinh_vien");

        grid.add(new Label("Email:"), 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(new Label("Mật khẩu:"), 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(new Label("Vai trò:"), 0, 2);
        grid.add(roleCombo, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the email field by default
        emailField.requestFocus();

        // Convert the result to a CreateAccountRequest when the save button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new CreateAccountRequest(
                    emailField.getText(),
                    passwordField.getText(),
                    roleCombo.getValue()
                );
            }
            return null;
        });

        Optional<CreateAccountRequest> result = dialog.showAndWait();
        result.ifPresent(request -> {
            try {
                ApiResponse<AccountDto> response = httpClientService.post("http://localhost:8080/api/accounts",
                    request, AccountDto.class);

                if ("SUCCESS".equals(response.getResult())) {
                    showConfirmation("Tạo tài khoản thành công");
                    loadAccounts();
                } else {
                    showValidationMessage("Lỗi khi tạo tài khoản: " + response.getMessage());
                }
            } catch (Exception e) {
                showValidationMessage("Lỗi kết nối: " + e.getMessage());
            }
        });
    }

    private void handleEditAccount(AccountDto account) {
        // Create dialog for editing account
        Dialog<UpdateAccountRequest> dialog = new Dialog<>();
        dialog.setTitle("Sửa tài khoản");
        dialog.setHeaderText("Cập nhật thông tin tài khoản");

        ButtonType saveButtonType = new ButtonType("Lưu", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField emailField = new TextField(account.email());
        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("sinh_vien", "giang_vien", "quan_tri");
        roleCombo.setValue(account.vaiTro());

        grid.add(new Label("Email:"), 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(new Label("Vai trò:"), 0, 1);
        grid.add(roleCombo, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new UpdateAccountRequest(
                    emailField.getText(),
                    roleCombo.getValue()
                );
            }
            return null;
        });

        Optional<UpdateAccountRequest> result = dialog.showAndWait();
        result.ifPresent(request -> {
            try {
                ApiResponse<AccountDto> response = httpClientService.put(
                    "http://localhost:8080/api/accounts/" + account.id(),
                    request, AccountDto.class);

                if ("SUCCESS".equals(response.getResult())) {
                    showConfirmation("Cập nhật tài khoản thành công");
                    loadAccounts();
                } else {
                    showValidationMessage("Lỗi khi cập nhật tài khoản: " + response.getMessage());
                }
            } catch (Exception e) {
                showValidationMessage("Lỗi kết nối: " + e.getMessage());
            }
        });
    }

    private void handleDeleteAccount(AccountDto account) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText("Bạn có chắc chắn muốn xóa tài khoản này?");
        alert.setContentText("Email: " + account.email());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                ApiResponse<Void> response = httpClientService.delete(
                    "http://localhost:8080/api/accounts/" + account.id());

                if ("SUCCESS".equals(response.getResult())) {
                    showConfirmation("Xóa tài khoản thành công");
                    loadAccounts();
                } else {
                    showValidationMessage("Lỗi khi xóa tài khoản: " + response.getMessage());
                }
            } catch (Exception e) {
                showValidationMessage("Lỗi kết nối: " + e.getMessage());
            }
        }
    }

    private void handleResetPassword(AccountDto account) {
        // Create dialog for resetting password
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Đặt lại mật khẩu");
        dialog.setHeaderText("Nhập mật khẩu mới cho tài khoản: " + account.email());

        ButtonType saveButtonType = new ButtonType("Lưu", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mật khẩu mới");

        grid.add(new Label("Mật khẩu mới:"), 0, 0);
        grid.add(passwordField, 1, 0);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return passwordField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newPassword -> {
            try {
                ApiResponse<Void> response = httpClientService.post(
                    "http://localhost:8080/api/accounts/" + account.id() + "/reset-password",
                    new ResetPasswordRequest(newPassword), Void.class);

                if ("SUCCESS".equals(response.getResult())) {
                    showConfirmation("Đặt lại mật khẩu thành công");
                } else {
                    showValidationMessage("Lỗi khi đặt lại mật khẩu: " + response.getMessage());
                }
            } catch (Exception e) {
                showValidationMessage("Lỗi kết nối: " + e.getMessage());
            }
        });
    }

    private void showValidationMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Thêm class record ResetPasswordRequest phía client
    record ResetPasswordRequest(String password) {}
}
