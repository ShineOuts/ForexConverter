package Forex;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

//Base class for styled components
abstract class StyledComponent {
    protected String style;

    public StyledComponent(String style) {
        this.style = style;
    }

    //Lets subclasses create their own style logic
    public abstract void applyStyle();
}
//Button styles and hover effects
class StyledButton extends StyledComponent {
    private Button button;
    private String hoverStyle;

    public StyledButton(String text, String normalStyle, String hoverStyle) {
        super(normalStyle);
        this.button = new Button(text);
        this.hoverStyle = hoverStyle;
    }

    @Override
    public void applyStyle() {
        button.setStyle(style);
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(style));
    }

    public Button getButton() { return button; }
}
//Data class to hold conversation information
class ConversionData {
    private double amount;
    private String fromCurrency;
    private String toCurrency;

    public ConversionData(double amount, String from, String to) {
        this.amount = amount;
        this.fromCurrency = from;
        this.toCurrency = to;
    }


    public double getAmount() { return amount; }
    public String getFromCurrency() { return fromCurrency; }
    public String getToCurrency() { return toCurrency; }
}
//Interface for different conversion strategies
interface CurrencyConverter {
    double convert(ConversionData data);
}
//Standard Converter
class StandardConverter implements CurrencyConverter {
    @Override
    public double convert(ConversionData data) {
        return CurrencyExchange.convert(
                data.getAmount(),
                data.getFromCurrency(),
                data.getToCurrency()
        );
    }
}

// ===== MAIN APPLICATION =====
public class CurrencyConverterApp extends Application {

    //UI Components
    private TextField amountField;
    private ComboBox<String> fromCurrency;
    private ComboBox<String> toCurrency;
    private Label resultLabel;


    //Converter Strategies
    private CurrencyConverter converter;

    //Supported Currencies
    private String[] currencies = {"USD", "JPY", "KRW", "EUR", "GBP", "CNY", "CHF", "AUD", "CAD", "PHP"};

    @Override
    public void start(Stage primaryStage) {

        converter = new StandardConverter();

        //Main container with gradient backgroud
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #BAF4FD 0%, #02D3FB 100%);");

        //Main Currency Converter Title Text Box
        Label titleLabel = new Label("💱 Currency Converter");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-effect: dropshadow(gaussian, black, 2, 1.0, 0, 0);");

        //Surrounding White Background of the GUI
        VBox card = new VBox(15);
        card.setPadding(new Insets(30));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 20, 0, 0, 5);");
        card.setMaxWidth(400);

        //Amount Input Section
        Label amountLabel = new Label("Amount:");
        amountLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));

        amountField = new TextField();
        amountField.setPromptText("Enter amount (e.g., 100)");
        amountField.setFont(Font.font("Arial", 16));
        amountField.setStyle("-fx-padding: 10; -fx-border-color: #ddd; -fx-border-radius: 5;");

        //From Currency Section
        Label fromLabel = new Label("From Currency:");
        fromLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));

        fromCurrency = new ComboBox<>();
        fromCurrency.getItems().addAll(currencies);
        fromCurrency.setValue("USD");
        fromCurrency.setStyle("-fx-font-size: 16;");
        fromCurrency.setMaxWidth(Double.MAX_VALUE);

        //To Currency Section
        Label toLabel = new Label("To Currency:");
        toLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));

        toCurrency = new ComboBox<>();
        toCurrency.getItems().addAll(currencies);
        toCurrency.setValue("EUR");
        toCurrency.setStyle("-fx-font-size: 16;");
        toCurrency.setMaxWidth(Double.MAX_VALUE);

        //Convert Button with hover effects
        StyledButton styledButton = new StyledButton(
                "Convert",
                "-fx-background-color: #667eea; -fx-text-fill: white; -fx-padding: 12 40; -fx-background-radius: 8;",
                "-fx-background-color: #5568d3; -fx-text-fill: white; -fx-padding: 12 40; -fx-background-radius: 8; -fx-cursor: hand;"
        );
        styledButton.applyStyle();

        Button convertButton = styledButton.getButton();
        convertButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        convertButton.setMaxWidth(Double.MAX_VALUE);

        //Result Display Section
        VBox resultBox = new VBox(5);
        resultBox.setAlignment(Pos.CENTER);
        resultBox.setStyle("-fx-background-color: #f0f4ff; -fx-padding: 20; -fx-background-radius: 8;");

        Label resultTitle = new Label("Result:");
        resultTitle.setFont(Font.font("Arial", 12));
        resultTitle.setTextFill(Color.GRAY);

        resultLabel = new Label("0.00");
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        resultLabel.setTextFill(Color.web("#667eea"));

        resultBox.getChildren().addAll(resultTitle, resultLabel);

        //Add all component to the card
        card.getChildren().addAll(
                amountLabel, amountField,
                fromLabel, fromCurrency,
                toLabel, toCurrency,
                convertButton,
                resultBox
        );

        root.getChildren().addAll(titleLabel, card);

        //Event handlers
        convertButton.setOnAction(e -> performConversion());
        amountField.setOnAction(e -> performConversion());

        //SetupStage
        Scene scene = new Scene(root, 500, 650);
        primaryStage.setTitle("Currency Converter");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    //Performs the Currency Conversion and updates the result label
    private void performConversion() {
        try {
            ConversionData data = new ConversionData(
                    Double.parseDouble(amountField.getText()),
                    fromCurrency.getValue(),
                    toCurrency.getValue()
            );
            double result = converter.convert(data);

            resultLabel.setText(String.format("%.2f %s", result, data.getToCurrency()));

        } catch (NumberFormatException ex) {
            showError("Please enter a valid number!");
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    //Displays the Error Message Dialouge
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //Entry Point of the Code - Starts the Application
    public static void main(String[] args) {
        launch(args);
    }
}
