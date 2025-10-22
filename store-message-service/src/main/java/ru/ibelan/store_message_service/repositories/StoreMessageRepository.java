package ru.ibelan.store_message_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ibelan.store_message_service.entities.StoreMessage;

@Repository
public interface StoreMessageRepository extends JpaRepository<StoreMessage, Long> {
}
