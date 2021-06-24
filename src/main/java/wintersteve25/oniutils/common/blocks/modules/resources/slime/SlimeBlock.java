package wintersteve25.oniutils.common.blocks.modules.resources.slime;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import wintersteve25.oniutils.common.blocks.base.ONIBaseRock;
import wintersteve25.oniutils.common.capability.germ.GermCapabilityProvider;
import wintersteve25.oniutils.common.capability.germ.GermsCapability;
import wintersteve25.oniutils.common.capability.germ.api.EnumGermTypes;
import wintersteve25.oniutils.common.capability.germ.api.GermStack;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.utils.helper.MiscHelper;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SlimeBlock extends ONIBaseRock {
    private static final String regName = "Slime";

    public SlimeBlock() {
        super(regName, Properties.of(Material.DIRT).harvestLevel(0).strength(0.25F, 0.25F).sound(SoundType.SLIME_BLOCK));
    }

    @Override
    public void spawnAfterBreak(BlockState state, ServerWorld serverWorld, BlockPos pos, ItemStack stack) {
        TileEntity tile = serverWorld.getBlockEntity(pos);

        AtomicInteger amount = new AtomicInteger();
        AtomicReference<EnumGermTypes> germ = null;

        if (tile instanceof SlimeTE) {
            SlimeTE te = (SlimeTE) tile;

            te.getCapability(GermsCapability.GERM_CAPABILITY).ifPresent(s -> {
                amount.set(s.getGermAmount());
                germ.set(s.getGermType());
                super.spawnAfterBreak(state, serverWorld, pos, new SlimeBlockItem().setGerm(germ.get(), amount.get()));
            });
        } else {
            super.spawnAfterBreak(state, serverWorld, pos, stack);
        }
    }

    @Override
    public String getRegName() {
        return regName;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ONIBlocks.SLIME_TE.get().create();
    }

    public static class SlimeBlockItem extends BlockItem {
        GermStack germStack = new GermStack();

        public SlimeBlockItem() {
            super(ONIBlocks.Slime, MiscHelper.DEFAULT_ITEM_PROPERTY);

            germStack.setGerm(EnumGermTypes.SLIMELUNG, 15000);
        }

        @Nullable
        @Override
        public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
            return new GermCapabilityProvider(germStack);
        }

        public ItemStack setGerm(EnumGermTypes germTypes, int amount) {
            germStack.setGerm(germTypes, amount);

            return new ItemStack(this);
        }
    }

}
