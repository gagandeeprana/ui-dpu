package com.dpu.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import com.dpu.client.PutAPIClient;
import com.dpu.constants.Iconstants;
import com.dpu.model.Category;
import com.dpu.model.Division;
import com.dpu.model.Failed;
import com.dpu.model.Status;
import com.dpu.model.Success;
import com.dpu.model.Terminal;
import com.dpu.model.Trailer;
import com.dpu.model.Type;
import com.dpu.util.Validate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TrailerEditController extends Application implements Initializable {

	@FXML
	Button btnUpdateTrailer;

	Long trailerId = 0l;

	@FXML
	TextField txtUnitNo, txtUsage, txtOwner, txtOoName, txtFinance;

	@FXML
	ComboBox<String> ddlStatus, ddlCategory, ddlDivision, ddlTerminal, ddlTrailerType;

	@FXML
	private void btnUpdateTrailerAction() {

		boolean result = validateAddDriverScreen();
		if (result) {
			editTrailer();
			closeEditTrailerScreen(btnUpdateTrailer);
		}
	}

	private void editTrailer() {

		Platform.runLater(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					ObjectMapper mapper = new ObjectMapper();
					Trailer trailer = setTrailerValue();
					String payload = mapper.writeValueAsString(trailer);

					String response = PutAPIClient.callPutAPI(
							Iconstants.URL_SERVER + Iconstants.URL_TRAILER_API + "/" + trailerId, null, payload);
					// MainScreen.trailerController.fillTrailer(response);
					try {
						Success success = mapper.readValue(response, Success.class);
						List<Trailer> trailerList = (List<Trailer>) success.getResultList();
						String res = mapper.writeValueAsString(trailerList);
						JOptionPane.showMessageDialog(null, success.getMessage());
						MainScreen.trailerController.fillTrailer(res);
					} catch (Exception e) {
						Failed failed = mapper.readValue(response, Failed.class);
						JOptionPane.showMessageDialog(null, failed.getMessage());
					}
					/*
					 * if(response != null && response.contains("message")) {
					 * Success success = mapper.readValue(response,
					 * Success.class); JOptionPane.showMessageDialog(null,
					 * success.getMessage() , "Info", 1); } else { Failed failed
					 * = mapper.readValue(response, Failed.class);
					 * JOptionPane.showMessageDialog(null, failed.getMessage(),
					 * "Info", 1); }
					 */
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Try Again..", "Info", 1);
				}
			}
		});

	}

	private Trailer setTrailerValue() {
		Trailer trailer = new Trailer();
		trailer.setUnitNo(Long.parseLong(txtUnitNo.getText()));
		trailer.setOwner(txtOwner.getText());
		trailer.setoOName(txtOoName.getText());
		trailer.setCategoryId(categoryList.get(ddlCategory.getSelectionModel().getSelectedIndex()).getCategoryId());
		trailer.setStatusId(statusList.get(ddlStatus.getSelectionModel().getSelectedIndex()).getId());
		trailer.setUsage(txtUsage.getText());
		trailer.setDivisionId(divisionList.get(ddlDivision.getSelectionModel().getSelectedIndex()).getDivisionId());
		trailer.setTerminalId(terminalList.get(ddlTerminal.getSelectionModel().getSelectedIndex()).getTerminalId());
		trailer.setTrailerTypeId(
				trailerTypeList.get(ddlTrailerType.getSelectionModel().getSelectedIndex()).getTypeId());
		trailer.setFinance(txtFinance.getText());
		return trailer;
	}

	private void closeEditTrailerScreen(Button btnSaveTrailer) {
		Stage loginStage = (Stage) btnSaveTrailer.getScene().getWindow();
		loginStage.close();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

	ObjectMapper mapper = new ObjectMapper();

	List<Category> categoryList = null;

	List<Status> statusList = null;

	List<Division> divisionList = null;

	List<Terminal> terminalList = null;

	List<Type> trailerTypeList = null;

	public void initData(Trailer t) {
		trailerId = t.getTrailerId();
		txtUnitNo.setText(String.valueOf(t.getUnitNo()));
		txtOwner.setText(t.getOwner());
		txtOoName.setText(t.getUsage());
		txtUsage.setText(t.getUsage());
		txtFinance.setText(t.getFinance());
		categoryList = t.getCategoryList();
		for (int i = 0; i < t.getCategoryList().size(); i++) {
			Category category = t.getCategoryList().get(i);
			ddlCategory.getItems().add(category.getName());
			if (category.getCategoryId() == t.getCategoryId()) {
				ddlCategory.getSelectionModel().select(i);
			}
		}
		statusList = t.getStatusList();
		for (int i = 0; i < t.getStatusList().size(); i++) {
			Status status = t.getStatusList().get(i);
			ddlStatus.getItems().add(status.getStatus());
			if (status.getId() == t.getStatusId()) {
				ddlStatus.getSelectionModel().select(i);
			}
		}
		divisionList = t.getDivisionList();
		for (int i = 0; i < t.getDivisionList().size(); i++) {
			Division division = t.getDivisionList().get(i);
			ddlDivision.getItems().add(division.getDivisionName());
			if (division.getDivisionId() == t.getDivisionId()) {
				ddlDivision.getSelectionModel().select(i);
			}
		}
		terminalList = t.getTerminalList();
		for (int i = 0; i < t.getTerminalList().size(); i++) {
			Terminal terminal = t.getTerminalList().get(i);
			ddlTerminal.getItems().add(terminal.getTerminalName());
			if (terminal.getTerminalId() == t.getTerminalId()) {
				ddlTerminal.getSelectionModel().select(i);
			}
		}
		trailerTypeList = t.getTrailerTypeList();
		for (int i = 0; i < t.getTrailerTypeList().size(); i++) {
			Type trailer = t.getTrailerTypeList().get(i);
			ddlTrailerType.getItems().add(trailer.getTypeName());
			if (trailer.getTypeId() == t.getTrailerTypeId()) {
				ddlTrailerType.getSelectionModel().select(i);
			}
		}
	}

	// -----------------------------------------------------------------------------

	@FXML
	Label lblUnitNo, lblOwner, lblOoName, lblStatus, lblCategory, lblUsage, lblDivision, lblTerminal, lblTrailerType,
			lblFinance;

	Validate validate = new Validate();

	private boolean validateAddDriverScreen() {

		boolean result = true;
		String unitNo = txtUnitNo.getText();
		// String owner = txtOwner.getText();
		// String usage = txtUsage.getText();
		// String ooName = txtOoName.getText();
		// String finance = txtFinance.getText();

		String status = ddlStatus.getSelectionModel().getSelectedItem();
		String category = ddlCategory.getSelectionModel().getSelectedItem();
		String division = ddlDivision.getSelectionModel().getSelectedItem();
		String terminal = ddlTerminal.getSelectionModel().getSelectedItem();
		String trailerType = ddlTrailerType.getSelectionModel().getSelectedItem();

		boolean blnUnitNo = validate.validateEmptyness(unitNo);
		if (!blnUnitNo) {
			result = false;
			txtUnitNo.setStyle("-fx-text-box-border: red;");
			lblUnitNo.setVisible(true);
			// lblUnitNo.setText("Company Name is Mandatory");
			// lblUnitNo.setTextFill(Color.RED);

		}

		/*
		 * boolean blnOwner = validate.validateEmptyness(owner); if (!blnOwner)
		 * { result = false; txtOwner.setStyle("-fx-text-box-border: red;");
		 * lblOwner.setVisible(true); //
		 * lblOwner.setText("Address is Mandatory"); //
		 * lblOwner.setTextFill(Color.RED); }
		 * 
		 * boolean blnUsage = validate.validateEmptyness(usage); if (!blnUsage)
		 * { result = false; txtUsage.setStyle("-fx-text-box-border: red;");
		 * lblUsage.setVisible(true); //
		 * lblUsage.setText("Phone Number is Mandatory"); //
		 * lblUsage.setTextFill(Color.RED); }
		 * 
		 * boolean blnOoname = validate.validateEmptyness(ooName); if
		 * (!blnOoname) { result = false;
		 * txtOoName.setStyle("-fx-text-box-border: red;");
		 * lblOoName.setVisible(true); //
		 * lblOoName.setText("Fax Number is Mandatory"); //
		 * lblOoName.setTextFill(Color.RED); }
		 * 
		 * boolean blnFinance = validate.validateEmptyness(finance); if
		 * (!blnFinance) { result = false;
		 * txtFinance.setStyle("-fx-text-box-border: red;");
		 * lblFinance.setVisible(true); //
		 * lblFinance.setText("Fax Number is Mandatory"); //
		 * lblFinance.setTextFill(Color.RED); }
		 */
		boolean blnStatus = validate.validateEmptyness(status);
		if (!blnStatus) {
			result = false;
			ddlStatus.setStyle("-fx-text-box-border: red;");
			lblStatus.setVisible(true);
			// lblStatus.setText("Fax Number is Mandatory");
			// lblStatus.setTextFill(Color.RED);
		}

		boolean blnCategory = validate.validateEmptyness(category);
		if (!blnCategory) {
			result = false;
			ddlCategory.setStyle("-fx-text-box-border: red;");
			lblCategory.setVisible(true);
			// lblCategory.setText("Fax Number is Mandatory");
			// lblCategory.setTextFill(Color.RED);
		}

		boolean blnDivision = validate.validateEmptyness(division);
		if (!blnDivision) {
			result = false;
			ddlDivision.setStyle("-fx-text-box-border: red;");
			lblDivision.setVisible(true);
			// lblDivision.setText("Fax Number is Mandatory");
			// lblDivision.setTextFill(Color.RED);
		}

		boolean blnTerminal = validate.validateEmptyness(terminal);
		if (!blnTerminal) {
			result = false;
			ddlTerminal.setStyle("-fx-text-box-border: red;");
			lblTerminal.setVisible(true);
			// lblTerminal.setText("Fax Number is Mandatory");
			// lblTerminal.setTextFill(Color.RED);
		}

		boolean blnTrailerType = validate.validateEmptyness(trailerType);
		if (!blnTrailerType) {
			result = false;
			ddlTrailerType.setStyle("-fx-text-box-border: red;");
			lblTrailerType.setVisible(true);
			// lblTrailerType.setText("Fax Number is Mandatory");
			// lblTrailerType.setTextFill(Color.RED);
		}

		return result;
	}

	@FXML
	private void trailerUnitKeyPressed() {

		String name = txtUnitNo.getText();
		boolean result = validate.validateEmptyness(name);
		if (result) {
			lblUnitNo.setTextFill(Color.BLACK);
			txtUnitNo.setStyle("-fx-focus-color: skyblue;");
		} else {
			txtUnitNo.setStyle("-fx-focus-color: red;");
			txtUnitNo.requestFocus();
			lblUnitNo.setVisible(true);
			// lblUnitNo.setText("Company Name is Mandatory");
			// lblUnitNo.setTextFill(Color.RED);
		}
	}

	/*
	 * @FXML private void trailerOwnerKeyPressed() {
	 * 
	 * String name = txtOwner.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblOwner.setTextFill(Color.BLACK);
	 * txtOwner.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtOwner.setStyle("-fx-focus-color: red;"); txtOwner.requestFocus();
	 * lblOwner.setVisible(true); //
	 * lblOwner.setText("Company Name is Mandatory"); //
	 * lblOwner.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void trailerUsageKeyPressed() {
	 * 
	 * String name = txtUsage.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblUsage.setTextFill(Color.BLACK);
	 * txtUsage.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtUsage.setStyle("-fx-focus-color: red;"); txtUsage.requestFocus();
	 * lblUsage.setVisible(true); //
	 * lblUsage.setText("Company Name is Mandatory"); //
	 * lblUsage.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void trailerOonameKeyPressed() {
	 * 
	 * String name = txtOoName.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblOoName.setTextFill(Color.BLACK);
	 * txtOoName.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtOoName.setStyle("-fx-focus-color: red;"); txtOoName.requestFocus();
	 * lblOoName.setVisible(true); //
	 * lblOoName.setText("Company Name is Mandatory"); //
	 * lblOoName.setTextFill(Color.RED); } }
	 * 
	 * @FXML private void trailerFinanceKeyPressed() {
	 * 
	 * String name = txtFinance.getText(); boolean result =
	 * validate.validateEmptyness(name); if (result) {
	 * lblFinance.setTextFill(Color.BLACK);
	 * txtFinance.setStyle("-fx-focus-color: skyblue;"); } else {
	 * txtFinance.setStyle("-fx-focus-color: red;"); txtFinance.requestFocus();
	 * lblFinance.setVisible(true); //
	 * lblFinance.setText("Company Name is Mandatory"); //
	 * lblFinance.setTextFill(Color.RED); } }
	 */
	@FXML
	private void trailerDdlStatusKeyPressed() {

		String textField = ddlStatus.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(textField);
		if (result) {
			lblStatus.setText("");
			ddlStatus.setStyle("-fx-focus-color: skyblue;");
			lblStatus.setVisible(false);
		} else {
			ddlStatus.setStyle("-fx-border-color: red;");
			lblStatus.setVisible(true);
			lblStatus.setText("TextField is Mandatory");
			// lblStatus.setTextFill(Color.RED);
		}
	}

	@FXML
	private void trailerDdlCategoryKeyPressed() {

		String textField = ddlCategory.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(textField);
		if (result) {
			lblCategory.setText("");
			ddlCategory.setStyle("-fx-focus-color: skyblue;");
			lblCategory.setVisible(false);
		} else {
			ddlCategory.setStyle("-fx-border-color: red;");
			lblCategory.setVisible(true);
			lblCategory.setText("TextField is Mandatory");
			// lblCategory.setTextFill(Color.RED);
		}
	}

	@FXML
	private void trailerDdlDivisionKeyPressed() {

		String textField = ddlDivision.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(textField);
		if (result) {
			lblDivision.setText("");
			ddlDivision.setStyle("-fx-focus-color: skyblue;");
			lblDivision.setVisible(false);
		} else {
			ddlDivision.setStyle("-fx-border-color: red;");
			lblDivision.setVisible(true);
			lblDivision.setText("TextField is Mandatory");
			// lblDivision.setTextFill(Color.RED);
		}
	}

	@FXML
	private void trailerTerminalKeyPressed() {

		String textField = ddlTerminal.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(textField);
		if (result) {
			lblTerminal.setText("");
			ddlTerminal.setStyle("-fx-focus-color: skyblue;");
			lblTerminal.setVisible(false);
		} else {
			ddlTerminal.setStyle("-fx-border-color: red;");
			lblTerminal.setVisible(true);
			lblTerminal.setText("TextField is Mandatory");
			// lblTerminal.setTextFill(Color.RED);
		}
	}

	@FXML
	private void trailerTypeKeyPressed() {

		String textField = ddlTrailerType.getSelectionModel().getSelectedItem();
		boolean result = validate.validateEmptyness(textField);
		if (result) {
			lblTrailerType.setText("");
			ddlTrailerType.setStyle("-fx-focus-color: skyblue;");
			lblTrailerType.setVisible(false);
		} else {
			ddlTrailerType.setStyle("-fx-border-color: red;");
			lblTrailerType.setVisible(true);
			lblTrailerType.setText("TextField is Mandatory");
			// lblTrailerType.setTextFill(Color.RED);
		}
	}

}