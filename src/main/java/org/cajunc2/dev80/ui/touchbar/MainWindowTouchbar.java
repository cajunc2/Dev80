package org.cajunc2.dev80.ui.touchbar;

import com.thizzer.jtouchbar.JTouchBar;
import com.thizzer.jtouchbar.item.TouchBarItem;
import com.thizzer.jtouchbar.item.view.TouchBarButton;

import org.cajunc2.dev80.simulator.ui.SimulatorWindow;
import org.cajunc2.dev80.ui.topic.Commands;

public class MainWindowTouchbar extends JTouchBar {
    public MainWindowTouchbar() {
        super();
        setCustomizationIdentifier("Dev80TouchBar");

        TouchBarButton compileButton = new TouchBarButton();
        compileButton.setAction((view) -> { Commands.BUILD_PROJECT.publish(); });
        compileButton.setImage(TouchbarIcons.iconFile("compile.png"));
        TouchBarItem compileItem = new TouchBarItem("CompileProject", compileButton, true);
        compileItem.setCustomizationLabel("Compile Project");

        TouchBarButton burnButton = new TouchBarButton();
        burnButton.setAction((view) -> { Commands.BURN_ROM.publish(); });
        burnButton.setImage(TouchbarIcons.iconFile("burn.png"));
        TouchBarItem burnItem = new TouchBarItem("BurnROM", burnButton, true);
        burnItem.setCustomizationLabel("Burn EEPROM");

        TouchBarButton simButton = new TouchBarButton();
        simButton.setAction((view) -> { new SimulatorWindow(null).setVisible(true); });
        simButton.setImage(TouchbarIcons.iconFile("processor.png"));
        TouchBarItem simItem = new TouchBarItem("StartSimulator", simButton, true);
        simItem.setCustomizationLabel("Show Simulator");

        addItem(compileItem);
        addItem(burnItem);
        addItem(new TouchBarItem(TouchBarItem.NSTouchBarItemIdentifierFixedSpaceSmall));
        addItem(simItem);


    }
}