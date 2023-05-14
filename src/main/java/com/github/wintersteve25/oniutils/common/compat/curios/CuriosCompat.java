package com.github.wintersteve25.oniutils.common.compat.curios;

import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.SlotTypeMessage;
import com.github.wintersteve25.oniutils.common.utils.ONIConstants;
import com.github.wintersteve25.oniutils.common.utils.helpers.LangHelper;

public class CuriosCompat {
    public static void register(InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("oniutils_goggles")
                .icon(ONIConstants.Resources.CURIOS_GOGGLES)
                .build());
    }

    public static void lang(LanguageProvider provider) {
        provider.add(LangHelper.curiosSlot("oniutils_goggles").getKey(), "Goggles");
    }
}
