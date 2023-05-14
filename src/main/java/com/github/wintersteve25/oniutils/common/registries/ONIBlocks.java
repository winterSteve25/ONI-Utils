package com.github.wintersteve25.oniutils.common.registries;

import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import com.github.wintersteve25.oniutils.ONIUtils;
import com.github.wintersteve25.oniutils.common.contents.base.blocks.ONIBaseBlock;
import com.github.wintersteve25.oniutils.common.contents.base.blocks.ONIBaseDirectional;
import com.github.wintersteve25.oniutils.common.contents.base.blocks.ONIBaseLoggableMachine;
import com.github.wintersteve25.oniutils.common.contents.base.blocks.bounding.ONIBoundingBlock;
import com.github.wintersteve25.oniutils.common.contents.base.blocks.bounding.ONIBoundingTE;
import com.github.wintersteve25.oniutils.common.contents.base.builders.ONIAbstractContainer;
import com.github.wintersteve25.oniutils.common.contents.base.builders.ONIBlockBuilder;
import com.github.wintersteve25.oniutils.common.contents.base.builders.ONIContainerBuilder;
import com.github.wintersteve25.oniutils.common.contents.base.enums.EnumCableTypes;
import com.github.wintersteve25.oniutils.common.contents.modules.blocks.power.cables.PowerCableBlock;
import com.github.wintersteve25.oniutils.common.contents.modules.blocks.power.coal.CoalGenTE;
import com.github.wintersteve25.oniutils.common.registration.block.ONIBlockDeferredRegister;
import com.github.wintersteve25.oniutils.common.registration.block.ONIBlockEntityDeferredRegister;
import com.github.wintersteve25.oniutils.common.registration.block.ONIDirectionalBlockRegistryData;
import com.github.wintersteve25.oniutils.common.utils.ONIConstants;
import com.github.wintersteve25.oniutils.common.utils.helpers.ModelFileHelper;
import com.github.wintersteve25.oniutils.common.utils.helpers.ResoureceLocationHelper;

public class ONIBlocks {

    public static final ONIBlockDeferredRegister BLOCKS = new ONIBlockDeferredRegister(ONIUtils.MODID);
    private static final ONIBlockEntityDeferredRegister BLOCK_ENTITIES = new ONIBlockEntityDeferredRegister(ONIUtils.MODID);
    public static final ContainerTypeDeferredRegister MENUS = new ContainerTypeDeferredRegister(ONIUtils.MODID);

    public static final class NonFunctionals {
        public static final BlockRegistryObject<Block, BlockItem> IGNEOUS_ROCK = BLOCKS.register("igneous_rock", () -> new ONIBaseBlock(1, 2, 6));
        public static final BlockRegistryObject<Block, BlockItem> SEDIMENTARY_ROCK = BLOCKS.register(ONIConstants.LangKeys.SEDIMENTARY_ROCK, () -> new ONIBaseDirectional(0, 4, 10), new ONIDirectionalBlockRegistryData(0, ModelFileHelper.createModelFile(ResoureceLocationHelper.ResourceLocationBuilder
                .getBuilder()
                .block()
                .rocks()
                .addPath(ONIConstants.LangKeys.SEDIMENTARY_ROCK)
                .build())));
        public static final BlockRegistryObject<Block, BlockItem> MAFIC_ROCK = BLOCKS.register("mafic_rock", () -> new ONIBaseBlock(2, 5, 18));
        public static final BlockRegistryObject<Block, BlockItem> REGOLITH = BLOCKS.register("regolith", () -> new ONIBaseBlock(1, 5, 18));
        public static final BlockRegistryObject<Block, BlockItem> NEUTRONIUM = BLOCKS.register("neutronium", () -> new ONIBaseBlock(3, 7, 30));
        public static final BlockRegistryObject<Block, BlockItem> FOSSIL = BLOCKS.register("fossil", () -> new ONIBaseBlock(0, 2, 3));
        public static final BlockRegistryObject<Block, BlockItem> BLEACH_STONE = BLOCKS.register("bleach_stone", () -> new ONIBaseBlock(1, 1.5F, 2));
        public static final BlockRegistryObject<Block, BlockItem> RUST = BLOCKS.register("rust", () -> new ONIBaseBlock(1, 1.5F, 2));
        public static final BlockRegistryObject<Block, BlockItem> POLLUTED_ICE = BLOCKS.register("polluted_ice", () -> new ONIBaseBlock(BlockBehaviour.Properties.of(Material.ICE).sound(SoundType.GLASS).strength(0.7F, 1).friction(0.98F)));
        public static final BlockRegistryObject<Block, BlockItem> ALGAE = BLOCKS.register("algae", () -> new ONIBaseBlock(BlockBehaviour.Properties.of(Material.PLANT).sound(SoundType.CROP).strength(0.2F, 1)), true, true, true, true);
        public static final BlockRegistryObject<Block, BlockItem> PHOSPHORITE = BLOCKS.register("phosphorite", () -> new ONIBaseBlock(1, 1, 2));
        public static final BlockRegistryObject<Block, BlockItem> FERTILIZER = BLOCKS.register("fertilizer", () -> new ONIBaseBlock(BlockBehaviour.Properties.of(Material.PLANT).sound(SoundType.CROP).strength(0.2F, 1)));
        public static final BlockRegistryObject<Block, BlockItem> GOLD_AMALGAM = BLOCKS.register("gold_amalgam", () -> new ONIBaseBlock(2, 3, 5));
        public static final BlockRegistryObject<Block, BlockItem> WOLFRAMITE = BLOCKS.register("wolframite", () -> new ONIBaseBlock(2, 6, 20));
        public static final BlockRegistryObject<Block, BlockItem> ABYSSALITE = BLOCKS.register("abyssalite", () -> new ONIBaseBlock(2, 5, 15));
        public static final BlockRegistryObject<Block, BlockItem> GRANITE = BLOCKS.register("granite", () -> new ONIBaseBlock(2, 4, 12));

