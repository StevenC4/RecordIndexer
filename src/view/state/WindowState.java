package view.state;

import java.awt.*;

import static java.awt.Toolkit.getDefaultToolkit;

/**
 * Created with IntelliJ IDEA.
 * User: Steven
 * Date: 4/6/14
 * Time: 5:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class WindowState {
    int windowWidth;
    int windowHeight;
    int horizontalBarPosition;
    int verticalBarPosition;
    int windowPositionX;
    int windowPositionY;

    public WindowState() {
        windowWidth = 890;
        windowHeight = 575;
        horizontalBarPosition = windowHeight / 2 - 70;
        verticalBarPosition = windowWidth / 2;

        Dimension screenSize = getDefaultToolkit().getScreenSize();

        windowPositionX = (int) (screenSize.getWidth() - windowWidth) / 2;
        windowPositionY = (int) (screenSize.getHeight() - windowHeight) / 2;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getHorizontalBarPosition() {
        return horizontalBarPosition;
    }

    public void setHorizontalBarPosition(int horizontalBarPosition) {
        this.horizontalBarPosition = horizontalBarPosition;
    }

    public int getVerticalBarPosition() {
        return verticalBarPosition;
    }

    public void setVerticalBarPosition(int verticalBarPosition) {
        this.verticalBarPosition = verticalBarPosition;
    }

    public int getWindowPositionX() {
        return windowPositionX;
    }

    public void setWindowPositionX(int windowPositionX) {
        this.windowPositionX = windowPositionX;
    }

    public int getWindowPositionY() {
        return windowPositionY;
    }

    public void setWindowPositionY(int windowPositionY) {
        this.windowPositionY = windowPositionY;
    }
}
