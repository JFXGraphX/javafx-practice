/*
 * Copyright 2014 Jens Deters. Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package de.jensd.javafx.utils;

import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;

/**
 * 
 * @author Jens Deters
 */
public class SplitPaneDemoController implements Initializable
{

    @FXML
    private ToggleButton bottomToggleButton;

    @FXML
    private SplitPane centerSplitPane;

    @FXML
    private ToggleButton leftToggleButton;

    @FXML
    private SplitPane mainSplitPane;

    @FXML
    private ToggleButton rightToggleButton;

    @FXML
    private ToggleButton topToggleButton;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        final SplitPaneDividerSlider leftSplitPaneDividerSlider = new SplitPaneDividerSlider(centerSplitPane, 0, SplitPaneDividerSlider.Direction.LEFT);
        final SplitPaneDividerSlider rightSplitPaneDividerSlider = new SplitPaneDividerSlider(centerSplitPane, 1, SplitPaneDividerSlider.Direction.RIGHT);
        final SplitPaneDividerSlider topSplitPaneDividerSlider = new SplitPaneDividerSlider(mainSplitPane, 0, SplitPaneDividerSlider.Direction.UP);
        final SplitPaneDividerSlider bottomSplitPaneDividerSlider = new SplitPaneDividerSlider(mainSplitPane, 1, SplitPaneDividerSlider.Direction.DOWN);

        leftToggleButton.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                leftSplitPaneDividerSlider.setAimContentVisible(t1);
            }
        });

        rightToggleButton.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                rightSplitPaneDividerSlider.setAimContentVisible(t1);
            }
        });

        topToggleButton.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                topSplitPaneDividerSlider.setAimContentVisible(t1);
            }
        });
        bottomToggleButton.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                bottomSplitPaneDividerSlider.setAimContentVisible(t1);
            }
        });

        AwesomeDude.setIcon(leftToggleButton, AwesomeIcon.TOGGLE_LEFT, "2em");
        AwesomeDude.setIcon(rightToggleButton, AwesomeIcon.TOGGLE_RIGHT, "2em");
        AwesomeDude.setIcon(topToggleButton, AwesomeIcon.TOGGLE_UP, "2em");
        AwesomeDude.setIcon(bottomToggleButton, AwesomeIcon.TOGGLE_DOWN, "2em");

        leftToggleButton.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                if (t1)
                {
                    AwesomeDude.setIcon(leftToggleButton, AwesomeIcon.TOGGLE_LEFT, "2em");
                } else
                {
                    AwesomeDude.setIcon(leftToggleButton, AwesomeIcon.TOGGLE_RIGHT, "2.5em");
                }
            }
        });
        
        new InvalidationListener()
        {
            
            @Override
            public void invalidated(Observable arg0)
            {
                // TODO Auto-generated method stub
                
            }
        };

        rightToggleButton.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                if (t1)
                {
                    AwesomeDude.setIcon(rightToggleButton, AwesomeIcon.TOGGLE_RIGHT, "2em");
                } else
                {
                    AwesomeDude.setIcon(rightToggleButton, AwesomeIcon.TOGGLE_LEFT, "2.5em");
                }
            }
        });

        topToggleButton.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                if (t1)
                {
                    AwesomeDude.setIcon(topToggleButton, AwesomeIcon.TOGGLE_UP, "2em");
                } else
                {
                    AwesomeDude.setIcon(topToggleButton, AwesomeIcon.TOGGLE_DOWN, "2.5em");
                }
            }
        });

        bottomToggleButton.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                if (t1)
                {
                    AwesomeDude.setIcon(bottomToggleButton, AwesomeIcon.TOGGLE_DOWN, "2em");
                } else
                {
                    AwesomeDude.setIcon(bottomToggleButton, AwesomeIcon.TOGGLE_UP, "2.5em");
                }
            }
        });
    }
}