        private static void register() {
        }
    }

    public static final class TileEntityBounded {
        //TE Blocks
        private static void register() {
        }
    }

    public static final class Machines {
        //Machines
        public static final class Power {
            //Power
            public static final BlockRegistryObject<ONIBaseLoggableMachine<CoalGenTE>, BlockItem> COAL_GEN_BLOCK = registerBuilder(CoalGenTE.createBlock());
            public static final TileEntityTypeRegistryObject<CoalGenTE> COAL_GEN_TE = BLOCK_ENTITIES.registerBE(COAL_GEN_BLOCK, CoalGenTE::new);
            public static final ONIContainerBuilder COAL_GEN_CONTAINER_BUILDER = CoalGenTE.createContainer();
            public static final ContainerTypeRegistryObject<ONIAbstractContainer> COAL_GEN_CONTAINER = COAL_GEN_CONTAINER_BUILDER.getContainerTypeRegistryObject();

//            public static final BlockRegistryObject<PowerCableBlock, BlockItem> WIRE_BLOCK = registerWire(EnumCableTypes.WIRE);
            public static final BlockRegistryObject<PowerCableBlock, BlockItem> CONDUCTIVE_WIRE_BLOCK = registerWire(EnumCableTypes.CONDUCTIVE);
//            public static final BlockRegistryObject<PowerCableBlock, BlockItem> HEAVI_WATT_WIRE_BLOCK = registerWire(EnumCableTypes.HEAVIWATTS);

            private static void register() {
            }
        }

        public static final class Oxygen {
            //Oxygen
            private static void register() {
            }
        }
    }

    //misc
    public static final class Misc {
        public static final BlockRegistryObject<ONIBoundingBlock, BlockItem> BOUNDING_BLOCK = BLOCKS.register("bounding_block", ONIBoundingBlock::new, false, false, false, false);
        public static final TileEntityTypeRegistryObject<ONIBoundingTE> BOUNDING_TE = BLOCK_ENTITIES.registerBE(BOUNDING_BLOCK, ONIBoundingTE::new);

        private static void register() {
        }
    }

    private static BlockRegistryObject<PowerCableBlock, BlockItem> registerWire(EnumCableTypes type) {
        return BLOCKS.register(type.getName(), () -> new PowerCableBlock(type), false, true, true, true);
    }

    private static <T extends ONIBaseBlock> BlockRegistryObject<T, BlockItem> registerBuilder(ONIBlockBuilder<T> builder) {
        var build = builder.build();
        return BLOCKS.register(builder.getRegName(), () -> build.getA().get(), (b) -> build.getB().apply(b), builder.isDoStateGen(), builder.isDoModelGen(), builder.isDoLangGen(), builder.isDoLootableGen());
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        BLOCK_ENTITIES.register(bus);
        MENUS.register(bus);

        NonFunctionals.register();
        TileEntityBounded.register();
        Machines.Power.register();
        Machines.Oxygen.register();
        Misc.register();
    }
}