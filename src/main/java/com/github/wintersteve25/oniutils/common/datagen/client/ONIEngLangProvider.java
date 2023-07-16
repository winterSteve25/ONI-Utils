package com.github.wintersteve25.oniutils.common.datagen.client;

import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.ChatFormatting;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.ModList;
import org.apache.commons.lang3.text.WordUtils;
import com.github.wintersteve25.oniutils.ONIUtils;
import com.github.wintersteve25.oniutils.client.gui.ONIBaseGuiTab;
import com.github.wintersteve25.oniutils.common.compat.curios.CuriosCompat;
import com.github.wintersteve25.oniutils.common.data.capabilities.germ.api.EnumGermType;
import com.github.wintersteve25.oniutils.common.data.capabilities.player_data.api.SkillType;
import com.github.wintersteve25.oniutils.common.data.capabilities.player_data.api.TraitType;
import com.github.wintersteve25.oniutils.common.data.capabilities.world_gas.api.chemistry.Element;
import com.github.wintersteve25.oniutils.common.registration.block.ONIBlockRegistryData;
import com.github.wintersteve25.oniutils.common.registration.item.ONIItemRegistryData;
import com.github.wintersteve25.oniutils.common.registries.ONIBlocks;
import com.github.wintersteve25.oniutils.common.registries.ONIItems;

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

        add("oniutils.message.needLevel", ChatFormatting.RED + "Requires %s level of %s to operate... Perhaps some complexity modifications might help");
        
        add("oniutils.message.requests.buildCanceled", "%s build has been canceled");

        add("death.attack.oniutils.oxygen", "%1$s forgot to breathe");
        add("death.attack.oniutils.gas", "%1$s forgot to wear a \"mask\"");
        add("death.attack.oniutils.germ", "%1$s didn't take the vaccine");

        add("oniutils.commands.germs.set.success", "Germ Amount Set to: %s %s");
        add("oniutils.commands.germs.get.success", "Current Germ Amount is: %s %s");
        add("oniutils.commands.germs.set.failed.typeIsNull", "Germ type is not valid");
        add("oniutils.commands.set.failed.entityIsNull", "Target can not be null");
        add("oniutils.commands.gas.set.failed.typeIsNull", "Gas type is not valid");

        //tooltips
        add("oniutils.tooltips.germs.itemGerms", ChatFormatting.GREEN + "Surface Germs: %s %s");
        add("oniutils.tooltips.items.holdShiftInfo", ChatFormatting.DARK_GRAY + "Hold" + ChatFormatting.WHITE + " <Sneak> " + ChatFormatting.DARK_GRAY + "For More Info");
        add("oniutils.tooltips.items.coal_generator", ChatFormatting.GRAY + "Coal Generator is a primitive generator that takes only coal, to generate a small amount of plasma");
        add("oniutils.tooltips.items.modification", ChatFormatting.DARK_AQUA + "Modifications allow attributes of machines to be modified");
        add("oniutils.tooltips.items.modification.velocity", ChatFormatting.GRAY + "Velocity Modification allow you to modify machine's operating speed. Up to (-)125%. 25% more each tier");
        add("oniutils.tooltips.items.modification.energy", ChatFormatting.GRAY + "Plasma Conservation Modification allow you to modify machine's power consumption rate. Up to (-)110%. 10% more each tier");
        add("oniutils.tooltips.items.modification.gas", ChatFormatting.GRAY + "Gas Efficiency Modification allow you to modify machine's gas consumption rate. Up to (-)125%. 25% more each tier");
        add("oniutils.tooltips.items.modification.fluid", ChatFormatting.GRAY + "Fluid Efficiency Modification allow you to modify machine's fluid consumption rate. Up to (-)125%. 25% more each tier");
        add("oniutils.tooltips.items.modification.temperature", ChatFormatting.GRAY + "Temperature Modification allow you to modify machine's temperature capacity. Up to (-)120%. 20% more each tier");
        add("oniutils.tooltips.items.modification.complexity", ChatFormatting.GRAY + "Complexity Modification allow you to modify machine's required operation skill level. Up to (-)125%. 25% more each tier");
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

        // curios
        if (ModList.get().isLoaded("curios")) {
            CuriosCompat.lang(this);
        }
    }

    private void autoGenLang() {
        for (BlockRegistryObject<? extends Block, ? extends BlockItem> b : ONIBlocks.BLOCKS.getAllBlocks().keySet()) {
            ONIBlockRegistryData data = ONIBlocks.BLOCKS.getAllBlocks().get(b);
            if (data.isDoLangGen()) add("block.oniutils." + b.getName(), WordUtils.capitalizeFully(b.getName().replace("_", " ")));
        }
        for (ItemRegistryObject<? extends Item> i : ONIItems.ITEMS.getAllItems().keySet()) {
            ONIItemRegistryData data = ONIItems.ITEMS.getAllItems().get(i);
            if (data.isDoLangGen()) add("item.oniutils." + i.getName(), WordUtils.capitalizeFully(i.getName().replace("_", " ")));
        }
        for (Element element : Element.values()) {
            add("gas.oniutils." + element.getName(), element.getLang() + " Gas");
        }
        for (EnumGermType germType : EnumGermType.values()) {
            add("germ.oniutils." + germType.getName(), WordUtils.capitalizeFully(germType.getName().replace("_", " ")));
        }
        for (SkillType skillType : SkillType.values()) {
            String n = skillType.name().toLowerCase();
            add("skill.oniutils." + n, WordUtils.capitalizeFully(n.replace("_", " ")));
        }
        for (TraitType traitType : TraitType.values()) {
            String n = traitType.name().toLowerCase();
            add("trait.oniutils." + n, WordUtils.capitalizeFully(n.replace("_", " ")));
        }
    }
}