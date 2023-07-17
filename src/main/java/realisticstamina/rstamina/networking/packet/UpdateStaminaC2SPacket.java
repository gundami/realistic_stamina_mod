package realisticstamina.rstamina.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import realisticstamina.rstamina.PlayerState;
import realisticstamina.rstamina.ServerState;

public class UpdateStaminaC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {

        ServerState serverState = ServerState.getServerState(server);
        PlayerState playerstate = ServerState.getPlayerState(player);

        boolean waterLogged = false;

        if (player.getWorld().getBlockState(player.getBlockPos()) == Blocks.WATER.getDefaultState()) {

            waterLogged = true;

        }

        if (player.isSprinting() || player.isSwimming()) {

            if (!player.isCreative() && !player.isSpectator()) {

                playerstate.stamina -= 0.25;
                playerstate.energy -= 0.004;
                playerstate.maxStamina = (playerstate.totalStamina * (playerstate.energy / 100));
                serverState.markDirty();
            }

        } else if (!player.isSprinting() && !player.isSwimming() && !player.isClimbing() && !waterLogged && playerstate.stamina < playerstate.maxStamina) {

            playerstate.stamina += 0.125;
            if (playerstate.stamina >  playerstate.maxStamina) {
                playerstate.stamina = playerstate.maxStamina;
            }

        }

        if (playerstate.stamina <= 24.0 && playerstate.stamina > 12.0) {

            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 3, 1, true, false));

        } else if (playerstate.stamina <= 12.0 && playerstate.stamina > 0.0) {

            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 3, 4, true, false));

        } else if (playerstate.stamina <= 0.0) {

            playerstate.stamina = 0.0;
            serverState.markDirty();
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 3, 5, true, false));

        }

        if (player.getHealth() <= 0.0) {
            playerstate.stamina = playerstate.maxStamina;
            playerstate.energy = 100.0;
            playerstate.energyFromResting = 0.0;
            playerstate.maxStamina = playerstate.totalStamina;
        }

    }

}
