package com.zg.natural_transmute.client.gui;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.client.inventory.HarmoniousChangeStoveMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HarmoniousChangeStoveScreen extends AbstractContainerScreen<HarmoniousChangeStoveMenu> {

    private static final String PATH = "textures/gui/harmonious_change_stove_";
    private static final ResourceLocation PROGRESS_BAR_0 = NaturalTransmute.prefix("harmonious_change_bar_0");
    private static final ResourceLocation PROGRESS_BAR_1 = NaturalTransmute.prefix("harmonious_change_bar_1");

    public HarmoniousChangeStoveScreen(HarmoniousChangeStoveMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {}

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBg(guiGraphics, partialTick, mouseX, mouseY);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int time = Mth.ceil(this.menu.getHarmoniousChangeTime() * 51.0F);
        ResourceLocation texture = NaturalTransmute.prefix(PATH + this.menu.getCurrentState() + ".png");
        guiGraphics.blit(texture, getGuiLeft(), getGuiTop(), 0, 0, this.imageWidth, this.imageHeight);
        guiGraphics.blitSprite(PROGRESS_BAR_0, (51), (21), (0), (0), getGuiLeft() + 47, getGuiTop() + 35, time, (21));

        var remaining = 49;
        if (!this.menu.hasFuelBucket()) {
            var now = Mth.clamp(this.menu.getFuelRemain(), 0, 8);
            if (now == 0) {
                remaining = 0;
            } else {
                remaining = 1 + now * 6;
            }
        }
        guiGraphics.blitSprite(PROGRESS_BAR_1, 49, 3, 0, 0, getGuiLeft() + 107, getGuiTop() + 22, remaining, 3);
    }

}