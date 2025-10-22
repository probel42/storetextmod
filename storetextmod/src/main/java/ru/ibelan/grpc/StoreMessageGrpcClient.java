package ru.ibelan.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import ru.ibelan.store_message_service.StoreMessageRequest;
import ru.ibelan.store_message_service.StoreMessageResponse;
import ru.ibelan.store_message_service.StoreMessageServiceGrpc;

public class StoreMessageGrpcClient {
	private StoreMessageServiceGrpc.StoreMessageServiceBlockingStub stub;

	public void initClient(String host, int port) {
		ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
				.usePlaintext()
				.build();
		stub = StoreMessageServiceGrpc.newBlockingStub(channel);
	}

	public String storeMessagePrivate(String playerId, String text) {
		if (stub == null) {
			return null;
		}
		try {
			StoreMessageRequest request = StoreMessageRequest.newBuilder()
					.setPlayerId(playerId)
					.setText(text)
					.build();
			StoreMessageResponse response;

			response = stub.storeMessage(request);
			if (response.getErrorCode() == 0) {
				return null;
			} else {
				return response.getStatusMessage();
			}
		} catch (StatusRuntimeException e) {
			return e.getMessage();
		}
	}

}
