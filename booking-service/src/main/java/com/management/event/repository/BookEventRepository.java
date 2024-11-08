package com.management.event.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.management.event.jpa.entity.BookEvent;

@Repository
public interface BookEventRepository extends MongoRepository<BookEvent, String> {
	List<BookEvent> findAllByRoomId(Long roomId);
}
