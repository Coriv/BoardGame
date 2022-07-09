module com.chess.chess {
    requires javafx.controls;
    requires javafx.fxml;

        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                    requires org.kordamp.bootstrapfx.core;
                requires com.almasb.fxgl.all;

    opens com.chess.chess to javafx.fxml;
    exports com.chess.chess;

}