package realisticstamina.rstamina;

import me.lortseam.completeconfig.data.ConfigOptions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realisticstamina.rstamina.item.TestItem;
import realisticstamina.rstamina.networking.NetworkingPackets;

public class RStaminaMod implements ModInitializer {

	public static final String modid = "rstamina";
	public static final Logger LOGGER = LoggerFactory.getLogger(modid);

	public static final RStaminaConfig config = new RStaminaConfig();

	//items
	public static final TestItem TEST_ITEM = new TestItem(new FabricItemSettings());

	@Override
	public void onInitialize() {

		//config
		config.load();
		ConfigOptions.mod(modid).branch(new String[]{"branch", "config"});

		//items
		Registry.register(Registry.ITEM, new Identifier("rstamina", "test_item"), TEST_ITEM);

		//networking
		NetworkingPackets.registerC2SPackets();

		//events
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {

			ServerState serverState = ServerState.getServerState(handler.player.world.getServer());
			PlayerState playerState = ServerState.getPlayerState(handler.player);

			if (playerState.totalStamina != config.totalStamina) {

				playerState.totalStamina = config.totalStamina;
				playerState.maxStamina = (playerState.totalStamina * (playerState.energy / 100));
				playerState.stamina = playerState.maxStamina;
				serverState.markDirty();

			}

		});

	}
}