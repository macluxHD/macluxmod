package net.maclux.mixin;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;

import net.maclux.maclux;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import net.minecraft.text.Text;

@Mixin(ClientPlayNetworkHandler.class)
public class SendPacketMixin {

    private String previousPacketName = "";

    @Inject(method = "sendPacket", at = @At("HEAD"))
    private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        if (maclux.packetLoggerEnabled) {
            MinecraftClient mc = MinecraftClient.getInstance();

            String packetName = packet.getClass().getName();
            packetName = packetName.substring(packetName.lastIndexOf('.') + 1);

            if (!maclux.packedReducerEnabled || !packetName.equals(previousPacketName)) {
                previousPacketName = packetName;

                mc.inGameHud.getChatHud()
                        .addMessage(Text.empty().append(Text.translatable(packetName)));
            }
        }
    }
}