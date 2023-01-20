package net.maclux;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;

public class MacluxScreen extends Screen {

    public MacluxScreen(Screen parent, GameOptions settings) {
        super(Text.translatable("Maclux Settings"));
    }

    Text restockerText() {
        if (maclux.restockerEnabled) {
            return Text.translatable("Restocker Enabled");
        } else {
            return Text.translatable("Restocker Disabled");
        }
    }

    Text packetLoggerText() {
        if (maclux.packetLoggerEnabled) {
            return Text.translatable("Packet Logger Enabled");
        } else {
            return Text.translatable("Packet Logger Disabled");
        }
    }

    Text packetReducerText() {
        if (maclux.packetReducerEnabled) {
            return Text.translatable("Packet Reducer Enabled");
        } else {
            return Text.translatable("Packet Reducer Disabled");
        }
    }

    Text invWalkText() {
        if (maclux.invWalkEnabled) {
            return Text.translatable("Inventory Walk Enabled");
        } else {
            return Text.translatable("Inventory Walk Disabled");
        }
    }

    protected void init() {
        // inv Walk Button
        this.addDrawableChild(ButtonWidget.builder(invWalkText(), button -> {
            maclux.invWalkEnabled = !maclux.invWalkEnabled;
            button.setMessage(invWalkText());
        }).dimensions(this.width / 2 - 100, this.height / 6 + 40, 200, 20).build());
        // restocker Button
        this.addDrawableChild(ButtonWidget.builder(restockerText(), button -> {
            maclux.restockerEnabled = !maclux.restockerEnabled;
            button.setMessage(restockerText());
        }).dimensions(this.width / 2 - 100, this.height / 6 + 70, 200, 20).build());
        // packet logger Button
        this.addDrawableChild(ButtonWidget.builder(packetLoggerText(), button -> {
            maclux.packetLoggerEnabled = !maclux.packetLoggerEnabled;
            button.setMessage(packetLoggerText());
        }).dimensions(this.width / 2 - 100, this.height / 6 + 100, 200, 20).build());

        // packet reducer Button
        this.addDrawableChild(ButtonWidget.builder(packetReducerText(), button -> {
            maclux.packetReducerEnabled = !maclux.packetReducerEnabled;
            button.setMessage(packetReducerText());
        }).dimensions(this.width / 2 - 100, this.height / 6 + 130, 200, 20).build());
    }
}
