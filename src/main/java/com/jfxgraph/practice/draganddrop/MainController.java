package com.jfxgraph.practice.draganddrop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class MainController implements Initializable {
	@FXML
	private AnchorPane mRoot;
	@FXML
	private Label m_drag, m_drop;
	@FXML
	private ImageView m_imageView;
	@FXML
	private TextArea m_textArea;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		m_drag.setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Dragboard dragboard = m_drag.startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				content.putString(m_drag.getText());
				dragboard.setContent(content);
			}
		});

		m_drop.setOnDragEntered(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				m_drop.setTextFill(Color.RED);
			}
		});

		m_drop.setOnDragExited(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				m_drop.setTextFill(Color.BLACK);
			}
		});

		m_drop.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if (event.getGestureSource() != m_drop && event.getDragboard().hasString()) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
			}
		});

		m_drop.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard dragboard = event.getDragboard();
				m_drop.setText(dragboard.getString());
			}
		});

		m_drag.setOnDragDone(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				m_drag.setText("");
			}
		});
		
		
		m_imageView.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if (event.getGestureSource() != m_imageView) {
					event.acceptTransferModes(TransferMode.ANY);
				}				
			}
		});
		
		m_imageView.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard dragboard = event.getDragboard();
				List<File> files = dragboard.getFiles();
				if(files.size() > 0){
					try {
						m_imageView.setImage(new Image(new FileInputStream(files.get(0))));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		m_textArea.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if (event.getGestureSource() != m_imageView) {
					event.acceptTransferModes(TransferMode.ANY);
				}				
			}
		});
		
		m_textArea.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard dragboard = event.getDragboard();
				List<File> files = dragboard.getFiles();
				if(files.size() > 0){
					m_textArea.setText(FileTools.readFile(files.get(0)));
				}
			}
		});
		
	}

}
