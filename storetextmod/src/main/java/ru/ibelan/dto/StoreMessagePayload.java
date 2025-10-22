package ru.ibelan.dto;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record StoreMessagePayload(String text) implements CustomPayload {
	public static final String STORE_MESSAGE_IDENTIFIER = "store-message-payload";

	public static final CustomPayload.Id<StoreMessagePayload> ID = new CustomPayload.Id<>(Identifier.of(STORE_MESSAGE_IDENTIFIER));
	public static final PacketCodec<RegistryByteBuf, StoreMessagePayload> CODEC = PacketCodec.tuple(
			PacketCodecs.STRING, StoreMessagePayload::text, StoreMessagePayload::new
	);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
