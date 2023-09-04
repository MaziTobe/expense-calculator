package com.motobe.expensetracker;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class AppState {
    protected static final double WIDTH = 400.0D;
    protected static final double HEIGHT = 600.0D;
    protected static final Background appBackground = new Background(new BackgroundFill[]{new BackgroundFill(new Color(0.3D, 0.3D, 0.3D, 1.0D), new CornerRadii(0.0D), new Insets(0.0D))});
    protected static final Background chartBackground = new Background(new BackgroundFill[]{new BackgroundFill(new Color(0.8D, 0.7D, 0.8D, 1.0D), new CornerRadii(0.0D), new Insets(0.0D))});
    protected static final Background labelBackground = new Background(new BackgroundFill[]{new BackgroundFill(new Color(0.2D, 0.3D, 0.3D, 1.0D), new CornerRadii(0.0D), new Insets(0.0D))});
    protected static final Background earnButtonBackground = new Background(new BackgroundFill[]{new BackgroundFill(new Color(0.2D, 0.8D, 0.2D, 1.0D), new CornerRadii(0.0D), new Insets(0.0D))});
    protected static final Background spendButtonBackground = new Background(new BackgroundFill[]{new BackgroundFill(new Color(0.8D, 0.2D, 0.3D, 1.0D), new CornerRadii(0.0D), new Insets(0.0D))});
    protected static final Background quitButtonBackground = new Background(new BackgroundFill[]{new BackgroundFill(new Color(0.2D, 0.6D, 0.9D, 1.0D), new CornerRadii(0.0D), new Insets(0.0D))});
    protected static final Color textColorNormal = new Color(0.6D, 0.6D, 0.9D, 1.0D);
    protected static final Color textColorHover = new Color(0.9D, 0.5D, 0.5D, 1.0D);
    protected static final Font chartFont;
    protected static final Font inputFont;
    protected static final Font labelFont;
    protected static final Font buttonFont;
    protected static final Font textFont;
    protected static final Color labelTextFill;
    protected static final Color buttonTextFill;

    public AppState() {
    }

    static {
        chartFont = Font.font((String)null, FontWeight.BOLD, FontPosture.ITALIC, 26.0D);
        inputFont = Font.font((String)null, FontWeight.BOLD, FontPosture.ITALIC, 26.0D);
        labelFont = Font.font((String)null, FontWeight.BOLD, FontPosture.ITALIC, 16.0D);
        buttonFont = Font.font((String)null, FontWeight.BOLD, FontPosture.ITALIC, 15.0D);
        textFont = Font.font((String)null, FontWeight.BOLD, FontPosture.ITALIC, 26.0D);
        labelTextFill = new Color(0.6D, 0.7D, 0.8D, 1.0D);
        buttonTextFill = new Color(1.0D, 1.0D, 1.0D, 1.0D);
    }
}