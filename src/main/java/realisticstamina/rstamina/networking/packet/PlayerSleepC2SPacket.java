package realisticstamina.rstamina.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import realisticstamina.rstamina.RStaminaMod;
import realisticstamina.rstamina.RStaminaPlayerState;
import realisticstamina.rstamina.ServerState;

public class PlayerSleepC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {

        ServerState serverState = ServerState.getServerState(server);
        RStaminaPlayerState playerState = ServerState.getPlayerState(player);

        if (RStaminaMod.config.fitnessSystem) {

            double energy = (playerState.energy - playerState.energyFromResting);

            if (energy < RStaminaMod.config.fitnessEnergyToGain) {
                if (playerState.totalStamina < RStaminaMod.config.fitnessStaminaLimit) {
                    playerState.totalStamina += RStaminaMod.config.fitnessStaminaChange;
                    player.sendMessageToClient(Text.literal("§a+" + RStaminaMod.config.fitnessStaminaChange + " Total Stamina"), true);
                }
            } else if (energy > RStaminaMod.config.fitnessEnergyToLose && energy < 100.0) {
                if (playerState.totalStamina > RStaminaMod.config.totalStamina) {
                    playerState.totalStamina -= RStaminaMod.config.fitnessStaminaChange;
                    player.sendMessageToClient(Text.literal("§c-" + RStaminaMod.config.fitnessStaminaChange + " Total Stamina"), true);
                    if (playerState.totalStamina < RStaminaMod.config.totalStamina) {
                        playerState.totalStamina = RStaminaMod.config.totalStamina;
                    }
                }
            }
        }

        playerState.energy = 100.0;
        playerState.maxStamina = (playerState.totalStamina * (playerState.energy / 100));
        playerState.energyFromResting = 0.0;
        serverState.markDirty();

    }

}
