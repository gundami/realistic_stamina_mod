package realisticstamina.rstamina.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import realisticstamina.rstamina.RStaminaClient;
import realisticstamina.rstamina.RStaminaMod;

import java.lang.reflect.Array;
import java.util.Iterator;

public class StaminaHudOverlay implements HudRenderCallback {

    private static final Identifier FILLED_STAMINA = new Identifier(RStaminaMod.modid,
            "textures/gui/stamina_full.png");
    private static final Identifier HALF_STAMINA = new Identifier(RStaminaMod.modid,
            "textures/gui/stamina_half.png");
    private static final Identifier EMPTY_STAMINA = new Identifier(RStaminaMod.modid,
            "textures/gui/stamina_empty.png");
    private static final Identifier EMPTY_ENERGY = new Identifier(RStaminaMod.modid,
            "textures/gui/energy_empty.png");
    private static final Identifier FULL_ENERGY = new Identifier(RStaminaMod.modid,
            "textures/gui/energy_1.png");
    private static final Identifier FIVE_SIXTH_ENERGY = new Identifier(RStaminaMod.modid,
            "textures/gui/energy_2.png");
    private static final Identifier FOUR_SIXTH_ENERGY = new Identifier(RStaminaMod.modid,
            "textures/gui/energy_3.png");
    private static final Identifier THREE_SIXTH_ENERGY = new Identifier(RStaminaMod.modid,
            "textures/gui/energy_4.png");
    private static final Identifier TWO_SIXTH_ENERGY = new Identifier(RStaminaMod.modid,
            "textures/gui/energy_5.png");
    private static final Identifier ONE_SIXTH_ENERGY = new Identifier(RStaminaMod.modid,
            "textures/gui/energy_6.png");

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {

        MinecraftClient client = MinecraftClient.getInstance();
        assert client != null;
        PlayerEntity player = client.player;
        assert player != null;
        boolean isCreative = player.isCreative();
        boolean isSpectator = player.isSpectator();

        if (!isCreative && !isSpectator) {

            //Stamina bar
            int x = 0;
            int y = 0;
            if (client != null) {
                int width = client.getWindow().getScaledWidth();
                int height = client.getWindow().getScaledHeight();

                x = width / 2 - 29 -29 - 29 - 12;
                y = height;
            }
            int offset = 49;
            if (player.getArmor() > 0){
                offset += 10;
            }
            //if (player.getHealth() > 20){
            //    offset += (player.getHealth()/20) * 10;
            //}
            Iterator<StatusEffectInstance> iterator = player.getStatusEffects().iterator();
            while (iterator.hasNext()){
                if(iterator.next().getEffectType().equals(StatusEffects.ABSORPTION)){
                    offset += 10;
                }
            }

            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, EMPTY_STAMINA);

            int z = 0;
            long stamina = Math.round(RStaminaClient.clientStoredStamina);

            for (int i = 0; i < 10; i++) {
                if (stamina  <= 1) {
                    z = offset + (Random.create().nextInt(3) - 1);
                } else {
                    z = offset;
                }
                drawContext.drawTexture(EMPTY_STAMINA, x + 8 + (i * 8), y - z, 0, 0, 9, 9,
                        9, 9);
            }
            for (int i = 0; i < 10; i++) {
                assert MinecraftClient.getInstance().player != null;
                long unit = Math.round(RStaminaClient.clientStoredMaxStamina)/10;
                if (stamina > (unit*i)) {
                    if (stamina - unit*i <= unit/2){
                        RenderSystem.setShaderTexture(0, HALF_STAMINA);
                        drawContext.drawTexture(HALF_STAMINA, x + 80 - ((10-1-i) * 8), y - z, 0, 0, 9, 9, 9, 9);
                    }else {
                        RenderSystem.setShaderTexture(0, FILLED_STAMINA);
                        drawContext.drawTexture(FILLED_STAMINA, x + 80 - ((10-1-i) * 8), y - z, 0, 0, 9, 9, 9, 9);
                    }
                } else {
                    break;
                }
            }
            //Energy ball
            int ex = 0;
            int ey = 0;
            if (client != null) {
                int width = client.getWindow().getScaledWidth();
                int height = client.getWindow().getScaledHeight();

                ex = width /2;
                ey = height;
            }
            int eoffset = 57;

            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, EMPTY_ENERGY);

            long energy = Math.round(RStaminaClient.clientStoredEnergy);
            int ez = 0;

            ez = eoffset;

            drawContext.drawTexture(EMPTY_ENERGY, ex-8, ey - ez, 0, 0, 16, 16, 16, 16);

