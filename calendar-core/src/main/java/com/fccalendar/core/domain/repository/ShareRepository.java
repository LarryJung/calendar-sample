package com.fccalendar.core.domain.repository;


import com.fccalendar.core.domain.RequestStatus;
import com.fccalendar.core.domain.Share;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShareRepository extends JpaRepository<Share, Long> {
    Optional<Share> findByIdAndToUserId(Long shareId, Long toUserId);

    List<Share> findAllByFromUserIdAndStatus(Long fromUserId,
                                             RequestStatus status);

    List<Share> findAllByToUserIdAndAndDirectionAndStatus(Long fromUserId,
                                                          Share.Direction direction,
                                                          RequestStatus status);
}
