module iiitd.byteme {
    requires javafx.controls;
    requires javafx.fxml;


    opens iiitd.byteme to javafx.fxml;
    exports iiitd.byteme;
    exports iiitd.byteme.database;
    opens iiitd.byteme.database to javafx.fxml;
    opens iiitd.byteme.handlers to javafx.fxml;
    opens iiitd.byteme.logistics to javafx.base;
    exports iiitd.byteme.handlers;
}