package org.lanjerry.admin.redis;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestRedisBean {

    private Integer id;

    private String name;
}
