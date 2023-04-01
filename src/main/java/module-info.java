module com.sinkingships.sinkingships {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;


    opens com.sinkingships.controllers to javafx.fxml;
    exports com.sinkingships;
}