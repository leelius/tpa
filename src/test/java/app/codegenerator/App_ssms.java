package app.codegenerator;

import sld.codegenerator.BuildCodePane;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * App(ssms,spring+springMVC+mybatis+maven+shiro+mybatisplus)
 *
 */
public class App_ssms extends Application {

	// 启用版本v02
	// 解决数据层和应用层耦合问题
	public static final String buildDate = "2020-04-23 19:39:11";

	public static BuildCodePane buildCodePane = new BuildCodePane();
	public static StringProperty currentPane = new SimpleStringProperty();

	public static sld.codegenerator.entity.Database mDb = null;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		String datebaseType = BuildCodePane.MySQL;
		String datebaseName = "tpa";
		String serverIp = "127.0.0.1";
		String user = "root";
		String password = "root";
		String port = "3306";

		// 全局配置
		String foldername = System.getProperty("user.dir");

		// foldername = "D:\\Users\\project\\sts\\ws";

		BorderPane mainPane = new BorderPane();

		mainPane.setTop(buildCodePane);
		currentPane.set("buildCodePane");

		Scene scene = new Scene(mainPane, 500, 300);

		buildCodePane.heightProperty().addListener(e -> {
			primaryStage.setHeight(buildCodePane.getHeight() + 50);
		});

		currentPane.addListener(e -> {
			primaryStage.setHeight(buildCodePane.getHeight() + 50);

		});

		buildCodePane.initial(datebaseType, serverIp, port, user, password, datebaseName, foldername);

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle(datebaseName + "代码自动生成器");
		// primaryStage.getIcons().add(new Image(filename));
		primaryStage.show();

	}
}
