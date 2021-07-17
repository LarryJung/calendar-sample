package com.fccalendar.api.dto;

import com.fccalendar.core.domain.Share;
import lombok.Data;

@Data
public class ShareCreateReq {
    private final Long toUserId;
    private final Share.Direction direction;
}
