package wintersteve25.oniutils.common.contents.base;

import harmonised.pmmo.api.APIUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.api.*;
import wintersteve25.oniutils.common.contents.modules.items.modifications.ONIModificationItem;

import javax.annotation.Nullable;

public class ONIBaseMachine extends ONIBaseDirectional {

    // block builder properties
    private ONIIHasGui gui;

    public ONIBaseMachine(int harvestLevel, float hardness, float resistance, String regName, SoundType soundType, Material material) {
        super(harvestLevel, hardness, resistance, regName, soundType, material);
    }

    public ONIBaseMachine(String regName, Properties properties) {
        super(regName, properties);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (!world.isRemote()) {
            TileEntity tileEntity = world.getTileEntity(pos);
            ItemStack heldItem = player.getHeldItem(hand);
            super.onBlockActivated(state, world, pos, player, hand, rayTraceResult);
            if (isCorrectTe(tileEntity)) {
                if (tileEntity instanceof ONIIModifiable && tileEntity instanceof ONIBaseTE) {
                    ONIBaseTE baseTE = (ONIBaseTE) tileEntity;
                    baseTE.onBlockActivated(state, world, pos, player, hand, rayTraceResult);
                    if (!heldItem.isEmpty() && heldItem.getItem() instanceof ONIModificationItem) {
                        ONIIModifiable modifiable = (ONIIModifiable) tileEntity;
                        if (modifiable.addMod((ONIBaseTE) tileEntity, heldItem)) {
                            player.swing(hand, true);
                            return ActionResultType.SUCCESS;
                        }
                    }
                }

                if (tileEntity instanceof ONIIRequireSkillToInteract) {
                    ONIIRequireSkillToInteract skill = (ONIIRequireSkillToInteract) tileEntity;
                    for (String skillRequired : skill.requiredSkill().keySet()) {
                        if (APIUtils.getLevel(skillRequired, player) < skill.getRequiredLevel(skillRequired)) {
                            player.sendStatusMessage(new TranslationTextComponent("oniutils.message.needLevel", skill.getRequiredLevel(skillRequired), skillRequired), true);
                            return ActionResultType.FAIL;
                        }
                    }
                }

                if (gui != null || this instanceof ONIIHasGui) {
                    if (gui == null) {
                        gui = (ONIIHasGui) this;
                    }
                    if (gui.machineName() != null) {
                        INamedContainerProvider containerProvider = new INamedContainerProvider() {
                            @Override
                            public ITextComponent getDisplayName() {
                                return gui.machineName();
                            }

                            @Override
                            public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                                return gui.container(i, world, pos, playerInventory, playerEntity);
                            }
                        };
                        NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getPos());
                    }
                }
            } else {
                ONIUtils.LOGGER.warn("Wrong tileEntity type found, failed to create container");
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Nullable
    public ONIIHasGui getGui() {
        return gui == null ? this instanceof ONIIHasGui ? (ONIIHasGui) this : null : gui;
    }

    public void setGui(ONIIHasGui gui) {
        this.gui = gui;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}