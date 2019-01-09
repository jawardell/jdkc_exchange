package jdkc_exchange;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.io.*;
import java.time.LocalDateTime;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * 
 * Built from the GUI Proposal Document 
 * found here:                  https://github.com/vsu-se/team2_fall18/wiki/guiDesign.pdf
 * implementation details:      https://github.com/vsu-se/team2_fall18/blob/wardell_2/docs/GUIdesign_more.pdf
 * 
 *
 */

public class Driver extends Application {

	private Stage primaryStage;
	private boolean saved, noClicked;
	private SiteManager sm;
	HBox back;
	private Stack<Pane> paneStack = new Stack<Pane>();


	private static final int WIDTH = 1010;
	private static final int HEIGHT = 900;
	private static final String LOGOPATH = "resources/logo.png";
	private static final String CSSPATH = "style.css";
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		/**
		 * initialize flags
		 */
		saved = false;
		noClicked = false;
		this.primaryStage = primaryStage;

		/**
		 * initialize scene
		 */
		primaryStage.setTitle("Sprint 3"); 
		primaryStage.setScene(makeScene(buildOpeningPane())); 
		primaryStage.show();

	}

	private Pane buildOpeningPane() {
		GridPane openPane = new GridPane();
		openPane.setAlignment(Pos.BASELINE_CENTER);
		HBox hboxBanner = buildBanner();
		HBox hboxSessionChoice = new HBox(10);
		Button btnLoadSession = new Button("Load Session");
		Button btnNewSession = new Button("New Session");

		btnLoadSession.setOnAction(e -> {
			paneStack.push(buildOpeningPane());
			primaryStage.setScene(makeScene(buildLoadingPane()));
		});

		btnNewSession.setOnAction(e -> {
			sm = new SiteManager();
			paneStack.push(buildOpeningPane());
			primaryStage.setScene(makeScene(buildSessionPane()));
		});
		
		GridPane g = new GridPane();
		g.add(btnLoadSession, 0, 0);
		g.add(new Label("\t\t\t\t"), 1, 0);
		g.add(btnNewSession, 2, 0);
		hboxSessionChoice.getChildren().addAll(g);
		hboxSessionChoice.setAlignment(Pos.BASELINE_CENTER);
		openPane.add(hboxBanner, 0, 0);
		openPane.add(new Label("\n\n\n\n\n\n\n"), 0, 1);
		openPane.add(hboxSessionChoice, 0, 2);
		openPane.setPadding(new Insets(40, 40, 40, 40));
		return openPane;
	}

	private Pane buildLoadingPane() {
		GridPane loadPane = new GridPane();
		loadPane.setAlignment(Pos.BASELINE_CENTER);
		HBox hboxBanner = buildBanner();
		HBox hboxBrowseButtons = new HBox();
		HBox bottomRow = new HBox();
		GridPane gp = new GridPane();
		Label title = new Label("Load Session");
		title.setId("titleHeader");
		gp.add(title, 0, 0);
		gp.add(new TextField(), 0, 3);
		Button btnBrowse = new Button("Browse");
		btnBrowse.setId("browseButton");
		Button btnContinue = new Button("Continue");
		Button backBtn = new Button("Back");
		TextField tfUpload = new TextField();
		tfUpload.setEditable(false);
		FileChooser fileChooser = new FileChooser();
		Label contErr = new Label("Must choose a save file to continue");
 	   	contErr.setTextFill(Color.RED);
 	   	
		btnBrowse.setOnAction(e -> {
			File file = fileChooser.showOpenDialog(primaryStage);
			if(file != null) {
				tfUpload.setText(file.getAbsolutePath());
			}
		});
		
		btnContinue.setOnAction(e -> {
			if(tfUpload.getText().trim().isEmpty()) {
				PauseTransition hide = new PauseTransition( Duration.seconds(3) );
     			hide.setOnFinished( event -> contErr.setVisible(false) );
     			hide.play();
				Alert alert = new Alert(AlertType.ERROR, "You must select a file before you can load the site manager.");
				alert.showAndWait();
			} else {

							File file = new File(tfUpload.getText());
							
							Thread thread = new Thread() {
								public void run() {

									sm = new SiteManager();
									sm = PersistenceManager.read(file);

								}

							};
							Label saveMsg = null;
							try {
								thread.start();
								loadPane.getChildren().clear();
								saveMsg = new Label("Saving Sesion ");
								loadPane.getChildren().add(saveMsg);
								while (thread.isAlive()) {
									saveMsg.setText(saveMsg.getText() + ". ");
								}
							} catch(Exception ex) {
								if(ex instanceof FileNotFoundException 
										|| ex instanceof NullPointerException) {
									saveMsg.setText("Session Import Failed. See error log.");
								}
							}
							
							saveMsg.setText("Session Imported Successfully!");
							
							primaryStage.setScene(makeScene(buildSessionPane()));
			}
			
			
		});
		
		backBtn.setOnAction(b -> {
			primaryStage.setScene(makeScene(paneStack.pop()));
		});
		
		gp.setPadding(new Insets(40, 40, 40, 40));
		hboxBrowseButtons.setPadding(new Insets(40, 40, 40, 40));
		hboxBanner.setPadding(new Insets(40, 40, 40, 40));
		gp.add(tfUpload, 0, 3);
		gp.add(btnBrowse, 2, 3);
		bottomRow.getChildren().addAll(backBtn,btnContinue);
		bottomRow.setAlignment(Pos.BOTTOM_CENTER);
		bottomRow.setSpacing(350);
		hboxBrowseButtons.getChildren().addAll(gp);
		hboxBrowseButtons.setAlignment(Pos.BASELINE_CENTER);
		loadPane.add(hboxBanner, 0, 0);
		loadPane.add(hboxBrowseButtons, 0, 1);
		loadPane.add(bottomRow, 0, 2);
		gp.setVgap(15); // No Box needed. sets vertical gap for all items in pane
		return loadPane;
	}

	private Pane buildSessionPane() {
		
		GridPane newPane = new GridPane();
		newPane.setAlignment(Pos.BASELINE_CENTER);
		HBox hboxBanner = buildBanner();
		hboxBanner.setAlignment(Pos.BASELINE_CENTER);
		HBox hboxButtons1 = new HBox(10);
		HBox hboxButtons2 = new HBox(10);
		HBox hboxButtons3 = new HBox(10);
		GridPane gp1 = new GridPane();
		
		

		/**
		 * Initialize Buttons
		 */
		Button btnNewMember = new Button("New Member");
		btnNewMember.wrapTextProperty().set(true);//doesnt work for some reason.. 
		Button btnNewGroup = new Button("New Group");
		btnNewGroup.wrapTextProperty().set(true);
		Button btnNewPost = new Button("New Post");
		btnNewPost.wrapTextProperty().set(true);
		Button btnManMember = new Button("Manage Member");
		btnManMember.wrapTextProperty().set(true);
		Button btnInspect = new Button("Inspect");
		Button btnSave = new Button("Save");
		Button btnQuit = new Button("Quit");

		/**
		 * Handle Button Events
		 */
		btnNewMember.setOnAction(e -> {
			paneStack.push(buildSessionPane());
			primaryStage.setScene(makeScene(buildNewMemberPane()));
		});

		btnNewGroup.setOnAction(e -> {
			paneStack.push(buildSessionPane());
			primaryStage.setScene(makeScene(buildNewGroupPane()));
		});

		btnNewPost.setOnAction(e -> {
			
			if(sm.getGroups().size() == 0 || sm.getMembers().size() == 0) {
				if(sm.getGroups().size() == 0 && sm.getMembers().size() == 0) {
					Alert alert = new Alert(AlertType.INFORMATION, "There are no members or groups. Please add to both before making a post.");
					alert.showAndWait();
				}
				else if(sm.getMembers().size() == 0) {
					Alert alert = new Alert(AlertType.INFORMATION, "There are no members. Please add to members via new member button.");
					alert.showAndWait();
				}
				else {
					Alert alert = new Alert(AlertType.INFORMATION, "There are no groups. Please add to groups via new group button.");
					alert.showAndWait();
				}
			}
			else{
				paneStack.push(buildSessionPane());
				primaryStage.setScene(makeScene(buildNewPostPane()));
			}

		});

		btnManMember.setOnAction(e -> {
			if((sm.getMembers()).size() == 0) {
				Alert alert = new Alert(AlertType.INFORMATION, "You have no memebers to manage. Please add memebers via New Member Button.");
				alert.showAndWait();
			}
			else {
				paneStack.push(buildSessionPane());
				primaryStage.setScene(makeScene(buildManEmailPrompt()));
			}
		});

		btnInspect.setOnAction(e -> {
			if((sm.getMembers()).size() == 0 && sm.getGroups().size() == 0) {
				Alert alert = new Alert(AlertType.INFORMATION, "There is nothing to inspect.");
				alert.showAndWait();
			}
			else {
				paneStack.push(buildSessionPane());
				primaryStage.setScene(makeScene(buildInspectPane()));
			}
		});

		btnSave.setOnAction(e -> {
			paneStack.push(buildSessionPane());
			primaryStage.setScene(makeScene(buildSavePane()));
		});

	
		btnQuit.setOnAction(e -> {
			if (noClicked) {
				primaryStage.close();
			}
			final Stage dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(primaryStage);
			VBox dialogVbox = new VBox(20);
			Button btnYes = new Button("Yes");
			Button btnNo = new Button("No");
			btnYes.setOnAction(e1 -> {
				dialog.close();
				primaryStage.setScene(makeScene(buildSavePane()));
			});
			btnNo.setOnAction(e1 -> {
				dialog.close();
				primaryStage.close();
			});

			
			GridPane g = new GridPane();
			HBox h = new HBox();
			h.getChildren().addAll(new Text("Would you like to save?\n\n\n"));
			h.setAlignment(Pos.CENTER);//not sure why this text will not center >:[
			g.add(h, 0, 0);
			g.add(btnYes, 0, 1);
			g.add(new Label("\t"), 1, 1);
			g.add(btnNo, 2, 1);
			g.setAlignment(Pos.CENTER);
			dialogVbox.getChildren().addAll(g);
			Scene dialogScene = new Scene(dialogVbox, 500, 200);
			dialogScene.getStylesheets().add(getClass().getResource(CSSPATH).toExternalForm());
			dialog.setScene(dialogScene);
			dialog.show();
		});

		/**
		 * Assemble elements for final grid pane
		 */
		gp1.add(btnNewMember, 0, 0);
		gp1.add(new Label("\t\t\t"), 1, 0);
		gp1.add(btnNewGroup, 2, 0);
		gp1.add(new Label("\t\t\t"), 3, 0);
		gp1.add(btnNewPost, 4, 0);
		hboxButtons1.getChildren().addAll(gp1);
		GridPane gp2 = new GridPane();
		gp2.add(btnManMember, 0, 0);
		gp2.add(new Label("\t\t\t"), 1, 0);
		gp2.add(btnInspect, 2, 0);
		hboxButtons2.getChildren().addAll(gp2);
		GridPane gp3 = new GridPane();
		
		gp3.add(btnSave, 2, 0);
		gp3.add(new Label("\t\t\t"), 3, 0);
		gp3.add(btnQuit, 4, 0);
		hboxButtons3.getChildren().addAll(gp3);		
		hboxButtons1.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons2.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons3.setAlignment(Pos.BASELINE_CENTER);
		gp1.setPadding(new Insets(40, 40, 40, 40));
		gp2.setPadding(new Insets(40, 40, 40, 40));
		gp3.setPadding(new Insets(40, 40, 40, 40));
		newPane.add(hboxBanner, 0, 0);
		newPane.add(new Label("\n\n"), 0, 1);
		newPane.add(hboxButtons1, 0, 2);
		newPane.add(hboxButtons2, 0, 3);
		newPane.add(new Label("\n\n\n\n\n"), 0, 4);
		newPane.add(hboxButtons3, 0, 5);
		return newPane;
	}

	private Scene makeScene(Pane pane) {
		Scene s = new Scene(pane, WIDTH, HEIGHT);
		s.getStylesheets().add(getClass().getResource(CSSPATH).toExternalForm());
		return s;
	}

	private Pane buildSavePane() {
		GridPane savePane = new GridPane();
		HBox hboxBanner = buildBanner();
		HBox hboxSaveButtons = new HBox();
		HBox hboxSeparator = new HBox();
		hboxSeparator.getChildren().addAll(new VBox(), new VBox(), new VBox());
		Label txtMsg = new Label("Enter a name for your save file.\t");
		Label txtWarn = new Label();
		TextField tfPath = new TextField();
		tfPath.setEditable(true);
		Button backBtn = new Button("Back");
		Button btnSave = new Button("Save");
		
		boolean illigalFileName = (tfPath.getText().contains("%")) 
				|| (tfPath.getText().contains("\\"))
				|| (tfPath.getText().contains("|"))
				|| (tfPath.getText().contains("?")
				|| (tfPath.getText().contains("/") 
				|| (tfPath.getText().contains(":"))
				|| (tfPath.getText().contains("*")) 
				|| (tfPath.getText().contains("\""))
				|| (tfPath.getText().contains("<")) 
				|| tfPath.getText().contains(">")));

		btnSave.setOnAction(e -> {
			if (illigalFileName) {
				String msg = "Your filename contains illegal characers.";
				msg += "\nDo not use % \\ | ? / : * \" < >";
				txtWarn.setText(msg);
				tfPath.setText("");
			} else {
				File file = new File(tfPath.getText());
				Thread thread = new Thread() {
					public void run() {
						
						
						saved = PersistenceManager.save(file, sm);
					}

				};

				thread.start();
				savePane.getChildren().clear();
				Label saveMsg = new Label("Saving Session ");
				savePane.getChildren().add(saveMsg);
				int i = 0;
				while (thread.isAlive()) {
					if(i % 7 == 0)
						saveMsg.setText(saveMsg.getText() + " . ");
					i++;
				}

				if (saved) {
					Alert alert = new Alert(AlertType.INFORMATION, "\n\n\n\nSession Saved Successfully!");
					alert.showAndWait();
					if(!noClicked) {
						primaryStage.close();
					}
					
				} else {
					
					Alert alert = new Alert(AlertType.INFORMATION, "\n\n\n\nSession Saved Failed. See error log.");
					alert.showAndWait();
				}
			}
		});
		
		backBtn.setOnAction(b -> {
			//Returns last pane that was built
			primaryStage.setScene(makeScene(paneStack.pop()));
		});
		
		GridPane g1 = new GridPane();
		g1.add(txtMsg, 0, 0);
		g1.add(tfPath, 1, 0);
		g1.add(txtWarn, 0, 2);

		hboxSaveButtons.getChildren().addAll(g1);
		hboxSaveButtons.setAlignment(Pos.BASELINE_LEFT);
		HBox hboxBottomButtons = new HBox();
		hboxBottomButtons.getChildren().addAll(backBtn, btnSave);
		hboxBottomButtons.setSpacing(100);
		hboxBottomButtons.setAlignment(Pos.BASELINE_LEFT);
		savePane.add(hboxBanner, 0, 0);
		savePane.add(new Label("\n\n\n\n\n\n"), 0, 1);
		savePane.add(hboxSaveButtons, 0, 2);
		savePane.add(hboxSeparator, 0, 3);
		savePane.add(hboxBottomButtons, 0, 4);
		savePane.setAlignment(Pos.BASELINE_CENTER);
		savePane.setPadding(new Insets(40, 40, 40, 40));

		return savePane;
	}

	private Pane buildInspectPane() {
		GridPane inspectPane = new GridPane();
		inspectPane.setAlignment(Pos.BASELINE_CENTER);
		HBox hboxBanner = buildBanner();
		hboxBanner.setAlignment(Pos.BASELINE_CENTER);
		
		HBox hboxButtons1 = new HBox();
		hboxButtons1.setAlignment(Pos.BASELINE_CENTER);
		HBox hboxButtons2 = new HBox();
		hboxButtons2.setAlignment(Pos.BASELINE_CENTER);
		HBox hboxButtons3 = new HBox();
		hboxButtons3.setSpacing(300);
		HBox hboxButtons4 = new HBox(); 
		hboxButtons4.setAlignment(Pos.BASELINE_CENTER);
		HBox hboxButtons5 =  new HBox();
		
		Label title = new Label("Inspect");
		title.setId("titleHeader");
		GridPane g1 = new GridPane();
		g1.add(title, 2, 0);
		g1.setHgap(30);
		
		g1.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons1.getChildren().addAll(g1);
		
		/**
		 * Initialize Buttons
		 */
		Button btnMember = new Button("Member");
		Button btnGroup = new Button("Group");
		Button btnSite = new Button("Site");
		
		//list of buttons for members
		Button btnGetMemberActivityLevel = new Button("Get Activity Level");
		btnGetMemberActivityLevel.setVisible(false);
		btnGetMemberActivityLevel.setMinWidth(150);
		ComboBox<String> cbActGroup = new ComboBox<String>();
		cbActGroup.setPromptText("Group Titles");
		cbActGroup.setVisible(false);
		Button btnGetMemberQuestions = new Button("Get Questions");
		btnGetMemberQuestions.setMinWidth(150);
		btnGetMemberQuestions.setVisible(false);
		Button btnGetMemberAnswers = new Button("Get Answers");
		btnGetMemberAnswers.setMinWidth(150);
		btnGetMemberAnswers.setVisible(false);

		//list of buttons for group
		Button btnGetGroupQuestions = new Button("Get Questions");
		btnGetGroupQuestions.setMinWidth(200);
		btnGetGroupQuestions.setVisible(false);
		Button btnGetGroupAnswers = new Button("Get Answers");
		btnGetGroupAnswers.setMinWidth(200);
		btnGetGroupAnswers.setWrapText(true);
		btnGetGroupAnswers.setVisible(false);
		Button btnGetGroupActiveMembers = new Button("Get Active Members");
		btnGetGroupActiveMembers.setMinWidth(200);
		btnGetGroupActiveMembers.setVisible(false);
		TextField tfNum21 = new TextField();
		tfNum21.setPromptText("Number");
		tfNum21.setMinWidth(50);
		tfNum21.setVisible(false);
		
		TextField tfNum22 = new TextField();
		tfNum22.setPromptText("Number");
		tfNum22.setMinWidth(50);
		tfNum22.setVisible(false);
		
		TextField tfNum23 = new TextField();
		tfNum23.setPromptText("Number");
		tfNum23.setMinWidth(50);
		tfNum23.setVisible(false);

		//list of buttons for site
		Button btnGetSiteActiveMembers = new Button("Get Active Members");
		btnGetSiteActiveMembers.setMinWidth(175);
		btnGetSiteActiveMembers.setVisible(false);
		Button btnGetSiteActiveGroups = new Button("Get Active Groups");
		btnGetSiteActiveGroups.setMinWidth(175);
		btnGetSiteActiveGroups.setVisible(false);
		Button btnGetSitePopularGroups = new Button("Get Popular Groups");
		btnGetSitePopularGroups.setMinWidth(175);
		btnGetSitePopularGroups.setVisible(false);
		Button btnSearch = new Button("Search");
		btnSearch.setMinWidth(150);
		btnSearch.setVisible(false);
		
		TextField tfGetGroups = new TextField();
		tfGetGroups.setPromptText("Search Groups");
		tfGetGroups.setMinWidth(50);
		tfGetGroups.setVisible(false);
		TextField tfGetMembers = new TextField();
		tfGetMembers.setPromptText("Search Members");
		tfGetMembers.setMinWidth(50);
		tfGetMembers.setVisible(false);

		TextField tfNum31 = new TextField();
		tfNum31.setPromptText("Number");
		tfNum31.setMinWidth(50);
		tfNum31.setVisible(false);
		
		TextField tfNum32 = new TextField();
		tfNum32.setPromptText("Number");
		tfNum32.setMinWidth(50);
		tfNum32.setVisible(false);
		
		TextField tfNum33 = new TextField();
		tfNum33.setPromptText("Number");
		tfNum33.setMinWidth(50);
		tfNum33.setVisible(false);
		

		
		GridPane g3 = new GridPane();
		
		// Get Activity Level
		Label lblGetMemberActivityLevel = new Label("Get Activity Level");
		ComboBox<String> cbEmails = new ComboBox<String>();
		cbEmails.setPromptText("Members");
		g3.add(lblGetMemberActivityLevel,2,0);
		g3.add(cbEmails, 4, 0);
		g3.add(cbActGroup, 6, 0);
		lblGetMemberActivityLevel.setVisible(false);
		cbEmails.setVisible(false);
		cbActGroup.setVisible(false);
		
		Label lblMemberQuestions = new Label("Get Questions");
		ComboBox<String> cbEmails2 = new ComboBox<String>();
		cbEmails2.setPromptText("Members");
		g3.add(lblMemberQuestions,2,0);
		lblMemberQuestions.setVisible(false);
		g3.add(cbEmails2, 4, 0);
		cbEmails2.setVisible(false);
		
		
		Label lblMemberAnswers = new Label("Get Answers");
		ComboBox<String> cbEmails3 = new ComboBox<String>();
		cbEmails3.setPromptText("Members");
		g3.add(lblMemberAnswers,2,0);
		lblMemberAnswers.setVisible(false);
		g3.add(cbEmails3, 4, 0);
		cbEmails3.setVisible(false);
		
		
		Label lblGroupQuestions = new Label("Get Questions");
		ComboBox<String> cbGroups = new ComboBox<String>();
		cbGroups.setPromptText("Groups");
		g3.add(lblGroupQuestions,2,0);
		lblGroupQuestions.setVisible(false);
		g3.add(cbGroups, 4, 0);
		g3.add(tfNum21, 6, 0);
		cbGroups.setVisible(false);
		tfNum21.setVisible(false);

		
		Label lblGroupAnswers = new Label("Get Answers");
		ComboBox<String> cbGroups2 = new ComboBox<String>();
		cbGroups2.setPromptText("Groups");
		g3.add(lblGroupAnswers,2,0);
		lblGroupAnswers.setVisible(false);
		g3.add(cbGroups2, 4, 0);
		g3.add(tfNum22, 6, 0);
		cbGroups2.setVisible(false);
		tfNum22.setVisible(false);

		
		Label lblActiveMembers = new Label("Get Active Members");
		ComboBox<String> cbGroups3 = new ComboBox<String>();
		cbGroups3.setPromptText("Groups");
		g3.add(lblActiveMembers,2,0);
		lblActiveMembers.setVisible(false);
		g3.add(cbGroups3, 4, 0);
		g3.add(tfNum23, 6, 0);
		cbGroups3.setVisible(false);
		tfNum23.setVisible(false);
		
		Label lblGetActiveMembers = new Label("Get Active Members");
		g3.add(lblGetActiveMembers,4,0);
		Button btnload = new Button("Load");
		btnload.setVisible(false);
		g3.add(btnload,8,0);
		lblGetActiveMembers.setVisible(false);
		g3.add(tfNum33, 6, 0);
		tfNum33.setVisible(false);
		
		Label lblGetActiveGroups = new Label("Get Active Groups");
		g3.add(lblGetActiveGroups,4,0);
		Button btnload2 = new Button("Load");
		btnload2.setVisible(false);
		g3.add(btnload2,8,0);
		lblGetActiveGroups.setVisible(false);
		g3.add(tfNum32, 6, 0);
		tfNum32.setVisible(false);
		
		Label lblGetPopularGroups = new Label("Get Popular Groups");
		Button btnload3 = new Button("Load");
		btnload3.setVisible(false);
		g3.add(btnload3,8,0);
		g3.add(lblGetPopularGroups,4,0);
		lblGetPopularGroups.setVisible(false);
		g3.add(tfNum31, 6, 0);
		tfNum31.setVisible(false);
		
		Button btnloadSearch1 = new Button("Search");
		btnloadSearch1.setVisible(false);
		Button btnloadSearch2 = new Button("Search");
		btnloadSearch2.setMinWidth(100);
		btnloadSearch2.setVisible(false);
		g3.add(tfGetGroups, 2, 0);
		g3.add(tfGetMembers, 6, 0);
		g3.add(btnloadSearch1, 4, 0);
		g3.add(btnloadSearch2, 8, 0);
		tfGetGroups.setVisible(false);
		tfGetMembers.setVisible(false);
		
		/***
		 * Fill the Member email 
		 * dropdowns all at once
		 ***/
		for(Member m : sm.getMembers()) {
			cbEmails.getItems().add(m.getEmail());
			cbEmails2.getItems().add(m.getEmail());
			cbEmails3.getItems().add(m.getEmail());
		}
		
		/**
		 * Handle Button Events
		 */
		btnMember.setOnAction(e -> {


      btnGetMemberActivityLevel.setVisible(true);
			btnGetMemberQuestions.setVisible(true);
			btnGetMemberAnswers.setVisible(true);
			
			lblGetMemberActivityLevel.setVisible(false);
			cbEmails.setVisible(false);
			cbActGroup.setVisible(false);
			
			lblMemberQuestions.setVisible(false);
			cbEmails2.setVisible(false);
			
			lblMemberAnswers.setVisible(false);
			cbEmails3.setVisible(false);
			
			btnGetGroupQuestions.setVisible(false);
			btnGetGroupAnswers.setVisible(false);
			btnGetGroupActiveMembers.setVisible(false);
			
			tfNum21.setVisible(false);
			tfNum22.setVisible(false);
			tfNum23.setVisible(false);
			
			lblGroupQuestions.setVisible(false);
			cbGroups.setVisible(false);
			lblGroupAnswers.setVisible(false);
			cbGroups2.setVisible(false);
			lblActiveMembers.setVisible(false);
			cbGroups3.setVisible(false);
			
			btnGetSiteActiveMembers.setVisible(false);
			btnGetSiteActiveGroups.setVisible(false);
			btnGetSitePopularGroups.setVisible(false);
			tfGetGroups.setVisible(false);
			tfGetMembers.setVisible(false);
			
			lblGetActiveMembers.setVisible(false);
			tfNum33.setVisible(false);
			lblGetActiveGroups.setVisible(false);
			tfNum32.setVisible(false);
			lblGetPopularGroups.setVisible(false);
			tfNum31.setVisible(false);
			btnSearch.setVisible(false);
			btnload.setVisible(false);
			btnload2.setVisible(false);
			btnload3.setVisible(false);
			
			btnloadSearch1.setVisible(false);
			btnloadSearch2.setVisible(false);
			
		});

		btnGroup.setOnAction(e -> {
			
			btnGetMemberActivityLevel.setVisible(false);
			cbActGroup.setVisible(false);
			btnGetMemberQuestions.setVisible(false);
			btnGetMemberAnswers.setVisible(false);
			
			lblGetMemberActivityLevel.setVisible(false);
			cbEmails.setVisible(false);
			cbActGroup.setVisible(false);
			
			lblMemberQuestions.setVisible(false);
			cbEmails2.setVisible(false);
			
			lblMemberAnswers.setVisible(false);
			cbEmails3.setVisible(false);
			
			btnGetGroupQuestions.setVisible(true);
			btnGetGroupAnswers.setVisible(true);
			btnGetGroupActiveMembers.setVisible(true);
			tfNum21.setVisible(false);
			tfNum22.setVisible(false);
			tfNum23.setVisible(false);
			
			
			lblGroupQuestions.setVisible(false);
			cbGroups.setVisible(false);
			lblGroupAnswers.setVisible(false);
			cbGroups2.setVisible(false);
			lblActiveMembers.setVisible(false);
			cbGroups3.setVisible(false);
			
			btnGetSiteActiveMembers.setVisible(false);
			btnGetSiteActiveGroups.setVisible(false);
			btnGetSitePopularGroups.setVisible(false);
			tfGetGroups.setVisible(false);
			tfGetMembers.setVisible(false);
			
			lblGetActiveMembers.setVisible(false);
			tfNum33.setVisible(false);
			lblGetActiveGroups.setVisible(false);
			tfNum32.setVisible(false);
			lblGetPopularGroups.setVisible(false);
			tfNum31.setVisible(false);
			btnSearch.setVisible(false);
			btnload.setVisible(false);
			btnload2.setVisible(false);
			btnload3.setVisible(false);
			
			btnloadSearch1.setVisible(false);
			btnloadSearch2.setVisible(false);
			
		});

		btnSite.setOnAction(e -> {
			
			btnGetMemberActivityLevel.setVisible(false);
			cbActGroup.setVisible(false);
			btnGetMemberQuestions.setVisible(false);
			btnGetMemberAnswers.setVisible(false);
			
			lblGetMemberActivityLevel.setVisible(false);
			cbEmails.setVisible(false);
			cbActGroup.setVisible(false);
			
			lblMemberQuestions.setVisible(false);
			cbEmails2.setVisible(false);
			
			lblMemberAnswers.setVisible(false);
			cbEmails3.setVisible(false);
			
			btnGetGroupQuestions.setVisible(false);
			btnGetGroupAnswers.setVisible(false);
			btnGetGroupActiveMembers.setVisible(false);
			
			lblGroupQuestions.setVisible(false);
			cbGroups.setVisible(false);
			lblGroupAnswers.setVisible(false);
			cbGroups2.setVisible(false);
			lblActiveMembers.setVisible(false);
			cbGroups3.setVisible(false);
			
			tfNum21.setVisible(false);
			tfNum22.setVisible(false);
			tfNum23.setVisible(false);
			
			btnGetSiteActiveMembers.setVisible(true);
			btnGetSiteActiveGroups.setVisible(true);
			btnGetSitePopularGroups.setVisible(true);
			tfGetGroups.setVisible(false);
			tfGetMembers.setVisible(false);
			btnSearch.setVisible(true);
			
			lblGetActiveMembers.setVisible(false);
			tfNum33.setVisible(false);
			lblGetActiveGroups.setVisible(false);
			tfNum32.setVisible(false);
			lblGetPopularGroups.setVisible(false);
			tfNum31.setVisible(false);
			btnload.setVisible(false);
			btnload2.setVisible(false);
			btnload3.setVisible(false);
			
			btnloadSearch1.setVisible(false);
			btnloadSearch2.setVisible(false);
			
		});
		

		// member button options set on actions
		btnGetMemberActivityLevel.setOnAction(e ->{
			btnGetMemberActivityLevel.setVisible(false);
			btnGetMemberQuestions.setVisible(false);
			btnGetMemberAnswers.setVisible(false);
			lblGetMemberActivityLevel.setVisible(true);
			cbEmails.setVisible(true);
			cbActGroup.setVisible(true);
		});
		
		btnGetMemberQuestions.setOnAction(e ->{
			btnGetMemberActivityLevel.setVisible(false);
			btnGetMemberQuestions.setVisible(false);
			btnGetMemberAnswers.setVisible(false);
			lblMemberQuestions.setVisible(true);
			cbEmails2.setVisible(true);
			cbActGroup.setVisible(true);
		});
		
		btnGetMemberAnswers.setOnAction(e ->{
			btnGetMemberActivityLevel.setVisible(false);
			btnGetMemberQuestions.setVisible(false);
			btnGetMemberAnswers.setVisible(false);
			
			lblMemberAnswers.setVisible(true);
			cbEmails3.setVisible(true);
			cbActGroup.setVisible(true);
		});
		
		// group button options set on actions
		
		btnGetGroupQuestions.setOnAction(e ->{
			btnGetGroupQuestions.setVisible(false);
			btnGetGroupAnswers.setVisible(false);
			btnGetGroupActiveMembers.setVisible(false);
			
			lblGroupQuestions.setVisible(true);
			cbGroups.setVisible(true);
			tfNum21.setVisible(true);
			

		});
		btnGetGroupAnswers.setOnAction(e ->{
			btnGetGroupQuestions.setVisible(false);
			btnGetGroupAnswers.setVisible(false);
			btnGetGroupActiveMembers.setVisible(false);
			
			lblGroupAnswers.setVisible(true);
			cbGroups2.setVisible(true);
			tfNum22.setVisible(true);
		});
		btnGetGroupActiveMembers.setOnAction(e ->{
			btnGetGroupQuestions.setVisible(false);
			btnGetGroupAnswers.setVisible(false);
			btnGetGroupActiveMembers.setVisible(false);
			
			lblActiveMembers.setVisible(true);
			cbGroups3.setVisible(true);
			tfNum23.setVisible(true);
		});
		
		for(Group grp : sm.getGroups()){
			cbGroups.getItems().add(grp.getTitle());
			cbGroups2.getItems().add(grp.getTitle());
			cbGroups3.getItems().add(grp.getTitle());
		}
		cbGroups.setDisable(false);
		
		//site button options set on actions
		
		btnGetSiteActiveMembers.setOnAction(e ->{
			btnSearch.setVisible(false);
			btnGetSiteActiveMembers.setVisible(false);
			btnGetSiteActiveGroups.setVisible(false);
			btnGetSitePopularGroups.setVisible(false);
			tfGetGroups.setVisible(false);
			tfGetMembers.setVisible(false);
			lblGetActiveMembers.setVisible(true);
			tfNum33.setVisible(true);
			btnload.setVisible(true);
			
		});
		btnGetSiteActiveGroups.setOnAction(e ->{
			btnSearch.setVisible(false);
			btnGetSiteActiveMembers.setVisible(false);
			btnGetSiteActiveGroups.setVisible(false);
			btnGetSitePopularGroups.setVisible(false);
			tfGetGroups.setVisible(false);
			tfGetMembers.setVisible(false);
			lblGetActiveGroups.setVisible(true);
			tfNum32.setVisible(true);
			btnload2.setVisible(true);

		});
		btnGetSitePopularGroups.setOnAction(e ->{
			btnSearch.setVisible(false);
			btnGetSiteActiveMembers.setVisible(false);
			btnGetSiteActiveGroups.setVisible(false);
			btnGetSitePopularGroups.setVisible(false);
			tfGetGroups.setVisible(false);
			tfGetMembers.setVisible(false);
			lblGetPopularGroups.setVisible(true);
			tfNum31.setVisible(true);
			btnload3.setVisible(true);
		});
		
		
		btnSearch.setOnAction(e ->{
			btnSearch.setVisible(false);
			btnGetSiteActiveMembers.setVisible(false);
			btnGetSiteActiveGroups.setVisible(false);
			btnGetSitePopularGroups.setVisible(false);
			tfGetGroups.setVisible(true);
			tfGetMembers.setVisible(true);
			lblGetPopularGroups.setVisible(false);
			tfNum31.setVisible(false);
			btnload3.setVisible(false);
			
			btnloadSearch1.setVisible(true);
			btnloadSearch2.setVisible(true);
		});
		
		
		
		TextArea taInspectArea = new TextArea();
		// Get Member's activity level
		cbEmails.setOnAction(e ->{
			if(cbEmails.getValue() != null){
				ArrayList<String> titles = new ArrayList<>();
				cbActGroup.getItems().clear();
				for(Group grp : sm.getMember(cbEmails.getValue()).getGroups()){
					titles.add(grp.getTitle());
				}
				cbActGroup.getItems().addAll(titles);
				cbActGroup.setDisable(false);
			}
			else {
				cbActGroup.setDisable(true);
			}
			
			cbActGroup.setOnAction(ev -> {
				int num = sm.getMember(cbEmails.getValue()).getActivityLevel(sm.getGroup(cbActGroup.getValue()));
				taInspectArea.setText(sm.getMember(cbEmails.getValue()).getFirstName() + " " + sm.getMember(cbEmails.getValue()).getLastName() +
						" has an activity level of " + num + " in the group titled " + cbActGroup.getValue());
			});
		});
		// Get Member's question
		cbEmails2.setOnAction(e ->{
			
			// Fills group dropdown based off what member is selected
				ArrayList<String> titles = new ArrayList<>();
				
				cbActGroup.getItems().clear();
				
				for(Group grp : sm.getMember(cbEmails2.getValue()).getGroups()){
					titles.add(grp.getTitle());
				}
				cbActGroup.getItems().addAll(titles);
				cbActGroup.setDisable(false);
			
			cbActGroup.setOnAction(ev -> {
				String retval = "Questions Asked in " + cbActGroup.getValue() + " by " + 
						sm.getMember(cbEmails2.getValue()).getFirstName() + " " + 
						sm.getMember(cbEmails2.getValue()).getLastName() + ":\n";
				
				for(Question q : sm.getMember(cbEmails2.getValue()).getQuestions(sm.getGroup(cbActGroup.getValue()))){
					retval += q.toString() + "\n";
				}
				taInspectArea.setWrapText(true);
				taInspectArea.setText(retval);
			});
		});
		cbEmails3.setOnAction(e ->{
			ArrayList<String> titles = new ArrayList<>();
			
			cbActGroup.getItems().clear();
			
			for(Group grp : sm.getMember(cbEmails3.getValue()).getGroups()){
				titles.add(grp.getTitle());
			}
			cbActGroup.getItems().addAll(titles);
			cbActGroup.setDisable(false);
		
			cbActGroup.setOnAction(ev -> {
				String retval = "Answers Asked in " + cbActGroup.getValue() + " by " + 
						sm.getMember(cbEmails3.getValue()).getFirstName() + " " + 
						sm.getMember(cbEmails3.getValue()).getLastName() + ":\n";
				
				for(Answer a : sm.getMember(cbEmails3.getValue()).getAnswers(sm.getGroup(cbActGroup.getValue()))){
					retval += a.toString() + "\n";
				}
				taInspectArea.setWrapText(true);
				taInspectArea.setText(retval);
			});
		});
		
		cbGroups.setOnAction(e ->{
			String retval = "Questions Asked in " + cbGroups.getValue() + ":\n";
			
			
			if(tfNum21.getText().isEmpty()) {
				for(Question q : sm.getGroup(cbGroups.getValue()).getQuestions()){
					retval += q.toString();
				}
			}
			else {
				for(Question q : sm.getGroup(cbGroups.getValue()).getQuestions(Integer.parseInt(tfNum21.getText()))){
					retval += q.toString();
				}
			}
			
			taInspectArea.setWrapText(true);
			taInspectArea.setText(retval);
		});
		
		cbGroups2.setOnAction(e ->{
			String retval = "Answers Asked in " + cbGroups2.getValue() + ":\n";
			
			
			if(tfNum22.getText().isEmpty()) {
				for(Answer a : sm.getGroup(cbGroups2.getValue()).getAnswers()){
					retval += a.toString();
				}
			}
			else {
				for(Answer a : sm.getGroup(cbGroups2.getValue()).getAnswers(Integer.parseInt(tfNum22.getText()))){
					retval += a.toString();
				}
			}
			
			taInspectArea.setWrapText(true);
			taInspectArea.setText(retval);
		});
		
		cbGroups3.setOnAction(e ->{
			String retval = "Most Active Members in " + cbGroups3.getValue() + ":\n";
			
			
			if(tfNum23.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION, "Enter a number in the Number Field!!");
				alert.showAndWait();
			}
			else {
				int i = 1;
				for(Member m : sm.getGroup(cbGroups3.getValue()).getActiveMembers(Integer.parseInt(tfNum23.getText()))){
					retval += i +". " + m.getFirstName() + " " + m.getLastName() + "\n";
					i++;
				}
				taInspectArea.setWrapText(true);
				taInspectArea.setText(retval);
			}
		});
		
		
		btnload.setOnAction(e ->{
			try {
				String retval = "Most Active Members:\n";
				
				if(tfNum33.getText().trim().isEmpty()) {
					Alert alert = new Alert(AlertType.INFORMATION, "Enter a number in the load Field!!");
					alert.showAndWait();
				}
				else {
					int i = 1;
					
					/**
					 * not working due to newly found errors in site manager
					 */
					//List<Member> m1 = sm.getActiveMembers(Integer.parseInt(tfNum33.getText()));
					int funval = 0;
					List<Member> list = sm.getMembers();
					if(Integer.parseInt(tfNum33.getText()) < sm.getMembers().size()) {
						Collections.reverse(list);
					}
					List<Member> m1 = new ArrayList<>();
					for(Member m : list) {
						if(funval == Integer.parseInt(tfNum33.getText()))
							break;
						m1.add(m);
						funval++;
					}
					
					for(Member m : m1) {
						retval += i +". " + m.getFirstName() + " " + m.getLastName() + "\n";
						i++;
					}
					taInspectArea.setWrapText(true);
					taInspectArea.setText(retval);
				}
			} catch(Exception exception) {
				taInspectArea.setText("Active members are temporarily unavailable.");
			}
			
		});
		
		btnload2.setOnAction(e ->{
			String retval = "Most Active Groups:\n";
			
			
			if(tfNum32.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION, "Enter a number in the load Field!!");
				alert.showAndWait();
			}
			else {
				int i = 1;
				for(Group g : sm.getActiveGroups(Integer.parseInt(tfNum32.getText()))){
					if(i == Integer.parseInt(tfNum32.getText()) + 1) 
						break;
					retval += i +". " + g.getTitle() + "\n";
					i++;
				}
				taInspectArea.setWrapText(true);
				taInspectArea.setText(retval);
			}
		});
		
		btnload3.setOnAction(e ->{
			String retval = "Most Popular Groups:\n";
			
			
			if(tfNum31.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION, "Enter a number in the load Field!!");
				alert.showAndWait();
			}
			else {
				int i = 1;
				for(Group g : sm.getPopularGroups(Integer.parseInt(tfNum31.getText()))){
					if(i-1 == Integer.parseInt(tfNum31.getText()))
						break;
					retval += i +". " + g.getTitle() + "\n";
					i++;
				}
				taInspectArea.setWrapText(true);
				taInspectArea.setText(retval);
			}
		});
		
		btnloadSearch1.setOnAction(e ->{
			String retval;
			if(tfGetGroups.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION, "Enter text to find a group!!");
				alert.showAndWait();
			}
			else {
				if(sm.getGroups(tfGetGroups.getText()).size() == 0) {
					taInspectArea.clear();
					retval = "No results were found based on the text entered.\nPlease Try Again";
				}
				else {
					retval = "Here's what we found based on your search text:\n"; 
					for(Group g : sm.getGroups(tfGetGroups.getText())){
						retval += g.toString() + "\n";
					}
				}
				taInspectArea.setWrapText(true);
				taInspectArea.setText(retval);
			}
		});
		
		btnloadSearch2.setOnAction(e ->{
			String retval;
			if(tfGetMembers.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION, "Enter text to find a member!!");
				alert.showAndWait();
			}
			else {
				if(sm.getMembers(tfGetMembers.getText()).size() == 0) {
					taInspectArea.clear();
					retval = "No results were found based on the text entered.\nPlease Try Again";
				}
				else {
					retval = "Here's what we found based on your search text:\n"; 
					for(Member m : sm.getMembers(tfGetMembers.getText())){
						retval += m.toString() + "\n";
					}
				}
				taInspectArea.setWrapText(true);
				taInspectArea.setText(retval);
			}
		});
		

		
		


		/**
		 * Add Buttons to hboxButtons2's grid pane
		 */
		//for button options
		GridPane g2 = new GridPane();
		g2.add(btnMember, 0, 0);
		g2.add(new Label("\t\t"), 1, 0);
		g2.add(btnGroup, 2, 0);
		g2.add(new Label("\t\t"), 3, 0);
		g2.add(btnSite, 4, 0);
		
		g2.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons2.getChildren().addAll(g2);
		
		//for buttons in third row after selection of g2 options
		

		taInspectArea.setEditable(false);
		
		//place member buttons in g3
		Label reuse = new Label("\t\t");
		Label reuse1 = new Label("\t\t");
		Label reuse2 = new Label("\t\t");
		Label reuse3 = new Label("\t\t");

		reuse.setVisible(false);
		reuse1.setVisible(false);
		reuse2.setVisible(false);
		reuse3.setVisible(false);
		
		g3.add(btnGetMemberActivityLevel, 0, 0);
		g3.add(reuse, 1, 0);
		g3.add(btnGetMemberQuestions, 2, 0);
		g3.add(reuse2, 3, 0);
		g3.add(btnGetMemberAnswers, 4, 0);
		
		//place group buttons in g3
		g3.add(btnGetGroupQuestions, 0, 0);
		g3.add(btnGetGroupAnswers, 2, 0);
		g3.add(btnGetGroupActiveMembers, 4, 0);
		
		//place site buttons in g3
		g3.add(btnGetSiteActiveMembers, 0, 0);
		g3.add(btnGetSiteActiveGroups, 2, 0);
		g3.add(btnGetSitePopularGroups, 4, 0);
		g3.add(reuse3, 5, 0);
		g3.add(btnSearch, 6, 0);

		
		g3.add(new Label("\n"), 0, 1);
		g3.setAlignment(Pos.CENTER);
		g3.setHgap(5);
		hboxButtons3.setAlignment(Pos.CENTER);
		hboxButtons3.getChildren().addAll(g3);
		hboxButtons3.setSpacing(5);
		
		
		//for text area
		GridPane g4 = new GridPane();
		g4.add(taInspectArea, 0, 0);		
		hboxButtons4.getChildren().addAll(g4);

		//bottom row and items
		Button backBtn = new Button("Back");
		backBtn.setOnAction(b -> {
			//Returns last pane that was built
			primaryStage.setScene(makeScene(paneStack.pop()));
		});
		
		GridPane g5 = new GridPane();
		g5.add(backBtn, 0, 0);
		
		g5.setAlignment(Pos.BASELINE_LEFT);
		hboxButtons5.getChildren().addAll(g5);
		
		inspectPane.add(hboxBanner, 0, 0);
		inspectPane.add(new Label("\n\n\n"), 0, 1);
		inspectPane.add(hboxButtons1, 0, 2);
		inspectPane.add(new Label("\n\n"), 0, 3);
		inspectPane.add(hboxButtons2, 0, 4);
		inspectPane.add(new Label("\n\n"), 0, 5);
		inspectPane.add(hboxButtons3, 0, 6);
		inspectPane.add(new Label("\n\n"), 0, 7);
		inspectPane.add(hboxButtons4, 0, 8);
		inspectPane.add(new Label("\n\n"), 0, 9);
		inspectPane.add(hboxButtons5, 0, 10);
		
		inspectPane.setAlignment(Pos.BASELINE_CENTER);

		return inspectPane;
	}

	private Pane buildJoinGroupPane(String memberEmail) {
		ArrayList<String> groupTitles = new ArrayList<>();
		for(Group g : sm.getGroups()) {
			groupTitles.add(g.getTitle());
		}
		
		GridPane g = new GridPane();
		
		HBox hboxButtons1 = new HBox();
		HBox hboxButtons2 = new HBox();
		HBox hboxButtons3 = new HBox();
		
		VBox vboxButtons1 = new VBox();
		VBox vboxButtons2 = new VBox();
		
		ComboBox<String> cbGroups = new ComboBox<String>(FXCollections.observableArrayList(groupTitles));
		cbGroups.setPromptText("Existing Groups");
		cbGroups.setMinWidth(175);
		
		
		Button btnCreJoin = new Button("Create and Join");
		Button btnJoin = new Button("Join");
		Button backBtn = new Button("Back");
		
		TextField tfNewTit = new TextField();
		tfNewTit.setPromptText("Group Title");
		
		TextField tfNewDes = new TextField();
		tfNewDes.setPromptText("Group Description");
		
		
		
		
		
		
		g.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons1.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons2.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons3.setAlignment(Pos.BASELINE_CENTER);
		vboxButtons1.setAlignment(Pos.BASELINE_CENTER);
		vboxButtons2.setAlignment(Pos.BASELINE_CENTER);
		
		
		Label title = new Label(String.format("Managing %s", memberEmail));
		title.setId("titleHeader");
		
		hboxButtons1.getChildren().addAll(title, new Label("\n\n\n"));
		
		
		
		Label l1 = new Label("Join Existing Group\n");
		l1.setStyle("-fx-font-weight: bold");
		
		Label l2 = new Label("Create new Group and Join\n");
		l2.setStyle("-fx-font-weight: bold");
		
		vboxButtons1.getChildren().addAll(l1 , cbGroups , new Label("\n\n\n\n\n\n\n"), btnJoin,  new Label("\n\n") );
		vboxButtons2.getChildren().addAll(l2 , tfNewTit, 
				new Label("\n\n") , tfNewDes , new Label("\n\n\n") , btnCreJoin);
		
		hboxButtons2.getChildren().addAll(vboxButtons1 , new Label("\t\t\t\t\t") , vboxButtons2);
		
		hboxButtons3.getChildren().add(backBtn);
		
		g.add(buildBanner(), 0, 0);
		g.add(new Label("\n\n"), 0, 1);
		
		g.add(hboxButtons1, 0, 2);
		g.add(new Label("\n"), 0, 3);
		
		g.add(hboxButtons2, 0, 4);
		g.add(new Label("\n\n"), 0, 5);
		
		g.add(hboxButtons3, 0, 6);
		
		
	
		
		
		/**
		 * Set Button Actions
		 */
		btnCreJoin.setOnAction(e -> {
			sm.addGroup(tfNewTit.getText(), tfNewDes.getText(), LocalDateTime.now());
			sm.getMember(memberEmail).joinGroup(sm.getGroup(tfNewTit.getText()), LocalDateTime.now());
			primaryStage.setScene(makeScene(buildSessionPane()));
		});
		
		btnJoin.setOnAction(e -> {
			sm.getMember(memberEmail).joinGroup(sm.getGroup(cbGroups.getValue()), LocalDateTime.now());
			primaryStage.setScene(makeScene(buildSessionPane()));
		});
		
		backBtn.setOnAction(b -> {
			//Returns last pane that was built
			primaryStage.setScene(makeScene(paneStack.pop()));
		});
		
		
		g.setAlignment(Pos.BASELINE_CENTER);
		
		return g;
	}

	private Pane buildLikePostPane(String memberEmail) {
		GridPane g = new GridPane();
		
		Button backBtn = new Button("Back");
		
		HBox hboxButtons1 = new HBox();
		HBox hboxButtons2 = new HBox();
		HBox hboxButtons3 = new HBox();
		hboxButtons1.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons2.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons3.setAlignment(Pos.BASELINE_CENTER);
		
		g.setAlignment(Pos.BASELINE_CENTER);
		
		g.add(buildBanner(), 0, 0);
		g.add(new Label("\n\n"), 0, 1);
		
		
		
		Label title = new Label(String.format("Managing  %s > Like Post", memberEmail));
		title.setId("titleHeader");
		
		hboxButtons1.getChildren().add(title);
		g.add(hboxButtons1, 0, 2);
		g.add(new Label("\n"), 0, 3);
		
		
		
		
		
		
		//Group Drop down box
		ComboBox<String> cbTitles = new ComboBox<String>();
		ArrayList<String> titles = new ArrayList<>();
		
		for(Group grp : sm.getMember(memberEmail).getGroups()){
			titles.add(grp.getTitle());
		}
		
		cbTitles.getItems().addAll(titles);
		cbTitles.setPromptText("Group titles");
		cbTitles.minWidth(200);		
		
		// Question drop down box
		ComboBox<String> cbQuestions = new ComboBox<String>();
		cbQuestions.setPromptText("Question titles");
		cbQuestions.minWidth(200);
		cbQuestions.setDisable(true);
		
		//Answer drop down box
		ComboBox<String> cbAnswers = new ComboBox<String>();
		cbAnswers.setPromptText("Answers");
		cbAnswers.minWidth(200);
		cbAnswers.setDisable(true);
		
		cbTitles.setOnAction(e -> {
			if(cbTitles.getValue() != null){
				ArrayList<String> questions = new ArrayList<>();
				cbQuestions.getItems().clear();
				for(Question qst : sm.getGroup(cbTitles.getValue()).getQuestions()){
					questions.add(qst.getTitle());
				}
				cbQuestions.getItems().addAll(questions);
				cbQuestions.setDisable(false);
			}
			else {
				cbQuestions.setDisable(true);
			}
		});
		
		cbQuestions.setOnAction(e -> {
			if(cbQuestions.getValue() != null){
				ArrayList<String> answers = new ArrayList<>();
				cbAnswers.getItems().clear();
				for(Answer ans : ((sm.getGroup(cbTitles.getValue())).getQuestion(cbQuestions.getValue())).getAnswers()){
					answers.add(ans.getText());
				}
				cbAnswers.getItems().addAll(answers);
				cbAnswers.setDisable(false);
			}
			else {
				cbAnswers.setDisable(true);
			}
		});
		
		backBtn.setOnAction(b -> {
			//Returns last pane that was built
			primaryStage.setScene(makeScene(paneStack.pop()));
		});
		
		
		Button btnLike = new Button("Add Like");
		Label txtMsg = new Label();
		txtMsg.setId("message");
		btnLike.setOnAction(e -> {
			boolean likeAdded = true;
			boolean alreadyLiked = false;

			if(cbAnswers.getValue() != null) {
				for(Answer ans : sm.getGroup(cbTitles.getValue()).getQuestion(cbQuestions.getValue()).getAnswers()) {
					if(ans.getText() == cbAnswers.getValue()) {
						Set<String> whoLiked = ans.getWhoLiked();
						if(whoLiked.contains(memberEmail) == false) {
							sm.getMember(memberEmail).like(ans);
						}
						else {
							likeAdded = false;
							alreadyLiked = true;
						}
					}
				}
			}

			if(alreadyLiked) {
				Alert alert = new Alert(AlertType.INFORMATION, "You've already liked this post!!");
				alert.showAndWait();
			} 
			else if(likeAdded) {
				Alert alert = new Alert(AlertType.INFORMATION, "You've liked this post!!");
				alert.show();
				try {
					Thread.currentThread();
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				alert.close();
				primaryStage.setScene(makeScene(buildSessionPane()));
			} else {
				Alert alert = new Alert(AlertType.INFORMATION, "You can't like this post.");
				alert.showAndWait();
			}

		});
		
		
		hboxButtons3.getChildren().addAll(backBtn, new Label("\t\t\t"), btnLike);
		
		VBox v = new VBox();
		v.setAlignment(Pos.BASELINE_CENTER);
		v.getChildren().addAll(txtMsg, new Label("Groups Titles"), cbTitles, 
				new Label("\n\n"), new Label("Questions Titles"), cbQuestions, new Label("\n\n"), 
				new Label("Answers"), cbAnswers, new Label("\n\n"), hboxButtons3);
		hboxButtons2.getChildren().add(v);
		g.add(hboxButtons2, 0, 4);
		
		
		
		return g;
		
	}

	private Pane buildManagerPane(String memberEmail) {
		GridPane managerPane = new GridPane();
		Button btnJoinGroup = new Button("Join Group");
		Button btnLikePost = new Button("Like Post");
		Button backBtn = new Button("Back");
		Label title = new Label(String.format("Managing %s", memberEmail));
		title.setId("titleHeader");
		
		HBox hboxButtonChoices = new HBox();
		hboxButtonChoices.setAlignment(Pos.BASELINE_CENTER);
		
		GridPane g1 = new GridPane();
		g1.setAlignment(Pos.BASELINE_CENTER);
		
		
		btnJoinGroup.setOnAction(e -> {
			paneStack.push(buildManagerPane(memberEmail));
			primaryStage.setScene(makeScene(buildJoinGroupPane(memberEmail)));
		});
		
		btnLikePost.setOnAction(e -> {
			paneStack.push(buildManagerPane(memberEmail));
			primaryStage.setScene(makeScene(buildLikePostPane(memberEmail)));
		});
		
		backBtn.setOnAction(b -> {
			//Returns last pane that was built
			primaryStage.setScene(makeScene(paneStack.pop()));
		});
		
		
		HBox hboxButtons1 = new HBox();
		HBox hboxButtons2 = new HBox();
		HBox hboxButtons3 = new HBox();
		HBox hboxButtons4 = new HBox();
		
		hboxButtons1.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons2.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons3.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons4.setAlignment(Pos.BASELINE_CENTER);
		
		
		hboxButtons1.getChildren().add(title);
		hboxButtons2.getChildren().add(btnJoinGroup);
		hboxButtons3.getChildren().add(btnLikePost);
		hboxButtons4.getChildren().add(backBtn);

		g1.setAlignment(Pos.BASELINE_CENTER);
		
		
		g1.add(new Label("\n\n\n\n"), 0, 0);
		g1.add(hboxButtons1, 0, 1);
		g1.add(new Label("\n\n\n\n"), 0, 2);
		g1.add(hboxButtons2, 0, 3);
		g1.add(new Label("\t\t\t\t"), 1, 3);
		g1.add(hboxButtons3, 2, 3);
		g1.add(new Label("\n\n\n\n"), 0, 4);
		g1.add(hboxButtons4, 0, 5);
		g1.setHgap(20);
		hboxButtonChoices.getChildren().addAll(g1);
		
		GridPane gp = new GridPane();
		gp.add(buildBanner(), 0, 0);
		hboxButtonChoices.setAlignment(Pos.BASELINE_CENTER);
		gp.add(hboxButtonChoices, 0, 1);
		gp.setAlignment(Pos.BASELINE_CENTER);
		managerPane.setAlignment(Pos.BASELINE_CENTER);
		managerPane.getChildren().addAll(gp);
		
		
		return managerPane;
	}

	private Pane buildNewPostPane() {
		return buildQuestAnsPrompt();
	}

	private Pane buildQuestAnsPrompt() {
		GridPane g = new GridPane();
		g.setAlignment(Pos.BASELINE_CENTER);
		HBox hboxButtons1 = new HBox();
		HBox hboxButtons2 = new HBox();
		HBox hboxButtons3 = new HBox();
		hboxButtons1.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons2.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons3.setAlignment(Pos.BASELINE_CENTER);
		g.add(buildBanner(), 0, 0);
		g.add(new Label("\n\n"), 0, 1);
		Button backBtn = new Button("Back");
		Label title = new Label("Create New Post");
		title.setId("titleHeader");
		hboxButtons1.getChildren().add(title);
		hboxButtons1.setSpacing(125);
		
		hboxButtons3.getChildren().addAll(backBtn);
		hboxButtons3.setSpacing(125);
		
		
		g.add(hboxButtons1, 0, 2);
		g.add(new Label("\n\n\n\n"), 0, 3);
		Button btnQuestion = new Button("Question");
		Button btnAnswer = new Button("Answer");
		
		
		btnQuestion.setOnAction(e -> {
			paneStack.push(buildQuestAnsPrompt());
			primaryStage.setScene(makeScene(buildNewQuestionPane()));
		});
		
		btnAnswer.setOnAction(e -> {
			paneStack.push(buildQuestAnsPrompt());
			primaryStage.setScene(makeScene(buildNewAnswerPane()));
		});
		
		backBtn.setOnAction(b -> {
			primaryStage.setScene(makeScene(paneStack.pop()));
		});
		
		
		hboxButtons2.getChildren().addAll(btnQuestion, new Label("\t\t\t\t"), btnAnswer);
		g.add(hboxButtons2, 0, 4);
		g.add(new Label("\n\n\n\n\n\n\n"), 0, 4);
		g.add(hboxButtons3, 0, 6);
		
		
		return g;
	}

	private Pane buildNewAnswerPane() {
		GridPane g = new GridPane();
		
		
		Button backBtn = new Button("Back");
		HBox hboxButtons1 = new HBox();
		HBox hboxButtons2 = new HBox();
		HBox hboxButtons3 = new HBox();
		HBox hboxButtons4 = new HBox();
		hboxButtons1.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons2.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons3.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons4.setAlignment(Pos.BASELINE_CENTER);

		
		g.add(buildBanner(), 0, 0);
		g.add(new Label("\n\n"), 0, 1);
		
		
		Label title = new Label("Creating New Answer");
		title.setId("titleHeader");
		hboxButtons1.getChildren().add(title);
		g.add(hboxButtons1, 0, 2);
		g.add(new Label("\n\n\n"), 0, 3);
		

		ArrayList<String> emails = new ArrayList<>();
		for(Member m : sm.getMembers()) {
			emails.add(m.getEmail());
		}
		ComboBox<String> cbEmail = new ComboBox<String>(FXCollections.observableList(emails));
		cbEmail.setPromptText("Member Emails");
		cbEmail.minWidth(200);
		
		
		ComboBox<String> cbGroup = new ComboBox<String>();
		cbGroup.setPromptText("Group Titles");
		cbGroup.setDisable(true);
		cbGroup.minWidth(200);
		
		ComboBox<String> cbQuests = new ComboBox<String>();
		cbQuests.setPromptText("Group Questions");
		cbQuests.minWidth(200);
		cbQuests.setDisable(true);
		
		cbGroup.setOnAction(a -> {
			if(cbGroup.getValue() != null){
				ArrayList<String> quest_titles = new ArrayList<>();
				cbQuests.getItems().clear();
				for(Question qst : sm.getMember(cbEmail.getValue()).getGroup(cbGroup.getValue()).getQuestions()){
					quest_titles.add(qst.getTitle());
				}
				cbQuests.getItems().addAll(quest_titles);
				cbQuests.setDisable(false);
			}
			else {
				cbQuests.setDisable(true);
			}
		});
		
		
		cbEmail.setOnAction(a -> {
			if(cbEmail.getValue() != null){
				ArrayList<String> titles = new ArrayList<>();
				cbGroup.getItems().clear();
				for(Group grp : sm.getMember(cbEmail.getValue()).getGroups()){
					titles.add(grp.getTitle());
				}
				cbGroup.getItems().addAll(titles);
				cbGroup.setDisable(false);
			}
			else {
				cbGroup.setDisable(true);
			}
			
		});
		
		
		TextField tfAnsTxt = new TextField();
		tfAnsTxt.setPromptText("Answer Text"); // Had to use this because the VBox offset with Group Question
		tfAnsTxt.setMinWidth(200);
			

		VBox vQTit = new VBox();
		VBox vATxt = new VBox();
		vQTit.setAlignment(Pos.BASELINE_CENTER);
		vATxt.setAlignment(Pos.BASELINE_CENTER);
		
		hboxButtons2.getChildren().addAll(cbEmail, new Label("\t\t\t"), cbGroup);
		g.add(hboxButtons2, 0, 4);
		g.add(new Label("\n\n\n"), 0, 5);
			

		hboxButtons3.getChildren().addAll(cbQuests, new Label("\t\t\t"), tfAnsTxt);
		g.add(hboxButtons3, 0, 6);
		g.add(new Label("\n\n\n"), 0, 7);
		Button btnCreate = new Button("Create");
			
		btnCreate.setOnAction(e -> {

			if(cbGroup.getValue() != null && tfAnsTxt.getText() != "" && tfAnsTxt.getText() != "") {
				Question q = sm.getMember(cbEmail.getValue()).getGroup(cbGroup.getValue()).getQuestion(cbQuests.getValue());
					
				Answer a = new Answer(q , tfAnsTxt.getText(), LocalDateTime.now());
					
				
				if(sm.getGroup(cbGroup.getValue()).getQuestion(cbQuests.getValue()) != null) {
					sm.getMember(cbEmail.getValue()).addAnswer(sm.getGroup(cbGroup.getValue()), sm.getGroup(cbGroup.getValue()).getQuestion(cbQuests.getValue()),a, a.date);
					primaryStage.setScene(makeScene(buildSessionPane()));
				}
				else {
					Alert alert = new Alert(AlertType.INFORMATION, "You must enter an answer.");
					alert.showAndWait();
				}
			}	
		
		
			paneStack.push(buildNewAnswerPane());
			primaryStage.setScene(makeScene(buildSessionPane()));
		});
		
		backBtn.setOnAction(b -> {
			//Returns last pane that was built
			primaryStage.setScene(makeScene(paneStack.pop()));
		});
		
		hboxButtons4.getChildren().addAll(backBtn, btnCreate);
		hboxButtons4.setSpacing(100);
		g.add(hboxButtons4, 0, 8);
		
		g.setAlignment(Pos.BASELINE_CENTER);
		
		return g;
	}

	private Pane buildNewQuestionPane() {
		GridPane g = new GridPane();
		g.setAlignment(Pos.BASELINE_CENTER);
		Button backBtn = new Button("Back");

		
		HBox hboxButtons1 = new HBox();
		HBox hboxButtons2 = new HBox();
		HBox hboxButtons3 = new HBox();
		HBox hboxButtons4 = new HBox();
		HBox hboxButtons5 = new HBox();
		hboxButtons1.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons2.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons3.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons4.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons5.setAlignment(Pos.BASELINE_CENTER);
		
		g.add(buildBanner(), 0, 0);
		g.add(new Label("\n\n"), 0, 1);
		
		
		Label title = new Label("Creating new Question");
		title.setId("titleHeader");
		hboxButtons1.getChildren().add(title);
		g.add(hboxButtons1, 0, 2);
		g.add(new Label("\n\n\n"), 0, 3);

		
		
		//Member Drop down box
		ComboBox<String> cbEmail = new ComboBox<String>(); // This is a dropdown
		ArrayList<String> emails = new ArrayList<>();
		for(Member m : sm.getMembers()) {
			emails.add(m.getEmail());
		}
		cbEmail.getItems().addAll(emails);
		cbEmail.setPromptText("Member emails");
		cbEmail.minWidth(300);
		
		ComboBox<String> cbGroups = new ComboBox<String>();
		cbGroups.setPromptText("Select Group");
		cbGroups.setMinWidth(150);
		cbGroups.setDisable(true);
		
		cbEmail.setOnAction(e -> {
			if(cbEmail.getValue() != null){
				ArrayList<String> titles = new ArrayList<>();
				cbGroups.getItems().clear();
				for(Group grp : sm.getMember(cbEmail.getValue()).getGroups()){
					titles.add(grp.getTitle());
				}
				cbGroups.getItems().addAll(titles);
				cbGroups.setDisable(false);
			}
			else {
				cbGroups.setDisable(true);
			}
		});
		
		TextField tfQueTit = new TextField();
		tfQueTit.setMinWidth(150);
		TextField tfQueTxt = new TextField();
		tfQueTxt.setMinWidth(200);
		
		VBox vEmail = new VBox();
		VBox vGTit = new VBox();
		VBox vQTit = new VBox();
		VBox vQTxt = new VBox();
		vEmail.setAlignment(Pos.BASELINE_CENTER);
		vGTit.setAlignment(Pos.BASELINE_CENTER);
		vQTit.setAlignment(Pos.BASELINE_CENTER);
		vQTxt.setAlignment(Pos.BASELINE_CENTER);
		
		vEmail.getChildren().add(cbEmail);
		vGTit.getChildren().add(cbEmail);
		hboxButtons2.getChildren().addAll(cbEmail, cbGroups);
		hboxButtons2.setSpacing(30);
		g.add(hboxButtons2, 0, 4);
		g.add(new Label("\n\n\n"), 0, 5);
		
		vQTit.getChildren().addAll(new Label("Question Title"), tfQueTit);
		vQTxt.getChildren().addAll(new Label("Question Text"), tfQueTxt);
		hboxButtons3.getChildren().addAll(vQTit, vQTxt);
		hboxButtons3.setSpacing(30);
		g.add(hboxButtons3, 0, 6);
		g.add(new Label("\n\n\n"), 0, 7);
		
		Button btnCreate = new Button("Create");
		
		
		
		btnCreate.setOnAction(e -> {

			if(cbGroups.getValue() != null && tfQueTit.getText() != "" && tfQueTxt.getText() != "") {
				
				if(sm.getGroup(cbGroups.getValue()).getQuestion(tfQueTit.getText()) == null) {

					Question q = new Question(tfQueTit.getText(), tfQueTxt.getText(), LocalDateTime.now());
					if(sm.getGroup(cbGroups.getValue()).getQuestion(tfQueTit.getText()) == null) {
						sm.getMember(cbEmail.getValue()).addQuestion(sm.getGroup(cbGroups.getValue()), q, q.date);
						primaryStage.setScene(makeScene(buildSessionPane()));
					}
					else {
						Alert alert = new Alert(AlertType.INFORMATION, "You are adding a question that already exists, change question title if you want to add another question.");
						alert.showAndWait();
					}
				}
			} else  {
				Alert alert = new Alert(AlertType.ERROR, "Please fill out the text fields.");
				alert.showAndWait();
			}
		});
		
		backBtn.setOnAction(b -> {
			//Returns last pane that was built
			primaryStage.setScene(makeScene(paneStack.pop()));
		});
		
		hboxButtons5.getChildren().addAll(backBtn, btnCreate);
		hboxButtons5.setSpacing(100);
		g.add(hboxButtons5, 0, 8);
		
		return g;
	}

	private Pane buildNewGroupPane() {
		GridPane g = new GridPane();
		g.add(buildBanner(), 0, 0);
		g.add(new Label("\n\n\n"), 0, 1);
		
		HBox hboxButtons1 = new HBox();
		HBox hboxButtons2 = new HBox();
		HBox hboxButtons3 = new HBox();
		hboxButtons1.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons2.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons3.setAlignment(Pos.BASELINE_CENTER);
		
		Button backBtn = new Button("Back");
		Label title = new Label("Creating New Group");
		title.setId("titleHeader");
		hboxButtons1.getChildren().add(title);
		g.add(hboxButtons1, 0, 2);
		g.add(new Label("\n\n\n\n"), 0, 3);
		
		TextField tfTit = new TextField();
		tfTit.setMinWidth(150);
		TextField tfDesc = new TextField();
		tfDesc.setMinWidth(250);
		VBox vTit = new VBox();
		VBox vDes = new VBox();
		vTit.setAlignment(Pos.BASELINE_CENTER);
		vDes.setAlignment(Pos.BASELINE_CENTER);
		vTit.getChildren().addAll(new Label("Title"), tfTit);
		vDes.getChildren().addAll(new Label("Description"), tfDesc);
		hboxButtons2.getChildren().addAll(vTit, new Label("\t\t\t"), vDes);
		g.add(hboxButtons2, 0, 4);
		g.add(new Label("\n\n\n\n"), 0, 5);
		Button btnCreate = new Button("Create");
		
		btnCreate.setOnAction(e -> {
			
			boolean areNull = (tfTit.getText().trim().isEmpty()) 
				       || (tfDesc.getText().trim().isEmpty());
			if(!areNull) {
				paneStack.push(buildNewGroupPane());
				sm.addGroup(tfTit.getText(), tfDesc.getText(), LocalDateTime.now());
				primaryStage.setScene(makeScene(buildSessionPane()));
			} else  {
				Alert alert = new Alert(AlertType.ERROR, "Please fill out the text fields.");
				alert.showAndWait();
			}
		});
		
		backBtn.setOnAction(b -> {
			//Returns last pane that was built
			primaryStage.setScene(makeScene(paneStack.pop()));
		});
		
		hboxButtons3.getChildren().addAll(backBtn, btnCreate);
		hboxButtons3.setSpacing(125);
		g.add(hboxButtons3, 0, 6);
		g.setAlignment(Pos.BASELINE_CENTER);
		
		
		
		return g;
	}

	private Pane buildNewMemberPane() {
		GridPane g = new GridPane();
		g.add(buildBanner(), 0, 0);
		g.add(new Label("\n\n"), 0, 1);
		
		HBox hboxButtons1 = new HBox();
		HBox hboxButtons2 = new HBox();
		HBox hboxButtons3 = new HBox();
		HBox hboxButtons4 = new HBox();
		
		
		hboxButtons1.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons2.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons3.setAlignment(Pos.BASELINE_CENTER);
		hboxButtons4.setAlignment(Pos.BASELINE_CENTER);
		
		

		
		
		Label txtTitle = new Label("Creating New Member");
		txtTitle.setId("titleHeader");
		
		
		
		hboxButtons1.getChildren().add(txtTitle);
		g.add(hboxButtons1, 0, 2);
		g.add(new Label("\n\n"), 0, 3);
		
		TextField tfFirstName = new TextField();
		tfFirstName.setMinWidth(150);
		
		TextField tfLastName = new TextField();
		tfLastName.setMinWidth(150);
		
		TextField tfScreenName = new TextField();
		tfScreenName.setMinWidth(150);
		
		TextField tfEmail = new TextField();
		tfEmail.setMinWidth(150);
		
		Button btnCreate = new Button("Create");
		Button backBtn = new Button("Back");
		
		
		VBox vFirstName = new VBox();
		vFirstName.getChildren().addAll(new Label("First Name"), tfFirstName);
		vFirstName.setAlignment(Pos.BASELINE_CENTER);
		
		VBox vLastName = new VBox();
		vLastName.getChildren().addAll(new Label("Last Name"), tfLastName);
		vLastName.setAlignment(Pos.BASELINE_CENTER);
		
		hboxButtons2.getChildren().addAll(vFirstName, new Label("\t\t\t"), vLastName);
		g.add(hboxButtons2, 0, 4);
		g.add(new Label("\n\n"), 0, 5);
		
		
		VBox vScreenName = new VBox();
		vScreenName.getChildren().addAll(new Label("Screen Name"), tfScreenName);
		vScreenName.setAlignment(Pos.BASELINE_CENTER);
		
		VBox vEmail = new VBox();
		vEmail.getChildren().addAll(new Label("Email Address"), tfEmail);
		vEmail.setAlignment(Pos.BASELINE_CENTER);
		
		hboxButtons3.getChildren().addAll(vScreenName, new Label("\t\t\t"), vEmail);
		g.add(hboxButtons3, 0, 6);
		g.add(new Label("\n\n\n\n\n"), 0, 7);
		
		hboxButtons4.getChildren().addAll(backBtn, btnCreate);
		hboxButtons4.setSpacing(45);
		g.add(hboxButtons4, 0, 8);
		
		btnCreate.setOnAction(e -> {
			/**
			 * sm shtuff here.. :}
			 */
			boolean areNull = (tfFirstName.getText().trim().isEmpty()) 
					       || (tfLastName.getText().trim().isEmpty()) 
					       || (tfScreenName.getText().trim().isEmpty())
					       || (tfScreenName.getText().trim().isEmpty());
			if(!areNull) {
				paneStack.push(buildNewMemberPane());
				sm.addMember(tfFirstName.getText(),tfLastName.getText(),
						tfScreenName.getText(), tfEmail.getText(), LocalDateTime.now());
				
				primaryStage.setScene(makeScene(buildSessionPane()));
			} else  {
				Alert alert = new Alert(AlertType.ERROR, "Please fill out the text fields.");
				alert.showAndWait();
			}
		});
		
		backBtn.setOnAction(b -> {
			//Returns last pane that was built
			primaryStage.setScene(makeScene(paneStack.pop()));
		});
		
		g.setAlignment(Pos.BASELINE_CENTER);
		
		return g;
	}

	private Pane buildManEmailPrompt() {
		GridPane g = new GridPane();
		GridPane g1 = new GridPane();
		HBox hboxBanner = buildBanner();
		HBox bottomRowBtns = new HBox();
		
		g.setAlignment(Pos.BASELINE_CENTER);
		g1.setAlignment(Pos.BASELINE_CENTER);
		hboxBanner.setAlignment(Pos.BASELINE_CENTER);
		bottomRowBtns.setAlignment(Pos.BASELINE_CENTER);
		
		g.add(hboxBanner, 0, 0);
		g1.add(new Label("\n\n\n\n"), 0, 0);
		Label title = new Label("Manage Member");
		title.setId("titleHeader");
		
		
			
		ComboBox<String> cbEmail = new ComboBox<String>(); // This is a dropdown
		ArrayList<String> emails = new ArrayList<>();
		for(Member m : sm.getMembers()) {
			emails.add(m.getEmail());
		}
		
		cbEmail.getItems().addAll(emails);
		cbEmail.setPromptText("Member emails");
		
		VBox memberInfo = new VBox();
		memberInfo.setAlignment(Pos.BASELINE_CENTER);
		memberInfo.setSpacing(40);
		
		memberInfo.getChildren().addAll(title, cbEmail);

		Button btnCont = new Button("Continue");
		Button backBtn = new Button("Back");
		
		/**
		 * Set Button Actions
		 */
		
		
		btnCont.setOnAction(e -> {
			/**
			 * take user to the next page
			 * 
			 */
			if( cbEmail.getValue() == null || cbEmail.getValue().trim().isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION, "Please select a member to proceed.");
				alert.showAndWait();
			}
			else {
				paneStack.push(buildManEmailPrompt());
				primaryStage.setScene(makeScene(buildManagerPane(cbEmail.getValue())));	//This needs to be changed to use the selected value from dropdown	
			}
		});
		
		backBtn.setOnAction(b -> {
			//Returns last pane that was built
			primaryStage.setScene(makeScene(paneStack.pop()));
		});
		
		/**
		 * Add Buttons to Pane
		 */
		g1.add(memberInfo, 0, 1);
		g1.add(new Label("\n\n"), 0, 2);
		bottomRowBtns.getChildren().addAll(backBtn, btnCont);
		bottomRowBtns.setSpacing(100);
		g1.add(bottomRowBtns, 0, 4);
		
		
		g.add(g1, 0, 1);
		/**
		 * Return Pane
		 */
		return g;
		
	}
	
	private HBox buildBanner() {
		Image imgLogo = new Image(LOGOPATH); // move the logo into this dir for now
		HBox hboxBanner = new HBox();
		VBox vboxLogo = new VBox();
		VBox vboxStats = new VBox();
		ImageView imageview = new ImageView();
		imageview.setImage(imgLogo);
		
		imageview.setOnMouseClicked((MouseEvent e) -> {
			primaryStage.setScene(makeScene(buildSessionPane()));
		});
		vboxLogo.getChildren().addAll(imageview);
		HBox hboxTime, hboxNumGroups, hboxNumPosts, hboxNumMems;
		hboxTime = new HBox();
		Label lblClock = initClock();
		updateClock(lblClock);
		hboxTime.getChildren().add(lblClock);
		hboxNumGroups = new HBox();
				
		int numGroups = 0;
		int numMembers = 0;
		int numPosts = 0;
		if(sm != null) {
			numGroups = sm.getGroups().size();
			numMembers = sm.getMembers().size();
			numPosts = getNumPosts();
		}
				
		hboxNumGroups.getChildren().add(new Label("\n\nGroups\t\t\t" + numGroups));
		hboxNumMems = new HBox();
		hboxNumMems.getChildren().add(new Label("Members\t\t\t" + numMembers));
		hboxNumPosts = new HBox();
		hboxNumPosts.getChildren().add(new Label("Posts\t\t\t" + numPosts));
		vboxStats.getChildren().addAll(hboxTime, hboxNumGroups, hboxNumMems, hboxNumPosts);
		GridPane g = new GridPane();
		g.add(vboxLogo, 0, 0);
		g.add(new Label("\t\t\t\t\t\t\t"), 1, 0);
		g.add(vboxStats, 2, 0);
		hboxBanner.getChildren().addAll(g);
		hboxBanner.setId("hBoxStyle");
		return hboxBanner;
	}

	private Label initClock() {
		Label lblClock = new Label();
		Calendar calendar = new GregorianCalendar();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		int second = calendar.get(Calendar.SECOND);
		int minute = calendar.get(Calendar.MINUTE);
		int hour = calendar.get(Calendar.HOUR);
		String time = String.format("%d:%2d:%d", hour, minute, second);
		String date = String.format("%d-%d-%d", month, day, year);
		lblClock.setText(String.format("Time%27s\nDate%25s", time, date ));
		return lblClock;
	}

	private void updateClock(Label lblClock) {
		Thread clock = new Thread() {
			public void run() {
				try {
					boolean flag = false;
					while (flag) {
						Calendar calendar = new GregorianCalendar();
						int day = calendar.get(Calendar.DAY_OF_MONTH);
						int month = calendar.get(Calendar.MONTH);
						int year = calendar.get(Calendar.YEAR);
						int second = calendar.get(Calendar.SECOND);
						int minute = calendar.get(Calendar.MINUTE);
						int hour = calendar.get(Calendar.HOUR);
						lblClock.setText(String.format("Time\t\t%d:%d:%d\nDate\t\t%d-%d-%d", hour, minute, second, year, month, day));
						sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		clock.start();
	}
	
	private int getNumPosts() {
		int count = 0; 
		for(Group g : sm.getGroups()) {
			count += g.getQuestions().size();
			count += g.getAnswers().size();
		}
		return count;	
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
