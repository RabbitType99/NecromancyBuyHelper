package dev.RabbitType99.NecromancyBuyHelper;

import dev.RabbitType99.NecromancyBuyHelper.NecromancyBuyHelper;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "necromancybuyhelper", name = "NecromancyBuyHelper", version = "1.0",clientSideOnly = true)
public class NecromancyBuyHelperInit {
    @EventHandler
    public void init(FMLInitializationEvent event){
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new NecromancyBuyHelper());
    }
}