            if (energy <= 100 && energy >= 84){
                RenderSystem.setShaderTexture(0, FULL_ENERGY);
                drawContext.drawTexture(FULL_ENERGY, ex-8, ey - ez, 0, 0, 16, 16, 16, 16);
            } else if (energy < 84 && energy >= 68) {
                RenderSystem.setShaderTexture(0, FIVE_SIXTH_ENERGY);
                drawContext.drawTexture(FIVE_SIXTH_ENERGY, ex-8, ey - ez, 0, 0, 16, 16, 16, 16);
            } else if (energy < 68 && energy >=52) {
                RenderSystem.setShaderTexture(0, FOUR_SIXTH_ENERGY);
                drawContext.drawTexture(FOUR_SIXTH_ENERGY, ex-8, ey - ez, 0, 0, 16, 16, 16, 16);
            } else if (energy < 52 && energy >=36){
                RenderSystem.setShaderTexture(0, THREE_SIXTH_ENERGY);
                drawContext.drawTexture(THREE_SIXTH_ENERGY, ex-8, ey - ez, 0, 0, 16, 16, 16, 16);
            } else if (energy < 36 && energy >=20) {
                RenderSystem.setShaderTexture(0, TWO_SIXTH_ENERGY);
                drawContext.drawTexture(TWO_SIXTH_ENERGY, ex-8, ey - ez, 0, 0, 16, 16, 16, 16);
            } else if (energy < 20 && energy >0) {
                RenderSystem.setShaderTexture(0, ONE_SIXTH_ENERGY);
                drawContext.drawTexture(ONE_SIXTH_ENERGY, ex-8, ey - ez, 0, 0, 16, 16, 16, 16);
            //} else if (energy < 12.5 && energy >0) {
            //    RenderSystem.setShaderTexture(0, EMPTY_ENERGY);
            //    drawContext.drawTexture(EMPTY_ENERGY, ex-8, ey - ez, 0, 0, 16, 16, 9, 9);
            }
            //TextRenderer textRenderer = client.textRenderer;
            //drawContext.drawTextWithShadow(textRenderer, Text.literal("§a"+((int)RStaminaClient.clientStoredEnergy) + "%"), ex-8, ey - ez + 4, 16777215);

        }

        /*
        //RStaminaMod.LOGGER.info("renderkajsd;flakjsdf");

        int x = RStaminaMod.config.hudX;
        int y = RStaminaMod.config.hudY;

        MinecraftClient client = MinecraftClient.getInstance();

        //RenderSystem.setShader(GameRenderer::getPositionTexShader);
        //RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        //RenderSystem.setShaderTexture(0, )

        if (client != null) {

            TextRenderer textRenderer = client.textRenderer;

            if ((Math.round(RStaminaClient.clientStoredStamina)) > 24.0 && (Math.round(RStaminaClient.clientStoredStamina)) == (Math.round(RStaminaClient.clientStoredMaxStamina))) {
                drawContext.drawTextWithShadow(textRenderer, Text.literal("§aStamina: §a" + (Math.round(RStaminaClient.clientStoredStamina)) + "§7/" + (Math.round(RStaminaClient.clientStoredMaxStamina))), x, y, 16777215);
            } else if ((Math.round(RStaminaClient.clientStoredStamina)) >= 24.0 && (Math.round(RStaminaClient.clientStoredStamina)) < (Math.round(RStaminaClient.clientStoredMaxStamina))) {
                drawContext.drawTextWithShadow(textRenderer, Text.literal("§2Stamina: §a" + (Math.round(RStaminaClient.clientStoredStamina)) + "§7/" + (Math.round(RStaminaClient.clientStoredMaxStamina))), x, y, 16777215);
            } else if ((Math.round(RStaminaClient.clientStoredStamina)) < 24.0 && (Math.round(RStaminaClient.clientStoredStamina)) > 12) {
                drawContext.drawTextWithShadow(textRenderer, Text.literal("§2Stamina: §e" + (Math.round(RStaminaClient.clientStoredStamina)) + "§7/" + (Math.round(RStaminaClient.clientStoredMaxStamina))), x, y, 16777215);
            } else if ((Math.round(RStaminaClient.clientStoredStamina)) <= 12 && (Math.round(RStaminaClient.clientStoredStamina)) > 0) {
                drawContext.drawTextWithShadow(textRenderer, Text.literal("§2Stamina: §6" + (Math.round(RStaminaClient.clientStoredStamina)) + "§7/" + (Math.round(RStaminaClient.clientStoredMaxStamina))), x, y, 16777215);
            } else if ((Math.round(RStaminaClient.clientStoredStamina)) <= 0) {
                drawContext.drawTextWithShadow(textRenderer, Text.literal("§2Stamina: §c" + (Math.round(RStaminaClient.clientStoredStamina)) + "§7/" + (Math.round(RStaminaClient.clientStoredMaxStamina))), x, y, 16777215);
            }

            drawContext.drawTextWithShadow(textRenderer, Text.literal("§eEnergy: §f" + ((float)RStaminaClient.clientStoredEnergy) + "%"), x, y + 10, 16777215);

        }

         */



    }
}
