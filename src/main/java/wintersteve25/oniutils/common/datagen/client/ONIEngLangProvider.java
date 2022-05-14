package wintersteve25.oniutils.common.datagen.client;

import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.ChatFormatting;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.ModList;
import wintersteve25.oniutils.ONIUtils;
import wintersteve25.oniutils.client.gui.ONIBaseGuiTab;
import wintersteve25.oniutils.common.contents.base.ONIIRegistryObject;
import wintersteve25.oniutils.common.data.capabilities.world_gas.api.chemistry.Element;
import wintersteve25.oniutils.common.compat.curios.CuriosCompat;
import wintersteve25.oniutils.common.init.ONIBlocks;
import wintersteve25.oniutils.common.init.ONIItems;
import wintersteve25.oniutils.common.utils.helpers.MiscHelper;

public class ONIEngLangProvider extends LanguageProvider {
    public ONIEngLangProvider(DataGenerator gen) {
        super(gen, ONIUtils.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        autoGenLang();

        //item groups
        add("itemGroup.oniutils", "FC: ONIUtils");

        //messages
        add("oniutils.message.germs.infectEntity", "Infected interacted entity with %s %s");
        add("oniutils.message.germs.infectItem", "Infected item(s) with %s %s");
        add("oniutils.message.germs.infectPlayer", "Infected with %s %s");

        add("oniutils.message.trait.gotTrait", "Traits: %s");
        add("oniutils.message.trait.traitInfo", "Check Trait infos here: https://github.com/FictionCraft-Team/ONI-Utils/wiki/Trait-System");

        add("oniutils.message.needLevel", ChatFormatting.RED + "Requires %s level of %s to operate... Perhaps some entanglement modifications might help");

        add("oniutils.message.manualGen", "Use Get off manual generator key(Right-Shift By Default) to dismount..");

        add("death.attack.oniutils.oxygen", "%1$s forgot to breathe");
        add("death.attack.oniutils.gas", "%1$s forgot to wear a \"mask\"");
        add("death.attack.oniutils.germ", "%1$s didn't take the vaccine");

        add("oniutils.commands.germs.set.success", "Germ Amount Set to: %s %s");
        add("oniutils.commands.germs.set.failed.typeIsNull", "Germ type is not valid");
        add("oniutils.commands.set.failed.entityIsNull", "Target can not be null");
        add("oniutils.commands.gas.set.failed.typeIsNull", "Gas type is not valid");

        //tooltips
        add("oniutils.tooltips.germs.itemGerms", ChatFormatting.GREEN + "Surface Germs: %s %s");
        add("oniutils.tooltips.items.holdShiftInfo", ChatFormatting.DARK_GRAY + "Hold" + ChatFormatting.WHITE + " <Sneak> " + ChatFormatting.DARK_GRAY + "For More Info");
        add("oniutils.tooltips.items.coal_generator", ChatFormatting.GRAY + "Coal Generator is a primitive generator that takes only coal, to generate a small amount of plasma");
        add("oniutils.tooltips.items.manual_gen", ChatFormatting.GRAY + "Manual Generator will generate a tiny amount of plasma when a player is running on it. It will generate more power when the player has speed effect");
        add("oniutils.tooltips.items.modification", ChatFormatting.DARK_AQUA + "Modifications allow attributes of machines to be modified");
        add("oniutils.tooltips.items.modification.velocity", ChatFormatting.GRAY + "Velocity Modification allow you to modify machine's operating speed. Up to 125%/-125%. 25% more each tier");
        add("oniutils.tooltips.items.modification.energy", ChatFormatting.GRAY + "Plasma Conservation Modification allow you to modify machine's power consumption rate. Up to 110%/-110%. 10% more each tier");
        add("oniutils.tooltips.items.modification.gas", ChatFormatting.GRAY + "Gas Efficiency Modification allow you to modify machine's gas consumption rate. Up to 125%/-125%. 25% more each tier");
        add("oniutils.tooltips.items.modification.fluid", ChatFormatting.GRAY + "Fluid Efficiency Modification allow you to modify machine's fluid consumption rate. Up to 125%/-125%. 25% more each tier");
        add("oniutils.tooltips.items.modification.temperature", ChatFormatting.GRAY + "Temperature Modification allow you to modify machine's temperature capacity. Up to 120%/-120%. 20% more each tier");
        add("oniutils.tooltips.items.modification.complexity", ChatFormatting.GRAY + "Complexity Modification allow you to modify machine's required operation skill level. Up to 125%/-125%. 25% more each tier");
        add("oniutils.tooltips.items.gas_visual_goggles", ChatFormatting.GRAY + "Put on to see the gases move in real time!");
        add("oniutils.tooltips.items.wire_cutter", ChatFormatting.GRAY + "Makes removing power cables easier!");

        //gui
        add("oniutils.gui.machines.power", "Plasma Stored: %s Pls");
        add("oniutils.gui.machines.upgradeNotSupported", "Modification Not Supported");
        add("oniutils.gui.machines.progress", "%s Ticks Left");

        add("oniutils.gui.titles.warning", ChatFormatting.DARK_RED + "Warnings");
        add("oniutils.gui.titles.redstoneOutput", ChatFormatting.RED + "Redstone Output");
        add("oniutils.gui.titles.modifications", ChatFormatting.DARK_AQUA + "Modifications");
        add("oniutils.gui.titles.invert", "Invert Redstone");

        add("oniutils.gui.titles.coal_generator", ChatFormatting.WHITE + "Coal Generator");
        add("oniutils.gui.titles.manual_gen", ChatFormatting.WHITE + "Manual Generator");

        add("oniutils.gui.info.energy", "Plasma Stored: %s");
        add("oniutils.gui.info.producingEnergy", "+%s Plasma/t");
        add("oniutils.gui.info.consumingEnergy", "-%s Plasma/t");
        add("oniutils.gui.info.producingGas", "+%s/t");
        add("oniutils.gui.info.consumingGas", "-%s/t");
        add("oniutils.gui.info.producingLiquid", "+%s/t");
        add("oniutils.gui.info.consumingLiquid", "-%s/t");
        add("oniutils.gui.info.progress", "Progress: %s");

        add("oniutils.gui.warning.durability", "Low Machine Durability!");
        add("oniutils.gui.warning.temperature", "Extreme Temperature!");
        add("oniutils.gui.warning.gasPressure", "High Gas Pressure!");
        add("oniutils.gui.warning.wrongGas", "Wrong Gas Input!");
        add("oniutils.gui.warning.allClear", "All Clear");

        add(ONIBaseGuiTab.REDSTONE_LOW, "Low Threshold: %s");
        add(ONIBaseGuiTab.REDSTONE_HIGH, "High Threshold: %s");
        add(ONIBaseGuiTab.REDSTONE_INVALID_NUMBER, "Invalid Number!");

        add("oniutils.gui.items.modification.bonus", "Modification Value: %s");

        // keybinds
        add("oniutils.keybinds.category", "FC: ONIUtils Keybinds");
        add("oniutils.offManualGen", "Get off manual generator");

        // curios
        if (ModList.get().isLoaded("curios")) {
            CuriosCompat.lang(this);
        }
    }

    private void autoGenLang() {
        for (ONIIRegistryObject<Block> b : ONIBlocks.blockList.keySet()) {
            if (b.doLangGen()) add("block.oniutils." + MiscHelper.langToReg(b.getRegName()), b.getRegName());
        }
        for (Element element : Element.values()) {
            add("gas.oniutils." + element.getName(), element.getLang() + " Gas");
        }
        for (ONIIRegistryObject<Item> i : ONIItems.itemRegistryList) {
            if (i.doLangGen()) add("item.oniutils." + MiscHelper.langToReg(i.getRegName()), i.getRegName());
        }
    }
}