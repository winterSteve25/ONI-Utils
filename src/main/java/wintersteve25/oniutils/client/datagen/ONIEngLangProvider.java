package wintersteve25.oniutils.client.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.data.LanguageProvider;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.common.blocks.base.ONIBaseDirectional;
import wintersteve25.oniutils.common.blocks.base.ONIBaseBlock;
import wintersteve25.oniutils.common.blocks.base.ONIBaseSixWaysBlock;
import wintersteve25.oniutils.client.gui.ONIBaseGuiTab;
import wintersteve25.oniutils.common.effects.ONIBaseEffect;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIEffects;
import wintersteve25.oniutils.common.utils.helper.MiscHelper;

public class ONIEngLangProvider extends LanguageProvider {
    public ONIEngLangProvider(DataGenerator gen) {
        super(gen, ONIUtils.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        autoGenLang();

        //simple blocks
        add(ONIBlocks.IgneousRock.get(), "Igneous Rock");

        //item groups
        add("itemGroup.oniutils", "Veiled Ascent");

        //messages
        add("oniutils.message.germs.infectEntity", "Infected interacted entity with %s %s");
        add("oniutils.message.germs.infectItem", "Infected item(s) with %s %s");
        add("oniutils.message.germs.infectPlayer", "Infected with %s %s");

        add("oniutils.message.trait.gotTrait", "Traits: %s");
        add("oniutils.message.trait.traitInfo", "Check Trait infos here: https://github.com/FictionCraft-Team/ONI-Utils/wiki/Trait-System");

        add("oniutils.message.manualGen", "Use Get off manual generator key(Right-Shift By Default) to dismount..");

        add("death.attack.oniutils.oxygen", "%1$s forgot to breathe");
        add("death.attack.oniutils.gas", "%1$s forgot to wear a \"mask\"");
        add("death.attack.oniutils.germ", "%1$s didn't take the vaccine");

        add("oniutils.commands.germs.set.success", "Germ Amount Set to: %s %s");
        add("oniutils.commands.germs.set.failed.typeIsNull", "Germ type is not valid");
        add("oniutils.commands.set.failed.entityIsNull", "Target can not be null");
        add("oniutils.commands.gas.set.failed.typeIsNull", "Gas type is not valid");

        //tooltips
        add("oniutils.tooltips.germs.itemGerms", TextFormatting.GREEN + "Surface Germs: %s %s");
        add("oniutils.tooltips.items.holdShiftInfo", TextFormatting.DARK_GRAY + "Hold" + TextFormatting.WHITE + " <Sneak> " + TextFormatting.DARK_GRAY + "For More Info");
        add("oniutils.tooltips.items.coal_gen", TextFormatting.GRAY + "Coal Generator is a primitive generator that takes only coal, to generate a small amount of plasma");
        add("oniutils.tooltips.items.manual_gen", TextFormatting.GRAY + "Manual Generator will generate a tiny amount of plasma when a player is running on it. It will generate more power when the player has speed effect");

        //gui
        add("oniutils.gui.machines.power", "Plasma Stored: %s Pls");
        add("oniutils.gui.machines.coal_gen", TextFormatting.BLACK + "Coal Generator");
        add("oniutils.gui.machines.manual_gen", TextFormatting.BLACK + "Manual Generator");

        add("oniutils.gui.titles.warning", TextFormatting.DARK_RED + "Warnings");
        add("oniutils.gui.titles.redstoneOutput", TextFormatting.RED + "Redstone Output");
        add("oniutils.gui.titles.coal_gen", "Coal Generator");
        add("oniutils.gui.titles.manual_gen", "Manual Generator");

        add("oniutils.gui.info.energy", "Plasma Stored: %s");
        add("oniutils.gui.info.producingEnergy", "Producing Plasma: %s");
        add("oniutils.gui.info.consumingEnergy", "Consuming Plasma: %s");
        add("oniutils.gui.info.producingGas", "Producing Gas: %s");
        add("oniutils.gui.info.consumingGas", "Consuming Gas: %s");
        add("oniutils.gui.info.producingLiquid", "Producing Liquid: %s");
        add("oniutils.gui.info.consumingLiquid", "Consuming Liquid: %s");
        add("oniutils.gui.info.progress", "Progress: %s");

        add("oniutils.gui.warning.durability", "Low Machine Durability!");
        add("oniutils.gui.warning.temperature", "Extreme Temperature!");
        add("oniutils.gui.warning.gasPressure", "High Gas Pressure!");
        add("oniutils.gui.warning.wrongGas", "Wrong Gas Input!");
        add("oniutils.gui.warning.allClear", "All Clear");

        add(ONIBaseGuiTab.REDSTONE_LOW, "Low Threshold: %s");
        add(ONIBaseGuiTab.REDSTONE_HIGH, "High Threshold: %s");
        add(ONIBaseGuiTab.REDSTONE_INVALID_NUMBER, "Invalid Number!");

        //keybinds
        add("oniutils.keybinds.category", "Veiled Ascent Keybinds");
        add("oniutils.offManualGen", "Get off manual generator");
    }

    private void autoGenLang() {
        for (ONIBaseBlock b : ONIBlocks.blockList.keySet()) {
            add("block.oniutils." + MiscHelper.langToReg(b.getRegName()), b.getRegName());
        }
        for (ONIBaseBlock b : ONIBlocks.blockNoDataList.keySet()) {
            add("block.oniutils." + MiscHelper.langToReg(b.getRegName()), b.getRegName());
        }
        for (ONIBaseDirectional b : ONIBlocks.directionalList.keySet()) {
            add("block.oniutils." + MiscHelper.langToReg(b.getRegName()), b.getRegName());
        }
        for (ONIBaseDirectional b : ONIBlocks.directionalNoDataList.keySet()) {
            add("block.oniutils." + MiscHelper.langToReg(b.getRegName()), b.getRegName());
        }
        for (ONIBaseSixWaysBlock b : ONIBlocks.sixWaysList.keySet()) {
            add("block.oniutils." + MiscHelper.langToReg(b.getRegName()), b.getRegName());
        }
        for (ONIBaseEffect e : ONIEffects.effectList) {
            add("effect.oniutils." + MiscHelper.langToReg(e.getRegName()), e.getRegName());
        }
    }
}
