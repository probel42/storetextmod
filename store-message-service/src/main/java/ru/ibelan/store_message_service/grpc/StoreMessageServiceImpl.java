package ru.ibelan.store_message_service.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import ru.ibelan.store_message_service.StoreMessageRequest;
import ru.ibelan.store_message_service.StoreMessageResponse;
import ru.ibelan.store_message_service.StoreMessageServiceGrpc;
import ru.ibelan.store_message_service.entities.StoreMessage;
import ru.ibelan.store_message_service.repositories.StoreMessageRepository;

import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
public class StoreMessageServiceImpl extends StoreMessageServiceGrpc.StoreMessageServiceImplBase {

	private final StoreMessageRepository storeMessageRepository;

	@Override
	public void storeMessage(StoreMessageRequest request, StreamObserver<StoreMessageResponse> responseObserver) {
		try {
			UUID playerId = UUID.fromString(request.getPlayerId());
			String text = request.getText();

			StoreMessage message = new StoreMessage();
			message.setUuid(playerId);
			message.setText(text);
			storeMessageRepository.save(message);

			responseObserver.onNext(StoreMessageResponse.newBuilder()
					.setErrorCode(0)
					.build());
			responseObserver.onCompleted();
		} catch (IllegalArgumentException illegalArgumentException) {
			responseObserver.onNext(StoreMessageResponse.newBuilder()
					.setErrorCode(1)
					.setStatusMessage(illegalArgumentException.getMessage())
					.build());
			responseObserver.onError(illegalArgumentException);
		}
	}
}
