package net.maclux.mixin;

import org.jetbrains.annotations.Nullable;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.maclux.maclux;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/*
 * Part of the restocker, this determines which slot the block was placed from and which slot it should be moved to.
 */
@Mixin(Block.class)
public abstract class BlockPlacedMixin {

    // Modified from PlayerInventory.getSlotWithStack allows ignoring a slot
    public int getSlotWithStackAndIgnoreSlot(PlayerInventory inv, ItemStack stack, int slot) {
        for (int i = 0; i < inv.main.size(); ++i) {
            if (inv.main.get(i).isEmpty() || !ItemStack.canCombine(stack, inv.main.get(i)) || i == slot)
                continue;
            return i;
        }
        return -1;
    }

    @Inject(method = "onPlaced", at = @At("HEAD"))
    private void place(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack,
            CallbackInfo info) {
        // Logger LOGGER = LoggerFactory.getLogger("BlockPlacedMixin");

        if (!(placer instanceof PlayerEntity) || !maclux.restockerEnabled || itemStack.getCount() != 1
                || ((PlayerEntity) placer).getAbilities().creativeMode) {
            maclux.moveStackToSlot = null;
            return;
        }

        PlayerInventory inv = ((PlayerEntity) placer).getInventory();
        int currentSlot = inv.selectedSlot;
        int itemSlot = getSlotWithStackAndIgnoreSlot(inv, new ItemStack(itemStack.getItem()), currentSlot);

        if (itemSlot == -1)
            return;

        // LOGGER.info("itemSlot: " + itemSlot);
        if (itemSlot >= -1 && itemSlot <= 8)
            itemSlot += 36;
        // Save the slot indexes so that the restocker can move the stack later on
        maclux.moveStackToSlot = new int[] { itemSlot, currentSlot };
    }
}