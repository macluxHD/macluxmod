package net.maclux.mixin;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.maclux.maclux;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;

/*
 * This mixin is used to move the stack to the slot designated previously in the BlockPlacedMixin.
 */
@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Inject(method = "interactBlock", at = @At("TAIL"))
    private void interactBlockMixin(ClientPlayerEntity player, Hand hand, BlockHitResult hitResult,
            CallbackInfoReturnable<ActionResult> info) {
        // Logger LOGGER =
        // LoggerFactory.getLogger("ClientPlayerInteractionManagerMixin");
        if (maclux.moveStackToSlot == null || !maclux.restockerEnabled)
            return;

        MinecraftClient mc = MinecraftClient.getInstance();
        ClientPlayerEntity playerClient = mc.player;
        ClientPlayerInteractionManager interactionManager = mc.interactionManager;

        // Move the stack to the slot
        interactionManager.clickSlot(playerClient.playerScreenHandler.syncId, maclux.moveStackToSlot[0],
                maclux.moveStackToSlot[1], SlotActionType.SWAP, playerClient);
        // Play a sound to indicate the restocking
        player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
        maclux.moveStackToSlot = null;
    }

}
