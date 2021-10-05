package com.eduardo.communication.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eduardo.communication.platform.model.CommunicationSchedule;

public interface CommunicationScheduleRepository extends JpaRepository<CommunicationSchedule, Long> {

}
