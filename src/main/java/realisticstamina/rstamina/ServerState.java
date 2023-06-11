package realisticstamina.rstamina;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;

public class ServerState extends PersistentState {

    int testInt = 0;

    public HashMap<UUID, PlayerState> players = new HashMap<>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {

        NbtCompound playersNbtCompound = new NbtCompound();
        players.forEach((UUID, playerSate) -> {
            NbtCompound playerStateNbt = new NbtCompound();

            playerStateNbt.putInt("testplayerdata", playerSate.testplayerdata);
            playerStateNbt.putDouble("stamina", playerSate.stamina);
            playerStateNbt.putDouble("maxStamina", playerSate.maxStamina);
            playerStateNbt.putDouble("totalStamina", playerSate.totalStamina);
            playerStateNbt.putDouble("energy", playerSate.energy);
            playerStateNbt.putDouble("energyFromResting", playerSate.energyFromResting);


            playersNbtCompound.put(String.valueOf(UUID), playerStateNbt);
        });
        nbt.put("players", playersNbtCompound);

        nbt.putInt("testInt", testInt);
        return nbt;
    }

    public static ServerState createFromNbt(NbtCompound tag) {

        ServerState serverState = new ServerState();

        NbtCompound playersTag = tag.getCompound("players");
        playersTag.getKeys().forEach(key -> {
            PlayerState playerState = new PlayerState();

            playerState.testplayerdata = playersTag.getCompound(key).getInt("testplayerdata");
            playerState.stamina = playersTag.getCompound(key).getDouble("stamina");
            playerState.maxStamina = playersTag.getCompound(key).getDouble("maxStamina");
            playerState.totalStamina = playersTag.getCompound(key).getDouble("totalStamina");
            playerState.energy = playersTag.getCompound(key).getDouble("energy");
            playerState.energyFromResting = playersTag.getCompound(key).getDouble("energyFromResting");

            UUID uuid = UUID.fromString(key);
            serverState.players.put(uuid, playerState);
        });

        serverState.testInt = tag.getInt("testInt");

        return serverState;
    }

    public static ServerState getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server
                .getWorld(World.OVERWORLD).getPersistentStateManager();

        ServerState serverState = persistentStateManager.getOrCreate(
                ServerState::createFromNbt,
                ServerState::new,
                RStaminaMod.modid);

        return serverState;
    }

    public static PlayerState getPlayerState(LivingEntity player) {
        ServerState serverState = getServerState(player.world.getServer());

        PlayerState playerState = serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerState());

        return playerState;
    }
}