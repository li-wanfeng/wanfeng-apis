package com.wanfeng.gatetway.utils;

public enum RedisKeys {
    BRUSH_PROOF("bursh_proof1",60l),
    BRUSH_PROOF222("bursh_proof2",60l),
    BRUSH_PROOF3("bursh_proof3",60l);

    private String prefix;
    private Long time;

    RedisKeys(String prefix, Long time) {
        this.prefix = prefix;  //key前缀
        this.time = time; //单位秒
    }

    public String getPrefix() {
        return prefix;
    }

    public Long getTime() {
        return time;
    }

    public String getkey(String... keys) {
        StringBuilder builder = new StringBuilder(prefix);
        for (String key : keys) {
            builder.append(":").append(key);
        }
        return builder.toString();
    }
}
