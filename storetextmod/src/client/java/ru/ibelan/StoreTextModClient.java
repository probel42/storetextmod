package ru.ibelan;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ibelan.dto.StoreMessagePayload;
import ru.ibelan.screen.StoreMessageScreen;

public class StoreTextModClient implements ClientModInitializer {
	public static final String MOD_ID = "storetextmod.client";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		initCommands();
	}

	private void initCommands() {
		initStoreMessageCommand();
	}

	private void initStoreMessageCommand() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher
				.register(ClientCommandManager.literal("store-message")
						.executes(context -> {
							MinecraftClient client = context.getSource().getClient();
							client.send(() -> client.setScreen(new StoreMessageScreen(this::onSend)));
							return 1;
						})));
	}

	private void onSend(String text) {
		if (text != null && !text.isEmpty()) {
			LOGGER.debug("sending message \"{}\"", text);
			ClientPlayNetworking.send(new StoreMessagePayload(text));
		}
	}
}