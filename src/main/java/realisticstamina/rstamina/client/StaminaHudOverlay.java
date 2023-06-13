package realisticstamina.rstamina.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import realisticstamina.rstamina.RStaminaClient;
import realisticstamina.rstamina.RStaminaMod;
import realisticstamina.rstamina.networking.NetworkingPackets;

public class StaminaHudOverlay implements HudRenderCallback {



    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {

        //RStaminaMod.LOGGER.info("renderkajsd;flakjsdf");

        int x = RStaminaMod.config.hudX;
        int y = RStaminaMod.config.hudY;

        MinecraftClient client = MinecraftClient.getInstance();

        //RenderSystem.setShader(GameRenderer::getPositionTexShader);
        //RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        //RenderSystem.setShaderTexture(0, )

        if (client != null) {

            ClientPlayNetworking.send(NetworkingPackets.REQUEST_PLAYERSTATE_C2S_PACKET_ID, PacketByteBufs.create());

            TextRenderer textRenderer = client.textRenderer;

            if ((Math.round(RStaminaClient.clientStoredStamina)) > 24.0 && (Math.round(RStaminaClient.clientStoredStamina)) == (Math.round(RStaminaClient.clientStoredMaxStamina))) {
                textRenderer.drawWithShadow(matrixStack, Text.literal("§aStamina: §a" + (Math.round(RStaminaClient.clientStoredStamina)) + "§7/" + (Math.round(RStaminaClient.clientStoredMaxStamina))), x, y, 16777215);
            } else if ((Math.round(RStaminaClient.clientStoredStamina)) >= 24.0 && (Math.round(RStaminaClient.clientStoredStamina)) < (Math.round(RStaminaClient.clientStoredMaxStamina))) {
                textRenderer.drawWithShadow(matrixStack, Text.literal("§2Stamina: §a" + (Math.round(RStaminaClient.clientStoredStamina)) + "§7/" + (Math.round(RStaminaClient.clientStoredMaxStamina))), x, y, 16777215);
            } else if ((Math.round(RStaminaClient.clientStoredStamina)) < 24.0 && (Math.round(RStaminaClient.clientStoredStamina)) > 12) {
                textRenderer.drawWithShadow(matrixStack, Text.literal("§2Stamina: §e" + (Math.round(RStaminaClient.clientStoredStamina)) + "§7/" + (Math.round(RStaminaClient.clientStoredMaxStamina))), x, y, 16777215);
            } else if ((Math.round(RStaminaClient.clientStoredStamina)) <= 12 && (Math.round(RStaminaClient.clientStoredStamina)) > 0) {
                textRenderer.drawWithShadow(matrixStack, Text.literal("§2Stamina: §6" + (Math.round(RStaminaClient.clientStoredStamina)) + "§7/" + (Math.round(RStaminaClient.clientStoredMaxStamina))), x, y, 16777215);
            } else if ((Math.round(RStaminaClient.clientStoredStamina)) <= 0) {
                textRenderer.drawWithShadow(matrixStack, Text.literal("§2Stamina: §c" + (Math.round(RStaminaClient.clientStoredStamina)) + "§7/" + (Math.round(RStaminaClient.clientStoredMaxStamina))), x, y, 16777215);
            }

            textRenderer.drawWithShadow(matrixStack, Text.literal("§eEnergy: §f" + ((float)RStaminaClient.clientStoredEnergy) + "%"), x, y + 10, 16777215);

        }

    }
}
