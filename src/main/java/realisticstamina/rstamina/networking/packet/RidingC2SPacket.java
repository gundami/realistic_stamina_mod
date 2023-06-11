package realisticstamina.rstamina.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import realisticstamina.rstamina.PlayerState;
import realisticstamina.rstamina.RStaminaMod;
import realisticstamina.rstamina.ServerState;

import java.util.Objects;

public class RidingC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {

        ServerState serverState = ServerState.getServerState(server);
        PlayerState playerstate = ServerState.getPlayerState(player);

        String vehicle = buf.readString();
        boolean isRiding = buf.readBoolean();

        if (RStaminaMod.config.enableResting) {
            boolean rest = true;
            RStaminaMod.LOGGER.info("V: " + vehicle + " R: " + isRiding);
            if (Objects.equals(vehicle, "Horse")) {
                if (!RStaminaMod.config.restRidingHorse) {rest = false;}
            }
            if (!RStaminaMod.config.restWhileBoatMoving) {
                if (isRiding) {rest = false;}
            }
            if (rest) {
                if (RStaminaMod.config.restingEnergyGainTick > 0) {
                    if ((playerstate.energy + RStaminaMod.config.restingEnergyGainTick) > 100.0) {
                        playerstate.energy = 100.0;
                        playerstate.maxStamina = (playerstate.totalStamina * (playerstate.energy / 100));
                        serverState.markDirty();
                    } else {
                        playerstate.energy += RStaminaMod.config.restingEnergyGainTick;
                        playerstate.maxStamina = (playerstate.totalStamina * (playerstate.energy / 100));
                        serverState.markDirty();
                    }
                }
            }
        }

    }

}
