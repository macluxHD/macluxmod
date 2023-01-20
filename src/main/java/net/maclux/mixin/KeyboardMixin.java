package net.maclux.mixin;

import java.util.Arrays;

import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.maclux.maclux;
import net.minecraft.client.Keyboard;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    // TODO: prevent player from stopping when inventory is opened
    @Inject(method = "onKey()V", at = @At(value = "RETURN", ordinal = 3))
    private void setKeyboardCallbacksReturn(long window, int key, int scancode, int action, int modifiers,
            CallbackInfo ci) {

        int[] allowedKeys = { 87, 65, 68, 83, 32 };

        if (maclux.invWalkEnabled && Arrays.stream(allowedKeys).anyMatch(x -> x == key)) {
            InputUtil.Key key2 = InputUtil.fromKeyCode(key, scancode);
            KeyBinding.setKeyPressed(key2, true);
            KeyBinding.onKeyPressed(key2);
        }
    }
}
