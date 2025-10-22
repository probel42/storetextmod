package ru.ibelan;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ibelan.dto.StoreMessagePayload;
import ru.ibelan.grpc.StoreMessageGrpcClient;

import java.util.UUID;

public class StoreTextMod implements ModInitializer {
	public static final String MOD_ID = "storetextmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private StoreMessageGrpcClient grpcClient = new StoreMessageGrpcClient();

	@Override
	public void onInitialize() {
		registerStoreMessageReceiver();
		grpcClient.initClient("localhost", 9090);
	}

	private void registerStoreMessageReceiver() {
		PayloadTypeRegistry.playC2S().register(StoreMessagePayload.ID, StoreMessagePayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(StoreMessagePayload.ID, (payload, context) -> {
			UUID playerId = context.player().getUuid();
			String text = payload.text();
			grpcClient.storeMessagePrivate(String.valueOf(playerId), text);
		});
	}
}